package com.example.coex_client.service

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class CoexFirebaseInstanceIdService : FirebaseInstanceIdService(){
    val TAG = "PushNotiService"
    lateinit var name : String

    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().getToken();

        super.onTokenRefresh()
    }
}