package com.ex.revolut.core

import android.content.Context
import android.net.ConnectivityManager

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */

//check internet connectivity
fun isConnected(context: Context): Boolean {
    val isConnected: Boolean
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting

    return isConnected
}
