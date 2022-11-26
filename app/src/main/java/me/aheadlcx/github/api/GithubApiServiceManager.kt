package me.aheadlcx.github.api

import com.ihsanbal.logging.LoggingInterceptor
import me.aheadlcx.jetpack.net.flow.FlowCallAdapterFactory
import me.aheadlcx.jetpack.net.service.weather.WeatherApiService
import me.aheadlcx.net.interceptor.GithubTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/26 4:42 下午
 */
object GithubApiServiceManager {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(FlowCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor.Builder().build())
                .addInterceptor(GithubTokenInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .build()
        )
        .build()
    val gitHubService: GitHubServiceKotlin = retrofit.create(GitHubServiceKotlin::class.java)
}