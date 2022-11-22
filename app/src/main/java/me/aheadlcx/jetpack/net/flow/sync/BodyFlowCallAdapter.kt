package me.aheadlcx.jetpack.net.flow.sync

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class BodyFlowCallAdapter<R>(private val responseType: R) : CallAdapter<R, Flow<R>> {
    override fun responseType(): Type {
        return responseType as Type
    }

    override fun adapt(call: Call<R>): Flow<R> {
        val flow = flow<R> {
            suspendCancellableCoroutine<R> { continuation ->
                continuation.invokeOnCancellation {
                    call.cancel()
                }
                try {
                    val response = call.execute()
                    val successful = response.isSuccessful
                    val code = response.code()
                    Log.i(
                        "responseFlow",
                        "responseFlow: successful=" + successful + "-code=" + code
                    )
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!)
                    } else {
                        continuation.resumeWithException(HttpException(response))
                    }
                } catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            }
        }
        return flow
    }
}