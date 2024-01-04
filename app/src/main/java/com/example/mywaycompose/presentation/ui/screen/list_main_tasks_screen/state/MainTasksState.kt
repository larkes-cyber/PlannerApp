package com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.state

import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.MainTaskWithVisualTasks

data class MainTasksState(
    val isLoading:Boolean = false,
    val success:List<MainTaskWithVisualTasks> = listOf(),
    val error:String = ""
)
