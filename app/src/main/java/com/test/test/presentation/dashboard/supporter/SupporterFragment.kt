package com.test.test.presentation.dashboard.supporter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test.R
import com.test.test.databinding.FragmentSupporterBinding
import com.test.test.presentation.adapter.LoadingStateAdapter
import com.test.test.presentation.adapter.SupporterAdapter
import com.test.test.presentation.adapter.VolunteerDetailSupporterAdapter
import com.test.test.presentation.adapter.VolunteerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SupporterFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSupporterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SupporterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSupporterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpRecyclerView()
        setUpEdtSearch()
        setUpLiveDataObserver()
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
                        binding.rvSupporter.adapter = adapter
                    }
                } else {
                    viewModel.supporter.observe(viewLifecycleOwner) {
                        val adapter = SupporterAdapter()
                        binding.rvSupporter.adapter = adapter.withLoadStateFooter(
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
        binding.rvSupporter.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpLiveDataObserver() {
//        viewModel.volunteerSummary.observe(viewLifecycleOwner) {
//            binding.apply {
//                tvSupporterNumberTotal.text = it.totalVolunteer.toString()
//                tvSupporterNumberMale.text = it.requestUpgradeCount.toString()
//                tvSupporterNumberFemale.text = it.requestUpgradeCount.toString()
//            }
//        }
        viewModel.supporter.observe(viewLifecycleOwner) {
            val adapter = SupporterAdapter()
            binding.rvSupporter.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                })
            adapter.submitData(lifecycle, it)
        }
    }

    private fun setUpActionListeners() {
        binding.btnBack.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }
}