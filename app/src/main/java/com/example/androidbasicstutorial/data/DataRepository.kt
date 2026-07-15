package com.example.androidbasicstutorial.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface DataRepository {
    val tasks: Flow<List<TodoTask>>
    suspend fun addTask(task: TodoTask)
    suspend fun toggleTask(taskId: String)
    suspend fun deleteTask(taskId: String)
    suspend fun clearAllTasks()
}

class DefaultDataRepository : DataRepository {
    private val _tasks = MutableStateFlow<List<TodoTask>>(
        listOf(
            TodoTask(
                id = "1",
                title = "Install Android Studio & JDK 20",
                category = "Setup",
                isCompleted = true,
                priority = TaskPriority.HIGH
            ),
            TodoTask(
                id = "2",
                title = "Configure Git & Initialize Repo",
                category = "Setup",
                isCompleted = true,
                priority = TaskPriority.HIGH
            ),
            TodoTask(
                id = "3",
                title = "Create TaskFlow Todo App",
                category = "App Dev",
                isCompleted = false,
                priority = TaskPriority.HIGH
            ),
            TodoTask(
                id = "4",
                title = "Review Android Lifecycle & Layouts",
                category = "Study",
                isCompleted = false,
                priority = TaskPriority.MEDIUM
            ),
            TodoTask(
                id = "5",
                title = "Verify Build & Push to GitHub",
                category = "App Dev",
                isCompleted = false,
                priority = TaskPriority.LOW
            )
        )
    )

    override val tasks: Flow<List<TodoTask>> = _tasks.asStateFlow()

    override suspend fun addTask(task: TodoTask) {
        _tasks.update { it + task }
    }

    override suspend fun toggleTask(taskId: String) {
        _tasks.update { list ->
            list.map { task ->
                if (task.id == taskId) task.copy(isCompleted = !task.isCompleted) else task
            }
        }
    }

    override suspend fun deleteTask(taskId: String) {
        _tasks.update { list ->
            list.filter { it.id != taskId }
        }
    }

    override suspend fun clearAllTasks() {
        _tasks.update { emptyList() }
    }
}
