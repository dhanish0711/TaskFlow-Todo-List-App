package com.example.androidbasicstutorial.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbasicstutorial.data.DataRepository
import com.example.androidbasicstutorial.data.TaskPriority
import com.example.androidbasicstutorial.data.TodoTask
import com.example.androidbasicstutorial.theme.ThemePalette
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

enum class TaskSortOption {
    DATE, TITLE, PRIORITY
}

class MainScreenViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _sortBy = MutableStateFlow(TaskSortOption.DATE)
    val sortBy = _sortBy.asStateFlow()

    private val _themePalette = MutableStateFlow(ThemePalette.Indigo)
    val themePalette = _themePalette.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme = _isDarkTheme.asStateFlow()

    val uiState: StateFlow<MainScreenUiState> = combine(
        dataRepository.tasks,
        _selectedCategory,
        _searchQuery,
        _sortBy
    ) { tasks, category, query, sort ->
        // 1. Filter by category
        var filteredTasks = if (category == "All") {
            tasks
        } else {
            tasks.filter { it.category.equals(category, ignoreCase = true) }
        }

        // 2. Filter by search query
        if (query.isNotBlank()) {
            filteredTasks = filteredTasks.filter { it.title.contains(query, ignoreCase = true) }
        }

        // 3. Sort
        filteredTasks = when (sort) {
            TaskSortOption.DATE -> filteredTasks.sortedByDescending { it.createdAt }
            TaskSortOption.TITLE -> filteredTasks.sortedBy { it.title.lowercase() }
            TaskSortOption.PRIORITY -> filteredTasks.sortedBy {
                when (it.priority) {
                    TaskPriority.HIGH -> 0
                    TaskPriority.MEDIUM -> 1
                    TaskPriority.LOW -> 2
                }
            }
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

    fun addTask(title: String, category: String, priority: TaskPriority) {
        if (title.isBlank() || category.isBlank()) return
        viewModelScope.launch {
            dataRepository.addTask(
                TodoTask(
                    id = UUID.randomUUID().toString(),
                    title = title.trim(),
                    category = category.trim(),
                    priority = priority
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

    fun clearAllTasks() {
        viewModelScope.launch {
            dataRepository.clearAllTasks()
        }
    }

    fun setCategoryFilter(category: String) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSortOption(option: TaskSortOption) {
        _sortBy.value = option
    }

    fun setThemePalette(palette: ThemePalette) {
        _themePalette.value = palette
    }

    fun setDarkTheme(dark: Boolean) {
        _isDarkTheme.value = dark
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
