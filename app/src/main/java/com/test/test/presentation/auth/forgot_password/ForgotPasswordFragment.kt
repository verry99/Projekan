package com.test.test.presentation.auth.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnKirimEmail.setOnClickListener(this@ForgotPasswordFragment)
            tvRegister.setOnClickListener(this@ForgotPasswordFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_kirim_email -> findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
            R.id.tv_register -> findNavController().navigate(R.id.action_forgotPasswordFragment_to_registerFragment)
        }
    }
}