package me.aheadlcx.jetpack.coroutine

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/26 3:42 下午
 */
class CoroutineShowCase {

    companion object {
        private const val TAG = "CoroutineShowCase"
    }

    public fun methodOne() {
        MainScope().launch {
            val name = getName()
            val age = getAge()
            val money = getMoney()
            val loveFemale = getLoveFemale()
            val people = "name=${name}, age=${age}, money=${money}, loveFemale=${loveFemale}"
            Log.i(TAG, "methodOne: people=${people}")
        }
    }

    private suspend fun getName(): String {
        val withContext = withContext(Dispatchers.IO) {
            "aheadlcx"
        }
        return withContext
    }

    private suspend fun getAge(): Int {
        return 18
    }

    private suspend fun getMoney():String{
        return "1024"
    }

    private suspend fun getLoveFemale():String{
        return "Paipai"
    }
}