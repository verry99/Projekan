package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.databinding.ItemGalleryBinding

class GalleryAdapter(private val imgUrls: List<String>) :
    RecyclerView.Adapter<GalleryAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemGalleryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val news = imgUrls[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int {
        return imgUrls.size
    }

    inner class ItemViewHolder(private val binding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imgUrl: String) {
            Glide.with(binding.root)
                .load(imgUrl)
                .into(binding.img)
        }
    }
}