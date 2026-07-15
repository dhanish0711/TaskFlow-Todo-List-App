package com.example.androidbasicstutorial

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.androidbasicstutorial.data.DataRepository
import com.example.androidbasicstutorial.data.UserRepository
import com.example.androidbasicstutorial.ui.main.MainScreen
import com.example.androidbasicstutorial.ui.main.MainScreenViewModel

@Composable
fun MainNavigation(
    dataRepository: DataRepository,
    userRepository: UserRepository
) {
  val backStack = rememberNavBackStack(Main)

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider =
      entryProvider {
        entry<Main> {
          val factory = object : ViewModelProvider.Factory {
              @Suppress("UNCHECKED_CAST")
              override fun <T : ViewModel> create(modelClass: Class<T>): T {
                  return MainScreenViewModel(dataRepository, userRepository) as T
              }
          }
          val mainViewModel: MainScreenViewModel = viewModel(factory = factory)
          MainScreen(
              onItemClick = { navKey -> backStack.add(navKey) },
              viewModel = mainViewModel,
              modifier = Modifier.safeDrawingPadding().padding(16.dp)
          )
        }
      },
  )
}
