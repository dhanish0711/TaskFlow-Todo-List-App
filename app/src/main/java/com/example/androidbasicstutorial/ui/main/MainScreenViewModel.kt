package com.example.androidbasicstutorial.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbasicstutorial.data.DataRepository
import com.example.androidbasicstutorial.data.TodoTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class MainScreenViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    val uiState: StateFlow<MainScreenUiState> = combine(
        dataRepository.tasks,
        _selectedCategory
    ) { tasks, category ->
        val filteredTasks = if (category == "All") {
            tasks
        } else {
            tasks.filter { it.category.equals(category, ignoreCase = true) }
        }
        val categories = listOf("All") + tasks.map { it.category }.distinct().sorted()
        val totalCount = tasks.size
        val completedCount = tasks.count { it.isCompleted }
        val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

        MainScreenUiState.Success(
            tasks = filteredTasks,
            categories = categories,
            selectedCategory = category,
            totalCount = totalCount,
            completedCount = completedCount,
            progress = progress
        ) as MainScreenUiState
    }.catch {
        emit(MainScreenUiState.Error(it))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainScreenUiState.Loading
    )

    fun addTask(title: String, category: String) {
        if (title.isBlank() || category.isBlank()) return
        viewModelScope.launch {
            dataRepository.addTask(
                TodoTask(
                    id = UUID.randomUUID().toString(),
                    title = title.trim(),
                    category = category.trim()
                )
            )
        }
    }

    fun toggleTask(taskId: String) {
        viewModelScope.launch {
            dataRepository.toggleTask(taskId)
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            dataRepository.deleteTask(taskId)
        }
    }

    fun setCategoryFilter(category: String) {
        _selectedCategory.value = category
    }
}

sealed interface MainScreenUiState {
    object Loading : MainScreenUiState
    data class Error(val throwable: Throwable) : MainScreenUiState
    data class Success(
        val tasks: List<TodoTask>,
        val categories: List<String>,
        val selectedCategory: String,
        val totalCount: Int,
        val completedCount: Int,
        val progress: Float
    ) : MainScreenUiState
}
