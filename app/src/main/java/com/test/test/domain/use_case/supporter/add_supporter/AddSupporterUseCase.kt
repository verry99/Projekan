package com.test.test.domain.use_case.supporter.add_supporter

import com.test.test.common.Resource
import com.test.test.data.remote.dto.volunteer.add_volunteer.AddVolunteerResponse
import com.test.test.domain.repository.SupporterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddSupporterUseCase @Inject constructor(
    private val supporterRepository: SupporterRepository
) {
    suspend operator fun invoke(
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
        maritalStatus: RequestBody,
    ): Flow<Resource<AddVolunteerResponse>> = flow {

        emit(Resource.Loading())
        try {
            val response = supporterRepository.addSupporter(
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
            emit(Resource.Success(response))

        } catch (e: HttpException) {
            when (e.code()) {
                in 400..499 -> {
                    emit(Resource.Error("Input salah. Mohon periksa kembali inputan Anda."))
                }

                in 500..599 -> {
                    emit(Resource.Error("Server Error."))
                }

                else -> {
                    emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection!"))
        }
    }
}