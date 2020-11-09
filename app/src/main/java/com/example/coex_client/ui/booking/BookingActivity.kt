package com.example.coex_client.ui.booking

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.coex_client.R
import com.example.coex_client.model.booking.BookingRespone
import com.example.coex_client.model.map.CWModel
import com.example.coex_client.model.map.RoomModel
import com.example.coex_client.ui.detail.DetailItemAdapter
import com.example.coex_client.ui.dialog.DialogLoading
import com.example.coex_client.viewmodel.booking.BookingActivityViewModel

class BookingActivity : AppCompatActivity(), DetailItemAdapter.OnItemClickListener{
    private lateinit var mDialogLoading: DialogLoading
    private lateinit var mViewModal: BookingActivityViewModel
    private lateinit var edtNumberGuest: EditText
    private  lateinit var edtDuration:EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnBack: ImageView
    private lateinit var txtCost: TextView
    private lateinit var edtStyleRoom: TextView
    private  lateinit var edtTime:TextView
    private lateinit var data: CWModel
    private lateinit var roomNow: RoomModel
    private var type = -1
    private lateinit var bookingId: String
    private lateinit var myDialog: Dialog
    private var costNow: Long = -1
    private var style = -1
//    private val listDate: ArrayList<ListDate>? = null
    private lateinit var bookingRespone: BookingRespone
//    private val mDialogTimePicker: DialogTimePicker? = null
//    private val mDialogLoading: DialogLoading? = null
//    private val mCalender: Calendar? = null
    private lateinit var txtOversizePeople: TextView
    private var checkShowOverPeople = false
    private lateinit var layoutShowPrice: LinearLayout
    private lateinit var proShowPrice: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Booking Activity", "oncreate")
        setContentView(R.layout.activity_booking)
        mViewModal = ViewModelProviders.of(this).get(BookingActivityViewModel::class.java)
        mViewModal.setView(this)
        style = intent.getIntExtra("type", -1)
        if (style == 1) {
            data = intent.getSerializableExtra("key") as CWModel
            createDialog()
        } else if (style == 2) {
            Toast.makeText(this, "chua lam", Toast.LENGTH_SHORT).show()
//            bookingRespone = intent.getSerializableExtra("key") as BookingInfoRespone?
//            edtDuration.setText(bookingRespone.getDuration().toString() + "")
//            edtNumberGuest.setText(bookingRespone.getNumPerson().toString() + "")
//            edtStyleRoom.setText(bookingRespone.getRoom().getName())
//            edtDuration.setHint(" hour(s)")
//            edtNumberGuest.setHint(bookingRespone.getRoom().getMaxPerson().toString() + " Guest(s)")
//            val d = Date(bookingRespone.getDateTime())
//            this.day = d.date
//            this.mount = d.month
//            this.year = d.year + 1900
//            this.hour = (bookingRespone.getStartTimeDate().toString() + "").toInt()
//            val gg: String = d.date
//                .toString() + "/" + (d.month + 1) + "/" + (d.year + 1900) + " - " + bookingRespone.getStartTimeDate() + "h"
//            checkAllFillData(
//                edtDuration.getText().toString(),
//                edtNumberGuest.getText().toString(),
//                type
//            )
//            edtTime.setText(gg)
//            bookingId = bookingRespone.getId()
//            edtStyleRoom.setClickable(false)
//            edtTime.setHint("Time")
//            val temp = Room()
//            temp.setAbout(bookingRespone.getRoom().getAbout())
//            temp.setId(bookingRespone.getRoom().getId())
//            temp.setMaxPerson(bookingRespone.getRoom().getMaxPerson())
//            temp.setPrice(bookingRespone.getRoom().getPrice())
//            temp.setCoworkingId(bookingRespone.getRoom().getCoworkingId())
//            temp.setName(bookingRespone.getRoom().getName())
//            btnSubmit.setText("EDIT")
//            roomNow = temp
//            type = 1000
        } else {
            data = intent.getSerializableExtra("key") as CWModel
            createDialog()
            type = intent.getIntExtra("room", 0)
//            onItemClick(type)
        }
        txtCost.setText("- VND")

