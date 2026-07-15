package com.example.androidbasicstutorial.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class UserSession(
    val username: String = "",
    val email: String = "",
    val isLoggedIn: Boolean = false
)

interface UserRepository {
    val userSession: Flow<UserSession>
    suspend fun login(username: String, email: String)
    suspend fun logout()
}

class FileUserRepository(private val sessionFile: File) : UserRepository {

    private val _userSession = MutableStateFlow(loadSession())
    override val userSession: Flow<UserSession> = _userSession.asStateFlow()

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    override suspend fun login(username: String, email: String) {
        val session = UserSession(username = username, email = email, isLoggedIn = true)
        saveSession(session)
        _userSession.update { session }
    }

    override suspend fun logout() {
        val session = UserSession()
        saveSession(session)
        _userSession.update { session }
    }

    private fun loadSession(): UserSession {
        return try {
            if (sessionFile.exists()) {
                val content = sessionFile.readText()
                json.decodeFromString<UserSession>(content)
            } else {
                UserSession()
            }
        } catch (e: Exception) {
            UserSession()
        }
    }

    private fun saveSession(session: UserSession) {
        try {
            val content = json.encodeToString(UserSession.serializer(), session)
            sessionFile.writeText(content)
        } catch (e: Exception) {
            // Ignore write exceptions in readonly scopes
        }
    }
}
