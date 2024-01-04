package com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.state

data class SubclassUIState(
    val chosenColor:Long = 0xFF000000,
    val chosenSubclass:String? = null,
    val subclassAlertDialogActive:Boolean = false,
    val colorPickerAlertDialogActive:Boolean = false,
    val chosenVsTaskId:String = ""
)