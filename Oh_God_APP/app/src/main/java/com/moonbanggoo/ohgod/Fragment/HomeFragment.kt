package com.moonbanggoo.ohgod.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moonbanggoo.ohgod.Activity.SelectLocationActivity
import com.moonbanggoo.ohgod.ConstantInterface
import com.moonbanggoo.ohgod.Presenter.LocationPresenter.attachLocationObserver
import com.moonbanggoo.ohgod.Presenter.LocationPresenter.dettachLocationObserver
import com.moonbanggoo.ohgod.Presenter.LocationPresenter.getLocation
import com.moonbanggoo.ohgod.Presenter.WeatherPresenter.getPercent
import com.moonbanggoo.ohgod.Presenter.WeatherPresenter.getWeather
import android.app.TimePickerDialog
import com.moonbanggoo.ohgod.Presenter.UseAlarmPresenter
import com.moonbanggoo.ohgod.Presenter.UseAlarmPresenter.attachTimeObserver
import android.os.Build
import android.widget.*
import androidx.annotation.RequiresApi
import com.moonbanggoo.ohgod.Presenter.NotificationMananger
import com.moonbanggoo.ohgod.Presenter.WeatherPresenter.dettachWeatherObserver
import com.moonbanggoo.ohgod.R
import android.app.Activity
import android.util.Log
import com.moonbanggoo.ohgod.Presenter.WeatherPresenter.attachWeatherObserver
import com.moonbanggoo.ohgod.Utility.TodayHelper


class HomeFragment : Fragment(), ConstantInterface.View_HomeFragment {
    private lateinit var am_pmText : TextView
    private lateinit var hourText : TextView
    private lateinit var minuteText : TextView
    private lateinit var rootView : ViewGroup
    var TAG = "ddd"

     @SuppressLint("ShowToast")
     override fun notifyWeatherChanged() {
        var nowWeather = getWeather()
         var nowPercent = getPercent()
         //ViewHomeFragTodayWeatherBackGround
         runOnUiThread {
             var day_bg = this.rootView.findViewById<LinearLayout>(R.id.ViewHomeFragTodayWeatherBackGround)

             when(nowWeather){
                 "0" -> {
                     nowWeather = "맑아요"
                     day_bg.setBackgroundResource(R.drawable.clear_day)
                 }
                 "snow" ->{
                     nowWeather = "눈이 와요"
                     day_bg.setBackgroundResource(R.drawable.snow_day3)
                 }
                 "rain" -> {
                     nowWeather = "비가 와요"
                     day_bg.setBackgroundResource(R.drawable.rainy_day)
                 }
                 "snowrain" -> {
                     nowWeather = "비나 눈이와요"
                     day_bg.setBackgroundResource(R.drawable.snow_rainy_day)
                 }
             }
             Log.i(TAG, "runOnUiThread")
             this.rootView.findViewById<TextView>(R.id.ViewWeatherOnFrag).setText(nowWeather)
             this.rootView.findViewById<TextView>(R.id.ViewPercentOnHomeFrag).setText(nowPercent+"%")
         }
    }
     fun Fragment?.runOnUiThread(action: () -> Unit) {
        this ?: return
        activity?.runOnUiThread(action)
    }
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("SetTextI18n")
    override fun notifyTimeChanged() {
        var item = UseAlarmPresenter.getNowTimeModel()
        this.am_pmText.text = item.am_pm
        this.hourText.text = item.hour + "시"
        this.minuteText.text = item.minute + "분"
    }

    override fun notifyPercentChanged() {
        this.rootView.findViewById<TextView>(R.id.ViewPercentOnHomeFrag).setText(getPercent() + "%")
    }

    override fun notifyLocationChanged() {
        this.rootView.findViewById<TextView>(R.id.ViewFirstLocationText).setText(getLocation())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.rootView = inflater.inflate(R.layout.fragment_home, container, false) as ViewGroup
        attachLocationObserver(this)
        attachTimeObserver(this)
        attachWeatherObserver(this)
        this.am_pmText = rootView.findViewById<TextView>(R.id.ViewAM_PM)
        this.hourText = rootView.findViewById<TextView>(R.id.ViewAlarmTimeOfHour)
        this.minuteText = rootView.findViewById<TextView>(R.id.ViewAlarmTimeOfMinute)
        var todayText = rootView.findViewById<TextView>(R.id.today)
        todayText.setText(TodayHelper.getTodayString())
        val locationEditButton = rootView.findViewById<TextView>(R.id.ViewFirstLocationText)
        locationEditButton.setOnClickListener {
            val intent = Intent(context, SelectLocationActivity::class.java)
            startActivity(intent)
        }

        var setTimeButton = rootView.findViewById<LinearLayout>(R.id.ViewSetTimeButton)
        setTimeButton.setOnClickListener{
            val item = UseAlarmPresenter.translateStringHourToIntHourModel()
            val hour = item.hour
            val minute = item.minute
            val tpd = TimePickerDialog(
                context,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                TimePickerDialog.OnTimeSetListener(
                    function = { view, h, m ->
                        UseAlarmPresenter.setNewTime(h,m)
                 })
                ,hour,minute,false)
            tpd.show()
        }
        notifyLocationChanged()
        notifyPercentChanged()
        notifyWeatherChanged()
        UseAlarmPresenter.notifyTimeObserver()
        return rootView
    }


    override fun onDestroy() {
        dettachLocationObserver()
        dettachWeatherObserver()
        UseAlarmPresenter.dettachTimeObserver()
        super.onDestroy()
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("LOG", "결과 받기 성공")
            }
        }
    }
}