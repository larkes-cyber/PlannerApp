package com.example.mywaycompose.presentation.ui.screen.tasks_screen.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mywaycompose.domain.model.DateServer
import com.example.mywaycompose.domain.model.toDateServer
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.DaysItemColor
import com.example.mywaycompose.presentation.theme.SelectedDayColor
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.presentation.theme.gilory
import com.example.mywaycompose.utils.DateService
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun NavDatesListView(
    days:List<DateServer>,
    toSelect: (DateServer) -> Unit,
    selectedDay:DateServer
) {
    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = days.indexOf(days.find { it.day == selectedDay.day }))

    LaunchedEffect(selectedDay){
        scrollState.scrollToItem(days.indexOf(days.find { it.day == selectedDay.day }))
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth(),
        state = scrollState
    ){
        itemsIndexed(days){ index, item ->
            val isSelected = item.day == selectedDay.day
            Text(
                text = AnnotatedString(DateService.convertDateToProduct(item)),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = gilory,
                    fontWeight = FontWeight.Bold,
                    color = if(isSelected) AppTheme.colors.secondLightPrimaryTitle else AppTheme.colors.secondPrimaryTitle,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal
                ),
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            toSelect(item)
                        }
                    )
            )
        }
    }

}