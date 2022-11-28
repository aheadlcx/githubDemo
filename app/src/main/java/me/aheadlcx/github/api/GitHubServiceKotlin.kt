package me.aheadlcx.github.api

import kotlinx.coroutines.flow.Flow
import me.aheadlcx.github.api.bean.UserInfo
import me.aheadlcx.net.annotations.BaseUrl
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/16 10:38 上午
 */
interface GitHubServiceKotlin {

    @Headers("Accept:application/vnd.github+json")
    @GET("user")
    suspend fun getUserInfoSub(@Header("Authorization") auth: String?): UserInfo

    @Headers("Accept:application/json")
    @POST("login/oauth/access_token")
    @BaseUrl("https://github.com/")
    fun getTokenByCodeFlow(@Body req: TokenBeanReq?): Flow<TokenBean>
}