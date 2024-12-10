package com.example.fitappnew

import ExerciseAdapter
import Item
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout

    // Списки данных
    private val exercises = listOf(
        Exercise("Plank", R.drawable.ex_plank, "Easy", "https://youtu.be/pSHjTRCQxIw?t=22"),
        Exercise("Push ups", R.drawable.ex_push_ups, "Easy", "https://youtu.be/WDIpL0pjun0"),
        Exercise("Pull-ups", R.drawable.ex_pull_ups, "Hard", "https://youtu.be/aAggnpPyR6E"),
        Exercise("Bench press", R.drawable.ex_bench_press, "Medium", "https://youtu.be/SCVCLChPQFY"),
        Exercise("Australian pull-ups", R.drawable.ex_australian_pull_ups, "Easy", "https://youtu.be/bHO0A4ZF_Zg"),
        Exercise("Bent over row", R.drawable.ex_bent_over_row, "Hard", "https://youtu.be/6gvmcqr226U")
    )

    private val complexes = listOf(
        Complex("EMOM 10", R.drawable.emom, "10 min",
            listOf("10 burpees", "15 push-ups", "20 squats"), "https://youtu.be/8BFjkT4LhlU"),
        Complex("Tabata", R.drawable.tabata, "4 min",
            listOf("20s work", "10s rest", "8 rounds"), "https://youtu.be/ttkq49P_7ds"),
        Complex("For Time", R.drawable.for_time, "15 min cap",
            listOf("100 push-ups", "100 sit-ups", "100 squats"), "https://youtu.be/RGPm3QiA3sI")
    )

    private val popularActivities = listOf(
        PopularActivity("Swimming", R.drawable.swimming, 430, "https://youtu.be/LijdyVaaDnY"),
        PopularActivity("Tennis", R.drawable.tennis, 450, "https://youtu.be/YqgcykDGB2A"),
        PopularActivity("Running", R.drawable.running, 600, "https://youtu.be/v1Bj-0QYnIg"),
        PopularActivity("Cycling", R.drawable.cycling, 400, "https://youtu.be/VsuShNWghXk")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        tabLayout = view.findViewById(R.id.tabLayout)

        setupTabLayout()
        setupRecyclerView()

        return view
    }

    private fun setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Exercises"))
        tabLayout.addTab(tabLayout.newTab().setText("Complexes"))
        tabLayout.addTab(tabLayout.newTab().setText("Popular"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showExercises()
                    1 -> showComplexes()
                    2 -> showPopularActivities()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        showExercises() // Показываем упражнения по умолчанию
    }

    private fun showExercises() {
        recyclerView.adapter = ExerciseAdapter(exercises.map {
            Item(it.name, it.imageResId, it.complexity, it.link)
        }, requireContext())
    }

    private fun showComplexes() {
        recyclerView.adapter = ComplexAdapter(complexes)
    }

    private fun showPopularActivities() {
        recyclerView.adapter = PopularActivityAdapter(popularActivities)
    }
}