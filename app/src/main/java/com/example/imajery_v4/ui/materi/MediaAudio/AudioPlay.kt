package com.example.imajery_v4.ui.materi.MediaAudio

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imajery_v4.R


class AudioPlay : AppCompatActivity() {

    lateinit var tv_audio_judul : TextView
    lateinit var tv_audio_current : TextView
    lateinit var tv_audio_duration : TextView
    lateinit var ib_audio : ImageButton
    lateinit var sb_audio : SeekBar
    lateinit var mediaplayer : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audio_play)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tv_audio_judul = findViewById(R.id.tv_audio_judul)
        tv_audio_current = findViewById(R.id.tv_audio_curent)
        tv_audio_duration = findViewById(R.id.tv_audio_duration)
        ib_audio = findViewById(R.id.ib_audio)
        sb_audio = findViewById(R.id.sb_audio)

        mediaplayer = MediaPlayer()

        val judul = intent.getStringExtra("judul")
        val url = intent.getStringExtra("url")
        var mins = 0
        var secs = 0

        tv_audio_judul.text = judul

        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            mediaplayer.setDataSource(url)
            mediaplayer.prepare()
            mediaplayer.start()
            mins = mediaplayer.duration / 60000
            secs = (mediaplayer.duration % 60000) / 1000
            sb_audio.max = mediaplayer.duration
            tv_audio_duration.text = "${mins.toString() + ":" + secs.toString()}"

            mediaplayer.setOnBufferingUpdateListener { mp, percent ->
                val cmin = mp.currentPosition / 60000
                val csec = (mp.currentPosition % 60000) / 1000
                sb_audio.progress = mp.currentPosition
                tv_audio_current.text = "${cmin.toString()} : ${csec.toString()}"
            }

        }catch (e : Exception){
            Toast.makeText(this@AudioPlay,"Gagal Memuat Audio.",Toast.LENGTH_LONG).show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaplayer.stop()
        mediaplayer.release()
        finish()
    }
}