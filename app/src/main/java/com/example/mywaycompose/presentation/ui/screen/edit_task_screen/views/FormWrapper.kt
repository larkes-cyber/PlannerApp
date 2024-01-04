package com.example.mywaycompose.presentation.ui.screen.edit_task_screen.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.TaskStrokeColor
import com.example.mywaycompose.presentation.theme.ThemeColors

@Composable
fun FormWrapper(
    modifier: Modifier = Modifier,
    visibleStroke:Boolean = true,
    content:@Composable () -> Unit
) {

    Card(
        backgroundColor = Color.Transparent,
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(width = 1.dp, color =if(visibleStroke) AppTheme.colors.primaryTitle else Color.Transparent),
        elevation = 0.dp
    ) {
        Column(
            modifier = modifier, verticalArrangement = Arrangement.Center) {
            content()
        }
    }

}