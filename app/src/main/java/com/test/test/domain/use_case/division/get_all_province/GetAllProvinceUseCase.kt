package com.test.test.domain.use_case.division.get_all_province

import com.test.test.data.remote.dto.region.toModel
import com.test.test.domain.models.Division.Province
import com.test.test.domain.repository.DivisionRepository
import javax.inject.Inject

class GetAllProvinceUseCase @Inject constructor(
    private val divisionRepository: DivisionRepository
) {
    //    operator fun invoke(): Flow<Result<List<Province>>> = flow {
//        try {
//            emit(Result.Loading())
//            val supporter = divisionRepository.getAllProvince().map { it.toModel() }
//            emit(Result.Success(supporter))
//        } catch (e: HttpException) {
//            emit(Result.Error(e.localizedMessage ?: "Unexpected Error"))
//        } catch (e: IOException) {
//            emit(Result.Error("Couldn't reach the server. Check your internet connection!"))
//        }
//    }
    suspend operator fun invoke(): List<Province> {
        return divisionRepository.getAllProvince().map { it.toModel() }
    }
}