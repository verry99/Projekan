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
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test.R
import com.test.test.databinding.FragmentSupporterBinding
import com.test.test.presentation.adapter.SupporterAdapter
import com.test.test.presentation.utils.formatNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SupporterFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSupporterBinding
    private val viewModel: SupporterViewModel by viewModels()
    private lateinit var adapter: SupporterAdapter
    private val args: SupporterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSupporterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpRecyclerView()

        if (args.role == "user") {
            binding.apply {
                content.visibility = View.GONE
                fabAddSupporter.visibility = View.GONE
                restriction.visibility = View.VISIBLE
            }
            return
        }
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
                        adapter.submitData(lifecycle, it)
                    }
                } else {
                    viewModel.supporter.observe(viewLifecycleOwner) {
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

        adapter = SupporterAdapter()

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
                    rvSupporter.visibility = View.VISIBLE
                    rvShimmer.stopShimmer()
                    rvShimmer.visibility = View.GONE
                }
            }
        }

        binding.rvSupporter.adapter = adapter
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@SupporterFragment)
            btnBackRestriction.setOnClickListener(this@SupporterFragment)
            fabAddSupporter.setOnClickListener(this@SupporterFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
            R.id.btn_back_restriction -> findNavController().navigateUp()
            R.id.fab_add_supporter -> findNavController().navigate(R.id.action_supporterFragment_to_addSupporterFragment)
        }
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {

            supporterSummary.observe(viewLifecycleOwner) {
                binding.apply {
                    tvSupporterNumberTotal.text = formatNumber(it.totalSupporter.toLong())
                }
            }

            supporter.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}