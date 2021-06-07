package com.example.rent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EditActivity : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    val newArrayList = arrayListOf<house>()
    private lateinit var newRecycleview: RecyclerView
    lateinit var id :String
    lateinit var delete :String
    lateinit var alertDialog: android.app.AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setTitle("刪除張貼")
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("確定要刪除？")
//        builder.setPositiveButton("Confirm",{ dialog, whichButton ->
//            deleteDate(delete)
////           val intent = Intent(this@EditActivity, MainActivity::class.java)
////            startActivity(intent)
//        })
//
//        builder.setNegativeButton("Cancel", { dialog, whichButton ->
//            Toast.makeText(this@EditActivity,"doesn't remove",Toast.LENGTH_SHORT).show()
//        })

//        val users = intent.getStringExtra("user")
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser != null) {
//            id = firebaseUser.uid
//        }
//        Log.d("TAG","id:"+id)


//        var house = mapOf<String,String>(
//            "address" to address,
//            "keeper" to keeper,
//            "marker" to marker,
//            "name" to name,
//            "price" to price,
//            "remark" to remark,
//            "tel" to tel,
//            "user" to user,
//            "arrayUri" to uri
//        )
        newRecycleview = findViewById(R.id.recycler2)
        newRecycleview.layoutManager = LinearLayoutManager(this)
        newRecycleview.setHasFixedSize(true)

        dbref = FirebaseDatabase.getInstance().getReference("house")
//        dbref = dbref.orderByChild("user").equalTo(firebaseUser.toString())
//            .child(id)
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        Log.d("TAG", userSnapshot.toString())
                            val user = userSnapshot.child("user").getValue().toString()
                        Log.d("TAG", user)
                        if (firebaseUser != null) {
                            Log.d("TAG", firebaseUser.email.toString())
                        }
                        if (firebaseUser != null) {
                            if(user == firebaseUser.email.toString()) {
                                val addhouse = userSnapshot.getValue(com.example.rent.house::class.java)
                                newArrayList.add(addhouse!!)
                            }
                        }
//                            val addhouse = userSnapshot.getValue(com.example.rent.house::class.java)
//                            newArrayList.add(addhouse!!)
                    }

//                    newRecycleview.adapter = EditAdapter(newArrayList)
                    newRecycleview.adapter = EditAdapter(newArrayList) { ball: house, position: Int ->

                        delete = ball.name.toString()
                        if (delete != null) {
                            if(delete.isNotEmpty())
                            {
                                val database = FirebaseDatabase.getInstance().getReference("house")
                                database.child(delete).removeValue().addOnSuccessListener {
                                    val adapter = newRecycleview.adapter
                                    if (adapter != null) {
                                        adapter.notifyDataSetChanged()
                                        adapter.notifyItemRemoved(position)
                                        val intent =  Intent( this@EditActivity,MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                    Toast.makeText(this@EditActivity,"remove successfully!",Toast.LENGTH_LONG).show()


                                }
//                                val dialog = builder.create()
//                                dialog.show()




                            }

                        }
                    }

                }
            }
        })


    }

    private fun deleteDate(userName:String)
    {

    }
}

