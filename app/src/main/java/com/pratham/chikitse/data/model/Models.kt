package com.pratham.chikitse.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single medical emergency type (e.g., Snake Bite, Choking).
 */
@Entity(tableName = "emergencies")
data class Emergency(
    @PrimaryKey val id: Int,
    val nameEn: String,
    val nameKn: String,
    val category: String,
    val colorHex: String,       // e.g. "#E53935"
    val iconName: String,       // drawable resource name suffix
    val stepsEn: String,        // JSON array of step strings
    val stepsKn: String,        // JSON array of step strings
    val dosEn: String,          // JSON array
    val dosKn: String,
    val dontsEn: String,        // JSON array
    val dontsKn: String,
    val keywordsEn: String,     // comma-separated search keywords
    val keywordsKn: String
)

/**
 * Represents a nearby hospital / health centre.
 */
@Entity(tableName = "hospitals")
data class Hospital(
    @PrimaryKey val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val latitude: Double,
    val longitude: Double,
    val districtKn: String,     // district name in Kannada for filtering
    val type: String            // "Government" | "Private" | "PHC"
)
