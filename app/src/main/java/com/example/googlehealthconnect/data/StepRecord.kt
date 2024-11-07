package com.example.googlehealthconnect.data

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.health.connect.client.records.StepsRecord
import kotlinx.coroutines.launch

@Composable
fun StepRecord(
    record: StepsRecord,
    modifier: Modifier = Modifier,
    repo: HealthRepo
){
    val coroutineScope = rememberCoroutineScope()
    Row{
        Text("Количество шагов " + record.startTime + " = " + record.count + " ")
        Button(onClick = {
            coroutineScope.launch{
                repo.deleteSteps(record)
            }
        },
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffff9115)
            )
        ){
            Text("Удалить", fontSize = 19.sp)
        }
    }
}