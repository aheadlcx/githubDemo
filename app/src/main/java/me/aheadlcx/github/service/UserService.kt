package me.aheadlcx.github.service

import com.shuyu.github.kotlin.model.bean.Event
import com.shuyu.github.kotlin.model.bean.User
import kotlinx.coroutines.flow.Flow
import me.aheadlcx.github.api.bean.UserInfo
import me.aheadlcx.github.config.AppConfig
import retrofit2.Response
import retrofit2.http.*
import java.util.*

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 11:06 下午
 */
interface UserService {
    /**
     * List events that a user has received
     */
    @GET("users/{user}/received_events")
    fun getNewsEvent(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = 20
    ): Flow<ArrayList<Event>>
//    @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE


    @GET("users/{user}/followers")
    fun getFollowers(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = 2
    ): Flow<ArrayList<User>>

    @Headers("Accept:application/vnd.github+json")
    @GET("user")
    fun getUserInfoByFlow(@Header("Authorization") auth: String?): Flow<UserInfo>


    //from gsy
    @GET("user")
    fun getPersonInfo(
        @Header("forceNetWork") forceNetWork: Boolean
    ): Flow<User>

    @GET("users/{user}")
    fun getUser(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String
    ): Flow<User>
}