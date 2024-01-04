package com.example.mywaycompose.presentation.ui.screen.auth_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywaycompose.domain.usecase.idea.UseInsertIdea
import com.example.mywaycompose.domain.usecase.idea.UseObserveIdeasFirebase
import com.example.mywaycompose.domain.usecase.idea.UseSyncIdeas
import com.example.mywaycompose.domain.usecase.local_service.UseNukeDatabase
import com.example.mywaycompose.domain.usecase.local_service.UseGetImageFileById
import com.example.mywaycompose.domain.usecase.local_service.UsePutActuallyVisualTaskId
import com.example.mywaycompose.domain.usecase.local_service.UseSaveFirstDate
import com.example.mywaycompose.domain.usecase.main_task.UseInsertShortMainTask
import com.example.mywaycompose.domain.usecase.main_task.UseObserveMainTaskFirebase
import com.example.mywaycompose.domain.usecase.main_task.UseSaveFirebaseImage
import com.example.mywaycompose.domain.usecase.main_task.UseSyncMainTasks
import com.example.mywaycompose.domain.usecase.product_task.UseObserveProductTasksFirebase
import com.example.mywaycompose.domain.usecase.product_task.UseInsertProductTask
import com.example.mywaycompose.domain.usecase.product_task.UseSyncProductTasks
import com.example.mywaycompose.domain.usecase.remote_service.UseGetAuthFirebaseSession
import com.example.mywaycompose.domain.usecase.remote_service.UseGetCurrentIdByModelKind
import com.example.mywaycompose.domain.usecase.remote_service.UseGetStartUsingDate
import com.example.mywaycompose.domain.usecase.remote_service.UsePutActuallyMainTaskId
import com.example.mywaycompose.domain.usecase.remote_service.UsePutStartUsingDateToFirebase

import com.example.mywaycompose.domain.usecase.task.UseInsertTask
import com.example.mywaycompose.domain.usecase.task.UseObserveTasksFirebase
import com.example.mywaycompose.domain.usecase.task.UseSyncTasks
import com.example.mywaycompose.domain.usecase.task_class.UseSyncTaskClasses
import com.example.mywaycompose.domain.usecase.visual_task.UseGetVisualTasksFromFirebase
import com.example.mywaycompose.domain.usecase.visual_task.UseInsertVisualTaskToDatabase
import com.example.mywaycompose.domain.usecase.visual_task.UseSyncVisualTasks
import com.example.mywaycompose.presentation.ui.screen.auth_screen.state.SplashScreenState
import com.example.mywaycompose.utils.Constants.LONG_TASK_ID_KIND
import com.example.mywaycompose.utils.Constants.MAIN_TASK_ID_KIND
import com.example.mywaycompose.utils.Constants.SUBTASK_ID_KIND
import com.example.mywaycompose.utils.Constants.TASK_STAT_ID_KIND
import com.example.mywaycompose.utils.Constants.VISUAL_TASK_ID_KIND
import com.example.mywaycompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useGetAuthFirebaseSession:UseGetAuthFirebaseSession,
    private val useSyncProductTasks: UseSyncProductTasks,
    private val useSyncMainTasks: UseSyncMainTasks,
    private val useSyncIdeas: UseSyncIdeas,
    private val useSyncVisualTasks: UseSyncVisualTasks,
    private val useSyncTasks: UseSyncTasks,
    private val useSyncTaskClasses: UseSyncTaskClasses
):ViewModel() {

    private val tasksHasBeenLoaded = savedStateHandle.getStateFlow("tasks_status",false)
    private val mainTasksHasBeenLoaded = savedStateHandle.getStateFlow("main_tasks_status",false)
    private val ideasHasBeenLoaded = savedStateHandle.getStateFlow("ideas_status",false)
    private val visualTasksHasBeenLoaded = savedStateHandle.getStateFlow("visual_tasks",false)
    private val productTasksHasBeenLoaded = savedStateHandle.getStateFlow("product_tasks", false)
    private val taskClassesHasBeenLoaded = savedStateHandle.getStateFlow("task_class", false)

    val state = combine(
        tasksHasBeenLoaded,
        mainTasksHasBeenLoaded,
        ideasHasBeenLoaded,
        visualTasksHasBeenLoaded,
        productTasksHasBeenLoaded,
        taskClassesHasBeenLoaded
    ){ props ->
        SplashScreenState(
            tasksHasBeenLoaded = props[0],
            mainTasksHasBeenLoaded = props[1],
            ideasHasBeenLoaded = props[2],
            visualTasksHasBeenLoaded = props[3],
            productTasksHasBeenLoaded = props[4],
            taskClassesHasBeenLoaded = props[5]
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SplashScreenState())

    private val _firstLaunch = useGetAuthFirebaseSession.execute().currentUser == null
    val firstLaunch = _firstLaunch


    init {
    }

    private fun setTasksLoadingDone(){
        savedStateHandle["tasks_status"] = true
    }

    private fun setMainTasksLoadingDone(){
        savedStateHandle["main_tasks_status"] = true
    }
    private fun setIdeasLoadingDone(){
        savedStateHandle["ideas_status"] = true
    }



    fun loadTasks(){
        useSyncTasks.invoke().onEach { res ->
            when(res){
                is Resource.Success -> {
                    setTasksLoadingDone()
                }
                is Resource.Error -> {
                    setTasksLoadingDone()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadMainTasks(){
        useSyncMainTasks.invoke().onEach { res ->
            when(res){
                is Resource.Success -> {
                    setMainTasksLoadingDone()
                }
                is Resource.Error -> {
                    setMainTasksLoadingDone()
                }
            }
        }.launchIn(viewModelScope)
    }


    fun loadIdeas(){
        useSyncIdeas.invoke().onEach { res ->
            when(res){
                is Resource.Success -> {
                    setIdeasLoadingDone()
                }
                is Resource.Error -> {
                    setIdeasLoadingDone()
                }
            }
        }.launchIn(viewModelScope)
    }




    fun loadVisualTasks(){
        useSyncVisualTasks.invoke().onEach {res ->
            when(res){
                is Resource.Success -> {
                    savedStateHandle["visual_tasks"] = true
                }
                is Resource.Error -> {
                    savedStateHandle["visual_tasks"] = true
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadProductTasks(){
        useSyncProductTasks.invoke().onEach { res ->
            when(res){
                is Resource.Success -> {
                    savedStateHandle["product_tasks"] = true
                }
                is Resource.Error -> {
                    savedStateHandle["product_tasks"] = true
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadTaskClasses(){
        useSyncTaskClasses.invoke().onEach {res ->
            when(res){
                is Resource.Success -> {
                    savedStateHandle["task_class"] = true
                }
                is Resource.Error -> {
                    savedStateHandle["task_class"] = true
                }
            }
        }.launchIn(viewModelScope)
    }

}