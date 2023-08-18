package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.data.remote.dto.analysis.get_item_by_area.AreaSupporter
import com.test.test.databinding.ItemVolunteerSupporterBinding

class AnalysisAreaSupporterAdapter(private val data: List<AreaSupporter>) :
    RecyclerView.Adapter<AnalysisAreaSupporterAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemVolunteerSupporterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val supporter = data[position]
        holder.bind(supporter)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ItemViewHolder(private val binding: ItemVolunteerSupporterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(supporter: AreaSupporter) {
            supporter.let {
                it.photo?.let { photo ->
                    Glide.with(binding.root)
                        .load("$IMAGE_URL/$photo")
                        .into(binding.imgProfile)
                }

                binding.apply {
                    tvNama.text = it.name
                    tvNumberDesc.text = ""
                    tvLocation.text =
                        "${it.village?.uppercase()}, ${it.subdistrict?.uppercase()}, ${it.regency?.uppercase()}"
                }
            }
//            setUpActionListener(supporter)
        }

//        private fun setUpActionListener(supporter: Post) {
//            itemView.setOnClickListener {
//                val action =
//                    DashboardFragmentDirections.actionDashboardFragmentToDetailPostDashboardFragment(
//                        supporter.slug
//                    )
//
//                itemView.findNavController().navigate(action)
//            }
//        }
    }
}