package com.test.test.domain.use_case.division.get_all_district

import com.test.test.data.remote.dto.region.toModel
import com.test.test.domain.models.Division.SubDistrict
import com.test.test.domain.repository.DivisionRepository
import javax.inject.Inject

class GetAllSubDistrictUseCase @Inject constructor(
    private val divisionRepository: DivisionRepository
) {
    //    operator fun invoke(regencyId: String): Flow<Result<List<SubDistrict>>> = flow {
//        try {
//            emit(Result.Loading())
//            val supporter = divisionRepository.getAllDistrict(regencyId).map { it.toModel() }
//            emit(Result.Success(supporter))
//        } catch (e: HttpException) {
//            emit(Result.Error(e.localizedMessage ?: "Unexpected Error"))
//        } catch (e: IOException) {
//            emit(Result.Error("Couldn't reach the server. Check your internet connection!"))
//        }
//    }
    suspend operator fun invoke(regencyId: String): List<SubDistrict> {
        return divisionRepository.getAllSubDistrict(regencyId).map { it.toModel() }
    }
}