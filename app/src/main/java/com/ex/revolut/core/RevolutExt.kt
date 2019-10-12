package com.ex.revolut.core

import android.content.Context
import android.net.ConnectivityManager
import android.view.View

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


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide(isGone: Boolean = true) {
    visibility = if (isGone) {
        View.GONE
    } else {
        View.INVISIBLE
    }
}