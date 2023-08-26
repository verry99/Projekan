package com.test.test.presentation.analysis.area

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test.R
import com.test.test.databinding.FragmentAnalysisAreaBinding
import com.test.test.presentation.adapter.AnalysisAreaSupporterAdapter
import com.test.test.presentation.adapter.AreaAdapter
import com.test.test.presentation.adapter.LoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisAreaFragment : Fragment(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentAnalysisAreaBinding
    private val viewModel: AnalysisAreaViewModel by viewModels()
    private val args by navArgs<AnalysisAreaFragmentArgs>()
    private lateinit var adapter: AnalysisAreaSupporterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalysisAreaBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpinners()
        setUpActionListeners()
        setUpRecyclerView()
        setUpLiveDataObservers()

        binding.apply {
            if (args.role == "volunteer") {
                tvTitle.text = "Sebaran Wilayah Relawan"
                tvSebaranWilayahPendukung.text = "Sebaran Wilayah Relawan"
                tvDaftarPendukung.text = "Daftar Relawan"
            }
        }
    }

    private fun setUpSpinners() {
        val spinnerDataAreaType = resources.getStringArray(R.array.spinner_area_type)

        val adapterAreaType = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerDataAreaType
        )

        val adapterAreaItem = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrayOf("Pilih Wilayah")
        )

        adapterAreaType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterAreaItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.apply {
            spinnerAreaType.adapter = adapterAreaType
            spinnerAreaItem.adapter = adapterAreaItem
        }
    }

    private fun setUpRecyclerView() {
        binding.apply {
            rvSupporterNumber.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvSupporter.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            adapter = AnalysisAreaSupporterAdapter()

            binding.rvSupporter.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                })
        }
    }

    private fun setUpLiveDataObservers() {
        viewModel.apply {

            areaSpinnerFiltered.observe(viewLifecycleOwner) {
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerAreaItem.adapter = adapter
            }

            summaryResponse.observe(viewLifecycleOwner) {
                binding.apply {
                    rvSupporterNumber.adapter = AreaAdapter(it.area)
                }
            }
            dataByArea.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }

            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        spinnerAreaType.isEnabled = false
                        spinnerAreaItem.isEnabled = false
                    }
                } else {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        spinnerAreaType.isEnabled = true
                        spinnerAreaItem.isEnabled = true
                    }
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) Toast.makeText(
                    requireContext(),
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@AnalysisAreaFragment)
            spinnerAreaType.onItemSelectedListener = this@AnalysisAreaFragment
            spinnerAreaItem.onItemSelectedListener = this@AnalysisAreaFragment
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedSpinnerId = parent?.id
        val selectedItem = parent?.getItemAtPosition(position).toString()

        when (selectedSpinnerId) {
            R.id.spinner_area_type -> viewModel.setSelectedAreaType(selectedItem)
            R.id.spinner_area_item -> viewModel.setSelectedAreaItem(selectedItem)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }
}