package com.example.coex_client

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coex_client.data.UserSharedPref
import com.example.coex_client.ui.map.MapsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var userToken = UserSharedPref(this).fetchAuthToken()
        if (userToken == ""){
            val loginIntent = Intent(this, MainLoginActivity::class.java)
            startActivity(loginIntent)
        }
        else{
            val homeIntent = Intent(this, MapsActivity::class.java)
            startActivity(homeIntent)
        }
    }
}