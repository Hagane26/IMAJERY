package com.example.imajery_v4.ui.materi.kuisoner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.recyclerview.widget.RecyclerView
import com.example.imajery_v4.R
import com.example.imajery_v4.models.Jawaban
import com.example.imajery_v4.models.ListPertanyaan
import com.example.imajery_v4.models.ListPertanyaanPost

class KuisonerAdapter(
    private val data : List<ListPertanyaan>,
    private val mid : Int,
    private val onAnswer : (List<Jawaban>) -> Unit // --> Callback saat jawaban dipilih
) : RecyclerView.Adapter<KuisonerAdapter.ViewHolder>() {

    private val jawab: MutableList<Jawaban> = mutableListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_pertanyaan : TextView = itemView.findViewById(R.id.tv_item_pertanyaan)
        val rg_1 : RadioGroup = itemView.findViewById(R.id.rg_pertanyaan_1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_pertanyaan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.tv_pertanyaan.text = item.pertanyaan
        holder.rg_1.removeAllViews()

        if(jawab.size < data.size){
            jawab.addAll(List(data.size - jawab.size){Jawaban(it,0,0)})
        }

        val opsi = DataOpsiPilihan.getOpsi(item.id)

        opsi.forEachIndexed { index, x_opsi ->
            val rb = RadioButton(holder.itemView.context)
            rb.text = x_opsi
            rb.id = index
            holder.rg_1.addView(rb)

            rb.setOnClickListener {

                for (i in 0 until holder.rg_1.childCount) {
                    val child = holder.rg_1.getChildAt(i) as RadioButton
                    if(child != null && child.id == index){
                        child.isChecked = true
                    }else{
                        child.isChecked = false
                    }
                }

                jawab[position] = Jawaban(mid,position,index)
                Toast.makeText(holder.itemView.context, "jawaban : ${jawab[position].value}", Toast.LENGTH_SHORT).show()
                onAnswer(jawab) // --> Callback saat jawaban dipilih
            }
        }
    }


}