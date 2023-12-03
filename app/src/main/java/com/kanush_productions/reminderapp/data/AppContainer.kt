package com.kanush_productions.reminderapp.data

import android.content.Context

interface AppContainer {
    val alarmItemsRepository: AlarmItemsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val alarmItemsRepository: AlarmItemsRepository by lazy {
        OfflineAlarmItemRepository(AlarmDatabase.getDatabase(context).alarmItemDao())
    }
}