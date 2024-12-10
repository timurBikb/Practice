package com.example.fitappnew

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ComplexAdapter(private val complexes: List<Complex>) :
    RecyclerView.Adapter<ComplexAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewExercise)
        val imageView: ImageView = view.findViewById(R.id.imageViewExercise)
        val textViewDuration: TextView = view.findViewById(R.id.textViewComplexity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val complex = complexes[position]
        holder.textViewName.text = complex.name
        holder.imageView.setImageResource(complex.imageResId)
        holder.textViewDuration.text = complex.duration

        // Добавляем обработчик нажатия
        holder.textViewName.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(complex.link))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = complexes.size
}