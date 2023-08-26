package com.test.test.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.R
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.data.remote.dto.notification.NotificationResponseItem
import com.test.test.databinding.ItemNotificationBinding
import com.test.test.presentation.dashboard.notification.NotificationFragmentDirections

class NotificationAdapter(private val context: Context) :
    PagingDataAdapter<NotificationResponseItem, NotificationAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        val data = getItem(position)!!
        viewHolder.bind(data)
    }

    inner class ItemViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: NotificationResponseItem) {
            binding.apply {
                notification.image?.let {
                    Glide
                        .with(itemView)
                        .load("$IMAGE_URL/${notification.image}")
                        .into(imgNotification)
                }

                tvTitle.text = notification.title
                tvDate.text = notification.message

                val isRead = notification.isRead!!.toInt()
                if (isRead == 0) {
                    val typeFace =
                        ResourcesCompat.getFont(context, R.font.inter_semibold)
                    tvTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                    tvDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                }
            }

            setUpActionListener(notification)
        }

        private fun setUpActionListener(notification: NotificationResponseItem) {
            itemView.setOnClickListener {
                val action =
                    NotificationFragmentDirections.actionNotificationFragmentToDetailNotificationFragment(
                        notification.id.toString()
                    )

                itemView.findNavController().navigate(action)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NotificationResponseItem>() {
            override fun areItemsTheSame(
                oldItem: NotificationResponseItem,
                newItem: NotificationResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: NotificationResponseItem,
                newItem: NotificationResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}