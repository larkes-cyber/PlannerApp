package com.example.mywaycompose.di.domain

import com.example.mywaycompose.domain.repository.IdeaRepository
import com.example.mywaycompose.domain.repository.UserRepository
import com.example.mywaycompose.domain.usecase.idea.UseInsertIdea
import com.example.mywaycompose.domain.usecase.idea.UseDeleteIdea
import com.example.mywaycompose.domain.usecase.idea.UseObserveIdeas
import com.example.mywaycompose.domain.usecase.idea.UseObserveIdeasFirebase
import com.example.mywaycompose.domain.usecase.idea.UsePushIdeaFirebase
import com.example.mywaycompose.domain.usecase.idea.UseSyncIdeas
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object DomainIdeaModule {

    @Provides
    fun provideAddIdea(ideaRepository: IdeaRepository): UseInsertIdea {
        return UseInsertIdea(
            ideaRepository = ideaRepository
        )
    }

    @Provides
    fun provideGetIdeas(ideaRepository: IdeaRepository): UseObserveIdeas {
        return UseObserveIdeas(ideaRepository = ideaRepository)
    }

    @Provides
    fun provideDeleteIdea(
        ideaRepository: IdeaRepository,
    ): UseDeleteIdea {
        return UseDeleteIdea(ideaRepository)
    }

    @Provides
    fun provideUsePushIdeaFirebase(
        ideaRepository: IdeaRepository
    ): UsePushIdeaFirebase {
        return UsePushIdeaFirebase(ideaRepository)
    }

    @Provides
    fun provideUseGetIdeasFirebase(
        userRepository: UserRepository,
        coroutineScope: CoroutineScope
    ): UseObserveIdeasFirebase {
        return UseObserveIdeasFirebase(userRepository,coroutineScope)
    }

    @Provides
    fun provideUseSyncIdeas(
        ideaRepository: IdeaRepository,
        coroutineScope: CoroutineScope
    ):UseSyncIdeas{
        return UseSyncIdeas(
            ideaRepository = ideaRepository,
            coroutineScope = coroutineScope
        )
    }

}