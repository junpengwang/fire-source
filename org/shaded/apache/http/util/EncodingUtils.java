/*     */ package org.shaded.apache.http.util;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
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
/*     */ 
/*     */ public final class EncodingUtils
/*     */ {
/*     */   public static String getString(byte[] data, int offset, int length, String charset)
/*     */   {
/*  63 */     if (data == null) {
/*  64 */       throw new IllegalArgumentException("Parameter may not be null");
/*     */     }
/*     */     
/*  67 */     if ((charset == null) || (charset.length() == 0)) {
/*  68 */       throw new IllegalArgumentException("charset may not be null or empty");
/*     */     }
/*     */     try
/*     */     {
/*  72 */       return new String(data, offset, length, charset);
/*     */     } catch (UnsupportedEncodingException e) {}
/*  74 */     return new String(data, offset, length);
/*     */   }
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
/*     */   public static String getString(byte[] data, String charset)
/*     */   {
/*  89 */     if (data == null) {
/*  90 */       throw new IllegalArgumentException("Parameter may not be null");
/*     */     }
/*  92 */     return getString(data, 0, data.length, charset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] getBytes(String data, String charset)
/*     */   {
/* 105 */     if (data == null) {
/* 106 */       throw new IllegalArgumentException("data may not be null");
/*     */     }
/*     */     
/* 109 */     if ((charset == null) || (charset.length() == 0)) {
/* 110 */       throw new IllegalArgumentException("charset may not be null or empty");
/*     */     }
/*     */     try
/*     */     {
/* 114 */       return data.getBytes(charset);
/*     */     } catch (UnsupportedEncodingException e) {}
/* 116 */     return data.getBytes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] getAsciiBytes(String data)
/*     */   {
/* 128 */     if (data == null) {
/* 129 */       throw new IllegalArgumentException("Parameter may not be null");
/*     */     }
/*     */     try
/*     */     {
/* 133 */       return data.getBytes("US-ASCII");
/*     */     } catch (UnsupportedEncodingException e) {
/* 135 */       throw new Error("HttpClient requires ASCII support");
/*     */     }
/*     */   }
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
/*     */   public static String getAsciiString(byte[] data, int offset, int length)
/*     */   {
/* 151 */     if (data == null) {
/* 152 */       throw new IllegalArgumentException("Parameter may not be null");
/*     */     }
/*     */     try
/*     */     {
/* 156 */       return new String(data, offset, length, "US-ASCII");
/*     */     } catch (UnsupportedEncodingException e) {
/* 158 */       throw new Error("HttpClient requires ASCII support");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getAsciiString(byte[] data)
/*     */   {
/* 171 */     if (data == null) {
/* 172 */       throw new IllegalArgumentException("Parameter may not be null");
/*     */     }
/* 174 */     return getAsciiString(data, 0, data.length);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/util/EncodingUtils.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */