package com.example.googlehealthconnect.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.googlehealthconnect.HealthRepo
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@SuppressLint("PermissionLaunchedDuringComposition", "UnrememberedMutableState")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun App(modifier: Modifier = Modifier) {
    val repo = HealthRepo(LocalContext.current)
    var states = mutableListOf<PermissionState>()
    val permisesState = mutableIntStateOf(0)
    var permises by permisesState
    permises+= repo.getStates(states)

    if (permises == 4){
        MainScreen(
            modifier = modifier,
            repo = repo
        )
    } else{
        Allow(
            modifier = modifier,
            states = states,
            permisesState = permisesState
        )
    }
}
