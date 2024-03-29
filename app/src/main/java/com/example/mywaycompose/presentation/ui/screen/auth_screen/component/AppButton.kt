package com.example.mywaycompose.presentation.ui.screen.auth_screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.AuthButtonColor
import com.example.mywaycompose.presentation.theme.AuthButtonTextColor
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.presentation.theme.monsterrat

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    title:String,
    callback:() -> Unit
) {

    Button(
        onClick = { callback() },
        colors = ButtonDefaults.buttonColors(backgroundColor = AppTheme.colors.secondPrimaryBackground),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontFamily = monsterrat,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.colors.primaryTitle,
            textAlign = TextAlign.Center
        )
    }

}