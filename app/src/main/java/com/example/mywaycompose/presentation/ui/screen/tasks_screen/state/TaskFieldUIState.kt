package com.example.mywaycompose.presentation.ui.screen.tasks_screen.state

import androidx.compose.ui.text.input.TextFieldValue

data class TaskFieldUIState(
    val active:Boolean = false,
    val task:String = "",
    val time:TextFieldValue = TextFieldValue(""),
    val error:String = ""
)