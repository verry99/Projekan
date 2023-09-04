package com.test.test.presentation.dashboard.real_count.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.test.test.R
import com.test.test.databinding.FragmentPopupProofImageBinding

class PopUpProofImageFragment : DialogFragment() {

    private lateinit var binding: FragmentPopupProofImageBinding

    override fun getTheme(): Int = R.style.NoMarginsDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopupProofImageBinding.inflate(inflater, container, false)

        setupWidthToMatchParent()

        return binding.root
    }

    private fun setupWidthToMatchParent() {
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUrl = arguments?.getString(IMG_URL)
        val desc = arguments?.getString(DESC) ?: ""

        if (imageUrl!!.endsWith(".mp4")) {
            binding.img.visibility = View.GONE
            setUpExoPlayer(imageUrl)
        } else {
            binding.playerView.visibility = View.GONE
            Glide.with(requireContext()).load(imageUrl).into(binding.img)
        }
        binding.tvDescription.text = desc
    }

    private fun setUpExoPlayer(videoUri: String) {
        val player = SimpleExoPlayer.Builder(requireContext()).build()

        val playerView = binding.playerView
        playerView.player = player

        val mediaItem = MediaItem.fromUri(videoUri)
        player.setMediaItem(mediaItem)

        player.prepare()
        player.play()
    }

    companion object {
        private const val IMG_URL = "imgUrl"
        private const val DESC = "description"

        fun newInstance(message: String, description: String?): PopUpProofImageFragment {
            val args = Bundle()
            args.putString(IMG_URL, message)
            args.putString(DESC, description)
            val fragment = PopUpProofImageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}