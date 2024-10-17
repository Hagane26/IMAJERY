package com.example.imajery_v4.ui.dashboard

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.imajery_v4.R
import com.example.imajery_v4.databinding.FragmentDashboardBinding
import com.example.imajery_v4.ui.about.imagery

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private var mediaPlayer: MediaPlayer? = null
    private var currentAudioIndex = 0
    private val audioList = listOf(
        "https://palevioletred-dragonfly-972749.hostingersite.com/public/Audio/intro/1/1.mp3",
        "https://palevioletred-dragonfly-972749.hostingersite.com/public/Audio/intro/1/2.mp3",
        "https://palevioletred-dragonfly-972749.hostingersite.com/public/Audio/intro/1/3.mp3"
    )


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val sharedRef = requireActivity().getSharedPreferences("Data-IMAJERY", MODE_PRIVATE)
        val refUserID = sharedRef.getInt("userID",0)
        val refUserName = sharedRef.getString("username","def usenname")

        val tvd: TextView = view.findViewById(R.id.text_dashboard)
        val btn_logout : Button = view.findViewById(R.id.btn_dashboard_logout)
        val btn_about : Button = view.findViewById(R.id.btn_dashboard_about)

        tvd.text = "Selamat Datang $refUserName di Aplikasi IMAGERY"

        btn_logout.setOnClickListener{
            val srEdit = sharedRef.edit()
            srEdit.putInt("login_status",0)
            srEdit.putInt("splash_status",0)
            srEdit.putInt("userID",0)
            srEdit.apply()
            requireActivity().finish()
            requireActivity().startActivity(requireActivity().intent)
        }

        btn_about.setOnClickListener{
            val intent = Intent(requireActivity(), imagery::class.java)
            startActivity(intent)
        }

        playAudioFromUrl(currentAudioIndex)

        return view
    }

    private fun playAudioFromUrl(index: Int) {
        try {
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

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        _binding = null
    }
}