package com.test.test.presentation.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.test.test.R
import com.test.test.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

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
                    val bottomNavigationView =
                        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
                    bottomNavigationView.menu.clear()
                    if (it != "admin") {
                        bottomNavigationView.inflateMenu(R.menu.regular_user_bottom_nav_menu)
                    } else {
                        bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu)
                    }
                }
            }

            isValid.observe(viewLifecycleOwner) {
                if (it) {
                    val topics = listOf("post", viewModel.role.value!!)
                    for (topic in topics) {
                        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                            .addOnCompleteListener {
                                Log.e("#loginfrag", "Subscribed to FCM topic: $topic")
                            }
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_onBoardingFragment)
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
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val email = binding.edtEmail.text.toString()
                        val password = binding.edtPassword.text.toString()
                        val deviceToken = task.result
                        Log.e("#logfrag", deviceToken)
                        viewModel.login(email, password, deviceToken)

                    } else {
                        println("Failed to get FCM token: ${task.exception}")
                    }
                }
            }

            R.id.tv_register -> findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            R.id.tv_forgot_password -> findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }
}