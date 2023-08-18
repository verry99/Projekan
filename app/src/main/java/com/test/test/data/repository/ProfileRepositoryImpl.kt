package com.test.test.data.repository

import com.test.test.data.remote.api.DashboardService
import com.test.test.data.remote.dto.profile.ProfileResponse
import com.test.test.data.remote.dto.profile.UpdateProfileResponse
import com.test.test.data.remote.dto.profile.update_password.UpdatePasswordResponse
import com.test.test.data.remote.dto.profile.update_phone.UpdatePhoneResponse
import com.test.test.domain.repository.ProfileRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.Header
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dashboardService: DashboardService
) : ProfileRepository {

    override suspend fun getProfile(token: String): ProfileResponse {
        return dashboardService.getProfile(token)
    }

    override suspend fun updateProfile(
        token: String,
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
    ): UpdateProfileResponse {

        return dashboardService.updateProfile(
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
        )
    }

    override suspend fun updatePassword(
        token: String,
        oldPassword: String,
        password: String,
        passwordConfirmation: String
    ): UpdatePasswordResponse {
        return dashboardService.updatePassword(token, oldPassword, password, passwordConfirmation)
    }

    override suspend fun updatePhone(
        token: String,
        password: String,
        phone: String,
    ): UpdatePhoneResponse {
        return dashboardService.updatePhone(token, password, phone)
    }
}