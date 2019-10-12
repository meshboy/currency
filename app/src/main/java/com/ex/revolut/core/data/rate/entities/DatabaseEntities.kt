package com.ex.revolut.core.data.rate.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ex.revolut.core.data.rate.domain.RateModel

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
@Entity(tableName = "rates")
data class DatabaseRate(
    val currency: String,
    val rate: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

//cache  the most recent base currency the user selected, the default is EUR
@Entity(tableName = "base_currency")
data class DatabaseBaseCurrencyRate(

    val id: Long = 1,

    @PrimaryKey
    val recentBase: String
)

fun List<DatabaseRate>.asDomainModel(): List<RateModel> {
    return map {
        RateModel(
            currency = it.currency,
            rate = it.rate.toDouble()
        )
    }
}