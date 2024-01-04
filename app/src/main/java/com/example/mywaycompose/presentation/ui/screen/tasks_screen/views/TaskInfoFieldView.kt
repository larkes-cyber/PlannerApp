package com.example.mywaycompose.presentation.ui.screen.tasks_screen.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.TaskColor
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.presentation.ui.component.AnErrorView
import com.example.mywaycompose.presentation.ui.screen.tasks_screen.component.TaskFieldComponent
import com.example.mywaycompose.presentation.ui.screen.tasks_screen.component.TasksTimeFieldComponent
import com.example.mywaycompose.utils.Constants

@Composable
fun TaskInfoFieldView(
    addTask:() -> Unit,
    timeFieldValue:TextFieldValue,
    taskFieldValue:String,
    onTimeFieldChange:(TextFieldValue) -> Unit,
    onTaskFieldChange:(String) -> Unit,
    error:String
) {

    val submittedTime = remember {
        mutableStateOf<String?>(null)
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = Constants.TASKS_HORIZONTAL_PADDINGS)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 51.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 5.dp,
            backgroundColor = AppTheme.colors.secondPrimaryBackground
        ) {
            Column(
                Modifier
                    .padding(horizontal = 15.dp)
                    .padding(vertical = 16.dp)
            )
            {
                TasksTimeFieldComponent(
                    timeFieldValue = timeFieldValue,
                    toSubmitTime = { submittedTime.value = "" },
                    toFocus = submittedTime.value == null,
                    onTimeChange = {
                        onTimeFieldChange(it)
                    }
                )
                if(error.isNotEmpty()) AnErrorView(error = error, size = 9)
                Spacer(modifier = Modifier.height(8.dp))
                TaskFieldComponent(
                    taskText = taskFieldValue,
                    submit = {
                        addTask()
                    },
                    toFocus = submittedTime.value != null,
                    onTaskChange = {
                        onTaskFieldChange(it)
                    }
                )
            }
        }
    }


}