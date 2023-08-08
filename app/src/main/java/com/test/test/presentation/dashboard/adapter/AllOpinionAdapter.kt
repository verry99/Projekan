package com.test.test.presentation.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.test.test.databinding.ItemOpinionBinding
import com.test.test.domain.models.Post
import com.test.test.presentation.dashboard.post.opinion.OpinionDashboardFragmentDirections

class AllOpinionAdapter(private val data: List<Post>) :
    RecyclerView.Adapter<AllOpinionAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemOpinionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val opinion = data[position]
        holder.bind(opinion)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ItemViewHolder(private val binding: ItemOpinionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(opinion: Post) {
            Glide.with(binding.root)
                .load(opinion.urlToImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imgOpinion)

            binding.apply {
                tvTitle.text = opinion.title
                tvDate.text = opinion.date
            }
            setUpActionListener(opinion)
        }

        private fun setUpActionListener(news: Post) {
            itemView.setOnClickListener {
                val action =
                    OpinionDashboardFragmentDirections.actionOpinionDashboardFragmentToDetailPostDashboardFragment(
                        news.slug
                    )

                itemView.findNavController().navigate(action)
            }
        }
    }
}