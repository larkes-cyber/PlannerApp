package com.example.mywaycompose.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.mywaycompose.R
import com.example.mywaycompose.presentation.navigation.BottomNavBar
import com.example.mywaycompose.presentation.navigation.BottomNavItem
import com.example.mywaycompose.presentation.navigation.Navigate
import com.example.mywaycompose.presentation.navigation.Screen
import com.example.mywaycompose.presentation.theme.MyWayComposeTheme
import com.example.mywaycompose.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val accountViewModel: MainActivityViewModel by viewModels()
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var showBottomNavBar by remember { mutableStateOf(true) }

            val themeUIState by accountViewModel.currentMainThemeColors.collectAsState()


            MyWayComposeTheme(darkTheme = themeUIState == Constants.DAY_MAIN_THEME) {
                val navController = rememberNavController()

                Scaffold(
                    Modifier.fillMaxSize(),
                    bottomBar = {
                        if(showBottomNavBar){
                            BottomNavBar(
                                items = listOf(
                                    BottomNavItem(
                                        icon = R.drawable.ic_baseline_architecture_24,
                                        name = "Идеи",
                                        route = "ideas_pull_screen"
                                    ),
                                    BottomNavItem(
                                        icon = R.drawable.tasks_nav_icon,
                                        name = "Задачи",
                                        route = Screen.TasksScreen.withArgs("null")
                                    ),
                                    BottomNavItem(
                                        icon = R.drawable.high_nav_icon,
                                        name = "Главные цели",
                                        route = "main_tasks_list"
                                    )
                                ),
                                navController = navController
                            ){
                                navController.navigate(it.route)
                            }
                        }
                    }
                ){
                    Navigate(
                        navController = navController,
                        showBottomNavBar = {bool ->
                            Log.d("sfsdfsdfsdf",bool.toString())
                            showBottomNavBar = bool
                        },
                        isLightTheme = themeUIState == Constants.DAY_MAIN_THEME
                    ){
                        accountViewModel.switchMainThemeColors()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyWayComposeTheme {
        Greeting("Android")
    }
}