package com.test.test.presentation.dashboard.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.test.test.databinding.ItemNewsBinding
import com.test.test.domain.models.Post
import com.test.test.presentation.dashboard.DashboardFragmentDirections

class NewsAdapter(private val data: List<Post>) :
    RecyclerView.Adapter<NewsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val news = data[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ItemViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: Post) {
            Glide.with(binding.root)
                .load(news.urlToImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imgNews)

            binding.tvTitle.text = news.title
            setUpActionListener(news)
        }

        private fun setUpActionListener(news: Post) {
            itemView.setOnClickListener {
                val action =
                    DashboardFragmentDirections.actionDashboardFragmentToDetailPostDashboardFragment(
                        news.slug
                    )

                itemView.findNavController().navigate(action)
            }
        }
    }
}