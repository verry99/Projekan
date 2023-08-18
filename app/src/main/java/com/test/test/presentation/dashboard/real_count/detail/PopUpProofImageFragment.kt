package com.test.test.presentation.dashboard.real_count.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.test.test.R

class PopUpProofImageFragment : DialogFragment() {
    private lateinit var imageview: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_popup_proof_image, container, false)
        imageview = view.findViewById<ImageView>(R.id.img)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUrl = arguments?.getString(ARG_MESSAGE)
        Glide.with(requireContext()).load(imageUrl).into(imageview)
    }

    companion object {
        private const val ARG_MESSAGE = "arg_message"

        fun newInstance(message: String): PopUpProofImageFragment {
            val args = Bundle()
            args.putString(ARG_MESSAGE, message)
            val fragment = PopUpProofImageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}