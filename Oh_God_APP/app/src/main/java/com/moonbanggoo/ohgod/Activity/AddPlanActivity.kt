package com.moonbanggoo.ohgod.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moonbanggoo.ohgod.R
import android.app.DatePickerDialog
import android.os.Build
import android.widget.*
import androidx.annotation.RequiresApi
import com.moonbanggoo.ohgod.Model.PlanModel
import com.moonbanggoo.ohgod.Presenter.PlanListFragmentPresenter.addPlan
import kotlinx.android.synthetic.main.activity_add_plan.*
import java.time.LocalDate
import java.util.*
class AddPlanActivity : AppCompatActivity(){
    lateinit var item : PlanModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plan)

        var backBtn = findViewById<ImageButton>(R.id.backButtonAddPlan)
        backBtn.setOnClickListener { onBackPressed() }

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
                    finish()
                }
            }
        }

        var selectDayBtn = findViewById<Button>(R.id.selectDayButton)
        selectDayBtn.setOnClickListener{
            val pickedDate = Calendar.getInstance()
            val minDate = Calendar.getInstance()
            val maxDate = Calendar.getInstance()
            var cal = Calendar.getInstance()
            pickedDate.set(2020, 2 - 1, 12)
            val datePickerDialog = DatePickerDialog(
                this,
                object : DatePickerDialog.OnDateSetListener {
                    @SuppressLint("SetTextI18n")
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
                        var parseDate = LocalDate.parse(resultDate)
                        val pickedDay = parseDate.dayOfWeek.value
//                        var stringDay : String = ""
//                        when(pickedDay){
//                            7 -> stringDay= "(일)요일"
//                            1 -> stringDay= "(월)요일"
//                            2 -> stringDay= "(화)요일"
//                            3 -> stringDay= "(수)요일"
//                            4 -> stringDay= "(목)요일"
//                            5 -> stringDay= "(금)요일"
//                            6 -> stringDay= "(토)요일"
//                        }
                        ViewNowDate.setText(resultDate)
                        item.plan_date = ViewNowDate.text.toString()
                    }
                },
                //2018-2-12
                pickedDate.get(Calendar.YEAR),
                pickedDate.get(Calendar.MONTH),
                pickedDate.get(Calendar.DATE)
            )
            minDate.set(LocalDate.now().year, LocalDate.now().monthValue-1, LocalDate.now().dayOfMonth)
            datePickerDialog.datePicker.minDate = minDate.getTime().getTime()
            maxDate.set(LocalDate.now().year, LocalDate.now().monthValue-1, LocalDate.now().dayOfMonth+7)
            datePickerDialog.datePicker.maxDate = maxDate.getTimeInMillis()
            datePickerDialog.show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}