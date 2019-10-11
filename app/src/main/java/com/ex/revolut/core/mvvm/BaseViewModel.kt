package com.ex.revolut.core.mvvm

import androidx.lifecycle.ViewModel

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */

abstract class BaseViewModel<V : BaseView> : ViewModel() {

    open lateinit var view: V
}