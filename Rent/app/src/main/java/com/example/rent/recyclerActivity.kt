package com.example.rent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class recyclerActivity : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var newRecycleview: RecyclerView
    val newArrayList = arrayListOf<house>()
    private lateinit var remark:String
    private lateinit var marker:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        this.setTitle("張貼總覽")

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser


        newRecycleview = findViewById(R.id.recycler3)
        newRecycleview.layoutManager = LinearLayoutManager(this)
        newRecycleview.setHasFixedSize(true)



        dbref = FirebaseDatabase.getInstance().getReference("house")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        Log.d("TAG", userSnapshot.toString())
                        val user = userSnapshot.child("user").getValue().toString()
                        remark = userSnapshot.child("remark").getValue().toString()
                        marker = userSnapshot.child("marker").getValue().toString()
                        Log.d("TAG", user)
                        if (firebaseUser != null) {
                            Log.d("TAG", firebaseUser.email.toString())
                        }
                        if (firebaseUser != null) {
                            if (user == firebaseUser.email.toString()) {
                                val addhouse =
                                    userSnapshot.getValue(com.example.rent.house::class.java)
                                newArrayList.add(addhouse!!)
                            }
                        }
//                            val addhouse = userSnapshot.getValue(com.example.rent.house::class.java)
//                            newArrayList.add(addhouse!!)
                    }

//                    newRecycleview.adapter = EditAdapter(newArrayList)
                    newRecycleview.adapter =
                        UpdateAdapter(newArrayList) { ball: house, position: Int ->
                            val intent = Intent(this@recyclerActivity, UpdateActivity::class.java)
                            intent.putExtra("NAME", ball.name)
                            intent.putExtra("price", ball.price)
                            intent.putExtra("ADDRESS", ball.address)
                            intent.putExtra("tel", ball.tel)
                            intent.putExtra("keeper", ball.keeper)
                            intent.putExtra("remark", ball.remark)
                            intent.putExtra("url", ball.arrayUri)
                            intent.putExtra("marker",ball.marker)
                            startActivity(intent)

                        }

                }
            }


        })


    }
}
