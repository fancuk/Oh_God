package com.moonbanggoo.ohgod.Presenter

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Icon
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.moonbanggoo.ohgod.Activity.MainActivity
import com.moonbanggoo.ohgod.App
import com.moonbanggoo.ohgod.R
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("StaticFieldLeak")
object NotificationMananger {
    var context : Context = App.applicationContext()
    var soundPool: SoundPool? = null
    var my_sound: Int = 0
    var loaded = false

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("ResourceAsColor")
    fun showNoti(title : String, msg : String){
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingI = PendingIntent.getActivity(
            context, 0,
            notificationIntent, 0
        )
        val builder = NotificationCompat.Builder(context, "default")

        val userIcon1 = Icon.createWithResource(context, R.drawable.back_arrow)  // 1

        val userName1 = "아맞다"    // 2


        //OREO API 26 이상에서는 채널 필요
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder.setSmallIcon(R.drawable.right_noti_logo) //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남
            builder.color = Color.RED
            val channelName = "매일 알람 채널"
            val description = "매일 정해진 시간에 알람합니다."
            val importance = NotificationManager.IMPORTANCE_HIGH //소리와 알림메시지를 같이 보여줌
            val channel = NotificationChannel("default", channelName, importance)
            channel.description = description
            notificationManager.createNotificationChannel(channel)
        } else {
            builder.setSmallIcon(R.drawable.right_noti_logo) // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남
            builder.color = Color.RED
        }
        var color : Int = R.color.colorAccent

        builder.setAutoCancel(true)
            .setSmallIcon(R.drawable.right_noti_logo)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources,R.drawable.app_logo))
            .setColor(Color.RED)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setTicker("{Time to watch some cool stuff!}")
            .setContentTitle("아! 맞다")
            .setContentText("알람시간이 변경되었어요")
            .setContentInfo("INFO")
            .setContentIntent(pendingI)
            .setPriority(Notification.PRIORITY_MAX)
            .setDefaults(
                Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE
            )
        if (notificationManager != null) {
            // 노티피케이션 동작시킴
            notificationManager.notify(1234, builder.build())
            val nextNotifyTime = Calendar.getInstance()
            // 내일 같은 시간으로 알람시간 결정
            nextNotifyTime.add(Calendar.DATE, 1)
            //  Preference에 설정한 값 저장
            val editor = context.getSharedPreferences("daily alarm", Context.MODE_PRIVATE).edit()
            editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis())
            editor.apply()
            val currentDateTime = nextNotifyTime.getTime()
            val date_text =
                SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(
                    currentDateTime
                )
            Toast.makeText(
                context.applicationContext,
                "다음 알람은 " + date_text + "으로 알람이 설정되었습니다!",
                Toast.LENGTH_SHORT
            ).show()
        }
        soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        my_sound = soundPool!!.load(context, R.raw.got_it, 1)

        soundPool!!.setOnLoadCompleteListener(object : SoundPool.OnLoadCompleteListener {
            override fun onLoadComplete(soundPool: SoundPool, sampleId: Int, status: Int) {
                loaded = true
                soundPool.play(my_sound,  0.9f, 0.9f, 1, 0, 1f);
            }
        })
    }

}