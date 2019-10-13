package com.ex.revolut.core.network

import com.ex.revolut.core.data.rate.dto.RateDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
interface NetworkService {

    @GET(value = "/latest")
    fun fetchCurrencyRates(@Query(value = "base") base: String): Deferred<RateDto>
}