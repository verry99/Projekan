package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.data.remote.dto.gallery.GalleryResponseItem
import com.test.test.databinding.ItemGalleryBinding
import com.test.test.presentation.dashboard.real_count.detail.PopUpProofImageFragment

class GalleryAdapter(private val fragmentManager: FragmentManager) :
    PagingDataAdapter<GalleryResponseItem, GalleryAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemGalleryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            viewHolder.bind(data)
        }
    }

    inner class ItemViewHolder(private val binding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gallery: GalleryResponseItem) {
            binding.apply {
                Glide
                    .with(itemView)
                    .load(gallery.image)
                    .into(img)

                gallery.description?.let {

                }
            }

            setUpActionListener(gallery)
        }

        private fun setUpActionListener(gallery: GalleryResponseItem) {
            itemView.setOnClickListener {
                val url = gallery.image ?: ""
                val popupFragment =
                    PopUpProofImageFragment.newInstance(url, gallery.description)
                popupFragment.show(fragmentManager, "my_popup")
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GalleryResponseItem>() {
            override fun areItemsTheSame(
                oldItem: GalleryResponseItem,
                newItem: GalleryResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GalleryResponseItem,
                newItem: GalleryResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}