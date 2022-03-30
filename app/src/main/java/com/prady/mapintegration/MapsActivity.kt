package com.prady.mapintegration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.prady.mapintegration.databinding.ActivityMapsBinding


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    var lat: Double = 0.0
    var log: Double = 0.0

    lateinit var poly: PolylineOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lat = intent.getDoubleExtra("lat", 0.0)
        log = intent.getDoubleExtra("log", 0.0)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val userCurrentLocation = LatLng(lat, log)
        val deliveryBoyCurrentLocation = LatLng(26.7877806, 81.0)

//        poly.add(userCurrentLocation).add(deliveryBoyCurrentLocation)
//        mMap.addPolyline(
//            PolylineOptions()
//                .add(userCurrentLocation, deliveryBoyCurrentLocation)
//                .width(5f)
//                .color(resources.getColor(R.color.purple_200))
//                .visible(true)
//                .zIndex(30f)
//        )

        mMap.addMarker(
            MarkerOptions().position(userCurrentLocation).alpha(2f).title("Current User Location")
                .snippet("Lat: $lat, Log: $log")
        )
        mMap.addMarker(
            MarkerOptions().position(deliveryBoyCurrentLocation).title("Delivery Boy Location")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userCurrentLocation))
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(userCurrentLocation.latitude, userCurrentLocation.longitude), 12.0f
            )
        )

        GetPathFromLocation(userCurrentLocation, deliveryBoyCurrentLocation) { polyLine ->
            mMap.addPolyline(
                polyLine
                    .width(5f)
                    .color(resources.getColor(R.color.purple_200))
                    .visible(true)
                    .zIndex(30f)
            )
        }.execute()
    }
}