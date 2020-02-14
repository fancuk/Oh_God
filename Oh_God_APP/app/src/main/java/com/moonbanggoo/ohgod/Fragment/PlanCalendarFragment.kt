package com.moonbanggoo.ohgod.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.moonbanggoo.ohgod.R
import java.util.*

class PlanCalendarFragment : Fragment(){
    private lateinit var rootView : ViewGroup
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.rootView = inflater.inflate(R.layout.fragment_plan_calendar, container, false) as ViewGroup
        var viewCal = this.rootView.findViewById<CalendarView>(R.id.ViewCalendar)
        return this.rootView
    }
}