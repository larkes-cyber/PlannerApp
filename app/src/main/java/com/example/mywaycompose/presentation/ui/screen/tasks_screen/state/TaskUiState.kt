package com.example.mywaycompose.presentation.ui.screen.tasks_screen.state

import com.example.mywaycompose.domain.model.Task

data class TaskUiState(
    val tasks:List<Task> = listOf(),
    val isLoading:Boolean = false,
    val error:String = ""
)