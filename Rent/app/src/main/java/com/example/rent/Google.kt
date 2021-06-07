package com.example.rent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.rent.databinding.ActivityMainBinding
import com.example.rent.databinding.FragmentGoogleBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Google.newInstance] factory method to
 * create an instance of this fragment.
 */
class Google : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    //private lateinit var binding:ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

//     private companion object myObject{
//
//    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //binding.ActivityMainBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGoogleBinding>(inflater,
            R.layout.fragment_google,container,false)
        // Inflate the layout for this fragment

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(Activity(),googleSignInOptions)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.googleSignInBtn.setOnClickListener{
            Log.d(TAG,"onCreate: begin Google SignIn")
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }
        return binding.root
        //return inflater.inflate(R.layout.fragment_google, container, false)
    }



    private fun checkUser(){

        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            startActivity(Intent(activity,ProfileActivity::class.java))
            activity?.finish();
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            Log.d(TAG,"OnActivityResult: Google SignIn intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }
            catch (e: Exception){
                Log.d(TAG,"OnActivityResult: ${e.message}")
            }
        }
    }


    private fun firebaseAuthWithGoogleAccount(account:GoogleSignInAccount?)
    {
        Log.d(TAG,"firebaseAuthWithGoogleAccount: begin firebase auth with google account")

        val credential = GoogleAuthProvider.getCredential(account!!.idToken,null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG,"firebaseAuthWithGoogleAccount: LoggedIn")

                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email

                Log.d(TAG,"firebaseAuthWithGoogleAccount: Uid: $uid")
                Log.d(TAG,"firebaseAuthWithGoogleAccount: Email: $email")

                if(authResult.additionalUserInfo!!.isNewUser)
                {
                    Log.d(TAG,"firebaseAuthWithGoogleAccount: Account created... \n$email")
                    //Toast.makeText(this@Google,"LoggedIn...\n$email",Toast.LENGTH_SHORT).show()

                }
                else{
                    Log.d(TAG,"firebaseAuthWithGoogleAccount: Existing user... \n$email")
                }
                startActivity(Intent(getActivity(),ProfileActivity::class.java))
                activity?.finish();
            }

            .addOnFailureListener{ e ->
                Log.d(TAG,"firebaseAuthWithGoogleAccount: Loggin Failed due to ${e.message}")
            }


    }





    companion object {
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGH_IN_TAG"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Google.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Google().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}