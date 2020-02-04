package com.example.ohgod.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.ohgod.R
import android.widget.ToggleButton
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ohgod.ConstantInterface
import com.example.ohgod.Presenter.PlanListFragmentPresenter
import com.example.ohgod.Presenter.PlanListFragmentPresenter.attachPlanListObserver
import com.example.ohgod.Presenter.PlanListFragmentPresenter.dettachPlanListObserver
import com.example.ohgod.Presenter.PlanListFragmentPresenter.getDbData
import com.example.ohgod.Presenter.PlanListFragmentPresenter.planList
import com.example.ohgod.ViewUtility.Adapter.PlanRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_plan_list.*

class PlanListFragment : Fragment(), ConstantInterface.View_PlanListFragment {
    private var TAG = "Home Fragment"
    private var llm = LinearLayoutManager(context)
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : PlanRecyclerAdapter
    override fun notifyChanged() {
        adapter = PlanRecyclerAdapter(activity, planList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_plan_list, container, false) as ViewGroup
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