package com.test.test.domain.repository

import com.test.test.data.remote.dto.profile.ProfileResponse
import com.test.test.data.remote.dto.profile.UpdateProfileResponse
import com.test.test.data.remote.dto.profile.update_password.UpdatePasswordResponse
import com.test.test.data.remote.dto.profile.update_phone.UpdatePhoneResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProfileRepository {

    suspend fun getProfile(token: String): ProfileResponse

    suspend fun updateProfile(
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
    ): UpdateProfileResponse

    suspend fun updatePassword(
        token: String,
        oldPassword: String,
        password: String,
        passwordConfirmation: String,
    ): UpdatePasswordResponse

    suspend fun updatePhone(
        token: String,
        password: String,
        phone: String,
    ): UpdatePhoneResponse
}