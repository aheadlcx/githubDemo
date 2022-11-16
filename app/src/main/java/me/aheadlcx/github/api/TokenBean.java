package me.aheadlcx.github.api;

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/15 2:40 下午
 */
public class TokenBean {
   private String access_token;
   private String scope;
   private String token_type;

   public String getAccess_token() {
      return access_token;
   }

   public TokenBean setAccess_token(String access_token) {
      this.access_token = access_token;
      return this;
   }

   public String getScope() {
      return scope;
   }

   public TokenBean setScope(String scope) {
      this.scope = scope;
      return this;
   }

   public String getToken_type() {
      return token_type;
   }

   public TokenBean setToken_type(String token_type) {
      this.token_type = token_type;
      return this;
   }
}
