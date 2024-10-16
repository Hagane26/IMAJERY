package com.example.imajery_v4.supports

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object retrofitClient {

    private const val BASE_URL = "https://palevioletred-dragonfly-972749.hostingersite.com/"

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val instance : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}