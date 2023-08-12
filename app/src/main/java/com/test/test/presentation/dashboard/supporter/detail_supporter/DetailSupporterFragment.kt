package com.test.test.presentation.dashboard.supporter.detail_supporter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.test.test.R
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.databinding.FragmentDetailSupporterBinding
import com.test.test.presentation.utils.convertToDayFirst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailSupporterFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailSupporterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailSupporterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailSupporterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpLiveDataObserver()
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {

            supporter.observe(viewLifecycleOwner) {
                binding.apply {
                    tvFullName.text = it.name
                    tvNamaLengkap.text = it.name
                    tvNik.text = it.nik
                    tvPhone.text = it.phone
                    tvAgama.text = it.religion
                    tvJenisKelamin.text = it.gender
                    tvKelurahan.text = it.village
                    tvKecamatan.text = it.subdistrict
                    tvKabupaten.text = it.regency
                    tvProvinsi.text = it.province
                    tvRt.text = it.rt
                    tvRw.text = it.rw
                    tvTps.text = it.tps

                    it.age?.let {
                        tvAge.text = it
                        tvUsia.text = it
                    }

                    it.maritalStatus?.let {
                        tvStatus.text = it
                    }

                    try {
                        tvTempatTanggalLahir.text =
                            "${it.placeOfBirth}, ${convertToDayFirst(it.dateOfBirth)}"
                    } catch (e: Exception) {
                        tvTempatTanggalLahir.text = "${it.placeOfBirth}, ${it.dateOfBirth}"
                    }

                    it.photo?.let {
                        Glide.with(binding.root).load(IMAGE_URL + it)
                            .into(binding.imgProfile)
                    }
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpActionListeners() {
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}