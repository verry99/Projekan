package com.test.test.presentation.dashboard.real_count.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.test.databinding.FragmentRealCountResultBinding
import com.test.test.domain.models.Rival
import com.test.test.presentation.adapter.RealCountResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type

@AndroidEntryPoint
class RealCountResultFragment : Fragment() {
    private lateinit var binding: FragmentRealCountResultBinding
    private val args by navArgs<RealCountResultFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRealCountResultBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListener()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvRealCount.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val rivals: String = args.rivals
        val type: Type = object : TypeToken<List<Rival>>() {}.type
        val data: MutableList<Rival> = Gson().fromJson(rivals, type)

        val pdip = data.filter { it.name == "PDI PERJUANGAN" }
        val sortedData = data.filter { it.name != "PDI PERJUANGAN" }.sortedByDescending { it.voice }

        binding.rvRealCount.adapter = RealCountResultAdapter(pdip + sortedData)
    }

    private fun setUpActionListener() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}