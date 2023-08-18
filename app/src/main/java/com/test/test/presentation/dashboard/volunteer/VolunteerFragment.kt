package com.test.test.presentation.dashboard.volunteer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test.R
import com.test.test.databinding.FragmentVolunteerBinding
import com.test.test.presentation.adapter.LoadingStateAdapter
import com.test.test.presentation.adapter.VolunteerAdapter
import com.test.test.presentation.utils.formatNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VolunteerFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentVolunteerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VolunteerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVolunteerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpRecyclerView()
        setUpEdtSearch()
        setUpLiveDataObserver()
        setUpSwipeRefresh()
    }

    private fun setUpSwipeRefresh() {
        binding.swipeToRefresh.apply {
            setOnRefreshListener {
                viewModel.refreshPage()
                viewModel.isLoading.observe(viewLifecycleOwner) {
                    if (!it) {
                        this.isRefreshing = false
                    }
                }
            }
        }
    }

    private fun setUpEdtSearch() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val volunteerName = s.toString()
                if (volunteerName.isNotEmpty()) {
                    viewModel.searchVolunteer(volunteerName).observe(viewLifecycleOwner) {
                        val adapter = VolunteerAdapter()
                        adapter.submitData(lifecycle, it)
                        binding.rvVolunteer.adapter = adapter
                    }
                } else {
                    viewModel.volunteer.observe(viewLifecycleOwner) {
                        val adapter = VolunteerAdapter()
                        binding.rvVolunteer.adapter = adapter.withLoadStateFooter(
                            footer = LoadingStateAdapter {
                                adapter.retry()
                            })
                        adapter.submitData(lifecycle, it)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setUpRecyclerView() {
        binding.rvVolunteer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {

            volunteerSummary.observe(viewLifecycleOwner) {
                binding.apply {
                    tvVolunteerNumber.text = formatNumber(it.totalVolunteer.toLong())
                    tvApprovalNumber.text = formatNumber(it.requestUpgradeCount.toLong())
                }
            }

            volunteer.observe(viewLifecycleOwner) {
                val adapter = VolunteerAdapter()
                binding.rvVolunteer.adapter = adapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        adapter.retry()
                    })
                adapter.submitData(lifecycle, it)
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@VolunteerFragment)
            btnVolunteerApproval.setOnClickListener(this@VolunteerFragment)
            fabAddVolunteer.setOnClickListener(this@VolunteerFragment)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
            R.id.btn_volunteer_approval -> {
                val action =
                    VolunteerFragmentDirections.actionVolunteerFragmentToVolunteerApprovalFragment(
                        viewModel.token
                    )
                findNavController().navigate(action)
            }

            R.id.fab_add_volunteer -> findNavController().navigate(R.id.action_volunteerFragment_to_addVolunteerFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            refreshPage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}