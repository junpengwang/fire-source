/*    */ package org.shaded.apache.http.client.methods;
/*    */ 
/*    */ import java.net.URI;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.shaded.apache.http.Header;
/*    */ import org.shaded.apache.http.HeaderElement;
/*    */ import org.shaded.apache.http.HeaderIterator;
/*    */ import org.shaded.apache.http.HttpResponse;
/*    */ import org.shaded.apache.http.annotation.NotThreadSafe;
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
/*    */ 
/*    */ @NotThreadSafe
/*    */ public class HttpOptions
/*    */   extends HttpRequestBase
/*    */ {
/*    */   public static final String METHOD_NAME = "OPTIONS";
/*    */   
/*    */   public HttpOptions() {}
/*    */   
/*    */   public HttpOptions(URI uri)
/*    */   {
/* 69 */     setURI(uri);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public HttpOptions(String uri)
/*    */   {
/* 77 */     setURI(URI.create(uri));
/*    */   }
/*    */   
/*    */   public String getMethod()
/*    */   {
/* 82 */     return "OPTIONS";
/*    */   }
/*    */   
/*    */   public Set<String> getAllowedMethods(HttpResponse response) {
/* 86 */     if (response == null) {
/* 87 */       throw new IllegalArgumentException("HTTP response may not be null");
/*    */     }
/*    */     
/* 90 */     HeaderIterator it = response.headerIterator("Allow");
/* 91 */     Set<String> methods = new HashSet();
/* 92 */     while (it.hasNext()) {
/* 93 */       Header header = it.nextHeader();
/* 94 */       HeaderElement[] elements = header.getElements();
/* 95 */       for (HeaderElement element : elements) {
/* 96 */         methods.add(element.getName());
/*    */       }
/*    */     }
/* 99 */     return methods;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/methods/HttpOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */