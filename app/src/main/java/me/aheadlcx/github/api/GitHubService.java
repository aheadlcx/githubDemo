package me.aheadlcx.github.api;

import java.util.List;

import me.aheadlcx.github.api.bean.UserInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/15 2:35 下午
 */
public interface GitHubService {

    @GET("users/{user}/repos")
    Call<List<Object>> listRepos(@Path("user") String user);

    @Headers({
            "Accept:application/json"
    })
    @POST("login/oauth/access_token")
    Call<TokenBean> getTokenByCode(@Body TokenBeanReq req);


    @Headers({
            "Accept:application/vnd.github+json"
    })
    @GET("user")
    Call<UserInfo> getUserInfo(@Header("Authorization") String auth);

    @Headers({
            "Accept:application/json"
    })
    @GET("users/aheadlcx/events/public")
    Call<Object> getUserEvent(@Header("Authorization") String auth);

}
