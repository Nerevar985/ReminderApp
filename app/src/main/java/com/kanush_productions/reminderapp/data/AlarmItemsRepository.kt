package com.kanush_productions.reminderapp.data

import com.kanush_productions.reminderapp.model.AlarmItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


interface AlarmItemsRepository {
    fun getAllItemsStream(): Flow<List<AlarmItem>>
    suspend fun insertItem(item: AlarmItem)
    suspend fun deleteItem(item: AlarmItem)
}


class OfflineAlarmItemRepository(private val alarmItemDao: AlarmItemDao): AlarmItemsRepository {
    override fun getAllItemsStream(): Flow<List<AlarmItem>> {
        return alarmItemDao.getAllItems()
    }

    override suspend fun insertItem(item: AlarmItem) {
        alarmItemDao.insert(item)
    }

     override suspend fun deleteItem(item: AlarmItem) {
         alarmItemDao.delete(item)
    }

}