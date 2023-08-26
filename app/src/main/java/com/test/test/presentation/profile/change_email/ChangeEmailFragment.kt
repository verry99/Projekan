package com.test.test.presentation.profile.change_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentChangeEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeEmailFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentChangeEmailBinding
    private val viewModel: ChangeEmailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeEmailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpLiveDataObserver()
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
                        "Berhasil mengubah alamat email.",
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

    private fun updateEmail() {
        val password = binding.edtPassword.text.toString()
        val email = binding.edtEmail.text.toString()

        if (password.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Lengkapi password terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (email.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Lengkapi Email baru terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        viewModel.updateEmail(password, email)
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@ChangeEmailFragment)
            btnSimpan.setOnClickListener(this@ChangeEmailFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
            R.id.btn_simpan -> updateEmail()
        }
    }
}