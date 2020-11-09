package com.example.coex_client.viewmodel.booking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coex_client.data.UserSharedPref
import com.example.coex_client.model.booking.BookingRequest
import com.example.coex_client.model.booking.BookingRespone
import com.example.coex_client.ui.booking.BookingActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.math.cos

class BookingActivityViewModel : ViewModel() {
    private lateinit var mLive: MutableLiveData<*>
    private lateinit var mLiveSuccess: MutableLiveData<*>
    private lateinit var mLiveFailed: MutableLiveData<*>
    private lateinit var mView: BookingActivity
    private lateinit var _userSharedPref : UserSharedPref

    fun setView(view: BookingActivity) {
        mView = view
        mView.bindView()
        _userSharedPref = UserSharedPref(mView)
    }

    fun getMutableLiveData(): MutableLiveData<*>? {
        return mLive
    }

    fun getSuccessMutableLiveData(): MutableLiveData<*>? {
        return mLiveSuccess
    }

    fun getFailedMutableLiveData(): MutableLiveData<*>? {
        return mLiveFailed
    }

    fun booking(description: String, startTime: String, numPerson: Int, duration: Float, roomId: String){
        val bookingRequest = BookingRequest(description, startTime, numPerson,duration, roomId)
        UserApi.retrofitService.booking(bookingRequest).enqueue(object :
            retrofit2.Callback<BookingRespone> {
            override fun onResponse(
                call: Call<BookingRespone>,
                response: Response<BookingRespone>
            ) {
                mView.bookingRespone(1, "")
            }

            override fun onFailure(call: Call<BookingRespone>, t: Throwable) {
                Log.d("booking", "faillllll")
                mView.bookingRespone(0, "fail")
            }
        })
    }
    fun editBooking(){}
    fun getPrice(duration: Float, numGess: Int, cost: Long){
        mView.showPrice(duration*numGess*cost)
    }
}