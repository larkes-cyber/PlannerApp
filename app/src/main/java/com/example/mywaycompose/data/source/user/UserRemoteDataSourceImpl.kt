package com.example.mywaycompose.data.source.user

import com.example.mywaycompose.data.remote.firebase_model.*
import com.example.mywaycompose.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserRemoteDataSourceImpl(
    private val signInClient: GoogleSignInClient,
    private val database: FirebaseDatabase
): UserRemoteDataSource {

    private val auth = Firebase.auth

    private fun cleanLogin(email:String):String{
        return email.split("@")[0].replace('.','@')
    }
    private val email = if(auth.currentUser != null) cleanLogin(auth.currentUser!!.email!!.split("@")[0]) else ""

    override fun googleAuthRequest(): GoogleSignInClient {
        return signInClient
    }

    override fun firebaseAuthWithGoogle(idToken: String): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        return auth.signInWithCredential(credential)
    }

    override fun getUserAuth(): FirebaseAuth {
        return auth
    }

    override fun <T> getAppData(kind: String): Flow<List<T>> = callbackFlow {
        val database = database.getReference(kind)
        var listener: ValueEventListener? = null
        database.child(email).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listener = this
                when(kind){
                    Constants.TASKS_KIND -> {
                        val list = mutableListOf<FirebaseTask>()
                        for(day in snapshot.children){
                            for(task in day.children){
                                list.add(task.getValue(FirebaseTask::class.java)!!)
                            }
                        }
                        trySend(list as List<T>)
                    }
                    Constants.MAIN_TASKS_KIND -> {
                        val list = mutableListOf<Pair<String, FirebaseMainTask>>()
                        for(task in snapshot.children){
                            list.add(Pair(task.key!!, task.getValue(FirebaseMainTask::class.java)!!))
                        }
                        trySend(list as List<T>)
                    }


                    Constants.PRODUCT_TASKS_KIND -> {
                        val list = mutableListOf<FirebaseProductTask>()
                        for(task in snapshot.children){
                            list.add(task.getValue(FirebaseProductTask::class.java)!!)
                        }
                        trySend(list as List<T>)
                    }

                    Constants.SUBTASK_TASKS_KIND -> {
                        val list = mutableListOf<FirebaseSubtask>()
                        for(subtasks in snapshot.children){
                            for(subtask in subtasks.children){
                                list.add(subtask.getValue(FirebaseSubtask::class.java)!!)
                            }
                        }
                        trySend(list as List<T>)
                    }
                    Constants.START_DAY_USING_KIND -> {
                        trySend(listOf(snapshot.value.toString()) as List<T>)
                    }

                    Constants.TASK_CLASS_DATABASE -> {
                        val out = mutableListOf<FirebaseTaskClass>()
                        for (idea in snapshot.children) {
                            out.add(
                                idea.getValue(FirebaseTaskClass::class.java)!!
                            )
                        }
                        trySend(out as List<T>)
                    }

                    Constants.IDEAS_KIND -> {
                        val out = mutableListOf<FirebaseIdea>()
                        for (idea in snapshot.children) {
                            out.add(
                                idea.getValue(FirebaseIdea::class.java)!!
                            )
                        }
                        trySend(out as List<T>)
                    }
                    Constants.VISUAL_TASK_KIND -> {
                        val list = mutableListOf<FirebaseVisualTask>()
                        for(stat in snapshot.children){
                            list.add(stat.getValue(FirebaseVisualTask::class.java)!!)
                        }
                        trySend(list as List<T>)
                    }
                }
                channel.close()
                database.removeEventListener(this)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        awaitClose {
            if(listener != null) database.removeEventListener(listener!!)
        }
    }

    override fun putStartUsingDate(date: String) {
        database.getReference(Constants.START_DAY_USING_KIND).child(email).setValue(date)
    }

    override fun getStartUsingDate(): Flow<List<String>> {
        return getAppData(Constants.START_DAY_USING_KIND)
    }
}