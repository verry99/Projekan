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
import com.test.test.R
import com.test.test.databinding.FragmentDetailVolunteerBinding
import com.test.test.presentation.dashboard.adapter.SupporterNumberAdapter
import dagger.hilt.android.AndroidEntryPoint

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

        setUpRecyclerView()
        setUpActionListeners()
        setUpLiveDataObserver()
        setUpGradientView(.35f)
    }

    private fun setUpRecyclerView() {
        binding.rvTableSupporter.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpGradientView(secondColorStart: Float) {
        binding.myComposable.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    Surface {
                        Column(modifier = Modifier.background(Color.White)) {
                            DemographyChart(secondColorStart = secondColorStart)
                        }
                    }
                }
            }
        }
    }

//    @Composable
//    private fun MyTable() {
//        LazyColumn(
//            content = {
//                item {
//                    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
//                        Column(
//                            modifier = Modifier
//                                .weight(1f)
//                                .fillMaxHeight()
//                                .background(Color(0, 105, 228, 0x52)),
//                            verticalArrangement = Arrangement.Center,
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Text(
//                                text = "Nama Wilayah",
//                                textAlign = TextAlign.Center,
//                            )
//                        }
//                        Column(modifier = Modifier.weight(2f)) {
//                            Text(
//                                text = "Dukungan",
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .background(Color(0, 105, 228, 0x52)),
//                                textAlign = TextAlign.Center,
//                            )
//                            Row(modifier = Modifier.background(Color(0, 105, 228, 0x77))) {
//                                Text(
//                                    text = "L",
//                                    textAlign = TextAlign.Center,
//                                    modifier = Modifier.weight(1f)
//                                )
//                                Text(
//                                    text = "P",
//                                    textAlign = TextAlign.Center,
//                                    modifier = Modifier.weight(1f)
//                                )
//                                Text(
//                                    text = "Total",
//                                    textAlign = TextAlign.Center,
//                                    modifier = Modifier.weight(1f)
//                                )
//                            }
//                        }
//                    }
//                }
//            }, modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .background(color = Color.White)
//        )
//    }
//
//    @Preview()
//    @Composable
//    private fun Chiki() {
//        MyTable()
//    }

    private fun setUpLiveDataObserver() {
        viewModel.provinceSupporterNumber.observe(viewLifecycleOwner) {
            binding.rvTableSupporter.adapter = SupporterNumberAdapter(it)
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

    @Composable
    fun DemographyChart(secondColorStart: Float) {
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
                            secondColorStart to Color(0x520069e4)
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
                        Text(text = "35%")
                        Text(text = "2030", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(text = "65%")
                        Text(text = "3770", fontWeight = FontWeight.Bold, fontSize = 20.sp)
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