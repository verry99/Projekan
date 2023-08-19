package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.data.remote.dto.post.PostResponseItem
import com.test.test.databinding.ItemActivitiesBinding
import com.test.test.presentation.dashboard.activities.ActivitiesFragmentDirections
import com.test.test.presentation.dashboard.post.news.NewsDashboardFragmentDirections

class ActivitiesAdapter :
    PagingDataAdapter<PostResponseItem, ActivitiesAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemActivitiesBinding.inflate(
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

    inner class ItemViewHolder(private val binding: ItemActivitiesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostResponseItem) {
            binding.apply {
                Glide
                    .with(itemView)
                    .load(post.bannerUrl)
                    .into(imgSchedule)
                tvTitle.text = post.title
                tvDate.text = post.humanReadableDate
            }

            setUpActionListener(post)
        }

        private fun setUpActionListener(post: PostResponseItem) {
            itemView.setOnClickListener {
                val action =
                    ActivitiesFragmentDirections.actionActivitiesFragmentToDetailActivitiesFragment(
                        post.slug.toString()
                    )

                itemView.findNavController().navigate(action)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostResponseItem>() {
            override fun areItemsTheSame(
                oldItem: PostResponseItem,
                newItem: PostResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PostResponseItem,
                newItem: PostResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}