package com.test.test.presentation.dashboard.real_count.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.domain.models.Division.SubDistrict
import com.test.test.domain.models.Division.Village
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.division.get_all_district.GetAllSubDistrictUseCase
import com.test.test.domain.use_case.division.get_all_tps.GetAllTpsUseCase
import com.test.test.domain.use_case.division.get_all_village.GetAllVillageUseCase
import com.test.test.domain.use_case.real_count.AddRealCountUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddRealCountViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val addRealCountUseCase: AddRealCountUseCase,
    private val getAllSubDistrictUseCase: GetAllSubDistrictUseCase,
    private val getAllVillageUseCase: GetAllVillageUseCase,
    private val getAllTpsUseCase: GetAllTpsUseCase,
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val success = MutableLiveData(false)

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<UserPref>()

    private val _tps = MutableLiveData<List<String>>()
    val tps: LiveData<List<String>> = _tps

    private val _subDistrict = MutableLiveData<List<SubDistrict>>()
    val subDistrict: LiveData<List<SubDistrict>> = _subDistrict

    private val _selectedSubDistrict = MutableLiveData("Pilih Kecamatan")

    fun setSelectedSubDistrict(value: String) {
        _selectedSubDistrict.value = value
    }

    val village: LiveData<List<Village>> = _selectedSubDistrict.switchMap {
        liveData {
            var village: List<Village> = listOf()
            try {
                if (it != "Pilih Kecamatan") village =
                    getAllVillageUseCase(subDistrict.value?.find { subDistrict -> subDistrict.name == it }?.id.toString())
            } catch (e: Exception) {
                Log.e("#addrealcountvm", e.localizedMessage ?: "Unexpected Error")
            }
            village = listOf(Village(id = "0", name = "Pilih Kelurahan")) + village
            emit(village)
        }
    }


    init {
        viewModelScope.launch() {
            getUserPreferenceUseCase().let {
                _user.value = it
            }

            val token = "Bearer " + _user.value!!.accessToken

            try {
                getAllTpsUseCase(token).tps.let {
                    _tps.value = listOf("Pilih Tps") + it
                }

                getAllSubDistrictUseCase("3471").let {
                    _subDistrict.value = listOf(SubDistrict("0", "Pilih Kecamatan")) + it
                }
            } catch (e: Exception) {
                Log.e("#addrealcountvm", e.localizedMessage ?: "Unexpected Error")
            }

        }
    }

    fun addRealCount(
        image: MultipartBody.Part,
        tps: RequestBody,
        count: RequestBody,
        subDistrict: RequestBody,
        village: RequestBody,
        name: RequestBody,
        voice: RequestBody,
    ) {
        val token = "Bearer " + _user.value?.accessToken!!
        viewModelScope.launch {
            addRealCountUseCase(
                token,
                image,
                tps,
                count,
                subDistrict,
                village,
                name,
                voice
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        success.value = true
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