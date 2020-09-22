package com.example.coex_client.data

import android.content.Context
import android.content.SharedPreferences
import com.example.coex_client.R

class UserSharedPref(context: Context) {
    private var prefs : SharedPreferences = context.getSharedPreferences("UserSharedPref",Context.MODE_PRIVATE)
    companion object{
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
    }

    fun saveAuthToken(token: String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN,token)
        editor.apply()
    }

    fun fetchAuthToken() : String?{
        return prefs.getString(USER_TOKEN,"")
    }


    // Temporary before creating database
    fun saveUserId(id : String){
        val editor = prefs.edit()
        editor.putString(USER_ID,id)
        editor.apply()
    }

    fun fetchUserId() : String?{
        return prefs.getString(USER_ID,"")
    }
}