package me.aheadlcx.github.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.aheadlcx.github.config.AppConfig;

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/15 2:42 下午
 */
public class TokenBeanReq {
   private List<String> scopes = Arrays.asList("user", "repo", "gist", "notifications");
   private String client_id = AppConfig.client_id;
   private String client_secret = AppConfig.client_secret;
   private String code;
   private String redirect_uri = AppConfig.redirect_uri;

   public String getClient_id() {
      return client_id;
   }

   public TokenBeanReq setClient_id(String client_id) {
      this.client_id = client_id;
      return this;
   }

   public String getClient_secret() {
      return client_secret;
   }

   public TokenBeanReq setClient_secret(String client_secret) {
      this.client_secret = client_secret;
      return this;
   }

   public String getCode() {
      return code;
   }

   public TokenBeanReq setCode(String code) {
      this.code = code;
      return this;
   }

   public String getRedirect_uri() {
      return redirect_uri;
   }

   public TokenBeanReq setRedirect_uri(String redirect_uri) {
      this.redirect_uri = redirect_uri;
      return this;
   }

}
