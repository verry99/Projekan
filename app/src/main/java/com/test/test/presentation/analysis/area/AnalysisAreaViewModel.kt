package com.test.test.presentation.analysis.area

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.data.remote.dto.analysis.get_area.AnalysisGetAreaResponse
import com.test.test.data.remote.dto.analysis.get_item_by_area.AnalysisDataByAreaResponse
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.analysis.GetAnalysisAreaUseCase
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
    private val getAnalysisDataByAreaUseCase: GetAnalysisDataByAreaUseCase
) : ViewModel() {

    private val _user = MutableLiveData<UserPref>()

    private val _selectedAreaType = MutableLiveData<String>()

    private val _selectedAreaItem = MutableLiveData<String>()

    fun setSelectedAreaType(type: String) {
        _selectedAreaType.value = type
    }

    fun setSelectedAreaItem(item: String) {
        if (item != "Pilih Wilayah") {
            _selectedAreaItem.value = item
            getDataByArea()
        }
    }

    val areaToShow: LiveData<List<String>> = _selectedAreaType.switchMap {
        liveData {
            when (it) {
                "Provinsi" -> emit(_areaResponse.value!!.data!!.province)
                "Kabupaten" -> emit(_areaResponse.value!!.data!!.regency)
                "Kecamatan" -> emit(_areaResponse.value!!.data!!.subdistrict)
                "TPS" -> emit(_areaResponse.value!!.data!!.tps)
                "Desa/Kelurahan" -> emit(_areaResponse.value!!.data!!.village)
            }
        }
    }

    private val _areaResponse = MutableLiveData<AnalysisGetAreaResponse>()
    val areaResponse: LiveData<AnalysisGetAreaResponse> = _areaResponse

    private val _areaDataResponse = MutableLiveData<AnalysisDataByAreaResponse>()
    val areaDataResponse: LiveData<AnalysisDataByAreaResponse> = _areaDataResponse

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
                            _areaResponse.value = it.data!!
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

    private fun getDataByArea() {
        val areaName = _selectedAreaItem.value!!
        val areaType = when (_selectedAreaType.value) {
            "Provinsi" -> "province"
            "Kabupaten" -> "regency"
            "Kecamatan" -> "subdistrict"
            "TPS" -> "tps"
            "Desa/Kelurahan" -> "village"
            else -> ""
        }
        viewModelScope.launch {
            getAnalysisDataByAreaUseCase(
                "Bearer ${_user.value!!.accessToken}",
                areaName,
                areaType
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        _areaDataResponse.value = it.data!!
                        Log.e("#aas", it.data.toString())
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

