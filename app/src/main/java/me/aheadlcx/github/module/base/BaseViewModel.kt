package me.aheadlcx.github.module.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 2:03 下午
 */
open class BaseViewModel: ViewModel() {

    protected fun launchOnMain(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch (Dispatchers.Main){
            block()
        }
    }

    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch (Dispatchers.IO){
            block()
        }
    }
}