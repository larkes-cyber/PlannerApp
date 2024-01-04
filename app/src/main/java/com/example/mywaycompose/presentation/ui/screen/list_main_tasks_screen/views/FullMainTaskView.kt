package com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.bonsai.core.Bonsai
import cafe.adriel.bonsai.core.BonsaiStyle
import cafe.adriel.bonsai.core.node.Branch
import cafe.adriel.bonsai.core.tree.Tree
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.presentation.theme.monsterrat
import com.example.mywaycompose.presentation.ui.component.GraphProviderView
import com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.MainTaskWithVisualTasks
import com.example.mywaycompose.utils.GetGraphCoordinates
import com.robertlevonyan.compose.buttontogglegroup.RowToggleButtonGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.roundToInt


@OptIn(ExperimentalTextApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun FullMainTaskView(
    mainTask: MainTaskWithVisualTasks,
    generateVsId:() -> Int,
    onDeleteNode:(VisualTask) -> Unit = {},
    onPushNode:(VisualTask) -> Unit = {},
    onSettingsClick:() -> Unit = {},
    addNewVisualTask:(VisualTask) -> Unit,
) {

    val showMoreInfo = remember {
        mutableStateOf(false)
    }


    var graph by remember {
        mutableStateOf(mainTask.visualIdeas)
    }


    val visualGraph = GetGraphCoordinates.get(graph)

    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val textMeasurer = rememberTextMeasurer()
    val painter = rememberVectorPainter(Icons.Default.Add)

    val primaryColor = AppTheme.colors.primaryBackground
    val primaryTitleColor = AppTheme.colors.primaryTitle

    val secondPrimary = AppTheme.colors.secondPrimaryBackground
    val textColor = AppTheme.colors.primaryTitle

    fun buildText(text:String,fontSize:Int, size: Size): TextLayoutResult {

        return textMeasurer.measure(
            text = AnnotatedString(text),
            style = TextStyle(
                color = primaryColor,
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

    val switcher = remember {
        mutableStateOf(0)
    }

    fun deleteDeepNodes(visualTask: VisualTask){
        if(visualTask.dependIds.isEmpty()) {
            val last = graph.toMutableList()
            last.remove(visualTask)
            graph = last
            return
        }
        graph.forEach {
            if(it.id in visualTask.dependIds){
                val last = graph.toMutableList()
                last.remove(visualTask)
                graph = last
                deleteDeepNodes(it)
            }
        }
    }

    fun deleteNode(vs: VisualTask){
        if(vs.parentId != "-1") {
            val element = graph.find { it.id == vs.parentId }
            val elIndex = graph.indexOf(element)
            element!!.dependIds.remove(vs.id)
            val last = graph.toMutableList()
            last[elIndex] = element
            graph = last
        }
        onDeleteNode(vs)
        deleteDeepNodes(vs)
        val last = graph.toMutableList()
        last.remove(vs)
        graph = last
    }

    fun findNodes(visualTask: VisualTask, count:Int):Int{
        if(visualTask.dependIds.isEmpty()) {
            return count + 1
        }
        var k = 0
        graph.forEach {
            if(it.id in visualTask.dependIds){
                val last = graph.toMutableList()
                graph = last
                k += findNodes(it, count + 1)
            }
        }
        return k
    }

    Column(
        modifier = Modifier.background(AppTheme.colors.primaryBackground)
    ) {
        ClickableMainTaskView(mainTask = mainTask){
            showMoreInfo.value = !showMoreInfo.value
        }
        if(showMoreInfo.value){
            Card(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp),
                backgroundColor = AppTheme.colors.primaryBackground,
                elevation = 3.dp,
                shape = RoundedCornerShape(bottomEnd = 5.dp, bottomStart = 5.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier.width(280.dp)
                        ) {
                            RowToggleButtonGroup(
                                primarySelection = 0,
                                buttonCount = 2,
                                selectedColor = AppTheme.colors.secondPrimaryBackground,
                                unselectedColor = AppTheme.colors.strongPrimaryBackground,
                                selectedContentColor = AppTheme.colors.secondLightPrimaryTitle,
                                unselectedContentColor = AppTheme.colors.secondPrimaryTitle,
                                elevation = ButtonDefaults.elevation(0.dp),
                                buttonTexts = arrayOf("Проводник", "Визуализация"),
                                buttonHeight = 32.dp,
                                border = BorderStroke(width = 0.dp, color = Color.Transparent),
                            ) { index ->
                                // check index and handle click
                                switcher.value = index

                            }
                        }
                        Box(
                        ) {
                            IconButton(onClick = { onSettingsClick() }) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp),
                                    tint = AppTheme.colors.iconColor
                                )
                            }
                        }

                    }



                    if(switcher.value == 0){
                       GraphProviderView(
                           graph = graph,
                           mainTask = mainTask,
                           generateVsId = {generateVsId()},
                           addNewVisualTask = {vs ->
                               if(vs.parentId != "-1") {
                                   val element = graph.find { it.id == vs.parentId }
                                   val elIndex = graph.indexOf(element)
                                   element!!.dependIds.add(vs.id!!)

                                   val last = graph.toMutableList()
                                   last[elIndex] = element
                                   graph = last
                               }
                               val last = graph.toMutableList()
                               last.add(vs)
                               graph = last
                               addNewVisualTask(vs)
                                              },
                           onDeleteNode = {vs ->
                               deleteNode(vs)

                           },
                           onPushClick = {vs ->
                              // deleteNode(vs)
                               onPushNode(vs)
                           }
                       )
                    }
                    else{
                        Spacer(modifier = Modifier.height(20.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(330.dp),
                            backgroundColor = AppTheme.colors.primaryBackground
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .pointerInput(Unit) {
                                        forEachGesture {
                                            awaitPointerEventScope {
                                                awaitFirstDown()
                                                do {
                                                    val event = awaitPointerEvent()
                                                    scale *= event.calculateZoom()
                                                    val offset = event.calculatePan()
                                                    offsetX += offset.x
                                                    offsetY += offset.y
                                                } while (event.changes.any { it.pressed })
                                            }
                                        }
                                    }
                            ) {

                                Box(
                                    modifier = Modifier
                                        .graphicsLayer(
                                            scaleX = scale,
                                            scaleY = scale,
                                            translationX = offsetX,
                                            translationY = offsetY
                                        )
                                        .fillMaxSize()
                                        .drawBehind {


                                            fun getWidthOfText(text: String): Float {
                                                val taskText =
                                                    buildText(text, fontSize = 12, size = size)
                                                return if ((taskText.size.width.dp * 0.65f) < 75.dp) 75.dp.toPx() else (taskText.size.width.dp * 0.65f).toPx()
                                            }

                                            fun drawBranch(
                                                firstNodeOffset: Offset,
                                                secondNodeOffset: Offset
                                            ) {
                                                drawLine(
                                                    color = primaryTitleColor,
                                                    start = Offset(
                                                        firstNodeOffset.x,
                                                        firstNodeOffset.y
                                                    ),
                                                    end = Offset(
                                                        secondNodeOffset.x,
                                                        secondNodeOffset.y
                                                    ),
                                                    strokeWidth = 2f
                                                )
                                            }

                                            fun drawNode(
                                                text: String,
                                                x: Float,
                                                y: Float
                                            ) {
                                                val taskText =
                                                    buildText(text, fontSize = 12, size = size)
                                                val taskWidth =
                                                    if ((taskText.size.width.dp * 0.65f) < 75.dp) 75.dp.toPx() else (taskText.size.width.dp * 0.75f).toPx()
                                                val path = Path().apply {
                                                    addRoundRect(
                                                        RoundRect(
                                                            rect = Rect(
                                                                offset = Offset(x, y),
                                                                size = Size(
                                                                    width = taskWidth,
                                                                    height = (taskText.size.height.dp + 5.dp).toPx()
                                                                ),
                                                            ),
                                                            bottomRight = CornerRadius(10f, 10f),
                                                            bottomLeft = CornerRadius(10f, 10f),
                                                            topRight = CornerRadius(10f, 10f),
                                                            topLeft = CornerRadius(10f, 10f)
                                                        )
                                                    )
                                                }
                                                drawPath(path, color = secondPrimary)
                                                drawText(
                                                    color = textColor,
                                                    textLayoutResult = taskText,
                                                    topLeft = Offset(
                                                        x = x + (taskWidth.dp / 11f).toPx(),
                                                        y = y + 10.dp.toPx(),
                                                    )
                                                )
                                            }


                                            val n = 80.dp
                                            val m = 54.dp

                                            val renderedNodes =
                                                mutableMapOf<String, MutableList<String>>()

                                            for (i in visualGraph) {
                                                renderedNodes[i.id!!.toString()] = mutableListOf()
                                            }

                                            fun findX(node: VisualTask): Dp {
                                                val parentNode =
                                                    visualGraph.find { it.id == node.parentId }
                                                        ?: return 0.dp
                                                return parentNode.xOffset + n + getWidthOfText(
                                                    parentNode.text
                                                ).toDp()
                                            }

                                            fun findY(node: VisualTask): Dp {
                                                val parentNode =
                                                    visualGraph.find { it.id == node.parentId }
                                                        ?: return 40.dp
                                                val parentDependsNodes =
                                                    visualGraph.filter { it.id in parentNode.dependIds }
                                                val currentNodesIds =
                                                    parentDependsNodes.map { it.dependIds }
                                                val checkIfNodesListIsEmpty =
                                                    currentNodesIds.map { if (it.isEmpty()) 0 else 1 }
                                                val parentRenderedDependsNodes =
                                                    renderedNodes[parentNode.id]

                                                var nodes = 0
                                                parentDependsNodes.filter { it.id !in node.dependIds }.forEach {
                                                    nodes += findNodes(it, 0)
                                                }
                                                Log.d("sfdfgffdfgdfg","${node.text}    $nodes")

                                                var yOffset =
                                                    m.value.dp * (parentRenderedDependsNodes!!.size) + parentNode.yOffset - ((parentDependsNodes.size - 1) * ((13.5).dp.toPx())).dp
                                                if (checkIfNodesListIsEmpty.sum() - 1 > 0) {
                                                    val su = currentNodesIds.map { it.size }
                                                    yOffset += ((parentRenderedDependsNodes.size - 1) * 20.dp.toPx() * su.sum()).dp
                                                }
                                                renderedNodes[parentNode.id]!!.add(node.id!!)
                                                return yOffset
                                            }

                                            for (i in visualGraph) {
                                                i.xOffset = findX(i)
                                                i.yOffset = findY(i)
                                            }

                                            for (i in visualGraph) {
                                                val startNodeX =
                                                    i.xOffset + (getWidthOfText(i.text) / 2f).dp
                                                val startNodeY = i.yOffset + 18.dp
                                                for (dependNodeId in i.dependIds) {
                                                    val dependNode =
                                                        visualGraph.find { it.id == dependNodeId }
                                                    val endX = dependNode!!.xOffset
                                                    val endY = dependNode.yOffset + 18.dp
                                                    drawBranch(
                                                        firstNodeOffset = Offset(
                                                            startNodeX.toPx(),
                                                            startNodeY.toPx()
                                                        ),
                                                        secondNodeOffset = Offset(
                                                            endX.toPx(),
                                                            endY.toPx()
                                                        )
                                                    )
                                                }
                                            }


                                            visualGraph.forEach { task ->
                                                drawNode(
                                                    task.text,
                                                    task.xOffset.toPx(),
                                                    task.yOffset.toPx()
                                                )
                                            }
                                        }
                                    ,
                                    contentAlignment = Alignment.Center
                                ) {

                                }

                            }
                        }
                    }

                }

            }
        }
    }

}