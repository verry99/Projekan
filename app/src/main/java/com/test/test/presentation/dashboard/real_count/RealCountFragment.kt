package com.test.test.presentation.dashboard.real_count

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentRealCountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealCountFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentRealCountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RealCountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRealCountBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListener()
    }

    private fun setUpActionListener() {
        binding.apply {
            btnBack.setOnClickListener(this@RealCountFragment)
            fabAdd.setOnClickListener(this@RealCountFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                findNavController().navigateUp()
            }

            R.id.fab_add -> {
                findNavController().navigate(R.id.action_realCountFragment_to_addRealCountFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}