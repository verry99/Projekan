package com.test.test.presentation.dashboard.real_count.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.test.test.R
import com.test.test.common.Constants.IMAGE_URL
import com.test.test.databinding.FragmentDetailRealCountBinding
import com.test.test.presentation.adapter.RivalAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRealCountFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailRealCountBinding? = null
    private val viewModel: DetailRealCountViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var imageUrl: String

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

        setUpRecyclerView()
        setUpActionListeners()
        setUpLiveObserver()
    }


    private fun setUpRecyclerView() {
        binding.rvSuaraRival.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
    }

    private fun setUpLiveObserver() {
        viewModel.apply {

            response.observe(viewLifecycleOwner) {
                binding.apply {
                    it.data!!.image?.let {
                        imageUrl = "$IMAGE_URL/$it"
                        Glide.with(requireContext()).load(imageUrl).into(imgLampiran)
                        imgLampiran.setOnClickListener(this@DetailRealCountFragment)
                    }

                    tvSuaraSbr.text = it.data.count
                    tvTps.text = it.data.tps
                    tvKecamatan.text = it.data.subdistrict
                    tvDesa.text = it.data.vilage
                    tvTotal.text = it.data.rivals.size.toString()

                    binding.rvSuaraRival.adapter = RivalAdapter(it.data.rivals)
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }

            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                    }
                } else {
                    binding.apply {
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@DetailRealCountFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
            R.id.img_lampiran -> {

                val popupFragment = PopUpProofImageFragment.newInstance(imageUrl)
                popupFragment.show(requireActivity().supportFragmentManager, "my_popup")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}