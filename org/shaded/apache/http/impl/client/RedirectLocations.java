/*    */ package org.shaded.apache.http.impl.client;
/*    */ 
/*    */ import java.net.URI;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ @NotThreadSafe
/*    */ public class RedirectLocations
/*    */ {
/*    */   private final Set<URI> uris;
/*    */   
/*    */   public RedirectLocations()
/*    */   {
/* 48 */     this.uris = new HashSet();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean contains(URI uri)
/*    */   {
/* 55 */     return this.uris.contains(uri);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void add(URI uri)
/*    */   {
/* 62 */     this.uris.add(uri);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean remove(URI uri)
/*    */   {
/* 69 */     return this.uris.remove(uri);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/RedirectLocations.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */