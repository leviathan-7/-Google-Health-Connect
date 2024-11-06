package com.example.googlehealthconnect.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.example.googlehealthconnect.HealthRepo
import com.example.googlehealthconnect.R

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    repo: HealthRepo
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        Text(
            text = "MainScreen",
            fontSize = 27.sp
        )
    }
}