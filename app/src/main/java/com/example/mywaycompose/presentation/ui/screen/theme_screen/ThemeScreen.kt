package com.example.mywaycompose.presentation.ui.screen.theme_screen

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mywaycompose.R
import com.example.mywaycompose.presentation.theme.AppTheme
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.presentation.theme.monsterrat


@Composable
fun ThemeScreen(
    isLightTheme:Boolean,
    navController: NavController,
    onThemeClick:() -> Unit
) {

    val mode = remember {
        mutableStateOf(isLightTheme)
    }


    val transition = updateTransition(
        targetState = mode.value,
        label = ""
    )

    val shift  by transition.animateDp(
        transitionSpec = { tween(
            durationMillis = 200,
            easing = FastOutLinearInEasing
        ) },
        label = "",
        targetValueByState = {isSelect ->
            if(isSelect) 100.dp else 0.dp
        }
    )

    val offset by transition.animateDp(
        transitionSpec = { tween(
            durationMillis = 200,
            easing = FastOutLinearInEasing
        ) },
        label = "",
        targetValueByState = {isSelect ->
            if(!isSelect) (-180).dp else (-40).dp
        }
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.primaryBackground),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                    Image(
                        painter = painterResource(id = if(!mode.value) R.drawable.ellipse_light_theme else R.drawable.ellipse_night_theme),
                        contentDescription = "",
                        modifier = Modifier.size(140.dp),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .offset(20.dp, offset)
                            .clip(RoundedCornerShape(100))
                            .background(AppTheme.colors.primaryBackground)
                            .size(130.dp)

                    )
                }

            }

            item {
                Text(
                    text = "Выберете тему",
                    fontSize = 20.sp,
                    color = AppTheme.colors.primaryTitle,
                    fontFamily = monsterrat,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Настройте интерфейс на свой вукс. \n" +
                            "День или ночь.",
                    fontSize = 12.sp,
                    color = AppTheme.colors.primaryTitle.copy(alpha = 0.8f),
                    fontFamily = monsterrat,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }

            item{
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(AppTheme.colors.strongPrimaryBackground)
                ) {
                    Box(
                        modifier = Modifier.padding(start = shift)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(40.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(AppTheme.colors.secondPrimaryBackground)
                                .align(Alignment.CenterEnd)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SwitchText(text = "Светлая", selected = !mode.value){
                            mode.value = false
                            onThemeClick()
                        }
                        SwitchText(text = "Тёмная", selected = mode.value){
                            mode.value = true
                            onThemeClick()
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(end = 30.dp, bottom = 60.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = AppTheme.colors.secondPrimaryBackground),
                shape = RoundedCornerShape(100),
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(painter = painterResource(
                        id = R.drawable.rightarrow),
                        contentDescription = "",
                        tint = AppTheme.colors.iconColor,
                        modifier = Modifier
                            .width(6.dp)
                            .height(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SwitchText(text:String, selected:Boolean, callback:() -> Unit){
    ClickableText(
        text = AnnotatedString(text),
        style = TextStyle(
            fontSize = 14.sp,
            color = if(selected) AppTheme.colors.primaryTitle else AppTheme.colors.primaryTitleOpposite,
            fontFamily = monsterrat,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        ),
        onClick = {
            if(!selected) callback()
        }
    )
}


//Card(
//modifier = Modifier
//.width(200.dp)
//.height(40.dp),
//backgroundColor = AppTheme.colors.strongPrimaryBackground,
//shape = RoundedCornerShape(20.dp)
//) {
//    Box(modifier = Modifier
//        .fillMaxSize()
//        .drawBehind {
//            val path = Path().apply {
//                addRoundRect(
//                    RoundRect(
//                        rect = Rect(
//                            offset = Offset(shift.value, 0f),
//                            size = Size(size.width / 1.8f, size.height),
//                        ),
//                        bottomRight = CornerRadius(50f, 50f),
//                        topRight = CornerRadius(50f, 50f),
//                        topLeft = CornerRadius(50f, 50f),
//                        bottomLeft = CornerRadius(50f, 50f)
//                    )
//                )
//            }
//            drawPath(
//                path,
//                color = secondColor
//            )
//        }
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 20.dp)
//        ) {

//        }
//    }
//
//}