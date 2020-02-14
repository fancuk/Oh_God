package com.moonbanggoo.ohgod.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.moonbanggoo.ohgod.Activity.AboutUsActivity
import com.moonbanggoo.ohgod.Activity.SelectLocationActivity
import com.moonbanggoo.ohgod.R

class SettingFragment  : Fragment(){
    private lateinit var rootView : ViewGroup
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.rootView = inflater.inflate(R.layout.fragment_setting, container, false) as ViewGroup

        var alarmSwitch = rootView.findViewById<Switch>(R.id.ViewAlarmSwitch)

        var developerInfoLinear = rootView.findViewById<LinearLayout>(R.id.ViewDeveloperInfoLinear)
        developerInfoLinear.setOnClickListener {
            val intent = Intent(context, AboutUsActivity::class.java)
            startActivity(intent)
        }

        var apiInfo = rootView.findViewById<LinearLayout>(R.id.ViewApiInfoLinear)
        apiInfo.setOnClickListener {
            Toast.makeText(context,"공공데이터포털 (https://www.data.go.kr/)\n기상청에서 제공한 동네예보\n통보문 조회서비스 이용",Toast.LENGTH_LONG).show()
        }
        return this.rootView
    }
}