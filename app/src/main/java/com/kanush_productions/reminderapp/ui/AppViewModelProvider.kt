package com.kanush_productions.reminderapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kanush_productions.reminderapp.ReminderApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MainViewModel(reminderApplication().container.alarmItemsRepository)
        }
    }
}

fun CreationExtras.reminderApplication(): ReminderApplication {
    return (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ReminderApplication)
}