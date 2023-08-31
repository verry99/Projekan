package com.test.test.presentation.analysis

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.test.test.R
import com.test.test.databinding.FragmentAnalysisBinding
import com.test.test.presentation.adapter.AreaAdapter
import com.test.test.presentation.utils.formatNumber
import com.test.test.presentation.utils.getStringPercentage
import com.test.test.presentation.utils.roundTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentAnalysisBinding
    private val viewModel: AnalysisViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalysisBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListener()
        setUpRecyclerView()
        setUpLiveDataObserver()
        showDemographyChart(0, 0)
        setUpSwipeRefresh()
    }

    private fun setUpSwipeRefresh() {
        binding.swipeToRefresh.apply {
            setOnRefreshListener {
                viewModel.fetchAnalysisData()
                isRefreshing = false
            }
        }
    }

    private fun setUpActionListener() {
        binding.apply {
            tvTableSelengkapnya.setOnClickListener(this@AnalysisFragment)
            tvRelawanSelengkapnya.setOnClickListener(this@AnalysisFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_table_selengkapnya -> {
                val action =
                    AnalysisFragmentDirections.actionAnalysisFragmentToAnalysisRegionFragment("supporter")
                findNavController().navigate(action)
            }

            R.id.tv_relawan_selengkapnya -> {
                val action =
                    AnalysisFragmentDirections.actionAnalysisFragmentToAnalysisRegionFragment("volunteer")
                findNavController().navigate(action)
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.apply {
            rvSupporterNumber.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvSupporterNumberRelawan.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpLiveDataObserver() {

        viewModel.apply {

            isLoading.observe(viewLifecycleOwner) {
                binding.apply {
                    if (it) {
                        analysisShimmer.apply {
                            visibility = View.VISIBLE
                            startShimmer()
                        }
                        container.visibility = View.GONE
                    } else {
                        analysisShimmer.apply {
                            visibility = View.GONE
                            stopShimmer()
                        }
                        container.visibility = View.VISIBLE
                    }
                }
            }

            analysisData.observe(viewLifecycleOwner) { response ->
                response.data?.let { data ->
                    binding.apply {
                        tvTotalNumber.text = formatNumber(data.totalSupporter!!.toLong())
                        tvSupporterGrowthPercentage.text =
                            "${data.percentageSupporter.toString()}%"
                        tvSupporterGrowthNumber.text =
                            "${formatNumber(data.growthSupporter!!.toLong())} dukungan"
                    }

                    data.genderSupporter?.let { gender ->
                        showDemographyChart(
                            gender.l?.toInt() ?: 0,
                            gender.p?.toInt() ?: 0
                        )
                    }

                    data.area?.let {
                        val supporterMaleTotal: Int =
                            it.fold(0) { acc, area -> acc + (area?.gender?.l ?: 0) }
                        val supporterFemaleTotal: Int =
                            it.fold(0) { acc, area -> acc + (area?.gender?.p ?: 0) }
                        val supporterTotal = supporterMaleTotal + supporterFemaleTotal

                        binding.apply {
                            tableLastItem.visibility = View.VISIBLE
                            rvSupporterNumber.visibility = View.VISIBLE
                            rvSupporterNumber.adapter = AreaAdapter(it)
                            tvTotalSupporterNumberMale.text =
                                formatNumber(supporterMaleTotal.toLong())
                            tvTotalSupporterNumberFemale.text =
                                formatNumber(supporterFemaleTotal.toLong())
                            tvTotalAllSupporterNumber.text = formatNumber(supporterTotal.toLong())
                        }
                    }

                    data.areaVolunteer?.let {
                        val supporterMaleTotal: Int =
                            it.fold(0) { acc, area -> acc + (area?.gender?.l ?: 0) }
                        val supporterFemaleTotal: Int =
                            it.fold(0) { acc, area -> acc + (area?.gender?.p ?: 0) }
                        val supporterTotal = supporterMaleTotal + supporterFemaleTotal

                        binding.apply {
                            tableLastItemRelawan.visibility = View.VISIBLE
                            rvSupporterNumberRelawan.visibility = View.VISIBLE
                            rvSupporterNumberRelawan.adapter = AreaAdapter(it)
                            tvTotalSupporterNumberMaleRelawan.text =
                                formatNumber(supporterMaleTotal.toLong())
                            tvTotalSupporterNumberFemaleRelawan.text =
                                formatNumber(supporterFemaleTotal.toLong())
                            tvTotalAllSupporterNumberRelawan.text =
                                formatNumber(supporterTotal.toLong())
                        }
                    }

                    data.supporter?.let {
                        if (it.xValues.isNotEmpty() && it.yValues.isNotEmpty()) {
                            val xValues = it.xValues
                            val yValues = it.yValues.map { yValue -> yValue.toFloat() }
                            setUpLineChartSupporter(xValues, yValues)
                        }
                    }

                    data.volunteer?.let {
                        if (it.xValues.isNotEmpty() && it.yValues.isNotEmpty()) {
                            val xValues = it.xValues
                            val yValues = it.yValues.map { yValue -> yValue.toFloat() }
                            setUpLineChartVolunteer(xValues, yValues)
                        }
                    }

                    data.age?.let {
                        val xValues =
                            listOf(
                                "<20",
                                "20-29",
                                "30-39",
                                "40-49",
                                "50-59",
                                "60-69",
                                "70-79",
                                "80>"
                            )
                        val yMaleValues = listOf(
                            it.x20.l!!.toFloat(),
                            it.x2029.l!!.toFloat(),
                            it.x3039.l!!.toFloat(),
                            it.x4049.l!!.toFloat(),
                            it.x5059.l!!.toFloat(),
                            it.x6069.l!!.toFloat(),
                            it.x7079.l!!.toFloat(),
                            it.x80.l!!.toFloat()
                        )
                        val yFemaleValues = listOf(
                            it.x20.p!!.toFloat(),
                            it.x2029.p!!.toFloat(),
                            it.x3039.p!!.toFloat(),
                            it.x4049.p!!.toFloat(),
                            it.x5059.p!!.toFloat(),
                            it.x6069.p!!.toFloat(),
                            it.x7079.p!!.toFloat(),
                            it.x80.p!!.toFloat()
                        )
                        setUpBarChart(xValues, yMaleValues, yFemaleValues)
                    }
                }
            }
        }
    }

    private fun showDemographyChart(
        supporterMaleTotal: Int,
        supporterFemaleTotal: Int
    ) {
        binding.myComposable.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    Surface {
                        Column(modifier = Modifier.background(Color.White)) {
                            DemographyChart(
                                supporterMaleTotal,
                                supporterFemaleTotal,
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun DemographyChart(
        supporterMaleTotal: Int,
        supporterFemaleTotal: Int,
    ) {

        val supporterTotal = supporterMaleTotal + supporterFemaleTotal
        var supporterMalePercentage = 0.0
        var supporterFemalePercentage = 0.0

        if (supporterTotal != 0) {
            supporterMalePercentage =
                (supporterMaleTotal.toDouble() / supporterTotal.toDouble()).roundTo(4)
            supporterFemalePercentage =
                (supporterFemaleTotal.toDouble() / supporterTotal.toDouble()).roundTo(4)
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            0F to Color(0x990092e4),
                            supporterMalePercentage.toFloat() to Color(
                                0x520069e4
                            )
                        )
                    )
                    .padding(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.img_demography_male),
                        "Gambar Demografi Laki-laki",
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(50.dp)
                    )
                    Column {
                        Text(
                            text = getStringPercentage(supporterMalePercentage)
                        )
                        Text(
                            text = formatNumber(supporterMaleTotal.toLong()),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(
                            text = getStringPercentage(supporterFemalePercentage)
                        )
                        Text(
                            text = formatNumber(supporterFemaleTotal.toLong()),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    Image(
                        painterResource(R.drawable.img_demography_female),
                        "Gambar Demografi Perempuan",
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(50.dp)
                    )
                }
            }
        }
    }

    private fun setUpBarChart(
        xValues: List<String>,
        yMaleValues: List<Float>,
        yFemaleValues: List<Float>
    ) {

        val barChart = binding.barChart
        barChart.setDrawGridBackground(false)
        barChart.axisRight.setDrawLabels(false)
        barChart.description.isEnabled = false
        barChart.isScaleXEnabled = true
        barChart.isDragEnabled = true
        barChart.setVisibleXRangeMaximum(3F)

        val dataValues = ArrayList<BarEntry>()
        for ((index, value) in yMaleValues.withIndex()) {
            dataValues.add(BarEntry(index.toFloat(), value))
        }

        val dataValues2 = ArrayList<BarEntry>()
        for ((index, value) in yFemaleValues.withIndex()) {
            dataValues2.add(BarEntry(index.toFloat(), value))
        }

        val xAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setCenterAxisLabels(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true

        val yAxis = barChart.axisLeft
        yAxis.setDrawGridLines(false)
        yAxis.axisMinimum = (yMaleValues + yFemaleValues).min()
        yAxis.axisMaximum = (yMaleValues + yFemaleValues).max()
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = resources.getColor(R.color.red)
        yAxis.labelCount = 6

        val dataSet1 = BarDataSet(dataValues, "Laki-laki")
        dataSet1.color = android.graphics.Color.parseColor("#C21010")
        val dataSet2 = BarDataSet(dataValues2, "Perempuan")
        dataSet2.color = android.graphics.Color.parseColor("#5A5A5A")

        val barData = BarData(dataSet1, dataSet2)
        val barSpace = 0.1f
        val groupSpace = 0.4f

        barData.barWidth = .2f
        barChart.data = barData
        xAxis.axisMinimum = 0f
        xAxis.axisMaximum =
            (0 + barChart.barData.getGroupWidth(groupSpace, barSpace) * xValues.size)
        barChart.axisLeft.axisMinimum = 0f
        barChart.groupBars(0f, groupSpace, barSpace)
        barChart.setFitBars(true)
        barChart.animateY(1000)
        barChart.invalidate()
    }

    private fun setUpLineChartSupporter(xValues: List<String>, yValues: List<Float>) {

        val lineChart = binding.lineChartSupporter

        val description = Description()
        description.text = ""
        lineChart.description = description
        lineChart.axisRight.setDrawLabels(false)

        val xAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
        xAxis.spaceMin = 0.1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelCount = xValues.size
        xAxis.granularity = 1f

        val yAxis = lineChart.axisLeft
        yAxis.setDrawGridLines(false)
        yAxis.axisMinimum = yValues.min()
        yAxis.axisMaximum = yValues.max()
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = resources.getColor(R.color.red)
        yAxis.labelCount = 6

        val lineEntry = ArrayList<Entry>()
        for ((i, value) in yValues.withIndex()) {
            lineEntry.add(Entry(i.toFloat(), value))
        }

        val lineDataSet = LineDataSet(lineEntry, "Pendukung")
        lineDataSet.color = resources.getColor(R.color.blue_demography_male)
        lineDataSet.circleRadius = 0f
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillColor = resources.getColor(R.color.light_blue)

        val data = LineData(lineDataSet)
        lineChart.data = data
        lineChart.animateXY(1000, 1000)
    }

    private fun setUpLineChartVolunteer(xValues: List<String>, yValues: List<Float>) {

        val lineChart = binding.lineChartVolunteer
        lineChart.setDrawGridBackground(false)

        val description = Description()
        description.text = ""
        lineChart.description = description
        lineChart.axisRight.setDrawLabels(false)

        val xAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.spaceMin = 0.1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
        xAxis.labelCount = xValues.size
        xAxis.granularity = 1f

        val yAxis = lineChart.axisLeft
        yAxis.setDrawGridLines(false)
        yAxis.axisMinimum = yValues.min()
        yAxis.axisMaximum = yValues.max()
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = resources.getColor(R.color.red)
        yAxis.labelCount = 6

        val lineEntry = ArrayList<Entry>()
        for ((i, value) in yValues.withIndex()) {
            lineEntry.add(Entry(i.toFloat(), value))
        }

        val lineDataSet = LineDataSet(lineEntry, "Relawan")
        lineDataSet.color = resources.getColor(R.color.blue_demography_male)
        lineDataSet.circleRadius = 0f
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillColor = resources.getColor(R.color.light_blue)

        val data = LineData(lineDataSet)
        lineChart.data = data
        lineChart.animateXY(1000, 1000)
    }
}