package com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mywaycompose.domain.model.toDateString
import com.example.mywaycompose.presentation.navigation.Screen
import com.example.mywaycompose.presentation.theme.*
import com.example.mywaycompose.presentation.ui.component.GraphProviderView
import com.example.mywaycompose.presentation.ui.component.MyWayTopAppBar
import com.example.mywaycompose.presentation.ui.component.TaskFieldView
import com.example.mywaycompose.presentation.ui.screen.auth_screen.component.AppButton
import com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.views.IdeaCardView
import com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.views.AddIdeaFormView
import com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.views.ChooseTaskClassAlert
import com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.views.TimePickerAlertView
import com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.MainTaskWithVisualTasks
import com.example.mywaycompose.utils.Constants.ADD_BIG_GOAL
import com.example.mywaycompose.utils.Constants.CANCEL_TEXT
import com.example.mywaycompose.utils.Constants.IDEA_HINT
import com.example.mywaycompose.utils.Constants.IDEA_TITLE
import com.example.mywaycompose.utils.Constants.NO_VISUAL_TASKS
import com.example.mywaycompose.utils.Constants.OK_TEXT
import com.example.mywaycompose.utils.DateService
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IdeasPullScreen(
    navController: NavController,
    viewModel: PullIdeasViewModel = hiltViewModel(),
    showBottomNavBar:(Boolean)  -> Unit
) {

    val user = viewModel.user
    val scope = rememberCoroutineScope()

    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val ideaUIState by viewModel.ideaUIState.collectAsState()
    val ideaFieldUIState by viewModel.ideaFieldUIState.collectAsState()
    val visualTaskUIState by viewModel.visualTaskUIState.collectAsState()
    val refreshingUIState by viewModel.refreshingUiState.collectAsState()
    val plannerIdeaUIState by viewModel.ideaPlannerTaskUIState.collectAsState()
    val dialogState = rememberMaterialDialogState()




    LaunchedEffect(sheetState.currentValue){
        showBottomNavBar(sheetState.isCollapsed)
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        content = {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = refreshingUIState),
                onRefresh = { viewModel.syncIdeas() },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .background(AppTheme.colors.primaryBackground)
                        .padding(horizontal = 16.dp)
                ) {
                    MyWayTopAppBar(
                        title = IDEA_TITLE,
                        image = user!!.photoUrl!!,
                        showCalendarIcon = false,
                        showPlusIcon = true,
                        plusCallback = {
                            viewModel.switchIdeaFieldActive()
                        },
                        profileIconCallback = {
                            navController.navigate(Screen.ThemeScreen.route)
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Column() {
                        if(ideaFieldUIState.active){
                            Box(
                                modifier = Modifier.padding(top = 35.dp)
                            ) {
                                AddIdeaFormView(
                                    task = ideaFieldUIState.idea,
                                    submitCallback = {
                                        if(ideaFieldUIState.idea.isEmpty()) return@AddIdeaFormView
                                        viewModel.addIdea()
                                    },
                                    taskListener = {
                                        viewModel.changeIdeaField(it)
                                    }
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ideaUIState.ideas.filter { !it.hide }.forEach {idea ->
                                IdeaCardView(
                                    idea = idea,
                                    onDeleteIdea = {
                                        viewModel.deleteIdea(idea)
                                    },
                                    onAddNodeClick = {
                                        showBottomNavBar(false)
                                        scope.launch {
                                            sheetState.expand()
                                        }
                                        viewModel.onVisualTaskFieldChange(idea.idea)
                                        viewModel.setTransitionIdea(idea)
                                    },
                                    onAddPlannerTaskClick = {
                                        viewModel.setPlannerIdea(idea)
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        },
        sheetContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                backgroundColor = AppTheme.colors.strongPrimaryBackground.copy(alpha = 0.95f),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if(plannerIdeaUIState.selectedClass == null && plannerIdeaUIState.selectedIdea != null){
                        ChooseTaskClassAlert(
                            classesList = plannerIdeaUIState.taskClassesList,
                            onDismiss = {
                                viewModel.resetPlannerTaskState()
                            },
                            onSkip = {
                                dialogState.show()
                                viewModel.setTaskClass("")
                            },
                            onSubmit = {
                                dialogState.show()
                                viewModel.setTaskClass(it)
                            }
                        )
                    }

                    if(plannerIdeaUIState.selectedDate != null && plannerIdeaUIState.selectedTime == null) {
                        TimePickerAlertView(
                            onDismiss = {
                                viewModel.resetPlannerTaskState()
                            },
                            onSubmit = { time ->
                                viewModel.setPlannerIdeaTime(time)
                            },
                            error = plannerIdeaUIState.timeError
                        )
                    }

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
                        elevation = 0.dp,
                        onCloseRequest = {
                            viewModel.resetPlannerTaskState()
                        }

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
                            viewModel.setPlannerIdeaDate(DateService.localDateToDateServer(date).toDateString())
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(start = 35.dp, top = 30.dp)
                            .height(310.dp)
                    ) {
                        if(visualTaskUIState.visualTasks.size == 1){
                            Text(
                                text = NO_VISUAL_TASKS,
                                color = AppTheme.colors.primaryTitle,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = monsterrat,
                                textAlign = TextAlign.Center
                            )
                        }
                        GraphProviderView(
                            graph = visualTaskUIState.visualTasks,
                            fontSize = 15,
                            offMode = true,
                            mainTask = MainTaskWithVisualTasks("12","dsad", icon = "sd", imageSrc = "asasd"),
                            generateVsId = { viewModel.generateTaskId() },
                            addNewVisualTask = {

                            },
                            onNodeClick = {
                                viewModel.setCurrentVisualTaskNode(it)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier.padding(horizontal = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Log.d("dfgsdfsdfsdf", visualTaskUIState.visualTaskField)
                        TaskFieldView(
                            hint = IDEA_HINT,
                            text = visualTaskUIState.visualTaskField,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(top = 6.dp, start = 5.dp)
                        ){
                            viewModel.onVisualTaskFieldChange(it)
                        }
                        if(visualTaskUIState.selectedVisualTask != null) {
                            AppButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(45.dp),
                                title = visualTaskUIState.selectedVisualTask!!.text
                            ) {
                                scope.launch {
                                    viewModel.createNewVsTask()
                                    sheetState.collapse()
                                }
                            }
                        }
                        AppButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp),
                            title = ADD_BIG_GOAL
                        ) {
                            scope.launch {
                                viewModel.addMainTask()
                                sheetState.collapse()
                            }
                        }
                    }
                }

            }
         },
        sheetPeekHeight = 0.dp,
        backgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        drawerElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent,
        modifier = Modifier.fillMaxSize()
    )
}