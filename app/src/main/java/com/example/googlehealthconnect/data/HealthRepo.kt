package com.example.googlehealthconnect.data

import android.content.Context
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
    private val clock: Clock = Clock.system(ZoneId.systemDefault())

    suspend fun insertSteps(count: String) {
        try {
            val stepsRecord = StepsRecord(
                count = count.toLong(),
                startTime = clock.instant(),
                endTime = clock.instant(),
                startZoneOffset = null,
                endZoneOffset = null,
            )
            healthConnectClient.insertRecords(listOf(stepsRecord))
        } catch (e: Exception) {
            // Run error handling here
        }
    }

    suspend fun readStepsByTimeRange(
        healthConnectClient: HealthConnectClient,
        startTime: Instant,
        endTime: Instant
    ) : List<StepsRecord>? {
        try {
            val response =
                healthConnectClient.readRecords(
                    ReadRecordsRequest(
                        StepsRecord::class,
                        timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                    )
                )
            response.records.get(0).metadata
            return response.records
        } catch (e: Exception) {
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
            // Run error handling here
        }
    }

}