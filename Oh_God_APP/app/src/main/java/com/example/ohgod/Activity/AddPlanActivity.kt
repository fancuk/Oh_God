package com.example.ohgod.Activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ohgod.R
import android.widget.Toast
import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.ohgod.Model.PlanModel
import com.example.ohgod.Presenter.PlanListFragmentPresenter.addPlan
import com.example.ohgod.Presenter.UseDbPresenter.insertNewPlan
import kotlinx.android.synthetic.main.activity_add_plan.*
import java.time.LocalDate
import java.util.*
class AddPlanActivity : AppCompatActivity(){
    lateinit var item : PlanModel
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plan)
        item = PlanModel("","")
        var saveButton = findViewById<Button>(R.id.ViewSaveButton)
        saveButton.setOnClickListener{
            var nowPlan = findViewById<TextView>(R.id.ViewPlanName)
            if(nowPlan.text.toString().equals("")){
                Toast.makeText(
                    applicationContext,
                    "ㅜ 무슨일인지 알려주셔야 해요 ㅜ",
                    Toast.LENGTH_LONG
                ).show()
            }else{
                this.item.plan_name = nowPlan.text.toString()
                if(this.item.plan_date.equals("")){
                    Toast.makeText(
                        applicationContext,
                        "ㅜ 시간을 정해주셔야 해요 ㅜ",
                        Toast.LENGTH_LONG
                    ).show()
                }else{
                    addPlan(item)
                    Toast.makeText(
                        applicationContext,
                        "저장되었어요!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        var selectDayBtn = findViewById<Button>(R.id.selectDayButton)
        selectDayBtn.setOnClickListener{
            val pickedDate = Calendar.getInstance()
            val minDate = Calendar.getInstance()
            val maxDate = Calendar.getInstance()
            var cal = Calendar.getInstance()
            //pickedDate.set(2020, 2 - 1, 12)
            val datePickerDialog = DatePickerDialog(
                this,
                object : DatePickerDialog.OnDateSetListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                        var selectYear = year.toString()
                        if(selectYear.length == 1){
                            selectYear = "0" + selectYear
                        }
                        var selectMonth = (month+1).toString()
                        if(selectMonth.length == 1){
                            selectMonth = "0" + selectMonth
                        }
                        var selectDay = dayOfMonth.toString()
                        if(selectDay.length == 1){
                            selectDay = "0" + selectDay
                        }
                        var resultDate = selectYear + "-" + selectMonth + "-" + selectDay
                        ViewNowDate.setText(resultDate)
                        var parseDate = LocalDate.parse(resultDate)
                        val pickedDay = parseDate.dayOfWeek.value
                        when(pickedDay){
                            7 -> ViewDayOfWeek.setText("(일)요일")
                            1 -> ViewDayOfWeek.setText("(월)요일")
                            2 -> ViewDayOfWeek.setText("(화)요일")
                            3 -> ViewDayOfWeek.setText("(수)요일")
                            4 -> ViewDayOfWeek.setText("(목)요일")
                            5 -> ViewDayOfWeek.setText("(금)요일")
                            6 -> ViewDayOfWeek.setText("(토)요일")
                        }
                        Toast.makeText(
                            applicationContext,
                            "select date : " + resultDate,
                            Toast.LENGTH_LONG
                        ).show()
                        item.plan_date = ViewNowDate.text.toString()
                    }
                },
                //2018-2-12
                pickedDate.get(Calendar.YEAR),
                pickedDate.get(Calendar.MONTH),
                pickedDate.get(Calendar.DATE)
            )
            minDate.set(2018, 2 - 1, 10)
            datePickerDialog.datePicker.minDate = minDate.getTime().getTime()
            maxDate.set(2018, 2 - 1, 17)
            datePickerDialog.datePicker.maxDate = maxDate.getTimeInMillis()
            datePickerDialog.show()
        }
    }
}