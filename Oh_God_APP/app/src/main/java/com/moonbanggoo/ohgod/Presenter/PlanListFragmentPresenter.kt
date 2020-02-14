package com.moonbanggoo.ohgod.Presenter

import android.os.Build
import androidx.annotation.RequiresApi
import com.moonbanggoo.ohgod.ConstantInterface
import com.moonbanggoo.ohgod.DataBase.SQLiteController
import com.moonbanggoo.ohgod.DataBase.SQLiteController.insertData
import com.moonbanggoo.ohgod.DataBase.SQLiteController.selectScheduleData
import com.moonbanggoo.ohgod.Model.PlanModel
import com.moonbanggoo.ohgod.Utility.TodayHelper

object PlanListFragmentPresenter : ConstantInterface.Presenter_PlanListFragment{
    var planList = ArrayList<PlanModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun deletePlan(item : PlanModel){
        SQLiteController.DeleteWantScheduleData(item)
        selectDataInPresenter()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun selectDataInPresenter(){
        this.planList.clear()
        addPlanAll(selectScheduleData(TodayHelper.getTodayString()))
    }

    fun addPlanAll(array : ArrayList<PlanModel>){
        for(item in array){
            planList.add(item)
        }
        notifyPlanListObservers()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addPlan(item : PlanModel){
        insertData(item)
        selectDataInPresenter()
        notifyPlanListObservers()
    }

    private var observer : ConstantInterface.View_PlanListFragment? = null


    override fun attachPlanListObserver(view: ConstantInterface.View_PlanListFragment) {
        this.observer = view
    }

    override fun dettachPlanListObserver() {
        this.observer = null
    }

    override fun notifyPlanListObservers() {
        if(this.observer != null){
            this.observer!!.notifyChanged()
        }
    }
}