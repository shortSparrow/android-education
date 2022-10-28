package com.example.mycomposeapp.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MyApp(
    mainViewModel: MainViewModel = viewModel()
) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        Onboarding(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings(
            names = mainViewModel.list,
            onRemoveClicked={task -> mainViewModel.removeTask(task)},
            onCheckedTask = { task, checked ->
                mainViewModel.toggleCheck(task, checked)
            },
        )
    }
}


