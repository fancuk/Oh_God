package com.moonbanggoo.ohgod.Presenter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import com.moonbanggoo.ohgod.App
import com.moonbanggoo.ohgod.ConstantInterface
import com.moonbanggoo.ohgod.Model.AlarmTimeIntegerModel
import com.moonbanggoo.ohgod.Model.AlarmTimeModel
import com.moonbanggoo.ohgod.Receiver.AlarmReceiver
import java.util.*

object UseAlarmPresenter {
    private lateinit var nowAmPm : String
    private lateinit var nowHour : String
    private lateinit var nowMinute : String
    private var observer : ConstantInterface.View_HomeFragment? = null
    lateinit var timeModel : AlarmTimeModel
    lateinit var pref : SharedPreferences

    fun attachTimeObserver(view : ConstantInterface.View_HomeFragment){
        this.observer = view
    }

    fun dettachTimeObserver(){
        this.observer = null
    }

    fun notifyTimeObserver(){
        if(this.observer != null)this.observer!!.notifyTimeChanged()
    }

    fun translateStringHourToIntHourModel() : AlarmTimeIntegerModel{
        var resHour : Int
        if(this.nowAmPm.equals("오후")){
            resHour = this.nowHour.toInt()+12
        }else resHour = this.nowHour.toInt()
        return AlarmTimeIntegerModel(resHour,this.nowMinute.toInt())
    }

    fun getNowTimeModel():AlarmTimeModel = AlarmTimeModel(
            nowAmPm,
            nowHour,
            nowMinute
        )


    fun setNewTime(hour : Int, minute : Int){
        var am_pm : String
        var nowHour = hour
        var nowMinute = minute
        if(nowHour >= 12) { am_pm = "오후"
            if(nowHour != 12)nowHour -= 12
        }else am_pm = "오전"
        this.nowHour = nowHour.toString()
        this.nowMinute = nowMinute.toString()
        this.nowAmPm = am_pm
        savePref()
        makeAlarm()
    }

    fun savePref(){
        var nowEditor = this.pref.edit()
        nowEditor.putString("am_pm",this.nowAmPm)
        nowEditor.putString("hour",this.nowHour)
        nowEditor.putString("minute",this.nowMinute)
        nowEditor.apply()
        nowEditor.commit()
        notifyTimeObserver()
    }

    fun initThis(editor : SharedPreferences) {
        this.pref = editor
        var nowAmPm = pref.getString("am_pm","")
        if(nowAmPm.equals(""))nowAmPm = "오전"
        var nowHour = pref.getString("hour","")
        if(nowHour.equals(""))nowHour ="9"
        var nowMinute = pref.getString("minute","")
        if(nowMinute.equals(""))nowMinute = "0"
        this.nowAmPm = nowAmPm!!
        this.nowHour = nowHour!!
        this.nowMinute = nowMinute!!
    }
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    fun makeAlarm(){
        alarmMgr = App.applicationContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(App.applicationContext(), AlarmReceiver::class.java).let {
                intent ->
            PendingIntent.getBroadcast(App.applicationContext(), 0, intent, 0)
        }
        alarmMgr?.cancel(alarmIntent)
        var testHour = translateStringHourToIntHourModel().hour

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, translateStringHourToIntHourModel().hour)
            set(Calendar.MINUTE, nowMinute.toInt())
            set(Calendar.SECOND,0)
        }
        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE,1)
        }
        var date = calendar.time

        for(i in 1 ..10)Log.d("Alarm Set", "$date 에 알람설정되었음 $testHour 시 $nowMinute 분 알람 설정됨")
        alarmMgr?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmMgr?.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                alarmIntent);
        }

    }
}