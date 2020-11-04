package com.example.coex_client.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import com.example.coex_client.R
import com.example.coex_client.databinding.FragmentHomeBinding
import com.example.coex_client.model.map.CWModel
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

    private lateinit var _viewXML : View
    private lateinit var _viewModel : HomeFragmentViewModel

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
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        var data = _viewModel._cwNearby;
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
        item.setVisibility(View.VISIBLE)
        txtItemTitle.setText(coex.getName())
        txtDistance.setText(Math.round(coex.getDistance() * 100).toDouble() / 100.toString() + "km")
        txtItemDes.setText(coex.getAbout())
        if (coex.getPhoto() != null && coex.getPhoto().size() !== 0) {
            Glide.with(context).load(IMAGE_LINK_BASE + coex.getPhoto().get(0)).into(imgItemImage)
        }
        if (coex.getRooms() != null) {
            val cost: Long = coex.getRooms().get(0).getPrice()
            txtItemCost.setText("$cost VND/ hour/ 1 person")
        } else {
            txtItemCost.setText("No room to show cost")
        }
        if (coex.getService().getDrink()) {
            layoutItemDrink.setVisibility(View.VISIBLE)
        } else {
            layoutItemDrink.setVisibility(View.GONE)
        }
        if (coex.getService().getAirConditioning()) {
            layoutItemAirCon.setVisibility(View.VISIBLE)
        } else {
            layoutItemAirCon.setVisibility(View.GONE)
        }
        if (coex.getService().getConversionCall()) {
            layoutItemConCall.setVisibility(View.VISIBLE)
        } else {
            layoutItemConCall.setVisibility(View.GONE)
        }
        if (coex.getService().getPrinter()) {
            layoutItemPrinter.setVisibility(View.VISIBLE)
        } else {
            layoutItemPrinter.setVisibility(View.GONE)
        }
        if (coex.getService().getWifi()) {
            layoutItemFreeWifi.setVisibility(View.VISIBLE)
        } else {
            layoutItemFreeWifi.setVisibility(View.GONE)
        }
        temp = coex
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

}