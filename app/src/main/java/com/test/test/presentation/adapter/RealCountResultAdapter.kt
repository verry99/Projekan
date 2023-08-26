package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.test.databinding.ItemRealCountResultBinding
import com.test.test.domain.models.Rival

class RealCountResultAdapter(private val data: List<Rival>) :
    RecyclerView.Adapter<RealCountResultAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemRealCountResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val news = data[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ItemViewHolder(private val binding: ItemRealCountResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Rival) {
            binding.tvNama.text = data.name
            binding.tvSupporterNumber.text = data.voice.toString()
        }

    }
}