package com.test.test.presentation.dashboard.volunteer.request_upgrade.detail

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
import com.test.test.databinding.FragmentDetailVolunteerApprovalBinding
import com.test.test.presentation.utils.convertToDayFirst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRequestUpgradeVolunteerFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentDetailVolunteerApprovalBinding
    private val viewModel: DetailRequestUpgradeVolunteerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailVolunteerApprovalBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpLiveDataObservers()
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@DetailRequestUpgradeVolunteerFragment)
            btnTerima.setOnClickListener(this@DetailRequestUpgradeVolunteerFragment)
            btnTolak.setOnClickListener(this@DetailRequestUpgradeVolunteerFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()

            R.id.btn_terima -> {
                viewModel.updateStatus("accepted")
            }

            R.id.btn_tolak -> {
                viewModel.updateStatus("rejected")
            }
        }
    }

    private fun setUpLiveDataObservers() {

        viewModel.apply {

            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        btnTerima.isEnabled = false
                        btnTolak.isEnabled = false
                    }
                } else {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        btnTerima.isEnabled = true
                        btnTolak.isEnabled = true
                    }
                }
            }

            success.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    findNavController().navigateUp()
                    if (it == "accepted") {
                        Toast.makeText(
                            requireActivity(),
                            "Berhasil menerima pengajuan volunteeer.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "Berhasil menolak pengajuan volunteeer.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    viewModel.success.value = ""
                }

            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

            data.observe(viewLifecycleOwner) {
                it.data?.let {
                    binding.apply {
                        it.profile!!.photo?.let { photo ->
                            Glide.with(requireContext()).load(IMAGE_URL + photo).into(imgProfile)
                        }

                        edtNik.setText(it.profile.nik)
                        edtNama.setText(it.profile.name)
                        edtNoHp.setText(it.user!!.phone)
                        edtTempatLahir.setText(it.profile.placeOfBirth)
                        edtEmail.setText(it.user.email)
                        try {
                            tvTanggalLahir.text = convertToDayFirst(it.profile.dateOfBirth!!)
                        } catch (e: Exception) {
                            tvTanggalLahir.text = it.profile.dateOfBirth
                        }
                        edtAlamat.setText(it.profile.address)
                        edtRt.setText(it.profile.rt)
                        edtRw.setText(it.profile.rw)
                        edtJenisKelamin.setText(it.profile.gender)
                        edtTps.setText(it.profile.tps)
                        edtProvinsi.setText(it.profile.province)
                        edtKabupaten.setText(it.profile.regency)
                        edtKecamatan.setText(it.profile.subdistrict)
                        edtDesa.setText(it.profile.village)
                        edtAlasan.setText(it.reason)
                    }
                }
            }
        }
    }
}