package com.ex.revolut.core.di.modules.views

import com.ex.revolut.rate.viewmodel.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */

val mainModule = Kodein.Module("Main Module") {
    bind() from provider { MainViewModelFactory(instance(), instance()) }
}