package com.test.test.presentation.analysis.region

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
import com.test.test.R
import com.test.test.databinding.FragmentAnalysisRegionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisRegionFragment : Fragment(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    private var _binding: FragmentAnalysisRegionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnalysisRegionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalysisRegionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpRecyclerView()
        setUpLiveDataObservers()
    }

    private fun setUpRecyclerView() {
        binding.apply {
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedSpinnerId = parent?.id
        val selectedItem = parent?.getItemAtPosition(position).toString()

        when (selectedSpinnerId) {
            R.id.spinner_provinsi -> viewModel.selectedProvince.value = selectedItem
            R.id.spinner_kabupaten -> viewModel.selectedRegency.value = selectedItem
            R.id.spinner_kecamatan -> viewModel.selectedDistrict.value = selectedItem
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@AnalysisRegionFragment)
            spinnerKabupaten.onItemSelectedListener = this@AnalysisRegionFragment
            spinnerKecamatan.onItemSelectedListener = this@AnalysisRegionFragment
        }
    }

    private fun setUpLiveDataObservers() {

        viewModel.apply {

            regency.observe(viewLifecycleOwner) {
                val itemList: List<String> = it.map { regency -> regency.name }
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerKabupaten.adapter = adapter
            }

            subDistrict.observe(viewLifecycleOwner) {
                val itemList: List<String> = it.map { district -> district.name }
                val adapter =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        itemList
                    )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerKecamatan.adapter = adapter
            }
            village.observe(viewLifecycleOwner) {
                val itemList: List<String> = it.map { village -> village.name }
                val adapter =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        itemList
                    )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerDesa.adapter = adapter
            }

            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                    }
                } else {
                    binding.apply {
                        progressBar.visibility = View.GONE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}