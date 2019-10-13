package com.ex.revolut.core.di.modules.network

import com.ex.revolut.core.network.NetworkService
import com.ex.revolut.core.network.NetworkServiceImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
val networkModule = Kodein.Module("Network Module") {
    bind<NetworkService>() with singleton { NetworkServiceImpl(instance()).api() }
}