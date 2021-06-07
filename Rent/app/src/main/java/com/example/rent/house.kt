 package com.example.rent

import android.net.Uri
import kotlin.collections.ArrayList

//data class house(
//    val name: String,
//    val imageId:Int,
//    val price: String,
//    val tel: String,
//    val address: String,
//    val keeper: String,
//    val remark:String,
//    val arrayUri: ArrayList<String>
//)
data class house(
    var user:String?=null,
    var name: String?=null,
    var address: String?=null,
    var marker:String?=null,
    var keeper: String?=null,
    var price: String?=null,
    var remark: String?=null,
    var tel:String?=null,
    var arrayUri: ArrayList<String>?=null
)
//val url:String
//val arrayUri: ArrayList<Uri>