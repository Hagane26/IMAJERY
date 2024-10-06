package com.example.imajery_v4

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.imajery_v4.databinding.ActivityMainBinding
import com.example.imajery_v4.ui.auth.auth_login
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedRef = getSharedPreferences("Data-IMAJERY", MODE_PRIVATE)
        val refSplash = sharedRef.getInt("splash_status",0)
        val refLogin = sharedRef.getInt("login_status",0)
        val refTimeLogin = sharedRef.getLong("login_time",0)

        val currentTime = ZonedDateTime.now().toInstant().toEpochMilli()
        val timeLogin = TimeUnit.MILLISECONDS.toDays(currentTime - refTimeLogin)

        Toast.makeText(this@MainActivity, "Login Time : $timeLogin", Toast.LENGTH_SHORT).show()

        if(refSplash == 0 || refLogin == 0 || timeLogin >= 1){
            startActivity(Intent(this@MainActivity,SplashActivity::class.java))
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard,
                R.id.navigation_materi,
                R.id.navigation_perkembangan
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}