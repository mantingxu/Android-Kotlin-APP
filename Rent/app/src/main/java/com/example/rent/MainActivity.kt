package com.example.rent

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.rent.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

//    lateinit var toggle:ActionBarDrawerToggle
    private lateinit var  drawerLayout:DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.myNavHostFragment)
        drawerLayout = binding.drawerLayout
        //val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navVIew: NavigationView = findViewById(R.id.navView)
        NavigationUI.setupWithNavController(navVIew, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)


    }


//
//        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        navVIew.setNavigationItemSelectedListener {
//
//            when(it.itemId)
//            {
//                R.id.nav_aboutme -> Toast.makeText(this,"Click me",Toast.LENGTH_SHORT).show()
//                R.id.nav_rateus -> rateApp()
//                else -> Toast.makeText(this,"Click me",Toast.LENGTH_SHORT).show()
//
//            }
//            true
//
//        }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }


//    fun rateApp() {
//        val uri = Uri.parse("market://details?id=$packageName")
//        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
//        try {
//            startActivity(myAppLinkToMarket)
//        } catch (e: ActivityNotFoundException) {
//            Toast.makeText(this, "Impossible to find an application for the market", Toast.LENGTH_LONG).show()
//        }
//    }

    // back 鍵功能啟動

//    override fun onOptionsItemSelected(item: MenuItem):Boolean {
//
//        if(toggle.onOptionsItemSelected(item))
//        {
//            return true
//
//        }
//        return super.onOptionsItemSelected(item)
//    }

}


//// nav_menu
