package com.kanush_productions.reminderapp.ui




import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope


import com.kanush_productions.reminderapp.data.AlarmItemsRepository
import com.kanush_productions.reminderapp.model.AlarmItem

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainViewModel(
    private val alarmItemsRepository: AlarmItemsRepository
): ViewModel() {
    var alarmItemsUiState: StateFlow<MainScreenUiState> = alarmItemsRepository.getAllItemsStream().map { MainScreenUiState(alarmItems = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = MainScreenUiState()
        )

    private val _mainScreenUiState = MutableStateFlow(MainScreenUiState())
    var mainScreenUiState: StateFlow<MainScreenUiState> =_mainScreenUiState

    fun setSelectedTimeText(time: LocalTime){
        val formattedTime = DateTimeFormatter
            .ofPattern("HH:mm")
            .format(time)
        _mainScreenUiState.update {
            it.copy(
                chosenTime = formattedTime
            )
        }
    }
    fun setReminderText(text: String){
        _mainScreenUiState.update {
            it.copy(
                reminderText = text
            )
        }
    }
    fun setCurrentScreen(screen: SCREEN){
        _mainScreenUiState.update {
            it.copy(
                currentScreen = screen
            )
        }
    }

     suspend fun removeFromList(item: AlarmItem){
        alarmItemsRepository.deleteItem(item)

    }

    suspend fun addToList(item: AlarmItem){
        val currentItems = alarmItemsUiState.value.alarmItems
        currentItems.forEach {
            if ((it.hour == item.hour) && (it.minute == item.minute)) {
                removeFromList(it)
            }
        }
        alarmItemsRepository.insertItem(item)
    }

    companion object{
        private const val TIMEOUT_MILLIS = 5000L
    }
}

data class MainScreenUiState(
    val reminderText: String = "",
    val chosenTime: String = DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.now()),
    val alarmItems: List<AlarmItem> = listOf(),
    val currentScreen: SCREEN = SCREEN.REMINDERS_SCREEN
)
