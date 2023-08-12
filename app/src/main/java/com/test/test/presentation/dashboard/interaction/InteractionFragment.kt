package com.test.test.presentation.dashboard.interaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test.R
import com.test.test.databinding.FragmentInteractionBinding
import com.test.test.presentation.adapter.InteractionAdapter
import com.test.test.presentation.adapter.LoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InteractionFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentInteractionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InteractionViewModel by viewModels()
    private val sharedViewModel: InteractionSharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInteractionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpRecyclerView()
        setUpLiveDataObserver()
    }

    private fun setUpRecyclerView() {
        binding.rvInteraction.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@InteractionFragment)
            fabAddInteraction.setOnClickListener(this@InteractionFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
            R.id.fab_add_interaction -> {
                findNavController().navigate(R.id.action_interactionFragment_to_addInteractionFragment)
            }
        }
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {

            interaction.observe(viewLifecycleOwner) {
                val adapter = InteractionAdapter(viewModel.token)
                binding.rvInteraction.adapter = adapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        adapter.retry()
                    })
                adapter.submitData(lifecycle, it)
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}