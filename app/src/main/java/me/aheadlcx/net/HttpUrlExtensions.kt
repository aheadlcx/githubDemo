package me.aheadlcx.net

import okhttp3.HttpUrl

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/26 5:48 下午
 */
fun HttpUrl.jointEncodedPath() = encodedPath.replaceFirst("/", "")