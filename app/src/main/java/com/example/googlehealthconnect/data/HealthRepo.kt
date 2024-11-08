package com.example.googlehealthconnect.data

import android.content.Context
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.AggregateRequest
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
        start: Long,
        final: Long
    ) : List<StepsRecord> {
        try {
            return healthConnectClient.readRecords(
                        ReadRecordsRequest(
                            StepsRecord::class,
                            timeRangeFilter = TimeRangeFilter.between(
                                Instant.ofEpochSecond(start),
                                Instant.ofEpochSecond(final + 1)
                            )
                        )
                    ).records
        } catch (e: Exception) {
            Log.v("HealthRepo","readException " + e.message)
            return listOf<StepsRecord>()
        }
    }

    suspend fun aggregateSteps(
        start: Long,
        final: Long
    ): Long {
        try {
            return healthConnectClient.aggregate(
                AggregateRequest(
                    metrics = setOf(StepsRecord.COUNT_TOTAL),
                    timeRangeFilter = TimeRangeFilter.between(
                        Instant.ofEpochSecond(start),
                        Instant.ofEpochSecond(final + 1)
                    )
                )
            )[StepsRecord.COUNT_TOTAL]!!
        } catch (e: Exception) {
            Log.v("HealthRepo","aggregateException " + e.message)
            return 0L
        }
    }

    suspend fun deleteSteps(record: StepsRecord) {
        try {
            healthConnectClient.deleteRecords(
                StepsRecord::class,
                TimeRangeFilter.between(
                    record.startTime.minusSeconds(2),
                    record.endTime.plusSeconds(2)
                )
            )
        } catch (e: Exception) {
            Log.v("HealthRepo","deleteException " + e.message)
        }
    }

}