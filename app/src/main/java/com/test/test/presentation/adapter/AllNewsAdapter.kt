package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.data.remote.dto.post.PostResponseItem
import com.test.test.databinding.ItemOpinionBinding
import com.test.test.presentation.dashboard.post.news.NewsDashboardFragmentDirections

class AllNewsAdapter :
    PagingDataAdapter<PostResponseItem, AllNewsAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemOpinionBinding.inflate(
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

    inner class ItemViewHolder(private val binding: ItemOpinionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostResponseItem) {
            binding.apply {
                Glide
                    .with(itemView)
                    .load(post.bannerUrl)
                    .into(imgOpinion)
                tvTitle.text = post.title
                tvDate.text = post.humanReadableDate
            }

            setUpActionListener(post)
        }

        private fun setUpActionListener(post: PostResponseItem) {
            itemView.setOnClickListener {
                val action =
                    NewsDashboardFragmentDirections.actionNewsDashboardFragmentToDetailPostDashboardFragment(
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