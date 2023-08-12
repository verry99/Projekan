package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.data.remote.dto.supporter.SupporterResponseItem
import com.test.test.databinding.ItemSupporterBinding
import com.test.test.presentation.dashboard.supporter.SupporterFragmentDirections

class SupporterAdapter :
    PagingDataAdapter<SupporterResponseItem, SupporterAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemSupporterBinding.inflate(
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

    inner class ItemViewHolder(private val binding: ItemSupporterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(supporter: SupporterResponseItem) {

            binding.apply {
                supporter.let {
                    tvNama.text = supporter.name
                    tvLocation.text =
                        "${it.village?.uppercase()}, ${it.subdistrict?.uppercase()}, ${it.regency?.uppercase()}"

                    it.photo?.let { photo ->
                        Glide.with(binding.root).load(IMAGE_URL + photo)
                            .into(binding.imgProfile)
                    }
                    tvAddedBy.text = "Ditambahkan oleh ${it.addedBy}"
                }
            }
            setUpActionListener(supporter)
        }

        private fun setUpActionListener(supporter: SupporterResponseItem) {
            itemView.setOnClickListener {
                val action =
                    SupporterFragmentDirections.actionSupporterFragmentToDetailSupporterFragment(
                        supporter.id.toString()
                    )

                itemView.findNavController().navigate(action)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SupporterResponseItem>() {
            override fun areItemsTheSame(
                oldItem: SupporterResponseItem,
                newItem: SupporterResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SupporterResponseItem,
                newItem: SupporterResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}