package com.example.rent

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FirebaseManager {
    private val TAG = "FirebaseManager"

    private val mStorageRef = FirebaseStorage.getInstance().reference
    fun uploadSingleImage(mContext :Context,imageURI : Uri)
    {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)
//        val builder = AlertDialog.Builder(mContext)
//        builder.setMessage("Uploading!!! Please wait......").show()
        val uploadTask = mStorageRef.child("users/$filename").putFile(imageURI)
        uploadTask.addOnSuccessListener {

            Log.d("TAG","Image Upload Suceesfully!")

            val downloadUrl = mStorageRef.child("users/$filename").downloadUrl
            downloadUrl.addOnSuccessListener {
                Log.d("TAG","Image PATH:$it")
            }
        }.addOnFailureListener {
            Log.d("TAG","Image Upload Failed!")
        }
    }



fun uploadImage(mContext:Context, imageURI: ArrayList<Uri>)
{

//        val builder = AlertDialog.Builder(mContext)
//        builder.setMessage("Uploading!!! Please wait......").show()
    for (i in 0 until imageURI.size)
    {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)+i
        val uploadTask = mStorageRef.child("users/$filename").putFile(imageURI.get(i))
        uploadTask.addOnSuccessListener {

            Log.d("TAG","Image Upload Suceesfully!")

            val downloadUrl = mStorageRef.child("users/$filename").downloadUrl
            downloadUrl.addOnSuccessListener {
                Log.d("TAG","Image PATH:$it")
            }
        }.addOnFailureListener {
            Log.d("TAG","Image Upload Failed!")
        }
    }

}
}