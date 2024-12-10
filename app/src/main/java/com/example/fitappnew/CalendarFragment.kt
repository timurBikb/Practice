package com.example.fitappnew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitappnew.adapters.WorkoutListAdapter
import com.example.fitappnew.data.AppDatabase
import com.example.fitappnew.data.WorkoutEntity
import com.example.fitappnew.data.WorkoutRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarFragment : Fragment() {
    private lateinit var calendarView: CalendarView
    private lateinit var repository: WorkoutRepository
    private lateinit var monthStatsTextView: TextView
    private lateinit var selectedDateText: TextView
    private lateinit var workoutTypeInput: TextInputEditText
    private lateinit var durationInput: TextInputEditText
    private lateinit var saveWorkoutButton: Button
    private lateinit var workoutsList: RecyclerView
    private lateinit var workoutListAdapter: WorkoutListAdapter
    private var selectedDate: Date = Date()
    private var currentCalendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        val database = AppDatabase.getDatabase(requireContext())
        repository = WorkoutRepository(database.workoutDao())

        initializeViews(view)
        setupRecyclerView()
        setupCalendarView()
        setupSaveButton()

        // Устанавливаем начальные значения
        currentCalendar.time = selectedDate
        updateMonthlyStats(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH))
        updateSelectedDateText()
        loadWorkoutsForDate(selectedDate)

        return view
    }

    private fun initializeViews(view: View) {
        calendarView = view.findViewById(R.id.calendarView)
        monthStatsTextView = view.findViewById(R.id.monthStatsTextView)
        selectedDateText = view.findViewById(R.id.selectedDateText)
        workoutTypeInput = view.findViewById(R.id.workoutTypeInput)
        durationInput = view.findViewById(R.id.durationInput)
        saveWorkoutButton = view.findViewById(R.id.saveWorkoutButton)
        workoutsList = view.findViewById(R.id.workoutsList)
    }

    private fun setupRecyclerView() {
        workoutListAdapter = WorkoutListAdapter(
            emptyList(),
            onEditClick = { workout -> showEditDialog(workout) },
            onDeleteClick = { workout -> showDeleteDialog(workout) }
        )
        workoutsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = workoutListAdapter
        }
    }

    private fun setupCalendarView() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            currentCalendar.set(year, month, dayOfMonth)
            selectedDate = currentCalendar.time

            updateSelectedDateText()
            loadWorkoutsForDate(selectedDate)
            updateMonthlyStats(year, month)

            // Очищаем поля ввода
            workoutTypeInput.text?.clear()
            durationInput.text?.clear()
        }
    }

    private fun loadWorkoutsForDate(date: Date) {
        viewLifecycleOwner.lifecycleScope.launch {
            val startOfDay = getStartOfDay(date)
            val endOfDay = getEndOfDay(date)
            val workouts = repository.getWorkoutForDate(startOfDay, endOfDay)
            workoutListAdapter.updateWorkouts(workouts)
        }
    }

    private fun showEditDialog(workout: WorkoutEntity) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_workout, null)
        val typeInput = dialogView.findViewById<TextInputEditText>(R.id.editWorkoutTypeInput)
        val durationInput = dialogView.findViewById<TextInputEditText>(R.id.editDurationInput)

        typeInput.setText(workout.workoutType)
        durationInput.setText(workout.duration.toString())

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Workout")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newType = typeInput.text?.toString() ?: return@setPositiveButton
                val newDuration = durationInput.text?.toString()?.toIntOrNull() ?: return@setPositiveButton

                viewLifecycleOwner.lifecycleScope.launch {
                    repository.updateWorkout(workout.copy(
                        workoutType = newType,
                        duration = newDuration
                    ))
                    loadWorkoutsForDate(selectedDate)
                    updateMonthlyStats(
                        currentCalendar.get(Calendar.YEAR),
                        currentCalendar.get(Calendar.MONTH)
                    )
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteDialog(workout: WorkoutEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Workout")
            .setMessage("Are you sure you want to delete this workout?")
            .setPositiveButton("Delete") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    repository.deleteWorkout(workout)
                    loadWorkoutsForDate(selectedDate)
                    updateMonthlyStats(
                        currentCalendar.get(Calendar.YEAR),
                        currentCalendar.get(Calendar.MONTH)
                    )
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun getStartOfDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    private fun getEndOfDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }

    private fun setupSaveButton() {
        saveWorkoutButton.setOnClickListener {
            val workoutType = workoutTypeInput.text?.toString() ?: return@setOnClickListener
            val duration = durationInput.text?.toString()?.toIntOrNull() ?: return@setOnClickListener

            val workout = WorkoutEntity(
                date = selectedDate,
                workoutType = workoutType,
                duration = duration
            )

            viewLifecycleOwner.lifecycleScope.launch {
                repository.addWorkout(workout)
                loadWorkoutsForDate(selectedDate)
                updateMonthlyStats(
                    currentCalendar.get(Calendar.YEAR),
                    currentCalendar.get(Calendar.MONTH)
                )
            }

            // Очищаем поля после сохранения
            workoutTypeInput.text?.clear()
            durationInput.text?.clear()
        }
    }

    private fun updateMonthlyStats(year: Int, month: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            val stats = repository.getMonthlyStats(year, month)
            val monthName = SimpleDateFormat("MMMM", Locale.ENGLISH).format(currentCalendar.time)

            monthStatsTextView.text = """
                $monthName $year Statistics:
                Total Workouts: ${stats.totalWorkouts}
                Total Duration: ${stats.totalDuration} minutes
            """.trimIndent()
        }
    }

    private fun updateSelectedDateText() {
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
        selectedDateText.text = "Selected Date: ${dateFormat.format(selectedDate)}"
    }
}