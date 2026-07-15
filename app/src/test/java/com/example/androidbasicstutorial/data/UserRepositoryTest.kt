package com.example.androidbasicstutorial.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File

class UserRepositoryTest {
    private lateinit var tempFile: File
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        tempFile = File.createTempFile("test_session", ".json")
        userRepository = FileUserRepository(tempFile)
    }

    @After
    fun tearDown() {
        if (tempFile.exists()) {
            tempFile.delete()
        }
    }

    @Test
    fun initialSession_isEmptyAndNotLoggedIn() = runTest {
        val session = userRepository.userSession.first()
        assertFalse(session.isLoggedIn)
        assertEquals("", session.username)
        assertEquals("", session.email)
    }

    @Test
    fun login_persistsSessionAndSetsLoggedIn() = runTest {
        userRepository.login("dhanish", "dhanish@example.com")
        val session = userRepository.userSession.first()
        assertTrue(session.isLoggedIn)
        assertEquals("dhanish", session.username)
        assertEquals("dhanish@example.com", session.email)
    }

    @Test
    fun logout_clearsSessionAndSetsLoggedOut() = runTest {
        userRepository.login("dhanish", "dhanish@example.com")
        userRepository.logout()
        val session = userRepository.userSession.first()
        assertFalse(session.isLoggedIn)
        assertEquals("", session.username)
        assertEquals("", session.email)
    }
}
