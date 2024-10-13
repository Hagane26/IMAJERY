package com.example.imajery_v4.ui.materi.kuisoner

import android.content.Intent
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
import com.example.imajery_v4.MainActivity
import com.example.imajery_v4.R
import com.example.imajery_v4.models.KuisonerHasilReq
import com.example.imajery_v4.models.KuisonerHasilRes
import com.example.imajery_v4.models.KuisonerPenilaian
import com.example.imajery_v4.supports.APIService
import com.example.imajery_v4.supports.retrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KuisonerHasil : AppCompatActivity() {

    private lateinit var rv : RecyclerView
    private lateinit var adapter : KuisonerHasilAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kuisoner_hasil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val apis = retrofitClient.instance.create(APIService::class.java)
        val kID = intent.getIntExtra("kID",0)

        val tv_value : TextView = findViewById(R.id.tv_kuisoner_hasil_value)
        val tv_kat : TextView = findViewById(R.id.tv_kuisoner_hasil_kat)
        val btn_back : Button = findViewById(R.id.btn_kuisoner_hasil_kembali)

        rv = findViewById(R.id.rv_kuisoner_hasil_list)
        rv.layoutManager = LinearLayoutManager(this)

        val postid = KuisonerHasilReq(kID)

        apis.kuisonerPenilaian(postid).enqueue(object : Callback<KuisonerPenilaian> {
            override fun onResponse(
                call: Call<KuisonerPenilaian>,
                response: Response<KuisonerPenilaian>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if(it.status == "1"){
                            tv_value.text = it.nilai
                        }
                    }
                }
            }

            override fun onFailure(call: Call<KuisonerPenilaian>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        apis.kuisonerHasil(postid).enqueue(object : Callback<List<KuisonerHasilRes>> {
            override fun onResponse(
                call: Call<List<KuisonerHasilRes>>,
                response: Response<List<KuisonerHasilRes>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { hasilKuisoner ->
                        adapter = KuisonerHasilAdapter(hasilKuisoner)
                        rv.adapter = adapter
                    }
                }else{
                    //Toast.makeText(this@KuisonerHasil,"No Data", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<KuisonerHasilRes>>, t: Throwable) {
                //Toast.makeText(this@KuisonerHasil,"Error : ${t.message.toString()}}", Toast.LENGTH_LONG).show()
            }
        })

        btn_back.setOnClickListener {
            startActivity(Intent(this@KuisonerHasil, MainActivity::class.java))
        }
    }
}