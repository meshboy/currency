package com.ex.revolut.core.network

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */
sealed class ResponseHandler<out T : Any> {
    class Success<out T : Any>(val response: T) : ResponseHandler<T>()
    class Error(val ex: Throwable? = null, val message: String? = ex?.localizedMessage) :
        ResponseHandler<Nothing>()
}