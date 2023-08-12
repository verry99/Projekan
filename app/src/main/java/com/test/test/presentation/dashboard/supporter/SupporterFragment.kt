package com.test.test.presentation.dashboard.supporter

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
import com.test.test.databinding.FragmentSupporterBinding
import com.test.test.presentation.adapter.LoadingStateAdapter
import com.test.test.presentation.adapter.SupporterAdapter
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
                val supporterName = s.toString()
                if (supporterName.isNotEmpty()) {
                    viewModel.searchSupporter(supporterName).observe(viewLifecycleOwner) {
                        val adapter = SupporterAdapter()
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

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@SupporterFragment)
            fabAddSupporter.setOnClickListener(this@SupporterFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
            R.id.fab_add_supporter -> findNavController().navigate(R.id.action_supporterFragment_to_addSupporterFragment)
        }
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {

            supporterSummary.observe(viewLifecycleOwner) {
                binding.apply {
                    tvSupporterNumberTotal.text = it.totalSupporter.toString()
                    tvSupporterNumberMale.text = it.gender.l.toString()
                    tvSupporterNumberFemale.text = it.gender.p.toString()
                }
            }

            supporter.observe(viewLifecycleOwner) {
                val adapter = SupporterAdapter()
                binding.rvSupporter.adapter = adapter.withLoadStateFooter(
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}