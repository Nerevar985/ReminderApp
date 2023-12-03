package com.kanush_productions.reminderapp.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kanush_productions.reminderapp.model.AlarmItem

@Database(version = 1, entities = [AlarmItem::class], exportSchema = false)
abstract class AlarmDatabase: RoomDatabase() {

    abstract fun alarmItemDao(): AlarmItemDao

    companion object{
        @Volatile
        private var Instance: AlarmDatabase? = null

        fun getDatabase(context: Context): AlarmDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, AlarmDatabase::class.java, "alarm_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }

    }
}