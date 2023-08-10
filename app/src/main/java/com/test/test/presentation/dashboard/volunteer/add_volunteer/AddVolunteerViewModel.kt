package com.test.test.presentation.dashboard.volunteer.add_volunteer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.test.test.common.Resource
import com.test.test.domain.models.Division.District
import com.test.test.domain.models.Division.Province
import com.test.test.domain.models.Division.Regency
import com.test.test.domain.models.Division.Village
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.division.get_all_district.GetAllDistrictUseCase
import com.test.test.domain.use_case.division.get_all_province.GetAllProvinceUseCase
import com.test.test.domain.use_case.division.get_all_regency.GetAllRegencyUseCase
import com.test.test.domain.use_case.division.get_all_village.GetAllVillageUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import com.test.test.domain.use_case.volunteer.add_volunteer.AddVolunteerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class AddVolunteerViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val addVolunteerUseCase: AddVolunteerUseCase,
    private val getAllProvinceUseCase: GetAllProvinceUseCase,
    private val getAllRegencyUseCase: GetAllRegencyUseCase,
    private val getAllDistrictUseCase: GetAllDistrictUseCase,
    private val getAllVillageUseCase: GetAllVillageUseCase
) : ViewModel() {

    private val _province = MutableLiveData<List<Province>>()
    val province: LiveData<List<Province>> = _province

    private val _selectedProvince = MutableLiveData<String>()
    private val _selectedRegency = MutableLiveData<String>()
    private val _selectedDistrict = MutableLiveData<String>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>(false)
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

    val district: LiveData<List<District>> = _selectedRegency.switchMap {
        liveData {
            var district: List<District> = listOf()
            if (it != "Pilih Kabupaten") district =
                getAllDistrictUseCase(regency.value?.find { regency -> regency.name == it }?.id.toString())
            district = listOf(District(id = "0", name = "Pilih Kecamatan")) + district
            emit(district)
        }
    }

    val village: LiveData<List<Village>> = _selectedDistrict.switchMap {
        liveData {
            var village: List<Village> = listOf()
            if (it != "Pilih Kecamatan") village =
                getAllVillageUseCase(district.value?.find { district -> district.name == it }?.id.toString())
            village = listOf(Village(id = "0", name = "Pilih Desa")) + village
            emit(village)
        }
    }

    private val _user = MutableLiveData<UserPref>()

    init {
        viewModelScope.launch {
            try {
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
            } catch (e: IOException) {
                Log.e("#edtee", "IOException: ${e.message}")
            } catch (e: Exception) {
                Log.e("#edtee", "An unexpected error occurred: ${e.message}")
            }
        }
    }

    fun addVolunteer(
        photo: MultipartBody.Part?,
        nik: RequestBody,
        name: RequestBody,
        phone: RequestBody,
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
        Log.e(
            "#viewmodle addvol",
            "$token $photo $nik $name $phone $birthPlace $birthDate $gender $address $rt $rw $tps $province $regency $subDistrict $village $religion $maritalStatus"
        )
        viewModelScope.launch {
            addVolunteerUseCase(
                token,
                photo,
                nik,
                name,
                phone,
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
                    is Resource.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = it.message!!
                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                    }

                    is Resource.Success -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}
