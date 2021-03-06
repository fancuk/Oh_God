package com.example.ohgod.Presenter

import com.example.ohgod.ConstantInterface

object WeatherPresenter : ConstantInterface.Presenter_WeatherPresenter{
    private var weather : String? = null
    private var percent : String? = null
    private var observer : ConstantInterface.View_HomeFragment? = null
    override fun attachWeatherObserver(view: ConstantInterface.View_HomeFragment) {
        this.observer = view
    }

    override fun dettachWeatherObserver() {
        this.observer = null
    }

    override fun notifyWeatherObservers() {
        if(this.observer!= null){
            this.observer!!.notifyWeatherChanged()
        }
    }

    override fun getWeather(): String {
       return this.weather!!
    }

    override fun getPercent(): String {
        return this.percent!!
    }

    fun setWeather(weather : String){
        this.weather = weather
        notifyWeatherObservers()
    }
    fun setPercent(percent : String){
        this.percent = percent
        notifyWeatherObservers()
    }
}