package me.aheadlcx.github

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import me.aheadlcx.github.api.GithubApiServiceManager
import me.aheadlcx.net.RetrofitUtil
import me.aheadlcx.github.api.TokenBeanReq
import me.aheadlcx.github.databinding.ActivityMainBinding
import me.aheadlcx.util.rxLaunch

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    var scope = MainScope()

    companion object {
        init {
            System.loadLibrary("github")
        }

        const val TAG = "MainActivity"
        const val redirect_uri = "https://www.163.com/"
        const val client_id = "84d56022a54eeb45eecf"
        const val client_secret = "af29bf5a1dfff246d342eef8e6db1e4106f9777e"
        const val url = "https://github.com/login/oauth/authorize?client_id=${client_id}" +
                "&redirect_uri=${redirect_uri}&scope=user"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var mWebView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initWebView()
        initListener()
    }

    private fun initWebView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        val webViewClient = MyWebViewClient(this)

        binding.webView.webViewClient = webViewClient
    }

    private fun initListener() {
        binding.txtUser.setOnClickListener(View.OnClickListener {
//            getUserInfo()
//            getUserInfoKotlin()
            getUserInfoFlow()
        })
        binding.txtLogin.setOnClickListener {
            Log.i(TAG, "initListener: webView.loadUrl=${url}")
            binding.webView.loadUrl(url)
        }
    }

    private fun test() {
        scope.rxLaunch<String> {
            onRequest = {
                "dataFromRequest"
            }
            onSuccess = {
                Log.i(TAG, "test: onSuccess.it= ${it}")
            }
            onError = {

            }
        }

        scope.launch {
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
        launch {
            GithubApiServiceManager.gitHubService.getUserInfoByFlow(getOauth())
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

    private fun getUserInfoKotlin() {
        scope.launch {
            val userInfo = RetrofitUtil.getGithubServiceKotlin(
                RetrofitUtil.baseUrl_event
            )
                .getUserInfoSub(getOauth())
            Log.i(TAG, "getUserInfoKotlin:userName= ${userInfo.login}")
            binding.txtShowInfo.post({
                binding.txtShowInfo.text = userInfo.login
            })
        }
    }

    private fun getOauth(): String {
        return "Bearer " + RetrofitUtil.accessToken
    }

    private fun getUserInfo() {
        Log.i(TAG, "getUserInfo: ")
        GlobalScope.launch(Dispatchers.IO) {
//            val userInfo = RetrofitUtil.getGithubService().getUserInfo("Bearer " + RetrofitUtil.accessToken)
            val userInfo = RetrofitUtil.getGithubService(
                RetrofitUtil.baseUrl_event
            )
                .getUserInfo(getOauth())
            try {
                val response = userInfo.execute()
                val body = response.body()
                Log.i(TAG, "getUserInfo:isSuccessful=" + response.isSuccessful)
                binding.txtShowInfo.post({
                    binding.txtShowInfo.text = body?.login
                })
            } catch (e: Exception) {
                Log.i(TAG, "getUserInfo: error=${e.message}")
            }
        }
    }

    private fun getTokenByCode(code: String) {
        Log.i(TAG, "getTokenByCode: code=${code}")
        var req = TokenBeanReq()
        req.let {
            it.code = code
            it.client_id = client_id
            it.client_secret = client_secret
        }
        launch {
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
        launch(Dispatchers.IO) {
            val tokenByCode = RetrofitUtil.getGithubService().getTokenByCode(req)
            try {
                val response = tokenByCode.execute()
                val body = response.body()
                val toJson = Gson().toJson(body)
                val accessToken = body?.access_token
                RetrofitUtil.setAccessToken(accessToken)
                Log.i(TAG, "getTokenByCode: accessToken=${accessToken}")
                Log.i(TAG, "getTokenByCode: toJson=${toJson}")
            } catch (e: Exception) {
                Log.i(TAG, "getTokenByCode: Exception=${e.message}")

            }
        }
    }

    class MyWebViewClient(val activity: MainActivity) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view!!.loadUrl(url!!)
            Log.i(TAG, "shouldOverrideUrlLoading: url=$url")
            Log.i(TAG, "shouldOverrideUrlLoading: redirect_uri=$redirect_uri")
            if (url.startsWith(redirect_uri) && url.startsWith(redirect_uri + "?code=")) {
                val code: String = url.replace(redirect_uri + "?code=", "")
                activity.getTokenByCode(code)
            }
            return true
        }
    }

    /**
     * A native method that is implemented by the 'github' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
}