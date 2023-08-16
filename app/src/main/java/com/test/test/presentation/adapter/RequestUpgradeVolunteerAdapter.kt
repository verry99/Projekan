package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.data.remote.dto.volunteer.request_upgrade.RequestUpgradeVolunteerResponseItem
import com.test.test.databinding.ItemSupporterBinding
import com.test.test.presentation.dashboard.volunteer.VolunteerFragmentDirections

class RequestUpgradeVolunteerAdapter() :
    PagingDataAdapter<RequestUpgradeVolunteerResponseItem, RequestUpgradeVolunteerAdapter.ItemViewHolder>(
        DIFF_CALLBACK
    ) {

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

        fun bind(volunteer: RequestUpgradeVolunteerResponseItem) {
            binding.apply {
                volunteer.profile?.let {
                    tvNama.text = it.name
                    tvLocation.text =
                        "${it.village?.uppercase()}, ${it.subdistrict?.uppercase()}, ${it.regency?.uppercase()}"

                    it.photo?.let {
                        Glide.with(binding.root).load(IMAGE_URL + volunteer.profile.photo)
                            .into(binding.imgProfile)
                    }
                }
                tvAddedBy.text = "Menunggu persetujuan"
            }

            setUpActionListener(volunteer)
        }

        private fun setUpActionListener(volunteer: RequestUpgradeVolunteerResponseItem) {
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RequestUpgradeVolunteerResponseItem>() {
            override fun areItemsTheSame(
                oldItem: RequestUpgradeVolunteerResponseItem,
                newItem: RequestUpgradeVolunteerResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RequestUpgradeVolunteerResponseItem,
                newItem: RequestUpgradeVolunteerResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}