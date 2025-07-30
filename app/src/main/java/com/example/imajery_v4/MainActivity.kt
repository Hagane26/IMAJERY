package com.example.imajery_v4

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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
import androidx.appcompat.app.AppCompatDelegate
import com.example.imajery_v4.databinding.ActivityAuthRegisterBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
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
        val refUserID = sharedRef.getInt("userID",0)

        val currentTime = ZonedDateTime.now().toInstant().toEpochMilli()
        val timeLogin = TimeUnit.MILLISECONDS.toDays(currentTime - refTimeLogin)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //Toast.makeText(this@MainActivity, "Login Time : $timeLogin", Toast.LENGTH_SHORT).show()

        if(refSplash == 0 || refLogin == 0 || timeLogin >= 1 || refUserID == 0){
            startActivity(Intent(this@MainActivity,SplashActivity::class.java))
        }

        val navView: BottomNavigationView = binding.navView
        mediaPlayer?.stop()
        mediaPlayer?.release()
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
        class AuthRegisterActivity : AppCompatActivity() {

            private lateinit var binding: ActivityAuthRegisterBinding

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityAuthRegisterBinding.inflate(layoutInflater)
                setContentView(binding.root)

                // Ini adalah tempat Anda menempatkan kode yang Anda berikan
                val etTanggalLahir = binding.etRegisTglLahir // Mengakses EditText dari ViewBinding

                etTanggalLahir.setOnClickListener {
                    val builder = MaterialDatePicker.Builder.datePicker()
                    val picker = builder.build()

                    picker.addOnPositiveButtonClickListener { selection ->
                        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                        calendar.timeInMillis = selection
                        val format = SimpleDateFormat("dd/MM/yyyy", Locale("id", "ID")) // Format untuk Indonesia
                        etTanggalLahir.setText(format.format(calendar.time))
                    }
                    // Penting: Gunakan supportFragmentManager jika di Activity
                    picker.show(supportFragmentManager, picker.toString())
                }

                // Lakukan inisialisasi view lainnya di sini jika ada
                // Contoh: Inisialisasi AutoCompleteTextView
                val bidangOlahragaOptions = resources.getStringArray(R.array.sport_options) // Anda perlu membuat array ini
                val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, bidangOlahragaOptions)
                (binding.tilBidangOlahraga.editText as? AutoCompleteTextView)?.setAdapter(adapter)

                // Inisialisasi tombol daftar dan login jika diperlukan
                binding.btnRegisSubmit.setOnClickListener {
                    // Logika saat tombol Daftar Sekarang diklik
                }
                binding.btnRegisLogin.setOnClickListener {
                    // Logika saat tombol Sudah Punya Akun diklik
                }
            }
        }
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}