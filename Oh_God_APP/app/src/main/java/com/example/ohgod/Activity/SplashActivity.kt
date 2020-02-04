package com.example.ohgod.Activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.ohgod.DataBase.SQLiteController.createTable
import com.example.ohgod.DataBase.SQLiteController.insertCity
import com.example.ohgod.DataBase.SQLiteController.openDatabase
import com.example.ohgod.DataBase.SQLiteController.selectCity
import com.example.ohgod.DataBase.SQLiteController.selectScheduleData
import com.example.ohgod.Model.WeatherModel
import com.example.ohgod.Network.Address
import com.example.ohgod.Network.OpenApi
import com.example.ohgod.Presenter.LocationPresenter.getLocation
import com.example.ohgod.Presenter.LocationPresenter.setLocation
import com.example.ohgod.Presenter.PlanListFragmentPresenter.addPlan
import com.example.ohgod.Presenter.PlanListFragmentPresenter.selectDataInPresenter
import com.example.ohgod.Presenter.WeatherPresenter.setPercent
import com.example.ohgod.Presenter.WeatherPresenter.setWeather
import com.example.ohgod.R
import kotlinx.coroutines.*
import java.lang.Runnable
import java.util.EnumSet.range
import kotlin.coroutines.CoroutineContext

class SplashActivity : AppCompatActivity(){
    var TAG = "LOADING ACTIVITY"
    var REST_TAG = "REST_API"
    lateinit var openApi: OpenApi
    lateinit var address: Address
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        //myJob = Job()
        this.openApi = OpenApi()
        this.address = Address()
        startLoading()
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

    fun startLoading(){

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
}