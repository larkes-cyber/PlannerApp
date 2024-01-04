package com.example.mywaycompose.domain.usecase.idea

import com.example.mywaycompose.domain.model.Idea
import com.example.mywaycompose.domain.repository.FakeIdeaRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UseInsertIdeaTest{

    private lateinit var useInsertIdea:UseInsertIdea
    private lateinit var localIdeasRepository: FakeIdeaRepository

    @Before
    fun setup(){
        localIdeasRepository = FakeIdeaRepository()
        useInsertIdea = UseInsertIdea(localIdeasRepository)
    }

    @Test
    fun `add an idea to local database`(): Unit = runBlocking {

        val idea = Idea(
            id = 0,
            idea = "idea"
        )

        useInsertIdea.execute(idea)



    }

}