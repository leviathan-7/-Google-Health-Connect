package com.example.googlehealthconnect.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.example.googlehealthconnect.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Allow(
    modifier: Modifier = Modifier,
    states: MutableList<PermissionState>,
    permisesState:  MutableIntState
) {
    var permises by permisesState

    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Text(
            text = "Выдайте разрешения:",
            fontSize = 27.sp
        )
        for (state in states){
            PermissionRequired(
                permissionState = state,
                permissionNotGrantedContent = {
                    Button(onClick = {
                        state.launchPermissionRequest()
                    },
                        modifier = modifier
                    ){
                        Text("Allow " + state.permission.split('.').get(3).replace('_', ' ').lowercase() + "?")
                    }
                },
                permissionNotAvailableContent = {
                    Text("В настройках google health разрешите " + state.permission.split('.').get(3))
                },
                content = {
                    permises++
                }
            )
        }
    }
}