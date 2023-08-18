package com.test.test.presentation.profile.change_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentChangePasswordBinding? = null
    private val viewModel: ChangePasswordViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpLiveDataObserver()
    }

    private fun updatePassword() {
        val oldPassword = binding.edtOldPassword.text.toString()
        val password = binding.edtPassword.text.toString()
        val passwordConfirmation = binding.edtPasswordConfirm.text.toString()

        if (oldPassword.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Lengkapi password lama terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (password.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Lengkapi password baru terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (passwordConfirmation.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Lengkapi konfirmasi password baru terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }


        if (password != passwordConfirmation) {
            Toast.makeText(
                requireContext(),
                "Pastikan konfirmasi password sama dengan password baru.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        viewModel.updatePassword(oldPassword, password, passwordConfirmation)
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        btnSimpan.isEnabled = false
                    }
                } else {
                    binding.apply {
                        btnSimpan.isEnabled = true
                        progressBar.visibility = View.GONE
                    }
                }
            }

            success.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigateUp()
                    Toast.makeText(
                        requireActivity(),
                        "Berhasil mengubah password.",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.success.value = false
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

    private fun setUpActionListeners(
    ) {
        binding.apply {
            btnBack.setOnClickListener(this@ChangePasswordFragment)
            btnSimpan.setOnClickListener(this@ChangePasswordFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
            R.id.btn_simpan -> updatePassword()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}