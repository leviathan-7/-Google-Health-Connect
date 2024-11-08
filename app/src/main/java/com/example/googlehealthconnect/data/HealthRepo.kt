package com.example.googlehealthconnect.data

import android.content.Context
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Mass
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import kotlin.text.toLong

class HealthRepo(context: Context) {
    private val healthConnectClient = HealthConnectClient.getOrCreate(context)

    suspend fun insertSteps(
        count: String,
        instant0: Instant,
        instant1: Instant
    ) {
        try {
            val stepsRecord = StepsRecord(
                count = count.toLong(),
                startTime = instant0,
                endTime = instant1,
                startZoneOffset = null,
                endZoneOffset = null,
            )
            healthConnectClient.insertRecords(listOf(stepsRecord))
        } catch (e: Exception) {
            Log.v("HealthRepo","insertException " + e.message)
        }
    }

    suspend fun readStepsByTimeRange(
        startTime: Instant,
        endTime: Instant
    ) : List<StepsRecord>? {
        try {
            return healthConnectClient.readRecords(
                        ReadRecordsRequest(
                            StepsRecord::class,
                            timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                        )
                    ).records
        } catch (e: Exception) {
            Log.v("HealthRepo","readException " + e.message)
            return null
        }
    }
    suspend fun deleteSteps(record: StepsRecord) {
        try {
            healthConnectClient.deleteRecords(
                StepsRecord::class,
                listOf(record.metadata.id),
                listOf(record.metadata.clientRecordId!!)
            )
        } catch (e: Exception) {
            Log.v("HealthRepo","deleteException " + e.message)
        }
    }

}