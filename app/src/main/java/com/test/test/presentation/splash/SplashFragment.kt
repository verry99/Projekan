package com.test.test.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.test.R
import com.test.test.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLiveDataObserver()
    }

    private fun setUpLiveDataObserver() {
        viewModel.user.observe(viewLifecycleOwner) {
            it?.accessToken.let { accessToken ->
                if (accessToken!!.isEmpty()) {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                } else {
                    lifecycleScope.launch {
                        delay(500)
                        if (it.role != "admin") {
                            val bottomNavigationView =
                                requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
                            bottomNavigationView.menu.clear()
                            bottomNavigationView.inflateMenu(R.menu.regular_user_bottom_nav_menu)
                        }
                        findNavController().navigate(R.id.action_splashFragment_to_dashboardFragment)
                    }
                }
            }
        }
    }
}