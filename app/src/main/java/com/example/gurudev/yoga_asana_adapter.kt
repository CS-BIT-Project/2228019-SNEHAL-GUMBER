package com.example.gurudev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class YogaAsanaAdapter(private val asanas: List<String>) :
    RecyclerView.Adapter<YogaAsanaAdapter.YogaViewHolder>() {

    class YogaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val asanaText: TextView = itemView.findViewById(R.id.asanaName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YogaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_yoga_asana_adapter, parent, false) // Ensure you have item_yoga_asana.xml
        return YogaViewHolder(view)
    }

    override fun onBindViewHolder(holder: YogaViewHolder, position: Int) {
        holder.asanaText.text = asanas[position]
    }

    override fun getItemCount(): Int = asanas.size
}
