package com.test.test.domain.use_case.volunteer.update_volunteer

import com.test.test.common.Resource
import com.test.test.data.remote.dto.volunteer.add_volunteer.AddVolunteerResponse
import com.test.test.domain.repository.VolunteerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateVolunteerUseCase @Inject constructor(
    private val volunteerRepository: VolunteerRepository
) {
    suspend operator fun invoke(
        token: String,
        id: Int,
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
    ): Flow<Resource<AddVolunteerResponse>> = flow {

        emit(Resource.Loading())
        try {

            val response = volunteerRepository.updateVolunteer(
                token,
                id,
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
            )

            emit(Resource.Success(response))
        } catch (e: HttpException) {
            when (e.code()) {
                in 400..499 -> {
                    e.response()?.errorBody()?.string()?.let {
                        val errorObj = JSONObject(it)
                        val errorMessage = errorObj.optString("message", "Unknown Error")
                        emit(Resource.Error(errorMessage))
                    }
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