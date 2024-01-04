package com.example.mywaycompose.presentation.ui.screen.ideas_pull_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Task
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mywaycompose.domain.model.Idea
import com.example.mywaycompose.presentation.theme.*
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IdeaCardView(
    idea:Idea,
    onDeleteIdea:(Idea) -> Unit,
    onAddPlannerTaskClick:() -> Unit,
    onAddNodeClick:() -> Unit
) {

    val showOptions = rememberSaveable() {
        mutableStateOf(false)
    }

    RevealSwipe(
        modifier = Modifier.padding(vertical = 5.dp),
        directions = setOf(
            RevealDirection.StartToEnd,
            RevealDirection.EndToStart
        ),
        backgroundCardStartColor = StatisticsKindSecondColor,
        maxRevealDp = 140.dp,
        hiddenContentEnd = {
            IconButton(onClick = {
                onDeleteIdea(idea)
            }) {
                Icon(
                    modifier = Modifier.padding(horizontal = 25.dp),
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null
                )
            }

        }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(0.dp))
                .background(AppTheme.colors.strongPrimaryBackground)
                .clickable {
                    showOptions.value = !showOptions.value
                }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 65.dp),
                elevation = 5.dp,
                backgroundColor = AppTheme.colors.secondPrimaryBackground,

                ) {
                Column(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = idea.idea,
                        color = AppTheme.colors.primaryTitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = monsterrat,
                        textAlign = TextAlign.Center
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Divider(
                            modifier = Modifier
                                .size(5.dp)
                                .clip(RoundedCornerShape(100))
                                .background(if (idea.online_sync) Color.Green else Color.Red)
                        )
                    }
                }
            }

            if(showOptions.value) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.padding(vertical = 15.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ClickableText(
                        text = AnnotatedString("Добавить в планнер"),
                        style = MaterialTheme.typography.button.copy(color = AppTheme.colors.primaryTitle),
                        onClick = {
                            onAddPlannerTaskClick()
                        }
                    )
                    ClickableText(
                        text = AnnotatedString("Добавить ноду"),
                        style = MaterialTheme.typography.button.copy(color = AppTheme.colors.primaryTitle),
                        onClick = {
                            onAddNodeClick()
                        }
                    )
                }
            }

        }
    }


}