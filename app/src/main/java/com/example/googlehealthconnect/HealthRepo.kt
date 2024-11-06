package com.example.googlehealthconnect

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

class HealthRepo(context: Context) {
    private val healthConnectClient = HealthConnectClient.getOrCreate(context)

    private val PERMISSIONS =
        setOf(
            HealthPermission.getReadPermission(WeightRecord::class),
            HealthPermission.getWritePermission(WeightRecord::class),
            HealthPermission.getReadPermission(StepsRecord::class),
            HealthPermission.getWritePermission(StepsRecord::class)
        )

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

}