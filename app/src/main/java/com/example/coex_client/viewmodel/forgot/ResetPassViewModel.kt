package com.example.coex_client.viewmodel.forgot

import UserApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coex_client.model.forgot.ResetRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPassViewModel : ViewModel() {
    private val _onResetSuccessfully = MutableLiveData<Boolean>()
    val onResetSuccessfully: LiveData<Boolean>
        get() = _onResetSuccessfully

    fun resetPass(email: String, otp: String, newPass: String, confPass: String) {
        val resetRequest = ResetRequest(email, otp, newPass, confPass)
        UserApi.retrofitService.resetPassword(resetRequest)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    _onResetSuccessfully.value = response.code() == 200
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                }
            }

            )
    }

    fun onResetPassComplete(){
        _onResetSuccessfully.value = false
    }
}