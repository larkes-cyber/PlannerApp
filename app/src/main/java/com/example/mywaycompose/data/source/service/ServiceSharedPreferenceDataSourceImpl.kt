package com.example.mywaycompose.data.source.service

import android.content.Context
import com.example.mywaycompose.utils.Constants

class ServiceSharedPreferenceDataSourceImpl(
    private val context: Context
):ServiceSharedPreferenceDataSource {

    private val idSharedPreferences = context.getSharedPreferences("id_storage", Context.MODE_PRIVATE)
    private val themeSharedPreferences = context.getSharedPreferences("app_theme", Context.MODE_PRIVATE)


    override fun putActuallyMainTaskId(id: Int) {
        idSharedPreferences.edit().putInt("main_task_id",id).apply()
    }

    override fun getActuallyMainTaskId(): Int {
        return idSharedPreferences.getInt("main_task_id",-1)
    }

    override fun putActuallyVisualTaskId(id: Int) {
        idSharedPreferences.edit().putInt("visual_task_id",id).apply()
    }

    override fun getActuallyVisualTaskId(): Int {
        return idSharedPreferences.getInt("visual_task_id",-1)
    }

    override fun saveAppTheme(theme: String) {
        themeSharedPreferences.edit().putString("app_theme",theme).apply()
    }

    override fun getAppTheme(): String {
        return themeSharedPreferences.getString("app_theme", Constants.DAY_MAIN_THEME)!!
    }
}