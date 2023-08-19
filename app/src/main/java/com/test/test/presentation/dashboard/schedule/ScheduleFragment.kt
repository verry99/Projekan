package com.test.test.presentation.dashboard.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.test.test.R
import com.test.test.databinding.FragmentScheduleBinding
import com.test.test.presentation.adapter.ScheduleViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentScheduleBinding
    private val args: ScheduleFragmentArgs by navArgs()
    private val viewModel: ScheduleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setToken(args.token)
        viewModel.fetchSchedules()
        setUpActionListener()
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ScheduleViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        val tabsArray = arrayOf("Semua", "Aktif", "Berakhir")

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabsArray[position]
        }.attach()
    }

    private fun setUpActionListener() {
        binding.apply {
            btnBack.setOnClickListener(this@ScheduleFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }
}