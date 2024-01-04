package com.example.mywaycompose.data.source.idea

import com.example.mywaycompose.data.remote.firebase_model.FirebaseIdea

interface IdeaFirebaseDataSource {

    fun pushIdea(idea: FirebaseIdea)
    fun deleteIdea(idea: FirebaseIdea)


}