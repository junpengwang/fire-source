/*     */ package org.shaded.apache.http.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ import org.shaded.apache.http.NameValuePair;
/*     */ import org.shaded.apache.http.ParseException;
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
/*     */ public final class EntityUtils
/*     */ {
/*     */   public static byte[] toByteArray(HttpEntity entity)
/*     */     throws IOException
/*     */   {
/*  67 */     if (entity == null) {
/*  68 */       throw new IllegalArgumentException("HTTP entity may not be null");
/*     */     }
/*  70 */     InputStream instream = entity.getContent();
/*  71 */     if (instream == null) {
/*  72 */       return new byte[0];
/*     */     }
/*  74 */     if (entity.getContentLength() > 2147483647L) {
/*  75 */       throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
/*     */     }
/*  77 */     int i = (int)entity.getContentLength();
/*  78 */     if (i < 0) {
/*  79 */       i = 4096;
/*     */     }
/*  81 */     ByteArrayBuffer buffer = new ByteArrayBuffer(i);
/*     */     try {
/*  83 */       byte[] tmp = new byte['က'];
/*     */       int l;
/*  85 */       while ((l = instream.read(tmp)) != -1) {
/*  86 */         buffer.append(tmp, 0, l);
/*     */       }
/*     */     } finally {
/*  89 */       instream.close();
/*     */     }
/*  91 */     return buffer.toByteArray();
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
/*     */   public static String getContentCharSet(HttpEntity entity)
/*     */     throws ParseException
/*     */   {
/* 105 */     if (entity == null) {
/* 106 */       throw new IllegalArgumentException("HTTP entity may not be null");
/*     */     }
/* 108 */     String charset = null;
/* 109 */     if (entity.getContentType() != null) {
/* 110 */       HeaderElement[] values = entity.getContentType().getElements();
/* 111 */       if (values.length > 0) {
/* 112 */         NameValuePair param = values[0].getParameterByName("charset");
/* 113 */         if (param != null) {
/* 114 */           charset = param.getValue();
/*     */         }
/*     */       }
/*     */     }
/* 118 */     return charset;
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
/*     */ 
/*     */   public static String toString(HttpEntity entity, String defaultCharset)
/*     */     throws IOException, ParseException
/*     */   {
/* 135 */     if (entity == null) {
/* 136 */       throw new IllegalArgumentException("HTTP entity may not be null");
/*     */     }
/* 138 */     InputStream instream = entity.getContent();
/* 139 */     if (instream == null) {
/* 140 */       return "";
/*     */     }
/* 142 */     if (entity.getContentLength() > 2147483647L) {
/* 143 */       throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
/*     */     }
/* 145 */     int i = (int)entity.getContentLength();
/* 146 */     if (i < 0) {
/* 147 */       i = 4096;
/*     */     }
/* 149 */     String charset = getContentCharSet(entity);
/* 150 */     if (charset == null) {
/* 151 */       charset = defaultCharset;
/*     */     }
/* 153 */     if (charset == null) {
/* 154 */       charset = "ISO-8859-1";
/*     */     }
/* 156 */     Reader reader = new InputStreamReader(instream, charset);
/* 157 */     CharArrayBuffer buffer = new CharArrayBuffer(i);
/*     */     try {
/* 159 */       char[] tmp = new char['Ѐ'];
/*     */       int l;
/* 161 */       while ((l = reader.read(tmp)) != -1) {
/* 162 */         buffer.append(tmp, 0, l);
/*     */       }
/*     */     } finally {
/* 165 */       reader.close();
/*     */     }
/* 167 */     return buffer.toString();
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
/*     */   public static String toString(HttpEntity entity)
/*     */     throws IOException, ParseException
/*     */   {
/* 183 */     return toString(entity, null);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/util/EntityUtils.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */