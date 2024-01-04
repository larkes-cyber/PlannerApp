package com.example.mywaycompose.presentation.ui.screen.edit_main_task.state

import android.net.Uri

data class MainTaskFieldUIState(
    val titleField:String = "",
    val iconField:String = "",
    val imageSrc:Uri? = null
)