/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import org.shaded.apache.http.ProtocolVersion;
/*     */ import org.shaded.apache.http.StatusLine;
/*     */ import org.shaded.apache.http.util.CharArrayBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicStatusLine
/*     */   implements StatusLine, Cloneable
/*     */ {
/*     */   private final ProtocolVersion protoVersion;
/*     */   private final int statusCode;
/*     */   private final String reasonPhrase;
/*     */   
/*     */   public BasicStatusLine(ProtocolVersion version, int statusCode, String reasonPhrase)
/*     */   {
/*  69 */     if (version == null) {
/*  70 */       throw new IllegalArgumentException("Protocol version may not be null.");
/*     */     }
/*     */     
/*  73 */     if (statusCode < 0) {
/*  74 */       throw new IllegalArgumentException("Status code may not be negative.");
/*     */     }
/*     */     
/*  77 */     this.protoVersion = version;
/*  78 */     this.statusCode = statusCode;
/*  79 */     this.reasonPhrase = reasonPhrase;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getStatusCode()
/*     */   {
/*  85 */     return this.statusCode;
/*     */   }
/*     */   
/*     */   public ProtocolVersion getProtocolVersion() {
/*  89 */     return this.protoVersion;
/*     */   }
/*     */   
/*     */   public String getReasonPhrase() {
/*  93 */     return this.reasonPhrase;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  98 */     return BasicLineFormatter.DEFAULT.formatStatusLine(null, this).toString();
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException
/*     */   {
/* 103 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicStatusLine.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */