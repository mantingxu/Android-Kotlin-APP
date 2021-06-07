package com.example.rent

//import com.example.rent.databinding.FragmentMyMapBinding

//import androidx.test.core.app.ApplicationProvider.getApplicationContext
import android.graphics.Color
import android.location.Address
import android.location.Criteria
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions


class MyMap : Fragment() {

    //private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val callback = OnMapReadyCallback { googleMap ->
        val args = MyMapArgs.fromBundle(requireArguments());
        //val geoCoder = Geocoder(context);
        //val value = geoCoder.getFromLocationName(args.address,1);
        //val location = LatLng(value[0].latitude,value[0].longitude);
        //val location = LatLng('23.87018923', '121.50905477');
        val location = LatLng(args.latitude.toDouble(), args.longtitude.toDouble());
        //googleMap.addMarker(MarkerOptions().position(location).title("taipei"));
        googleMap.addMarker(MarkerOptions().position(location).title(args.name));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15F));



        //val taipei = LatLng(25.08, 121.45)
        //googleMap.addMarker(MarkerOptions().position(taipei).title("Marker in Taipei"))
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(10F), 3000, null);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(taipei))
//        val polylineOpt = PolylineOptions()
//        polylineOpt.add(LatLng(25.033611, 121.565000))
//        polylineOpt.add(LatLng(25.032728, 121.565137))
//        polylineOpt.add(LatLng(25.033739, 121.527886))
//        polylineOpt.add(LatLng(25.038716, 121.517758))
//        polylineOpt.add(LatLng(25.045656, 121.519636))
//        polylineOpt.add(LatLng(25.046200, 121.517533))
//
//        polylineOpt.color(Color.BLUE)

//
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(taipei, 13F));
//        val polyline = googleMap.addPolyline(polylineOpt)
//        polyline.width = 10f

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val binding = DataBindingUtil.inflate<FragmentMyMapBinding>(inflater,
//            R.layout.fragment_my_map,container,false)

        //return binding.root

        return inflater.inflate(R.layout.fragment_my_map, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        //mapFragment?.onCreate(savedInstanceState)
        //mapFragment?.onResume()

    }
}