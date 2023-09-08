package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.test.data.remote.dto.volunteer.detail_volunteer.Area
import com.test.test.databinding.ItemTableSupporterBinding
import com.test.test.presentation.utils.formatNumber
import java.util.Locale

class AreaAdapter(private val data: List<Area?>?) :
    RecyclerView.Adapter<AreaAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemTableSupporterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val area = data?.get(position)
        holder.bind(area!!)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    inner class ItemViewHolder(private val binding: ItemTableSupporterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(area: Area) {
            binding.apply {
                tvRegion.text =
                    area.name?: "".lowercase().split(" ").joinToString(" ") { it.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    } }
                tvSupporterNumberMale.text =
                    formatNumber((area.gender!!.l?.toString() ?: "0").toLong())
                tvSupporterNumberFemale.text =
                    formatNumber((area.gender.p?.toString() ?: "0").toLong())
                tvSupporterNumberTotal.text = formatNumber(area.total!!.toLong())
            }
        }
    }
}