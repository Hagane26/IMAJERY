package com.example.imajery_v4.ui.materi.kuisoner

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imajery_v4.R
import com.example.imajery_v4.models.Jawaban
import com.example.imajery_v4.models.JawabanReq
import com.example.imajery_v4.models.JawabanRes
import com.example.imajery_v4.models.ListPertanyaan
import com.example.imajery_v4.models.ListPertanyaanPost
import com.example.imajery_v4.supports.APIService
import com.example.imajery_v4.supports.retrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Kuisoner : AppCompatActivity() {

    private lateinit var rv : RecyclerView
    private lateinit var adapter : KuisonerAdapter
    private var jawabanList: List<Jawaban> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_kuisoner)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val apis = retrofitClient.instance.create(APIService::class.java)
        val mID = intent.getIntExtra("mID",0)
        val kID = intent.getIntExtra("kID",0)
        val uID = intent.getIntExtra("uID",0)
        val postid = ListPertanyaanPost(mID)

        val btn_kirim : Button = findViewById(R.id.btn_kuisoner_kirim)

        rv = findViewById(R.id.rv_pertanyaan)
        rv.layoutManager = LinearLayoutManager(this)

        apis.getPertanyaan(postid).enqueue(object : Callback<List<ListPertanyaan>> {
            override fun onResponse(
                call: Call<List<ListPertanyaan>>,
                response: Response<List<ListPertanyaan>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { pertanyaanList ->
                        adapter = KuisonerAdapter(pertanyaanList,mID){ jawaban ->
                            jawabanList = jawaban   // --> identifikasi jawaban
                        }
                        rv.adapter = adapter
                    }
                }else{
                    Toast.makeText(this@Kuisoner,"Gagal mengambil data \n ${response.code()} \n ${response.raw()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<ListPertanyaan>>, t: Throwable) {
                Toast.makeText(this@Kuisoner,"Error \n ${t.message.toString()}", Toast.LENGTH_LONG).show()
            }

        })

        btn_kirim.setOnClickListener {
            if(jawabanList.isNotEmpty()){

                val jawab = jawabanList.joinToString(separator = ","){jawaban ->
                    "{'id_kuisoner': ${kID}, 'id_pertanyaan': ${jawaban.idp}, 'value': ${jawaban.value}}"
                }

                val data = JawabanReq(
                    DataJawaban = jawab
                )

                apis.kirimJawaban(data).enqueue(object : Callback<JawabanRes> {
                    override fun onResponse(
                        call: Call<JawabanRes>,
                        response: Response<JawabanRes>
                    ) {
                        if(response.isSuccessful){
                            response.body()?.let {
                                if(it.status == "1"){
                                    Toast.makeText(this@Kuisoner,"Jawaban berhasil dikirim \n ${it.status + " : " + it.message}", Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(this@Kuisoner,"Gagal Mengirim Jawaban", Toast.LENGTH_LONG).show()
                                }
                            }
                        }else{
                            Toast.makeText(this@Kuisoner,"Gagal Mengirim Jawaban 2", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<JawabanRes>, t: Throwable) {
                        Toast.makeText(this@Kuisoner,"Error :\n ${t.message.toString()}", Toast.LENGTH_LONG).show()
                    }

                })
                //Toast.makeText(this, jawabanList.toString(), Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Belum ada jawaban yang dipilih.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun back(){

    }
}