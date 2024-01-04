package com.example.mywaycompose.data.source.idea

import com.example.mywaycompose.data.remote.firebase_model.FirebaseIdea
import com.example.mywaycompose.data.remote.firebase_model.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class IdeaFirebaseDataSourceImpl(
    private val database: FirebaseDatabase,
    private val firebaseUser: FirebaseUser
):IdeaFirebaseDataSource {
    private val ideasDatabaseReference = database.getReference("ideas_database")
    override fun pushIdea(idea: FirebaseIdea) {
        ideasDatabaseReference.child(firebaseUser.email).child(idea.idea!!).setValue(idea)
    }

    override fun deleteIdea(idea: FirebaseIdea) {
        ideasDatabaseReference.child(firebaseUser.email).child(idea.idea!!).removeValue()
    }
}