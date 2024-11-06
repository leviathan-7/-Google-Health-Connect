package com.example.googlehealthconnect.data

import android.content.Context
import androidx.health.connect.client.HealthConnectClient

class HealthRepo(context: Context) {
    private val healthConnectClient = HealthConnectClient.getOrCreate(context)


}