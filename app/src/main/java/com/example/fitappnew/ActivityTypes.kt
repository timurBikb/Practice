package com.example.fitappnew

data class Exercise(
    val name: String,
    val imageResId: Int,
    val complexity: String,
    val link: String
)

data class Complex(
    val name: String,
    val imageResId: Int,
    val duration: String,
    val exercises: List<String>,
    val link: String // Добавляем поле для ссылки
)

data class PopularActivity(
    val name: String,
    val imageResId: Int,
    val caloriesPerHour: Int,
    val link: String // Добавляем поле для ссылки
)