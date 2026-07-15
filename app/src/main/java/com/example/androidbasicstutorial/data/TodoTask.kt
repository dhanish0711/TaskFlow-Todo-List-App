package com.example.androidbasicstutorial.data

import kotlinx.serialization.Serializable

enum class TaskPriority {
    LOW, MEDIUM, HIGH
}

@Serializable
data class TodoTask(
    val id: String,
    val title: String,
    val category: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val priority: TaskPriority = TaskPriority.MEDIUM
)
