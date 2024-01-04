package com.example.mywaycompose.presentation.ui.screen.edit_task_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.model.Task
import com.example.mywaycompose.domain.model.toDateString
import com.example.mywaycompose.domain.usecase.local_service.UseCheckCorrectTime
import com.example.mywaycompose.domain.usecase.local_service.UseCompareDateWithCurrent
import com.example.mywaycompose.domain.usecase.local_service.UseGetActualityDate

import com.example.mywaycompose.domain.usecase.main_task.UseObserveAllMainTasks
import com.example.mywaycompose.domain.usecase.main_task.UseObserveMainTaskById

import com.example.mywaycompose.domain.usecase.task.UseDeleteTask
import com.example.mywaycompose.domain.usecase.task.UseInsertTask
import com.example.mywaycompose.domain.usecase.task.UseObserveTaskById
import com.example.mywaycompose.domain.usecase.task.UsePushTaskFirebase
import com.example.mywaycompose.domain.usecase.task.UseUpdateTask
import com.example.mywaycompose.presentation.ui.screen.edit_task_screen.state.MainTasksState
import com.example.mywaycompose.presentation.ui.screen.edit_task_screen.state.TaskFieldUIState
import com.example.mywaycompose.presentation.ui.screen.edit_task_screen.state.TaskState
import com.example.mywaycompose.presentation.ui.screen.edit_task_screen.state.TaskUIState
import com.example.mywaycompose.utils.Constants.EqualsCurrentDate
import com.example.mywaycompose.utils.Constants.MoreThenCurrentDate
import com.example.mywaycompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
  private val useObserveTaskById: UseObserveTaskById,
  private val useInsertTask: UseInsertTask,
  private val savedStateHandle: SavedStateHandle,
  private val useDeleteTask: UseDeleteTask
):ViewModel() {


    private val _taskUIState = MutableStateFlow(TaskUIState())
    val taskUIState:StateFlow<TaskUIState> = _taskUIState

    private val _taskFieldUIState = MutableStateFlow(TaskFieldUIState())
    val taskFieldUIState:StateFlow<TaskFieldUIState> = _taskFieldUIState

    private val _hasBeenDone = MutableStateFlow(false)
    val hasBeenDone:StateFlow<Boolean> = _hasBeenDone

    init {
      useObserveTaskById.invoke(savedStateHandle.get<String>("id")!!.toInt()).onEach { res ->
          when(res){
            is Resource.Loading -> {
              _taskUIState.value = TaskUIState(isLoading = true)
            }
            is Resource.Success -> {
              _taskUIState.value = TaskUIState(task = res.data)
              _taskFieldUIState.value = TaskFieldUIState(
                titleField = res.data!!.task,
                date = res.data.date,
                timeField = TextFieldValue(res.data.time),
                grade = res.data.grade ?: 0
              )
            }
            is Resource.Error -> {
              _taskUIState.value = TaskUIState(error = res.message!!)
            }
          }
        }.launchIn(viewModelScope)
    }

    fun onTitleFieldChange(title:String){
      _taskFieldUIState.value = taskFieldUIState.value.copy(titleField = title)
    }

    fun onTimeFieldChange(time:TextFieldValue){
      _taskFieldUIState.value = taskFieldUIState.value.copy(timeField = time)
    }

    fun onGradeChange(grade:Int){
      _taskFieldUIState.value = taskFieldUIState.value.copy(grade = grade)
    }

    fun onDateChange(date:String){
        _taskFieldUIState.value = taskFieldUIState.value.copy(date = date)
    }

    fun onDone(){
        viewModelScope.launch {
            val taskFieldUIStateValue = taskFieldUIState.value

            Log.d("wefgbfdsfgb", taskFieldUIStateValue.date)

            val task = Task(
                task = taskFieldUIStateValue.titleField,
                time = taskFieldUIStateValue.timeField.text,
                date = taskFieldUIStateValue.date,
                grade = taskFieldUIStateValue.grade,
                status = taskUIState.value.task!!.status,
                id = taskUIState.value.task!!.id
            )

            useDeleteTask.execute(taskUIState.value.task!!)
            useInsertTask.execute(task)
            _hasBeenDone.value = true
        }
    }


}
