package com.test.test.presentation.analysis

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
import kotlin.math.round

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

        setUpChartGenderDemography()
        setUpLineChartSupporter()
        setUpLineChartVolunteer()
        setUpBarChart()
    }

    private fun setUpChartGenderDemography() {
        showDemographyChart(22, 44, 66)
    }

    private fun showDemographyChart(
        supporterMaleTotal: Int,
        supporterFemaleTotal: Int,
        supporterTotal: Int
    ) {
        binding.myComposable.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    Surface {
                        Column(modifier = Modifier.background(androidx.compose.ui.graphics.Color.White)) {
                            DemographyChart(
                                supporterMaleTotal,
                                supporterFemaleTotal,
                                supporterTotal
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
        supporterTotal: Int
    ) {
        fun Double.roundTo(decimals: Int): Double {
            var multiplier = 1.0
            repeat(decimals) { multiplier *= 10 }
            return round(this * multiplier) / multiplier
        }

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
                        Text(text = supporterMalePercentage.times(100).toString() + "%")
                        Text(
                            text = supporterMaleTotal.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(
                            text = supporterFemalePercentage.times(100).toString() + "%"
                        )
                        Text(
                            text = supporterFemaleTotal.toString(),
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

    private fun setUpBarChart() {

        val barChart = binding.barChart
        barChart.setDrawGridBackground(false)
        barChart.axisRight.setDrawLabels(false)
        barChart.description.isEnabled = false

        val dataValues = ArrayList<BarEntry>()
        dataValues.add(BarEntry(0f, 10f))
        dataValues.add(BarEntry(1f, 20f))
        dataValues.add(BarEntry(2f, 30f))
        dataValues.add(BarEntry(3f, 40f))
        dataValues.add(BarEntry(4f, 50f))

        val dataValues2 = ArrayList<BarEntry>()
        dataValues2.add(BarEntry(0f, 10f))
        dataValues2.add(BarEntry(1f, 15f))
        dataValues2.add(BarEntry(2f, 25f))
        dataValues2.add(BarEntry(3f, 35f))
        dataValues2.add(BarEntry(4f, 45f))

        val labels = listOf("0-20", "20-35", "35-40", "45-55", "55-..")
        val xAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.isGranularityEnabled = true

        val yAxis = barChart.axisLeft
        yAxis.setDrawGridLines(false)
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 50f
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = resources.getColor(R.color.red)
        yAxis.labelCount = 10

        val dataSet1 = BarDataSet(dataValues, "Laki-laki")
        dataSet1.color = R.color.red
        val dataSet2 = BarDataSet(dataValues2, "Perempuan")
        dataSet2.color = R.color.blue_demography_male

        val barData = BarData(dataSet1, dataSet2)
        barChart.data = barData
        barData.barWidth = .3f

        val barSpace = 0.1f
        val groupSpace = 0.2f
        barChart.groupBars(0f, groupSpace, barSpace)
        barChart.setFitBars(true);
        barChart.animateY(1000)
        barChart.invalidate()
    }

    private fun setUpLineChartSupporter() {

        val lineChart = binding.lineChartSupporter

        val description = Description()
        description.text = ""
        lineChart.description = description
        lineChart.axisRight.setDrawLabels(false)

        val xValues = listOf("12/08", "13/08", "14/08", "15/08", "16/08", "17/08", "18/08")

        val xAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
        xAxis.labelCount = xValues.size
        xAxis.granularity = 1f

        val yAxis = lineChart.axisLeft
        yAxis.setDrawGridLines(false)
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 100f
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = resources.getColor(R.color.red)
        yAxis.labelCount = 10

        val lineEntry = ArrayList<Entry>()
        lineEntry.add(Entry(0F, 0F))
        lineEntry.add(Entry(1F, 15F))
        lineEntry.add(Entry(2F, 24F))
        lineEntry.add(Entry(3F, 37f))
        lineEntry.add(Entry(4F, 41f))
        lineEntry.add(Entry(5F, 66f))
        lineEntry.add(Entry(6F, 99f))

        val lineDataSet = LineDataSet(lineEntry, "Pendukung")
        lineDataSet.color = resources.getColor(R.color.blue_demography_male)
        lineDataSet.circleRadius = 0f
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillColor = resources.getColor(R.color.light_blue)

        val data = LineData(lineDataSet)
        lineChart.data = data
        lineChart.animateXY(1000, 1000)
    }

    private fun setUpLineChartVolunteer() {

        val lineChart = binding.lineChartVolunteer

        lineChart.setDrawGridBackground(false)

        val description = Description()
        description.text = ""
        lineChart.description = description
        lineChart.axisRight.setDrawLabels(false)

        val xValues = listOf("12/08", "13/08", "14/08", "15/08", "16/08", "17/08", "18/08")

        val xAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
        xAxis.labelCount = xValues.size
        xAxis.granularity = 1f

        val yAxis = lineChart.axisLeft
        yAxis.setDrawGridLines(false)
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 100f
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = resources.getColor(R.color.red)
        yAxis.labelCount = 10

        val lineEntry = ArrayList<Entry>()
        lineEntry.add(Entry(0F, 0F))
        lineEntry.add(Entry(1F, 15F))
        lineEntry.add(Entry(2F, 24F))
        lineEntry.add(Entry(3F, 37f))
        lineEntry.add(Entry(4F, 41f))
        lineEntry.add(Entry(5F, 66f))
        lineEntry.add(Entry(6F, 99f))

        val lineDataSet = LineDataSet(lineEntry, "Relawan")
        lineDataSet.color = resources.getColor(R.color.blue_demography_male)
        lineDataSet.circleRadius = 0f
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillColor = resources.getColor(R.color.light_blue)

        val data = LineData(lineDataSet)
        lineChart.data = data
        lineChart.animateXY(1000, 1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}