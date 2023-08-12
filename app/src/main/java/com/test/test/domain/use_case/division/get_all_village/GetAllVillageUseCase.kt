package com.test.test.domain.use_case.division.get_all_village

import com.test.test.data.remote.dto.region.toModel
import com.test.test.domain.models.Division.Village
import com.test.test.domain.repository.DivisionRepository
import javax.inject.Inject

class GetAllVillageUseCase @Inject constructor(
    private val divisionRepository: DivisionRepository
) {
    //    operator fun invoke(districtId: String): Flow<Result<List<Village>>> = flow {
//        try {
//            emit(Result.Loading())
//            val supporter = divisionRepository.getAllVillage(districtId).map { it.toModel() }
//            emit(Result.Success(supporter))
//        } catch (e: HttpException) {
//            emit(Result.Error(e.localizedMessage ?: "Unexpected Error"))
//        } catch (e: IOException) {
//            emit(Result.Error("Couldn't reach the server. Check your internet connection!"))
//        }
//    }
    suspend operator fun invoke(districtId: String): List<Village> {
        return divisionRepository.getAllVillage(districtId).map { it.toModel() }
    }
}