package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.data.remote.dto.interaction.InteractionResponseItem
import com.test.test.databinding.ItemInteractionBinding
import com.test.test.presentation.dashboard.interaction.InteractionFragmentDirections
import com.test.test.presentation.utils.convertToHumanReadableDate

class InteractionAdapter(private val token: String) :
    PagingDataAdapter<InteractionResponseItem, InteractionAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemInteractionBinding.inflate(
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

    inner class ItemViewHolder(private val binding: ItemInteractionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(interaction: InteractionResponseItem) {

            binding.apply {
                interaction.let {
                    tvTitle.text = it.title
                    tvFullName.text = it.user?.name

                    try {
                        tvDate.text = convertToHumanReadableDate(it.createdAt!!)
                    } catch (e: Exception) {
                        tvDate.text = it.createdAt!!
                    }

                    tvDescription.text = it.description
                    Glide.with(itemView).load(IMAGE_URL + "/" + it.image).into(imgInteraction)
                }
            }
            setUpActionListener(interaction)
        }

        private fun setUpActionListener(interaction: InteractionResponseItem) {
            itemView.setOnClickListener {
                val action =
                    InteractionFragmentDirections.actionInteractionFragmentToDetailInteractionFragment(
                        interaction.id.toString(),
                        token
                    )

                itemView.findNavController().navigate(action)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<InteractionResponseItem>() {
            override fun areItemsTheSame(
                oldItem: InteractionResponseItem,
                newItem: InteractionResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: InteractionResponseItem,
                newItem: InteractionResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}