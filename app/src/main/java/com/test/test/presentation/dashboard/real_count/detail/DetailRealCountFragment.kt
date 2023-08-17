package com.test.test.presentation.dashboard.real_count.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentDetailRealCountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRealCountFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailRealCountBinding? =
        null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentDetailRealCountBinding.inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setUpRecyclerView()
        setUpActionListeners()
//        setUpLiveObserver()
    }

    //
//    private fun setUpRecyclerView() {
//        binding.rvComment.layoutManager =
//            LinearLayoutManager(
//                requireContext(),
//                LinearLayoutManager.VERTICAL,
//                false
//            )
//
//        binding.rvComment.adapter = adapter.withLoadStateFooter(
//            footer = LoadingStateAdapter
//            {
//                adapter.retry()
//            })
//    }
//
//    private fun setUpLiveObserver() {
//        viewModel.apply {
//
//            interaction.observe(viewLifecycleOwner) {
//                binding.apply {
//                    tvTitle.text = it.title
//                    tvFullName.text = it.author
//                    tvDescription.text = it.description
//
//                    try {
//                        tvDate.text = convertToHumanReadableDate(it.createdAt!!)
//                    } catch (e: Exception) {
//                        tvDate.text = it.createdAt
//                    }
//                }
//            }
//
//            comment.observe(viewLifecycleOwner) {
//                adapter.submitData(lifecycle, it)
//            }
//
//            addCommentSuccess.observe(viewLifecycleOwner) {
//                if (it) {
//                    binding.edtComment.setText("")
//                    viewModel.fetchComment()
//                    comment.observe(viewLifecycleOwner) {
//                        adapter.submitData(lifecycle, it)
//                    }
//
//                    viewModel.setAddCommentSuccess(false)
//                }
//            }
//
//            errorMessage.observe(viewLifecycleOwner) {
//                if (it.isNotEmpty()) {
//                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            addCommentIsLoading.observe(viewLifecycleOwner) {
//                if (it) {
//                    binding.apply {
//                        btnSendComment.visibility = View.GONE
//                        progressBarAddComment.visibility = View.VISIBLE
//                    }
//                } else {
//                    binding.apply {
//                        btnSendComment.visibility = View.VISIBLE
//                        progressBarAddComment.visibility = View.GONE
//                    }
//                }
//            }
//        }
//    }
//
    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@DetailRealCountFragment)
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