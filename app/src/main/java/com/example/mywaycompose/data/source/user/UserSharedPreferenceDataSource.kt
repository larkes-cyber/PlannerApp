package com.example.mywaycompose.data.source.user

interface UserSharedPreferenceDataSource {
    fun saveFirstDate(date:String)
    fun getFirstDate():String
}