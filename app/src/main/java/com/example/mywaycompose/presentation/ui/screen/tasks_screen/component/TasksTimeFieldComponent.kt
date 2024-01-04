package com.example.mywaycompose.presentation.ui.screen.tasks_screen.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.HintTextFieldColor
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.presentation.theme.monsterrat

@Composable
fun TasksTimeFieldComponent(
    timeFieldValue:TextFieldValue,
    toSubmitTime:()->Unit,
    toFocus:Boolean,
    onTimeChange:(TextFieldValue) -> Unit
) {

    val focusRequester = remember { FocusRequester() }

    val oneHourFlag = remember {
        mutableStateOf(false)
    }

    BasicTextField(
        value = timeFieldValue,
        onValueChange = {
            try {
                val letter = it.text

                if (letter.isNotEmpty()) {
                    val checkNum = letter.filter { !it.isWhitespace() }.toInt()
                }

                if (letter.length > 6) return@BasicTextField
                if (letter.length > 1) {
                    if ((letter.length < timeFieldValue.text.length) && (letter[letter.length - 1].toString() == " ") && (timeFieldValue.text[timeFieldValue.text.length - 1].toString() == " ")) {
                        val letterWithoutSpace = letter.filter { res -> !res.isWhitespace() }
                        if (letterWithoutSpace.length == 1) {
                            oneHourFlag.value = false
                        }
                        onTimeChange(TextFieldValue(
                            text = if (letterWithoutSpace.length == 1) "" else letterWithoutSpace[0].toString(),
                            selection = TextRange(1)
                        ))
                        return@BasicTextField
                    }
                }

                if (timeFieldValue.text.isEmpty() && it.text[0].toString().toInt() > 2) {
                    oneHourFlag.value = true
                }

                if ((letter.length == 2) && (it.text[0].toString()
                        .toInt() == 2 && it.text[1].toString().toInt() > 3)
                ) {
                    oneHourFlag.value = true
                    onTimeChange(TextFieldValue(
                        text = it.text[0] + "   " + it.text[1],
                        selection = TextRange(letter.length + 3)
                    ))
                    return@BasicTextField
                }

                if (it.text.isEmpty()) {
                    oneHourFlag.value = false
                }

                if (letter.length == 2 && oneHourFlag.value) {
                    onTimeChange(TextFieldValue(
                        text = it.text[0] + "   " + it.text[1],
                        selection = TextRange(letter.length + 3)
                    ))
                    return@BasicTextField
                }

                if (letter.length == 2 && !oneHourFlag.value) {
                    onTimeChange(TextFieldValue(
                        text = it.text + "  ",
                        selection = TextRange(letter.length + 2)
                    ))
                    return@BasicTextField
                }

                onTimeChange(it)
            } catch (e: Exception) {

            }

        },
        cursorBrush = SolidColor(Color.Transparent),
        textStyle = TextStyle(
            color = AppTheme.colors.primaryTitle,
            fontWeight = FontWeight.Bold,
            fontFamily = monsterrat,
            fontSize = 12.sp
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                toSubmitTime()
            }
        ),
        modifier = Modifier
            .height(17.dp)
            .width(43.dp)
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .fillMaxSize()
            ) {
                Box(Modifier.fillMaxSize()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PumpTimeTasks(timeFieldValue.text.isEmpty())
                        PumpTimeTasks(
                            timeFieldValue.text.length <= 1,
                            oneHourFlag.value,
                            timeFieldValue
                        )
                        if (timeFieldValue.text.length > 1 && timeFieldValue.text[0].toString()
                                .toInt() < 2
                        ) Spacer(modifier = Modifier.width(if (timeFieldValue.text[0] != '0') 3.dp else 6.dp))
                        if (timeFieldValue.text.length > 1 && timeFieldValue.text[0].toString()
                                .toInt() > 2
                        ) Spacer(modifier = Modifier.width(4.dp))
                        if (timeFieldValue.text.length > 1 && timeFieldValue.text[0].toString()
                                .toInt() == 2
                        ) Spacer(modifier = Modifier.width(4.dp))
                        DotsDecorationTasksTime()
                        if (timeFieldValue.text.length > 3 || (timeFieldValue.text.length > 2 && oneHourFlag.value)) Spacer(
                            modifier = Modifier.width(3.dp)
                        )
                        PumpTimeTasks(timeFieldValue.text.length <= 4)
                        PumpTimeTasks(timeFieldValue.text.length <= 5)
                    }
                }
            }
            innerTextField()
        }
    )
    if (toFocus){
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
fun PumpTimeTasks(show:Boolean, hide:Boolean = false, numState:TextFieldValue? = null) {
    if(show && !hide){
        HintTasksTime()
    }else{
        if((numState != null)){
            val parsedStr = numState.text.filter { res -> !res.isWhitespace() }
            Log.d("fucking_str", parsedStr)
            if((parsedStr.length > 1) &&parsedStr[1].toString().toInt() == 1){
                Log.d("fucking_str", "parsedStr")
                Divider(Modifier
                    .width((if (parsedStr[0].toString() == "1") (2.5).dp else (4.5).dp))
                    .height(0.dp))
                    return
                }
            if((parsedStr.length > 1) &&parsedStr[1].toString().toInt() == 0){
                Log.d("fucking_str", "parsedStr")
                Divider(Modifier

                    .width((if (parsedStr[0].toString() == "1") (6.2).dp else (8).dp))
                    .height(0.dp))
                return
            }
        }
        Divider(if(hide) Modifier
            .width(2.dp)
            .height(0.dp) else Modifier
            .width((5.5).dp)
            .height(0.dp))
    }



}

@Composable
fun DotsDecorationTasksTime() {
    Column(Modifier.padding(top = 5.dp)) {
        Card(modifier = Modifier.size(3.dp), shape = RoundedCornerShape(360.dp), backgroundColor = AppTheme.colors.primaryTitle) {}
        Spacer(modifier = Modifier.height(2.dp))
        Card(modifier = Modifier.size(3.dp), shape = RoundedCornerShape(360.dp), backgroundColor = AppTheme.colors.primaryTitle) {}
    }
}

@Composable
fun HintTasksTime() {
    Row() {
        Box(Modifier.padding(top = 5.dp)) {
            Divider(
                Modifier
                    .width(9.dp)
                    .height(1.dp)
                    .background(AppTheme.colors.primaryTitle)
            )
        }
        Spacer(modifier = Modifier.width((0.5).dp))
    }
}
