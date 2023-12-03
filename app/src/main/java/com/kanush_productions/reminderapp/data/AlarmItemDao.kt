package com.kanush_productions.reminderapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kanush_productions.reminderapp.model.AlarmItem
import kotlinx.coroutines.flow.Flow


@Dao
interface AlarmItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AlarmItem)
    @Delete
    suspend fun delete(item: AlarmItem)
    @Query("SELECT * from `alarm items`")
    fun getAllItems(): Flow<List<AlarmItem>>
}