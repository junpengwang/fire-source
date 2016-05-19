/*     */ package org.shaded.apache.http.client.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URI;
/*     */ import java.net.URLDecoder;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Scanner;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ import org.shaded.apache.http.NameValuePair;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.message.BasicNameValuePair;
/*     */ import org.shaded.apache.http.util.EntityUtils;
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
/*     */ @Immutable
/*     */ public class URLEncodedUtils
/*     */ {
/*     */   public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
/*     */   private static final String PARAMETER_SEPARATOR = "&";
/*     */   private static final String NAME_VALUE_SEPARATOR = "=";
/*     */   
/*     */   public static List<NameValuePair> parse(URI uri, String encoding)
/*     */   {
/*  76 */     List<NameValuePair> result = Collections.emptyList();
/*  77 */     String query = uri.getRawQuery();
/*  78 */     if ((query != null) && (query.length() > 0)) {
/*  79 */       result = new ArrayList();
/*  80 */       parse(result, new Scanner(query), encoding);
/*     */     }
/*  82 */     return result;
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
/*     */   public static List<NameValuePair> parse(HttpEntity entity)
/*     */     throws IOException
/*     */   {
/*  99 */     List<NameValuePair> result = Collections.emptyList();
/*     */     
/* 101 */     String contentType = null;
/* 102 */     String charset = null;
/*     */     
/* 104 */     Header h = entity.getContentType();
/* 105 */     if (h != null) {
/* 106 */       HeaderElement[] elems = h.getElements();
/* 107 */       if (elems.length > 0) {
/* 108 */         HeaderElement elem = elems[0];
/* 109 */         contentType = elem.getName();
/* 110 */         NameValuePair param = elem.getParameterByName("charset");
/* 111 */         if (param != null) {
/* 112 */           charset = param.getValue();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 117 */     if ((contentType != null) && (contentType.equalsIgnoreCase("application/x-www-form-urlencoded"))) {
/* 118 */       String content = EntityUtils.toString(entity, "ASCII");
/* 119 */       if ((content != null) && (content.length() > 0)) {
/* 120 */         result = new ArrayList();
/* 121 */         parse(result, new Scanner(content), charset);
/*     */       }
/*     */     }
/* 124 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isEncoded(HttpEntity entity)
/*     */   {
/* 132 */     Header h = entity.getContentType();
/* 133 */     if (h != null) {
/* 134 */       HeaderElement[] elems = h.getElements();
/* 135 */       if (elems.length > 0) {
/* 136 */         String contentType = elems[0].getName();
/* 137 */         return contentType.equalsIgnoreCase("application/x-www-form-urlencoded");
/*     */       }
/* 139 */       return false;
/*     */     }
/*     */     
/* 142 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void parse(List<NameValuePair> parameters, Scanner scanner, String encoding)
/*     */   {
/* 164 */     scanner.useDelimiter("&");
/* 165 */     while (scanner.hasNext()) {
/* 166 */       String[] nameValue = scanner.next().split("=");
/* 167 */       if ((nameValue.length == 0) || (nameValue.length > 2)) {
/* 168 */         throw new IllegalArgumentException("bad parameter");
/*     */       }
/* 170 */       String name = decode(nameValue[0], encoding);
/* 171 */       String value = null;
/* 172 */       if (nameValue.length == 2)
/* 173 */         value = decode(nameValue[1], encoding);
/* 174 */       parameters.add(new BasicNameValuePair(name, value));
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
/*     */   public static String format(List<? extends NameValuePair> parameters, String encoding)
/*     */   {
/* 188 */     StringBuilder result = new StringBuilder();
/* 189 */     for (NameValuePair parameter : parameters) {
/* 190 */       String encodedName = encode(parameter.getName(), encoding);
/* 191 */       String value = parameter.getValue();
/* 192 */       String encodedValue = value != null ? encode(value, encoding) : "";
/* 193 */       if (result.length() > 0)
/* 194 */         result.append("&");
/* 195 */       result.append(encodedName);
/* 196 */       result.append("=");
/* 197 */       result.append(encodedValue);
/*     */     }
/* 199 */     return result.toString();
/*     */   }
/*     */   
/*     */   private static String decode(String content, String encoding) {
/*     */     try {
/* 204 */       return URLDecoder.decode(content, encoding != null ? encoding : "ISO-8859-1");
/*     */     }
/*     */     catch (UnsupportedEncodingException problem) {
/* 207 */       throw new IllegalArgumentException(problem);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String encode(String content, String encoding) {
/*     */     try {
/* 213 */       return URLEncoder.encode(content, encoding != null ? encoding : "ISO-8859-1");
/*     */     }
/*     */     catch (UnsupportedEncodingException problem) {
/* 216 */       throw new IllegalArgumentException(problem);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/utils/URLEncodedUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */