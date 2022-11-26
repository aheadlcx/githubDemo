package me.aheadlcx.jetpack

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/21 10:04 下午
 */
class MyViewModel: ViewModel() {

    companion object{
        private const val TAG = "MyViewModel"
    }
    val nameLiveData = MutableLiveData<String>()


    private fun test(){
    }


    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ")
    }
}