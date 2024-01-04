package com.example.mywaycompose.data.repository

import com.example.mywaycompose.data.local.database.entity.IdeaEntity
import com.example.mywaycompose.data.remote.firebase_model.FirebaseIdea
import com.example.mywaycompose.data.remote.firebase_model.FirebaseTask
import com.example.mywaycompose.data.source.idea.IdeaFirebaseDataSource
import com.example.mywaycompose.data.source.idea.IdeasRoomDataSource
import com.example.mywaycompose.data.source.user.UserRemoteDataSource
import com.example.mywaycompose.domain.mapper.toFirebaseIdea
import com.example.mywaycompose.domain.mapper.toFirebaseTask
import com.example.mywaycompose.domain.mapper.toIdea
import com.example.mywaycompose.domain.mapper.toIdeaEntity
import com.example.mywaycompose.domain.mapper.toTask
import com.example.mywaycompose.domain.mapper.toTaskEntity
import com.example.mywaycompose.domain.repository.IdeaRepository
import com.example.mywaycompose.utils.Constants
import com.example.mywaycompose.utils.InternetConnectionService
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DataIdeaRepository(
    private val ideasRoomDataSource: IdeasRoomDataSource,
    private val ideaFirebaseDataSource: IdeaFirebaseDataSource,
    private val internetConnectionService: InternetConnectionService,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val coroutineScope: CoroutineScope
): IdeaRepository {
    override suspend fun addIdea(idea: IdeaEntity) {
        if(internetConnectionService.isOnline()){
            ideaFirebaseDataSource.pushIdea(idea.toIdea().toFirebaseIdea())
            idea.online_sync = true
        }else{
            idea.online_sync = false
        }
        ideasRoomDataSource.insertIdea(idea)
    }

    override suspend fun getIdeas(): List<IdeaEntity> {
        return ideasRoomDataSource.observeIdeas()
    }

    override suspend fun deleteIdea(idea: IdeaEntity) {
        if(internetConnectionService.isOnline()){
            ideaFirebaseDataSource.deleteIdea(idea.toIdea().toFirebaseIdea())
            ideasRoomDataSource.deleteIdea(idea)
        }else{
            idea.hide = true
            ideasRoomDataSource.insertIdea(idea)
        }
    }

    override fun pushIdeaFirebase(idea: FirebaseIdea) {
        ideaFirebaseDataSource.pushIdea(idea)
    }

    override fun deleteIdeaFirebase(idea: FirebaseIdea) {
        ideaFirebaseDataSource.deleteIdea(idea)
    }

    override fun syncIdeas(): Flow<Resource<String>> = callbackFlow {
        if(internetConnectionService.isOnline()){
            val allIdeas = getIdeas()
            allIdeas.forEach {
                if(it.hide){
                    ideaFirebaseDataSource.deleteIdea(it.toIdea().toFirebaseIdea())
                    ideasRoomDataSource.deleteIdea(it.toIdea().toIdeaEntity())
                }
                if(!it.hide && !it.online_sync){
                    ideaFirebaseDataSource.pushIdea(it.toIdea().toFirebaseIdea())
                }
            }
            val serverIdeas = userRemoteDataSource.getAppData<FirebaseIdea>(Constants.IDEAS_KIND)
            ideasRoomDataSource.nukeIdeaDatabase()
            serverIdeas.onEach {ideas ->
                ideas.forEach {
                    val idea = it.toIdea()
                    idea.online_sync = true
                    ideasRoomDataSource.insertIdea(idea.toIdeaEntity())
                }
                trySend(Resource.Success("OK"))
                channel.close()
            }.launchIn(coroutineScope)
        }else{
            trySend(Resource.Error(Constants.NO_INTERNET))
        }
        awaitClose {   }
    }


}

