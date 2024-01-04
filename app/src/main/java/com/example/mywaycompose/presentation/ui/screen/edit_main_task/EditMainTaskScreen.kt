package com.example.mywaycompose.presentation.ui.screen.edit_main_task

import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mywaycompose.R
import com.example.mywaycompose.presentation.navigation.Screen
import com.example.mywaycompose.presentation.theme.*
import com.example.mywaycompose.presentation.ui.component.AnErrorView
import com.example.mywaycompose.presentation.ui.screen.edit_main_task.views.MainTaskPreviewCardView
import com.example.mywaycompose.presentation.ui.component.TaskFieldView
import com.example.mywaycompose.utils.Constants.GOAL_TEXT_FIELD_TITLE
import com.example.mywaycompose.utils.Constants.GOAL_TITLE_HINT
import com.example.mywaycompose.utils.Constants.NEW_GOAL_TITLE
import com.example.mywaycompose.utils.Constants.SELECT_GOAL_IMAGE_TITLE

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditMainTaskScreen(
    id:String,
    editable:Boolean,
    navController: NavController,
    viewModel: EditMainTaskViewModel = hiltViewModel()
) {



    val mainTaskUIState by viewModel.mainTaskUIState.collectAsState()
    val mainTaskFieldUIState by viewModel.mainTaskFieldUIState.collectAsState()
    val hasBeenDone by viewModel.hasBeenDone.collectAsState()



    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if(uri != null) {
                viewModel.setMainTaskImage(uri)
            }
        }

    LaunchedEffect(hasBeenDone){
        if(hasBeenDone){
            navController.navigate(Screen.MainTasksListScreen.route)
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground)
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = NEW_GOAL_TITLE,
                    fontSize = 20.sp,
                    fontFamily = monsterrat,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.primaryTitle,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal
                )
                IconButton(onClick = {
                    viewModel.onDone()
                }, modifier = Modifier.size(30.dp)) {
                    Icon(
                        painterResource(id = R.drawable.close_circle),
                        tint = AppTheme.colors.iconColor,
                        modifier = Modifier.size(30.dp),
                        contentDescription = ""
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
            MainTaskPreviewCardView(
                image = mainTaskFieldUIState.imageSrc,
                title = mainTaskFieldUIState.titleField.ifEmpty { GOAL_TITLE_HINT },
                icon = mainTaskFieldUIState.iconField.ifEmpty {  "\uD83C\uDF59" }
            )
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
            Divider(modifier = Modifier
                .width(160.dp)
                .height(3.dp)
                .clip(RoundedCornerShape(40))
                .background(AppTheme.colors.primaryTitle))
            Spacer(modifier = Modifier.height(33.dp))
        }

        item {
            Button(
                onClick = {
                    galleryLauncher.launch("image/*")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                border = BorderStroke(width = 1.5.dp, AppTheme.colors.primaryTitle),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = SELECT_GOAL_IMAGE_TITLE,
                            color = AppTheme.colors.primaryTitle,
                            fontSize = 16.sp,
                            fontFamily = monsterrat,
                            fontWeight = FontWeight.Normal
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.image_icon),
                            contentDescription = "",
                            tint = AppTheme.colors.iconColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

            }
        }
        
        item {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = GOAL_TEXT_FIELD_TITLE,
                fontSize = 18.sp,
                fontFamily = monsterrat,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.primaryTitle,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TaskFieldView(
                        hint = GOAL_TITLE_HINT,
                        text = mainTaskFieldUIState.titleField,
                        modifier = Modifier
                            .width(252.dp)
                            .height(50.dp)
                            .padding(7.dp)
                    ){
                        viewModel.onTitleField(it)
                    }
                    TaskFieldView(
                        hint = "\uD83C\uDF59",
                        text = mainTaskFieldUIState.iconField,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(top = 7.dp, start = 8.dp),
                        maxLen = 2
                    ){
                        viewModel.onIconField(it)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = {
                        viewModel.deleteMainTask()
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    Icon(imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        modifier = Modifier.size(45.dp),
                        tint = AppTheme.colors.iconColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(45.dp))
        }

    }

}