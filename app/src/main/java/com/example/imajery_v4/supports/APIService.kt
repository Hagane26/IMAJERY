package com.example.imajery_v4.supports

import com.example.imajery_v4.models.Jawaban
import com.example.imajery_v4.models.JawabanReq
import com.example.imajery_v4.models.JawabanRes
import com.example.imajery_v4.models.KuisonerReq
import com.example.imajery_v4.models.KuisonerRes
import com.example.imajery_v4.models.ListMateri
import com.example.imajery_v4.models.ListPertanyaan
import com.example.imajery_v4.models.ListPertanyaanPost
import com.example.imajery_v4.models.LoginReq
import com.example.imajery_v4.models.LoginRes
import com.example.imajery_v4.models.KuisonerHasilReq
import com.example.imajery_v4.models.KuisonerHasilRes
import com.example.imajery_v4.models.KuisonerPenilaian
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    @POST("/auth_login")
    fun login(@Body loginReq: LoginReq): Call<LoginRes>

    @GET("/materi")
    fun getMateri(): Call<List<ListMateri>>

    @POST("/kuisoner")
    fun getPertanyaan(@Body id: ListPertanyaanPost): Call<List<ListPertanyaan>>

    @POST("/kuisoner_buat")
    fun buatKuisoner(@Body kuisoner: KuisonerReq): Call<KuisonerRes>

    @POST("/kirim_jawaban")
    fun kirimJawaban(@Body jawaban: JawabanReq): Call<JawabanRes>

    @POST("/kuisoner_hasil")
    fun kuisonerHasil(@Body id: KuisonerHasilReq): Call<List<KuisonerHasilRes>>

    @POST("/penilaian_hasil")
    fun kuisonerPenilaian(@Body id: KuisonerHasilReq): Call<KuisonerPenilaian>

}