package com.moonbanggoo.ohgod.Activity

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.moonbanggoo.ohgod.Fragment.HomeFragment
import com.moonbanggoo.ohgod.Fragment.PlanFragment
import com.moonbanggoo.ohgod.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moonbanggoo.ohgod.Fragment.SettingFragment

class MainActivity : AppCompatActivity(){
    private lateinit var homeFrag : Fragment
    private lateinit var planFrag : Fragment
    private lateinit var settingFrag : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.homeFrag = HomeFragment()
        this.planFrag = PlanFragment()
        this.settingFrag = SettingFragment()

        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.message)
        bottomNavigationView.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                    // 어떤 메뉴 아이템이 터치되었는지 확인합니다.
                    when (item.itemId) {
                        R.id.home -> {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container,homeFrag)
                                .commit()
                            return true
                        }
                        R.id.menu -> {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container,planFrag)
                                .commit()
                            return true
                        }
                        R.id.setting -> {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container,settingFrag)
                                .commit()
                            return true
                        }
                    }
                    return false
                }
            })
        supportFragmentManager.beginTransaction()
            .replace(R.id.container,homeFrag)
            .commit()
    }
}