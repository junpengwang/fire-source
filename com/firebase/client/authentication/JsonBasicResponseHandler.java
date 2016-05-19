/*    */ package com.firebase.client.authentication;
/*    */ 
/*    */ import com.firebase.client.utilities.encoding.JsonHelpers;
/*    */ import com.shaded.fasterxml.jackson.core.type.TypeReference;
/*    */ import com.shaded.fasterxml.jackson.databind.ObjectMapper;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Map;
/*    */ import org.shaded.apache.http.HttpEntity;
/*    */ import org.shaded.apache.http.HttpResponse;
/*    */ import org.shaded.apache.http.client.ResponseHandler;
/*    */ 
/*    */ class JsonBasicResponseHandler implements ResponseHandler<Map<String, Object>>
/*    */ {
/*    */   public Map<String, Object> handleResponse(HttpResponse response) throws IOException
/*    */   {
/* 17 */     if (response == null) {
/* 18 */       return null;
/*    */     }
/* 20 */     HttpEntity entity = response.getEntity();
/* 21 */     if (entity != null) {
/* 22 */       InputStream is = entity.getContent();
/*    */       try {
/* 24 */         (Map)JsonHelpers.getMapper().readValue(is, new TypeReference() {});
/*    */       }
/*    */       finally {
/* 27 */         is.close();
/*    */       }
/*    */     }
/* 30 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/authentication/JsonBasicResponseHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */