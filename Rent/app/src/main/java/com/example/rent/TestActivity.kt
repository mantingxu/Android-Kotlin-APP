package com.example.rent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.*
import com.github.dhaval2404.imagepicker.ImagePicker

class TestActivity : AppCompatActivity() {
    var counter = 0
    private var images: ArrayList<Uri>? = null

    companion object {
        const val REQUEST_FROM_CAMERA = 1000
        const val REQUEST_FROM_GALLERY = 1001
        const val REQUEST_FROM_SINGLE_GALLERY = 1002
    }

    private lateinit var take: Button
    private lateinit var pick: Button
    private lateinit var upload: Button
    private lateinit var nextimage: Button
    private lateinit var previousimage: Button
    private lateinit var pick_one: Button
    //    private lateinit var pick_image:ImageView
    private lateinit var switch: ImageSwitcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        images = ArrayList()

        upload = findViewById(R.id.button12)
        pick = findViewById(R.id.pick_multi)
        take = findViewById(R.id.camera)
        switch = findViewById(R.id.imageSwitcher2)
        nextimage = findViewById(R.id.nextStep)
        previousimage = findViewById(R.id.previousStep)
        pick_one = findViewById(R.id.pick_single)
//        pick_image = findViewById(R.id.imageView2)


        switch.setFactory(ViewSwitcher.ViewFactory {
            getImageView()
        })
        val animOut = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right)
        val animIn = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left)

        val animOut1 = AnimationUtils.loadAnimation(this,android.R.anim.fade_out)
        val animIn1 = AnimationUtils.loadAnimation(this,android.R.anim.fade_in)
        switch.outAnimation = animOut
        switch.inAnimation = animIn


        nextimage.setOnClickListener {

            if (counter == images!!.size - 1) {
                Toast.makeText(this, "這是最後一張了", Toast.LENGTH_SHORT).show()
            } else {
                counter++
                val imageChanger = images!![counter]
                switch.setImageURI(imageChanger)
                Log.d("TAG","counter is "+ counter)
            }
        }
        previousimage.setOnClickListener{

            if(counter <= 0)
            {
                Toast.makeText(this,"這已經是第一張了",Toast.LENGTH_SHORT).show()
            }
            else
            {
                counter--
                val imageChanger_back = images!![counter]
                switch.setImageURI(imageChanger_back)
                Log.d("TAG","counter is "+ counter)
            }
        }
        // gallery(multiple)
        pick.setOnClickListener {
            images = ArrayList()
            switch.setImageURI(null)
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                REQUEST_FROM_GALLERY
            )
        }
        //gallery(single)
        pick_one.setOnClickListener {
            pickOneImageFromGallery()
        }


        // camera
        take.setOnClickListener {
            switch.setImageURI(null)
            images = ArrayList()
            captureImageUsingCamera()

        }
    } // end OnCreate

    private fun pickOneImageFromGallery() {
        ImagePicker.with(this)
            .galleryOnly()
            .start(REQUEST_FROM_SINGLE_GALLERY)
    }

    private fun captureImageUsingCamera() {
        ImagePicker.with(this)
            .cameraOnly()
            .start(REQUEST_FROM_CAMERA)
    }
//     imageSwitcher
    private fun getImageView(): ImageView {
        val imageView = ImageView(this)
        //imageView.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
//        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.setImageResource(R.drawable.baseball)
        return imageView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_FROM_CAMERA -> {
//                    pick_image.setImageURI(data?.data!!)
                    switch.setImageURI(data?.data!!)
                    upload.setOnClickListener {
                        FirebaseManager().uploadSingleImage(this, data.data!!)
                    }

                }
                REQUEST_FROM_GALLERY -> {
                    Log.d("TAG", "requestCode is" + requestCode)
                    val count = data?.clipData!!.itemCount
                    if (count != 0 && count != 1) {
                        for (i in 0 until count) {
                            val imageUrl = data.clipData!!.getItemAt(i).uri
                            images!!.add(imageUrl)
                            switch.setImageURI(images?.get(i))
                        }
//                        pick_image.setImageURI(images?.get(0))
                        //Log.d("TAG",images?.get(0).toString())
                    } else if (count == 1) {
                        switch.setImageURI(data.data!!)
                    } else {
                        Log.d("TAG", "count is" + count)
                    }


                    upload.setOnClickListener {
                        //FirebaseManager().uploadImage(this,data?.data!!)
                        images?.let { it1 -> FirebaseManager().uploadImage(this, it1) }
                    }
                }
                REQUEST_FROM_SINGLE_GALLERY -> {
                        switch.setImageURI(data?.data!!)
                    upload.setOnClickListener {
                        FirebaseManager().uploadSingleImage(this, data.data!!)
                    }
                }

            }

        }
    }

}




