package com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.state

import com.example.mywaycompose.domain.model.VisualTask

data class VisualTaskUIState(
    val visualTasks:List<VisualTask> = listOf(),
    val visualTaskField:String = "",
    val isLoading:Boolean = false,
    val error:String = "",
    val selectedVisualTask:VisualTask? = null
)