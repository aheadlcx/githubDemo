package me.aheadlcx.github.util

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/16 5:17 下午
 */

fun <T> CoroutineScope.rxLaunch(init: CoroutineBuilder<T>.() -> Unit) {
    val result = CoroutineBuilder<T>().apply(init)
    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        result.onError?.invoke(throwable)
    }
    launch(coroutineExceptionHandler) {
        val ret = result.onRequest?.invoke()
        ret?.let {
            result.onSuccess?.invoke(ret)
        }
    }
}

class CoroutineBuilder<T> {
    var onRequest: (suspend () -> T)? = null
    var onSuccess: ((T) -> Unit)? = null
    var onError: ((Throwable) -> Unit)? = null;
}
//}