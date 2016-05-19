/*    */ package com.firebase.client.authentication.util;
/*    */ 
/*    */ import com.firebase.client.utilities.Base64;
/*    */ import com.shaded.fasterxml.jackson.core.type.TypeReference;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class JsonWebToken
/*    */ {
/*    */   private final Map<String, Object> header;
/*    */   private final Map<String, Object> claims;
/*    */   private final Object data;
/*    */   private final String signature;
/*    */   
/*    */   private JsonWebToken(Map<String, Object> header, Map<String, Object> claims, Object data, String signature)
/*    */   {
/* 17 */     this.header = header;
/* 18 */     this.claims = claims;
/* 19 */     this.data = data;
/* 20 */     this.signature = signature;
/*    */   }
/*    */   
/*    */   public Map<String, Object> getHeader() {
/* 24 */     return this.header;
/*    */   }
/*    */   
/*    */   public Map<String, Object> getClaims() {
/* 28 */     return this.claims;
/*    */   }
/*    */   
/*    */   public Object getData() {
/* 32 */     return this.data;
/*    */   }
/*    */   
/*    */   public String getSignature() {
/* 36 */     return this.signature;
/*    */   }
/*    */   
/*    */   private static String fixLength(String str) {
/* 40 */     int missing = (4 - str.length() % 4) % 4;
/* 41 */     if (missing == 0) {
/* 42 */       return str;
/*    */     }
/* 44 */     StringBuilder builder = new StringBuilder(str);
/* 45 */     for (int i = 0; i < missing; i++) {
/* 46 */       builder.append("=");
/*    */     }
/* 48 */     return builder.toString();
/*    */   }
/*    */   
/*    */   public static JsonWebToken decode(String token) throws IOException
/*    */   {
/* 53 */     String[] parts = token.split("\\.");
/* 54 */     if (parts.length != 3) {
/* 55 */       throw new IOException("Not a valid token: " + token);
/*    */     }
/* 57 */     TypeReference<Map<String, Object>> mapRef = new TypeReference() {};
/* 58 */     Map<String, Object> header = (Map)com.firebase.client.utilities.encoding.JsonHelpers.getMapper().readValue(Base64.decode(fixLength(parts[0])), mapRef);
/* 59 */     Map<String, Object> claims = (Map)com.firebase.client.utilities.encoding.JsonHelpers.getMapper().readValue(Base64.decode(fixLength(parts[1])), mapRef);
/* 60 */     String signature = parts[2];
/* 61 */     Object data = claims.get("d");
/* 62 */     claims.remove("d");
/*    */     
/* 64 */     return new JsonWebToken(header, claims, data, signature);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/authentication/util/JsonWebToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */