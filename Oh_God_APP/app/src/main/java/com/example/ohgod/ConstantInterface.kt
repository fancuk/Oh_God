package com.example.ohgod

import com.example.ohgod.Model.PlanModel

interface ConstantInterface {
    interface View_PlanListFragment{
        fun notifyChanged()
    }
    interface View_HomeFragment{
        fun notifyLocationChanged()
        fun notifyWeatherChanged()
        fun notifyPercentChanged()
    }

    interface Presenter_PlanListFragment{
        fun attachPlanListObserver(view : View_PlanListFragment)
        fun dettachPlanListObserver()
        fun notifyPlanListObservers()
    }
    interface Presenter_LocationPresenter{
        fun attachLocationObserver(view : View_HomeFragment)
        fun dettachLocationObserver()
        fun notifyLocationObservers()
    }
    interface Presenter_WeatherPresenter{
        fun attachWeatherObserver(view : View_HomeFragment)
        fun dettachWeatherObserver()
        fun notifyWeatherObservers()
        fun getWeather() : String
        fun getPercent() : String
    }
}