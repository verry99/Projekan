package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.test.data.remote.dto.interaction.detail_interaction.InteractionCommentResponseItem
import com.test.test.databinding.ItemInteractionCommentBinding
import com.test.test.presentation.utils.convertToHumanReadableDate

class InteractionCommentAdapter :
    PagingDataAdapter<InteractionCommentResponseItem, InteractionCommentAdapter.ItemViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemInteractionCommentBinding.inflate(
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

    inner class ItemViewHolder(private val binding: ItemInteractionCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: InteractionCommentResponseItem) {

            binding.apply {
                comment.let {
                    tvName.text = it.user?.name
                    tvContent.text = it.body

                    try {
                        tvDate.text = convertToHumanReadableDate(it.createdAt!!)
                    } catch (e: Exception) {
                        tvDate.text = it.createdAt
                    }

                    it.user?.isVerified?.let { isVerified ->
                        if (isVerified) {
                            tvIsVerified.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<InteractionCommentResponseItem>() {
            override fun areItemsTheSame(
                oldItem: InteractionCommentResponseItem,
                newItem: InteractionCommentResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: InteractionCommentResponseItem,
                newItem: InteractionCommentResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}