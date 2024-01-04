package com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.monsterrat
import com.example.mywaycompose.presentation.ui.component.AnErrorView
import com.example.mywaycompose.utils.toTimeFormatString

@Composable
fun TimePickerAlertView(
    error:String = "",
    onSubmit:(String) -> Unit,
    onDismiss:() -> Unit
) {
    var pickerValue by remember { mutableStateOf(0) }
    var pickerValue1 by remember { mutableStateOf(0) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            backgroundColor = AppTheme.colors.primaryBackground,
            modifier = Modifier.fillMaxWidth().height(300.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "Выберете время:",
                    fontFamily = monsterrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 19.sp,
                    color = AppTheme.colors.primaryTitle,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NumberPicker(
                        value = pickerValue,
                        dividersColor = AppTheme.colors.primaryTitle,
                        textStyle = TextStyle(color = AppTheme.colors.primaryTitle),
                        range = 0..23,
                        onValueChange = {
                            pickerValue = it
                        }
                    )
                    Column {
                        Divider(modifier = Modifier
                            .size(4.dp)
                            .clip(RoundedCornerShape(100))
                            .background(AppTheme.colors.primaryTitle))
                        Spacer(modifier = Modifier.height(2.dp))
                        Divider(modifier = Modifier
                            .size(4.dp)
                            .clip(RoundedCornerShape(100))
                            .background(AppTheme.colors.primaryTitle))
                    }
                    NumberPicker(
                        value = pickerValue1,
                        dividersColor = AppTheme.colors.primaryTitle,
                        textStyle = TextStyle(color = AppTheme.colors.primaryTitle),
                        range = 0..59,
                        onValueChange = {
                            pickerValue1 = it
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                if(error.isNotEmpty()){
                    Text(
                        text = error,
                        style = MaterialTheme.typography.caption.copy(color = Color.Red, fontSize = 12.sp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                }

                Box(modifier = Modifier.padding(bottom = 15.dp)) {
                    Button(
                        onClick = {
                            val time = "$pickerValue:$pickerValue1".toTimeFormatString()
                            onSubmit(time)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = AppTheme.colors.strongPrimaryBackground)
                    ) {
                        Text(
                            text = "Готово!",
                            fontFamily = monsterrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = AppTheme.colors.primaryTitle,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

        }
    }
}