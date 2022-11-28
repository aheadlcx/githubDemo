package me.aheadlcx

import android.app.Application
import com.shuyu.github.kotlin.common.gsyimageloader.GSYImageLoaderManager
import com.shuyu.github.kotlin.common.gsyimageloader.gsygiideloader.GSYGlideImageLoader
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
        ///初始化图片加载
        ///初始化图片加载
        GSYImageLoaderManager.initialize(GSYGlideImageLoader(this))
    }
}