package com.moonbanggoo.ohgod.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.moonbanggoo.ohgod.R

class AboutUsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developers)
        var backBtn = findViewById<ImageButton>(R.id.backButtonAboutUs)
        backBtn.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}