package me.aheadlcx.wan.net

import com.ihsanbal.logging.LoggingInterceptor
import me.aheadlcx.net.RetrofitUtil
import me.aheadlcx.wan.net.service.WanAndroidService
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/20 8:28 下午
 */
object WanRetrofitUtil {

    //    companion object {
    val wanBaseUrl = "https://www.wanandroid.com/"
//    }

    var retrofit: Retrofit? = null


    fun initRetrofit() {
        retrofit = RetrofitUtil.init(wanBaseUrl)
    }

    fun getWanRetrofit(): Retrofit? {
        if (retrofit == null) {
            initRetrofit()
        }
        return retrofit
    }

    fun getWanService() :WanAndroidService{
        return getWanRetrofit()!!.create(WanAndroidService::class.java)
    }


}