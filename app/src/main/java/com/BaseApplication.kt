package com

import android.app.Application
import com.ex.revolut.core.di.modules.database.databaseModule
import com.ex.revolut.core.di.modules.network.networkModule
import com.ex.revolut.core.di.modules.views.mainModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import timber.log.Timber

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-09
 */
class BaseApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@BaseApplication))
        import(networkModule)
        import(databaseModule)
        import(mainModule)
    }

    override fun onCreate() {
        super.onCreate()
        setUpTimber()
    }

    private fun setUpTimber() {
        Timber.plant(Timber.DebugTree())
    }

}