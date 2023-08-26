package com.test.test.presentation.analysis.area

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.test.common.Resource
import com.test.test.data.remote.dto.analysis.get_area.AnalysisGetAreaResponse
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponse
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponseItem
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.analysis.GetAnalysisAreaUseCase
import com.test.test.domain.use_case.analysis.GetAnalysisDataByAreaSummaryUseCase
import com.test.test.domain.use_case.analysis.GetAnalysisDataByAreaUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisAreaViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val getAnalysisAreaUseCase: GetAnalysisAreaUseCase,
    private val getAnalysisDataByAreaUseCase: GetAnalysisDataByAreaUseCase,
    private val getAnalysisDataByAreaSummaryUseCase: GetAnalysisDataByAreaSummaryUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()

    private val _selectedAreaType = MutableLiveData<String>()

    fun setSelectedAreaType(input: String) {
        val type = when (input) {
            "Provinsi" -> "province"
            "Kabupaten" -> "regency"
            "Kecamatan" -> "subdistrict"
            "TPS" -> "tps"
            "Desa/Kelurahan" -> "village"
            else -> ""
        }
        _selectedAreaType.value = type
    }

    private val _selectedAreaItem = MutableLiveData<String>()

    private val role: String = state.get<String>("role")!!

    fun setSelectedAreaItem(item: String) {
        if (item != "Pilih Wilayah") {
            _selectedAreaItem.value = item
            getDataSummaryByArea()
        }
    }

    private val _areaSpinner = MutableLiveData<AnalysisGetAreaResponse>()

    val areaSpinnerFiltered: LiveData<List<String>> = _selectedAreaType.switchMap {
        liveData {
            when (it) {
                "province" -> emit(_areaSpinner.value!!.data!!.province)
                "regency" -> emit(_areaSpinner.value!!.data!!.regency)
                "subdistrict" -> emit(_areaSpinner.value!!.data!!.subdistrict)
                "tps" -> emit(_areaSpinner.value!!.data!!.tps)
                "village" -> emit(_areaSpinner.value!!.data!!.village)
            }
        }
    }

    private val _summaryResponse = MutableLiveData<AnalysisDataByAreaResponse>()
    val summaryResponse: LiveData<AnalysisDataByAreaResponse> = _summaryResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().let { userPref ->
                _user.value = userPref
                getAnalysisAreaUseCase("Bearer ${userPref.accessToken}").onEach {
                    when (it) {
                        is Resource.Success -> {
                            _isLoading.value = false
                            _areaSpinner.value = it.data!!
                        }

                        is Resource.Error -> {
                            _isLoading.value = false
                            _errorMessage.value = it.message!!
                        }

                        is Resource.Loading -> {
                            _isLoading.value = true
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    val dataByArea: LiveData<PagingData<AnalysisDataByAreaResponseItem>> =
        _selectedAreaItem.switchMap {
            val token = "Bearer ${_user.value!!.accessToken}"
            var areaName = ""
            var areaType = ""
            try {
                areaName = it
                areaType = _selectedAreaType.value!!
            } catch (e: Exception) {
            }
            return@switchMap getAnalysisDataByAreaUseCase(token, areaName, areaType, role).cachedIn(
                viewModelScope
            )
        }

    private fun getDataSummaryByArea() {
        val areaName = _selectedAreaItem.value!!
        val areaType = _selectedAreaType.value!!
        viewModelScope.launch {
            getAnalysisDataByAreaSummaryUseCase(
                "Bearer ${_user.value!!.accessToken}",
                areaName,
                areaType,
                role
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        _summaryResponse.value = it.data!!
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = it.message!!
                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}

