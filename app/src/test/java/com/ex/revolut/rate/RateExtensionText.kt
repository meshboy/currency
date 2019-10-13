package com.ex.revolut.rate

import com.ex.revolut.core.data.rate.domain.RateModel
import com.ex.revolut.core.data.rate.dto.RateDto
import com.ex.revolut.core.data.rate.dto.convertToRateList
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-13
 */
@RunWith(JUnit4::class)
class RateExtensionText {

    @Test
    fun convert_rateMap_to_rateModel_list() {

        val rates = mapOf(
            "AUD" to 1.6102,
            "BGN" to 1.9483
        )

        val rateDto = RateDto("EUR", rates)

        val expectedResult = arrayListOf(
            RateModel("AUD", 1.6102),
            RateModel("BGN", 1.9483)
        )
        assertEquals(expectedResult, rateDto.rates.convertToRateList())
    }

    @Test
    fun ensure_emptyRateMap_returns_empty_ratetModel_list() {
        val rates = emptyMap<String, Double>()
        val rateDto = RateDto("", rates)
        assertEquals(emptyList<RateModel>(), rateDto.rates.convertToRateList())
    }
}