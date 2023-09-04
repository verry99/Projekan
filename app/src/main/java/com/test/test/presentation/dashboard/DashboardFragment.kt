package com.test.test.presentation.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test.R
import com.test.test.common.Constants
import com.test.test.databinding.FragmentDashboardBinding
import com.test.test.presentation.adapter.NewsAdapter
import com.test.test.presentation.adapter.OpinionAdapter
import com.test.test.presentation.utils.getGreetingText
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@AndroidEntryPoint
class DashboardFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvGreeting.text = getGreetingText(requireContext())

        setUpShimmer()
        setUpActionListener()
        setUpRecyclerView()
        setUpLiveDataObserver()
        binding.carouselView.registerLifecycle(lifecycle)

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS
            ) -> {
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }

    private fun setUpShimmer() {
        binding.apply {
            carouselShimmer.startShimmer()
            rvBeritaShimmer.startShimmer()
            rvOpiniShimmer.startShimmer()
        }
    }

    private fun setUpActionListener() {
        binding.apply {
            btnCekDpt.setOnClickListener(this@DashboardFragment)
            btnNotification.setOnClickListener(this@DashboardFragment)
            btnRelawan.setOnClickListener(this@DashboardFragment)
            btnPendukung.setOnClickListener(this@DashboardFragment)
            btnRealCount.setOnClickListener(this@DashboardFragment)
            btnProfilSbr.setOnClickListener(this@DashboardFragment)
            btnJadwal.setOnClickListener(this@DashboardFragment)
            btnAktifitas.setOnClickListener(this@DashboardFragment)
            btnInteraksi.setOnClickListener(this@DashboardFragment)
            btnGaleri.setOnClickListener(this@DashboardFragment)
            btnKontak.setOnClickListener(this@DashboardFragment)
            tvBeritaSelengkapnya.setOnClickListener(this@DashboardFragment)
            btnOpiniSelengkapnya.setOnClickListener(this@DashboardFragment)
        }
    }

    private fun setUpRecyclerView() {
        binding.apply {
            rvBerita.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            rvOpini.layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically() = false
            }
        }
    }

    private fun setUpLiveDataObserver() {
        viewModel.apply {

            user.observe(viewLifecycleOwner) {
                binding.tvFullName.text = it.name
            }

            notification.observe(viewLifecycleOwner) {
                if (it > 0) {
                    binding.badgeNotif.visibility = View.VISIBLE
                    binding.badgeNotif.text = it.toString()
                } else {
                    binding.badgeNotif.visibility = View.GONE
                }
            }

            banners.observe(viewLifecycleOwner) {
//                if (!it.isNullOrEmpty()) {
//                    val imageList = ArrayList<SlideModel>()
//                    for (banner in it) {
//                        imageList.add(
//                            SlideModel(
//                                Constants.IMAGE_URL + "/" + banner.urlToImage,
//                                banner.title
//                            )
//                        )
//                    }
//
//                    binding.apply {
//                        carouselView.setImageList(imageList, ScaleTypes.CENTER_CROP)
//                        carouselShimmer.stopShimmer()
//                        carouselShimmer.visibility = View.GONE
//                        carouselView.visibility = View.VISIBLE
//                    }
//                } else {
//                    binding.apply {
//                        carouselShimmer.stopShimmer()
//                        carouselShimmer.visibility = View.GONE
//                        carouselView.visibility = View.VISIBLE
//                    }
//                }

                if (!it.isNullOrEmpty()) {
                    val imageList = mutableListOf<CarouselItem>()
                    for (banner in it) {
                        imageList.add(
                            CarouselItem(
                                Constants.IMAGE_URL + "/" + banner.urlToImage,
                                banner.title
                            )
                        )
                    }
                    binding.apply {
                        carouselShimmer.stopShimmer()
                        carouselShimmer.visibility = View.GONE
                        carouselView.setData(imageList)
                        carouselView.visibility = View.VISIBLE
                    }
                } else {
                    binding.apply {
                        carouselShimmer.stopShimmer()
                        carouselShimmer.visibility = View.GONE
                        carouselView.visibility = View.VISIBLE
                    }
                }
            }

            news.observe(viewLifecycleOwner) {
                binding.apply {
                    if (!it.isNullOrEmpty()) {
                        rvBerita.adapter = NewsAdapter(it)
                        rvBeritaShimmer.stopShimmer()
                        rvBeritaShimmer.visibility = View.GONE
                        rvBerita.visibility = View.VISIBLE
                    } else {
                        rvBeritaShimmer.stopShimmer()
                        rvBeritaShimmer.visibility = View.GONE
                    }
                }
            }

            opinion.observe(viewLifecycleOwner) {
                binding.apply {
                    if (!it.isNullOrEmpty()) {
                        rvOpini.adapter = OpinionAdapter(it)
                        rvOpiniShimmer.stopShimmer()
                        rvOpiniShimmer.visibility = View.GONE
                        rvOpini.visibility = View.VISIBLE
                    } else {
                        rvOpiniShimmer.stopShimmer()
                        rvOpiniShimmer.visibility = View.GONE
                    }
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    if (it == "expired") {
                        viewModel.logout()
                        Log.e("#dashfrag", "log out")
                    } else {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            logout.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
                    Toast.makeText(requireContext(), "Please login first!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btn_relawan -> {
                when (viewModel.user.value?.role) {
                    "admin" -> {
                        val action =
                            DashboardFragmentDirections.actionDashboardFragmentToVolunteerFragment(
                                viewModel.user.value!!.accessToken
                            )
                        findNavController().navigate(action)
                    }

                    "volunteer" -> {
                        findNavController().navigate(R.id.action_dashboardFragment_to_volunteerVolunteerFragment)
                    }

                    "user" -> {
                        findNavController().navigate(R.id.action_dashboardFragment_to_userVolunteerFragment)
                    }
                }

            }

            R.id.btn_pendukung -> {
                val action =
                    DashboardFragmentDirections.actionDashboardFragmentToSupporterFragment(
                        viewModel.user.value!!.accessToken,
                        viewModel.user.value!!.role
                    )
                findNavController().navigate(action)
            }

            R.id.btn_notification -> {
                val action =
                    DashboardFragmentDirections.actionDashboardFragmentToNotificationFragment(
                        viewModel.user.value!!.accessToken
                    )
                findNavController().navigate(action)
            }

            R.id.btn_real_count -> {
                val action =
                    DashboardFragmentDirections.actionDashboardFragmentToRealCountFragment(
                        viewModel.user.value!!.accessToken,
                        viewModel.user.value!!.role
                    )
                findNavController().navigate(action)
            }

            R.id.btn_profil_sbr -> findNavController().navigate(R.id.action_dashboardFragment_to_profileSBRFragment)
            R.id.btn_jadwal -> {
                val action =
                    DashboardFragmentDirections.actionDashboardFragmentToScheduleFragment(
                        viewModel.user.value!!.accessToken
                    )
                findNavController().navigate(action)
            }

            R.id.btn_aktifitas -> {
                val action =
                    DashboardFragmentDirections.actionDashboardFragmentToActivitiesFragment(
                        viewModel.user.value!!.accessToken
                    )
                findNavController().navigate(action)
            }

            R.id.btn_interaksi -> {
                val action =
                    DashboardFragmentDirections.actionDashboardFragmentToInteractionFragment(
                        viewModel.user.value!!.accessToken
                    )
                findNavController().navigate(action)
            }

            R.id.btn_galeri -> {
                val action =
                    DashboardFragmentDirections.actionDashboardFragmentToGalleryFragment(
                        viewModel.user.value!!.accessToken
                    )
                findNavController().navigate(action)
            }

            R.id.btn_kontak -> findNavController().navigate(R.id.action_dashboardFragment_to_contactFragment)
            R.id.btn_cek_dpt -> openInChrome()
            R.id.tv_berita_selengkapnya -> {
                val action =
                    DashboardFragmentDirections.actionDashboardFragmentToNewsDashboardFragment(
                        viewModel.user.value!!.accessToken
                    )
                findNavController().navigate(action)
            }

            R.id.btn_opini_selengkapnya -> {
                val action =
                    DashboardFragmentDirections.actionDashboardFragmentToOpinionDashboardFragment(
                        viewModel.user.value!!.accessToken
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun openInChrome() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://cekdptonline.kpu.go.id"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.android.chrome")

        try {
            startActivity(intent)
        } catch (e: Exception) {
            val fallbackIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://cekdptonline.kpu.go.id"))
            startActivity(fallbackIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshUserPreference()
    }
}