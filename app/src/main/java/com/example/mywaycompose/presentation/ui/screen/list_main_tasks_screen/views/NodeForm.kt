package com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.presentation.theme.monsterrat

@Composable
fun NodeForm(
    text:String,
    onChange:(String) -> Unit,
    onDone:() -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(modifier = Modifier.padding(horizontal = 4.dp).padding(top = 0.dp)) {
            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .width(19.dp)
                .background(AppTheme.colors.primaryTitle))
        }
        Row() {
            BasicTextField(
                value = text,
                onValueChange = {onChange(it)},
                textStyle = TextStyle(
                    color = AppTheme.colors.primaryTitle.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontFamily = monsterrat,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .height(17.dp)
                    .width(100.dp)
                    .padding(start = 4.dp),
                decorationBox = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Divider(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(AppTheme.colors.strongPrimaryBackground))
                    }
                    it()
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    onDone()
                },
                modifier = Modifier.size(14.dp)
            ) {
                Icon(
                    Icons.Default.Done,
                    contentDescription = "",
                    modifier = Modifier.size(14.dp),
                    tint = AppTheme.colors.primaryTitle
                )
            }
        }
        }
}