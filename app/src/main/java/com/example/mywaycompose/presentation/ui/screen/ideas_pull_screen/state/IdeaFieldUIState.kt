package com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.state

import com.example.mywaycompose.domain.model.Idea

data class IdeaFieldUIState(
    val idea:String = "",
    val active:Boolean = false,
    val transitionIdea: Idea? = null
)