package com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.monsterrat
import com.example.mywaycompose.presentation.ui.component.TaskFieldView
import com.example.mywaycompose.utils.Constants.ADD_SUBCLASS_TITLE
import com.example.mywaycompose.utils.Constants.CANCEL_TEXT
import com.example.mywaycompose.utils.Constants.OK_TEXT

@Composable
fun AddSubclassAlertDialog(
    modifier: Modifier = Modifier,
    subclass:String,
    chosenColor:Long = 0xff,
    onSubclassTitleChange:(String) -> Unit = {},
    onColorPickerClick:() -> Unit = {},
    onSubmit:() -> Unit,
    onDismiss:() -> Unit
) {


    Dialog(onDismissRequest = { onDismiss() }) {

        Card(
            backgroundColor = AppTheme.colors.primaryBackground,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp).padding(15.dp)
            ) {
                Text(
                    text = ADD_SUBCLASS_TITLE,
                    style = MaterialTheme.typography.h2.copy(color = AppTheme.colors.primaryTitle, fontSize = 19.sp, fontFamily = monsterrat, fontWeight = FontWeight.Medium)
                )
                Spacer(modifier = Modifier.height(25.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Divider(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color(chosenColor))
                            .clickable {
                                onColorPickerClick()
                            }
                    )
                    TaskFieldView(
                        text = subclass,
                        modifier = Modifier
                            .height(32.dp)
                            .fillMaxWidth()
                            .padding(top = 3.dp, start = 5.dp)
                    ){
                        onSubclassTitleChange(it)
                    }
                }
                Spacer(modifier = Modifier.height(35.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        ClickableText(
                            text = AnnotatedString(CANCEL_TEXT),
                            style = MaterialTheme.typography.caption.copy(color = AppTheme.colors.secondLightPrimaryTitle, fontSize = 13.sp)
                        ){
                            onDismiss()
                        }
                        ClickableText(
                            text = AnnotatedString(OK_TEXT),
                            style = MaterialTheme.typography.caption.copy(color = AppTheme.colors.secondLightPrimaryTitle, fontSize = 13.sp)
                        ){
                            onSubmit()
                        }
                    }
                }
            }
        }

    }

}