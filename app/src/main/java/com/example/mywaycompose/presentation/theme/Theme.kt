package com.example.mywaycompose.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = Colors(
    primaryBackground = Color(0xFF23202B),
    primaryTitle = Color(0xFFFFFFFF),
    secondPrimaryTitle = Color(0xFFA2ACAE),
    secondLightPrimaryTitle = Color(0xFFB46DEF),
    thirdPrimaryTitle = Color(0x80FFFFFF),
    thirdLightPrimaryTitle = Color(0xE6FFFFFF),
    secondPrimaryBackground = Color(0xFF2E2938),
    errorTitle = Color(0xE6CA4444),
    iconColor = Color(0x80FFFFFF),
    thirdPrimaryBackground = Color(0xFF23202B),
    strongPrimaryBackground = Color(0xFF1A1820),
    primaryTitleOpposite = Color(0x80FFFFFF),
    selectedIcon = Color(0xE6FFFFFF)
)

private val LightColorPalette = Colors(
    primaryBackground = Color(0xFFE3D4CB),
    primaryTitle = Color(0xFF261E16),
    secondPrimaryTitle =  Color(0x66261E16),
    secondLightPrimaryTitle = Color(0xFFC48750),
    thirdPrimaryTitle = Color(0x66261E16),
    thirdLightPrimaryTitle = Color(0xFF261E16),
    secondPrimaryBackground = Color(0xFFEBDED6),
    errorTitle = Color(0xE6CA4444),
    iconColor = Color(0xFF23202B),
    thirdPrimaryBackground = Color(0xFFC1B6AF),
    strongPrimaryBackground =Color(0xFFC9BAB1),
    primaryTitleOpposite = Color(0xFFFFFFFF),
    selectedIcon = Color(0xFF261E16)
)

data class Colors(
    val primaryBackground: Color,
    val primaryTitle:Color,
    val secondPrimaryTitle:Color,
    val secondLightPrimaryTitle:Color,
    val thirdPrimaryTitle:Color,
    val thirdLightPrimaryTitle:Color,
    val secondPrimaryBackground:Color,
    val errorTitle:Color,
    val iconColor:Color,
    val thirdPrimaryBackground:Color,
    val strongPrimaryBackground:Color,
    val primaryTitleOpposite:Color,
    val selectedIcon:Color
)

@Composable
fun MyWayComposeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    CompositionLocalProvider(
        LocalColorProvider provides if(darkTheme) DarkColorPalette else LightColorPalette
    ) {
        content()
    }

}

object AppTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColorProvider.current
}


val LocalColorProvider = staticCompositionLocalOf<Colors> {
    error("fddfdd")
}