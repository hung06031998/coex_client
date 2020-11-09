package com.example.coex_client.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import com.example.coex_client.R
import com.example.coex_client.ui.booking.BookingActivity

class DialogLoading(bookingActivity: BookingActivity) {
    private lateinit var activity: Activity
    private lateinit var myDialog: Dialog
    private var isStart = false

    fun startLoadingDialog() {
        if (!isStart) {
            isStart = true
            myDialog = Dialog(
                activity!!,
                R.style.Theme_AppCompat_Light_Dialog_Alert
            )
            myDialog.setContentView(R.layout.dialog_load)
            myDialog!!.show()
            myDialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog!!.setCanceledOnTouchOutside(false) //bam ra ngoai
            myDialog!!.setCancelable(false) //bam nut back
        }
    }

    fun dissLoadingDialog() {
        if (isStart) {
            isStart = false
            Log.d("hehe", "dissLoadingDialog")
            if (myDialog != null && myDialog!!.isShowing) {
                Log.d("hehe", "dissLoadingDialog != null")
                myDialog!!.dismiss()
            }
        }
    }

    fun checkLoading(): Boolean {
        return isStart
    }
}