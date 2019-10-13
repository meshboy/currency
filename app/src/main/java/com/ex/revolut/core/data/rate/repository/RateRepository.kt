package com.ex.revolut.core.data.rate.repository

import com.ex.revolut.core.data.database.Repository
import com.ex.revolut.core.data.rate.dto.RateDto
import com.ex.revolut.core.data.rate.entities.DatabaseRate

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
interface RateRepository : Repository<DatabaseRate> {
    suspend fun fetchRemoteRates(base: String): RateDto?
    suspend fun fetchLocalRates(): List<DatabaseRate>
}