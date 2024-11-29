package com.example.imajery_v4.ui.about

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imajery_v4.R

class imagery : AppCompatActivity() {
    lateinit var tv_title : TextView
    private var mediaPlayer: MediaPlayer? = null
    private var currentAudioIndex = 0
    private var server = ""
    private val audioList = listOf(
        "{$server}/public/Audio/intro/2/1.mp3",
        "{$server}/public/Audio/intro/2/2.mp3",
        "{$server}/public/Audio/intro/2/3.mp3",
        "{$server}/public/Audio/intro/2/4.mp3",
        "{$server}/public/Audio/intro/2/5.mp3"
    )
    private val audioLabels = listOf(
        "Pengenalan IMAGERY",
        "Waktu Melakukan Latihan IMAGERY",
        "Manfaat melakukan IMAGERY bagi atlet pelajar",
        "Mengapa atlet pelajar harus berlatih IMAGERY?",
        "Petunjuk latihan IMAGERY"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imagery)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mediaPlayer?.release()
        tv_title = findViewById(R.id.tv_about_label)
        playAudioFromUrl(currentAudioIndex)
    }

    private fun playAudioFromUrl(index: Int) {
        try {
            tv_title.text = audioLabels[index]
            // Hentikan audio sebelumnya
            mediaPlayer?.release()

            // Inisialisasi mediaPlayer
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(audioList[index]) // Set URL sebagai data source
            mediaPlayer?.prepareAsync() // Persiapkan secara asynchronous
            mediaPlayer?.setOnPreparedListener {
                it.start() // Memulai pemutaran saat siap
            }

            // Listener ketika audio selesai diputar
            mediaPlayer?.setOnCompletionListener {
                nextAudio() // Pindah ke audio berikutnya
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Fungsi untuk audio berikutnya
    private fun nextAudio() {
        currentAudioIndex++
        if (currentAudioIndex >= audioList.size) {
            currentAudioIndex = 0 // Kembali ke awal jika sudah sampai di akhir list
        }
        playAudioFromUrl(currentAudioIndex)
    }

    // Fungsi untuk audio sebelumnya
    private fun previousAudio() {
        currentAudioIndex = if (currentAudioIndex - 1 < 0) audioList.size - 1 else currentAudioIndex - 1
        playAudioFromUrl(currentAudioIndex)
    }

    private fun togglePlayPause() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }
}