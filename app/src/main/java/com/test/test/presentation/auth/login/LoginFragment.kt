package com.test.test.presentation.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.test.R
import com.test.test.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListener()
        setUpLiveDataObserver()
    }

    private fun setUpActionListener() {
        binding.apply {
            btnLogin.setOnClickListener(this@LoginFragment)
            tvRegister.setOnClickListener(this@LoginFragment)
            tvForgotPassword.setOnClickListener(this@LoginFragment)
        }
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            role.observe(viewLifecycleOwner) {
                it?.let {
                    if (it != "admin") {
                        val bottomNavigationView =
                            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
                        bottomNavigationView.menu.clear()
                        bottomNavigationView.inflateMenu(R.menu.regular_user_bottom_nav_menu)
                    }
                }
            }

            isValid.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                btnLogin.isEnabled = false
                tvRegister.isEnabled = false
                tvForgotPassword.isEnabled = false
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                btnLogin.isEnabled = true
                tvRegister.isEnabled = true
                tvForgotPassword.isEnabled = true
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                val deviceToken = "abc"

                viewModel.login(email, password, deviceToken)
            }

            R.id.tv_register -> findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            R.id.tv_forgot_password -> findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}