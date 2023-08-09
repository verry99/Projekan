package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.common.Constants
import com.test.test.data.remote.dto.volunteer.detail_volunteer.Supporter
import com.test.test.databinding.ItemVolunteerSupporterBinding

class SupporterAdapter(private val data: List<Supporter?>) :
    RecyclerView.Adapter<SupporterAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemVolunteerSupporterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        val data = data[position]
        viewHolder.bind(data!!)
    }

    inner class ItemViewHolder(private val binding: ItemVolunteerSupporterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(supporter: Supporter) {
            binding.apply {
                tvNama.text = supporter.name
                tvLocation.text =
                    "${supporter.village}, ${supporter.subdistrict}, ${supporter.regency}"

                supporter.photo?.let {
                    Glide.with(binding.root).load(Constants.IMAGE_URL + supporter.photo)
                        .into(binding.imgVolunteer)

                }

                tvSupporterNumber.text = supporter.tps.toString()
                tvNumberDesc.text = "tps"
            }
            setUpActionListener(supporter)
        }

        private fun setUpActionListener(supporter: Supporter) {
            itemView.setOnClickListener {
//                val action =
//                    VolunteerFragmentDirections.actionVolunteerFragmentToDetailVolunteerFragment(
//                        supporter.id.toString()
//                    )
//
//                itemView.findNavController().navigate(action)
            }
        }
    }
}