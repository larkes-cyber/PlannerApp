package com.example.mywaycompose.domain.mapper

import com.example.mywaycompose.data.local.database.entity.IdeaEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseIdea
import com.example.mywaycompose.domain.model.Idea

fun Idea.toIdeaEntity(): IdeaEntity {
    return IdeaEntity(
        id = this.id,
        idea = this.idea,
        online_sync = online_sync,
        hide = hide
    )
}

fun IdeaEntity.toIdea(): Idea {
    return Idea(
        id = this.id,
        idea = this.idea,
        online_sync = online_sync,
        hide = hide
    )
}

fun FirebaseIdea.toIdea():Idea{
    return Idea(
        id = id,
        idea = idea!!
    )
}

fun Idea.toFirebaseIdea(): FirebaseIdea {
    return FirebaseIdea(
        id = id,
        idea = idea
    )
}
