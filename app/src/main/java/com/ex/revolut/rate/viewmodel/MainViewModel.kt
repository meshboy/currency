package com.ex.revolut.rate.viewmodel

import com.ex.revolut.core.data.rate.repository.BaseCurrencyRepository
import com.ex.revolut.core.data.rate.repository.RateRepository
import com.ex.revolut.core.mvvm.BaseViewModel
import com.ex.revolut.rate.view.MainView

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
class MainViewModel(
    val rateRepository: RateRepository,
    val baseCurrencyRepository: BaseCurrencyRepository
) : BaseViewModel<MainView>() {

}