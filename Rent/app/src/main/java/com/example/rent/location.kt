package com.example.rent

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.rent.databinding.FragmentLocationBinding
import com.google.android.gms.location.LocationServices


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [location.newInstance] factory method to
 * create an instance of this fragment.
 */
class location : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //private LocationManage locationManager;
    //private String CommandStr;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = DataBindingUtil.inflate<FragmentLocationBinding>(inflater,
            R.layout.fragment_location,container,false)
        // Inflate the layout for this fragment
        val client = LocationServices.getFusedLocationProviderClient(Activity())
        val CommandStr = LocationManager.GPS_PROVIDER;

//        binding.lat
//        binding.lon
//        binding.submit.setOnClickListener()
//        {
//            locationManager = (LocationManager) getSysteService(LOCATION_SERVICE)
//            if(ContextCompat.checkSelfPermission(Activity(),ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(Activity(),ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED)
//            {
//                //getCurrentLocation();
//                return;
//            }
//        }


        return binding.root;
        //return inflater.inflate(R.layout.fragment_location, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment location.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            location().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}