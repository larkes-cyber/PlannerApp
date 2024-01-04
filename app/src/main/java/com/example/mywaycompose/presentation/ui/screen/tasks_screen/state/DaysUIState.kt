package com.example.mywaycompose.presentation.ui.screen.tasks_screen.state

import com.example.mywaycompose.domain.model.DateServer

data class DaysUIState(
    val days:List<DateServer> = listOf(),
    val isLoading:Boolean = false,
    val error:String = ""
)