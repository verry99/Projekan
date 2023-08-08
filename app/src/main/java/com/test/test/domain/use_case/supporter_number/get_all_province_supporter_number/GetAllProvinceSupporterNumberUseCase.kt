package com.test.test.domain.use_case.supporter_number.get_all_province_supporter_number

import com.test.test.domain.models.SupporterNumber
import com.test.test.domain.repository.SupporterNumberRepository
import javax.inject.Inject

class GetAllProvinceSupporterNumberUseCase @Inject constructor(
    private val supporterNumberRepository: SupporterNumberRepository
) {
    operator fun invoke(): List<SupporterNumber> {
        val myList = listOf(
            SupporterNumber("Jawa Barat", "26", "34", "60"),
            SupporterNumber("Jawa Tengah", "245", "210", "455"),
            SupporterNumber("Jawa Timur", "512", "518", "1030"),
        )

        return myList + getTotalSupporterNumber(myList)
    }

    private fun getTotalSupporterNumber(myList: List<SupporterNumber>): SupporterNumber {
        var totalSupporterNumberMale = 0
        var totalSupporterNumberFemale = 0
        var totalSupporterNumber = 0

        for (s in myList) {
            totalSupporterNumberMale += s.supporterNumberMale.toInt()
            totalSupporterNumberFemale += s.supporterNumberFemale.toInt()
            totalSupporterNumber += s.supporterNumberTotal.toInt()
        }

        return SupporterNumber(
            "Total",
            totalSupporterNumberMale.toString(),
            totalSupporterNumberFemale.toString(),
            totalSupporterNumber.toString()
        )
    }
}