package com.example.rent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rent.databinding.ActivityGoogleBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class GoogleActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityGoogleBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    // 建立常數
    private companion object {
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    private var i:Int = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTitle("登入")
        binding = ActivityGoogleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this@GoogleActivity,googleSignInOptions)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.googleSignInBtn.setOnClickListener{
            Log.d(TAG,"onCreate: begin Google SignIn")
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }




    private fun checkUser(){

        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            startActivity(Intent(this@GoogleActivity,ProfileActivity::class.java))
            finish();
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            Log.d(TAG,"OnActivityResult: Google SignIn intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                Log.d(TAG,"hello no error in here")
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }
            catch (e: Exception){
                Log.d(TAG,"OnActivityResult: ${e.message}")
            }
        }
    }
    private fun updateUI(user: FirebaseUser?) {

    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?)
    {
        Log.d(TAG,"firebaseAuthWithGoogleAccount: begin firebase auth with google account")

        val credential = GoogleAuthProvider.getCredential(account!!.idToken,null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG,"firebaseAuthWithGoogleAccount: LoggedIn")

                val firebaseUser = firebaseAuth.currentUser
                updateUI(firebaseUser)
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email

                Log.d(TAG,"firebaseAuthWithGoogleAccount: Uid: $uid")
                Log.d(TAG,"firebaseAuthWithGoogleAccount: Email: $email")

//                if(authResult.additionalUserInfo!!.isNewUser)
//                {
//                    Log.d(TAG,"firebaseAuthWithGoogleAccount: Account created... \n$email")
//                    //Toast.makeText(this@Google,"LoggedIn...\n$email",Toast.LENGTH_SHORT).show()
//
//                }
//                else{
//                    Log.d(TAG,"firebaseAuthWithGoogleAccount: Existing user... \n$email")
//                }
                // activity 之間的頁面跳轉
                startActivity(Intent(this@GoogleActivity,ProfileActivity::class.java))
                finish();
            }

            .addOnFailureListener{ e ->
                Log.d(TAG,"firebaseAuthWithGoogleAccount: Loggin Failed due to ${e.message}")
            }


    }


}