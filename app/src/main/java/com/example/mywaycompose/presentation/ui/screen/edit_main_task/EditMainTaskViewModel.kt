package com.example.mywaycompose.presentation.ui.screen.edit_main_task

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.usecase.main_task.*
import com.example.mywaycompose.domain.usecase.visual_task.UseAddNewDependNode
import com.example.mywaycompose.domain.usecase.visual_task.UseFindVisualMainTask
import com.example.mywaycompose.presentation.ui.screen.edit_main_task.state.EditMainTaskScreenState
import com.example.mywaycompose.presentation.ui.screen.edit_main_task.state.MainTaskFieldUIState
import com.example.mywaycompose.presentation.ui.screen.edit_main_task.state.MainTaskUIState
import com.example.mywaycompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMainTaskViewModel @Inject constructor(
    private val useObserveMainTaskById: UseObserveMainTaskById,
    private val savedStateHandle: SavedStateHandle,
    private val useUpdateMainTask: UseUpdateMainTask,
    private val useDeleteMainTask: UseDeleteMainTask
    ):ViewModel() {

    private val _mainTaskUIState = MutableStateFlow(MainTaskUIState())
    val mainTaskUIState:StateFlow<MainTaskUIState> = _mainTaskUIState

    private val _mainTaskFieldUIState = MutableStateFlow(MainTaskFieldUIState())
    val mainTaskFieldUIState:StateFlow<MainTaskFieldUIState> = _mainTaskFieldUIState

    private val _hasBeenDone = MutableStateFlow(false)
    val hasBeenDone:StateFlow<Boolean> = _hasBeenDone

    init{

        viewModelScope.launch {
            useObserveMainTaskById.invoke(savedStateHandle.get<String>("id")!!).onEach {res ->
                when(res){
                    is Resource.Loading -> {
                        _mainTaskUIState.value = MainTaskUIState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _mainTaskUIState.value = MainTaskUIState(
                            mainTask = res.data
                        )
                        _mainTaskFieldUIState.value = MainTaskFieldUIState(
                            imageSrc = if(res.data?.imageSrc != null) res.data.imageSrc!!.toUri() else null,
                            titleField = res.data!!.title,
                            iconField = res.data.icon!!
                        )
                    }
                    is Resource.Error -> {
                        _mainTaskUIState.value = MainTaskUIState(error = res.message!!)
                    }
                }
            }.launchIn(viewModelScope)
        }

    }

    fun setMainTaskImage(uri:Uri){
        _mainTaskFieldUIState.value = _mainTaskFieldUIState.value.copy(imageSrc = uri)
    }

    fun onTitleField(title:String){
        _mainTaskFieldUIState.value = _mainTaskFieldUIState.value.copy(titleField = title)
    }

    fun onIconField(icon:String){
        _mainTaskFieldUIState.value = _mainTaskFieldUIState.value.copy(iconField = icon)
    }

    fun onDone(){
        viewModelScope.launch {
            useUpdateMainTask.execute(
               mainTask =  MainTask(
                    id = mainTaskUIState.value.mainTask!!.id,
                    title = mainTaskFieldUIState.value.titleField,
                    icon = mainTaskFieldUIState.value.iconField,
                    imageSrc = if(mainTaskFieldUIState.value.imageSrc == null) null else mainTaskFieldUIState.value.imageSrc.toString()
                ),
                photoEdited = mainTaskUIState.value.mainTask!!.imageSrc.toString() != mainTaskFieldUIState.value.imageSrc.toString() && mainTaskUIState.value.mainTask!!.imageSrc != null
            )
            _hasBeenDone.value = true
        }
    }

    fun deleteMainTask(){
        viewModelScope.launch {
            useDeleteMainTask.execute(mainTaskUIState.value.mainTask!!)
            _hasBeenDone.value = true
        }
    }


}