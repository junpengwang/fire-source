/*    */ package org.shaded.apache.http.impl.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.shaded.apache.http.HttpEntity;
/*    */ import org.shaded.apache.http.HttpResponse;
/*    */ import org.shaded.apache.http.StatusLine;
/*    */ import org.shaded.apache.http.annotation.Immutable;
/*    */ import org.shaded.apache.http.client.HttpResponseException;
/*    */ import org.shaded.apache.http.client.ResponseHandler;
/*    */ import org.shaded.apache.http.util.EntityUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class BasicResponseHandler
/*    */   implements ResponseHandler<String>
/*    */ {
/*    */   public String handleResponse(HttpResponse response)
/*    */     throws HttpResponseException, IOException
/*    */   {
/* 65 */     StatusLine statusLine = response.getStatusLine();
/* 66 */     if (statusLine.getStatusCode() >= 300) {
/* 67 */       throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
/*    */     }
/*    */     
/*    */ 
/* 71 */     HttpEntity entity = response.getEntity();
/* 72 */     return entity == null ? null : EntityUtils.toString(entity);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/BasicResponseHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */