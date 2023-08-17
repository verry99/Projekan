package com.test.test.presentation.dashboard.volunteer.request_upgrade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test.R
import com.test.test.databinding.FragmentVolunteerApprovalBinding
import com.test.test.presentation.adapter.LoadingStateAdapter
import com.test.test.presentation.adapter.RequestUpgradeVolunteerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestUpgradeVolunteerFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentVolunteerApprovalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RequestUpgradeVolunteerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVolunteerApprovalBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpRecyclerView()
        setUpLiveDataObserver()
    }

    private fun setUpRecyclerView() {
        binding.rvVolunteer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {

            volunteer.observe(viewLifecycleOwner) {
                val adapter = RequestUpgradeVolunteerAdapter()
                binding.rvVolunteer.adapter = adapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        adapter.retry()
                    })
                adapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@RequestUpgradeVolunteerFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            fetchData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}