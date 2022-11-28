package me.aheadlcx.github.api

import com.ihsanbal.logging.LoggingInterceptor
import me.aheadlcx.github.service.RepoService
import me.aheadlcx.github.service.UserService
import me.aheadlcx.net.interceptor.BaseUrlInterceptor
import me.aheadlcx.jetpack.net.interceptor.GithubTokenInterceptor
import me.aheadlcx.net.adapter.FlowCallAdapterFactory
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
                .addInterceptor(BaseUrlInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .build()
        )
        .build()
    val gitHubService: GitHubServiceKotlin = retrofit.create(GitHubServiceKotlin::class.java)
    val userService: UserService = retrofit.create(UserService::class.java)
    val repoService: RepoService = retrofit.create(RepoService::class.java)
}