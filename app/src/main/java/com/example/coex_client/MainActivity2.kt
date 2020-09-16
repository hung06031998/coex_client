package com.example.coex_client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coex_client.data.UserSharedPref
import com.example.coex_client.model.user.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val userSharedPref = UserSharedPref(this)
        val userToken = userSharedPref.fetchAuthToken()
        Log.d("Login Token","$userToken")

        UserApi.retrofitService.getUserMe(token = "Bearer ${userToken}").enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user  = response.body()
                if(response.code() == 200) {
                    Log.d("Login Token", "UserID : ${user?.id}")
                    if (user != null) {
                        userSharedPref.saveUserId(user.id)
                        Log.d("Login Token", "UserId SharedPref : ${userSharedPref.fetchUserId()}")
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {

            }
        })

//        val coworkingRequest = CoworkingRequest("Hanoihub", "About","0123456789","Hanoi","20.993170","105.849449")
        val coworkingRequest : RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("name", "Hanoi Hub")
            .addFormDataPart("about","About")
            .addFormDataPart("location[0]","20.993170")
            .addFormDataPart("location[1]","105.849449")
            .addFormDataPart("phone","0123456789").build()
    }

}