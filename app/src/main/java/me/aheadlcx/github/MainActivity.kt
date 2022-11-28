package me.aheadlcx.github

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import me.aheadlcx.github.api.GithubApiServiceManager
import me.aheadlcx.net.RetrofitUtil
import me.aheadlcx.github.api.TokenBeanReq
import me.aheadlcx.github.databinding.ActivityMainBinding
import me.aheadlcx.util.rxLaunch

class MainActivity : AppCompatActivity() {

    companion object {
        init {
            System.loadLibrary("github")
        }

        const val TAG = "MainActivity"
//        const val redirect_uri = "https://www.163.com/"
        const val redirect_uri = "aheadlcx://github/authed"
        const val client_id = "84d56022a54eeb45eecf"
        const val client_secret = "af29bf5a1dfff246d342eef8e6db1e4106f9777e"
        const val url = "https://github.com/login/oauth/authorize?client_id=${client_id}" +
                "&redirect_uri=${redirect_uri}&scope=user"
    }

    private lateinit var binding: ActivityMainBinding
    private var isLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initWebView()
        initListener()
    }

    private fun initWebView() {
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        val webViewClient = MyWebViewClient(this)

        binding.webView.webViewClient = webViewClient
    }

    private fun initListener() {
        binding.txtUser.setOnClickListener(View.OnClickListener {
            getUserInfoFlow()
        })
        binding.txtLogin.setOnClickListener {
            Log.i(TAG, "initListener: webView.loadUrl=${url},isLogin=${isLogin}")
            if (isLogin) {
                return@setOnClickListener
            }
            binding.webView.loadUrl(url)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun test() {
        lifecycleScope.rxLaunch<String> {
            onRequest = {
                "dataFromRequest"
            }
            onSuccess = {
                Log.i(TAG, "test: onSuccess.it= ${it}")
            }
            onError = {

            }
        }

        lifecycleScope.launch {
            kotlin.runCatching {
                "请求数据"
            }.onSuccess {
                Log.i(TAG, "test: 成功= " + it)
            }.onFailure {
                Log.i(TAG, "test: 失败=" + it.message)
            }.getOrNull()?.let {
                Log.i(TAG, "test: 数据为空")
            }
        }
    }

    private fun getUserInfoFlow() {
        lifecycleScope.launch {
            GithubApiServiceManager.userService.getUserInfoByFlow(getOauth())
                .flowOn(Dispatchers.IO)
                .catch { cause ->
                    Log.i(TAG, "getUserInfoFlow:catch errorMsg=" + cause.message)
                }
                .onCompletion { cause ->
                    Log.i(TAG, "getUserInfoFlow: onCompletion.errorMsg" + cause?.message)
                }
                .collect {
                    Log.i(TAG, "getUserInfoFlow:请求数据成功=${it}")
                }
        }
    }

    private fun getOauth(): String {
        return "Bearer " + RetrofitUtil.accessToken
    }

    private fun onHandleGetCode(code: String) {
        binding.webView.visibility = View.GONE
        getTokenByCode(code)
        binding.txtLogin.text = "已经登录"
        isLogin = true
    }

    private fun getTokenByCode(code: String) {
        Log.i(TAG, "getTokenByCode: code=${code}")
        var req = TokenBeanReq()
        req.let {
            it.code = code
            it.client_id = client_id
            it.client_secret = client_secret
        }
        lifecycleScope.launch {
            GithubApiServiceManager.gitHubService.getTokenByCodeFlow(req)
                .flowOn(Dispatchers.IO)
                .catch { cause ->
                    Log.i(TAG, "getTokenByCodeFlow:catch.errorMsg=${cause.message}")
                }.collect {
                    val accessToken = it.access_token
                    RetrofitUtil.setAccessToken(accessToken)
                    Log.i(TAG, "getTokenByCodeFlow: accessToken=${accessToken}")
                }
        }
    }

    class MyWebViewClient(val activity: MainActivity) : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request != null && request.url != null &&
                request.url.toString().startsWith(redirect_uri)) {
                val code = request.url.getQueryParameter("code")
                if (code != null) {
                    activity.onHandleGetCode(code)
                };
                return true
            }
            return false
        }

//        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//            view!!.loadUrl(url!!)
//            Log.i(TAG, "shouldOverrideUrlLoading: url=$url")
//            Log.i(TAG, "shouldOverrideUrlLoading: redirect_uri=$redirect_uri")
//            if (url.startsWith(redirect_uri) && url.startsWith(redirect_uri + "?code=")) {
//                val code: String = url.replace(redirect_uri + "?code=", "")
//                activity.onHandleGetCode(code)
//            }
//            return true
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }



    /**
     * A native method that is implemented by the 'github' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
}