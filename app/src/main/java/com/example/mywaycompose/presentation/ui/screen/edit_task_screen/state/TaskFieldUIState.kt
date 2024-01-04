package com.example.mywaycompose.presentation.ui.screen.edit_task_screen.state

import androidx.compose.ui.text.input.TextFieldValue

data class TaskFieldUIState(
    val titleField:String = "",
    val timeField: TextFieldValue = TextFieldValue(""),
    val date:String = "",
    val grade:Int = 0
)