package com.example.coex_client.viewmodel.homeFragment

import UserApi
import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.coex_client.R
import com.example.coex_client.data.UserSharedPref
import com.example.coex_client.model.map.CWModel
import com.example.coex_client.model.map.NearbyCWModel
import com.example.coex_client.ui.map.HomeFragment
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.pow
import kotlin.math.sqrt

class HomeFragmentViewModel : ViewModel() {
    private lateinit var _view : HomeFragment
    private lateinit var _ggMap : GoogleMap
    private lateinit var _userSharedPref : UserSharedPref
    private lateinit var _googleApiClient : GoogleApiClient
    public lateinit var _cwNearby : List<CWModel>
    public fun setUpView(homeFragment: HomeFragment){
        _view = homeFragment
        _userSharedPref = UserSharedPref(_view.requireContext())
        _view.bindView()
        initMap()
    }

    private fun initMap() {
        var supportMapFragment = _view.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(_view)
    }

    fun setUpMap(ggMap: GoogleMap) {
        _ggMap = ggMap
        _ggMap.getUiSettings().setMyLocationButtonEnabled(true)
        _ggMap.getUiSettings().isZoomControlsEnabled = true
        _ggMap.setOnMarkerClickListener(_view)
        _ggMap.setOnCameraIdleListener {
            val cameraPosition = _ggMap.cameraPosition
            val radiusInMeters: Double = getMapVisibleRadius(_ggMap)
            val radiusInKilometers = radiusInMeters / 1000
            val latitude = cameraPosition.target.latitude
            val longitude = cameraPosition.target.longitude
            _cwNearby = markCoworkingNearbyCameraLocation(radiusInKilometers, latitude, longitude)
        }
        var fragment = _view.childFragmentManager.findFragmentById(R.id.map)
        val v1 = fragment!!.view as ViewGroup?
        if (v1 != null) {
            val v2 = v1.getChildAt(0) as ViewGroup
            val v3 = v2.getChildAt(2) as ViewGroup
            val currentPosition = v3.getChildAt(0) as View
            val positionWidth = currentPosition.layoutParams.width
            val positionHeight = currentPosition.layoutParams.height
            val positionParams =
                RelativeLayout.LayoutParams(positionWidth, positionHeight)
            positionParams.setMargins(dpToPx(24),dpToPx(90),
                0,
                0
            )
            positionParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
            positionParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
            currentPosition.layoutParams = positionParams
            val currentPosition2 = v3.getChildAt(4) as View
            val positionWidth2 = currentPosition2.layoutParams.width
            val positionHeight2 = currentPosition2.layoutParams.height
            val positionParams2 =
                RelativeLayout.LayoutParams(positionWidth2, positionHeight2)
            positionParams2.setMargins(dpToPx(24),dpToPx(140),
                0,
                0
            )
            positionParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
            positionParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
            currentPosition2.layoutParams = positionParams2
            val currentPosition3 = v3.getChildAt(2) as View
            val positionWidth3 = currentPosition3.layoutParams.width
            val positionHeight3 = currentPosition3.layoutParams.height
            val positionParams3 =
                RelativeLayout.LayoutParams(positionWidth3, positionHeight3)
            positionParams3.setMargins(
                0,dpToPx(90),dpToPx(24),
                0
            )
            positionParams3.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
            positionParams3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
            currentPosition3.layoutParams = positionParams3


            //Initializing googleApiClient
            _googleApiClient = GoogleApiClient.Builder(_view.requireContext())
                .addConnectionCallbacks(_view)
                .addOnConnectionFailedListener(_view)
                .addApi(LocationServices.API)
                .build()

            _googleApiClient.connect()
        }
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    private fun getMapVisibleRadius(googleMap: GoogleMap): Double {
        val visibleRegion = googleMap.projection.visibleRegion
        val distanceWidth = FloatArray(1)
        val distanceHeight = FloatArray(1)
        val farRight = visibleRegion.farRight
        val farLeft = visibleRegion.farLeft
        val nearRight = visibleRegion.nearRight
        val nearLeft = visibleRegion.nearLeft
        Location.distanceBetween(
            (farLeft.latitude + nearLeft.latitude) / 2,
            farLeft.longitude,
            (farRight.latitude + nearRight.latitude) / 2,
            farRight.longitude,
            distanceWidth
        )
        Location.distanceBetween(
            farRight.latitude,
            (farRight.longitude + farLeft.longitude) / 2,
            nearRight.latitude,
            (nearRight.longitude + nearLeft.longitude) / 2,
            distanceHeight
        )
        return sqrt(
            distanceWidth[0].toDouble().pow(2.0) + distanceHeight[0].toDouble().pow(2.0)
        ) / 2
    }

    private fun markCoworkingNearbyCameraLocation(distance: Double, lat: Double, long: Double) : List<CWModel>{
        var result: List<CWModel> = listOf()
        UserApi.retrofitService.getCoworkingNearbyAPI("Bearer ${_userSharedPref.fetchAuthToken().toString()}", distance, lat, long).enqueue(object :
            Callback<NearbyCWModel> {
            override fun onResponse(call: Call<NearbyCWModel>, response: Response<NearbyCWModel>) {
                val listStation = response.body()
                if (response.code() == 200) {
                    if (listStation != null) {
                        Log.d("getCWNearby", "listCWNearby : ${listStation}")
                        result = listStation.listCoWorking
                        _ggMap.clear()
                        for (station in listStation.listCoWorking){
                            _ggMap.addMarker(MarkerOptions().position(LatLng(station.location[0], station.location[1])))
                        }
                    }
                    _view.refreshData(result);
                } else {
                    Log.d("getCWNearby", "listCWNearby : errorrrrrr")
                }
            }

            override fun onFailure(call: Call<NearbyCWModel>, t: Throwable) {
                Log.d("getCWNearby", "listCWNearby : errorrrrrr222222")
            }
        })
        return result
    }


    fun requestPermissionToAccessLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            val accessCoarsePermission = ContextCompat.checkSelfPermission(
                _view.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            val accessFinePermission = ContextCompat.checkSelfPermission(
                _view.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                || accessFinePermission != PackageManager.PERMISSION_GRANTED
            ) {
                val permissions = arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

                _view.getActivity()?.let {
                    ActivityCompat.requestPermissions(
                        it, permissions,
                        123
                    ).apply {
                        if (accessCoarsePermission == PackageManager.PERMISSION_GRANTED
                            && accessFinePermission == PackageManager.PERMISSION_GRANTED
                        )
                            enableLocation()
                    }
                }
            }
            else
                enableLocation()
            return
        }
    }

    fun moveToHaNoiOrCurrentLocation() {
        var latitude = 21.0241208
        var longitude = 105.7890571
        _ggMap.clear()
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(_view.requireActivity())
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(_view.requireActivity(), OnSuccessListener {
                location ->
                if (location != null) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
            }
            })
        } catch (e: SecurityException) {
            Log.d("Location: ", e.toString())
        }
        moveCamera(latitude, longitude)
    }

    private fun moveCamera(lat: Double, long: Double) {
        _ggMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, long)))
        _ggMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    fun enableLocation() {
        _ggMap.isMyLocationEnabled = true
    }
}