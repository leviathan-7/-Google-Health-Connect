package com.example.googlehealthconnect.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.health.connect.client.records.StepsRecord
import com.example.googlehealthconnect.data.HealthRepo
import com.example.googlehealthconnect.R
import com.example.googlehealthconnect.data.DatePick
import com.example.googlehealthconnect.data.StepRecord
import com.example.googlehealthconnect.navigation.TopAppBar
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    repo: HealthRepo,
    navigateToSettings: () -> Unit
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopAppBar(
            title = "Шагомер"
        )
        Button(onClick = {
            navigateToSettings()
        },
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffff9115)
            )
        ){
            Text("Добавить данные", fontSize = 19.sp)
        }

        val coroutineScope = rememberCoroutineScope()
        var lst = listOf<StepsRecord>()
        var stepsRecords = remember {mutableStateOf(lst)}
        var steps = remember {mutableStateOf(0L)}
        var start = remember {mutableStateOf(0L)}
        var final = remember {mutableStateOf(4000000000L)}
        coroutineScope.launch {
            var res = repo.readStepsByTimeRange(
                Instant.ofEpochSecond(start.value),
                Instant.ofEpochSecond(final.value)
            )
            if (res != null){
                stepsRecords.value = res
            }
        }
        Text("Шагов за период = " + steps.value, fontSize = 23.sp)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text("От ", fontSize = 23.sp)
            DatePick(time = start)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text("До ", fontSize = 23.sp)
            DatePick(time = final)
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ){
            for(record in stepsRecords.value){
                steps.value+= record.count
                item{
                    StepRecord(
                        record = record,
                        modifier = modifier,
                        repo = repo
                    )
                }
            }
        }

    }
}

