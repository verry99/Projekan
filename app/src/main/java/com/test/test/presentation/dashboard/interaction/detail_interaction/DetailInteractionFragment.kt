package com.test.test.presentation.dashboard.interaction.detail_interaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.test.test.R
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.databinding.FragmentDetailInteractionBinding
import com.test.test.presentation.adapter.InteractionCommentAdapter
import com.test.test.presentation.adapter.LoadingStateAdapter
import com.test.test.presentation.dashboard.interaction.InteractionSharedViewModel
import com.test.test.presentation.utils.convertToHumanReadableDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailInteractionFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailInteractionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailInteractionViewModel by viewModels()

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

        setUpRecyclerView()
        setUpActionListeners()
        setUpLiveObserver()
    }

    private fun setUpRecyclerView() {
        binding.rvComment.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpLiveObserver() {
        viewModel.apply {

            interaction.observe(viewLifecycleOwner) {
                binding.apply {
                    tvTitle.text = it.title
                    tvFullName.text = it.author
                    tvDescription.text = it.description
                    try {
                        tvDate.text = convertToHumanReadableDate(it.createdAt!!)
                    } catch (e: Exception) {
                        tvDate.text = it.createdAt
                    }

                    it.image?.let {
                        Glide
                            .with(requireContext())
                            .load("$IMAGE_URL/$it")
                            .into(binding.imgInteraction)
                    }
                }
            }

            comment.observe(viewLifecycleOwner) {
                val adapter = InteractionCommentAdapter()
                binding.rvComment.adapter = adapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        adapter.retry()
                    })
                adapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@DetailInteractionFragment)
        }
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