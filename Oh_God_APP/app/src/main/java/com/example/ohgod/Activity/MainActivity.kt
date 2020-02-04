package com.example.ohgod.Activity

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ohgod.Fragment.HomeFragment
import com.example.ohgod.Fragment.PlanFragment
import com.example.ohgod.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(){
    private lateinit var homeFrag : Fragment
    private lateinit var planFrag : Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.homeFrag = HomeFragment()
        this.planFrag = PlanFragment()

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
                           // supportFragmentManager.beginTransaction()
                             //   .replace(R.id.container,settingFragment)
                             //   .commit()
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