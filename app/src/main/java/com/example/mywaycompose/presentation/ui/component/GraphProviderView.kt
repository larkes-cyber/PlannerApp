package com.example.mywaycompose.presentation.ui.component

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.bonsai.core.Bonsai
import cafe.adriel.bonsai.core.BonsaiScope
import cafe.adriel.bonsai.core.BonsaiStyle
import cafe.adriel.bonsai.core.node.Branch
import cafe.adriel.bonsai.core.node.Node
import cafe.adriel.bonsai.core.node.NodeComponent
import cafe.adriel.bonsai.core.tree.Tree
import cafe.adriel.bonsai.core.tree.TreeScope
import com.example.mywaycompose.domain.model.MainTask
import com.example.mywaycompose.domain.model.VisualTask
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.presentation.theme.monsterrat
import com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.MainTaskWithVisualTasks
import com.example.mywaycompose.presentation.ui.screen.list_main_tasks_screen.views.NodeForm
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GraphProviderView(
    graph: List<VisualTask>,
    fontSize:Int = 13,
    offMode:Boolean = false,
    onDeleteNode:(VisualTask) -> Unit = {},
    onNodeClick:(VisualTask) -> Unit = {},
    onPushClick:(VisualTask) -> Unit = {},
    mainTask:MainTaskWithVisualTasks,
    generateVsId:() -> Int,
    addNewVisualTask:(VisualTask) -> Unit
) {


    val rememberedTree = remember {
        mutableStateOf<Tree<String>?>(null)
    }

    val selectedNodes = remember {
        mutableStateListOf<Node<String>>()
    }

    fun onClick(node:VisualTask){
        val currentNode = rememberedTree.value!!.nodes.find { it.content == node.text }
        if(currentNode !in selectedNodes){
            selectedNodes.add(currentNode!!)
            onNodeClick(node)
        }

        val adjacentNodes:List<String> = if(node.parentId != "-1") {
            val adjacentNodesIds = graph.find { it.id == node.parentId }!!.dependIds
            graph.filter { it.id in adjacentNodesIds && it.id != node.id }.map { it.text }
        }else{
            graph.filter { it.parentId == "-1" && it.text != node.text }.map { it.text }
        }

        selectedNodes
            .filter { it.content in adjacentNodes }
            .forEach {
                rememberedTree.value!!.collapseNode(it)
                selectedNodes.remove(it)
            }

    }

    @Composable
    fun NodeContent(
        node:VisualTask,
        nodeScope:BonsaiScope<String>? = null
    ){

        val showAddNextNodeForm = remember {
            mutableStateOf(false)
        }
        val formText = remember {
            mutableStateOf("")
        }

        val showDeleteIcon = remember {
            mutableStateOf(false)
        }

        val error = remember {
            mutableStateOf("")
        }

        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = node.text,
                    style = TextStyle(
                        fontSize = fontSize.sp,
                        color = AppTheme.colors.primaryTitle,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = monsterrat
                    ),
                    modifier = Modifier.combinedClickable(
                        onClick = {
                            if(showAddNextNodeForm.value) showAddNextNodeForm.value = false
                            formText.value = ""
                            showDeleteIcon.value = false
                        },
                        onLongClick = {
                            if(node.parentId != "-1") showDeleteIcon.value = true
                        }
                    )
                )
                if(!offMode){
                    if(!showAddNextNodeForm.value){
                        Box(modifier = Modifier.padding(start = 10.dp, top = 5.dp)) {
                            Row() {
                                if(showDeleteIcon.value) {
                                    IconButton(
                                        onClick = {
                                            onDeleteNode(node)
                                        },
                                        modifier = Modifier.size(14.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "",
                                            modifier = Modifier.size(14.dp),
                                            tint = AppTheme.colors.primaryTitle
                                        )
                                    }
                                }else{
                                    IconButton(
                                        onClick = {
                                            showAddNextNodeForm.value = !showAddNextNodeForm.value
                                        },
                                        modifier = Modifier.size(14.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Add,
                                            contentDescription = "",
                                            modifier = Modifier.size(14.dp),
                                            tint = AppTheme.colors.primaryTitle
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(7.dp))
                                    if(node.dependIds.isEmpty() && node.parentId != "-1") {
                                        IconButton(
                                            onClick = {
                                                onPushClick(node)
                                            },
                                            modifier = Modifier.size(14.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.ArrowForward,
                                                contentDescription = "",
                                                modifier = Modifier.size(14.dp),
                                                tint = AppTheme.colors.primaryTitle
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }else{
                        NodeForm(
                            text = formText.value,
                            onChange = {formText.value = it}
                        ){
                            if(formText.value.length > 3) {
                                error.value = ""
                                val genId = generateVsId()
                                val vsTask = VisualTask(
                                    id = UUID.randomUUID().toString(),
                                    dependIds = mutableListOf(),
                                    text = formText.value,
                                    mainTaskId = mainTask.id,
                                    parentId = node.id!!
                                )
                                showAddNextNodeForm.value = !showAddNextNodeForm.value
                                addNewVisualTask(vsTask)
                                formText.value = ""
                            }else{
                                error.value = "размер"
                            }
                        }
                        Text(
                            text = error.value,
                            color = Color(0xFFB3261E),
                            style = MaterialTheme.typography.overline
                        )
                    }
                }
            }
        }
    }
    var w = 0f

    val tree = Tree<String> {
        val arr = graph.filter { it.parentId == "-1" }

        for(n1 in arr){
            Branch(
                n1!!.text,
                customName = {
                    NodeContent(
                        n1,
                        this@Branch
                    )
                }
            ) {
                w += 3.1f
                for (i1 in n1.dependIds) {
                    onClick(n1)
                    val n2 = graph.find { it.id == i1 }
                    Branch( n2!!.text,
                        customName = {
                            NodeContent(
                                node = n2
                            )
                        }) {
                        for (i2 in n2.dependIds) {
                            onClick(n2)
                            val n3 = graph.find { it.id == i2 }

                            Branch( n3!!.text,
                                customName = {
                                    NodeContent(
                                        node = n3
                                    )
                                }){
                                for (i3 in n3.dependIds) {
                                    onClick(n3)
                                    Log.d("sdfsdsdsfsdf",n3.dependIds.toString())
                                    val n4 = graph.find { it.id == i3 }
                                    Branch(n4!!.text,
                                        customName = {
                                            NodeContent(
                                                node = n4
                                            )
                                        }){
                                        for (i4 in n4.dependIds) {
                                            onClick(n4)
                                            val n5 = graph.find { it.id == i4 }
                                            Branch(n5!!.text,
                                                customName = {
                                                    NodeContent(
                                                        node = n5
                                                    )
                                                }
                                            ){
                                                for (i5 in n5.dependIds) {
                                                    onClick(n5)
                                                    val n6 = graph.find { it.id == i5 }
                                                    Branch(n6!!.text,
                                                        customName = {
                                                            NodeContent(
                                                                node = n6
                                                            )
                                                        }
                                                    ){
                                                        for (i6 in n6.dependIds) {
                                                            onClick(n6)
                                                            val n7 = graph.find { it.id == i6 }
                                                            Branch(n7!!.text,
                                                                customName = {
                                                                    NodeContent(
                                                                        node = n7
                                                                    )
                                                                }
                                                            ){
                                                                for (i7 in n7.dependIds) {
                                                                    onClick(n7)
                                                                    val n8 = graph.find { it.id == i7 }
                                                                    Branch(n8!!.text,
                                                                        customName = {
                                                                            NodeContent(
                                                                                node = n8
                                                                            )
                                                                        }
                                                                    ){
                                                                        for (i8 in n8.dependIds) {
                                                                            onClick(n8)
                                                                            val n9 = graph.find { it.id == i8 }
                                                                            Branch(n9!!.text,
                                                                                customName = {
                                                                                    NodeContent(
                                                                                        node = n9
                                                                                    )
                                                                                }
                                                                            ){
                                                                                for (i9 in n9.dependIds) {
                                                                                    onClick(n9)
                                                                                    val n10 = graph.find { it.id == i9 }
                                                                                    Branch(n10!!.text,
                                                                                        customName = {
                                                                                            NodeContent(
                                                                                                node = n10
                                                                                            )
                                                                                        }
                                                                                    ){
                                                                                        for (i10 in n10.dependIds) {
                                                                                            onClick(n10)
                                                                                            val n11 = graph.find { it.id == i10 }
                                                                                            Branch(n11!!.text,
                                                                                                customName = {
                                                                                                    NodeContent(
                                                                                                        node = n11
                                                                                                    )
                                                                                                }
                                                                                            ){
                                                                                                for (i11 in n11.dependIds) {
                                                                                                    onClick(n11)
                                                                                                    val n12 = graph.find { it.id == i11 }
                                                                                                    Branch(n12!!.text,
                                                                                                        customName = {
                                                                                                            NodeContent(
                                                                                                                node = n12
                                                                                                            )
                                                                                                        }
                                                                                                    ){

                                                                                                    }
                                                                                                }

                                                                                            }
                                                                                        }

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    rememberedTree.value = tree


    Bonsai(
        rememberedTree.value!!,
        style = BonsaiStyle(
            toggleIconColorFilter = ColorFilter.tint(AppTheme.colors.primaryTitle)
        ),
        onClick = {
            Log.d("fsdfsdfsdfsd","##############")
            tree.clearSelection()
            tree.toggleExpansion(it)
        },
        onDoubleClick = { node -> Log.d("fsdfsdfsdfsd","##############") },
        onLongClick = { node -> Log.d("fsdfsdfsdfsd","##############") }
    )

}

@Composable
fun Lolka() {
    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
}
