package com.moonbanggoo.ohgod.Service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.content.Context
import android.util.Log
import android.media.SoundPool
import android.media.AudioManager
import android.media.MediaPlayer
import com.moonbanggoo.ohgod.Presenter.NotificationMananger.context
import com.moonbanggoo.ohgod.R
import android.os.Vibrator



class AlarmRingService : Service(){
    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object{
        var sp: SoundPool? = null
        var my_sound: Int = 0
        var loaded = false

    }


    fun loadSound(){
        if(loaded)return
        sp = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        my_sound = sp!!.load(applicationContext, R.raw.got_it, 1)
        sp!!.setOnLoadCompleteListener(object : SoundPool.OnLoadCompleteListener {
            override fun onLoadComplete(soundPool: SoundPool, sampleId: Int, status: Int) {
                loaded = true
            }
        })
    }

    fun loadAndPlaySound(){
        sp = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        my_sound = sp!!.load(applicationContext, R.raw.got_it, 1)
        sp!!.setOnLoadCompleteListener(object : SoundPool.OnLoadCompleteListener {
            override fun onLoadComplete(soundPool: SoundPool, sampleId: Int, status: Int) {
                loaded = true
                sp!!.play(my_sound, 0.9f, 0.9f, 1, 0, 1f);
            }
        })
    }

    fun playSound(){
        Log.d("SOUND_TAG_IN_BACKGROUND","소리를 재생합니다!")
        var mp = MediaPlayer.create(context, R.raw.got_it)
        mp.start()
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        val vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vib.vibrate(1000)

        mp.setOnCompletionListener(MediaPlayer.OnCompletionListener { mp ->
            if (!mp.isPlaying) {
                mp.release()
            } else {
                mp.stop()
                mp.release()
            }
        })
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestory() 실행", "서비스 파괴")
    }
}