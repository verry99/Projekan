package com.test.test.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.R
import com.test.test.common.Constants
import com.test.test.data.remote.dto.real_counts.RealCountResponseItem
import com.test.test.databinding.ItemVolunteerSupporterBinding
import com.test.test.presentation.dashboard.real_count.RealCountFragmentDirections
import com.test.test.presentation.utils.formatNumber

class RealCountAdapter() :
    PagingDataAdapter<RealCountResponseItem, RealCountAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemVolunteerSupporterBinding.inflate(
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

    inner class ItemViewHolder(private val binding: ItemVolunteerSupporterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(realCount: RealCountResponseItem) {
            binding.apply {
                tvNama.text = "TPS " + realCount.tps
                imgProfile.setImageResource(R.drawable.ic_tps)

                tvLocation.text =
                    "${realCount.village?.uppercase()}, ${realCount.subdistrict?.uppercase()}"

                tvSupporterNumber.text = formatNumber(realCount.count!!.toLong())
                tvNumberDesc.text = "Suara"

                realCount.image?.let {
                    Glide.with(binding.root).load(Constants.IMAGE_URL + "/" + it)
                        .into(binding.imgProfile)
                }
            }
            setUpActionListener(realCount)
        }

        private fun setUpActionListener(realCount: RealCountResponseItem) {
            itemView.setOnClickListener {
                val action =
                    RealCountFragmentDirections.actionRealCountFragmentToDetailRealCountFragment(
                        realCount.id.toString()
                    )

                itemView.findNavController().navigate(action)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RealCountResponseItem>() {
            override fun areItemsTheSame(
                oldItem: RealCountResponseItem,
                newItem: RealCountResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RealCountResponseItem,
                newItem: RealCountResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}