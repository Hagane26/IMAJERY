package com.example.imajery_v4.ui.materi

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imajery_v4.R
import com.example.imajery_v4.models.ListMateri
import com.example.imajery_v4.supports.APIService
import com.example.imajery_v4.supports.retrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MateriFragment : Fragment() {

    private lateinit var rv : RecyclerView
    private lateinit var adapter : MateriAdapter

    companion object {
        fun newInstance() = MateriFragment()
    }

    private val viewModel: MateriViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_materi, container, false)

        rv = view.findViewById(R.id.rv_item_materi)
        rv.layoutManager = LinearLayoutManager(requireContext())

        val apis = retrofitClient.instance.create(APIService::class.java)
        apis.getMateri().enqueue(object : Callback<List<ListMateri>> {
            override fun onResponse(
                call: Call<List<ListMateri>>,
                response: Response<List<ListMateri>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        adapter = MateriAdapter(data)
                        rv.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<List<ListMateri>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        return view
    }
}