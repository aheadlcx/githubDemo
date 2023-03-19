package me.aheadlcx.net.adapter

import kotlinx.coroutines.flow.Flow
import me.aheadlcx.net.adapter.async.AsyncBodyFlowCallAdapter
import me.aheadlcx.net.adapter.async.AsyncResponseFlowCallAdapter
import me.aheadlcx.net.adapter.sync.BodyFlowCallAdapter
import me.aheadlcx.net.adapter.sync.ResponseFlowCallAdapter
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class FlowCallAdapterFactory private constructor(private val async: Boolean) :
    CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        //返回 null  代表自己不想要这个
        if (getRawType(returnType) != Flow::class.java) return null
        if (returnType !is ParameterizedType) {
            //必须是参数化类型
            throw IllegalStateException("the flow type must be parameterized as Flow<Bean>")
        }

        //获取Flow的泛型参数，例如 Flow<String> 返回的就是 String
        val flowableType = getParameterUpperBound(0, returnType)

        //获取Flow的泛型参数的原始类型，返回 class 类型
        val rawFlowableType = getRawType(flowableType)
        return if (rawFlowableType == Response::class.java) {
            if (flowableType !is ParameterizedType) {
                throw IllegalStateException("should be Flow<Response<T>>");
            }
            //选取Response的泛型参数作为ResponseBody，创建Flow<Response<R>>模式的FlowCallAdapter
            val responseBodyType = getParameterUpperBound(0, flowableType)
            createResponseFlowCallAdapter(async, responseBodyType)
        } else {
            createBodyFlowCallAdapter(async, flowableType)
        }
    }

    companion object {
        private const val TAG = "FlowCallAdapterFactory"
        @JvmStatic
        fun create(async: Boolean = false) = FlowCallAdapterFactory(async)
    }
}

private fun createResponseFlowCallAdapter(async: Boolean, responseBodyType: Type) =
    if (async)
        AsyncResponseFlowCallAdapter(responseBodyType)
    else
        ResponseFlowCallAdapter(responseBodyType)

private fun createBodyFlowCallAdapter(async: Boolean, responseBodyType: Type) =
    if (async)
        AsyncBodyFlowCallAdapter(responseBodyType)
    else
        BodyFlowCallAdapter(responseBodyType)