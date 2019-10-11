package com.ex.revolut.core.network

import android.content.Context
import com.ex.revolut.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-10-11
 */

class NetworkServiceImpl(context: Context) {

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val logLevel =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = logLevel
        return loggingInterceptor
    }

    private val file: File = File(context.cacheDir, "okhttp-network-cache")

    private val cache: Cache = Cache(file, 10 * 1000 * 1000) //10MB cache


    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(httpLoggingInterceptor())
        .addInterceptor(ConnectionInterceptor(context))
        .connectTimeout(120, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(stringFromJNI())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    fun api(): NetworkService = retrofit.create(NetworkService::class.java)

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}