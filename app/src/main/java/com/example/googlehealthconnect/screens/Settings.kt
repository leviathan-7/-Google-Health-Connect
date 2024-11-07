package com.example.googlehealthconnect.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.googlehealthconnect.data.HealthRepo
import com.example.googlehealthconnect.R
import com.example.googlehealthconnect.data.DatePick
import com.example.googlehealthconnect.navigation.TopAppBar
import kotlinx.coroutines.launch
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    modifier: Modifier = Modifier,
    repo: HealthRepo,
    navigateBack: () -> Unit
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopAppBar(
            title = "Добавление данных",
            canNavigateBack = true,
            navigateUp = navigateBack
        )
        val coroutineScope = rememberCoroutineScope()

        val newStep = remember {mutableStateOf("")}
        TextField(
            value = newStep.value,
            textStyle = TextStyle(fontSize=25.sp),
            onValueChange = {newText -> newStep.value = newText},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        var time = remember {mutableStateOf(0L)}
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text("Дата ", fontSize = 23.sp)
            DatePick(time = time)
        }
        Button(onClick = {
            coroutineScope.launch {
                repo.insertSteps(
                    count = newStep.value,
                    instant = Instant.ofEpochSecond(time.value)
                )
                navigateBack()
            }
        },
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffff9115)
            )
        ){
            Text("Добавить шаги", fontSize = 19.sp)
        }

    }
}