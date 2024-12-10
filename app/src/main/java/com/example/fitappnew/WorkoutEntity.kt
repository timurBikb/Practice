// WorkoutEntity.kt
package com.example.fitappnew.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date,
    val workoutType: String,
    val duration: Int,
    val completed: Boolean = true
)