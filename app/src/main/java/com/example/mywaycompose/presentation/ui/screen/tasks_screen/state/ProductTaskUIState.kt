package com.example.mywaycompose.presentation.ui.screen.tasks_screen.state

import com.example.mywaycompose.domain.model.ProductTask

data class ProductTaskUIState(
    val productTasks:List<ProductTask> = listOf(),
    val isLoading:Boolean = false,
    val error:String = "",
    val activeTask:ProductTask? = null
)