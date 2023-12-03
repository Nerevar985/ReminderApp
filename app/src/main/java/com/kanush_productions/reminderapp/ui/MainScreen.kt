package com.kanush_productions.reminderapp.ui

import android.content.Context

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kanush_productions.reminderapp.AndroidAlarmScheduler
import com.kanush_productions.reminderapp.model.AlarmItem
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalTime


@Composable
fun ReminderApp(){
    val viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val alarmItemsUiState = viewModel.alarmItemsUiState.collectAsState().value
    val mainScreenUiState = viewModel.mainScreenUiState.collectAsState().value

    val context = LocalContext.current
    val scheduler = AndroidAlarmScheduler(context)
    val coroutineScope = rememberCoroutineScope()

    if (mainScreenUiState.currentScreen == SCREEN.CREATE_SCREEN) {
        MainScreen(onScreenChange = {
            viewModel.setCurrentScreen(SCREEN.REMINDERS_SCREEN)
        },
            uiState = mainScreenUiState,
            onAddItemClick = {
                coroutineScope.launch {
                    viewModel.addToList(it)
                }

            },
            scheduler = scheduler,
            context = context,
            onChooseTime = {
                viewModel.setSelectedTimeText(it)
            },
            onReminderTextChange = {
                viewModel.setReminderText(it)
            }
        )

    } else {
        MyRemindersScreen(
            onScreenChange = {
                viewModel.setCurrentScreen(SCREEN.CREATE_SCREEN)
            },
            alarmItems = alarmItemsUiState.alarmItems,
            onDeleteClick = {
                coroutineScope.launch {
                    viewModel.removeFromList(it)
                }
                scheduler.cancel(it)
            },

            )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onScreenChange: () -> Unit,
    uiState: MainScreenUiState,
    onAddItemClick: (AlarmItem)-> Unit,
    scheduler: AndroidAlarmScheduler,
    context: Context,
    onChooseTime: (LocalTime)-> Unit,
    onReminderTextChange: (String) -> Unit
) {
    var alarmItem: AlarmItem?
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val timeDialogState = rememberMaterialDialogState()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = "Выбрать время",
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = uiState.chosenTime,
                fontSize = 50.sp,
                modifier = Modifier.clickable {
                    timeDialogState.show()
                }
                    .weight(1f),)
            Spacer(modifier = Modifier.size(1.dp)
                .weight(0.9f))
        }



        TextField(value = uiState.reminderText, onValueChange = {
            onReminderTextChange(it)

        },
            label = {
                Text(text = "Напоминание")
            },
            modifier = Modifier.fillMaxWidth( ))
        Button(onClick = {

            val time = uiState.chosenTime.toCharArray()
            val hour = ("${time[0]}${time[1]}").toInt()
            val minute = ("${time[3]}${time[4]}")

            alarmItem = AlarmItem(
                hour = hour,
                minute = minute,
                message = uiState.reminderText
            )
            onAddItemClick(alarmItem!!)
            alarmItem?.let (scheduler::schedule)

            Toast.makeText(
                context,
                "Напоминание установлено",
                Toast.LENGTH_LONG
            ).show()

            //Log.d("MyLog", "button clicked, $hour, $minute")
        },
            modifier = Modifier.padding(36.dp)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(text = "Установить")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 36.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(onClick = { onScreenChange() }) {
                Text(text = "Список напоминаний")
            }
        }
        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(text = "Ok") {

                }
                negativeButton(text = "Отмена")
            }
        ) {
            timepicker(
                initialTime = LocalTime.now(),
                title = "Выберите время",
                is24HourClock = true
            ) {
                onChooseTime(it)
            }
        }
    }

}

enum class SCREEN {
    CREATE_SCREEN, REMINDERS_SCREEN
}