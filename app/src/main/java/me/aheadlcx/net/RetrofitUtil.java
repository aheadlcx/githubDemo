package me.aheadlcx.net;

import com.google.gson.Gson;
import com.ihsanbal.logging.LoggingInterceptor;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import me.aheadlcx.github.api.GitHubService;
import me.aheadlcx.github.api.GitHubServiceKotlin;
import me.aheadlcx.jetpack.net.interceptor.AccessTokenAuthenticator;
import me.aheadlcx.jetpack.net.interceptor.TestInterceptor;
import me.aheadlcx.net.converter.MyGsonConverterFactory;
import me.aheadlcx.jetpack.net.interceptor.GithubTokenInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/15 2:34 下午
 */
public class RetrofitUtil {
    private static final String TAG = "RetrofitUtil";
    private static Map<String, Retrofit> map = new HashMap<>();
    private static final int DEFAULT_CONNECT_TIMEOUT = 30;
    private static final int DEFAULT_READ_TIMEOUT = 30;

    public static String accessToken = "";
    public static String baseUrl_event = "https://api.github.com/";
    private static String baseUrl = "https://github.com/";

    public static void setAccessToken(String accessToken) {
        RetrofitUtil.accessToken = accessToken;
    }

    public static Retrofit init() {
        return init(baseUrl);
    }

    public static Retrofit init(String url) {
        Retrofit retrofit = map.get(url);
        if (retrofit != null) {
            return retrofit;
        }

        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor.Builder().build();
        clientBuilder
//                .authenticator(new AccessTokenAuthenticator())
                .addInterceptor(loggingInterceptor);
        clientBuilder.addInterceptor(new GithubTokenInterceptor());
        clientBuilder.addInterceptor(new TestInterceptor());
        OkHttpClient okHttpClient = clientBuilder.build();
        Retrofit.Builder builder = new Retrofit.Builder().client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(MyGsonConverterFactory.create(new Gson()));
//                .addConverterFactory(CustomGsonConverterFactory.create())
//                .addCallAdapterFactory(new ApiResultCallAdapterFactory());
        clientBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });


        Retrofit retrofitRes = builder.build();
        map.put(url, retrofitRes);
        return retrofitRes;
    }

    public static Retrofit getRetrofit() {
        return getRetrofit(baseUrl);
    }

    public static Retrofit getRetrofit(String url) {
        return init(url);
    }

    public static GitHubService getGithubService() {
        return getGithubService(baseUrl);
    }

    public static GitHubService getGithubService(String url) {
        return getRetrofit(url).create(GitHubService.class);
    }

    public static GitHubServiceKotlin getGithubServiceKotlin(String url) {
        return getRetrofit(url).create(GitHubServiceKotlin.class);
    }

    private static SSLSocketFactory sslSocketFactory(X509TrustManager x509Manager) {

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509Manager}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
}
