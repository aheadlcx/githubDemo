package me.aheadlcx

import android.app.Application
import kotlin.properties.Delegates

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 2:18 下午
 */
class GithubApp : Application() {
    companion object {
        var instance: GithubApp by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}