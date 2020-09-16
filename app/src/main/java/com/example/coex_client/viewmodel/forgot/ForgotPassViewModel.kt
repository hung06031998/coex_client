package com.example.coex_client.viewmodel.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coex_client.data.UserSharedPref
import com.example.coex_client.model.forgot.ForgotRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPassViewModel : ViewModel() {

    private val _onSendSuccessfully = MutableLiveData<Boolean>()
    val onSendSuccessfully: LiveData<Boolean>
        get() = _onSendSuccessfully

    private lateinit var userSharedPref: UserSharedPref
    fun forgotPass(email: String) {
        val forgotRequest = ForgotRequest(email)
        UserApi.retrofitService.forgotPassword(forgotRequest).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                _onSendSuccessfully.value = response.code() == 200
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }



}
