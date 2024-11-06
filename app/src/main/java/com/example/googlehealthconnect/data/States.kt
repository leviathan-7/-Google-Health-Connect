package com.example.googlehealthconnect.data

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun getStates(states: MutableList<PermissionState>): Int {
    var permises = 0
    PERMISSIONS.forEach {
        val state = rememberPermissionState(it)
        states.add(state)
        if (state.hasPermission) {
            permises++
        }
    }
    return permises
}