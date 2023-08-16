package com.test.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.test.R
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.data.remote.dto.schedule.ScheduleResponseItem
import com.test.test.databinding.ItemScheduleBinding
import com.test.test.presentation.dashboard.schedule.ScheduleFragmentDirections
import com.test.test.presentation.utils.convertToNormalDate

class ScheduleAdapter :
    PagingDataAdapter<ScheduleResponseItem, ScheduleAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemScheduleBinding.inflate(
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

    inner class ItemViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: ScheduleResponseItem) {

            binding.apply {

                schedule.let {
                    Glide.with(itemView).load(IMAGE_URL + "/" + it.image).into(imgSchedule)
                    tvTitle.text = it.title
                    try {
                        tvDate.text = convertToNormalDate(it.startDate!!)
                    } catch (e: Exception) {
                        tvDate.text = it.startDate
                    }

                    if (it.active!!) {
                        tvStatus.text = "AKTIF"
                    } else {
                        tvStatus.text = "BERAKHIR"
                        val textColor = ContextCompat.getColor(itemView.context, R.color.red)
                        tvStatus.setTextColor(textColor)
                    }
                }
            }
            setUpActionListener(schedule)
        }

        private fun setUpActionListener(schedule: ScheduleResponseItem) {
            itemView.setOnClickListener {
                val action =
                    ScheduleFragmentDirections.actionScheduleFragmentToDetailScheduleFragment(
                        schedule.id.toString(),
                    )

                itemView.findNavController().navigate(action)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ScheduleResponseItem>() {
            override fun areItemsTheSame(
                oldItem: ScheduleResponseItem,
                newItem: ScheduleResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ScheduleResponseItem,
                newItem: ScheduleResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}