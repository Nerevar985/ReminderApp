package com.kanush_productions.reminderapp

import android.Manifest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kanush_productions.reminderapp.data.AlarmDatabase



import com.kanush_productions.reminderapp.data.OfflineAlarmItemRepository
import com.kanush_productions.reminderapp.model.AlarmItem


class AlarmBroadcastReceiver() : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val item = intent.getSerializableExtra("item") as AlarmItem

        Log.d("MyLog", "${item.message} 123")
        val builder = NotificationCompat.Builder(context,"channel1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(item.message)
            .setContentText(item.message)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)


        val notificationManager = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(123,builder.build())
    }
}