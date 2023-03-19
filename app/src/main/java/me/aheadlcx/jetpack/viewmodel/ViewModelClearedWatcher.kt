package me.aheadlcx.jetpack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * Description:
 * author: aheadlcx
 * Date:2023/2/7 11:00 上午
 */
class ViewModelClearedWatcher(
    storeOwner: ViewModelStoreOwner,
//    private val reachabilityWatcher: ReachabilityWatcher
    private val reachabilityWatcher: Any
) : ViewModel() {

    // We could call ViewModelStore#keys with a package spy in androidx.lifecycle instead,
    // however that was added in 2.1.0 and we support AndroidX first stable release. viewmodel-2.0.0
    // does not have ViewModelStore#keys. All versions currently have the mMap field.
    private val viewModelMap: Map<String, ViewModel>? = try {
        val mMapField = ViewModelStore::class.java.getDeclaredField("mMap")
        mMapField.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        mMapField[storeOwner.viewModelStore] as Map<String, ViewModel>
    } catch (ignored: Exception) {
        null
    }

    override fun onCleared() {
        viewModelMap?.values?.forEach { viewModel ->
//            reachabilityWatcher.expectWeaklyReachable(
//                viewModel, "${viewModel::class.java.name} received ViewModel#onCleared() callback"
//            )
        }
    }

//    companion object {
//        fun install(
//            storeOwner: ViewModelStoreOwner,
////            reachabilityWatcher: ReachabilityWatcher
//            reachabilityWatcher: Any
//        ) {
//            val provider = ViewModelProvider(storeOwner, object : ViewModelProvider.Factory {
//                @Suppress("UNCHECKED_CAST")
//                override fun <T : ViewModel?> create(modelClass: Class<T>): T =
//                    ViewModelClearedWatcher(storeOwner, reachabilityWatcher) as T
//            })
//            //仅仅是向 ViewModelStoreOwner 的 ViewStore 中注入自己的 ViewModel。如果 Activity 要销毁了，就会调用全部的
//            // ViewModel 的 onCleared 方法
//            provider.get(ViewModelClearedWatcher::class.java)
//        }
//    }
}