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
import androidx.navigation.findNavController
import com.example.coex_client.R
import com.example.coex_client.data.UserSharedPref
import com.example.coex_client.databinding.FragmentHomeBinding
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

    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

}