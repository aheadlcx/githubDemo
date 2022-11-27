package me.aheadlcx.github.module.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import me.aheadlcx.github.config.AppConfig
import me.aheadlcx.github.module.base.BaseViewModel
import me.aheadlcx.github.repository.LoginRepository
import me.aheadlcx.github.utils.GSYPreference

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 1:59 下午
 */
class LoginViewModel() : BaseViewModel() {
    private val repo: LoginRepository = LoginRepository()
    private var usernameStorage: String by GSYPreference(AppConfig.USER_NAME, "")

    private var passwordStorage: String by GSYPreference(AppConfig.PASSWORD, "")

    /**
     * 登录结果
     */
    val loginResult = MutableLiveData<Boolean>()


    /**
     * 登录执行
     */
    fun oauth(code: String) {
        repo.oauth(viewModelScope, code, loginResult)

    }
}