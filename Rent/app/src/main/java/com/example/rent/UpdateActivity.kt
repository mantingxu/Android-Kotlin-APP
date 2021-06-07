package com.example.rent

import com.example.rent.R
import com.example.rent.TestActivity
import com.example.rent.house
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.rent.databinding.ActivityUpdateBinding
import com.example.rent.databinding.ActivityUploadBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.sql.Struct
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UpdateActivity : AppCompatActivity() {

    var counter = 0
//    private var images: ArrayList<Uri?>? = null
    private lateinit var images: ArrayList<String>
    private lateinit var URL: ArrayList<String>
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var database: DatabaseReference
    private var q: ArrayList<String> = arrayListOf<String>()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email: String
    private lateinit var marker: String
    lateinit var alertDialog: AlertDialog
    lateinit var firebaseUser: FirebaseUser
    private lateinit var x:Uri
    companion object {
        const val REQUEST_FROM_CAMERA = 1000
        const val REQUEST_FROM_GALLERY = 1001
        const val REQUEST_FROM_SINGLE_GALLERY = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        this.setTitle("更新租屋資訊（房東）")
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("NAME")
        val price = intent.getStringExtra("price")
        val address = intent.getStringExtra("ADDRESS")
        val tel = intent.getStringExtra("tel")
        val keeper = intent.getStringExtra("keeper")
        val remark = intent.getStringExtra("remark")
        val arrayUri:ArrayList<String> = intent.getStringArrayListExtra("url")
        val marker1 = intent.getStringExtra("marker")


//        binding.imageSwitcher2.setImageURI(arrayUri)

//        binding.radioGroup.check();





        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!
        if (firebaseUser != null) {
            email = firebaseUser.email.toString()
        }

        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        marker = "無"
        images = arrayUri
        Log.d("TAG",name)
        binding.houseName.setText(name)
        binding.housePrice.setText(price)
        binding.where.setText(address)
        binding.who.setText(keeper)
        binding.telephone.setText(tel)
        binding.remark.setText(remark)
        binding.where2.setText(marker1)
         x = Uri.parse(images!![0])
//        binding.imageSwitcher2.setImageURI(x)



        binding.btn123.setOnClickListener {
            Toast.makeText(this@UpdateActivity,"請上傳照片",Toast.LENGTH_SHORT).show()
        }

        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder
            .setIcon(R.drawable.loading)
//            .setTitle("標題")
            .setMessage("上傳中...")
        // 建立對話方塊
        alertDialog = alertBuilder.create()








        binding.imageSwitcher2.setFactory(ViewSwitcher.ViewFactory {
            getImageView()
        })
        val animOut = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
        val animIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)

        binding.imageSwitcher2.outAnimation = animOut
        binding.imageSwitcher2.inAnimation = animIn


        binding.button9.setOnClickListener {

            if (counter == images!!.size - 1) {
                Toast.makeText(this, "這是最後一張了", Toast.LENGTH_SHORT).show()
            } else {
                counter++
                val imageChanger = Uri.parse(images!![counter])
                binding.imageSwitcher2.setImageURI(imageChanger)
                Log.d("TAG", "counter is " + counter)
            }
        }
        binding.button8.setOnClickListener {

            if (counter <= 0) {
                Toast.makeText(this, "這已經是第一張了", Toast.LENGTH_SHORT).show()
            } else {
                counter--
                val imageChanger_back = Uri.parse(images!![counter])
                binding.imageSwitcher2.setImageURI(imageChanger_back)
                Log.d("TAG", "counter is " + counter)
            }
        }
        // gallery(multiple)
        binding.pickMulti.setOnClickListener {
            images = ArrayList()
            binding.imageSwitcher2.setImageURI(null)
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                TestActivity.REQUEST_FROM_GALLERY
            )
        }
        //gallery(single)
        binding.pickSingle.setOnClickListener {
            pickOneImageFromGallery()
        }


        // camera
        binding.camera.setOnClickListener {
            binding.imageSwitcher2.setImageURI(null)
            images = ArrayList()
            captureImageUsingCamera()

        }

    }


