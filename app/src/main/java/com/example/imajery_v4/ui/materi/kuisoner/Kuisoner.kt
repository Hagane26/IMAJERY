package com.example.imajery_v4.ui.materi.kuisoner

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imajery_v4.R
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
        val m_id = intent.getIntExtra("m_id",0)
        val postid = ListPertanyaanPost(1)

        rv = findViewById(R.id.rv_pertanyaan)
        rv.layoutManager = LinearLayoutManager(this)

        apis.getPertanyaan(postid).enqueue(object : Callback<List<ListPertanyaan>> {
            override fun onResponse(
                call: Call<List<ListPertanyaan>>,
                response: Response<List<ListPertanyaan>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        adapter = KuisonerAdapter(it)
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

    }
}