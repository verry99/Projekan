package com.test.test.presentation.dashboard.other_user.user.volunteer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentUserVolunteerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserVolunteerFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentUserVolunteerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserVolunteerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserVolunteerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpLiveDataObservers()
    }

    private fun setUpLiveDataObservers() {
        viewModel.apply {
            status.observe(viewLifecycleOwner) {
                when (it) {
                    "accepted" -> {
                        binding.apply {
                            tvTitle1.text = "Selamat, pengajuan anda telah disetujui."
                            tvTitle2.visibility = View.GONE
                            tvAlasan.visibility = View.GONE
                            edtAlasan.visibility = View.GONE
                            edtAlasanDesc.visibility = View.GONE
                            btnDaftar.visibility = View.GONE
                        }
                    }

                    "rejected" -> {

                    }

                    "pending" -> {
                        binding.apply {
                            tvTitle1.text = "Anda sudah dalam daftar pengajuan sebagai relawan."
                            tvTitle2.visibility = View.GONE
                            tvAlasan.visibility = View.GONE
                            edtAlasan.visibility = View.GONE
                            edtAlasanDesc.visibility = View.GONE
                            btnDaftar.visibility = View.GONE
                        }
                    }
                }
            }

            success.observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.success.value = false
                    findNavController().navigateUp()
                    Toast.makeText(
                        requireActivity(),
                        "Berhasil menambahkan topik.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }

            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        binding.btnDaftar.isEnabled = false
                    }
                } else {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        binding.btnDaftar.isEnabled = true
                    }
                }
            }
        }
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@UserVolunteerFragment)
            btnDaftar.setOnClickListener(this@UserVolunteerFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
            R.id.btn_daftar -> {
                val reason = binding.edtAlasan.text.toString()
                if (reason.isNotEmpty()) {
                    viewModel.requestUpgradeVolunteer(binding.edtAlasan.text.toString())
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Lengkapi alasan terlebih dahulu.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}