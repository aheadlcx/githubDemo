package me.aheadlcx.github.api

import me.aheadlcx.github.api.bean.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/16 10:38 上午
 */
interface GitHubServiceKotlin {

    @Headers("Accept:application/vnd.github+json")
    @GET("user")
    suspend fun getUserInfoSub(@Header("Authorization") auth: String?): UserInfo


    @Headers("Accept:application/vnd.github+json")
    @GET("user")
    suspend fun getUserInfoSubRes(@Header("Authorization") auth: String?): Response<UserInfo>
}