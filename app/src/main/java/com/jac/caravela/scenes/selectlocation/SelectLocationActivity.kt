package com.jac.caravela.scenes.selectlocation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.jac.caravela.R
import com.jac.caravela.extensions.showLongMessage
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_select_location.*
import kotlinx.android.synthetic.main.toolbar.*

class SelectLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var selectedLocation: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        toolbar_title?.text = getString(R.string.select_location)
        toolbar_progressbar?.visibility = View.VISIBLE

        setupViewElements()
    }

    private fun setupViewElements() {
        select_location_cancel_BTN?.setOnClickListener {
            map.clear()
            select_location_dialog?.visibility = View.GONE
        }

        select_location_save_BTN?.setOnClickListener {
            val intent = Intent()
            intent.putExtra(LATITUDE_EXTRA, selectedLocation?.latitude)
            intent.putExtra(LONGITUDE_EXTRA, selectedLocation?.longitude)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        checkLocationPermission()

        map.setOnMapLongClickListener { latLng ->
            googleMap.clear()
            googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.selected_position))
                    .snippet(getString(R.string.selected_position_description))
            )
            selectedLocation = latLng
            select_location_dialog?.visibility = View.VISIBLE
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true

            buildLocationRequest()
            buildLocationCallback()

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        } else {
            showLongMessage(getString(R.string.location_premission))
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_LOCATION_PERMISSION)
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun buildLocationCallback() {
        locationCallback = object: LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                val location = locationResult?.locations?.get(locationResult.locations.size - 1)
                val myLocation = LatLng(location?.latitude ?: 0.0, location?.longitude ?: 0.0)

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 13f))
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                toolbar_progressbar?.visibility = View.GONE
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_LOCATION_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    checkLocationPermission()
                return
            }
        }
    }

    companion object {
        const val MY_LOCATION_PERMISSION = 3
        const val LATITUDE_EXTRA = "LATITUDE_EXTRA"
        const val LONGITUDE_EXTRA = "LONGITUDE_EXTRA"
    }
}
