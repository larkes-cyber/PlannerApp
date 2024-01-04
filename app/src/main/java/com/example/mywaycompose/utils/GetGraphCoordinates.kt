package com.example.mywaycompose.utils

import android.util.Log
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mywaycompose.domain.model.VisualTask
import java.lang.Integer.sum
import kotlin.math.atan2

//val t = 60
//val w = 3.1f
//val c = 110
//val h = 60
//visualGraph.forEach { task ->
//    val needTask = visualGraph.find { it.id == task.parentId }
//    Log.d("sfgdfgdfgdfg","${task.text}  ${task.xOffset} ${task.yOffset}")
//    drawNode(text = task.text, x = getSizeOfText(needTask?.text ?: "").width.toFloat(), y = task.yOffset.toPx())
//}
//                                        drawNode(text = "main task", x = 0.dp.toPx(), y = 110.dp.toPx())
//                                        drawNode(text = "subtask1", x = getSizeOfText("main task").width * w, y = (c - t).dp.toPx() ) //+-60 | *3.1f
//                                        drawNode(text = "subtask2", x = getSizeOfText("main task").width * w, y = (c + (h*1) - t).dp.toPx() ) //+-60 | *3.1f
//                                        drawNode(text = "subtask3", x = getSizeOfText("main task").width * w, y = (c + (h*2) - t).dp.toPx() ) //+-60 | *3.1f

//                                        drawNode(text = "subtask2", x = getSizeOfText("main task").width * w, y = (110 - t).dp.toPx() )
//                                        drawNode(text = "subtask2", x = getSizeOfText("main task").width * w, y = (170 - t).dp.toPx() )
//                                        drawNode(text = "subtask3", x = getSizeOfText("main task").width * 3.1f, y = (230-t).dp.toPx() )
//                                        drawNode(text = "subtask3", x = getSizeOfText("main task").width * 3.1f, y = (290-t).dp.toPx() )

object GetGraphCoordinates {

    fun get(list:List<VisualTask>):List<VisualTask>{

        val n = 20.dp
        val m = 35.dp

        val renderedNodes = mutableMapOf<String, MutableList<String>>()

        for(i in list){
            renderedNodes[i.id!!] = mutableListOf()
        }

        fun findX(node:VisualTask):Dp{
            val parentNode = list.find { it.id == node.parentId } ?: return 0.dp
            return parentNode.xOffset + n
        }
        fun findY(node:VisualTask):Dp{
            val parentNode = list.find { it.id == node.parentId } ?: return 40.dp
            val parentDependsNodes = list.filter { it.id in parentNode.dependIds }
            val currentNodesIds = parentDependsNodes.map { it.dependIds }
            val checkIfNodesListIsEmpty = currentNodesIds.map { if(it.isEmpty()) 0 else 1 }
            val g:Int = (checkIfNodesListIsEmpty.sum() - 1)
            val k = (checkIfNodesListIsEmpty.sum() - 1) * m.value
            val parentRenderedDependsNodes = renderedNodes[parentNode.id]
            if(parentRenderedDependsNodes!!.isEmpty()){
                renderedNodes[parentNode.id]!!.add(node.id!!)
                return parentNode.yOffset + k.dp
            }
            val yOffset = k.dp * (parentRenderedDependsNodes.size - 1) + parentNode.yOffset
            renderedNodes[parentNode.id]!!.add(node.id!!)
            return yOffset
        }

        for(i in list){
            i.xOffset = findX(i)
            i.yOffset = findY(i)
        }
        return list
    }

}