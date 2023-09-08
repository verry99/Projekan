package com.test.test.presentation.dashboard.volunteer.view_profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.data.remote.dto.volunteer.detail_volunteer.Volunteer
import com.test.test.domain.models.Division.Province
import com.test.test.domain.models.Division.Regency
import com.test.test.domain.models.Division.SubDistrict
import com.test.test.domain.models.Division.Village
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.division.get_all_district.GetAllSubDistrictUseCase
import com.test.test.domain.use_case.division.get_all_province.GetAllProvinceUseCase
import com.test.test.domain.use_case.division.get_all_regency.GetAllRegencyUseCase
import com.test.test.domain.use_case.division.get_all_village.GetAllVillageUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import com.test.test.domain.use_case.volunteer.get_detail_volunteer.GetDetailVolunteerUseCase
import com.test.test.domain.use_case.volunteer.update_volunteer.UpdateVolunteerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class ViewVolunteerViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val updateVolunteerUseCase: UpdateVolunteerUseCase,
    private val getDetailVolunteerUseCase: GetDetailVolunteerUseCase,
    private val getAllProvinceUseCase: GetAllProvinceUseCase,
    private val getAllRegencyUseCase: GetAllRegencyUseCase,
    private val getAllSubDistrictUseCase: GetAllSubDistrictUseCase,
    private val getAllVillageUseCase: GetAllVillageUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _volunteer = MutableLiveData<Volunteer>()
    val volunteer: LiveData<Volunteer> = _volunteer

    private val id = state.get<String>("id")

    private val _province = MutableLiveData<List<Province>>()
    val province: LiveData<List<Province>> = _province

    private val _selectedProvince = MutableLiveData<String>()
    private val _selectedRegency = MutableLiveData<String>()
    private val _selectedDistrict = MutableLiveData<String>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val success = MutableLiveData(false)

    private val _user = MutableLiveData<UserPref>()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSelectedProvince(provinceName: String) {
        _selectedProvince.value = provinceName
    }

    fun setSelectedRegency(regencyName: String) {
        _selectedRegency.value = regencyName
    }

    fun setSelectedDistrict(districtName: String) {
        _selectedDistrict.value = districtName
    }

    val regency: LiveData<List<Regency>> = _selectedProvince.switchMap {
        liveData {
            var regency: List<Regency> = listOf()
            try {
                if (it != "Pilih Provinsi") regency =
                    getAllRegencyUseCase(_province.value?.find { province -> province.name == it }?.id.toString())
            } catch (e: UnknownHostException) {
                Log.e("#edt", "error getting regency")
            }
            regency = listOf(Regency(id = "0", name = "Pilih Kabupaten")) + regency
            emit(regency)
        }
    }

    val subDistrict: LiveData<List<SubDistrict>> = _selectedRegency.switchMap {
        liveData {
            var subDistrict: List<SubDistrict> = listOf()
            if (it != "Pilih Kabupaten") subDistrict =
                getAllSubDistrictUseCase(regency.value?.find { regency -> regency.name == it }?.id.toString())
            subDistrict = listOf(SubDistrict(id = "0", name = "Pilih Kecamatan")) + subDistrict
            emit(subDistrict)
        }
    }

    val village: LiveData<List<Village>> = _selectedDistrict.switchMap {
        liveData {
            var village: List<Village> = listOf()
            if (it != "Pilih Kecamatan") village =
                getAllVillageUseCase(subDistrict.value?.find { district -> district.name == it }?.id.toString())
            village = listOf(Village(id = "0", name = "Pilih Desa")) + village
            emit(village)
        }
    }

    init {
        viewModelScope.launch {
            val province =
                withContext(Dispatchers.IO) {
                    try {
                        getAllProvinceUseCase()
                    } catch (e: UnknownHostException) {
                        emptyList()
                    }
                }

            _province.value = listOf(Province(id = "0", "Pilih Provinsi")) + province

            getUserPreferenceUseCase().let {
                _user.value = it
            }

            getDetailVolunteerUseCase(
                "Bearer ${_user.value!!.accessToken}",
                id!!.toInt()
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        _volunteer.value = it.data!!.volunteer!!
                        Log.e("#viewvolvm", "${it.data.volunteer}")
                    }

                    is Resource.Error -> {
                        _isLoading.value = false

                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun updateVolunteer(
        photo: MultipartBody.Part?,
        nik: RequestBody,
        name: RequestBody,
        phone: RequestBody,
        email: RequestBody,
        birthPlace: RequestBody,
        birthDate: RequestBody,
        gender: RequestBody,
        address: RequestBody,
        rt: RequestBody,
        rw: RequestBody,
        tps: RequestBody,
        province: RequestBody,
        regency: RequestBody,
        subDistrict: RequestBody,
        village: RequestBody,
        religion: RequestBody,
        maritalStatus: RequestBody
    ) {
        val token = "Bearer " + _user.value?.accessToken
        viewModelScope.launch {
            updateVolunteerUseCase(
                token,
                id!!.toInt(),
                photo,
                nik,
                name,
                phone,
                email,
                birthPlace,
                birthDate,
                gender,
                address,
                rt,
                rw,
                tps,
                province,
                regency,
                subDistrict,
                village,
                religion,
                maritalStatus
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