package com.example.imajery_v4.models

data class RegisterReq(
    val username : String,
    val namaDepan: String,
    val namaBelakang: String,
    val tanggalLahir: String,
    val gender: String,
    val password: String,
    val img : String
)
