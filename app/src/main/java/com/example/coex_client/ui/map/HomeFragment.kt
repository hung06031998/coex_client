package com.example.coex_client.ui.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import com.example.coex_client.R
import com.example.coex_client.databinding.FragmentHomeBinding
import com.example.coex_client.model.map.CWModel
import com.example.coex_client.ui.detail.DetailCOEXActivity
import com.example.coex_client.viewmodel.homeFragment.HomeFragmentViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker


class HomeFragment : Fragment(),
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener,
    LifecycleObserver {

    private var REQUEST_BOOKING = 1212
    private var selectedCW: CWModel = CWModel()
    private lateinit var txtItemCost: TextView
    private lateinit var imgItemImage: ImageView
    private lateinit var txtItemDes: TextView
    private lateinit var txtDistance: TextView
    private lateinit var txtItemTitle: TextView
    private lateinit var _viewXML : View
    private lateinit var _viewModel : HomeFragmentViewModel
    private lateinit var item: RelativeLayout
    private lateinit var layoutItemDrink: RelativeLayout
    private lateinit var layoutItemAirCon: RelativeLayout
    private lateinit var layoutItemConCall: RelativeLayout
    private lateinit var layoutItemFreeWifi: RelativeLayout
    private lateinit var layoutItemPrinter: RelativeLayout
    private lateinit var data : List<CWModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewXML = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false).root
        _viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        _viewModel.setUpView(this)
        return _viewXML
    }

    override fun onMapReady(ggMap: GoogleMap) {
        _viewModel.setUpMap(ggMap)
    }

    override fun onConnected(p0: Bundle?) {
        _viewModel.requestPermissionToAccessLocation()
        _viewModel.moveToHaNoiOrCurrentLocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode != 123) {
            return
        }
        val accessCoarsePermission = ContextCompat.checkSelfPermission(
            this.requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val accessFinePermission = ContextCompat.checkSelfPermission(
            this.requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
            || accessFinePermission != PackageManager.PERMISSION_GRANTED
        )
            _viewModel.enableLocation()
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    fun bindView() {
        item = _viewXML.findViewById(R.id.fragment_home_layout_detail)
        item.setOnClickListener {
            var moveData = Intent(context, DetailCOEXActivity::class.java)
            moveData.putExtra("key", selectedCW)
            startActivityForResult(moveData, 1212)
        }
        item.visibility = View.GONE
        txtItemCost = _viewXML.findViewById(R.id.fragment_home_txt_cost)
        txtItemDes = _viewXML.findViewById(R.id.fragment_home_txt_description)
        txtItemTitle = _viewXML.findViewById(R.id.fragment_home_txt_name)
        txtDistance = _viewXML.findViewById(R.id.fragment_home_txt_distance)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        for (i in data.indices) {
            if (marker.position.latitude == data[i].location[0] &&
                marker.position.longitude == data[i].location[1]
            ) {
                setItemToCard(data[i])
            }
        }
        return false
    }

    private fun setItemToCard(coex: CWModel) {
        item.visibility = View.VISIBLE
        txtItemTitle.text = coex.name
//        txtDistance.setText((coex.getDistance() * 100).roundToInt().toDouble() / 100.toString() + "km")
        txtDistance.text = "chua lam"
//        txtItemDes.text = coex.about
        txtItemDes.text = coex.about
//        if (coex.photo.isNotEmpty()) {
//            Glide.with(context).load(IMAGE_LINK_BASE + coex.getPhoto().get(0)).into(imgItemImage)
//        }
//        if (coex.getRooms() != null) {
//            val cost: Long = coex.getRooms().get(0).getPrice()
//            txtItemCost.setText("$cost VND/ hour/ 1 person")
//        } else {
//            txtItemCost.setText("No room to show cost")
//        }
//        if (coex.getService().getDrink()) {
//            layoutItemDrink.setVisibility(View.VISIBLE)
//        } else {
//            layoutItemDrink.setVisibility(View.GONE)
//        }
//        if (coex.getService().getAirConditioning()) {
//            layoutItemAirCon.setVisibility(View.VISIBLE)
//        } else {
//            layoutItemAirCon.setVisibility(View.GONE)
//        }
//        if (coex.getService().getConversionCall()) {
//            layoutItemConCall.setVisibility(View.VISIBLE)
//        } else {
//            layoutItemConCall.setVisibility(View.GONE)
//        }
//        if (coex.getService().getPrinter()) {
//            layoutItemPrinter.setVisibility(View.VISIBLE)
//        } else {
//            layoutItemPrinter.setVisibility(View.GONE)
//        }
//        if (coex.getService().getWifi()) {
//            layoutItemFreeWifi.setVisibility(View.VISIBLE)
//        } else {
//            layoutItemFreeWifi.setVisibility(View.GONE)
//        }
        selectedCW = coex
    }

    fun refreshData(data : List<CWModel>){
        this.data = data;
    }
    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

}