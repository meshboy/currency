package com.ex.revolut.core.data.rate.repository

import androidx.lifecycle.LiveData
import com.ex.revolut.core.data.database.Repository
import com.ex.revolut.core.data.rate.entities.DatabaseBaseCurrencyRate

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
interface BaseCurrencyRepository: Repository<DatabaseBaseCurrencyRate> {
    fun getOne(): LiveData<DatabaseBaseCurrencyRate>
}