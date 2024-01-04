package com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.toColorLong
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.monsterrat
import com.example.mywaycompose.utils.Constants
import com.example.mywaycompose.utils.Constants.CANCEL_TEXT
import com.example.mywaycompose.utils.Constants.OK_TEXT
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import com.godaddy.android.colorpicker.toColorInt

@Composable
fun ColorPicker(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: (Long) -> Unit
) {

    var currentColor by remember {
        mutableStateOf(0xff000000)
    }

    Dialog(onDismissRequest = onDismiss) {

        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            backgroundColor = AppTheme.colors.primaryBackground
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 27.dp)
                    .padding(bottom = 8.dp)

            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = Constants.CHOOSE_COLOR_TITLE,
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = AppTheme.colors.primaryTitle,
                            fontSize = 19.sp,
                            fontFamily = monsterrat
                        )
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                ClassicColorPicker(
                    color =HsvColor.from(Color(currentColor)),
                    modifier = Modifier
                        .height(300.dp)
                        .padding(16.dp),
                    onColorChanged = { hsvColor: HsvColor ->
                        currentColor = hsvColor.toColor().toHexLong()
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(onClick = onNegativeClick) {
                        Text(text = CANCEL_TEXT)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                        onPositiveClick(currentColor)
                    }) {
                        Text(text = OK_TEXT)
                    }
                }
            }
        }
    }
}

fun Color.toHexLong(): Long {
    val red = (this.red * 255).toInt()
    val green = (this.green * 255).toInt()
    val blue = (this.blue * 255).toInt()
    val alpha = (this.alpha * 255).toInt()

    return ((alpha shl 24) or (red shl 16) or (green shl 8) or blue).toLong()
}