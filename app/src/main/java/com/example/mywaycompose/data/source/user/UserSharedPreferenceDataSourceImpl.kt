package com.example.mywaycompose.data.source.user

import android.content.Context

class UserSharedPreferenceDataSourceImpl(
    private val context: Context
):UserSharedPreferenceDataSource {
    private val sharedPreferences =
        context.getSharedPreferences("AuthData", Context.MODE_PRIVATE)

    override fun saveFirstDate(date: String) {
        sharedPreferences.edit().putString("date",date).apply()
    }

    override fun getFirstDate(): String {
        return sharedPreferences.getString("date","none")!!
    }
}