package com.example.imajery_v4.ui.materi.kuisoner

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.imajery_v4.R
import com.example.imajery_v4.models.KuisonerHasilRes
import com.example.imajery_v4.ui.materi.detail.Materi_Detail
import com.example.imajery_v4.ui.materi.kuisoner.KuisonerAdapter.ViewHolder

class KuisonerHasilAdapter(
    private val data : List<KuisonerHasilRes>,
): RecyclerView.Adapter<KuisonerHasilAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_pertanyaan : TextView = itemView.findViewById(R.id.tv_item_jawaban_pertanyaan)
        val tv_value : TextView = itemView.findViewById(R.id.tv_item_jawaban_value)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_jawaban, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.tv_pertanyaan.text = item.pertanyaan
        var jawabanref = ""
        when(item.jawaban){
            "1"-> {jawabanref = "Sangat Tidak Setuju"}
            "2"-> {jawabanref = "Tidak Setuju"}
            "3"-> {jawabanref = "Netral"}
            "4"-> {jawabanref = "Setuju"}
            "5"-> {jawabanref = "Sangat Setuju"}
        }
        holder.tv_value.text = "Jawaban dipilih : ${jawabanref}"

    }

}