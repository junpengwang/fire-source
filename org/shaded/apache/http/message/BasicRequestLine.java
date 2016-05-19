/*    */ package org.shaded.apache.http.message;
/*    */ 
/*    */ import org.shaded.apache.http.ProtocolVersion;
/*    */ import org.shaded.apache.http.RequestLine;
/*    */ import org.shaded.apache.http.util.CharArrayBuffer;
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
/*    */ public class BasicRequestLine
/*    */   implements RequestLine, Cloneable
/*    */ {
/*    */   private final ProtocolVersion protoversion;
/*    */   private final String method;
/*    */   private final String uri;
/*    */   
/*    */   public BasicRequestLine(String method, String uri, ProtocolVersion version)
/*    */   {
/* 55 */     if (method == null) {
/* 56 */       throw new IllegalArgumentException("Method must not be null.");
/*    */     }
/*    */     
/* 59 */     if (uri == null) {
/* 60 */       throw new IllegalArgumentException("URI must not be null.");
/*    */     }
/*    */     
/* 63 */     if (version == null) {
/* 64 */       throw new IllegalArgumentException("Protocol version must not be null.");
/*    */     }
/*    */     
/* 67 */     this.method = method;
/* 68 */     this.uri = uri;
/* 69 */     this.protoversion = version;
/*    */   }
/*    */   
/*    */   public String getMethod() {
/* 73 */     return this.method;
/*    */   }
/*    */   
/*    */   public ProtocolVersion getProtocolVersion() {
/* 77 */     return this.protoversion;
/*    */   }
/*    */   
/*    */   public String getUri() {
/* 81 */     return this.uri;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 86 */     return BasicLineFormatter.DEFAULT.formatRequestLine(null, this).toString();
/*    */   }
/*    */   
/*    */   public Object clone() throws CloneNotSupportedException
/*    */   {
/* 91 */     return super.clone();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicRequestLine.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */