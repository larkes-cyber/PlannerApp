package com.example.mywaycompose.presentation.ui.screen.tasks_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mywaycompose.presentation.navigation.Screen
import com.example.mywaycompose.presentation.theme.*
import com.example.mywaycompose.presentation.ui.component.MyWayTopAppBar
import com.example.mywaycompose.presentation.ui.screen.tasks_screen.views.CompletedTaskView
import com.example.mywaycompose.presentation.ui.screen.tasks_screen.views.SwipeableTaskView
import com.example.mywaycompose.presentation.ui.screen.tasks_screen.views.TaskView
import com.example.mywaycompose.presentation.ui.screen.tasks_screen.views.TaskInfoFieldView
import com.example.mywaycompose.presentation.ui.screen.tasks_screen.views.NavDatesListView
import com.example.mywaycompose.presentation.ui.screen.tasks_screen.views.PrimaryTaskView
import com.example.mywaycompose.utils.Constants.CANCEL_TEXT
import com.example.mywaycompose.utils.Constants.HELLO_TEXT
import com.example.mywaycompose.utils.Constants.OK_TEXT
import com.example.mywaycompose.utils.DateService
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState




@Composable
fun TasksScreen(
    date:String? = null,
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val editTask by viewModel.editTask.collectAsState()

    val dialogState = rememberMaterialDialogState()


    val taskUIState by viewModel.taskUIState.collectAsState()
    val daysUIState by viewModel.daysUIState.collectAsState()
    val productTaskUIState by viewModel.productTaskUIState.collectAsState()
    val activeDayUIState by viewModel.activeDayUIState.collectAsState()
    val taskFieldUIState by viewModel.taskFieldUIState.collectAsState()
    val refreshingUIState by viewModel.refreshingUiState.collectAsState()

    val authSession = viewModel.authSession

    LaunchedEffect(productTaskUIState.activeTask){
        if(productTaskUIState.activeTask != null){
            viewModel.onTaskFieldChange(productTaskUIState.activeTask!!.task)
        }
    }

    LaunchedEffect(editTask){
        if(editTask != null) navController.navigate(Screen.EditTaskScreen.withArgs(editTask.toString()))
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppTheme.colors.primaryBackground)
        ) {
            MyWayTopAppBar(
                title = HELLO_TEXT,
                showCalendarIcon = true,
                showPlusIcon = true,
                plusCallback = {
                    viewModel.switchTaskFieldActive()
                },
                calendarCallback = {
                    dialogState.show()
                },
                profileIconCallback = {
                    navController.navigate(Screen.ThemeScreen.route)
                },
                image = authSession!!.photoUrl!!
            )
            Spacer(modifier = Modifier.height(8.dp))
            NavDatesListView(
                days = daysUIState.days,
                toSelect = { day ->
                    viewModel.setActiveDate(day)
                },
                selectedDay = activeDayUIState!!
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refreshingUIState),
            onRefresh = { viewModel.refreshTasks() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.primaryBackground)
            ){

                item {
                    MaterialDialog(
                        dialogState = dialogState,
                        buttons = {
                            positiveButton(
                                text = OK_TEXT,
                                textStyle = TextStyle(color = SelectedDateCalendarColor)
                            )
                            negativeButton(
                                text = CANCEL_TEXT,
                                textStyle = TextStyle(color = SelectedDateCalendarColor)
                            )
                        },
                        backgroundColor = AppTheme.colors.primaryBackground,
                        elevation = 0.dp

                    ) {
                        datepicker(
                            colors = DatePickerDefaults.colors(
                                headerBackgroundColor = AppTheme.colors.secondPrimaryBackground,
                                dateActiveBackgroundColor = AppTheme.colors.secondPrimaryBackground,
                                dateActiveTextColor = AppTheme.colors.secondLightPrimaryTitle,
                                headerTextColor = AppTheme.colors.primaryTitle,
                                dateInactiveTextColor =  AppTheme.colors.primaryTitle,
                                calendarHeaderTextColor = AppTheme.colors.primaryTitle
                            ),

                            ) { date ->
                            viewModel.setActiveDate(DateService.localDateToDateServer(date))
                        }
                    }
                }

                item {
                    if(taskFieldUIState.active){
                        TaskInfoFieldView(
                            addTask = {
                                viewModel.addTask()
                            },
                            timeFieldValue = taskFieldUIState.time,
                            taskFieldValue = taskFieldUIState.task,
                            onTimeFieldChange = {
                                viewModel.onTimeFieldChange(it)
                            },
                            onTaskFieldChange = {
                                viewModel.onTaskFieldChange(it)
                            },
                            error = taskFieldUIState.error
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                items(taskUIState.tasks.filter { !it.status && !it.hide },key = {it.id!!}){task ->
                    SwipeableTaskView(
                        deleteCallback = {
                            viewModel.deleteTask(task)
                        },
                        toCompleteCallback = {
                            viewModel.toCompleteTask(task)
                        },
                        block = !viewModel.checkTodayDate()
                    ){
                        TaskView(
                            task = task,
                            callbackToIdeas = {viewModel.taskToIdea(task)},
                            editTask = {viewModel.toEditTask(task.id!!)}
                        )
                    }
                }


                items(taskUIState.tasks.filter { it.status && !it.hide }, key = {it.id!!}){ task ->
                    CompletedTaskView(task = task){
                        viewModel.deleteTask(task)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(150.dp))
                }

            }
        }
    }
}