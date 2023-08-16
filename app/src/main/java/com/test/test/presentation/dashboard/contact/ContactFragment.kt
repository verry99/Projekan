package com.test.test.presentation.dashboard.contact

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.test.test.R
import com.test.test.databinding.FragmentContactBinding

class ContactFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@ContactFragment)
            btnInstagram.setOnClickListener(this@ContactFragment)
            btnYoutube.setOnClickListener(this@ContactFragment)
            btnFacebook.setOnClickListener(this@ContactFragment)
            btnWebsite.setOnClickListener(this@ContactFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()

            R.id.btn_instagram -> {
                val username = "susanto_sbr" // Replace with the Instagram username you want to open

                val uri = Uri.parse("http://instagram.com/_u/$username")

                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.instagram.android")

                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    val webUri = Uri.parse("http://instagram.com/$username")
                    val webIntent = Intent(Intent.ACTION_VIEW, webUri)

                    try {
                        startActivity(webIntent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            requireContext(),
                            "Tidak dapat membuka Instagram",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            R.id.btn_facebook -> {
                val facebookUsername = "susanto.budiraharjo"

                val uri = Uri.parse("https://www.facebook.com/$facebookUsername")

                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.facebook.katana")

                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    val webUri = Uri.parse("https://www.facebook.com/$facebookUsername")
                    val webIntent = Intent(Intent.ACTION_VIEW, webUri)

                    try {
                        startActivity(webIntent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            requireContext(),
                            "Tidak dapat membuka Youtube",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            R.id.btn_youtube -> {
                val youtubeChannelId = "susantobudiraharjo"

                val uri = Uri.parse("https://www.youtube.com/@$youtubeChannelId")

                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.youtube")

                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    val webUri = Uri.parse("https://www.youtube.com/@$youtubeChannelId")
                    val webIntent = Intent(Intent.ACTION_VIEW, webUri)

                    try {
                        startActivity(webIntent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            requireContext(),
                            "Tidak dapat membuka Youtube",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            R.id.btn_website -> {
                val webUri = Uri.parse("https://www.susantobudiraharjo.com")
                val webIntent = Intent(Intent.ACTION_VIEW, webUri)

                try {
                    startActivity(webIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        requireContext(),
                        "Tidak dapat membuka Website",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}