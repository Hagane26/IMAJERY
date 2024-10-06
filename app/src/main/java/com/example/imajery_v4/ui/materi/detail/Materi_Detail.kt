package com.example.imajery_v4.ui.materi.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imajery_v4.R
import com.example.imajery_v4.ui.materi.kuisoner.Kuisoner

class Materi_Detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_materi_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val m_id = intent.getIntExtra("m_id",0)
        val judul = intent.getStringExtra("judul")
        val desc = intent.getStringExtra("desc")

        val tv_detail_judul : TextView = findViewById(R.id.tv_detail_judul)
        val tv_detail_desc : TextView = findViewById(R.id.tv_detail_desc)
        val btn_pretest : Button = findViewById(R.id.btn_pretest)

        tv_detail_judul.text = judul
        tv_detail_desc.text = desc

        btn_pretest.setOnClickListener {
            val intent = Intent(this, Kuisoner::class.java).apply {
                putExtra("m_id", m_id)
            }
            startActivity(intent)
        }
    }

}