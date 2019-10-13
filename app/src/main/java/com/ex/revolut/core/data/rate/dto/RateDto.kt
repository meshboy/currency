package com.ex.revolut.core.data.rate.dto

import com.ex.revolut.core.data.rate.domain.RateModel
import com.ex.revolut.core.data.rate.entities.DatabaseRate
import com.squareup.moshi.JsonClass
import timber.log.Timber

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-10
 */

@JsonClass(generateAdapter = true)
data class RateDto(
    val base: String,
    val rates: Map<String, Double>
)


fun Map<String, Double>.convertToRateList(): List<RateModel> {

    var rateList = emptyList<RateModel>()

    val iterator = iterator()
    while (iterator.hasNext()) {
        val map = iterator.next()
        val rateModel = RateModel(map.key, map.value)
        rateList = rateList.plus(rateModel)
    }
    return rateList
}

fun RateDto.asDomainModel(): List<RateModel> {
    return rates.convertToRateList()
}


fun List<RateModel>.asDatabaseModel(): List<DatabaseRate> {
    return map {
        DatabaseRate(
            currency = it.currency,
            rate = it.rate.toString()
        )
    }
}

