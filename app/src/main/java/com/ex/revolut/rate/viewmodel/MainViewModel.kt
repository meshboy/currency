package com.ex.revolut.rate.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ex.revolut.core.data.rate.domain.RateModel
import com.ex.revolut.core.data.rate.dto.RateDto
import com.ex.revolut.core.data.rate.dto.asDatabaseModel
import com.ex.revolut.core.data.rate.dto.asDomainModel
import com.ex.revolut.core.data.rate.entities.DatabaseBaseCurrencyRate
import com.ex.revolut.core.data.rate.entities.DatabaseRate
import com.ex.revolut.core.data.rate.entities.asDomainModel
import com.ex.revolut.core.data.rate.repository.BaseCurrencyRepository
import com.ex.revolut.core.data.rate.repository.RateRepository
import com.ex.revolut.core.mvvm.BaseViewModel
import com.ex.revolut.rate.view.MainView
import kotlinx.coroutines.*
import timber.log.Timber

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
class MainViewModel(
    private val rateRepository: RateRepository,
    private val baseCurrencyRepository: BaseCurrencyRepository
) : BaseViewModel<MainView>() {

    //    set up coroutine scope to handle operations off the thread
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    val baseCurrency = baseCurrencyRepository.getOne()

    var rates = MutableLiveData<List<RateModel>>()

    /**
     * fetch the rates using base currency saved.
     * update successful fetched rate in the db alongside with the base currency
     */
    fun fetchRates(baseCurrency: String) {
        Timber.d("base  currency %s", baseCurrency)
        coroutineScope.launch {
            showLoading()

//            check and set if there are local rates
            val localRates = fetchLocalRates()
            if (!localRates.isNullOrEmpty()) {
                rates.value = localRates.asDomainModel()
                hideLoading()
            }

            val rateDto: RateDto? = fetchRemoteRates(baseCurrency)
            if (rateDto != null) {
                rates.value = rateDto.asDomainModel()
                saveRates(rateDto.asDomainModel())
            }

//            hide loading if its still showing
            hideLoading()
        }
    }

    private suspend fun fetchLocalRates(): List<DatabaseRate> {
        return withContext(Dispatchers.IO) {
            rateRepository.fetchLocalRates()
        }
    }

    private suspend fun fetchRemoteRates(baseCurrency: String): RateDto? {
        return withContext(Dispatchers.IO) {
            rateRepository.fetchRemoteRates(baseCurrency)
        }
    }

    fun saveBaseCurrency(currency: String){
        coroutineScope.launch {
            saveRecentBaseCurrency(currency)
        }
    }

    private suspend fun saveRecentBaseCurrency(currency: String) {
        withContext(Dispatchers.IO) {
            //            clear up existing base currency
            baseCurrencyRepository.deleteAll()
            baseCurrencyRepository.insert(DatabaseBaseCurrencyRate(recentBase = currency))
        }
    }

    private suspend fun saveRates(rates: List<RateModel>) {
        withContext(Dispatchers.IO) {
            //            clear up the recent saved rates
            rateRepository.deleteAll()
            rateRepository.insert(*rates.asDatabaseModel().toTypedArray())
        }
    }


    fun showLoading() {
        view.showLoading()
    }

    fun hideLoading() {
        view.hideLoading()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}