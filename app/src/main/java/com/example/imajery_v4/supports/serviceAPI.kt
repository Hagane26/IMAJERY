package com.example.imajery_v4.supports

import com.example.imajery_v4.models.LoginReq
import com.example.imajery_v4.models.LoginRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface serviceAPI  {
    @POST
    fun login(@Body loginReq: LoginReq): Call<LoginRes>

}