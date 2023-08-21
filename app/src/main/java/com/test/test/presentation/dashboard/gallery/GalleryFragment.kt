package com.test.test.presentation.dashboard.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.test.test.R
import com.test.test.databinding.FragmentGalleryBinding
import com.test.test.presentation.adapter.GalleryAdapter

class GalleryFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentGalleryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActionListeners()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvGallery.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val urls = listOf(
            "https://images.unsplash.com/photo-1682685794761-c8e7b2347702?ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80",
            "https://images.unsplash.com/photo-1692526665228-ea6533af65ed?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80",
            "https://images.unsplash.com/photo-1692062466883-0638432fe2f2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=723&q=80",
            "https://images.unsplash.com/photo-1692375584849-0b0b1195f878?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80",
            "https://images.unsplash.com/photo-1692317023059-499bf304ef55?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80",
            "https://images.unsplash.com/photo-1692345083308-2ebd7a1063fd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80",
            "https://images.unsplash.com/photo-1692364042693-bd5aed6360d3?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=465&q=80",
            "https://images.unsplash.com/photo-1692027569991-3ca637422603?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=435&q=80",
            "https://images.unsplash.com/photo-1692533050174-a830ddbe455b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80",
        )
        binding.rvGallery.adapter = GalleryAdapter(urls)
    }

    private fun setUpActionListeners() {
        binding.apply {
            btnBack.setOnClickListener(this@GalleryFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }
}