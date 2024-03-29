package me.aheadlcx.jetpack.net;

import android.text.TextUtils;
import android.util.Log;

import com.ihsanbal.logging.LoggingInterceptor;

import java.io.IOException;
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
import me.aheadlcx.net.adapter.FlowCallAdapterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/15 2:34 下午
 */
public class FlowRetrofitUtil {
    private static final String TAG = "RetrofitUtil";
    private static Map<String, Retrofit> map = new HashMap<>();
    private static final int DEFAULT_CONNECT_TIMEOUT = 30;
    private static final int DEFAULT_READ_TIMEOUT = 30;

    public static String accessToken = "";
    public static String baseUrl_event = "https://api.github.com/";
    //    private static String baseUrl = "https://github.com/";
    static String baseUrl = "https://www.wanandroid.com/";

    public static void setAccessToken(String accessToken) {
        FlowRetrofitUtil.accessToken = accessToken;
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
        clientBuilder.addInterceptor(loggingInterceptor);
        clientBuilder.addInterceptor(new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                if (!TextUtils.isEmpty(accessToken)) {
                    builder.header("Authorization", "Bearer " + accessToken);
                    Log.i(TAG, "intercept: accessToken=" + accessToken);
                } else {
                    Log.i(TAG, "intercept: accessToken------");
                }
                Request request = builder
                        .build();
                return chain.proceed(request);
            }
        });
        Retrofit.Builder builder = new Retrofit.Builder().client(clientBuilder.build())
                .baseUrl(url)
                .addCallAdapterFactory(FlowCallAdapterFactory.create(false))
//                .addConverterFactory(MyGsonConverterFactory.create(new Gson()));
                .addConverterFactory(GsonConverterFactory.create());// responseBodyConverter
        // 返回null 代表不是自己处理
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
