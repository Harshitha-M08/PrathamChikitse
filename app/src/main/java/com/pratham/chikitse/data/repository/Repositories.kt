package com.pratham.chikitse.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pratham.chikitse.data.dao.EmergencyDao
import com.pratham.chikitse.data.dao.HospitalDao
import com.pratham.chikitse.data.model.Emergency
import com.pratham.chikitse.data.model.Hospital
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmergencyRepository @Inject constructor(
    private val emergencyDao: EmergencyDao,
    private val context: Context
) {
    fun getAllEmergencies(): Flow<List<Emergency>> = emergencyDao.getAllEmergencies()

    suspend fun getEmergencyById(id: Int): Emergency? = emergencyDao.getEmergencyById(id)

    suspend fun searchEmergencies(query: String): List<Emergency> =
        emergencyDao.searchEmergencies(query)

    fun getEmergenciesByCategory(category: String): Flow<List<Emergency>> =
        emergencyDao.getEmergenciesByCategory(category)

    /** Seeds the DB from assets if empty */
    suspend fun seedIfNeeded() {
        if (emergencyDao.count() == 0) {
            val json = context.assets.open("emergencies.json").bufferedReader().readText()
            val type = object : TypeToken<List<Emergency>>() {}.type
            val list: List<Emergency> = Gson().fromJson(json, type)
            emergencyDao.insertAll(list)
        }
    }
}

@Singleton
class HospitalRepository @Inject constructor(
    private val hospitalDao: HospitalDao,
    private val context: Context
) {
    fun getAllHospitals(): Flow<List<Hospital>> = hospitalDao.getAllHospitals()

    suspend fun getNearestHospitals(): List<Hospital> = hospitalDao.getNearestHospitals()

    suspend fun seedIfNeeded() {
        if (hospitalDao.count() == 0) {
            val json = context.assets.open("hospitals.json").bufferedReader().readText()
            val type = object : TypeToken<List<Hospital>>() {}.type
            val list: List<Hospital> = Gson().fromJson(json, type)
            hospitalDao.insertAll(list)
        }
    }
}
