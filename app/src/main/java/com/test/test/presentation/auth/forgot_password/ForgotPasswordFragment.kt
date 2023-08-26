package com.test.test.presentation.auth.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListener()
        setUpLiveDataObserver()
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {

            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnKirimEmail.isEnabled = false
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.btnKirimEmail.isEnabled = true
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }

            isValid.observe(viewLifecycleOwner) {
                binding.apply {
                    if (it) {
                        content.visibility = View.GONE
                        statusContainer.visibility = View.VISIBLE
                    } else {
                        content.visibility = View.VISIBLE
                        statusContainer.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setUpActionListener() {
        binding.apply {
            btnKirimEmail.setOnClickListener(this@ForgotPasswordFragment)
            btnBack.setOnClickListener(this@ForgotPasswordFragment)
            tvRegister.setOnClickListener(this@ForgotPasswordFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_kirim_email -> {
                viewModel.forgotPassword(binding.edtEmail.text.toString())
            }

            R.id.btn_back -> findNavController().navigateUp()
            R.id.tv_register -> findNavController().navigate(R.id.action_forgotPasswordFragment_to_registerFragment)
        }
    }
}