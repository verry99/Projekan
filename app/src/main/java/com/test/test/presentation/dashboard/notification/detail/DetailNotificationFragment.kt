package com.test.test.presentation.dashboard.notification.detail

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
import com.test.test.databinding.FragmentDetailNotificationBinding
import com.test.test.presentation.utils.convertToFullDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailNotificationFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentDetailNotificationBinding
    private val viewModel: DetailNotificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNotificationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpLiveDataObserver()
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {
            notification.observe(viewLifecycleOwner) {
                it?.data?.let { data ->
                    data.image?.let {
                        binding.cardContainer.visibility = View.VISIBLE
                        Glide.with(requireContext())
                            .load(data.image)
                            .into(binding.img)
                    }

                    binding.apply {
                        tvTitle.text = data.title

                        try {
                            tvDate.text = convertToFullDate(data.createdAt!!)
                        } catch (e: Exception) {
                            tvDate.text = data.createdAt
                        }

                        tvPesan.text = data.message
                    }
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