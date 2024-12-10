package com.example.fitappnew

import java.util.Date
import java.util.Calendar

class WorkoutTracker {
    private val workouts = mutableListOf<WorkoutRecord>()

    fun addWorkout(workout: WorkoutRecord) {
        workouts.add(workout)
    }

    fun getWorkoutForDate(date: Date): WorkoutRecord? {
        return workouts.find { isSameDay(it.date, date) }
    }

    fun getWorkoutsForMonth(year: Int, month: Int): List<WorkoutRecord> {
        return workouts.filter {
            val cal = Calendar.getInstance()
            cal.time = it.date
            cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) == month
        }
    }

    fun getMonthlyStats(year: Int, month: Int): WorkoutStats {
        val monthWorkouts = getWorkoutsForMonth(year, month)
        return WorkoutStats(
            totalWorkouts = monthWorkouts.size,
            totalDuration = monthWorkouts.sumOf { it.duration },
            completedWorkouts = monthWorkouts.count { it.completed }
        )
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }
}

data class WorkoutStats(
    val totalWorkouts: Int,
    val totalDuration: Int,
    val completedWorkouts: Int
)