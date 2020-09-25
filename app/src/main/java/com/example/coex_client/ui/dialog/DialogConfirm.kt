package com.upit.coex.user.screen.dialog

import android.app.Activity
import android.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.coex_client.R

class DialogConfirm(
    private val activity: Activity,
    private val title: String,
    private val message: String
) {
    private var dialog: AlertDialog? = null
    private var mOnClickYes: onClickYes? = null
    fun setmOnClickYes(mOnClickYes: onClickYes?) {
        this.mOnClickYes = mOnClickYes
    }

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_confirm, null)
        builder.setView(view)
        builder.setCancelable(false)
        //
        val btnCancel: Button
        val btnAccepted: Button
        val txtTitle: TextView
        val txtMessage: TextView
        txtMessage = view.findViewById(R.id.confirm_dialog_txt_description)
        txtTitle = view.findViewById(R.id.confirm_dialog_txt_title)
        btnAccepted = view.findViewById(R.id.confirm_dialog_btn_accept)
        btnCancel = view.findViewById(R.id.confirm_dialog_btn_cancel)
        txtMessage.text = message
        txtTitle.text = title
        btnAccepted.setOnClickListener { v: View? ->
            mOnClickYes!!.onYesClick()
            dialog!!.dismiss()
        }
        btnCancel.setOnClickListener { v: View? -> dialog!!.dismiss() }
        dialog = builder.show()
    }

    interface onClickYes {
        fun onYesClick()
    }

}