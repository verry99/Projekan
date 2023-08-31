package com.test.test.presentation.dashboard.interaction.detail_interaction

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
import com.test.test.presentation.utils.convertToHumanReadableDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailInteractionFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentDetailInteractionBinding
    private val viewModel: DetailInteractionViewModel by viewModels()
    private val adapter = InteractionCommentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailInteractionBinding.inflate(inflater, container, false)

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

        binding.rvComment.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter
            {
                adapter.retry()
            })
    }

    private fun setUpLiveObserver() {
        viewModel.apply {

            isLoading.observe(viewLifecycleOwner) {
                binding.apply {
                    if (it) {
                        progressBar.visibility = View.VISIBLE
                        content.visibility = View.GONE
                    } else {
                        progressBar.visibility = View.GONE
                        content.visibility = View.VISIBLE
                    }
                }
            }

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
                adapter.submitData(lifecycle, it)
            }

            addCommentSuccess.observe(viewLifecycleOwner) {
                if (it) {
                    Toast.makeText(
                        requireContext(),
                        "Berhasil menambahkan komentar.",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.edtComment.setText("")
                    viewModel.fetchComment()
                    comment.observe(viewLifecycleOwner) {
                        adapter.submitData(lifecycle, it)
                    }

                    viewModel.setAddCommentSuccess(false)
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }

            addCommentIsLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.apply {
                        btnSendComment.visibility = View.GONE
                        progressBarAddComment.visibility = View.VISIBLE
                        hideKeyboard()
                    }
                } else {
                    binding.apply {
                        btnSendComment.visibility = View.VISIBLE
                        progressBarAddComment.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.edtComment.windowToken, 0)
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@DetailInteractionFragment)
            btnSendComment.setOnClickListener(this@DetailInteractionFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
            R.id.btn_send_comment -> {
                addComment()
            }
        }
    }

    private fun addComment() {
        val commentBody = binding.edtComment.text.toString()

        if (commentBody.length < 5) {
            Toast.makeText(
                requireContext(),
                "Komen harus berisi minimal 5 karakter",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        viewModel.addComment(commentBody)
    }
}