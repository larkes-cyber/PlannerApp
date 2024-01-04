package com.example.mywaycompose.domain.usecase.idea

import com.example.mywaycompose.domain.mapper.toIdea
import com.example.mywaycompose.domain.model.Idea
import com.example.mywaycompose.domain.repository.IdeaRepository
import com.example.mywaycompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class UseObserveIdeas(
    private val ideaRepository: IdeaRepository
) {

    operator fun invoke():Flow<Resource<List<Idea>>> = flow {
        emit(Resource.Loading())
        try {
            val ideas = ideaRepository.getIdeas()
            emit(Resource.Success(ideas.map { it.toIdea() }))
        }catch (e:Exception){
            emit(Resource.Error(e.message.toString()))
        }

    }

}