package com.test.test.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponseItem
import com.test.test.databinding.ItemDataByAreaBinding

class AnalysisAreaSupporterAdapter() :
    PagingDataAdapter<AnalysisDataByAreaResponseItem, AnalysisAreaSupporterAdapter.ItemViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemDataByAreaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        val data = getItem(position)
        Log.e("#anareadapter", "$data")
        if (data != null) {
            viewHolder.bind(data)
        }
    }

    inner class ItemViewHolder(private val binding: ItemDataByAreaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: AnalysisDataByAreaResponseItem) {
            binding.apply {
                tvNama.text = data.name
                data.let {
                    tvLocation.text =
                        "${data.village?.uppercase()}, ${data.subdistrict?.uppercase()}, ${data.regency?.uppercase()}"

                    it.photo?.let {
                        Glide.with(binding.root).load(IMAGE_URL + data.photo)
                            .into(binding.imgProfile)
                    }
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnalysisDataByAreaResponseItem>() {
            override fun areItemsTheSame(
                oldItem: AnalysisDataByAreaResponseItem,
                newItem: AnalysisDataByAreaResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: AnalysisDataByAreaResponseItem,
                newItem: AnalysisDataByAreaResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}