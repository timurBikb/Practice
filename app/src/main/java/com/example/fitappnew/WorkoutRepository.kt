// WorkoutRepository.kt
package com.example.fitappnew.data

import java.util.Calendar
import java.util.Date

class WorkoutRepository(private val workoutDao: WorkoutDao) {
    suspend fun addWorkout(workout: WorkoutEntity) {
        workoutDao.insert(workout)
    }

    suspend fun updateWorkout(workout: WorkoutEntity) {
        workoutDao.update(workout)
    }

    suspend fun deleteWorkout(workout: WorkoutEntity) {
        workoutDao.delete(workout)
    }

    suspend fun getWorkoutForDate(startOfDay: Date, endOfDay: Date): List<WorkoutEntity> {
        return workoutDao.getWorkoutsForDate(startOfDay, endOfDay)
    }

    suspend fun getMonthlyStats(year: Int, month: Int): WorkoutStatsResult {
        val calendar = Calendar.getInstance()

        // Начало месяца
        calendar.set(year, month, 1, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startDate = calendar.time

        // Конец месяца
        calendar.set(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endDate = calendar.time

        return workoutDao.getMonthlyStats(startDate, endDate)
    }
}