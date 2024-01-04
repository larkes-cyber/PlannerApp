package com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen

import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.model.VisualTask

class MainTaskWithVisualTasks(
    var id:String,
    val title:String,
    val icon:String?,
    var imageSrc:String?,
    var doubts:Boolean = false,
    var idIdea:String? = null,
    val visualIdeas:List<VisualTask> = listOf()
)

