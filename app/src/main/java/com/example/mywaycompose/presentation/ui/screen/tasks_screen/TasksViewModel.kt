package com.example.mywaycompose.presentation.ui.screen.tasks_screen


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywaycompose.domain.model.*
import com.example.mywaycompose.domain.usecase.idea.UseInsertIdea
import com.example.mywaycompose.domain.usecase.local_service.UseCheckCorrectTime
import com.example.mywaycompose.domain.usecase.local_service.UseGetActualityDate
import com.example.mywaycompose.domain.usecase.local_service.UseGetListOfMonthDays
import com.example.mywaycompose.domain.usecase.product_task.UseObserveProductTasks
import com.example.mywaycompose.domain.usecase.remote_service.UseGetAuthFirebaseSession
import com.example.mywaycompose.domain.usecase.task.UseInsertTask
import com.example.mywaycompose.domain.usecase.task.UseDeleteTask
import com.example.mywaycompose.domain.usecase.task.UseObserveTasks
import com.example.mywaycompose.domain.usecase.task.UseCheckSameTasks
import com.example.mywaycompose.domain.usecase.task.UsePushTaskFirebase
import com.example.mywaycompose.domain.usecase.task.UseSyncTasks
import com.example.mywaycompose.domain.usecase.task.UseToCompleteTask
import com.example.mywaycompose.presentation.ui.screen.tasks_screen.state.*
import com.example.mywaycompose.utils.Constants.SAME_TASK_ERROR
import com.example.mywaycompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class TasksViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useGetActualityDate: UseGetActualityDate,
    private val useGetListOfMonthDays: UseGetListOfMonthDays,
    private val useDeleteTask: UseDeleteTask,
    private val useObserveTasks: UseObserveTasks,
    private val useInsertTask: UseInsertTask,
    private val useToCompleteTask: UseToCompleteTask,
    private val useInsertIdea: UseInsertIdea,
    private val useGetAuthFirebaseSession: UseGetAuthFirebaseSession,
    private val useCheckSameTasks: UseCheckSameTasks,
    private val useObserveProductTasks: UseObserveProductTasks,
    private val useSyncTasks: UseSyncTasks
    ):ViewModel() {


    private val _taskUIState = MutableStateFlow(TaskUiState())
    val taskUIState:StateFlow<TaskUiState> = _taskUIState

    private val _activeDayUIState = MutableStateFlow<DateServer?>(null)
    val activeDayUIState:StateFlow<DateServer?> = _activeDayUIState

    private val _daysUIState = MutableStateFlow(DaysUIState())
    val daysUIState:StateFlow<DaysUIState> = _daysUIState

    private val _taskFieldUIState = MutableStateFlow(TaskFieldUIState())
    val taskFieldUIState:StateFlow<TaskFieldUIState> = _taskFieldUIState

    private val _productTaskUIState = MutableStateFlow(ProductTaskUIState())
    val productTaskUIState:StateFlow<ProductTaskUIState> = _productTaskUIState

    private val _refreshingUiState = MutableStateFlow(false)
    val refreshingUiState:StateFlow<Boolean> = _refreshingUiState

    private val _editTask = MutableStateFlow<Int?>(null)
    val editTask = _editTask

    val authSession = useGetAuthFirebaseSession.execute().currentUser


    init {
        val date = savedStateHandle.get<String>("date")
        if(date == "null") setActiveDate()
        else{
            _activeDayUIState.value = date!!.toDateServer()
            observeTasks()
            observeDays()
        }
        observeProductTasks()
    }

    private fun syncTasks(){
        useSyncTasks.invoke().onEach {res ->
            when(res){
                is Resource.Success -> {
                    observeTasks()
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

    private fun observeTasks(){
        viewModelScope.launch {
            useObserveTasks.invoke(activeDayUIState.value!!.toDateString()).onEach { res ->
                when(res){
                    is Resource.Success -> {
                        _taskUIState.value = TaskUiState(tasks = res.data!!)
                    }
                    is Resource.Loading -> {
                        _taskUIState.value = TaskUiState(isLoading = true)
                    }
                    is Resource.Error -> {
                        _taskUIState.value = TaskUiState(error = res.message!!)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun observeDays(date:DateServer = activeDayUIState.value!!){
        viewModelScope.launch {
            _daysUIState.value = DaysUIState(isLoading = true)
            _daysUIState.value = DaysUIState(days = useGetListOfMonthDays.execute(date).map { it.first })
        }
    }

    private fun observeProductTasks(){
        useObserveProductTasks.invoke().onEach {res ->
            when(res) {
                is Resource.Success -> {
                    _productTaskUIState.value = ProductTaskUIState(productTasks = res.data!!)
                }
                is Resource.Loading -> {
                    _productTaskUIState.value = ProductTaskUIState(isLoading = true)
                }
                is Resource.Error -> {
                    _productTaskUIState.value = ProductTaskUIState(error = res.message!!)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refreshTasks(){
        syncTasks()
    }
    fun setActiveDate(date:DateServer = useGetActualityDate.execute()){
        _activeDayUIState.value = date
        observeTasks()
        observeDays()
    }


    fun onTaskFieldChange(task:String){
        _taskFieldUIState.value = taskFieldUIState.value.copy(task = task)
    }
    fun onTimeFieldChange(time:TextFieldValue){
        _taskFieldUIState.value = taskFieldUIState.value.copy(time = time)
    }

    private suspend fun checkCorrectTask(task:Task):Boolean{
        val correctForm = task.checkCorrectTask()
        val sameTask = useCheckSameTasks.execute(time = task.time, date = task.date)
        _taskFieldUIState.value = taskFieldUIState.value.copy(error = correctForm)
        if(sameTask) _taskFieldUIState.value = taskFieldUIState.value.copy(error = SAME_TASK_ERROR  )
        return correctForm.isEmpty() && !sameTask

    }
    fun addTask(){
        val task = Task(
            task = taskFieldUIState.value.task,
            time = taskFieldUIState.value.time.text.split(" ").filter { it.isNotEmpty() }.joinToString(separator = ":"),
            date = activeDayUIState.value!!.toDateString(),
            status = false,
            grade = 0
        )

        viewModelScope.launch {
            val correct = checkCorrectTask(task)
            if(correct){
                viewModelScope.launch {
                    _productTaskUIState.value = productTaskUIState.value.copy(activeTask = null)
                    useInsertTask.execute(task)
                    observeTasks()
                    switchTaskFieldActive()
                }
            }
        }

    }
    
    fun setActiveProductTask(productTask:ProductTask?){
        _productTaskUIState.value = productTaskUIState.value.copy(activeTask = productTask)
    }

    fun toEditTask(id:Int){
        _editTask.value = id
    }

    fun toCompleteTask(task:Task){
        task.status = true
        viewModelScope.launch {
            useToCompleteTask.execute(task)
            observeTasks()
        }
    }

    fun switchTaskFieldActive(){
        _taskFieldUIState.value = TaskFieldUIState(active = !taskFieldUIState.value.active)
    }


    fun deleteTask(task:Task){
        viewModelScope.launch {
            useDeleteTask.execute(task)
            observeTasks()
        }
    }

    fun taskToIdea(task:Task){
        deleteTask(task)
        viewModelScope.launch {
            useInsertIdea.execute(Idea(idea = task.task))
        }
    }

    fun checkTodayDate():Boolean = useGetActualityDate.execute() == activeDayUIState.value
}