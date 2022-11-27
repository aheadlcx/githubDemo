package me.aheadlcx.github.module.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 1:44 下午
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingView = getBindingView(inflater, container)
        onCreateView(bindingView.rootView)
        return bindingView
    }


//    class ParamViewModelFactory<VM : ViewModel>(
//        private val factory: () -> VM,
//    ) : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T = factory() as T
//    }
////
//    inline fun <reified VM : ViewModel> Fragment.viewModel(
//        noinline factory: () -> VM,
//    ): Lazy<VM> = viewModels { ParamViewModelFactory(factory) }

    open fun actionOpenByBrowser() {

    }

    open fun actionCopy() {

    }

    open protected fun onCreateView(mainView: View?){}



    abstract fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View

    /**
     * Navigation 的页面跳转
     */
    fun navigationPopUpTo(
        view: View,
        args: Bundle?,
        actionId: Int,
        finishStack: Boolean,
        inclusive: Boolean
    ) {
        val controller = Navigation.findNavController(view)
        controller.navigate(
            actionId,
            args, NavOptions.Builder().setPopUpTo(controller.graph.id, inclusive).build()
        )
        if (finishStack) {
            activity?.finish()
        }
    }

    fun navigationBack(view: View) {
        val controller = Navigation.findNavController(view)
        controller.popBackStack()
    }

    fun exitFull() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
        )
    }

}