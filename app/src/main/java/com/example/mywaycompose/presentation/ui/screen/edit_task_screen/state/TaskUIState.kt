package com.example.mywaycompose.presentation.ui.screen.edit_task_screen.state

import com.example.mywaycompose.domain.model.Task

data class TaskUIState(
    val task:Task? = null,
    val isLoading:Boolean = false,
    val error:String = ""
)