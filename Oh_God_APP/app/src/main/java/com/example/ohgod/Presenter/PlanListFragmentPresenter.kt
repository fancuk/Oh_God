package com.example.ohgod.Presenter

import com.example.ohgod.ConstantInterface
import com.example.ohgod.DataBase.SQLiteController.insertData
import com.example.ohgod.DataBase.SQLiteController.selectScheduleData
import com.example.ohgod.Model.PlanModel

object PlanListFragmentPresenter : ConstantInterface.Presenter_PlanListFragment{
    var planList = ArrayList<PlanModel>()

    fun selectDataInPresenter(){
        this.planList.clear()
        addPlanAll(selectScheduleData())
    }

    fun addPlanAll(array : ArrayList<PlanModel>){
        for(item in array){
            planList.add(item)
        }
        notifyPlanListObservers()
    }

    fun addPlan(item : PlanModel){
        insertData(item)
        selectDataInPresenter()
        notifyPlanListObservers()
    }

    private var observer : ConstantInterface.View_PlanListFragment? = null

    fun getDbData(){
        var item = PlanModel("test plan","2020.02.04")
        addPlan(item)
        addPlan(item)
        addPlan(item)
        notifyPlanListObservers()
    }

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