package com.example.imajery_v4.models

data class JawabanReq(
    val mid : Int = 0,
    val tipe : String,
    val DataJawaban : List<Jawaban>
)
