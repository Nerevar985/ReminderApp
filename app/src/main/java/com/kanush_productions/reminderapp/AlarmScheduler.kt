package com.kanush_productions.reminderapp

import com.kanush_productions.reminderapp.model.AlarmItem

interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}