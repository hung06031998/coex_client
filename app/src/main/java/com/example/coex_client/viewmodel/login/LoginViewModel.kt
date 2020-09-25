package com.example.coex_client.viewmodel.login

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coex_client.data.UserSharedPref
import com.example.coex_client.model.login.LoginRequest
import com.example.coex_client.model.login.LoginResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _loginSuccessfully = MutableLiveData<Boolean>()
    val loginSuccesFully: LiveData<Boolean>
        get() = _loginSuccessfully

    private lateinit var userSharedPref: UserSharedPref

    private lateinit var firebaseToken: String

    init {
        getFirebaseToken()
    }

    fun getFirebaseToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    firebaseToken = task.result?.token.toString()
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                firebaseToken = task.result?.token.toString()
            })
    }

    fun signInAuthorize(email: String, password: String, context: Context) {
        getFirebaseToken()
        userSharedPref = UserSharedPref(context)

        Log.d("Firebase token", firebaseToken)

        val signInfo = LoginRequest(email, password, firebaseToken)

        UserApi.retrofitService.login(signInfo).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse = response.body()
                if (response.code() == 200) {
                    if (loginResponse != null) {
                        userSharedPref.saveAuthToken(loginResponse.token)
                        _loginSuccessfully.value = true
                    }
                    Log.d("Login Token", userSharedPref.fetchAuthToken().toString())

                } else {
                    _loginSuccessfully.value = false
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }
        })

    }

}