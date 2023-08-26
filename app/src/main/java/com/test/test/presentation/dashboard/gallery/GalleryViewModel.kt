package com.test.test.presentation.dashboard.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.test.test.domain.use_case.gallery.GetAllGalleryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getAllGalleryUseCase: GetAllGalleryUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    val token = "Bearer " + state.get<String>("token")!!

    var gallery = getAllGalleryUseCase(token).cachedIn(viewModelScope)

}