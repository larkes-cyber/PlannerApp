package com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.state

import com.example.mywaycompose.domain.model.Idea

data class IdeaUIState(
    val ideas:List<Idea> = listOf(),
    val isLoading:Boolean = false,
    val error:String = ""
)