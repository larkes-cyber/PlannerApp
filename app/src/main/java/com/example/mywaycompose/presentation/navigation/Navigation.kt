package com.example.mywaycompose.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.presentation.ui.screen.auth_screen.AuthorizationScreen
import com.example.mywaycompose.presentation.ui.screen.auth_screen.SplashScreen
import com.example.mywaycompose.presentation.ui.screen.auth_screen.WelcomeScreen
import com.example.mywaycompose.presentation.ui.screen.edit_main_task.EditMainTaskScreen
import com.example.mywaycompose.presentation.ui.screen.edit_task_screen.EditTaskScreen
import com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.IdeasPullScreen
import com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.ListMainTasksScreen
import com.example.mywaycompose.presentation.ui.screen.tasks_screen.TasksScreen
import com.example.mywaycompose.presentation.ui.screen.theme_screen.ThemeScreen
import kotlinx.coroutines.flow.MutableStateFlow


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigate(
    navController: NavHostController,
    isLightTheme:Boolean,
    showBottomNavBar:(Boolean) -> Unit = {},
    switchAppTheme:() -> Unit
){






    NavHost(navController = navController, startDestination =  Screen.SplashScreen.route){
        composable(route = Screen.SplashScreen.route){
            SplashScreen(
                navController = navController
            )
        }
        composable(route = Screen.AuthScreen.route){
            AuthorizationScreen(navController = navController)
        }
        composable(
            route = Screen.TasksScreen.route + "/{date}",
            arguments = listOf(
                navArgument("date"){
                    type = NavType.StringType
                    defaultValue = "none"
                }
            )
        ){entry ->
            val date = entry.arguments?.getString("date")
            TasksScreen(
                navController = navController,
                date = if(date!!.length == 1) null else date
            )
        }

        composable(route = Screen.WelcomeScreen.route){
            WelcomeScreen(navController = navController)
        }
        composable(
            route = Screen.MainTasksListScreen.route
        ){
            ListMainTasksScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.EditMainTaskScreen.route + "/{id}" + "/{editable}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                    defaultValue = "none"
                },
                navArgument("editable"){
                    type = NavType.StringType
                    defaultValue = "false"
                }
            )
        ){entry ->
            val potantialId = entry.arguments!!.getString("id")!!
            EditMainTaskScreen(
                id = potantialId,
                editable = entry.arguments!!.getString("editable")!!.toBoolean() ,
                navController = navController
            )
        }


        composable(
            route = Screen.EditTaskScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                    defaultValue = "-1"
                    nullable = false
                })
        ){entry ->
            EditTaskScreen(
                navController = navController,
                id = entry.arguments?.getString("id")!!.toInt()
            )
        }

        composable(
            route = Screen.ThemeScreen.route
        ){
            ThemeScreen(
                navController = navController,
                isLightTheme = isLightTheme,
            ){
                switchAppTheme()
            }
        }

        composable(
            route = Screen.IdeasPullScreen.route
        ){

            IdeasPullScreen(
                navController = navController,
                showBottomNavBar = {bool ->
                    showBottomNavBar(bool)
                }
            )
        }



    }

}



