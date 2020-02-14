package com.moonbanggoo.ohgod.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootCompletedReceiver : BroadcastReceiver(){
    private val TAG: String = "BootCompletedReceiver"
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val action = intent.action
        Log.e(TAG, "Receive ACTION $action")
        if (action == null) {
            Log.e(TAG, "action is null")
            return
        }
    }
}