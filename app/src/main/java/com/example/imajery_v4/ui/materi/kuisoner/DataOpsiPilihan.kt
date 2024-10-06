package com.example.imajery_v4.ui.materi.kuisoner

object DataOpsiPilihan {
    val opsi : List<String> = listOf(
        "STS",
        "TS",
        "N",
        "S",
        "SS",
    )

    fun getOpsi(opsiID: Int): List<String> {
        return opsi
    }
}