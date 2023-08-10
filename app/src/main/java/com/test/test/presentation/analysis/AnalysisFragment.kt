package com.test.test.presentation.analysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.test.test.R
import com.test.test.databinding.FragmentAnalysisBinding

class AnalysisFragment : Fragment() {

    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLineChartData()
    }

    private fun setLineChartData() {
        val xValue = ArrayList<String>()
        xValue.add("12 July")
        xValue.add("13 July")
        xValue.add("14 July")
        xValue.add("15 July")
        xValue.add("16 July")
        xValue.add("17 July")
        xValue.add("18 July")

        val lineEntry = ArrayList<Entry>()
        lineEntry.add(Entry(2000f, 0))
        lineEntry.add(Entry(500f, 1))
        lineEntry.add(Entry(9000f, 2))
        lineEntry.add(Entry(4000f, 3))
        lineEntry.add(Entry(5000f, 4))
        lineEntry.add(Entry(3000f, 5))
        lineEntry.add(Entry(6000f, 6))

        val lineDataSet = LineDataSet(lineEntry, "Vote")
        lineDataSet.color = resources.getColor(R.color.blue_demography_male)

        lineDataSet.circleRadius = 0f
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillColor = resources.getColor(R.color.blue_demography_female)

        val lineChart = binding.lineChart
        val data = LineData(xValue, lineDataSet)
        lineChart.data = data
//        lineChart.setBackgroundColor(resources.getColor(R.color.white))
        lineChart.animateXY(1000, 1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}