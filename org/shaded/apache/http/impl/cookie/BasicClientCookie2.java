/*    */ package org.shaded.apache.http.impl.cookie;
/*    */ 
/*    */ import java.util.Date;
/*    */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*    */ import org.shaded.apache.http.cookie.SetCookie2;
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
/*    */ public class BasicClientCookie2
/*    */   extends BasicClientCookie
/*    */   implements SetCookie2
/*    */ {
/*    */   private String commentURL;
/*    */   private int[] ports;
/*    */   private boolean discard;
/*    */   
/*    */   public BasicClientCookie2(String name, String value)
/*    */   {
/* 55 */     super(name, value);
/*    */   }
/*    */   
/*    */   public int[] getPorts()
/*    */   {
/* 60 */     return this.ports;
/*    */   }
/*    */   
/*    */   public void setPorts(int[] ports) {
/* 64 */     this.ports = ports;
/*    */   }
/*    */   
/*    */   public String getCommentURL()
/*    */   {
/* 69 */     return this.commentURL;
/*    */   }
/*    */   
/*    */   public void setCommentURL(String commentURL) {
/* 73 */     this.commentURL = commentURL;
/*    */   }
/*    */   
/*    */   public void setDiscard(boolean discard) {
/* 77 */     this.discard = discard;
/*    */   }
/*    */   
/*    */   public boolean isPersistent()
/*    */   {
/* 82 */     return (!this.discard) && (super.isPersistent());
/*    */   }
/*    */   
/*    */   public boolean isExpired(Date date)
/*    */   {
/* 87 */     return (this.discard) || (super.isExpired(date));
/*    */   }
/*    */   
/*    */   public Object clone() throws CloneNotSupportedException
/*    */   {
/* 92 */     BasicClientCookie2 clone = (BasicClientCookie2)super.clone();
/* 93 */     clone.ports = ((int[])this.ports.clone());
/* 94 */     return clone;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/BasicClientCookie2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */