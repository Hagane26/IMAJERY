package com.example.imajery_v4.supports

import com.example.imajery_v4.models.ListMateri
import com.example.imajery_v4.models.ListPertanyaan
import com.example.imajery_v4.models.ListPertanyaanPost
import com.example.imajery_v4.models.LoginReq
import com.example.imajery_v4.models.LoginRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    @POST("/login")
    fun login(@Body loginReq: LoginReq): Call<LoginRes>

    @GET("/materi")
    fun getMateri(): Call<List<ListMateri>>

    @POST("/materiPertanyaan")
    fun getPertanyaan(@Body id: ListPertanyaanPost): Call<List<ListPertanyaan>>
}