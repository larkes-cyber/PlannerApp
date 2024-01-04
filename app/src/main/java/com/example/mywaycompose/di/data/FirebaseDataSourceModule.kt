package com.example.mywaycompose.di.data

import com.example.mywaycompose.data.source.idea.IdeaFirebaseDataSource
import com.example.mywaycompose.data.source.idea.IdeaFirebaseDataSourceImpl
import com.example.mywaycompose.data.source.main_task.MainTaskFirebaseDataSource
import com.example.mywaycompose.data.source.main_task.MainTaskFirebaseDataSourceImpl
import com.example.mywaycompose.data.source.product_task.ProductTaskFirebaseDataSource
import com.example.mywaycompose.data.source.product_task.ProductTaskFirebaseDataSourceImpl
import com.example.mywaycompose.data.source.task.TaskFirebaseDataSource
import com.example.mywaycompose.data.source.task.TaskFirebaseDataSourceImpl
import com.example.mywaycompose.data.source.visual_task.VisualTaskFirebaseDataSource
import com.example.mywaycompose.data.source.visual_task.VisualTaskFirebaseDataSourceImpl
import com.example.mywaycompose.data.remote.firebase_model.FirebaseUser
import com.example.mywaycompose.data.source.service.ServiceRemoteDataSource
import com.example.mywaycompose.data.source.service.ServiceRemoteDataSourceImpl
import com.example.mywaycompose.data.source.task_class.TaskClassRemoteDataSource
import com.example.mywaycompose.data.source.task_class.TaskClassRemoteDataSourceImpl
import com.example.mywaycompose.data.source.user.UserRemoteDataSource
import com.example.mywaycompose.data.source.user.UserRemoteDataSourceImpl
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object FirebaseDataSourceModule {

    private fun cleanLogin(email:String):String{
        return email.split("@")[0].replace('.','@')
    }

    @Provides
    fun provideFirebaseUser(): FirebaseUser {
        val auth = Firebase.auth
        val email = if(auth.currentUser != null) cleanLogin(auth.currentUser!!.email!!.split("@")[0]) else ""
        return FirebaseUser(email = email)
    }

    @Provides
    fun provideTaskFirebaseDataSource(
        database: FirebaseDatabase,
        firebaseUser: FirebaseUser
    ):TaskFirebaseDataSource = TaskFirebaseDataSourceImpl(
        database = database,
        firebaseUser = firebaseUser
    )

    @Provides
    fun provideMainTaskFirebaseDataSource(
        database: FirebaseDatabase,
        firebaseUser: FirebaseUser,
        firebaseStorageReference: StorageReference
    ):MainTaskFirebaseDataSource = MainTaskFirebaseDataSourceImpl(
        database = database,
        firebaseUser = firebaseUser,
        firebaseStorageReference = firebaseStorageReference
    )

    @Provides
    fun provideIdeaFirebaseDataSource(
        database: FirebaseDatabase,
        firebaseUser: FirebaseUser
    ):IdeaFirebaseDataSource = IdeaFirebaseDataSourceImpl(
        database = database,
        firebaseUser = firebaseUser
    )

    @Provides
    fun provideProductTaskFirebaseDataSource(
        database: FirebaseDatabase,
        firebaseUser: FirebaseUser
    ): ProductTaskFirebaseDataSource = ProductTaskFirebaseDataSourceImpl(
        database = database,
        firebaseUser = firebaseUser
    )

    @Provides
    fun provideVisualTaskFirebaseDataSource(
        database: FirebaseDatabase,
        firebaseUser: FirebaseUser
    ): VisualTaskFirebaseDataSource = VisualTaskFirebaseDataSourceImpl(
        database = database,
        firebaseUser = firebaseUser
    )


    @Provides
    fun provideServiceRemoteDataSource(
        signInClient: GoogleSignInClient,
        database: FirebaseDatabase,
        firebaseStorageReference: StorageReference
    ): ServiceRemoteDataSource = ServiceRemoteDataSourceImpl(
        signInClient = signInClient,
        database = database,
        firebaseStorageReference = firebaseStorageReference
    )

    @Provides
    fun provideUserRemoteDataSource(
        signInClient: GoogleSignInClient,
        database: FirebaseDatabase
    ): UserRemoteDataSource = UserRemoteDataSourceImpl(
        signInClient = signInClient,
        database = database
    )

    @Provides
    fun provideTaskClassRemoteDataSource(
        database: FirebaseDatabase,
        firebaseUser: FirebaseUser
    ):TaskClassRemoteDataSource{
        return TaskClassRemoteDataSourceImpl(
            database = database,
            firebaseUser = firebaseUser
        )
    }

}