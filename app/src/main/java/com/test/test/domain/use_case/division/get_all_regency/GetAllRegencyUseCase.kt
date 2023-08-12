package com.test.test.domain.use_case.division.get_all_regency

import com.test.test.data.remote.dto.region.toModel
import com.test.test.domain.models.Division.Regency
import com.test.test.domain.repository.DivisionRepository
import javax.inject.Inject

class GetAllRegencyUseCase @Inject constructor(
    private val divisionRepository: DivisionRepository
) {
    //    operator fun invoke(provinceId: String): Flow<Result<List<Regency>>> = flow {
//        try {
//            emit(Result.Loading())
//            val supporter = divisionRepository.getAllRegency(provinceId).map { it.toModel() }
//            emit(Result.Success(supporter))
//        } catch (e: HttpException) {
//            emit(Result.Error(e.localizedMessage ?: "Unexpected Error"))
//        } catch (e: IOException) {
//            emit(Result.Error("Couldn't reach the server. Check your internet connection!"))
//        }
//    }
    suspend operator fun invoke(provinceId: String): List<Regency> {
        return divisionRepository.getAllRegency(provinceId).map { it.toModel() }
    }
}