package com.test.test.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.test.test.R
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpLiveDataObserver()
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {
            profile.observe(viewLifecycleOwner) {
                binding.apply {
                    tvFullName.text = it.name
                    tvRole.text = it.role

                    it.profile.photo.let {
                        Glide.with(requireContext())
                            .load("$IMAGE_URL/$it")
                            .into(binding.imgProfile)
                    }
                }
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

            logout.observe(viewLifecycleOwner) {
                if (it) findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnEditProfile.setOnClickListener(this@ProfileFragment)
            btnUbahPassword.setOnClickListener(this@ProfileFragment)
            btnUbahNoHp.setOnClickListener(this@ProfileFragment)
            tvKeluar.setOnClickListener(this@ProfileFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit_profile -> findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
            R.id.btn_ubah_password -> findNavController().navigate(R.id.action_profileFragment_to_changePasswordFragment)
            R.id.btn_ubah_no_hp -> findNavController().navigate(R.id.action_profileFragment_to_changePhoneNumberFragment)
            R.id.tv_keluar -> {
                MaterialDialog(requireContext()).show {
                    title(text = "Konfirmasi logout")
                    positiveButton(text = "Keluar") {
                        viewModel.logout()
                    }
                    negativeButton(text = "Batal")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.apply {
            profile.observe(viewLifecycleOwner) {
                binding.apply {
                    tvFullName.text = it.name
                    tvRole.text = it.role

                    it.profile.photo.let {
                        Glide.with(requireContext())
                            .load("$IMAGE_URL/$it")
                            .into(binding.imgProfile)
                    }
                }
            }
        }
    }
}