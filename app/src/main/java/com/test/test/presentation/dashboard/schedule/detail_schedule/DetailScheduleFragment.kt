package com.test.test.presentation.dashboard.schedule.detail_schedule

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
import com.test.test.common.Constants
import com.test.test.databinding.FragmentDetailScheduleBinding
import com.test.test.presentation.utils.convertToFullDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailScheduleFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentDetailScheduleBinding
    private val viewModel: DetailScheduleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailScheduleBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpLiveObserver()
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

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }

            schedule.observe(viewLifecycleOwner) { response ->
                response.data?.let {
                    binding.apply {
                        tvTitle.text = it.title
                        tvDescription.text = it.description
                        tvLocationDescription.text = it.location

                        try {
                            tvMulaiDesc.text = convertToFullDate(it.startDate!!)
                            tvSelesaiDesc.text = convertToFullDate(it.endDate!!)
                        } catch (e: Exception) {
                            tvMulaiDesc.text = convertToFullDate(it.startDate!!)
                            tvSelesaiDesc.text = convertToFullDate(it.endDate!!)
                        }

                        Glide.with(requireContext()).load(Constants.IMAGE_URL + "/" + it.image)
                            .into(imgJadwal)
                    }
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
}