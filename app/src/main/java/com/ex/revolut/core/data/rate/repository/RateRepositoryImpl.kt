package com.ex.revolut.core.data.rate.repository

import android.content.Context
import com.ex.revolut.core.data.rate.dto.RateDto
import com.ex.revolut.core.data.rate.entities.DatabaseRate
import com.ex.revolut.core.data.rate.entities.RateDao
import com.ex.revolut.core.isConnected
import com.ex.revolut.core.network.NetworkService
import com.ex.revolut.core.network.ResponseHandler

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
class RateRepositoryImpl(
    private val context: Context,
    private val networkService: NetworkService,
    private val rateDao: RateDao
) :
    RateRepository {

    private suspend fun remoteRates(base: String): ResponseHandler<RateDto> {
        return try {
            val result = networkService.fetchCurrencyRates(base).await()
            ResponseHandler.Success(result)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResponseHandler.Error()
        }
    }

    override suspend fun fetchRemoteRates(base: String): RateDto? {
        if (!isConnected(context = context))
            return null

        return when (val rates = remoteRates(base)) {
            is ResponseHandler.Success<RateDto> -> {
                rates.response
            }
            else -> null
        }
    }

    override suspend fun fetchLocalRates(): List<DatabaseRate> {
        return rateDao.getRatesByBase()
    }

    override fun deleteAll() {
        rateDao.deleteAll()
    }

    override fun insert(vararg d: DatabaseRate) {
        rateDao.insert(*d)
    }
}