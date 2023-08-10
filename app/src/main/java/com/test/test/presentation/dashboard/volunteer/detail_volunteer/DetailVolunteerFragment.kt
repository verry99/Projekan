package com.test.test.presentation.dashboard.volunteer.detail_volunteer

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
import com.bumptech.glide.Glide
import com.test.test.R
import com.test.test.common.Constants
import com.test.test.databinding.FragmentDetailVolunteerBinding
import com.test.test.presentation.adapter.AreaAdapter
import com.test.test.presentation.adapter.VolunteerDetailSupporterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class DetailVolunteerFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailVolunteerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailVolunteerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailVolunteerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpChart()
        setUpRecyclerView()
        setUpActionListeners()
        setUpLiveDataObserver()
    }

    private fun setUpChart() {
        showDemographyChart(0, 0, 0)

    }

    private fun setUpRecyclerView() {
        binding.apply {
            rvSupporterNumber.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvSupporter.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setUpLiveDataObserver() {
        viewModel.volunteer.observe(viewLifecycleOwner) {
            binding.apply {
                tvFullName.text = it.name
                tvTotalRecruits.text = it.supporterCount.toString()

                it.profile?.photo?.let {
                    Glide.with(binding.root).load(Constants.IMAGE_URL + it)
                        .into(binding.imgProfile)
                }

                if (it.area.isNullOrEmpty()) {
                    showDemographyChart(0, 0, 0)
                } else {
                    val supporterMaleTotal: Int =
                        it.area.fold(0) { acc, area -> acc + (area?.gender?.l ?: 0) }
                    val supporterFemaleTotal: Int = it.supporterCount?.minus(supporterMaleTotal)!!
                    showDemographyChart(supporterMaleTotal, supporterFemaleTotal, it.supporterCount)

                    rvSupporterNumber.adapter = AreaAdapter(it.area)
                }

                if (!it.supporter.isNullOrEmpty()) {
                    binding.rvSupporter.adapter = VolunteerDetailSupporterAdapter(it.supporter)
                }
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
                        Column(modifier = Modifier.background(Color.White)) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}