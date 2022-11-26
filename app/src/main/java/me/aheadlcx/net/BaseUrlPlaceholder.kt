package me.aheadlcx.net

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/26 5:45 下午
 */
class BaseUrlPlaceholder {
    companion object{
        val instance:HttpUrl = "https://api.github.com/".toHttpUrl()
    }
}