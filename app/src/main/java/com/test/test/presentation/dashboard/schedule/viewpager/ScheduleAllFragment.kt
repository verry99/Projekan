package com.test.test.presentation.dashboard.schedule.viewpager

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
import com.test.test.databinding.FragmentScheduleAllBinding
import com.test.test.presentation.adapter.LoadingStateAdapter
import com.test.test.presentation.adapter.ScheduleAdapter
import com.test.test.presentation.dashboard.schedule.ScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleAllFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentScheduleAllBinding
    private val viewModel: ScheduleViewModel by viewModels({ requireParentFragment() })
    private lateinit var adapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleAllBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpRecyclerView()
        setUpLiveDataObserver()
    }

    private fun setUpRecyclerView() {
        binding.rvSchedule.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter = ScheduleAdapter()

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
                    rvSchedule.visibility = View.VISIBLE
                    rvShimmer.stopShimmer()
                    rvShimmer.visibility = View.GONE
                }
            }
        }

        binding.rvSchedule.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            })
    }

    private fun setUpActionListeners() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {
            allSchedule?.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
        }
    }
}