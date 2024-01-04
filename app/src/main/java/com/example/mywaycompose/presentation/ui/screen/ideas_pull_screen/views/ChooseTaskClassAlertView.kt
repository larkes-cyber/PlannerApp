package com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mywaycompose.domain.model.TaskClass
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.monsterrat
import com.example.mywaycompose.utils.Constants.CHOOSE_CLASS_BTN_TITLE
import com.example.mywaycompose.utils.Constants.CHOSE_TASK_CLASS
import com.example.mywaycompose.utils.Constants.SKIP_BTN_TITLE

@Composable
fun ChooseTaskClassAlert(
    classesList:List<TaskClass>,
    onSubmit:(String) -> Unit = {},
    onSkip:() -> Unit = {},
    onDismiss:() -> Unit = {}
) {

    val selectedClass = rememberSaveable{
        mutableStateOf<Int?>(null)
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            backgroundColor = AppTheme.colors.primaryBackground,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = CHOSE_TASK_CLASS,
                        fontSize = 19.sp,
                        color = AppTheme.colors.primaryTitle,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn(
                        modifier = Modifier
                            .height(180.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        itemsIndexed(classesList){index, item ->
                            Button(
                                contentPadding = PaddingValues(0.dp),
                                elevation = ButtonDefaults.elevation(0.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                                onClick = {
                                    if(selectedClass.value == index){
                                        selectedClass.value = null
                                        return@Button
                                    }
                                    selectedClass.value = index
                                }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp)
                                ) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(Color(item.color))
                                        ) {

                                        }
                                        Text(
                                            text = item.title,
                                            fontSize = 15.sp,
                                            color = AppTheme.colors.primaryTitle
                                        )
                                    }
                                    if(index == selectedClass.value){
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription ="",
                                            modifier = Modifier.size(18.dp),
                                            tint = AppTheme.colors.secondPrimaryTitle
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.padding(top = 25.dp, end = 20.dp)
                ) {
                    ClickableText(
                        text = AnnotatedString(SKIP_BTN_TITLE),
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = AppTheme.colors.secondLightPrimaryTitle
                        )
                    ){
                        onSkip()
                    }
                    ClickableText(
                        text = AnnotatedString(CHOOSE_CLASS_BTN_TITLE),
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = if(selectedClass.value != null) AppTheme.colors.secondLightPrimaryTitle else  AppTheme.colors.thirdPrimaryTitle
                        )
                    ){
                        if(selectedClass.value != null) onSubmit(classesList[selectedClass.value!!].id)
                    }
                }

            }
        }
    }

}