package com.example.coex_client.ui.detail

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.coex_client.MainLoginActivity
import com.example.coex_client.R
import com.example.coex_client.model.map.CWModel
import com.example.coex_client.model.map.RoomModel
import com.example.coex_client.ui.booking.BookingActivity

class DetailCOEXActivity : AppCompatActivity(), DetailItemAdapter.OnItemClickListener {
    private lateinit var adapter: DetailItemAdapter
    private lateinit var rcyRoom: RecyclerView
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
        initWindow()
        myDialog.dismiss()

        btnSubmit.setOnClickListener {
            if (!data.rooms[0].coWorkingId.isNullOrBlank()) {
                val moveData =
                    Intent(this, BookingActivity::class.java)
                moveData.putExtra("type", 1)
                moveData.putExtra("key", data)
                startActivity(moveData)
            }
        }
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
        rcyRoom = findViewById<RecyclerView>(R.id.activity_detail_coex_recycview_style_room)
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
        layoutAirCon.visibility = View.GONE
        layoutConCall.visibility = View.GONE
        layoutDrink.visibility = View.GONE
        layoutPrinter.visibility = View.GONE
        layoutWifi.visibility = View.GONE
        if (!data.rooms[0].coWorkingId.isNullOrEmpty()) {
            for (room in data.rooms){
                if (room.service.drink){
                    layoutDrink.visibility = View.VISIBLE
                }
                if (room.service.airCondition){
                    layoutAirCon.visibility = View.VISIBLE
                }
                if (room.service.conversionCall){
                    layoutConCall.visibility = View.VISIBLE
                }
                if (room.service.printer){
                    layoutPrinter.visibility = View.VISIBLE
                }
                if (room.service.wifi){
                    layoutWifi.visibility = View.VISIBLE
                }
            }
            var roomData: List<RoomModel> = data.rooms
            adapter = DetailItemAdapter()
            adapter.setData(roomData)
            adapter.setType(1)
            adapter.setItemClickListener(this)
            rcyRoom.adapter = adapter
        }
    }

    override fun onItemClick(item: Int) {
        var moveData = Intent(this, MainLoginActivity::class.java)
        moveData.putExtra("type", 3)
        moveData.putExtra("key", data)
        moveData.putExtra("room", item)
        startActivityForResult(moveData, 1111)
    }

    private fun initWindow() {
        myDialog = Dialog(this)
        val inflater = layoutInflater
        val v2: View = inflater.inflate(R.layout.dialog_view_more, null)
        myDialog.setContentView(v2)
        myDialog.findViewById<View>(R.id.dialog_view_more_btn_close)
            .setOnClickListener { v: View? -> myDialog.dismiss() }
        val layoutWifi2: RelativeLayout
        val layoutDrink2: RelativeLayout
        val layoutPrinter2: RelativeLayout
        val layoutConCall2: RelativeLayout
        val layoutAirCon2: RelativeLayout
        val txtOther: TextView
        layoutWifi2 = v2.findViewById(R.id.dialog_show_more_item_wifi)
        layoutConCall2 = v2.findViewById(R.id.dialog_show_more_item_con_call)
        layoutAirCon2 = v2.findViewById(R.id.dialog_show_more_item_air_con)
        layoutDrink2 = v2.findViewById(R.id.dialog_show_more_item_drink)
        layoutPrinter2 = v2.findViewById(R.id.dialog_show_more_item_printer)
        txtOther = v2.findViewById(R.id.dialog_show_more_txt_orther)
        layoutDrink2.visibility = View.GONE
        layoutWifi2.visibility = View.GONE
        layoutPrinter2.visibility = View.GONE
        layoutConCall2.visibility = View.GONE
        layoutAirCon2.visibility = View.GONE
        if (!data.rooms[0].coWorkingId.isNullOrEmpty()) {
            for (room in data.rooms){
                if (room.service.drink){
                    layoutDrink2.visibility = View.VISIBLE
                }
                if (room.service.airCondition){
                    layoutAirCon2.visibility = View.VISIBLE
                }
                if (room.service.conversionCall){
                    layoutConCall2.visibility = View.VISIBLE
                }
                if (room.service.printer){
                    layoutPrinter2.visibility = View.VISIBLE
                }
                if (room.service.wifi){
                    layoutWifi2.visibility = View.VISIBLE
                }
                if (!room.service.other.isNullOrEmpty()){
                    var text : String = ""
                    for (service in room.service.other) {
                        text += service.toString() + "\n"
                    }
                    txtOther.text = text
                }
            }
        }

        myDialog.setCanceledOnTouchOutside(false)
        myDialog.setCancelable(false)
    }
}