//package com.example.rent

//import android.app.Activity
//import android.app.AlertDialog
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.util.Log
//import android.widget.*
//import androidx.annotation.NonNull
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.net.toUri
//import com.example.rent.databinding.ActivityUploadBinding
//import com.google.android.gms.common.internal.Constants
//import com.google.android.gms.tasks.OnFailureListener
//import com.google.android.gms.tasks.OnSuccessListener
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.StorageReference
//import com.google.firebase.storage.UploadTask
//import java.io.IOException
//import java.net.URL
//import java.text.SimpleDateFormat
//import java.util.*

//class uploadActivity : AppCompatActivity() {
//
//
//    private var images: ArrayList<Uri?>? = null
//    private lateinit var URL: ArrayList<String>
//    private lateinit var binding: ActivityUploadBinding
//    private lateinit var database: DatabaseReference
//    private val PICK_IMAGE_REQUEST = 100
//    private var filePath: Uri? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        this.setTitle("新增租屋資訊（房東）")
//        binding = ActivityUploadBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.imageChoose.setOnClickListener {
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100)
//        }
//        Log.d("tag", images?.size.toString()+ "images")
//        binding.registerBtn.setOnClickListener {
//            val builder = AlertDialog.Builder(this)
//            builder.setMessage("Uploading!!! Please wait......").show()
//            uploadImage()
//        }
//    }
//
//
//    private fun uploadImage() {
//        var individual:Uri
//        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
//        val now = Date()
//        val filename = formatter.format(now)
//        val storageReference = FirebaseStorage.getInstance().getReference("images/$filename")
////        for (upload_count in 0 until images?.size!!) {
////            individual = images?.get(upload_count)!!
////            val Task = storageReference.putFile(individual)
////            Task.continueWith{
////                if (!it.isSuccessful) {
////                    it.exception?.let { t ->
////                        throw t
////                    }
////                }
////                storageReference.downloadUrl
////            }.addOnCompleteListener {
////                if (it.isSuccessful) {
////                    it.result!!.addOnSuccessListener { task ->
////                        val myUri = task.toString()
////                        Log.d("tag", myUri)
////                        URL?.add(myUri)
//////                        val houseName = binding.houseName.text.toString()
//////                        val where = binding.where.text.toString()
//////                        val who = binding.who.text.toString()
//////                        val housePrice = binding.housePrice.text.toString()
//////                        val telephone = binding.telephone.text.toString()
//////                        val available = binding.howmany.text.toString()
//////                        val remark = binding.remark.text.toString()
//////
//////                        database = FirebaseDatabase.getInstance().getReference("house")
//////                        val houses = house(
//////                            houseName,
//////                            housePrice,
//////                            telephone,
//////                            where,
//////                            available,
//////                            who,
//////                            remark,
//////                            URL
//////                        )
//////                        database.child(houseName).setValue(houses).addOnSuccessListener {
//////                            binding.houseName.text.clear()
//////                            binding.where.text.clear()
//////                            binding.who.text.clear()
//////                            binding.housePrice.text.clear()
//////                            binding.telephone.text.clear()
//////                            binding.howmany.text.clear()
//////                            binding.remark.text.clear()
//////                            binding.imageView.setImageDrawable(null)
//////
//////                            // Toast.makeText(this@uploadActivity,"Saved",Toast.LENGTH_SHORT).show()
//////                        }
//////                            .addOnFailureListener {
//////                                // Toast.makeText(this@uploadActivity,"Failed",Toast.LENGTH_SHORT).show()
//////                            }
////
////                    }
////
////
//////                    Toast.makeText(this@uploadActivity, "Successfully upload", Toast.LENGTH_SHORT)
//////                        .show()
////                }
//////            }
////            }
////        }
////        val houseName = binding.houseName.text.toString()
////        val where = binding.where.text.toString()
////        val who = binding.who.text.toString()
////        val housePrice = binding.housePrice.text.toString()
////        val telephone = binding.telephone.text.toString()
////        val available = binding.howmany.text.toString()
////        val remark = binding.remark.text.toString()
////
////        database = FirebaseDatabase.getInstance().getReference("house")
////        val houses = house(
////            houseName,
////            housePrice,
////            telephone,
////            where,
////            available,
////            who,
////            remark,
////            URL
////        )
////        database.child(houseName).setValue(houses).addOnSuccessListener {
////            binding.houseName.text.clear()
////            binding.where.text.clear()
////            binding.who.text.clear()
////            binding.housePrice.text.clear()
////            binding.telephone.text.clear()
////            binding.howmany.text.clear()
////            binding.remark.text.clear()
////            binding.imageView.setImageDrawable(null)
////
////            // Toast.makeText(this@uploadActivity,"Saved",Toast.LENGTH_SHORT).show()
////        }
////            .addOnFailureListener {
////                // Toast.makeText(this@uploadActivity,"Failed",Toast.LENGTH_SHORT).show()
////            }
////
////        Toast.makeText(this@uploadActivity, "Successfully upload", Toast.LENGTH_SHORT).show()
//
//        val uploadTask = storageReference.putFile(filePath!!)
//        uploadTask.continueWith {
//            if (!it.isSuccessful) {
//                it.exception?.let { t ->
//                    throw t
//                }
//            }
//            storageReference.downloadUrl
//        }.addOnCompleteListener {
//            if (it.isSuccessful) {
//                it.result!!.addOnSuccessListener { task ->
//                    val myUri = task.toString()
//                    Log.d("tag", myUri)
//                    val houseName = binding.houseName.text.toString()
//                    val where = binding.where.text.toString()
//                    val who = binding.who.text.toString()
//                    val housePrice = binding.housePrice.text.toString()
//                    val telephone = binding.telephone.text.toString()
//                    val available = binding.howmany.text.toString()
//                    val remark = binding.remark.text.toString()
//
//                    database = FirebaseDatabase.getInstance().getReference("house")
//                    val houses = house(
//                        houseName,
//                        housePrice,
//                        telephone,
//                        where,
//                        available,
//                        who,
//                        remark,
//                        myUri
//                    )
//                    database.child(houseName).setValue(houses).addOnSuccessListener {
//                        binding.houseName.text.clear()
//                        binding.where.text.clear()
//                        binding.who.text.clear()
//                        binding.housePrice.text.clear()
//                        binding.telephone.text.clear()
//                        binding.howmany.text.clear()
//                        binding.remark.text.clear()
//                        binding.imageView.setImageDrawable(null)
//
//                        // Toast.makeText(this@uploadActivity,"Saved",Toast.LENGTH_SHORT).show()
//                    }
//                        .addOnFailureListener {
//                            // Toast.makeText(this@uploadActivity,"Failed",Toast.LENGTH_SHORT).show()
//                        }
//                }
//
//
//                Toast.makeText(this@uploadActivity, "Successfully upload", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }
//
//
////    private fun getImageView(): ImageView {
////        val imageView = ImageView(this)
////        //imageView.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
////        imageView.scaleType = ImageView.ScaleType.FIT_XY
////        imageView.setImageResource(R.drawable.school1)
////
////        return imageView
////
////    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
//            if (data == null || data.data == null) {
//                return
//            }
////            images = data.getParcelableArrayListExtra(Intent.EXTRA_ALLOW_MULTIPLE)
////            Log.d("tag123", images?.size.toString()+ "images")
////            if(data.clipData?.itemCount)
//            filePath = data.data
////            val count = data.clipData!!.itemCount
////            for(i in 0 until count)
////            {
////                val imageUri = data.clipData!!.getItemAt(i).uri
////                images!!.add(imageUri)
////            }
//            try {
//                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
//                binding.imageView.setImageBitmap(bitmap)
////                binding.imageView.setImageURI(filePath)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//        //Toast.makeText(this,requestCode,Toast.LENGTH_SHORT).show()
////        if(requestCode == 100 ) {
////            if (requestCode == Activity.RESULT_OK) {
////                ImageUri = data?.data!!
////                binding.imageView.setImageURI(ImageUri)
////                if(data!!.clipData != null)
////                {
////                    val count = data.clipData!!.itemCount
////
////                    for(i in 0 until count)
////                    {
////                        val imageUri = data.clipData!!.getItemAt(i).uri
////                        images!!.add(imageUri)
////                    }
////                    binding.alert.setVisibility(View.VISIBLE)
////                    binding.alert.setText("You Have Selected" + images!!.size + "images")
////                    binding.imageChoose.setVisibility(View.GONE)
////                    uploadImage(this@uploadActivity,data.data!!)
////                }
////                else
////                {
////                    Toast.makeText(this,"Please select image",Toast.LENGTH_SHORT).show()
////                }
////            }
////
////        }
////        else{
////            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
////        }
//
//
//    }
//}