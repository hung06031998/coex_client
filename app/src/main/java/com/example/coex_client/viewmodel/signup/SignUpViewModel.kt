package com.example.coex_client.viewmodel.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coex_client.model.signup.SignupRequest
import com.example.coex_client.model.signup.SignupResponse
import com.example.coex_client.model.login.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    private val _onSignUpSuccessfully = MutableLiveData<Boolean>()
    val onSignUpSucssessfully: LiveData<Boolean>
        get() = _onSignUpSuccessfully

    fun signUp(fullname: String, email: String, phoneNumber: String, password: String) {
        val signupInfo = SignupRequest(fullname, email, phoneNumber, password)

        UserApi.retrofitService.signup(signupInfo).enqueue(object : Callback<SignupResponse> {

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                _onSignUpSuccessfully.value = response.code() == 200
            }
        })
    }

    fun onSignUpComplete(){
        _onSignUpSuccessfully.value = false
    }
}