        onDIalogTouch()
    }

    private fun onDIalogTouch() {
        btnBack.setOnClickListener { finish() }
        edtStyleRoom.setOnClickListener {
            if (style == 1 || style == 3) {
                myDialog.show()
            }
        }
        edtNumberGuest.onFocusChangeListener = OnFocusChangeListener { view, b ->
            checkAllFillData(
                edtDuration.text.toString(),
                edtNumberGuest.text.toString(),
                type
            )
        }
//        edtTime.setOnClickListener { mDialogTimePicker.startLoadingDialog() }
        btnSubmit.setOnClickListener {
            if (edtDuration.text.toString() == "" || edtTime.text
                    .toString() == "" || edtNumberGuest.text
                    .toString() == "" || type == -1
            ) {
                Toast.makeText(this@BookingActivity, "Require data !", Toast.LENGTH_SHORT)
                    .show()
            } else {
                showDialog()
            }
        }
        edtNumberGuest.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                if (edtNumberGuest.text.toString() != "" && roomNow != null) {
                    if ((edtNumberGuest.text.toString()
                            .trim { it <= ' ' } + "").toInt() > roomNow.maxPerson
                    ) {
                        if (!checkShowOverPeople) {
                            checkShowOverPeople = true
                            txtOversizePeople.visibility = View.VISIBLE
                        }
                    } else {
                        if (checkShowOverPeople) {
                            checkShowOverPeople = false
                            txtOversizePeople.visibility = View.GONE
                        }
                        checkAllFillData(
                            edtDuration.text.toString(),
                            edtNumberGuest.text.toString(),
                            type
                        )
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        edtDuration.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                checkAllFillData(
                    edtDuration.text.toString(),
                    edtNumberGuest.text.toString(),
                    type
                )
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun showDialog() {
        val myDialog2 = Dialog(this@BookingActivity)
        myDialog2.setContentView(R.layout.dialog_confirm)
        val txtTitle = myDialog2.findViewById<TextView>(R.id.confirm_dialog_txt_title)
        val txtDes = myDialog2.findViewById<TextView>(R.id.confirm_dialog_txt_description)
        if (style == 1 || style == 3) {
            txtTitle.setText(R.string.activity_booking_dialog_txt_title)
            txtDes.setText(R.string.activity_booking_dialog_txt_des)
        } else {
            txtTitle.setText(R.string.activity_booking_dialog_txt_title_style_2)
            txtDes.setText(R.string.activity_booking_dialog_txt_des_style_2)
        }
        myDialog2.findViewById<View>(R.id.confirm_dialog_btn_cancel)
            .setOnClickListener { v: View? -> myDialog2.dismiss() }
        myDialog2.findViewById<View>(R.id.confirm_dialog_btn_accept)
            .setOnClickListener { v: View? ->
//                mDialogLoading.startLoadingDialog()
                caculateDate()
                if (style == 1 || style == 3) {
//                    mViewModal.booking(
//                        listDate,
//                        edtNumberGuest.text.toString().toInt(),
//                        roomNow.getId()
//                    )
                    mViewModal.booking("", "2020-11-11-06-00", edtNumberGuest.text.toString().toInt(), edtDuration.text.toString().toFloat(), roomNow.id)
                } else {mDialogLoading
//                    mViewModal.editBooking(
//                        listDate,
//                        edtNumberGuest.text.toString().toInt(),
//                        roomNow.getId(),
//                        bookingId
//                    )
                    mViewModal.editBooking()
                }
                myDialog2.cancel()
            }
        myDialog2.show()
        myDialog2.setCanceledOnTouchOutside(false) //bam ra ngoai
        myDialog2.setCancelable(false) //bam nut back
    }

    private var day = -1
    private var mount = -1
    private var year = -1
    private var hour = -1
    private fun checkAllFillData(
        duration: String,
        numGuest: String,
        type: Int
    ) {
        if (duration != "" && numGuest != "" && type != -1 && day != -1 && mount != -1 && year != -1 && hour != -1) {
            proShowPrice.visibility = View.VISIBLE
            layoutShowPrice.visibility = View.GONE
            caculateDate()
//            mViewModal.getPrice(
//                listDate,
//                edtNumberGuest.text.toString().toInt(),
//                roomNow.getId()
//            )
            mViewModal.getPrice(numGuest.toFloat(), numGuest.toInt(), 20000)
        }
    }

    fun showPrice(cost: Float){
        proShowPrice.visibility = View.GONE
        layoutShowPrice.visibility = View.VISIBLE
        txtCost.text = "$cost VND"
    }
    private fun caculateDate(){}

    private fun createDialog() {
        val adapter = DetailItemAdapter()
        adapter.setData(data.rooms)
        adapter.setType(1)
        adapter.setItemClickListener(this)
        myDialog = Dialog(this@BookingActivity)
        val inflater = layoutInflater
        val v2: View = inflater.inflate(R.layout.dialog_style_room, null)
        myDialog.setContentView(v2)
        myDialog.findViewById<View>(R.id.dialog_style_room_btn_close)
            .setOnClickListener { v: View? -> myDialog.dismiss() }
        val t = myDialog.findViewById<TextView>(R.id.dialog_style_room_txt_title)
        t.text = "Style room"
        val temp: RecyclerView = myDialog.findViewById(R.id.dialog_style_room_rcy)
        temp.adapter = adapter
        myDialog.setCanceledOnTouchOutside(false) //bam ra ngoai
        myDialog.setCancelable(false) //bam nut back
    }

    fun bindView() {
        edtDuration = findViewById(R.id.activity_booking_edt_duration)
        edtNumberGuest = findViewById(R.id.activity_booking_edt_number_guest)
        edtStyleRoom = findViewById(R.id.activity_booking_edt_style_room)
        edtTime = findViewById(R.id.activity_booking_txt_time)
        btnSubmit = findViewById(R.id.activity_booking_btn_ok)
        btnBack = findViewById(R.id.activity_booking_btn_back)
        txtCost = findViewById(R.id.activity_booking_edt_cost)
        txtOversizePeople = findViewById(R.id.activity_booking_txt_oversize_people)
        layoutShowPrice = findViewById(R.id.layout_show_price)
        proShowPrice = findViewById(R.id.layout_show_price_loading)
        proShowPrice.visibility = View.GONE
        txtOversizePeople.visibility = View.GONE
        mDialogLoading = DialogLoading(this)
//        mDialogTimePicker = DialogTimePicker(this)
//        mDialogTimePicker.setmOnClickYes(this)
    }

    fun bookingRespone(number: Int, respone: String){
        mDialogLoading.dissLoadingDialog()
        if (number == 0) {
//            val errorData = BaseErrorData(respone)
            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText(respone)
                .show()
        } else {
            SweetAlertDialog(this@BookingActivity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Booking done !")
                .setContentText("Your booking is successful !")
                .setConfirmClickListener(object : SweetAlertDialog.OnSweetClickListener {
                    override fun onClick(sweetAlertDialog: SweetAlertDialog?) {
                        val returnIntent = Intent()
                        returnIntent.putExtra("result", "ok")
//                        returnIntent.putExtra("date", mCalender)
                        setResult(Activity.RESULT_OK, returnIntent)
                        finish()
                    }
                })
                .show()
        }
    }


    override fun onItemClick(item: Int) {
        myDialog.dismiss()
        type = item
        roomNow = data.rooms[type]
        edtStyleRoom.setText(roomNow.name)
        edtNumberGuest.hint = "Max person : " + roomNow.maxPerson
        checkAllFillData(
            edtDuration.text.toString(),
            edtNumberGuest.text.toString(),
            type
        )
    }
}