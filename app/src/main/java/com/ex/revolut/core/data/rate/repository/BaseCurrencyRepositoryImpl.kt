package com.ex.revolut.core.data.rate.repository

import androidx.lifecycle.LiveData
import com.ex.revolut.core.data.rate.entities.BaseCurrencyDao
import com.ex.revolut.core.data.rate.entities.DatabaseBaseCurrencyRate

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
class BaseCurrencyRepositoryImpl(private val baseCurrencyDao: BaseCurrencyDao) :
    BaseCurrencyRepository {

    override fun getOne(): LiveData<DatabaseBaseCurrencyRate> {
        return baseCurrencyDao.getBaseCurrency()
    }

    override fun deleteAll() {
        baseCurrencyDao.deleteAll()
    }

    override fun insert(vararg d: DatabaseBaseCurrencyRate) {
        baseCurrencyDao.insert(*d)
    }
}