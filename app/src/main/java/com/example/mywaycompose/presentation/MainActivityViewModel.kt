package com.example.mywaycompose.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mywaycompose.domain.usecase.local_service.UseGetAppTheme
import com.example.mywaycompose.domain.usecase.local_service.UseSaveAppTheme
import com.example.mywaycompose.presentation.theme.ThemeColors
import com.example.mywaycompose.utils.Constants
import com.example.mywaycompose.utils.Constants.DAY_MAIN_THEME
import com.example.mywaycompose.utils.Constants.NIGHT_MAIN_THEME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val useGetAppTheme: UseGetAppTheme,
    private val useSaveAppTheme: UseSaveAppTheme
):ViewModel() {

    private val _currentMainThemeColors = MutableStateFlow(DAY_MAIN_THEME)
    val currentMainThemeColors:StateFlow<String> = _currentMainThemeColors

    init {
        switchMainThemeColors(useGetAppTheme.execute())
    }

    fun switchMainThemeColors(theme:String? = null){
        if(theme != null){
            _currentMainThemeColors.value = theme
        }else{
            if(currentMainThemeColors.value == DAY_MAIN_THEME)  _currentMainThemeColors.value = NIGHT_MAIN_THEME
            else  _currentMainThemeColors.value = DAY_MAIN_THEME
            useSaveAppTheme.execute(currentMainThemeColors.value)
        }
    }

}