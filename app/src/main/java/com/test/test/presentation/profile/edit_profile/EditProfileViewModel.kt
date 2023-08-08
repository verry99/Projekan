package com.test.test.presentation.profile.edit_profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.test.test.domain.models.Division.District
import com.test.test.domain.models.Division.Province
import com.test.test.domain.models.Division.Regency
import com.test.test.domain.models.Division.Village
import com.test.test.domain.use_case.division.get_all_district.GetAllDistrictUseCase
import com.test.test.domain.use_case.division.get_all_province.GetAllProvinceUseCase
import com.test.test.domain.use_case.division.get_all_regency.GetAllRegencyUseCase
import com.test.test.domain.use_case.division.get_all_village.GetAllVillageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.io.File
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLException

@HiltViewModel
class EditProfileViewModel @Inject constructor(
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
            } catch (e: UnknownHostException) {
                Log.e("#edtee", "UnknownHostException: ${e.message}")
            } catch (e: IOException) {
                Log.e("#edtee", "IOException: ${e.message}")
            } catch (e: SocketTimeoutException) {
                Log.e("#edtee", "SocketTimeoutException: ${e.message}")
            } catch (e: ConnectException) {
                Log.e("#edtee", "ConnectException: ${e.message}")
            } catch (e: SSLException) {
                Log.e("#edtee", "SSLException: ${e.message}")
            } catch (e: JSONException) {
                Log.e("#edtee", "JSONException: ${e.message}")
            } catch (e: JsonParseException) {
                Log.e("#edtee", "JsonParseException: ${e.message}")
            } catch (e: Exception) {
                // Catch other types of exceptions if needed
                Log.e("#edtee", "An unexpected error occurred: ${e.message}")
            }
        }
    }

    fun setSelectedProvince(provinceName: String) {
        _selectedProvince.value = provinceName
    }

    fun setSelectedRegency(regencyName: String) {
        _selectedRegency.value = regencyName
    }

    fun setSelectedDistrict(districtName: String) {
        _selectedDistrict.value = districtName
    }

    fun postProfile(
        nik: Int,
        name: String,
        phone: String,
        birthPlace: String,
        birthDate: String,
        gender: Char,
        address: String,
        rt: Int,
        rw: Int,
        tps: Int,
        province: String,
        regency: String,
        subDistrict: String,
        village: String,
        religion: String,
        photo: File,
        maritalStatus: String
    ) {

    }
}

