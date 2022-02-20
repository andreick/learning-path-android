package com.andreick.location

import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.andreick.location.databinding.ActivityMapsBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lastLocation: Location? = null
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                lastLocation = locationResult.lastLocation
                placeMarkerOnMap(LatLng(
                    locationResult.lastLocation.latitude, locationResult.lastLocation.longitude
                ))
            }
        }
        createLocationRequest()
     }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap.apply {
            val myPlace = LatLng(40.73, -73.99)
            addMarker(MarkerOptions().position(myPlace).title("Nova York"))
            moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 12f))
            uiSettings.isZoomControlsEnabled = true
            setOnMarkerClickListener(this@MapsActivity)
        }
        setupMap()
    }

    private fun setupMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requestFineLocationPermission()
            return
        }
        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    private fun requestFineLocationPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun placeMarkerOnMap(latLng: LatLng) {
        val markerOptions = MarkerOptions().position(latLng).title(getAddress(latLng))
        /*val icon = ActivityCompat.getDrawable(this, R.drawable.ic_baseline_location_city_24)
        if (icon != null) markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon.toBitmap(128, 128)))*/
        map.addMarker(markerOptions)
    }

    private fun getAddress(latLng: LatLng):String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addressLst: List<Address> =
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        val address = addressLst[0].getAddressLine(0)
        /*val city = addressLst[0].locality
        val state = addressLst[0].adminArea
        val country = addressLst[0].countryName
        val postalCode = addressLst[0].postalCode*/
        return address
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requestFineLocationPermission()
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                // Location settings are not satisfied,
                // but this can be fixed by showing the user a dialog
                try {
                    e.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) { /*ignore error*/ }
            }
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean = false

    override fun onResume() {
        super.onResume()
        if (!locationUpdateState) startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 0
        private const val REQUEST_CHECK_SETTINGS = 2
    }
}