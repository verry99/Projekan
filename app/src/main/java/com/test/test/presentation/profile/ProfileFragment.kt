package com.test.test.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.test.test.R
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpLiveDataObserver()
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {
            user.observe(viewLifecycleOwner) {
                binding.apply {
                    tvFullName.text = it.name
                    tvRole.text = it.role

                    if (it.urlToImage.isNotEmpty()) {
                        Glide.with(requireContext())
                            .load(IMAGE_URL + it.urlToImage)
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
                viewModel.logout()
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}