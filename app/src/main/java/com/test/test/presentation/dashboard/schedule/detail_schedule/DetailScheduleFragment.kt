package com.test.test.presentation.dashboard.schedule.detail_schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentDetailInteractionBinding
import com.test.test.presentation.adapter.InteractionCommentAdapter
import com.test.test.presentation.dashboard.interaction.detail_interaction.DetailScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailScheduleFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailInteractionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailScheduleViewModel by viewModels()
    private val adapter = InteractionCommentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailInteractionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpLiveObserver()
    }

    private fun setUpLiveObserver() {
        viewModel.apply {

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@DetailScheduleFragment)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}