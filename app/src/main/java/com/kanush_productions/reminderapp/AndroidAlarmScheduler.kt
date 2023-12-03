package com.kanush_productions.reminderapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.kanush_productions.reminderapp.model.AlarmItem
import com.kanush_productions.reminderapp.ui.MainViewModel
import java.util.Calendar

class AndroidAlarmScheduler(
    private val context: Context,
): AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    private val calendar = Calendar.getInstance()

    override fun schedule(item: AlarmItem) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra("item", item)
        }
        val minute = item.minute.toInt()
        calendar.set(Calendar.HOUR_OF_DAY, item.hour)
        calendar.set(Calendar.MINUTE, minute)
        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
        Log.d("MyLog", "${calendar.timeInMillis}")
    }

    override fun cancel(item: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, AlarmBroadcastReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
        )

    }
}