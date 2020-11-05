package com.example.coex_client.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.coex_client.R
import com.example.coex_client.model.map.CWModel

class DetailCOEXActivity : AppCompatActivity() {
    private lateinit var myDialog: Dialog
    private lateinit var mViewPaper: ViewPager
    private lateinit var imgBack: ImageView
    private lateinit var btnSubmit: Button
    private lateinit var layoutAirCon: RelativeLayout
    private lateinit var layoutConCall: RelativeLayout
    private lateinit var layoutDrink: RelativeLayout
    private lateinit var layoutPrinter: RelativeLayout
    private lateinit var layoutWifi: RelativeLayout
    private lateinit var txtTitle: TextView
    private lateinit var txtDescription: TextView
    private lateinit var txtPlace: TextView
    private lateinit var txtShowMore: TextView
    private lateinit var txtDistance: TextView
    private lateinit var data: CWModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_c_o_e_x)
        bindView()
        data = (intent.getSerializableExtra("key") as CWModel?)!!
        bindData(data)
//        initWindow()
        myDialog.dismiss()

//        btnSubmit.setOnClickListener {
//            if (data.getRooms() != null) {
//                if (data.getRooms().size() > 0) {
//                    val moveData =
//                        Intent(this@DetailCOEXActivity, BookingActivity::class.java)
//                    moveData.putExtra("type", 1)
//                    moveData.putExtra("key", data)
//                    startActivityForResult(moveData, DetailCOEXActivity.REQUEST_DATA_BOOKING)
//                }
//            }
//        }
        imgBack.setOnClickListener { finish() }
        txtShowMore.setOnClickListener { myDialog.show() }
    }

    fun bindView() {
        myDialog = Dialog(this)
        txtTitle = findViewById<TextView>(R.id.activity_detail_coex_txt_title)
        txtDescription = findViewById<TextView>(R.id.activity_detail_coex_txt_description)
        txtPlace = findViewById<TextView>(R.id.activity_detail_coex_txt_place)
        txtShowMore = findViewById<TextView>(R.id.activity_detail_txt_show_more)
        layoutAirCon = findViewById<RelativeLayout>(R.id.activity_detail_coex_item_air_conditioning)
        layoutConCall = findViewById<RelativeLayout>(R.id.activity_detail_coex_item_conversion_call)
        layoutDrink = findViewById<RelativeLayout>(R.id.activity_detail_coex_item_drink)
        layoutPrinter = findViewById<RelativeLayout>(R.id.activity_detail_coex_item_printer)
        layoutWifi = findViewById<RelativeLayout>(R.id.activity_detail_coex_item_wifi)
//        rcyRoom = findViewById<RecyclerView>(R.id.activity_detail_coex_recycview_style_room)
        btnSubmit =
            findViewById<Button>(R.id.activity_detail_coex_btn_confirm_booking)
        imgBack = findViewById<ImageView>(R.id.activity_detail_coex_btn_back)
//        indicator = findViewById(R.id.activity_detail_coex_indicator)
        mViewPaper = findViewById<ViewPager>(R.id.activity_detail_coex_view_paper)
        txtDistance = findViewById<TextView>(R.id.activity_detail_coex_txt_distance)
    }

    private fun bindData(data: CWModel) {
        this.data = data
        txtTitle.text = data.name
        txtPlace.text = data.address
//        txtDistance.setText(Math.round(data.getDistance() * 100).toDouble() / 100.toString() + "km")
        txtDescription.text = data.about
        txtDistance.text = "chua lam"
//        if (data.photo.isNotEmpty()) {
////            L.d("ahiuhiu", data.getPhoto().size().toString() + "")
//            var listImage =
//                data.photo as ArrayList<String>
//            var adapter = ListPhotoAdapter(this, listImage)
//            mViewPaper.adapter = adapter
//            indicator.setViewPager(mViewPaper)
//            adapter.registerDataSetObserver(indicator.getDataSetObserver())
//        }
//        if (data.getService().getDrink()) {
//            layoutDrink.visibility = View.VISIBLE
//        } else {
//            layoutDrink.visibility = View.GONE
//        }
//        if (data.getService().getAirConditioning()) {
//            layoutAirCon.visibility = View.VISIBLE
//        } else {
//            layoutAirCon.visibility = View.GONE
//        }
//        if (data.getService().getConversionCall()) {
//            layoutConCall.visibility = View.VISIBLE
//        } else {
//            layoutConCall.visibility = View.GONE
//        }
//        if (data.getService().getPrinter()) {
//            layoutPrinter.visibility = View.VISIBLE
//        } else {
//            layoutPrinter.visibility = View.GONE
//        }
//        if (data.getService().getWifi()) {
//            layoutWifi.visibility = View.VISIBLE
//        } else {
//            layoutWifi.visibility = View.GONE
//        }
//        val roomData: List<Room> = data.getRooms()
//        adapter = DetailItemAdapter()
//        adapter.setData(roomData)
//        adapter.setType(1)
//        adapter.setItemClickListener(this)
//        rcyRoom.setAdapter(adapter)
    }
}