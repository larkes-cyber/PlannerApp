package com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywaycompose.domain.model.Idea
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.model.Task
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.domain.usecase.idea.UseInsertIdea
import com.example.mywaycompose.domain.usecase.idea.UseObserveIdeas
import com.example.mywaycompose.domain.usecase.local_service.UseGetActuallyVisualTaskId
import com.example.mywaycompose.domain.usecase.local_service.UsePutActuallyVisualTaskId
import com.example.mywaycompose.domain.usecase.main_task.UseInsertShortMainTask
import com.example.mywaycompose.domain.usecase.main_task.UseObserveAllMainTasks
import com.example.mywaycompose.domain.usecase.main_task.UseGetMainTaskId
import com.example.mywaycompose.domain.usecase.remote_service.UseGetAuthFirebaseSession
import com.example.mywaycompose.domain.usecase.remote_service.UsePutActuallyMainTaskId
import com.example.mywaycompose.domain.usecase.remote_service.UsePutActuallyVisualTaskIdToFirebase
import com.example.mywaycompose.domain.usecase.idea.UseDeleteIdea
import com.example.mywaycompose.domain.usecase.idea.UsePushIdeaFirebase
import com.example.mywaycompose.domain.usecase.idea.UseSyncIdeas
import com.example.mywaycompose.domain.usecase.local_service.UseCheckCorrectTime
import com.example.mywaycompose.domain.usecase.main_task.UseInsertMainTask
import com.example.mywaycompose.domain.usecase.main_task.UsePushMainTaskFirebase
import com.example.mywaycompose.domain.usecase.task.UseCheckSameTasks
import com.example.mywaycompose.domain.usecase.task.UseInsertTask
import com.example.mywaycompose.domain.usecase.task_class.UseObserveTaskClasses
import com.example.mywaycompose.domain.usecase.visual_task.UseAddNewDependNode
import com.example.mywaycompose.domain.usecase.visual_task.UseGetAllVisualTasks
import com.example.mywaycompose.domain.usecase.visual_task.UseSyncVisualTasks
import com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.state.IdeaFieldUIState
import com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.state.IdeaPlannerTaskUIState
import com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.state.IdeaUIState
import com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.state.VisualTaskUIState
import com.example.mywaycompose.utils.Constants.SAME_TASK_ERROR
import com.example.mywaycompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PullIdeasViewModel @Inject constructor(
    private val useGetAuthFirebaseSession: UseGetAuthFirebaseSession,
    private val useInsertIdea: UseInsertIdea,
    private val useObserveIdeas: UseObserveIdeas,
    private val useDeleteIdea: UseDeleteIdea,
    private val useGetActuallyVisualTaskId: UseGetActuallyVisualTaskId,
    private val usePutActuallyVisualTaskIdToFirebase: UsePutActuallyVisualTaskIdToFirebase,
    private val usePutActuallyVisualTaskId: UsePutActuallyVisualTaskId,
    private val useGetAllVisualTasks: UseGetAllVisualTasks,
    private val useAddNewDependNode: UseAddNewDependNode,
    private val useInsertMainTask: UseInsertMainTask,
    private val pushMainTaskFirebase: UsePushMainTaskFirebase,
    private val useSyncIdeas: UseSyncIdeas,
    private val useSyncVisualTasks: UseSyncVisualTasks,
    private val useInsertTask: UseInsertTask,
    private val useCheckSameTasks: UseCheckSameTasks,
    private val useObserveTaskClasses: UseObserveTaskClasses
    ):ViewModel() {

    val user = useGetAuthFirebaseSession.execute().currentUser

    private val _ideaUIState = MutableStateFlow(IdeaUIState())
    val ideaUIState:StateFlow<IdeaUIState> = _ideaUIState

    private val _ideaFieldUIState = MutableStateFlow(IdeaFieldUIState())
    val ideaFieldUIState:StateFlow<IdeaFieldUIState> = _ideaFieldUIState

    private val _ideaPlannerTaskUIState = MutableStateFlow(IdeaPlannerTaskUIState())
    val ideaPlannerTaskUIState:StateFlow<IdeaPlannerTaskUIState> = _ideaPlannerTaskUIState

    private val _visualTaskUIState = MutableStateFlow(VisualTaskUIState())
    val visualTaskUIState:StateFlow<VisualTaskUIState> = _visualTaskUIState

    private val _refreshingUiState = MutableStateFlow(false)
    val refreshingUiState:StateFlow<Boolean> = _refreshingUiState


    init {
        observeIdeas()
        observeVisualTasks()
        observeTaskClasses()
    }


    fun syncIdeas(){
        useSyncVisualTasks.invoke().launchIn(viewModelScope)
        useSyncIdeas.invoke().onEach {res ->
            when(res){
                is Resource.Success -> {
                    observeIdeas()
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

    private fun observeIdeas(){
        useObserveIdeas.invoke().onEach { res ->
            when(res){
                is Resource.Loading -> {
                    _ideaUIState.value = IdeaUIState(isLoading = true)
                }
                is Resource.Success -> {
                    _ideaUIState.value = IdeaUIState(ideas = res.data!!)
                }
                is Resource.Error -> {
                    _ideaUIState.value = IdeaUIState(error = res.message!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun observeTaskClasses(){
        viewModelScope.launch {
            val classes = useObserveTaskClasses.execute()
            _ideaPlannerTaskUIState.value = ideaPlannerTaskUIState.value.copy(taskClassesList = classes)
        }
    }

    private fun observeVisualTasks(){
        viewModelScope.launch {
            _visualTaskUIState.value = visualTaskUIState.value.copy(visualTasks = useGetAllVisualTasks.execute())
        }
    }

    fun addIdea(){
        viewModelScope.launch {

            useInsertIdea.execute(Idea(idea = ideaFieldUIState.value.idea))
            observeIdeas()
            switchIdeaFieldActive()
        }
    }

    fun deleteIdea(idea:Idea){
        viewModelScope.launch {
            useDeleteIdea.execute(idea)
            val newIdeasList = ideaUIState.value.ideas.toMutableList()
            newIdeasList.remove(idea)
            _ideaUIState.value = ideaUIState.value.copy(ideas = newIdeasList)
        }
    }

    fun createNewVsTask(){
        viewModelScope.launch {
            useAddNewDependNode.execute(
                VisualTask(
                    id = UUID.randomUUID().toString(),
                    dependIds = mutableListOf(),
                    text = visualTaskUIState.value.visualTaskField,
                    parentId = visualTaskUIState.value.selectedVisualTask!!.id!!,
                    mainTaskId = visualTaskUIState.value.selectedVisualTask!!.mainTaskId
                )
            )
            cleanUpLastEditing()
        }
    }

    fun addMainTask(){
        viewModelScope.launch {

            val mainTaskId = UUID.randomUUID().toString()
            val mainTask =  MainTask(
                title = visualTaskUIState.value.visualTaskField,
                icon = "",
                id = mainTaskId
                )

            useInsertMainTask.execute(mainTask)
            pushMainTaskFirebase.execute(mainTask)

            useAddNewDependNode.execute(
                VisualTask(
                    id = UUID.randomUUID().toString(),
                    dependIds = mutableListOf(),
                    text = visualTaskUIState.value.visualTaskField,
                    parentId = "-1",
                    mainTaskId = mainTaskId
                )
            )
            cleanUpLastEditing()
        }
    }

    fun changeIdeaField(idea:String){
        _ideaFieldUIState.value = ideaFieldUIState.value.copy(idea = idea)
    }

    private fun addTask(){
        viewModelScope.launch {

            val selectedClass = ideaPlannerTaskUIState.value.taskClassesList.find { ideaPlannerTaskUIState.value.selectedClass!! == it.id }

            useInsertTask.execute(Task(
                task = ideaPlannerTaskUIState.value.selectedIdea!!.idea,
                time = ideaPlannerTaskUIState.value.selectedTime!!,
                date = ideaPlannerTaskUIState.value.selectedDate!!,
                status = false,
                grade = 0,
                subtaskTitle = selectedClass?.title,
                subtaskColor = selectedClass?.color.toString()
            ))
            deleteIdea(ideaPlannerTaskUIState.value.selectedIdea!!)
            resetPlannerTaskState()
        }
    }


    fun setTaskClass(title:String){
        _ideaPlannerTaskUIState.value = ideaPlannerTaskUIState.value.copy(selectedClass = title)
    }
    fun resetPlannerTaskState(){
        _ideaPlannerTaskUIState.value = IdeaPlannerTaskUIState(ideaPlannerTaskUIState.value.taskClassesList)
    }
    fun setPlannerIdea(idea:Idea){
        _ideaPlannerTaskUIState.value = ideaPlannerTaskUIState.value.copy(selectedIdea = idea)
    }
    fun setPlannerIdeaDate(date:String){
        _ideaPlannerTaskUIState.value = ideaPlannerTaskUIState.value.copy(selectedDate = date)
    }
    fun setPlannerIdeaTime(time:String){
        viewModelScope.launch {
            val res = useCheckSameTasks.execute(time = time,date = ideaPlannerTaskUIState.value.selectedDate!!)
            _ideaPlannerTaskUIState.value = if(res) ideaPlannerTaskUIState.value.copy(timeError = SAME_TASK_ERROR) else ideaPlannerTaskUIState.value.copy(selectedTime = time)
            if(!res) addTask()
        }
    }
    fun switchIdeaFieldActive(){
        viewModelScope.launch {
            _ideaFieldUIState.value = IdeaFieldUIState(active = !_ideaFieldUIState.value.active)
        }
    }

    fun setTransitionIdea(idea:Idea?){
        _ideaFieldUIState.value = ideaFieldUIState.value.copy(transitionIdea = idea)
    }

    fun setCurrentVisualTaskNode(visualTask: VisualTask?){
        _visualTaskUIState.value = visualTaskUIState.value.copy(selectedVisualTask = visualTask)
    }

    fun onVisualTaskFieldChange(visualTask: String){
        _visualTaskUIState.value = visualTaskUIState.value.copy(visualTaskField = visualTask)
    }


    private fun cleanUpLastEditing(){
        onVisualTaskFieldChange("")
        deleteIdea(ideaFieldUIState.value.transitionIdea!!)
        setTransitionIdea(null)
        observeVisualTasks()
    }




    fun generateTaskId():Int{
        val id = useGetActuallyVisualTaskId.execute() + 1
        usePutActuallyVisualTaskId.execute(id)
        usePutActuallyVisualTaskIdToFirebase.execute(id)
        return id
    }




}