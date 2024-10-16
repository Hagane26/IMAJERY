package com.example.imajery_v4.ui.materi

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.imajery_v4.R
import com.example.imajery_v4.models.ListMateri
import com.example.imajery_v4.ui.materi.detail.Materi_Detail
import com.google.android.material.card.MaterialCardView


class MateriAdapter(
    private val data : List<ListMateri>,
) : RecyclerView.Adapter<MateriAdapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card : MaterialCardView = itemView.findViewById(R.id.card_item_materi)
        val tv_judul : TextView = itemView.findViewById(R.id.tv_item_judul)
        val tv_desc : TextView = itemView.findViewById(R.id.tv_item_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_materi, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.tv_judul.text = item.judul
        holder.tv_desc.text = item.deskripsi

        holder.card.setOnClickListener {
            val intent = Intent(holder.itemView.context, Materi_Detail::class.java)
            intent.putExtra("mid",item.id)
            intent.putExtra("judul", item.judul)
            intent.putExtra("desc", item.deskripsi)

            //Toast.makeText(holder.itemView.context,"id => ${item.id}",Toast.LENGTH_SHORT).show()
            holder.itemView.context.startActivity(intent)
        }
    }
}