package com.moonbanggoo.ohgod.Presenter

import com.moonbanggoo.ohgod.ConstantInterface
import com.moonbanggoo.ohgod.DataBase.SQLiteController

object LocationPresenter : ConstantInterface.Presenter_LocationPresenter{
    private var observer : ConstantInterface.View_HomeFragment? = null
    private var location : String? = "서울"
    override fun attachLocationObserver(view: ConstantInterface.View_HomeFragment) {
        this.observer = view
    }

    override fun dettachLocationObserver() {
        this.observer = null
    }

    override fun notifyLocationObservers() {
        if(this.observer != null){
            this.observer!!.notifyLocationChanged()
        }
    }

    fun setLocation(location : String){
        this.location = location
        SQLiteController.insertCity(location)
        notifyLocationObservers()
    }

    fun getLocation() : String{
        return this.location!!
    }
}