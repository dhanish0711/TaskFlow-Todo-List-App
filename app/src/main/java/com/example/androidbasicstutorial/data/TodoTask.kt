package com.example.androidbasicstutorial.data

import kotlinx.serialization.Serializable

@Serializable
data class TodoTask(
    val id: String,
    val title: String,
    val category: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
