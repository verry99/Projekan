package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.test.data.remote.dto.real_counts.detail.Rival
import com.test.test.databinding.ItemEdittextPairLayoutDisabledBinding

class RivalAdapter(private val data: List<Rival>) :
    RecyclerView.Adapter<RivalAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemEdittextPairLayoutDisabledBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val rival = data[position]
        holder.bind(rival)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ItemViewHolder(private val binding: ItemEdittextPairLayoutDisabledBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rival: Rival) {
            binding.apply {
                tvNama.text = rival.name
                tvSuara.text = rival.suara.toString()
            }
        }
    }
}