//    private fun uploadImage() {
//        var individual:Uri
//        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
//        val now = Date()
//        val filename = formatter.format(now)
//        val storageReference = FirebaseStorage.getInstance().getReference("images/$filename")
//
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                TestActivity.REQUEST_FROM_CAMERA -> {
//                    pick_image.setImageURI(data?.data!!)
                    binding.imageSwitcher2.setImageURI(data?.data!!)
                    binding.btn123.setOnClickListener {
                        alertDialog.show()
                        uploadSingleImage(this, data.data!!)
                        //Thread.sleep(1L)
                        //uploadData()
                    }

                }


                TestActivity.REQUEST_FROM_GALLERY -> {
                    Log.d("TAG", "requestCode is" + requestCode)
                    //val count = data?.clipData!!.itemCount
                    if (data?.clipData != null) {
                        val count = data?.clipData!!.itemCount
                        Log.d("TAG","count:"+count)
                        if (count != 0) {
                            for (i in 0 until count) {
//                            val imageUrl = data.clipData!!.getItemAt(i).uri
                                val imageUrl = data.clipData!!.getItemAt(i).uri
                                images!!.add(imageUrl.toString())
                                binding.imageSwitcher2.setImageURI(Uri.parse(images?.get(i)))
                            }
//                        pick_image.setImageURI(images?.get(0))
                            //Log.d("TAG",images?.get(0).toString())
//                    } else if (count == 1) {
//                        binding.imageSwitcher2.setImageURI(data.data!!)
//                    } else {
                            Log.d("TAG", "count is" + count)
                        }
                    }
                    else{
                        Toast.makeText(this@UpdateActivity,"請選擇多張照片",Toast.LENGTH_LONG).show()
                    }
//                    else
//                    {
//
//                    }
                    binding.btn123.setOnClickListener {
                        alertDialog.show()
//                        FirebaseManager().uploadImage(this,data?.data!!)
                        images?.let { it1 -> uploadImage(this, it1) }
                        //uploadData()
//                        alertDialog.dismiss()
                    }
                }


                TestActivity.REQUEST_FROM_SINGLE_GALLERY -> {
                    binding.imageSwitcher2.setImageURI(data?.data!!)
                    binding.btn123.setOnClickListener {
                        alertDialog.show()
                        //FirebaseManager().uploadSingleImage(this, data.data!!)
                        uploadSingleImage(this, data.data!!)
                        //uploadData()
//                        alertDialog.dismiss()
                    }
                }


            }

        }
    }

    // imageSwitcher need
    private fun getImageView(): ImageView {
        val imageView = ImageView(this)
        //imageView.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
//        imageView.scaleType = ImageView.ScaleType.FIT_XY
//        imageView.setImageResource(R.drawable.nopic)
        Picasso.get().load(x).into(imageView)
//        imageView.setImageURI(x)
        return imageView
    }

    // gallery
    private fun pickOneImageFromGallery() {
        ImagePicker.with(this)
            .galleryOnly()
            .start(TestActivity.REQUEST_FROM_SINGLE_GALLERY)
    }

    //camera
    private fun captureImageUsingCamera() {
        ImagePicker.with(this)
            .cameraOnly()
            .start(TestActivity.REQUEST_FROM_CAMERA)
    }

    private val TAG = "FirebaseManager"

    private val mStorageRef = FirebaseStorage.getInstance().reference


    private fun uploadSingleImage(mContext: Context, imageURI: Uri) {
        if(imageURI == null)
        {
            Toast.makeText(this@UpdateActivity,"請上傳照片",Toast.LENGTH_SHORT).show()
        }
        val houseName = binding.houseName.text.toString()
        val where = binding.where.text.toString()
        val who = binding.who.text.toString()
        val housePrice = binding.housePrice.text.toString()
        val telephone = binding.telephone.text.toString()
        val remark = binding.remark.text.toString()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)
        val uploadTask = mStorageRef.child("users/$filename").putFile(imageURI)

        uploadTask.addOnSuccessListener {

            Log.d("TAG", "Image Upload Suceesfully!")
            val downloadUrl = mStorageRef.child("users/$filename").downloadUrl
            downloadUrl.addOnSuccessListener {
                Log.d("TAG", "Image PATH:$it")
                q.add(it.toString())
                Log.d("TAG","marker:"+marker)
                val houses = house(
                    email,
                    houseName,
                    where,
                    marker,
                    who,
                    housePrice,
                    remark,
                    telephone,
                    q
                )
                FirebaseDatabase.getInstance().getReference("house").child(houseName)
                    .setValue(houses).addOnSuccessListener {
                        binding.houseName.text.clear()
                        binding.where.text.clear()
                        binding.who.text.clear()
                        binding.housePrice.text.clear()
                        binding.telephone.text.clear()
                        binding.howmany.text.clear()
                        binding.remark.text.clear()
                        binding.imageSwitcher2.setImageResource(R.drawable.nopic)
                        alertDialog.dismiss()
                        Toast.makeText(this@UpdateActivity, "資料上傳成功", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener {
                        Toast.makeText(this@UpdateActivity, "網路不穩定請重新上傳資料", Toast.LENGTH_LONG)
                            .show()
                    }


            }

        }
    }


    private fun uploadImage(mContext: Context, imageURI: ArrayList<String>) {
        if(imageURI.size == null)
        {
            Toast.makeText(this@UpdateActivity,"請上傳照片",Toast.LENGTH_SHORT).show()
        }
        val houseName = binding.houseName.text.toString()
        val where = binding.where.text.toString()
        val who = binding.who.text.toString()
        val housePrice = binding.housePrice.text.toString()
        val telephone = binding.telephone.text.toString()
//    val available = binding.howmany.text.toString()
        val remark = binding.remark.text.toString()
        for (i in 0 until imageURI.size) {
            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val filename = formatter.format(now) + i
            val uploadTask =
                Uri.parse(imageURI.get(i))?.let { mStorageRef.child("users/$filename").putFile(it) }

            if (uploadTask != null) {
                uploadTask.addOnSuccessListener {

                    Log.d("TAG", "Image Upload Suceesfully!")

                    val downloadUrl = mStorageRef.child("users/$filename").downloadUrl
                    downloadUrl.addOnSuccessListener {
                        Log.d("TAG", "Image PATH:$it")
                        q.add(it.toString())
                        if (marker == null)
                        {
                            marker = "無"
                        }
                        val houses = house(
                            email,
                            houseName,
                            where,
                            marker,
                            who,
                            housePrice,
                            remark,
                            telephone,
                            q
                        )
                        FirebaseDatabase.getInstance().getReference("house").child(houseName)
                            .setValue(houses).addOnSuccessListener {
                                binding.houseName.text.clear()
                                binding.where.text.clear()
                                binding.who.text.clear()
                                binding.housePrice.text.clear()
                                binding.telephone.text.clear()
                                binding.howmany.text.clear()
                                binding.remark.text.clear()
                                binding.imageSwitcher2.setImageResource(R.drawable.nopic)
                                alertDialog.dismiss()
                                Toast.makeText(this@UpdateActivity, "資料上傳成功", Toast.LENGTH_LONG).show()
                            }.addOnFailureListener {
                                Toast.makeText(this@UpdateActivity, "網路不穩定請重新上傳資料", Toast.LENGTH_LONG)
                                    .show()
                            }


                    }

                }.addOnFailureListener {
                    Log.d("TAG", "Image Upload Failed!")
                }
            }
        }
    }


//    private fun uploadData() {
//        val houseName = binding.houseName.text.toString()
//        val where = binding.where.text.toString()
//        val who = binding.who.text.toString()
//        val housePrice = binding.housePrice.text.toString()
//        val telephone = binding.telephone.text.toString()
////    val available = binding.howmany.text.toString()
//        val remark = binding.remark.text.toString()
//
////        q.add("123")
//        database = FirebaseDatabase.getInstance().getReference("house")
//        val houses = house(
//            email,
//            houseName,
//            where,
//            marker,
//            who,
//            housePrice,
//            remark,
//            telephone,
//            q
//        )
//        database.child(houseName).setValue(houses).addOnSuccessListener {
//            binding.houseName.text.clear()
//            binding.where.text.clear()
//            binding.who.text.clear()
//            binding.housePrice.text.clear()
//            binding.telephone.text.clear()
//            binding.howmany.text.clear()
//            binding.remark.text.clear()
//            binding.imageSwitcher2.setImageDrawable(null)
//
//            // Toast.makeText(this@uploadActivity,"Saved",Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener {
//            // Toast.makeText(this@uploadActivity,"Failed",Toast.LENGTH_SHORT).show()
//        }
//    }


}




