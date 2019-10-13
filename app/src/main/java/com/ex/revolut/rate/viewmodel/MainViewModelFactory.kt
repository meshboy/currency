package com.ex.revolut.rate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ex.revolut.core.data.rate.repository.BaseCurrencyRepository
import com.ex.revolut.core.data.rate.repository.RateRepository

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
class MainViewModelFactory(
    val rateRepository: RateRepository,
    val baseCurrencyRepository: BaseCurrencyRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                rateRepository,
                baseCurrencyRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class $modelClass")
    }
}