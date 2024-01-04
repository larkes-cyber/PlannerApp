package com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywaycompose.domain.model.ProductTask
import com.example.mywaycompose.domain.model.TaskClass
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.domain.usecase.local_service.UseGetActuallyVisualTaskId
import com.example.mywaycompose.domain.usecase.local_service.UsePutActuallyVisualTaskId
import com.example.mywaycompose.domain.usecase.main_task.UseObserveAllMainTasks
import com.example.mywaycompose.domain.usecase.main_task.UseSyncMainTasks
import com.example.mywaycompose.domain.usecase.product_task.UseInsertProductTask
import com.example.mywaycompose.domain.usecase.product_task.UsePushProductTaskFirebase
import com.example.mywaycompose.domain.usecase.remote_service.UseGetAuthFirebaseSession
import com.example.mywaycompose.domain.usecase.remote_service.UsePutActuallyVisualTaskIdToFirebase
import com.example.mywaycompose.domain.usecase.task_class.UseInsertTaskClass
import com.example.mywaycompose.domain.usecase.visual_task.*
import com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.state.MainTasksState
import com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.state.SubclassUIState

import com.example.mywaycompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainTaskListViewModel @Inject constructor(
    private val useObserveAllMainTasks: UseObserveAllMainTasks,
    private val useGetAuthFirebaseSession: UseGetAuthFirebaseSession,
    private val useInsertVisualTaskToDatabase: UseInsertVisualTaskToDatabase,
    private val useGetAllVisualTasksFromDatabase: UseGetAllVisualTasksFromDatabase,
    private val useGetActuallyVisualTaskId: UseGetActuallyVisualTaskId,
    private val usePutActuallyVisualTaskId: UsePutActuallyVisualTaskId,
    private val usePutActuallyVisualTaskIdToFirebase: UsePutActuallyVisualTaskIdToFirebase,
    private val usePushVisualTaskToFirebase: UsePushVisualTaskToFirebase,
    private val useAddNewDependNode: UseAddNewDependNode,
    private val useDeleteVisualTask: UseDeleteVisualTask,
    private val useInsertProductTask: UseInsertProductTask,
    private val useSyncMainTasks: UseSyncMainTasks,
    private val useSyncVisualTasks: UseSyncVisualTasks,
    private val useInsertTaskClass: UseInsertTaskClass
): ViewModel() {

    private val _mainTasksState = mutableStateOf(MainTasksState())
    val mainTasksState = _mainTasksState

    private val _hasBeenSelected = MutableStateFlow<Int?>(null)
    val hasBeenSelected = _hasBeenSelected

    private val _refreshingUiState = MutableStateFlow(false)
    val refreshingUiState: StateFlow<Boolean> = _refreshingUiState

    private val _subclassUIState = MutableStateFlow(SubclassUIState())
    val subclassUIState:StateFlow<SubclassUIState> = _subclassUIState

    val _authSession = useGetAuthFirebaseSession.execute()

    init {
        getMainTasks()
    }

    fun onAddSubclassClick(subclass:String, vsTaskId:String){
        _subclassUIState.value = subclassUIState.value.copy(chosenSubclass = subclass, subclassAlertDialogActive = true, chosenVsTaskId = vsTaskId)
    }
    fun onSubclassTitleChange(subclass:String){
        _subclassUIState.value = subclassUIState.value.copy(chosenSubclass = subclass)
    }
    fun onAddSubclassColorClick(){
        _subclassUIState.value = subclassUIState.value.copy(subclassAlertDialogActive = false, colorPickerAlertDialogActive = true)
    }
    fun onAddSubclassColorChange(color:Long){
        _subclassUIState.value = subclassUIState.value.copy(chosenColor = color, subclassAlertDialogActive = true, colorPickerAlertDialogActive = false)
    }
    fun onSubclassDone(){
        viewModelScope.launch {
            useInsertTaskClass.execute(TaskClass(
                title = subclassUIState.value.chosenSubclass!!,
                color = subclassUIState.value.chosenColor,
                online_sync = false,
                vsTaskId = subclassUIState.value.chosenVsTaskId
            ))
        }
        resetSubclassUIState()
    }

    fun resetSubclassUIState(){
        _subclassUIState.value = SubclassUIState()

    }

    fun syncTasks(){
        useSyncVisualTasks.invoke().launchIn(viewModelScope)
        useSyncMainTasks.invoke().onEach {res ->
            when(res){
                is Resource.Success -> {
                    getMainTasks()
                    _refreshingUiState.value = false
                }
                is Resource.Loading -> {
                    _refreshingUiState.value = true
                }
                is Resource.Error -> {
                    _refreshingUiState.value = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun generateTaskId():Int{
        val id = useGetActuallyVisualTaskId.execute() + 1
        usePutActuallyVisualTaskId.execute(id)
        usePutActuallyVisualTaskIdToFirebase.execute(id)
        return id
    }

    fun deleteNode(visualTask: VisualTask){
        viewModelScope.launch {
            useDeleteVisualTask.execute(visualTask)
        }
    }



    private fun getMainTasks(){
        useObserveAllMainTasks.invoke().onEach { res ->
            when(res){
                is Resource.Loading -> _mainTasksState.value = MainTasksState(isLoading = true)
                is Resource.Success -> {
                    viewModelScope.launch {
                        val tasks = res.data!!.map { task ->
                            var vsTasks = useGetAllVisualTasksFromDatabase.execute(task.id!!)
                            if(vsTasks.isEmpty()) {
                                val firstVsTask = VisualTask(
                                    id = UUID.randomUUID().toString(),
                                    text = task.title,
                                    mainTaskId = task.id!!,
                                    dependIds = mutableListOf(),
                                    parentId = "-1"
                                )
                                usePushVisualTaskToFirebase.execute(firstVsTask)
                                useInsertVisualTaskToDatabase.execute(firstVsTask)
                                vsTasks = listOf(firstVsTask)
                            }
                            MainTaskWithVisualTasks(
                                id = task.id,
                                icon = task.icon,
                                title = task.title,
                                imageSrc = task.imageSrc,
                                idIdea = task.idIdea.toString(),
                                visualIdeas = vsTasks
                            )
                        }
                        _mainTasksState.value = MainTasksState(success = tasks)
                    }

                }
                is Resource.Error -> _mainTasksState.value = MainTasksState(error = res.message!!)
            }
        }.launchIn(viewModelScope)
    }

    fun addNewVisualTask(visualTask: VisualTask){
        viewModelScope.launch {
            useAddNewDependNode.execute(visualTask)
        }
    }

    fun swapToProductTask(visualTask: VisualTask){

        val productTaskId = UUID.randomUUID().toString()

        viewModelScope.launch {
            useInsertProductTask.execute(ProductTask(
                task = visualTask.text,
                goalId = visualTask.mainTaskId,
                id = productTaskId
            ))
        }
    }

    fun movingToMainTaskDetail(id:Int){
        _hasBeenSelected.value = id
    }

}