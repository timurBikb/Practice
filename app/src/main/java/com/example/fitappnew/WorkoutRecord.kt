package com.example.fitappnew

import java.util.Date

data class WorkoutRecord(
    val date: Date,
    val workoutType: String,
    val duration: Int, // в минутах
    val completed: Boolean = true
)