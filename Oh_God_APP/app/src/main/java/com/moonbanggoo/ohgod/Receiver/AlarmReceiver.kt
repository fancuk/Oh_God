package com.moonbanggoo.ohgod.Receiver

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.*
import android.content.Intent
import android.util.Log
import com.moonbanggoo.ohgod.Activity.MainActivity
import android.os.Build
import com.moonbanggoo.ohgod.R
import com.moonbanggoo.ohgod.Service.AlarmRingService
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.PowerManager
import android.os.Vibrator
import androidx.annotation.RequiresApi
import com.moonbanggoo.ohgod.Activity.AlarmTestActivity
import com.moonbanggoo.ohgod.Activity.SplashActivity
import com.moonbanggoo.ohgod.DataBase.SQLiteController
import com.moonbanggoo.ohgod.Model.PlanModel
import com.moonbanggoo.ohgod.Model.WeatherModel
import com.moonbanggoo.ohgod.Network.Address
import com.moonbanggoo.ohgod.Network.OpenApi
import com.moonbanggoo.ohgod.Presenter.LocationPresenter
import com.moonbanggoo.ohgod.Presenter.NotificationMananger
import com.moonbanggoo.ohgod.Presenter.PlanListFragmentPresenter
import com.moonbanggoo.ohgod.Presenter.WeatherPresenter
import com.moonbanggoo.ohgod.Utility.Helper
import com.moonbanggoo.ohgod.Utility.TodayHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AlarmReceiver : BroadcastReceiver() {
    lateinit var context: Context
    var TAG = "LOADING ACTIVITY"
    var REST_TAG = "BACKGROUND_REST_API"
    var SHARED_TEAG = "SHARED_PREFERENCE_JOBS"
    var idx : Int = 0
    lateinit var openApi: OpenApi
    lateinit var address: Address
    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)



    fun getPlan(){
        // 플랜을 얻어와야 한다.
        Log.d("SQL_TEST", "오늘의 날짜는 : " + TodayHelper.getTodayString())

        var planListToday =   SQLiteController.selectTodayScheduleData(TodayHelper.getTodayString())
        Log.d("SQL_TEST", planListToday.size.toString())
//        var item = PlanModel("노트북 배터리 충전기 챙기기!", "2020-02-13")
////        planListToday.add(item)
////        planListToday.add(item)
////        planListToday.add(item)
        for(item in planListToday){
            showNotiAppInBackground(item.plan_name)
        }

        // 플랜을 삭제해야 한다.
    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getRest(){
        GlobalScope.launch {
            print(Thread.currentThread().name)
            openApi = OpenApi()
            address = Address()
            Log.d(TAG,"DB 초기화 작업 ......................")
            SQLiteController.openDatabase()
            Log.d(TAG,"DB 초기화 작업 SUCCESS")
            SQLiteController.createTable()
            Log.d(TAG,"DATA를 불러옵니다.........................")
            PlanListFragmentPresenter.selectDataInPresenter()
            Log.d(TAG,"DATA LOAD SUCCESS .........................")

            Log.d(TAG,"LOCATION을 불러옵니다.........................")
            var location = SQLiteController.selectCity()
            if(location.equals("")){
                SQLiteController.insertCity("서울")
                LocationPresenter.setLocation("서울")
            }else LocationPresenter.setLocation(location)
            Log.d(TAG,"LOCATION LOAD SUCCESS .........................")
            Log.d(REST_TAG, "Address Code를 불러옵니다...............")
            var addressCode = address.getAddressCode(LocationPresenter.getLocation())
            Log.d(REST_TAG, "Address Code SUCCESS")

            Log.d(REST_TAG, "Request URL를 불러옵니다...............")
            var requestUrl = openApi.getUrl(addressCode)
            Log.d(REST_TAG, "Request URL SUCCESS")

            Log.d(REST_TAG, "Weather를 불러옵니다...............")
            var todayWeather : WeatherModel = openApi.getTodayWeather(requestUrl)
            Log.d(REST_TAG, "Weather SUCCESS")

            Log.d(REST_TAG, "받은 WEATHER"+todayWeather)
            WeatherPresenter.setWeather(todayWeather.weather)
            WeatherPresenter.setPercent(todayWeather.percent)

            var nowItem = getNotiMessage()
            var nowMessage : String ="기본 메세지"
            var flag = true
            Log.d(REST_TAG,"받아온 날씨는 : " + nowItem.weather)
            when(nowItem.weather){
                "rain" -> nowMessage = "비가 와요! 우산은 챙기셨나요?"
                "snow" -> nowMessage = "눈이 와요! 우산은 챙기셨나요?"
                "snowrain" -> nowMessage = "오늘은 눈이오거나 비가 올거에요 우산은 챙기셨나요?"
                "0" ->  nowMessage = "오늘은 맑네요!"//flag =false
            }
            if(flag)showNotiAppInBackground(nowMessage)
            getPlan()
        }
    }


    fun getNotiMessage() : WeatherModel{
        var nowWeather = WeatherPresenter.getWeather()
        var nowPercent = WeatherPresenter.getPercent()
        var res = WeatherModel()
        res.setWeather(nowPercent,nowWeather)
        return res
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun showNotiAppInBackground(nowMessage : String){
        this.idx +=1
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(context, SplashActivity::class.java)
        //notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingI = PendingIntent.getActivity(
            context, 0,
            notificationIntent, 0
        )
        val builder = NotificationCompat.Builder(context, "default")
        //OREO API 26 이상에서는 채널 필요
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder.setSmallIcon(R.drawable.right_noti_logo) //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남
            builder.color = Color.RED
            val channelName = "매일 알람 채널"
            val description = "매일 정해진 시간에 알람합니다."
            val importance = NotificationManager.IMPORTANCE_HIGH //소리와 알림메시지를 같이 보여줌
            val channel = NotificationChannel("default", channelName, importance)
            channel.description = description
            notificationManager?.createNotificationChannel(channel)
        } else builder.setSmallIcon(R.mipmap.ic_launcher) // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남
        builder.setAutoCancel(true)
            .setSmallIcon(R.drawable.right_noti_logo)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources,R.drawable.app_logo))
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setTicker("{Time to watch some cool stuff!}")
            .setContentTitle("아! 맞다")
            .setContentText(nowMessage)
            .setContentInfo(nowMessage)
            .setContentIntent(pendingI)
            .setPriority(Notification.PRIORITY_MAX)
            .setSound(Uri.parse("android.resource://" + context.packageName + "/" +
            R.raw.got_it))
        if (notificationManager != null) {
            // 노티피케이션 동작시킴
            notificationManager.notify(idx, builder.build())
            val nextNotifyTime = Calendar.getInstance()
            // 내일 같은 시간으로 알람시간 결정
            nextNotifyTime.add(Calendar.DATE, 1)

            var alarmRingService : AlarmRingService = AlarmRingService()
            alarmRingService.playSound()

            var mp = MediaPlayer.create(NotificationMananger.context, R.raw.got_it)
            mp.start()
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            val vib = NotificationMananger.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vib.vibrate(1000)

            mp.setOnCompletionListener(MediaPlayer.OnCompletionListener { mp ->
                if (!mp.isPlaying) {
                    mp.release()
                } else {
                    mp.stop()
                    mp.release()
                }
            })

            //  Preference에 설정한 값 저장
            val editor = context.getSharedPreferences("daily alarm", MODE_PRIVATE).edit()
            editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis())
            editor.apply()
            val currentDateTime = nextNotifyTime.getTime()
            val date_text =
                SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(
                    currentDateTime
                )
        }
        Log.d("SQL_TEST", "오늘의 일정 불러오기.......")

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(
            "Alarm",
            "알람이 울립니다!!알람이 울립니다!!"
        )
        this.context = context!!

        if(WeatherPresenter.isNull()){
            getRest()
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                getPlan()
            }
        }
    }
}


