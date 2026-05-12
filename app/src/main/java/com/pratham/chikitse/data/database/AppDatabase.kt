package com.pratham.chikitse.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pratham.chikitse.data.dao.EmergencyDao
import com.pratham.chikitse.data.dao.HospitalDao
import com.pratham.chikitse.data.model.Emergency
import com.pratham.chikitse.data.model.Hospital

@Database(
    entities = [Emergency::class, Hospital::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emergencyDao(): EmergencyDao
    abstract fun hospitalDao(): HospitalDao

    companion object {
        const val DATABASE_NAME = "pratham_chikitse.db"
    }
}
