/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.shaded.apache.http.NameValuePair;
/*     */ import org.shaded.apache.http.util.CharArrayBuffer;
/*     */ import org.shaded.apache.http.util.LangUtils;
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
/*     */ public class BasicNameValuePair
/*     */   implements NameValuePair, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6437800749411518984L;
/*     */   private final String name;
/*     */   private final String value;
/*     */   
/*     */   public BasicNameValuePair(String name, String value)
/*     */   {
/*  60 */     if (name == null) {
/*  61 */       throw new IllegalArgumentException("Name may not be null");
/*     */     }
/*  63 */     this.name = name;
/*  64 */     this.value = value;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  68 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  72 */     return this.value;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     if (this.value == null) {
/*  79 */       return this.name;
/*     */     }
/*  81 */     int len = this.name.length() + 1 + this.value.length();
/*  82 */     CharArrayBuffer buffer = new CharArrayBuffer(len);
/*  83 */     buffer.append(this.name);
/*  84 */     buffer.append("=");
/*  85 */     buffer.append(this.value);
/*  86 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   public boolean equals(Object object)
/*     */   {
/*  91 */     if (object == null) return false;
/*  92 */     if (this == object) return true;
/*  93 */     if ((object instanceof NameValuePair)) {
/*  94 */       BasicNameValuePair that = (BasicNameValuePair)object;
/*  95 */       return (this.name.equals(that.name)) && (LangUtils.equals(this.value, that.value));
/*     */     }
/*     */     
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 103 */     int hash = 17;
/* 104 */     hash = LangUtils.hashCode(hash, this.name);
/* 105 */     hash = LangUtils.hashCode(hash, this.value);
/* 106 */     return hash;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 110 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicNameValuePair.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */