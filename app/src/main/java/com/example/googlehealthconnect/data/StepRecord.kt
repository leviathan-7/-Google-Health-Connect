package com.example.googlehealthconnect.data

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.health.connect.client.records.StepsRecord
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun StepRecord(
    record: StepsRecord,
    modifier: Modifier = Modifier,
    repo: HealthRepo,
    key:  MutableState<Int>
){
    val coroutineScope = rememberCoroutineScope()
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Text("Шагов " + SimpleDateFormat("MM/dd/yyyy").format(Date(record.startTime.epochSecond * 1000))  + " = " + record.count + "  ", fontSize = 23.sp)
        Button(onClick = {
            coroutineScope.launch{
                repo.deleteSteps(record)
                key.value++
            }
        },
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffffffff)
            )
        ){
            Text("❌", fontSize = 23.sp)
        }
    }
}