package me.aheadlcx.net.interceptor

import android.text.TextUtils
import android.util.Log
import me.aheadlcx.net.RetrofitUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/26 5:13 下午
 */
class GithubTokenInterceptor : Interceptor {
    companion object{
        private const val TAG = "GithubToken"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        if (!TextUtils.isEmpty(RetrofitUtil.accessToken)) {
            builder.header("Authorization", "Bearer " + RetrofitUtil.accessToken)
            Log.i(TAG, "intercept: accessToken=" + RetrofitUtil.accessToken)
        } else {
            Log.i(TAG, "intercept: accessToken------")
        }
        val request = builder
            .build()
        return chain.proceed(request)
    }
}