package me.aheadlcx.jetpack.net.interceptor

import android.text.TextUtils
import android.util.Log
import me.aheadlcx.github.config.AppConfig
import me.aheadlcx.github.utils.GSYPreference
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
    private var accessTokenStorage: String by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
//        val accessToken = RetrofitUtil.accessToken
        val accessToken = accessTokenStorage
        if (!TextUtils.isEmpty(accessToken)) {
            builder.header("Authorization", "Bearer " + accessToken)
            Log.i(TAG, "intercept: accessToken=" + accessToken)
        } else {
            Log.i(TAG, "intercept: accessToken------")
        }
        val request = builder
            .build()
        return chain.proceed(request)
    }
}