package com.example.mywaycompose.presentation.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.TaskStrokeColor
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.presentation.theme.monsterrat
import com.example.mywaycompose.presentation.ui.screen.edit_task_screen.views.FormWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskFieldView(
    modifier: Modifier = Modifier,
    hint:String = "",
    text:String,
    textHintTextStyle:TextStyle = TextStyle(
        color = AppTheme.colors.primaryTitle,
        fontWeight = FontWeight.Medium,
        fontFamily = monsterrat,
        fontSize = 14.sp
    ),
    visibleStroke:Boolean = true,
    maxLen:Int? = null,
    focus:Boolean = false,
    showCheckMark:Boolean = false,
    onDoneListener:() -> Unit = {},
    callback:(String) -> Unit
) {

    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(focus){
        if(focus) focusRequester.requestFocus()
    }

    FormWrapper(
        modifier = modifier,
        visibleStroke = visibleStroke
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    if(maxLen != null && it.length > maxLen) return@BasicTextField
                    callback(it)
                },
                modifier = modifier
                    .focusRequester(focusRequester)
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .weight(7f)
                    .onFocusEvent {
                        if (it.isFocused) {
                            scope.launch {
                                delay(200)
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
                ,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onDoneListener()
                    }
                ),
                cursorBrush = SolidColor(AppTheme.colors.primaryTitle),
                textStyle = TextStyle(
                    color = AppTheme.colors.primaryTitle,
                    fontWeight = FontWeight.Medium,
                    fontFamily = monsterrat,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                decorationBox = {
                    if(text.isEmpty()){
                        Text(
                            text = hint,
                            style = textHintTextStyle
                        )
                    }
                    it()
                }
            )
            if(showCheckMark) {
                Box(modifier = Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        tint = AppTheme.colors.primaryTitle,
                        contentDescription = "",
                        modifier = Modifier
                            .clickable {
                                onDoneListener()
                            }
                    )
                }
            }

        }
    }

}