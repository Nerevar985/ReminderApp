package com.kanush_productions.reminderapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Alarm items")
data class AlarmItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hour: Int,
    val minute: String,
    val message: String
) : Serializable

