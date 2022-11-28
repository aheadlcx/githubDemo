package me.aheadlcx.github.module.login

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import me.aheadlcx.github.R
import me.aheadlcx.github.config.AppConfig
import me.aheadlcx.github.databinding.FragmentLoginOauthBinding
import me.aheadlcx.github.module.base.BaseFragment

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/27 1:55 下午
 */
class LoginOAuthFragment : BaseFragment() {
    companion object {
        private const val TAG = "LoginOAuthFragment"
    }

    private lateinit var binding: FragmentLoginOauthBinding

    private lateinit var loginViewModel: LoginViewModel

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentLoginOauthBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: 自定义构造器 
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel.loginResult.observe(this.viewLifecycleOwner, Observer {
            if (it != null && it == true) {
                //登录成功
                Log.i(TAG, "onViewCreated: login.success.${it}")
                navigationPopUpTo(view, null, R.id.action_nav_login_to_main, true, true)
            } else {
                //登录失败
                Log.i(TAG, "onViewCreated:.failed")
                Toast.makeText(activity, getString(R.string.loginFailed), Toast.LENGTH_LONG).show()
            }
        })
        initWeb()
    }

    private fun initWeb() {
        val settings = binding.oauthWebview.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        settings.domStorageEnabled = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS

        val webViewClient: WebViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.i(TAG, "onPageStarted: ")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.i(TAG, "onPageFinished: ")
                binding.oauthWebviewLoadingBar.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val urlReLoad = request?.url.toString() ?: "emptyUrl"
                Log.i(TAG, "shouldOverrideUrlLoading:urlReLoad=${urlReLoad}")
                if (request != null && request.url != null &&
                    request.url.toString().startsWith(AppConfig.redirect_uri)
                ) {
                    val code = request.url.getQueryParameter("code")
                    if (code != null) {
                        loginViewModel.oauth(code)
                    };
                    return true
                }
                view!!.loadUrl(urlReLoad)
                return false
            }
        }

        binding.oauthWebview.webViewClient = webViewClient

        val url = AppConfig.Login_Url;

        binding.oauthWebview.loadUrl(url)
    }
}