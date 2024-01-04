package com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.state

import com.example.mywaycompose.domain.model.Idea
import com.example.mywaycompose.domain.model.TaskClass

data class IdeaPlannerTaskUIState(
    val taskClassesList:List<TaskClass> = listOf(),
    val selectedDate:String? = null,
    val selectedTime:String? = null,
    val selectedIdea:Idea? = null,
    val timeError:String = "",
    val selectedClass:String? = null
)