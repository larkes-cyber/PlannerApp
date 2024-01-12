package com.example.mywaycompose.presentation.ui.screen.edit_task_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.example.mywaycompose.R
import com.example.mywaycompose.domain.model.toDateString
import com.example.mywaycompose.presentation.navigation.Screen
import com.example.mywaycompose.presentation.theme.*
import com.example.mywaycompose.presentation.ui.component.LoadingView
import com.example.mywaycompose.presentation.ui.screen.edit_task_screen.component.*
import com.example.mywaycompose.presentation.ui.screen.edit_task_screen.views.EditTaskHeaderView
import com.example.mywaycompose.presentation.ui.screen.edit_task_screen.views.GradeClickerView
import com.example.mywaycompose.presentation.ui.screen.edit_task_screen.views.MainTasksSliderView
import com.example.mywaycompose.presentation.ui.component.TaskFieldView
import com.example.mywaycompose.presentation.ui.screen.auth_screen.component.AuthErrorComponent
import com.example.mywaycompose.presentation.ui.screen.edit_task_screen.views.TimeTaskFieldView
import com.example.mywaycompose.utils.Constants
import com.example.mywaycompose.utils.Constants.EDIT_TASK_SCREEN_TITLE
import com.example.mywaycompose.utils.Constants.TASK_FIELD_HINT
import com.example.mywaycompose.utils.Constants.TIME_FIELD_HINT
import com.example.mywaycompose.utils.DateService
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState


@SuppressLint("RememberReturnType")
@Composable
fun EditTaskScreen(
    navController: NavController,
    id:Int,
    viewModel: EditTaskViewModel = hiltViewModel()
) {


    val taskUIState by viewModel.taskUIState.collectAsState()
    val taskFieldUIState by viewModel.taskFieldUIState.collectAsState()
    val hasBeenDone by viewModel.hasBeenDone.collectAsState()

    LaunchedEffect(hasBeenDone){
        if(hasBeenDone){
            navController.navigate(Screen.TasksScreen.withArgs(taskFieldUIState.date))
        }
    }

    val dialogState = rememberMaterialDialogState()


    Box(modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.primaryBackground)){


        LazyColumn(modifier = Modifier.fillMaxSize()){

            item{
                MaterialDialog(
                    dialogState = dialogState,
                    buttons = {
                        positiveButton(
                            text = Constants.OK_TEXT,
                            textStyle = TextStyle(color = SelectedDateCalendarColor)
                        )
                        negativeButton(
                            text = Constants.CANCEL_TEXT,
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
                        viewModel.onDateChange(DateService.localDateToDateServer(date).toDateString())
                    }
                }
            }

            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                        .padding(top = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = EDIT_TASK_SCREEN_TITLE,
                        fontSize = 18.sp,
                        fontFamily = monsterrat,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.colors.primaryTitle,
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Normal
                    )
                    IconButton(onClick = {
                        dialogState.show()
                    }) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp),
                            tint = AppTheme.colors.iconColor
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            item { 
                Spacer(modifier = Modifier.height(50.dp))
            }

            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                        TimeTaskFieldView(
                            hint = TIME_FIELD_HINT,
                            text = taskFieldUIState.timeField,
                            modifier = Modifier
                                .width(53.dp)
                                .height(48.dp)
                                .padding(1.dp)
                        ){ viewModel.onTimeFieldChange(it) }
                        TaskFieldView(
                            hint = TASK_FIELD_HINT,
                            text = taskFieldUIState.titleField,
                            modifier = Modifier
                                .width(265.dp)
                                .height(48.dp)
                                .padding(7.dp)
                        ){
                            viewModel.onTitleFieldChange(it)
                        }
                }
            }
            item {
                Column(modifier = Modifier.padding(bottom = 30.dp)) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = TASK_FIELD_HINT,
                            fontSize = 18.sp,
                            fontFamily = monsterrat,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.colors.primaryTitle,
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Normal
                        )
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        GradeClickerView(
                            callback = {
                                viewModel.onGradeChange(it + taskFieldUIState.grade)
                            },
                            numChange = {
                                viewModel.onGradeChange(it.toInt())
                            },
                            number = taskFieldUIState.grade
                        )
                    }
                }

            }
        }
        Box(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 25.dp, bottom = 44.dp)) {
            IconButton(
                onClick = {
                          viewModel.onDone()
            }, modifier = Modifier.size(50.dp)) {
                Icon(imageVector = Icons.Rounded.CheckCircleOutline,
                    contentDescription = "",
                    modifier = Modifier.size(50.dp),
                    tint = AppTheme.colors.iconColor
                )
            }
        }

    }

}