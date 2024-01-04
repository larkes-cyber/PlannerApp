package com.example.mywaycompose.di.domain

import com.example.mywaycompose.domain.repository.ServiceRepository
import com.example.mywaycompose.domain.repository.UserRepository
import com.example.mywaycompose.domain.usecase.local_service.UseCheckCorrectTime
import com.example.mywaycompose.domain.usecase.local_service.UseCompareDateWithCurrent
import com.example.mywaycompose.domain.usecase.local_service.UseGetActualityDate
import com.example.mywaycompose.domain.usecase.local_service.UseGetAppTheme
import com.example.mywaycompose.domain.usecase.local_service.UseGetFirstDate
import com.example.mywaycompose.domain.usecase.local_service.UseGetImageFileById
import com.example.mywaycompose.domain.usecase.local_service.UseGetListOfMonthDays
import com.example.mywaycompose.domain.usecase.local_service.UseGetTypeOfImage
import com.example.mywaycompose.domain.usecase.local_service.UseSaveAppTheme
import com.example.mywaycompose.domain.usecase.local_service.UseSaveFirstDate
import com.example.mywaycompose.domain.usecase.local_service.UseSaveImage
import com.example.mywaycompose.domain.usecase.main_task.UseGetMainTaskId
import com.example.mywaycompose.domain.usecase.remote_service.UseAuthGoogleWithFirebase
import com.example.mywaycompose.domain.usecase.remote_service.UseGetAuthFirebaseSession
import com.example.mywaycompose.domain.usecase.remote_service.UseGetCurrentIdByModelKind
import com.example.mywaycompose.domain.usecase.remote_service.UseGetGoogleSignInSetup
import com.example.mywaycompose.domain.usecase.remote_service.UseGetStartUsingDate
import com.example.mywaycompose.domain.usecase.remote_service.UsePutActuallyMainTaskId
import com.example.mywaycompose.domain.usecase.remote_service.UsePutStartUsingDateToFirebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object DomainSerivceModule {
    @Provides
    fun provideUseGetActualityDate(): UseGetActualityDate {
        return UseGetActualityDate()
    }

    @Provides
    fun provideUseGetListOfMonthDays(
        serviceRepository: ServiceRepository
    ): UseGetListOfMonthDays {
        return UseGetListOfMonthDays(
            serviceRepository = serviceRepository
        )
    }

    @Provides
    fun provideUseCheckCorrectTime(
        serviceRepository: ServiceRepository
    ): UseCheckCorrectTime {
        return UseCheckCorrectTime(serviceRepository = serviceRepository)
    }

    @Provides
    fun provideUseCompareDateWithCurrent(serviceRepository: ServiceRepository): UseCompareDateWithCurrent {
        return UseCompareDateWithCurrent(serviceRepository)
    }

    @Provides
    fun provideUsePutActuallyMainTaskId(
        serviceRepository: ServiceRepository
    ): UsePutActuallyMainTaskId {
        return UsePutActuallyMainTaskId(serviceRepository)
    }

    @Provides
    fun provideUseGetMainTaskId(
        serviceRepository: ServiceRepository
    ): UseGetMainTaskId {
        return UseGetMainTaskId(serviceRepository)
    }

    @Provides
    fun provideUseSaveFirstDate(serviceRepository: ServiceRepository): UseSaveFirstDate {
        return UseSaveFirstDate(serviceRepository)
    }

    @Provides
    fun provideUseGetFirstDate(serviceRepository: ServiceRepository): UseGetFirstDate {
        return UseGetFirstDate(serviceRepository)
    }

    @Provides
    fun provideUseGetTypeOfImage(serviceRepository: ServiceRepository): UseGetTypeOfImage {
        return UseGetTypeOfImage(serviceRepository)
    }

    @Provides
    fun provideUseSaveImage(serviceRepository: ServiceRepository): UseSaveImage {
        return UseSaveImage(serviceRepository)
    }
    @Provides
    fun provideUseGetImageFileById(serviceRepository: ServiceRepository): UseGetImageFileById {
        return UseGetImageFileById(serviceRepository)
    }

    @Provides
    fun provideUseSaveAppTheme(serviceRepository: ServiceRepository): UseSaveAppTheme {
        return UseSaveAppTheme(serviceRepository)
    }

    @Provides
    fun provideUseGetAppTheme(serviceRepository: ServiceRepository): UseGetAppTheme {
        return UseGetAppTheme(serviceRepository)
    }

    @Provides
    fun provideUseGetGoogleSignInSetup(
        userRepository: UserRepository
    ): UseGetGoogleSignInSetup {
        return UseGetGoogleSignInSetup(
            userRepository
        )
    }

    @Provides
    fun provideUseAuthGoogleWithFirebase(
        userRepository: UserRepository
    ): UseAuthGoogleWithFirebase {
        return UseAuthGoogleWithFirebase(userRepository)
    }

    @Provides
    fun provideUseGetFirebaseAuthSession(
        userRepository: UserRepository
    ): UseGetAuthFirebaseSession {
        return UseGetAuthFirebaseSession(userRepository)
    }



    @Provides
    fun provideUseGetCurrentIdByModel(
        serviceRepository: ServiceRepository,
        coroutineScope: CoroutineScope
    ): UseGetCurrentIdByModelKind {
        return UseGetCurrentIdByModelKind(
            serviceRepository = serviceRepository,
            coroutineScope = coroutineScope
        )
    }

    @Provides
    fun provideUsePutStartUsingDate(
        serviceRepository: ServiceRepository,
        userRepository: UserRepository
    ): UsePutStartUsingDateToFirebase {
        return UsePutStartUsingDateToFirebase(
            serviceRepository = serviceRepository,
            userRepository = userRepository
        )
    }

    @Provides
    fun provideUseGetStartUsingDate(
        userRepository: UserRepository,
        coroutineScope: CoroutineScope
    ): UseGetStartUsingDate {
        return UseGetStartUsingDate(
            userRepository = userRepository,
            coroutineScope = coroutineScope
        )
    }

}