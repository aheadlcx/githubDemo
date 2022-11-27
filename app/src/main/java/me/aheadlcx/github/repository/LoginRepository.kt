package me.aheadlcx.github.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.aheadlcx.github.MainActivity
import me.aheadlcx.github.api.GithubApiServiceManager
import me.aheadlcx.github.api.TokenBeanReq
import me.aheadlcx.github.config.AppConfig
import me.aheadlcx.github.utils.GSYPreference
import me.aheadlcx.net.RetrofitUtil

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 2:17 下午
 */
class LoginRepository {

    companion object {
        private const val TAG = "LoginRepository"
    }

    private var accessTokenStorage: String by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    private var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO, "")

    private suspend fun getTokenByCode(code: String): Flow<String> {
        val req = TokenBeanReq()
        req.let {
            it.code = code
        }
//        val async = scope.async {
        val flow = GithubApiServiceManager.gitHubService.getTokenByCodeFlow(req)
            .flowOn(Dispatchers.IO)
            .map {
                val token = it.access_token
                accessTokenStorage = token
                Log.i(TAG, "getTokenByCodeFlow: accessToken=${token}")
                RetrofitUtil.setAccessToken(token)
                token!!
            }
        return flow
    }

    /**
     * 登录
     */
    fun oauth(scope: CoroutineScope, code: String, token: MutableLiveData<Boolean>) {

        scope.launch {
            getTokenByCode(code)
                .catch {
                    token.value = false
                }.collect {
                    token.value = true
                }
        }
    }

    /**
     * 清除token
     */
    fun clearTokenStorage() {
        accessTokenStorage = ""
//        userBasicCodeStorage = ""
    }
}