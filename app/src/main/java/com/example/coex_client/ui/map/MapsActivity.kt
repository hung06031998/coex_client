package com.example.coex_client.ui.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coex_client.R
import com.example.coex_client.data.UserSharedPref
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapsActivity : AppCompatActivity() {
    private lateinit var mBottomNavigationView: BottomNavigationView
    private lateinit var _fragmentSchedule: FragmentSchedule
    private lateinit var _fragmentSetting: FragmentSetting
    private lateinit var _fragmentHome: HomeFragment
    private lateinit var _userToken: String
    private lateinit var _map: GoogleMap

    //
//    private lateinit var locationCallback: LocationCallback
//    private lateinit var locationRequest: LocationRequest
//    private lateinit var lastLocation: Location
//    private var locationUpdateState = false
//    companion object {
//        private val REQUEST_LOCATION_PERMISSION = 1
//
//        private const val REQUEST_CHECK_SETTINGS = 2
//    }
//
//    private lateinit var fusedLocationClient: FusedLocationProviderClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        checkToken()
        bindView()
        intiData()
        //
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(p0: LocationResult) {
//                super.onLocationResult(p0)
//
//                lastLocation = p0.lastLocation
////                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
//            }
//        }
//        createLocationRequest()
    }

    private fun checkToken() {
        var userToken = UserSharedPref(this).fetchAuthToken()
        if (userToken == ""){}
    }

    private fun intiData() {
        _fragmentHome = HomeFragment()
        _fragmentSchedule = FragmentSchedule()
        _fragmentSetting = FragmentSetting()
        loadFragment(_fragmentHome)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.maplayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun bindView() {
        mBottomNavigationView = findViewById(R.id.main_navigation)
        mBottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_main_home -> {
                    loadFragment(_fragmentHome)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_main_schedule -> {
                    loadFragment(_fragmentSchedule)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_main_setting -> {
                    loadFragment(_fragmentSetting)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    //
//    override fun onMapReady(googleMap: GoogleMap) {
//        _map = googleMap
//        enableMyLocation()
//        loadPlacePicker()
//        imgMyLocation.setOnClickListener { view: View? -> moveToaocation() }
//        moveToCurrentLocation()
//        _map.uiSettings.isZoomControlsEnabled = true;

//    }





//    private fun startLocationUpdates() {
//        //1
//        if (ActivityCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                REQUEST_LOCATION_PERMISSION)
//            return
//        }
//        //2
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
//    }

//    private fun createLocationRequest() {
//        // 1
//        locationRequest = LocationRequest()
//        // 2
//        locationRequest.interval = 10000
//        // 3
//        locationRequest.fastestInterval = 5000
//        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//
//        val builder = LocationSettingsRequest.Builder()
//            .addLocationRequest(locationRequest)
//
//        // 4
//        val client = LocationServices.getSettingsClient(this)
//        val task = client.checkLocationSettings(builder.build())
//
//        // 5
//        task.addOnSuccessListener {
//            locationUpdateState = true
//            startLocationUpdates()
//        }
//        task.addOnFailureListener { e ->
//            // 6
//            if (e is ResolvableApiException) {
//                // Location settings are not satisfied, but this can be fixed
//                // by showing the user a dialog.
//                try {
//                    // Show the dialog by calling startResolutionForResult(),
//                    // and check the result in onActivityResult().
//                    e.startResolutionForResult(this@MapsActivity,
//                        REQUEST_CHECK_SETTINGS)
//                } catch (sendEx: IntentSender.SendIntentException) {
//                    // Ignore the error.
//                }
//            }
//        }
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CHECK_SETTINGS) {
//            if (resultCode == Activity.RESULT_OK) {
//                locationUpdateState = true
//                startLocationUpdates()
//            }
//        }
//    }

    // 2
//    override fun onPause() {
//        super.onPause()
//        fusedLocationClient.removeLocationUpdates(locationCallback)
//    }

    // 3
//    public override fun onResume() {
//        super.onResume()
//        if (!locationUpdateState) {
//            startLocationUpdates()
//        }
//    }

//    private fun moveToCurrentLocation(){
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//    }
//    private fun loadPlacePicker() {
//        val apiKey = getString(com.example.coex_client.R.string.google_maps_key)
//        Places.initialize(applicationContext, apiKey)
//
//        // Create a new PlacesClient instance
//        val placesClient = Places.createClient(this)
//        // Initialize the AutocompleteSupportFragment.
//        val autocompleteFragment =
//            supportFragmentManager.findFragmentById(com.example.coex_client.R.id.autocomplete_fragment)
//                    as AutocompleteSupportFragment
//
//        // Specify the types of place data to return.
//        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS))
//
//        // Set up a PlaceSelectionListener to handle the response.
//        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onPlaceSelected(place: Place) {
//                _map.clear()
//                Log.i("Map1", "Place: ${place.name}, ${place.id}")
//
//                val resultLatLng = LatLng(place.latLng!!.latitude, place.latLng!!.longitude)
//                Log.d("Map1","${resultLatLng}")
//                if (resultLatLng != null) {
//                    placeMarkerOnMap(resultLatLng)
//                    _map.animateCamera(CameraUpdateFactory.newLatLngZoom(resultLatLng,15.0f))
//                }
//            }
//
//            override fun onError(status: Status) {
//                Log.i("Map1", "An error occurred: $status")
//            }
//        })
//
//
//    }

//    private fun placeMarkerOnMap(location: LatLng) {
//        // 1
//        val markerOptions = MarkerOptions().position(location)
//        // 2
//        _map.addMarker(markerOptions)
//    }

//    private fun isPermissionGranted() : Boolean {
//        return ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//    }

//    private fun enableMyLocation() {
//        if (isPermissionGranted()) {
//
//            _map.isMyLocationEnabled = true;
//            _map.uiSettings.isMyLocationButtonEnabled = true;
//        }
//        else {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
//                REQUEST_LOCATION_PERMISSION
//            )
//        }
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray) {
//        if (requestCode == REQUEST_LOCATION_PERMISSION) {
//            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
//                enableMyLocation()
//            }
//        }
//    }
}