package com.example.ohgod.Presenter

import com.example.ohgod.ConstantInterface
import com.example.ohgod.DataBase.SQLiteController

object LocationPresenter : ConstantInterface.Presenter_LocationPresenter{
    private var observer : ConstantInterface.View_HomeFragment? = null
    private var location : String? = null
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