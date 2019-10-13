package com.ex.revolut.core.di.modules.database

import com.ex.revolut.core.data.database.RevolutDatabase
import com.ex.revolut.core.data.rate.entities.BaseCurrencyDao
import com.ex.revolut.core.data.rate.entities.RateDao
import com.ex.revolut.core.data.rate.repository.BaseCurrencyRepository
import com.ex.revolut.core.data.rate.repository.BaseCurrencyRepositoryImpl
import com.ex.revolut.core.data.rate.repository.RateRepository
import com.ex.revolut.core.data.rate.repository.RateRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */

val databaseModule = Kodein.Module("Database Module") {
    bind<RevolutDatabase>() with singleton { RevolutDatabase.getDatabase(instance()) }

//    rate
    bind<RateDao>() with singleton { RevolutDatabase.getDatabase(instance()).rateDao }
    bind<RateRepository>() with singleton { RateRepositoryImpl(instance(), instance(), instance()) }

//    base rate
    bind<BaseCurrencyDao>() with singleton {
        RevolutDatabase.getDatabase(instance()).baseCurrencyDao
    }
    bind<BaseCurrencyRepository>() with singleton { BaseCurrencyRepositoryImpl(instance()) }
}