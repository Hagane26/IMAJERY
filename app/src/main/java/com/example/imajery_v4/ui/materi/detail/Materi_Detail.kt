package com.example.imajery_v4.ui.materi.detail

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imajery_v4.R
import com.example.imajery_v4.models.AudioReq
import com.example.imajery_v4.models.AudioRes
import com.example.imajery_v4.models.KuisonerReq
import com.example.imajery_v4.models.KuisonerRes
import com.example.imajery_v4.supports.APIService
import com.example.imajery_v4.supports.retrofitClient
import com.example.imajery_v4.ui.materi.MateriAdapter
import com.example.imajery_v4.ui.materi.kuisoner.Kuisoner
import retrofit2.Call
import retrofit2.Response

class Materi_Detail : AppCompatActivity() {

    private lateinit var rv_audio : RecyclerView
    private lateinit var adapter : AudioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_materi_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedRef = getSharedPreferences("Data-IMAJERY", MODE_PRIVATE)
        val refUserID = sharedRef.getInt("userID",0)

        val apis = retrofitClient.instance.create(APIService::class.java)
        val m_id = intent.getIntExtra("m_id",0)
        val judul = intent.getStringExtra("judul")
        val desc = intent.getStringExtra("desc")

        val tv_detail_judul : TextView = findViewById(R.id.tv_detail_judul)
        val tv_detail_desc : TextView = findViewById(R.id.tv_detail_desc)
        val btn_pretest : Button = findViewById(R.id.btn_pretest)

        rv_audio  = findViewById(R.id.rv_materi_audio)
        rv_audio.layoutManager = LinearLayoutManager(this)

        tv_detail_judul.text = judul
        tv_detail_desc.text = desc

        val audio_data = AudioReq(m_id)

        apis.getAudio(audio_data).enqueue(object : retrofit2.Callback<List<AudioRes>> {
            override fun onResponse(call: Call<List<AudioRes>>, response: Response<List<AudioRes>>) {
                if(response.isSuccessful){
                    val data = response.body()
                    if (data != null) {
                        adapter = AudioAdapter(data)
                        rv_audio.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<List<AudioRes>>, t: Throwable) {
                Toast.makeText(this@Materi_Detail,"Error : \n ${t.message.toString()}", Toast.LENGTH_LONG).show()
            }

        })

        btn_pretest.setOnClickListener {

            val data = KuisonerReq(
                id_user = refUserID,
                id_materi = m_id
            )
            apis.buatKuisoner(data).enqueue(object : retrofit2.Callback<KuisonerRes> {
                override fun onResponse(call: Call<KuisonerRes>, response: Response<KuisonerRes>) {
                    if(response.isSuccessful){
                        response.body()?.let {
                            if(it.status == "1"){
                                gas(m_id,it.id_kuisoner, refUserID, this@Materi_Detail)
                            }else{
                                Toast.makeText(this@Materi_Detail,"Anda Tidak Bisa Menuju ke Kuisoner!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }else{
                        Toast.makeText(this@Materi_Detail,"No Response \n ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<KuisonerRes>, t: Throwable) {
                    Toast.makeText(this@Materi_Detail,"Error :\n ${t.message.toString()}", Toast.LENGTH_LONG).show()
                }

            })

        }
    }

    private fun gas(mID : Int, kID :Int, uID : Int,ctx : Context){
        val intent = Intent(ctx, Kuisoner::class.java).apply {
            putExtra("mID", mID)
            putExtra("kID", kID)
            putExtra("uID", uID)
        }
        startActivity(intent)
    }

}