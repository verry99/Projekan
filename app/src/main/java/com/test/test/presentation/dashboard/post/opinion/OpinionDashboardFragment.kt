package com.test.test.presentation.dashboard.post.opinion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test.R
import com.test.test.databinding.FragmentPostDashboardBinding
import com.test.test.presentation.adapter.AllOpinionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpinionDashboardFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPostDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OpinionDashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = getString(R.string.opini)

        setUpActionListeners()
        setUpRecyclerView()
        setUpLiveDataObserver()
    }

    private fun setUpRecyclerView() {
        binding.apply {
            rvPost.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {
            opinion.observe(viewLifecycleOwner) {
                binding.rvPost.adapter = AllOpinionAdapter(it)
            }
        }
    }

    private fun setUpActionListeners() {
        binding.btnBack.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }
}