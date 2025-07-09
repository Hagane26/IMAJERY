package com.example.imajery_v4.ui.materi.detail

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.imajery_v4.R
import com.example.imajery_v4.models.AudioRes
import com.example.imajery_v4.ui.materi.MediaAudio.AudioPlay

class AudioAdapter (
    private val data : List<AudioRes>
):RecyclerView.Adapter<AudioAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val audio_judul : TextView = itemView.findViewById(R.id.tv_item_audio_judul)
        val card_audio : View = itemView.findViewById(R.id.card_item_audio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_materi_audio, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.audio_judul.text = item.judul
        holder.card_audio.setOnClickListener {
            val intent = Intent(holder.itemView.context, AudioPlay::class.java)
                .apply {
                    putExtra("judul", item.judul)
                    putExtra("url", item.url)
                }
            holder.itemView.context.startActivity(intent)
        }
    }
}