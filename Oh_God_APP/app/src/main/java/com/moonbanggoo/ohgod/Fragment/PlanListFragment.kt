package com.moonbanggoo.ohgod.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.moonbanggoo.ohgod.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moonbanggoo.ohgod.ConstantInterface
import com.moonbanggoo.ohgod.Presenter.PlanListFragmentPresenter.attachPlanListObserver
import com.moonbanggoo.ohgod.Presenter.PlanListFragmentPresenter.dettachPlanListObserver
import com.moonbanggoo.ohgod.Presenter.PlanListFragmentPresenter.planList
import com.moonbanggoo.ohgod.ViewUtility.Adapter.PlanRecyclerAdapter

class PlanListFragment : Fragment(), ConstantInterface.View_PlanListFragment {
    private var TAG = "Home Fragment"
    private var llm = LinearLayoutManager(context)
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : PlanRecyclerAdapter
    private lateinit var rootView : ViewGroup
    override fun notifyChanged() {
        if(planList.size.equals(0)){
            var planLayout =  rootView.findViewById<LinearLayout>(R.id.ViewPlanListLayout)
            planLayout.setBackgroundResource(R.drawable.tung2)
        }else {
            var planLayout =  rootView.findViewById<LinearLayout>(R.id.ViewPlanListLayout)
            planLayout.setBackgroundResource(R.color.colorWhite)
        }
        adapter = PlanRecyclerAdapter(activity, planList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_plan_list, container, false) as ViewGroup
        attachPlanListObserver(this)
        recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        notifyChanged()
        return rootView
    }
    override fun onDestroy() {
        dettachPlanListObserver()
        super.onDestroy()
    }
}