package com.example.androidbasicstutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.androidbasicstutorial.data.DataRepository
import com.example.androidbasicstutorial.data.DefaultDataRepository
import com.example.androidbasicstutorial.data.FileUserRepository
import com.example.androidbasicstutorial.data.UserRepository
import com.example.androidbasicstutorial.theme.AndroidBasicsTutorialTheme
import java.io.File

class MainActivity : ComponentActivity() {
  private lateinit var userRepository: UserRepository
  private lateinit var dataRepository: DataRepository

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val sessionFile = File(filesDir, "user_session.json")
    userRepository = FileUserRepository(sessionFile)
    dataRepository = DefaultDataRepository()

    enableEdgeToEdge()
    setContent {
      AndroidBasicsTutorialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          MainNavigation(dataRepository, userRepository)
        }
      }
    }
  }
}
