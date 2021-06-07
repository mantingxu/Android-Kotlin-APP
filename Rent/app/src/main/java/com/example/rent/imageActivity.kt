package com.example.rent

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.location.*
import java.util.*
import kotlin.collections.ArrayList

class imageActivity : AppCompatActivity() {
    //未完成
    val schoolimages = arrayOf(R.drawable.school1,R.drawable.school2,R.drawable.school3,R.drawable.school4)
    var counter = 0
    val PERMISSION_ID = 1010
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var x = "0.0"
    var y = "0.0"

    private lateinit var url:ArrayList<String>
    private lateinit var word:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val passname = intent.getStringExtra("NAME")
        val passaddress = intent.getStringExtra("ADDRESS")
        this.setTitle("詳細資訊")
        val pre_btn = findViewById<Button>(R.id.button6)
        val btn = findViewById<Button>(R.id.button7)
        val content = findViewById<TextView>(R.id.content)
        val map = findViewById<Button>(R.id.mapping)
        //val switcher = findViewById<ImageSwitcher>(R.id.imageSwitcher1)
        val pager = findViewById<ViewPager>(R.id.pager)
        val number = findViewById<TextView>(R.id.number)
        //setSwitching()
//        switcher.setFactory (ViewSwitcher.ViewFactory{
//            getImageView()
//        })
        word = intent.getStringExtra("remark")
        url = intent.getStringArrayListExtra("url")
        content.setText(word)
        val animOut = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right)
        val animIn = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left)

        val animOut1 = AnimationUtils.loadAnimation(this,android.R.anim.fade_out)
        val animIn1 = AnimationUtils.loadAnimation(this,android.R.anim.fade_in)
//        switcher.outAnimation = animOut
//        switcher.inAnimation = animIn


//        var imgs = listOf<Int>(R.drawable.doncic,R.drawable.basketball,R.drawable.baseball)
        var adapter = Adapter(url,this)
        pager.adapter = adapter


        RequestPermission()
        map.setOnClickListener{

            Log.d("Debug:",CheckPermission().toString())
            Log.d("Debug:",isLocationEnabled().toString())

            //getLastLocation()
            if(CheckPermission()){
                if(isLocationEnabled()){
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                    }
                    fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                        var location: Location? = task.result
                        if(location == null){
//                        NewLocationData()
                            Log.d("TAG0",NewLocationData().toString())
                            x = location?.latitude.toString()
                            y = location?.longitude.toString()
                            if (location != null) {
                                Log.d("Debug1:" ,"Your Location:"+ location.longitude)
                            }
                            if (location != null) {
                                Log.d("TAG1","You Current Location is : Long: "+ y + " , Lat: " + x + "\n")// + getCityName(location.latitude,location.longitude))
                            }

                        }else{
                            x = location?.latitude.toString()
                            y = location?.longitude.toString()
                            Log.d("Debug2:" ,"Your Location:"+ location.longitude)
                            Log.d("TAG2","You Current Location is : Long: "+ y + " , Lat: " + x + "\n")// + getCityName(location.latitude,location.longitude))
                            var (a,b) = getLong(x.toDouble(),y.toDouble())
                            val intent = Intent(this@imageActivity,MapActivity::class.java)
                            intent.putExtra("NAME",passname)
                            intent.putExtra("ADDRESS",passaddress)
                            intent.putExtra("NAME1",a)
                            intent.putExtra("ADDRESS1",b)
                            startActivity(intent)
                        }
                    }

                }else{
                    Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
                }
            }else{
                RequestPermission()
            }



        }

//        btn.setOnClickListener{
//            switcher.outAnimation = animOut
//            switcher.inAnimation = animIn
//
//            if(counter<=url.size-1)
//            {
//
//
////                val imageChanger = schoolimages[counter]
////                switcher.setImageResource(imageChanger)
//                val imageChanger = url[counter]
////                Picasso.get().load(imageChanger).into(switcher);
//                switcher.setImageURI(Uri.parse(imageChanger))
//                counter++
//
//            }
//            else
//            {
//                Toast.makeText(this,"這是最後一張了",Toast.LENGTH_SHORT).show()
//            }
//        }
//        pre_btn.setOnClickListener{
//            switcher.outAnimation = animIn
//            switcher.inAnimation = animOut
//
//            if(counter>0)
//            {
//                counter--
////                val imageChanger_back = schoolimages[counter]
////                switcher.setImageResource(imageChanger_back)
//                val imageChanger = url[counter]
//                switcher.setImageURI(Uri.parse(imageChanger))
//                //counter = schoolimages.size
//            }
//            else
//            {
//                Toast.makeText(this,"這已經是第一張了",Toast.LENGTH_SHORT).show()
//            }
//        }

    }

    private fun CheckPermission():Boolean{
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if(
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false

    }

    private fun RequestPermission(){
        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled():Boolean{
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }





    private fun getLastLocation(){
//        if(CheckPermission()){
//            if(isLocationEnabled()){
//                if (ActivityCompat.checkSelfPermission(
//                        this,
//                        ACCESS_FINE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                }
//                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
//                    var location: Location? = task.result
//                    if(location == null){
////                        NewLocationData()
//                        Log.d("TAG",NewLocationData().toString())
//                    }else{
//                        x = location?.latitude.toString()
//                        y = location?.longitude.toString()
//                        Log.d("Debug1:" ,"Your Location:"+ location.longitude)
//                        Log.d("TAG1","You Current Location is : Long: "+ x + " , Lat: " + y + "\n" + getCityName(location.latitude,location.longitude))
//                    }
//                }
//
//            }else{
//                Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
//            }
//        }else{
//            RequestPermission()
//        }
    }


    private fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
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
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,locationCallback,Looper.myLooper()
        )
    }

    private fun getCityName(lat: Double,long: Double):String{
        var cityName:String = ""
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,3)

        cityName = Adress.get(0).locality
        countryName = Adress.get(0).countryName
        Log.d("Debug:","Your City: " + cityName + " ; your Country " + countryName)
        return cityName
    }

    private fun getLong(lat: Double,long: Double):Array<String>{
        return arrayOf(lat.toString(),long.toString())
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
//            x = lastLocation?.latitude.toString()
//            y = lastLocation?.longitude.toString()
            Log.d("Debug10:","your last last location: "+ lastLocation.longitude.toString())
            Log.d("TAG10","You Last Location is : Long: "+ lastLocation.longitude + " , Lat: " + lastLocation.latitude + "\n" )//+ getCityName(lastLocation.latitude,lastLocation.longitude))

        }
    }


// switcher
    private fun getImageView(): ImageView
    {
        val imageView = ImageView(this)
        //imageView.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
//        imageView.setImageResource(R.id)

        return imageView

    }


}