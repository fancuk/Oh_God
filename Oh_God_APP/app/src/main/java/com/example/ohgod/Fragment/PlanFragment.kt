package com.example.ohgod.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.ohgod.Activity.AddPlanActivity
import com.example.ohgod.R
import kotlinx.android.synthetic.main.fragment_plan.*

class PlanFragment : Fragment(){
    var toggleList: TextView? = null
    var toggleCal: TextView? = null
    private lateinit var nowFrag : Fragment
    private var listFrag = PlanListFragment()
    private var calFrag = PlanCalendarFragment()
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_plan, container, false) as ViewGroup
        var addPlanBtn = rootView.findViewById<Button>(R.id.addPlanButton)
        addPlanBtn.setOnClickListener{
            val intent = Intent(context, AddPlanActivity::class.java)
            startActivity(intent)
        }
        this.toggleList = rootView.findViewById<TextView>(R.id.toggle_list)

        this.toggleList!!.setOnClickListener{
            toggle_cal.setTextColor(ContextCompat.getColor(context!!,R.color.colorTextGray))
            toggle_list.setTextColor(ContextCompat.getColor(context!!,R.color.colorAccent))
            var transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.container,listFrag)
            transaction.commit()
        }

        this.toggleCal = rootView.findViewById<TextView>(R.id.toggle_cal)
        this.toggleCal!!.setOnClickListener{
            //TextView.setTextColor(ContextCompat.getColor(context!!, R.color.empoMain))
            toggle_cal.setTextColor(ContextCompat.getColor(context!!,R.color.colorAccent))
            toggle_list.setTextColor(ContextCompat.getColor(context!!,R.color.colorTextGray))
            var transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.container,calFrag)
            transaction.commit()
        }

        var transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.container,listFrag)
        transaction.commit()

        return rootView
    }
}