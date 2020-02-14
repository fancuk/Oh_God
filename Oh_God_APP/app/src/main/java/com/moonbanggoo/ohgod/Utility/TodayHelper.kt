package com.moonbanggoo.ohgod.Utility

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*

object TodayHelper {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodayString() : String{
        var now = LocalDate.now()
        return now.toString()
    }
}