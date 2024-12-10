package com.example.fitappnew

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PopularActivityAdapter(private val activities: List<PopularActivity>) :
    RecyclerView.Adapter<PopularActivityAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewExercise)
        val imageView: ImageView = view.findViewById(R.id.imageViewExercise)
        val textViewCalories: TextView = view.findViewById(R.id.textViewComplexity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = activities[position]
        holder.textViewName.text = activity.name
        holder.imageView.setImageResource(activity.imageResId)
        holder.textViewCalories.text = "${activity.caloriesPerHour} kcal/hr"

        // Добавляем обработчик нажатия
        holder.textViewName.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(activity.link))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = activities.size
}