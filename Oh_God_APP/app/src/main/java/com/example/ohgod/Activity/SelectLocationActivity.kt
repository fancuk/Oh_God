package com.example.ohgod.Activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ohgod.Network.RestCoroutine.getWeather
import com.example.ohgod.Presenter.LocationPresenter.setLocation
import com.example.ohgod.R

class SelectLocationActivity : AppCompatActivity(){
    lateinit var seoul : Button
    lateinit var incheon : Button
    lateinit var gyeonggi : Button
    lateinit var busan : Button
    lateinit var ulsan : Button
    lateinit var gyeongnam : Button
    lateinit var deagu : Button
    lateinit var gyeongbuk : Button
    lateinit var gwangju : Button
    lateinit var junnam : Button
    lateinit var junbuk : Button
    lateinit var deajeon : Button
    lateinit var sejong : Button
    lateinit var chungnam : Button
    lateinit var chungbuk : Button
    lateinit var gangwon : Button
    lateinit var jeju : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location_first)
       this.seoul = findViewById<Button>(R.id.seoulButton)
       this.incheon = findViewById<Button>(R.id.IncheonButton)
       this.gyeonggi = findViewById<Button>(R.id.GyeonggiButton)
       this.busan   = findViewById<Button>(R.id.busanButton)
       this.ulsan   = findViewById<Button>(R.id.ulsanButton)
       this.gyeongnam = findViewById<Button>(R.id.GyeongnamButton)
       this.deagu   = findViewById<Button>(R.id.DeaguButton)
       this.gyeongbuk = findViewById<Button>(R.id.GyeongBukButton)
       this.gwangju  = findViewById<Button>(R.id.GwangjuButton)
       this.junnam  = findViewById<Button>(R.id.JeonNamButton)
       this.junbuk  = findViewById<Button>(R.id.JeonBukButton)
       this.deajeon  = findViewById<Button>(R.id.DeajonButton)
       this.sejong  = findViewById<Button>(R.id.SejongButton)
       this.chungnam = findViewById<Button>(R.id.ChungNamButton)
       this.chungbuk = findViewById<Button>(R.id.ChungBukButton)
       this.gangwon  = findViewById<Button>(R.id.GangWonButton)
       this.jeju  = findViewById<Button>(R.id.JejuButton)

        this.seoul      .setOnClickListener         {setLocation(this.seoul      .text.toString()); getWeather(); finish()}
        this.incheon    .setOnClickListener         {setLocation(this.incheon    .text.toString()); getWeather(); finish()}
        this.gyeonggi   .setOnClickListener         {setLocation(this.gyeonggi   .text.toString()); getWeather(); finish()}
        this.busan      .setOnClickListener         {setLocation(this.busan      .text.toString()); getWeather(); finish()}
        this.ulsan      .setOnClickListener         {setLocation(this.ulsan      .text.toString()); getWeather(); finish()}
        this.gyeongnam  .setOnClickListener         {setLocation(this.gyeongnam  .text.toString()); getWeather(); finish()}
        this.deagu      .setOnClickListener         {setLocation(this.deagu      .text.toString()); getWeather(); finish()}
        this.gyeongbuk  .setOnClickListener         {setLocation(this.gyeongbuk  .text.toString()); getWeather(); finish()}
        this.gwangju    .setOnClickListener         {setLocation(this.gwangju    .text.toString()); getWeather(); finish()}
        this.junnam     .setOnClickListener         {setLocation(this.junnam     .text.toString()); getWeather(); finish()}
        this.junbuk     .setOnClickListener         {setLocation(this.junbuk     .text.toString()); getWeather(); finish()}
        this.deajeon    .setOnClickListener         {setLocation(this.deajeon    .text.toString()); getWeather(); finish()}
        this.sejong     .setOnClickListener         {setLocation(this.sejong     .text.toString()); getWeather(); finish()}
        this.chungnam   .setOnClickListener         {setLocation(this.chungnam   .text.toString()); getWeather(); finish()}
        this.chungbuk   .setOnClickListener         {setLocation(this.chungbuk   .text.toString()); getWeather(); finish()}
    }
}