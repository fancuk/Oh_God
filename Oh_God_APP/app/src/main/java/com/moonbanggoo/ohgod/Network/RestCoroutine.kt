package com.moonbanggoo.ohgod.Network

import android.util.Log
import com.moonbanggoo.ohgod.ConstantInterface
import com.moonbanggoo.ohgod.Model.WeatherModel
import com.moonbanggoo.ohgod.Presenter.LocationPresenter
import com.moonbanggoo.ohgod.Presenter.WeatherPresenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object RestCoroutine {
    var REST_TAG = "REST_API"
    lateinit var openApi: OpenApi
    lateinit var address: Address
    private  var observer : ConstantInterface.View_AddLocationActivity? = null

    fun attachObserver(view : ConstantInterface.View_AddLocationActivity){
       if(this.observer != null)this.observer = view
    }

    fun dettachObserver() {
        this.observer = null
    }

    fun getWeather(){
        GlobalScope.launch {
            openApi = OpenApi()
            address = Address()
            print(Thread.currentThread().name)

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
        }
    }
}