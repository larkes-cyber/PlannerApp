package com.example.mywaycompose.data.source.service

interface ServiceSharedPreferenceDataSource {

    fun putActuallyMainTaskId(id:Int)
    fun getActuallyMainTaskId():Int
    fun putActuallyVisualTaskId(id:Int)
    fun getActuallyVisualTaskId():Int
    fun saveAppTheme(theme:String)
    fun getAppTheme():String

}