package me.aheadlcx.jetpack.net.interceptor

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Description:
 * author: aheadlcx
 * Date:2023/2/6 3:14 下午
 */
class AccessTokenAuthenticator:Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        TODO("Not yet implemented")
    }
}