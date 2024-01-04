package com.example.mywaycompose.data.source.service

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
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ServiceRemoteDataSourceImpl(
    private val signInClient: GoogleSignInClient,
    private val database: FirebaseDatabase,
    private val firebaseStorageReference: StorageReference
):ServiceRemoteDataSource {

    private val auth = Firebase.auth
    private val idsDatabaseReference = database.getReference("ids")
    private val mainTasksImageStorageReference = firebaseStorageReference.child("main_tasks_image_storage")


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

    override fun getUserAuth(): FirebaseAuth = auth

    override fun putActuallyMainTaskId(id: Int){
        idsDatabaseReference.child(email).child("main_task_id").setValue(id)
    }

    override fun deletePhotoById(id: String) {
        mainTasksImageStorageReference.child(email).child(id).delete()
    }

    override fun getImageReference(id: String): StorageReference {
        return mainTasksImageStorageReference.child(email).child(id)
    }

    override fun getCurrentIdBySomeModel(kind: String): Flow<Int> = callbackFlow{
        var listener: ValueEventListener? = null
        idsDatabaseReference.child(email).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listener = this
                var fl = true
                for (i in snapshot.children) {
                    val key = i.key
                    if (key == kind) {
                        fl = false
                        trySend(i.value.toString().toInt())
                        channel.close()
                        idsDatabaseReference.removeEventListener(this)
                    }
                }
                if(fl){
                    trySend(0)
                    channel.close()
                    idsDatabaseReference.removeEventListener(this)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        awaitClose {
            if(listener != null) idsDatabaseReference.removeEventListener(listener!!)
        }
    }

    override fun putActuallyVisualTaskId(id: Int) {
        idsDatabaseReference.child(email).child(Constants.VISUAL_TASK_ID_KIND).setValue(id)
    }
}