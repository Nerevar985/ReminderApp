package com.kanush_productions.reminderapp

import android.app.Application
import android.content.Context
import com.kanush_productions.reminderapp.data.AppContainer
import com.kanush_productions.reminderapp.data.AppDataContainer

class ReminderApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
    init {
        instance = this
    }
    companion object {
        private var instance: ReminderApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}