package com.test.test.presentation.dashboard.activities.detail

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
import com.test.test.databinding.FragmentDetailActivitiesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivitiesFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailActivitiesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailActivitiesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailActivitiesBinding.inflate(inflater, container, false)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}