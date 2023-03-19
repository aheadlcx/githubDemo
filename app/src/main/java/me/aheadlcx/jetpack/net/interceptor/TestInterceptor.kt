package me.aheadlcx.jetpack.net.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Description:
 * author: aheadlcx
 * Date:2023/2/6 10:43 下午
 */
class TestInterceptor:Interceptor {
    companion object{
        private const val TAG = "TestInterceptor"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        Log.i(TAG, "intercept:2222")
        return chain.proceed(originalRequest)

    }
}