package com.ex.revolut.core.network

import android.content.Context
import com.ex.revolut.core.isConnected
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
class ConnectionInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected(context)) {
            throw IOException("No internet")
        }
        return chain.proceed(chain.request())
    }
}