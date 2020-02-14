package com.moonbanggoo.ohgod

interface ConstantInterface {
    interface View_AddLocationActivity{
        fun goBack()
    }
    interface View_PlanListFragment{
        fun notifyChanged()
    }
    interface View_HomeFragment{
        fun notifyLocationChanged()
        fun notifyWeatherChanged()
        fun notifyPercentChanged()
        fun notifyTimeChanged()
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