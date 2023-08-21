package com.test.test.presentation.analysis.area

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test.R
import com.test.test.databinding.FragmentAnalysisAreaBinding
import com.test.test.presentation.adapter.AnalysisAreaSupporterAdapter
import com.test.test.presentation.adapter.AreaAdapter
import com.test.test.presentation.utils.formatNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisAreaFragment : Fragment(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentAnalysisAreaBinding
    private val viewModel: AnalysisAreaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalysisAreaBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpinners()
        setUpActionListeners()
        setUpRecyclerView()
        setUpLiveDataObservers()
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
        }
    }

    private fun setUpLiveDataObservers() {
        viewModel.apply {

            areaToShow.observe(viewLifecycleOwner) {
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerAreaItem.adapter = adapter
            }

            areaDataResponse.observe(viewLifecycleOwner) { response ->
                response.data!!.area.let {
                    val supporterMaleTotal: Int =
                        it.fold(0) { acc, area -> acc + (area.gender?.l ?: 0) }
                    val supporterFemaleTotal: Int =
                        it.fold(0) { acc, area -> acc + (area.gender?.p ?: 0) }
                    val supporterTotal = supporterMaleTotal + supporterFemaleTotal

                    binding.apply {
                        tableLastItem.visibility = View.VISIBLE
                        rvSupporterNumber.adapter = AreaAdapter(it)
                        tvTotalSupporterNumberMale.text = formatNumber(supporterMaleTotal.toLong())
                        tvTotalSupporterNumberFemale.text =
                            formatNumber(supporterFemaleTotal.toLong())
                        tvTotalAllSupporterNumber.text = formatNumber(supporterTotal.toLong())
                    }

                    binding.rvSupporter.adapter =
                        AnalysisAreaSupporterAdapter(response.data.allSupporter.supporter)
                }
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