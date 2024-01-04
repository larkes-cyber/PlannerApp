package com.example.mywaycompose.presentation.ui.screen.edit_main_task.state

import android.net.Uri
import com.example.mywaycompose.domain.model.MainTask

data class MainTaskUIState(
    val mainTask:MainTask? = null,
    val isLoading:Boolean = false,
    val error:String = ""
)