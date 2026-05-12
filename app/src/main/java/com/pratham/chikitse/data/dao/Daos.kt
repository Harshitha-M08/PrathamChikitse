package com.pratham.chikitse.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pratham.chikitse.data.model.Emergency
import com.pratham.chikitse.data.model.Hospital
import kotlinx.coroutines.flow.Flow

@Dao
interface EmergencyDao {

    @Query("SELECT * FROM emergencies ORDER BY nameEn ASC")
    fun getAllEmergencies(): Flow<List<Emergency>>

    @Query("SELECT * FROM emergencies WHERE id = :id")
    suspend fun getEmergencyById(id: Int): Emergency?

    @Query(
        """SELECT * FROM emergencies WHERE
        lower(nameEn) LIKE '%' || lower(:query) || '%' OR
        lower(nameKn) LIKE '%' || lower(:query) || '%' OR
        lower(keywordsEn) LIKE '%' || lower(:query) || '%' OR
        lower(keywordsKn) LIKE '%' || lower(:query) || '%'
        ORDER BY nameEn ASC"""
    )
    suspend fun searchEmergencies(query: String): List<Emergency>

    @Query("SELECT * FROM emergencies WHERE category = :category ORDER BY nameEn ASC")
    fun getEmergenciesByCategory(category: String): Flow<List<Emergency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(emergencies: List<Emergency>)

    @Query("SELECT COUNT(*) FROM emergencies")
    suspend fun count(): Int
}

@Dao
interface HospitalDao {

    @Query("SELECT * FROM hospitals ORDER BY name ASC")
    fun getAllHospitals(): Flow<List<Hospital>>

    @Query("SELECT * FROM hospitals ORDER BY name ASC LIMIT 10")
    suspend fun getNearestHospitals(): List<Hospital>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hospitals: List<Hospital>)

    @Query("SELECT COUNT(*) FROM hospitals")
    suspend fun count(): Int
}
