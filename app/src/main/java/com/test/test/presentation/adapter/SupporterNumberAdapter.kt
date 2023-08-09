package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.test.databinding.ItemTableSupporterBinding
import com.test.test.domain.models.SupporterNumber

class SupporterNumberAdapter(private val data: List<SupporterNumber>) :
    RecyclerView.Adapter<SupporterNumberAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemTableSupporterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val totalSupporter = data[position]
        holder.bind(totalSupporter)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ItemViewHolder(private val binding: ItemTableSupporterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(supporterNumber: SupporterNumber) {
            binding.tvRegion.text = supporterNumber.region
            binding.tvSupporterNumberMale.text = supporterNumber.supporterNumberMale
            binding.tvSupporterNumberFemale.text = supporterNumber.supporterNumberFemale
            binding.tvSupporterNumberTotal.text = supporterNumber.supporterNumberTotal

//            // setUpActionListener(supporterNumber)
        }

//        private fun setUpActionListener(totalSupporter: SupporterNumber) {
//            itemView.setOnClickListener {
//                val action =
//                    HomeFragmentDirections.actionFragmentHomeTototalSupporterFragment(totalSupporter)
//
//                itemView.findNavController().navigate(action)
//            }
//        }
    }
}