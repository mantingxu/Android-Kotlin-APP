package com.example.rent

import android.graphics.Color
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapActivity : AppCompatActivity() ,OnMapReadyCallback {

    private lateinit var passedaddress:String
    private lateinit var passedname:String
    private lateinit var mMap:GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)


        passedname = intent.getStringExtra("NAME")
        passedaddress = intent.getStringExtra("ADDRESS")



        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val geoCoder = Geocoder(this);
        val value = geoCoder.getFromLocationName(passedname,1)
        val location = LatLng(value[0].latitude,value[0].longitude);
//        val long = intent.getStringExtra("long").toDouble()
//        val lat = intent.getStringExtra("lat").toDouble()
//        Log.d("TAG",long.toString())
//        Log.d("TAG",lat.toString())
//        val location = LatLng(lat,long)
        val tel = intent.getStringExtra("NAME1").toDouble()
        val ui = intent.getStringExtra("ADDRESS1").toDouble()
        Log.d("TAG",tel.toString())
        val taipei = LatLng(tel, ui)
        val now =  LatLng(	25.136577	,121.507154)
        googleMap.addMarker(MarkerOptions()
            .position(taipei)
            .title("目前位置")
            .snippet("你在這裡")
            .alpha(0.7f)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15F), 3000, null);
        googleMap.getMaxZoomLevel()
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(taipei))

        googleMap.addMarker(MarkerOptions().position(location).title("Marker in "+passedname))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15F), 3000, null);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))






//        val location = LatLng(args.latitude.toDouble(), args.longtitude.toDouble());
//        //googleMap.addMarker(MarkerOptions().position(location).title("taipei"));
//        googleMap.addMarker(MarkerOptions().position(location).title(args.name));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15F));
    }
}