package me.aheadlcx.github.config

import me.aheadlcx.github.MainActivity

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 2:21 下午
 */
object AppConfig {

    const val GITHUB_BASE_URL = "https://github.com/"

    const val GITHUB_API_BASE_URL = "https://api.github.com/"

    const val GITHUB_CONTENT_BASE_URL = "https://raw.githubusercontent.com/"

    const val GRAPHIC_HOST = "https://ghchart.rshah.org/"

    const val client_id = "84d56022a54eeb45eecf"
    const val client_secret = "af29bf5a1dfff246d342eef8e6db1e4106f9777e"
    const val redirect_uri = "aheadlcx://github/authed"

    const val API_TOKEN = "4d65e2a5626103f92a71867d7b49fea0"

    const val PAGE_SIZE = 5

    const val HTTP_TIME_OUT = 20 * 1000L

    const val HTTP_MAX_CACHE_SIZE = 16 * 1024 * 1024L

    const val IMAGE_MAX_CACHE_SIZE = 16 * 1024 * 1024L

    const val CACHE_MAX_AGE = 7 * 24 * 60 * 60L

    const val ACCESS_TOKEN = "accessToken"

    const val USER_BASIC_CODE = "userBasicCode"

    const val USER_NAME = "user_name"

    const val PASSWORD = "password"

    const val USER_INFO = "userInfo"

    const val Login_Url = "https://github.com/login/oauth/authorize?client_id=${client_id}" +
            "&state=app&redirect_uri=${redirect_uri}&scope=user"

}