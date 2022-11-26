package me.aheadlcx.net.interceptor

import me.aheadlcx.net.BaseUrlPlaceholder
import me.aheadlcx.net.annotations.BaseUrl
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import java.lang.reflect.Method
import me.aheadlcx.net.jointEncodedPath
import okhttp3.HttpUrl.Companion.toHttpUrl

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/26 5:39 下午
 */
class BaseUrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val srcRequest = chain.request()
        val srcUrl = srcRequest.url
        srcRequest.tag(Invocation::class.java)?.method()?.let { method ->
            val newUrl = parseOverridingUrl(method)
            if (newUrl !== NO_OVERRIDING) {
                return@intercept chain.proceed(
                    srcRequest.newBuilder()
                        .url(
                            srcUrl.newBuilder()
                                .scheme(newUrl.scheme)
                                .host(newUrl.host)
                                .port(newUrl.port)
                                .encodedPath(newUrl.encodedPath + srcUrl.jointEncodedPath())
                                .build()
                        )
                        .build()
                )
            }
        }
        return chain.proceed(srcRequest)
    }
}

private val cachedOverridingUrls = mutableMapOf<Method, HttpUrl>()
private val NO_OVERRIDING = BaseUrlPlaceholder.instance
private fun parseOverridingUrl(invokeMethod: Method): HttpUrl {
    cachedOverridingUrls[invokeMethod]?.let { return@parseOverridingUrl it }
    synchronized(BaseUrlInterceptor::class) {
        cachedOverridingUrls[invokeMethod]?.let { return@parseOverridingUrl it }
        val baseUrlStr = invokeMethod.getAnnotation(BaseUrl::class.java)?.value
            ?: invokeMethod.declaringClass.getAnnotation(BaseUrl::class.java)?.value
        baseUrlStr?.let {
            if (!it.endsWith("/")) throw IllegalStateException("base url must be ended with /. please fix the api interface #${invokeMethod.declaringClass.name}.${invokeMethod.name}#'s base url:$it")
            it.toHttpUrl().let { httpUrl ->
                cachedOverridingUrls[invokeMethod] = httpUrl
                return@parseOverridingUrl httpUrl
            }
        }
        cachedOverridingUrls[invokeMethod] = NO_OVERRIDING
        return@parseOverridingUrl NO_OVERRIDING
    }
}