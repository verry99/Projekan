package com.test.test.presentation.dashboard.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.test.test.R
import com.test.test.databinding.FragmentGalleryBinding
import com.test.test.presentation.adapter.GalleryAdapter
import com.test.test.presentation.adapter.LoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentGalleryBinding
    private val viewModel: GalleryViewModel by viewModels()
    private lateinit var adapter: GalleryAdapter

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
        setUpLiveDataObserver()
    }

    private fun setUpLiveDataObserver() {
        viewModel.gallery.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun setUpRecyclerView() {
        binding.rvGallery.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        adapter = GalleryAdapter(childFragmentManager )

        adapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                binding.apply {
                    tvEmpty.visibility = View.VISIBLE
                    rvGallery.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
            } else if (loadState.source.refresh is LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvGallery.visibility = View.GONE
            } else {
                binding.apply {
                    rvGallery.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        }

        binding.rvGallery.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            })
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