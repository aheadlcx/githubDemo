package me.aheadlcx.jetpack.net.flow.sync

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ResponseFlowCallAdapter<R>(private val responseBodyType: R) :
    CallAdapter<R, Flow<Response<R>>> {
    override fun responseType() = responseBodyType as Type

    override fun adapt(call: Call<R>): Flow<Response<R>> = responseFlow(call)
}

fun <T> responseFlow(call: Call<T>): Flow<Response<T>> = flow {
    suspendCancellableCoroutine<Response<T>> { continuation ->
        continuation.invokeOnCancellation {
            call.cancel()
        }
        try {
            val response = call.execute()
            val successful = response.isSuccessful
            val code = response.code()
            Log.i("responseFlow", "responseFlow: successful=" + successful + "-code=" + code)
            continuation.resume(response)
        } catch (e: Throwable) {
            Log.i("responseFlow", "responseFlow:message=" + e.message)
            continuation.resumeWithException(e)
        }
    }.let {
        emit(it)
    }
}