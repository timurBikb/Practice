// WorkoutListAdapter.kt
package com.example.fitappnew.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitappnew.R
import com.example.fitappnew.data.WorkoutEntity
import java.text.SimpleDateFormat
import java.util.Locale

class WorkoutListAdapter(
    private var workouts: List<WorkoutEntity>,
    private val onEditClick: (WorkoutEntity) -> Unit,
    private val onDeleteClick: (WorkoutEntity) -> Unit
) : RecyclerView.Adapter<WorkoutListAdapter.WorkoutViewHolder>() {

    class WorkoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeText: TextView = view.findViewById(R.id.workoutTimeText)
        val typeText: TextView = view.findViewById(R.id.workoutTypeText)
        val durationText: TextView = view.findViewById(R.id.workoutDurationText)
        val editButton: ImageButton = view.findViewById(R.id.editButton)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        holder.timeText.text = timeFormat.format(workout.date)
        holder.typeText.text = workout.workoutType
        holder.durationText.text = "${workout.duration} min"

        holder.editButton.setOnClickListener { onEditClick(workout) }
        holder.deleteButton.setOnClickListener { onDeleteClick(workout) }
    }

    override fun getItemCount() = workouts.size

    fun updateWorkouts(newWorkouts: List<WorkoutEntity>) {
        workouts = newWorkouts
        notifyDataSetChanged()
    }
}