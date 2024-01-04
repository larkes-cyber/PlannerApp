package com.example.mywaycompose.presentation.ui.screen.tasks_screen.views

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mywaycompose.domain.mapper.toDarknesColor
import com.example.mywaycompose.domain.model.*
import com.example.mywaycompose.presentation.theme.*
import com.example.mywaycompose.presentation.ui.screen.tasks_screen.component.OptionButtonsBar
import com.example.mywaycompose.utils.Constants.TASKS_HORIZONTAL_PADDINGS
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class, ExperimentalTextApi::class)
@Composable
fun PrimaryTaskView(
    task:Task,
    callbackEdit:() -> Unit = {},
    callbackRemove:() -> Unit = {},
    callBackComplete:() -> Unit = {},
    callbackToIdeas:() -> Unit = {},
    callbackCheck:() -> Unit = {},
    showPrimaryText:Boolean = true,
    longTaskProgress:Float = 0f,
    timeTextFontSize:Int = 16
) {



    val showOptionsState = remember {
        mutableStateOf(false)
    }

    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }

    val localDensity = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    val taskTextHeightState = remember {
        mutableStateOf(0.dp)
    }

    val titleColor = AppTheme.colors.primaryTitle
    val simpleTaskBackground = AppTheme.colors.thirdPrimaryBackground


    fun buildText(text:String,fontSize:Int, size:Size):TextLayoutResult{
       return textMeasurer.measure(
            text = AnnotatedString(text),
            style = TextStyle(
                color = titleColor,
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = monsterrat
            ),
            constraints = Constraints(
                maxWidth = size.width.roundToInt(),
                maxHeight = size.height.roundToInt()
            )
        )
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = TASKS_HORIZONTAL_PADDINGS)
            .padding(vertical = 3.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 75.dp)
                .combinedClickable(
                    onClick = {
                        if (!showOptionsState.value) callbackEdit()
                        showOptionsState.value = false

                    },
                    onLongClick = { showOptionsState.value = true },
                )
                .onGloballyPositioned { coordinates ->
                    if (columnHeightDp.value == 0f) columnHeightDp =
                        with(localDensity) { coordinates.size.height.toDp() }
                },
            shape = RoundedCornerShape(10.dp),
            elevation = 3.dp,
            backgroundColor = AppTheme.colors.secondPrimaryBackground
        ) {

            Column(
                modifier = Modifier.background(AppTheme.colors.primaryBackground)
            ) {

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colors.secondPrimaryBackground)
                    .padding(end = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Box(Modifier
                        .width(220.dp)) {

                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(75.dp + taskTextHeightState.value)
                                .padding(horizontal = 18.dp)
                                .drawBehind {

                                    var taskText = buildText(task.task, fontSize = 12, size = size)
                                    val timeText = buildText(
                                        task.time,
                                        fontSize = timeTextFontSize,
                                        size = size
                                    )

                                    val primaryTextValue = if (task.idSubTask == null) task.mainTaskTitle!! else task.subtaskTitle!!

                                    val primaryText = buildText(
                                        if(primaryTextValue.length > 16) primaryTextValue.substring(0..16) + "..." else primaryTextValue,
                                        fontSize = 12,
                                        size = size / 1.2f
                                    )

                                    val taskTextHeight = taskText.size.height
                                    if (taskTextHeight.toDp() > 30.5.dp) {
                                        taskTextHeightState.value =
                                            taskTextHeight.toDp() / 1.1f
                                    }

                                    if(task.task.length > 84){
                                        taskText = buildText(task.task.substring(0,80)+"....", fontSize = 12, size = size)
                                    }


                                    val path = Path().apply {
                                        addRoundRect(
                                            RoundRect(
                                                rect = Rect(
                                                    offset = Offset(0f, 0f),
                                                    size = Size(size.width / 1.9f, size.height),
                                                ),
                                                bottomRight = CornerRadius(100f, 125f)
                                            )
                                        )
                                    }
                                    drawWithLayer {

                                        fun drawShape() {
                                            if (task.idSubTask != null) {
                                                drawPath(
                                                    path,
                                                    color = task.subtaskColor!!.toDarknesColor(),
                                                    blendMode = BlendMode.SrcOut
                                                )
                                            } else {
                                                drawPath(
                                                    path,
                                                    color = simpleTaskBackground,
                                                    blendMode = BlendMode.SrcOut
                                                )
                                            }
                                        }


                                    }


                                }
                        )
                        Box(
                            modifier = Modifier
                                .background(if (task.idSubTask != null) task.subtaskColor!!.toDarknesColor() else simpleTaskBackground)
                                .width(18.dp)
                                .height(75.dp + taskTextHeightState.value)

                        ) {

                        }
                        GradeView(
                            num = task.grade!!
                        )

                    }
                    if(task.mainTaskImage != null){
                        Box(modifier = Modifier
                            .width(116.dp)
                            .height(65.dp)
                            .clip(RoundedCornerShape(12.dp))
                        ) {
                            AsyncImage(
                                model = task.mainTaskImage,
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }

//    GradeView(
//        colors = colors,
//        num = task.grade!!
//    )


}

fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}