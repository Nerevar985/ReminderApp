package com.kanush_productions.reminderapp.ui


import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanush_productions.reminderapp.model.AlarmItem


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyRemindersScreen(
    onScreenChange: () -> Unit,
    alarmItems: List<AlarmItem>,
    onDeleteClick: (AlarmItem)-> Unit,
) {
    BackHandler {
        onScreenChange()
    }
    Scaffold {
        if (alarmItems.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Empty",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
        LazyColumn(
            modifier = Modifier.padding(top = 4.dp)
        ){
            items(alarmItems){item ->
                ListItem(
                    item = item,
                    onDeleteClick = {onDeleteClick(it)},
                )

            }

        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            FloatingActionButton(onClick = {onScreenChange()},
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    }

    }


@Composable
fun ListItem(
    item: AlarmItem,
    onDeleteClick: (AlarmItem)-> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 16.dp, bottom = 16.dp, end = 16.dp)
        ){
            Text(text = "${item.hour}:${item.minute}",
                fontSize = 44.sp,
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                onDeleteClick(item)

            }) {
                Icon(imageVector = Icons.Filled.Delete,
                    "",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }

}