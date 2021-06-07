package com.example.rent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.rent.databinding.ActivityMainBinding
import com.example.rent.databinding.ActivityProfileBinding
import com.example.rent.databinding.FragmentGoogleBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
//import uploadActivity

class ProfileActivity : AppCompatActivity() {


    private lateinit var binding: ActivityProfileBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    private companion object {
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGH_IN_TAG"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTitle("房東專屬介面")
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.button2.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
        }

        binding.button3.setOnClickListener{
            startActivity(Intent(this@ProfileActivity,uploadActivity::class.java))
        }
        val firebaseUser = firebaseAuth.currentUser
        val email = firebaseUser?.email
        binding.button4.setOnClickListener{
            val intent = Intent(this@ProfileActivity,EditActivity::class.java)
            intent.putExtra("user",email)
            startActivity(intent)
        }
        binding.button5.setOnClickListener{
            val intent = Intent(this@ProfileActivity,recyclerActivity::class.java)
//            intent.putExtra("user",email)
            startActivity(intent)
        }

    }

    private fun checkUser(){

        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this,MainActivity::class.java))
            finish();
        }
        else
        {
            val email = firebaseUser.email
            binding.textView5.text = email

        }


    }
}

//https://www.youtube.com/watch?v=clU6s0M88OE