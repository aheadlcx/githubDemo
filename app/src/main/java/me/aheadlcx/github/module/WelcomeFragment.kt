package me.aheadlcx.github.module

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import me.aheadlcx.github.R
import me.aheadlcx.github.config.AppConfig
import me.aheadlcx.github.databinding.FragmentWelcomeBinding
import me.aheadlcx.github.module.base.BaseFragment
import me.aheadlcx.github.utils.GSYPreference

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 3:10 下午
 */
class WelcomeFragment : BaseFragment() {

    companion object {
        private const val TAG = "WelcomeFragment"
    }

    private lateinit var binding: FragmentWelcomeBinding

    /***
     * 委托属性，GSYPreference 把取值和存值的操作代理给 accessTokenStorage
     * 后续的赋值和取值最终是操作的 GSYPreference 得 setValue 和 getValue 函数
     */
    private var accessTokenStorage by GSYPreference(AppConfig.ACCESS_TOKEN, "")

    private var userInfoStorage: String by GSYPreference(AppConfig.USER_INFO, "")

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentWelcomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            checkPermission()
        }, 200)
    }

    fun checkPermission() {
//        val storage = Permission.Group.STORAGE
        val storage = arrayOf(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
//        , "android.permission.MANAGE_EXTERNAL_STORAGE")

        AndPermission.with(this)
            .runtime()
            .permission(storage)
            .onGranted {
                goNext(requireView())
            }.onDenied {
                Log.i(TAG, "checkPermission: onDenied=" + it.toString())
            }.start()
    }

    private fun goNext(view: View) {
        if (accessTokenStorage.isEmpty()) {
            ///去登录页
            navigationPopUpTo(view, null, R.id.action_nav_wel_to_login, false, true)
        } else {
            // TODO: 需要考虑 token 过期么
            if (userInfoStorage.isEmpty()) {
//            if (accessTokenStorage.isEmpty()) {
                ///去登录页
                navigationPopUpTo(view, null, R.id.action_nav_wel_to_login, false, true)
            } else {
                ///读取用户数据
//                val user = GsonUtils.parserJsonToBean(userInfoStorage, User::class.java)
//                AppGlobalModel.userObservable = user
//                UserConversion.cloneDataFromUser(context, user, appGlobalModel.userObservable)
                //去主页
                navigationPopUpTo(view, null, R.id.action_nav_wel_to_main, true, true)
            }

        }
    }

}