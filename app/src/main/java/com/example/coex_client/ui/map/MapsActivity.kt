package com.example.coex_client.ui.map

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.coex_client.R
import com.example.coex_client.data.UserSharedPref
import com.example.coex_client.model.map.CWModel
import com.example.coex_client.model.map.NearbyCWModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_maps.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.pow
import kotlin.math.sqrt

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var userToken: String
    private lateinit var map: GoogleMap
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var lastLocation: Location
    private var locationUpdateState = false
    companion object {
        private val REQUEST_LOCATION_PERMISSION = 1
        //
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val userSharedPref = UserSharedPref(this)
        userToken = userSharedPref.fetchAuthToken().toString()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                lastLocation = p0.lastLocation
//                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
            }
        }
        createLocationRequest()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        enableMyLocation()
        loadPlacePicker()
        imgMyLocation.setOnClickListener { view: View? -> moveToCurrentLocation() }
        moveToCurrentLocation()
        map.uiSettings.isZoomControlsEnabled = false;
        map.setOnCameraIdleListener {
            val cameraPosition = map.cameraPosition
            val radiusInMeters: Double = getMapVisibleRadius(googleMap)
            val radiusInKilometers = radiusInMeters / 1000
            val latitude = cameraPosition.target.latitude
            val longitude = cameraPosition.target.longitude
            markCoworkingNearby(radiusInKilometers, latitude, longitude)

        }
    }

    fun markCoworkingNearby(distance: Double, lat: Double, long: Double) : List<CWModel>{
        var result: List<CWModel> = listOf()
        UserApi.retrofitService.getCoworkingNearbyAPI("Bearer $userToken", distance, lat, long).enqueue(object :
            Callback<NearbyCWModel> {
            override fun onResponse(call: Call<NearbyCWModel>, response: Response<NearbyCWModel>) {
                val listStation = response.body()
                if (response.code() == 200) {
                    if (listStation != null) {
                        Log.d("getCWNearby", "listCWNearby : ${listStation}")
                        result = listStation.listCoWorking
                        map.clear()
                        for (station in listStation.listCoWorking){
                            map.addMarker(MarkerOptions().position(LatLng(station.location[0], station.location[1])))
                        }
                    }
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

    private fun startLocationUpdates() {
        //1
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION)
            return
        }
        //2
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    private fun createLocationRequest() {
        // 1
        locationRequest = LocationRequest()
        // 2
        locationRequest.interval = 10000
        // 3
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        // 4
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        // 5
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            // 6
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(this@MapsActivity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }
    }

    // 2
    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // 3
    public override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

    private fun moveToCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        map.clear()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,15.0f))
//                    map.addMarker(MarkerOptions().position(currentLatLng))
                }
            }
    }
    private fun loadPlacePicker() {
        val apiKey = getString(com.example.coex_client.R.string.google_maps_key)
        Places.initialize(applicationContext, apiKey)

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(com.example.coex_client.R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                map.clear()
                Log.i("Map1", "Place: ${place.name}, ${place.id}")

                val resultLatLng = LatLng(place.latLng!!.latitude, place.latLng!!.longitude)
                Log.d("Map1","${resultLatLng}")
                if (resultLatLng != null) {
                    placeMarkerOnMap(resultLatLng)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(resultLatLng,15.0f))
                }
            }

            override fun onError(status: Status) {
                Log.i("Map1", "An error occurred: $status")
            }
        })


    }

    private fun placeMarkerOnMap(location: LatLng) {
        // 1
        val markerOptions = MarkerOptions().position(location)
        // 2
        map.addMarker(markerOptions)
    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            map.isMyLocationEnabled = true;
            map.uiSettings.isMyLocationButtonEnabled = false;
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}