package com.example.googlehealthconnect.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.googlehealthconnect.data.HealthRepo
import com.example.googlehealthconnect.data.getStates
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@SuppressLint("PermissionLaunchedDuringComposition", "UnrememberedMutableState")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    navigateToSettings: () -> Unit,
    repo: HealthRepo
) {
    var states = mutableListOf<PermissionState>()
    val permisesState = mutableIntStateOf(0)
    var permises by permisesState
    permises+= getStates(states)

    if (permises == 4){
        MainScreen(
            modifier = modifier,
            repo = repo,
            navigateToSettings = navigateToSettings
        )
    } else{
        Allow(
            modifier = modifier,
            states = states,
            permisesState = permisesState
        )
    }
}
