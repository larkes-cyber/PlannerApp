package com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mywaycompose.presentation.theme.MainThemeColor
import com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.views.ClickableMainTaskView
import com.example.mywaycompose.presentation.navigation.Screen
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.presentation.ui.component.MyWayTopAppBar
import com.example.mywaycompose.presentation.ui.component.AnErrorView
import com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.views.AddSubclassAlertDialog
import com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.views.ColorPicker
import com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.views.FullMainTaskView
import com.example.mywaycompose.utils.Constants.GOALS_TITLE
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun ListMainTasksScreen(
    navController: NavController,
    viewModel: MainTaskListViewModel = hiltViewModel()
) {



    val hasBeenSelected by viewModel.hasBeenSelected.collectAsState()

    val viewModelValue = viewModel.mainTasksState.value
    val user = viewModel._authSession.currentUser

    val refreshingUIState by viewModel.refreshingUiState.collectAsState()
    val subclassUIState by viewModel.subclassUIState.collectAsState()


    LaunchedEffect(hasBeenSelected){
        if(hasBeenSelected != null) navController.navigate(Screen.DetailMainTaskScreen.withArgs(hasBeenSelected.toString()))
    }



    Column(modifier = Modifier
        .fillMaxSize()
        .background(AppTheme.colors.primaryBackground)
        .padding(horizontal = 16.dp)

    ) {
        if(subclassUIState.colorPickerAlertDialogActive) {
            ColorPicker(
                onDismiss = {
                    viewModel.onAddSubclassColorChange(0xffffffff)
                },
                onNegativeClick = {
                    viewModel.onAddSubclassColorChange(0xffffffff)
                },
                onPositiveClick = {
                    viewModel.onAddSubclassColorChange(it)
                }
            )
        }
        if(subclassUIState.subclassAlertDialogActive) {
            AddSubclassAlertDialog(
                subclass = subclassUIState.chosenSubclass!!,
                chosenColor = subclassUIState.chosenColor,
                onColorPickerClick = {
                      viewModel.onAddSubclassColorClick()
                },
                onSubclassTitleChange = {
                      viewModel.onSubclassTitleChange(it)
                },
                onSubmit = {
                   viewModel.onSubclassDone()
                },
                onDismiss = {
                    viewModel.resetSubclassUIState()
                }
            )
        }

        MyWayTopAppBar(
            title = GOALS_TITLE,
            image = user!!.photoUrl!!,
            showCalendarIcon = false,
            showPlusIcon = false,
            plusCallback = {
            },
            profileIconCallback = {
                navController.navigate(Screen.ThemeScreen.route)
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        if(viewModelValue.isLoading){
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        if(viewModelValue.success.isNotEmpty()){
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 110.dp)
            ){
                itemsIndexed(viewModelValue.success){index, item ->
                    Column() {
                        FullMainTaskView(
                            mainTask = item,
                            generateVsId = {viewModel.generateTaskId()},
                            onDeleteNode = {
                                viewModel.deleteNode(it)
                            },
                            onPushNode = {
                                viewModel.onAddSubclassClick(
                                    subclass = it.text,
                                    vsTaskId = it.id!!
                                )
                            },
                            onSettingsClick = {
                                navController.navigate(Screen.EditMainTaskScreen.withArgs(item.id.toString(), "true"))
                            }
                        ){ visualTask ->
                            viewModel.addNewVisualTask(visualTask)
                        }
                    }
                }
            }
        }
        if(viewModelValue.error.isNotEmpty()){
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                AnErrorView(error = viewModelValue.error, size = 14)
            }
        }
    }

}