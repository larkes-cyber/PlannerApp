package com.example.mywaycompose.presentation.ui.screen.auth_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mywaycompose.presentation.navigation.Screen
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.AuthTitleColor
import com.example.mywaycompose.presentation.theme.MainThemeColor
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.presentation.theme.monsterrat
import com.example.mywaycompose.presentation.ui.screen.auth_screen.component.AuthTitleComponent

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val firstLaunch = viewModel.firstLaunch
    LaunchedEffect(firstLaunch){
        if(firstLaunch) navController.navigate(Screen.AuthScreen.route)
    }

    LaunchedEffect(Unit){
        if(!firstLaunch) {
            viewModel.loadTasks()
            viewModel.loadMainTasks()
            viewModel.loadIdeas()
            viewModel.loadVisualTasks()
            viewModel.loadProductTasks()
            viewModel.loadTaskClasses()
        }
    }

    Log.d("task_class_log", state.taskClassesHasBeenLoaded.toString())

    LaunchedEffect(
        state
    ){
        if(
            state.tasksHasBeenLoaded
            && state.mainTasksHasBeenLoaded
            && state.ideasHasBeenLoaded
            && state.productTasksHasBeenLoaded
            && state.visualTasksHasBeenLoaded
            && state.taskClassesHasBeenLoaded
        ){
            navController.navigate(Screen.IdeasPullScreen.route)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground),
        verticalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Подождите, пока данные загружаются",
                fontSize = 24.sp,
                fontFamily = monsterrat,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.primaryTitle,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Normal
            )
            CircularProgressIndicator()
        }
    }
    
}