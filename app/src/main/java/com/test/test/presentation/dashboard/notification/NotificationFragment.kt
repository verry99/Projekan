package com.test.test.presentation.dashboard.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test.R
import com.test.test.databinding.FragmentNotificationBinding
import com.test.test.presentation.adapter.LoadingStateAdapter
import com.test.test.presentation.adapter.NotificationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentNotificationBinding
    private val viewModel: NotificationViewModel by viewModels()
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpRecyclerView()
        setUpLiveDataObserver()
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {
            notification.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.apply {
            rvNotification.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        adapter = NotificationAdapter(requireActivity())

        adapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                binding.apply {
                    rvShimmer.stopShimmer()
                    tvEmpty.visibility = View.VISIBLE
                    rvShimmer.visibility = View.GONE
                }
            } else if (loadState.source.refresh is LoadState.Loading) {
                binding.rvShimmer.apply {
                    startShimmer()
                    visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    rvNotification.visibility = View.VISIBLE
                    rvShimmer.stopShimmer()
                    rvShimmer.visibility = View.GONE
                }
            }
        }

        binding.rvNotification.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            })
    }

    private fun setUpActionListeners() {
        binding.btnBack.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchNotification()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }
}