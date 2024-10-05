package com.example.m11_timer_data_storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

private const val PREFERENCE_NAME = "p_name"
private const val LAST_INPUT_USER = "l_i_user"

class Repository(context: Context) {
    private var localString: String? = null
    private var prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
    }

    private fun getDataFromSharedPreference() = prefs.getString(LAST_INPUT_USER, null)

    private fun getDataFromLocalVariable() = localString

    fun saveText(text: String) {
        prefs.edit()?.putString(LAST_INPUT_USER, text)?.apply()
        localString = text
    }

    fun getText(): String {
        return when {
            getDataFromLocalVariable() != null -> getDataFromLocalVariable().toString()
            getDataFromSharedPreference() != null -> getDataFromSharedPreference().toString()
            else -> ""
        }
    }

    fun clearText() {
        prefs.edit()?.remove(LAST_INPUT_USER)?.apply()
        localString = ""
    }
}