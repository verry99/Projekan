package com.test.test.presentation.analysis.region

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.test.test.domain.models.Division.Regency
import com.test.test.domain.models.Division.SubDistrict
import com.test.test.domain.models.Division.Village
import com.test.test.domain.models.Profile
import com.test.test.domain.models.UserPref
import com.test.test.domain.use_case.division.get_all_district.GetAllDistrictUseCase
import com.test.test.domain.use_case.division.get_all_regency.GetAllRegencyUseCase
import com.test.test.domain.use_case.division.get_all_village.GetAllVillageUseCase
import com.test.test.domain.use_case.user_pref.get_user.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class AnalysisRegionViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val getAllRegencyUseCase: GetAllRegencyUseCase,
    private val getAllDistrictUseCase: GetAllDistrictUseCase,
    private val getAllVillageUseCase: GetAllVillageUseCase
) : ViewModel() {

    private val _regency = MutableLiveData<List<Regency>>()
    val regency: LiveData<List<Regency>> = _regency

    val selectedProvince = MutableLiveData<String>()
    val selectedRegency = MutableLiveData<String>()
    val selectedDistrict = MutableLiveData<String>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _user = MutableLiveData<UserPref>()

    val success = MutableLiveData(false)

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile> = _profile

    val subDistrict: LiveData<List<SubDistrict>> = selectedRegency.switchMap {
        liveData {
            var subDistrict: List<SubDistrict> = listOf()
            if (it != "Pilih Kabupaten") subDistrict =
                getAllDistrictUseCase(regency.value?.find { regency -> regency.name == it }?.id.toString())
            subDistrict = listOf(SubDistrict(id = "0", name = "Pilih Kecamatan")) + subDistrict
            emit(subDistrict)
        }
    }

    val village: LiveData<List<Village>> = selectedDistrict.switchMap {
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
            try {
                val regencyList =
                    withContext(Dispatchers.IO) {
                        try {
                            getAllRegencyUseCase("1")
                        } catch (e: UnknownHostException) {
                            emptyList()
                        }
                    }
                _regency.value = listOf(Regency(id = "0", name = "Pilih Kabupaten")) + regencyList

                getUserPreferenceUseCase().let { userPref ->
                    _user.value = userPref
                }
            } catch (e: IOException) {
                Log.e("#edtee", "IOException: ${e.message}")
            } catch (e: Exception) {
                Log.e("#edtee", "An unexpected error occurred: ${e.message}")
            }
        }
    }
}

