// WorkoutDao.kt
package com.example.fitappnew.data

import androidx.room.*
import java.util.Date

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insert(workout: WorkoutEntity)

    @Update
    suspend fun update(workout: WorkoutEntity)

    @Delete
    suspend fun delete(workout: WorkoutEntity)

    @Query("SELECT * FROM workouts WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getWorkoutsForMonth(startDate: Date, endDate: Date): List<WorkoutEntity>

    @Query("SELECT * FROM workouts WHERE date >= :startOfDay AND date < :endOfDay")
    suspend fun getWorkoutsForDate(startOfDay: Date, endOfDay: Date): List<WorkoutEntity>

    @Query("""
    SELECT 
        COUNT(*) as totalWorkouts,
        SUM(duration) as totalDuration
    FROM workouts
    WHERE date BETWEEN :startDate AND :endDate
""")
    suspend fun getMonthlyStats(startDate: Date, endDate: Date): WorkoutStatsResult
}