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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.googlehealthconnect.data.HealthRepo
import com.example.googlehealthconnect.R
import com.example.googlehealthconnect.data.DatePick
import com.example.googlehealthconnect.data.StepRecord
import com.example.googlehealthconnect.navigation.TopAppBar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Date

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
        var key = remember {mutableStateOf(0)}
        var lst = remember {mutableStateOf(listOf<StepsRecord>())}
        var steps = remember {mutableStateOf(0L)}
        var start = remember {mutableStateOf(0L)}
        val f = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
        var final = remember {mutableStateOf(f)}

        LaunchedEffect(start.value, final.value, key.value) {
            steps.value = repo.aggregateSteps(start.value, final.value)
            lst.value = repo.readStepsByTimeRange(start.value, final.value)
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
            for(record in lst.value){
                item{
                    StepRecord(
                        record = record,
                        modifier = modifier,
                        repo = repo,
                        key = key
                    )
                }
            }
        }

    }
}

