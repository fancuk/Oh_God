package com.example.ohgod.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ohgod.Activity.AddPlanActivity
import com.example.ohgod.Activity.MainActivity
import com.example.ohgod.Activity.SelectLocationActivity
import com.example.ohgod.ConstantInterface
import com.example.ohgod.Presenter.LocationPresenter.attachLocationObserver
import com.example.ohgod.Presenter.LocationPresenter.dettachLocationObserver
import com.example.ohgod.Presenter.LocationPresenter.getLocation
import com.example.ohgod.Presenter.WeatherPresenter.getPercent
import com.example.ohgod.Presenter.WeatherPresenter.getWeather
import com.example.ohgod.R

class HomeFragment : Fragment(), ConstantInterface.View_HomeFragment {
    override fun notifyWeatherChanged() {
        var nowWeather = getWeather()
        when(nowWeather){
            "0" -> nowWeather = "맑아요"
            "snow" -> nowWeather = "눈와요"
            "rain" -> nowWeather = "비와요"
            "snowrain" -> nowWeather = "비나 눈이와요"
        }
        this.rootView.findViewById<TextView>(R.id.ViewWeatherOnFrag).setText(nowWeather)
    }

    override fun notifyPercentChanged() {
        this.rootView.findViewById<TextView>(R.id.ViewPercentOnHomeFrag).setText(getPercent() + "%")
    }

    lateinit var rootView : ViewGroup
    override fun notifyLocationChanged() {
        this.rootView.findViewById<TextView>(R.id.ViewFirstLocationText).setText(getLocation())
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.rootView = inflater.inflate(R.layout.fragment_home, container, false) as ViewGroup

        attachLocationObserver(this)

        var locationEditButton = rootView.findViewById<Button>(R.id.ViewLocationEditButton)
        locationEditButton.setOnClickListener {
            val intent = Intent(context, SelectLocationActivity::class.java)
            startActivity(intent)
        }

        var timePicker = rootView.findViewById<TimePicker>(R.id.ViewHomeTimePicker)
        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            Toast.makeText(
                context,
                view.toString() + "." + hourOfDay.toString() + "." + minute.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
        notifyLocationChanged()
        notifyPercentChanged()
        notifyWeatherChanged()
        return rootView
    }

    override fun onDestroy() {
        dettachLocationObserver()
        super.onDestroy()
    }
}