package com.example.androidbasicstutorial.data

import com.example.androidbasicstutorial.data.api.ApiTodo
import com.example.androidbasicstutorial.data.api.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

interface DataRepository {
    val tasks: Flow<List<TodoTask>>
    suspend fun addTask(task: TodoTask)
    suspend fun toggleTask(taskId: String)
    suspend fun deleteTask(taskId: String)
    suspend fun clearAllTasks()
    suspend fun syncRemoteTasks()
    suspend fun uploadTask(task: TodoTask)
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

    override suspend fun syncRemoteTasks() {
        val remoteTodos = RetrofitInstance.api.getTodos().take(8)
        _tasks.update { localList ->
            val mappedTasks = remoteTodos.map { apiTodo ->
                TodoTask(
                    id = "remote_${apiTodo.id}",
                    title = apiTodo.title,
                    category = "Cloud",
                    isCompleted = apiTodo.completed,
                    priority = TaskPriority.MEDIUM
                )
            }
            val existingIds = localList.map { it.id }.toSet()
            val newUniqueTasks = mappedTasks.filter { it.id !in existingIds }
            localList + newUniqueTasks
        }
    }

    override suspend fun uploadTask(task: TodoTask) {
        val apiTodo = ApiTodo(
            userId = 1,
            id = Random.nextInt(1000, 9999),
            title = task.title,
            completed = task.isCompleted
        )
        RetrofitInstance.api.createTodo(apiTodo)
    }
}
