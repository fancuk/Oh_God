package com.moonbanggoo.ohgod.Activity

import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.moonbanggoo.ohgod.DataBase.SQLiteController.createTable
import com.moonbanggoo.ohgod.DataBase.SQLiteController.insertCity
import com.moonbanggoo.ohgod.DataBase.SQLiteController.openDatabase
import com.moonbanggoo.ohgod.DataBase.SQLiteController.selectCity
import com.moonbanggoo.ohgod.Model.WeatherModel
import com.moonbanggoo.ohgod.Network.Address
import com.moonbanggoo.ohgod.Network.OpenApi
import com.moonbanggoo.ohgod.Presenter.LocationPresenter.getLocation
import com.moonbanggoo.ohgod.Presenter.LocationPresenter.setLocation
import com.moonbanggoo.ohgod.Presenter.PlanListFragmentPresenter.selectDataInPresenter
import com.moonbanggoo.ohgod.Presenter.UseAlarmPresenter
import com.moonbanggoo.ohgod.Presenter.WeatherPresenter.setPercent
import com.moonbanggoo.ohgod.Presenter.WeatherPresenter.setWeather
import kotlinx.coroutines.*
import java.lang.Runnable
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.moonbanggoo.ohgod.Receiver.BootCompletedReceiver


class SplashActivity : AppCompatActivity(){
    lateinit var editor : SharedPreferences

    var TAG = "LOADING ACTIVITY"
    var REST_TAG = "REST_API"
    var SHARED_TEAG = "SHARED_PREFERENCE_JOBS"

    lateinit var openApi: OpenApi
    lateinit var address: Address

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        editor = getSharedPreferences("AlarmTime", Context.MODE_PRIVATE)

        //myJob = Job()
        this.openApi = OpenApi()
        this.address = Address()
        startLoading()
    }

    fun getAlarm(){
        val receiver = ComponentName(this, BootCompletedReceiver::class.java)
        this.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        UseAlarmPresenter.initThis(editor)
        var item  = UseAlarmPresenter.getNowTimeModel()
        Toast.makeText(this,"${item.am_pm} ${item.hour}시 ${item.minute}분에 알람이 설정되어있어요",Toast.LENGTH_SHORT)
    }
    //coroutine
//    private lateinit var myJob : Job
//    private var handler = CoroutineExceptionHandler{
//            coroutineContext, throwable -> Log.e("Exception", ":" + throwable)
//    }
//    override val coroutineContext: CoroutineContext
//        get() = myJob + Dispatchers.Main

    fun getRest(){
        GlobalScope.launch {
          print(Thread.currentThread().name)
            Log.d(REST_TAG, "Address Code를 불러옵니다...............")
            var addressCode = address.getAddressCode(getLocation())
            Log.d(REST_TAG, "Address Code SUCCESS")

            Log.d(REST_TAG, "Request URL를 불러옵니다...............")
            var requestUrl = openApi.getUrl(addressCode)
            Log.d(REST_TAG, "Request URL SUCCESS")

            Log.d(REST_TAG, "Weather를 불러옵니다...............")
            var todayWeather : WeatherModel = openApi.getTodayWeather(requestUrl)
            Log.d(REST_TAG, "Weather SUCCESS")

            Log.d(REST_TAG, "받은 WEATHER"+todayWeather)
            setWeather(todayWeather.weather)
            setPercent(todayWeather.percent)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startLoading(){
        Log.d(SHARED_TEAG, "Alarm Timer 를 불러옵니다...............")
        getAlarm()
        Log.d(SHARED_TEAG, "Alarm Timer init Success")

        Log.d(TAG,"DB 초기화 작업 ......................")
        openDatabase()
        Log.d(TAG,"DB 초기화 작업 SUCCESS")
        createTable()
        Log.d(TAG,"DATA를 불러옵니다.........................")
        selectDataInPresenter()
        Log.d(TAG,"DATA LOAD SUCCESS .........................")

        Log.d(TAG,"LOCATION을 불러옵니다.........................")
        var location =  selectCity()
        if(location.equals("")){
            insertCity("서울")
            setLocation("서울")
        }else setLocation(location)
        Log.d(TAG,"LOCATION LOAD SUCCESS .........................")

        Log.d(TAG,"REST GET STARTED ......................")
        getRest()
        Log.d(TAG,"REST GET SUCCESS")

        var handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "test Notification Channel"
            val descriptionText = "test Notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("notify", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}