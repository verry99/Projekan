package com.test.test.presentation.dashboard.schedule.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test.R
import com.test.test.databinding.FragmentScheduleInactiveBinding
import com.test.test.presentation.adapter.LoadingStateAdapter
import com.test.test.presentation.adapter.ScheduleAdapter
import com.test.test.presentation.dashboard.schedule.ScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleInactiveFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentScheduleInactiveBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScheduleViewModel by viewModels({ requireParentFragment() })
    private lateinit var adapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleInactiveBinding.inflate(inflater, container, false)

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
        binding.rvSchedule.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            })
    }

    private fun setUpActionListeners() {
        binding.apply {

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {
            inactiveSchedule?.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}