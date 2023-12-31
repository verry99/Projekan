package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.data.remote.dto.volunteer.VolunteerResponseItem
import com.test.test.databinding.ItemVolunteerSupporterBinding
import com.test.test.presentation.dashboard.volunteer.VolunteerFragmentDirections

class VolunteerAdapter :
    PagingDataAdapter<VolunteerResponseItem, VolunteerAdapter.ItemViewHolder>(DIFF_CALLBACK) {

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

        fun bind(volunteer: VolunteerResponseItem) {
            binding.apply {
                tvNama.text = volunteer.name
                volunteer.profile?.let {
                    tvLocation.text =
                        "${volunteer.profile.village?.uppercase()}, ${volunteer.profile.subdistrict?.uppercase()}, ${volunteer.profile.regency?.uppercase()}"

                    it.photo?.let {
                        Glide.with(binding.root).load(IMAGE_URL + "/" + volunteer.profile.photo)
                            .into(binding.imgProfile)
                    }
                }
                tvSupporterNumber.text = volunteer.supporterCount.toString()
                tvNumberDesc.text = "Dukungan"
            }
            setUpActionListener(volunteer)
        }

        private fun setUpActionListener(volunteer: VolunteerResponseItem) {
            itemView.setOnClickListener {
                val action =
                    VolunteerFragmentDirections.actionVolunteerFragmentToDetailVolunteerFragment(
                        volunteer.id.toString()
                    )

                itemView.findNavController().navigate(action)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VolunteerResponseItem>() {
            override fun areItemsTheSame(
                oldItem: VolunteerResponseItem,
                newItem: VolunteerResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: VolunteerResponseItem,
                newItem: VolunteerResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}