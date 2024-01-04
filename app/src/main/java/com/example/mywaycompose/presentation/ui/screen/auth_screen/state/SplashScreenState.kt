package com.example.mywaycompose.presentation.ui.screen.auth_screen.state

data class SplashScreenState(
    val tasksHasBeenLoaded:Boolean = false,
    val mainTasksHasBeenLoaded:Boolean = false,
    val ideasHasBeenLoaded:Boolean = false,
    val visualTasksHasBeenLoaded:Boolean = false,
    val productTasksHasBeenLoaded:Boolean = false,
    val taskClassesHasBeenLoaded:Boolean = false
)