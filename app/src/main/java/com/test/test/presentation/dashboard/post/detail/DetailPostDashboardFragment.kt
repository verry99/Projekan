package com.test.test.presentation.dashboard.post.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.test.test.R
import com.test.test.databinding.FragmentDetailPostDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPostDashboardFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentDetailPostDashboardBinding
    private val viewModel: DetailPostDashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailPostDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpLiveDataObserver()
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {
            post.observe(viewLifecycleOwner) {
                Glide.with(requireContext())
                    .load(it.bannerUrl)
                    .into(binding.imagePost)

                binding.apply {
                    tvDate.text = it.humanReadableDate
                    tvTitle.text = it.title

                    webView.loadData(it.content!!, "text/html", "UTF-8")
                }
            }

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

            errorMessage.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpActionListeners() {
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }
}