package com.example.androidbasicstutorial.ui.main

import com.example.androidbasicstutorial.data.DataRepository
import com.example.androidbasicstutorial.data.TaskPriority
import com.example.androidbasicstutorial.data.TodoTask
import com.example.androidbasicstutorial.data.UserRepository
import com.example.androidbasicstutorial.data.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun uiState_initiallyLoading() = runTest {
        val viewModel = MainScreenViewModel(FakeDataRepository(), FakeUserRepository())
        assertTrue(viewModel.uiState.value is MainScreenUiState.Loading)
    }

    @Test
    fun addTask_updatesUiState_andSuccessStateReturned() = runTest {
        val repository = FakeDataRepository()
        val viewModel = MainScreenViewModel(repository, FakeUserRepository())
        
        val collectJob = launch { viewModel.uiState.collect {} }

        viewModel.addTask("Test Task", "Dev", TaskPriority.HIGH)
        testDispatcher.scheduler.advanceUntilIdle()

        val successState = viewModel.uiState.value as MainScreenUiState.Success
        assertEquals(1, successState.tasks.size)
        assertEquals("Test Task", successState.tasks[0].title)
        assertEquals(TaskPriority.HIGH, successState.tasks[0].priority)

        collectJob.cancel()
    }

    @Test
    fun searchQuery_filtersTasksCorrectly() = runTest {
        val repository = FakeDataRepository()
        val viewModel = MainScreenViewModel(repository, FakeUserRepository())

        val collectJob = launch { viewModel.uiState.collect {} }

        viewModel.addTask("Study Kotlin", "Study", TaskPriority.MEDIUM)
        viewModel.addTask("Code UI", "Dev", TaskPriority.HIGH)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.setSearchQuery("Kotlin")
        testDispatcher.scheduler.advanceUntilIdle()

        val successState = viewModel.uiState.value as MainScreenUiState.Success
        assertEquals(1, successState.tasks.size)
        assertEquals("Study Kotlin", successState.tasks[0].title)

        collectJob.cancel()
    }

    @Test
    fun sortByPriority_ordersTasksCorrectly() = runTest {
        val repository = FakeDataRepository()
        val viewModel = MainScreenViewModel(repository, FakeUserRepository())

        val collectJob = launch { viewModel.uiState.collect {} }

        viewModel.addTask("Low Priority", "Dev", TaskPriority.LOW)
        viewModel.addTask("High Priority", "Dev", TaskPriority.HIGH)
        viewModel.addTask("Medium Priority", "Dev", TaskPriority.MEDIUM)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.setSortOption(TaskSortOption.PRIORITY)
        testDispatcher.scheduler.advanceUntilIdle()

        val successState = viewModel.uiState.value as MainScreenUiState.Success
        assertEquals(3, successState.tasks.size)
        assertEquals("High Priority", successState.tasks[0].title)
        assertEquals("Medium Priority", successState.tasks[1].title)
        assertEquals("Low Priority", successState.tasks[2].title)

        collectJob.cancel()
    }

    @Test
    fun userSession_loginLogout_updatesStateFlow() = runTest {
        val viewModel = MainScreenViewModel(FakeDataRepository(), FakeUserRepository())
        val collectJob = launch { viewModel.userSession.collect {} }
        
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.userSession.value.isLoggedIn)

        viewModel.login("dhanish", "dhanish@example.com")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.userSession.value.isLoggedIn)
        assertEquals("dhanish", viewModel.userSession.value.username)

        viewModel.logout()
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.userSession.value.isLoggedIn)

        collectJob.cancel()
    }
}

private class FakeDataRepository : DataRepository {
    private val _tasks = MutableStateFlow<List<TodoTask>>(emptyList())
    override val tasks: Flow<List<TodoTask>> = _tasks.asStateFlow()

    override suspend fun addTask(task: TodoTask) {
        _tasks.update { it + task }
    }

    override suspend fun toggleTask(taskId: String) {
        _tasks.update { list ->
            list.map { if (it.id == taskId) it.copy(isCompleted = !it.isCompleted) else it }
        }
    }

    override suspend fun deleteTask(taskId: String) {
        _tasks.update { list -> list.filter { it.id != taskId } }
    }

    override suspend fun clearAllTasks() {
        _tasks.update { emptyList() }
    }

    override suspend fun syncRemoteTasks() {
        _tasks.update {
            it + TodoTask("remote_1", "Remote Task", "Cloud")
        }
    }

    override suspend fun uploadTask(task: TodoTask) {}
}

private class FakeUserRepository : UserRepository {
    private val _userSession = MutableStateFlow(UserSession())
    override val userSession: Flow<UserSession> = _userSession.asStateFlow()

    override suspend fun login(username: String, email: String) {
        _userSession.value = UserSession(username, email, true)
    }

    override suspend fun logout() {
        _userSession.value = UserSession()
    }
}
