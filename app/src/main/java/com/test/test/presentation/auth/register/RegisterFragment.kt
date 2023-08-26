package com.test.test.presentation.auth.register

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
import com.test.test.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListener()
        setUpLiveDataObserver()
    }

    private fun setUpActionListener() {
        binding.apply {
            btnRegister.setOnClickListener(this@RegisterFragment)
            tvLogin.setOnClickListener(this@RegisterFragment)
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
                    findNavController().navigate(R.id.action_registerFragment_to_onBoardingFragment)
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
                btnRegister.isEnabled = false
                tvLogin.isEnabled = false
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                btnRegister.isEnabled = true
                tvLogin.isEnabled = true
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    val name = binding.edtName.text.toString()
                    val email = binding.edtEmail.text.toString()
                    val phone = binding.edtNoHp.text.toString()
                    val password = binding.edtPassword.text.toString()
                    val passwordConfirmation = binding.edtPasswordConfirm.text.toString()
                    val deviceToken = task.result

                    FirebaseMessaging.getInstance().subscribeToTopic("post").addOnCompleteListener {
                        Log.e("#registerfrag", "successfully subscribe FCM topic post")
                        viewModel.register(
                            name,
                            email,
                            phone,
                            password,
                            passwordConfirmation,
                            deviceToken
                        )
                    }
                }
            }

            R.id.tv_login -> findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}