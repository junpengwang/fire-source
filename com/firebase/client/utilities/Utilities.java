/*     */ package com.firebase.client.utilities;
/*     */ 
/*     */ import com.firebase.client.FirebaseException;
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.core.RepoInfo;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class Utilities
/*     */ {
/*     */   public static ParsedUrl parseUrl(String url) throws FirebaseException
/*     */   {
/*  19 */     String original = url;
/*     */     try {
/*  21 */       int schemeOffset = original.indexOf("//");
/*  22 */       if (schemeOffset == -1) {
/*  23 */         throw new URISyntaxException(original, "Invalid scheme specified");
/*     */       }
/*  25 */       int pathOffset = original.substring(schemeOffset + 2).indexOf("/");
/*  26 */       if (pathOffset != -1) {
/*  27 */         pathOffset += schemeOffset + 2;
/*  28 */         String[] pathSegments = original.substring(pathOffset).split("/");
/*  29 */         StringBuilder builder = new StringBuilder();
/*  30 */         for (int i = 0; i < pathSegments.length; i++) {
/*  31 */           if (!pathSegments[i].equals("")) {
/*  32 */             builder.append("/");
/*  33 */             builder.append(java.net.URLEncoder.encode(pathSegments[i], "UTF-8"));
/*     */           }
/*     */         }
/*  36 */         original = original.substring(0, pathOffset) + builder.toString();
/*     */       }
/*     */       
/*  39 */       URI uri = new URI(original);
/*     */       
/*     */ 
/*  42 */       String pathString = uri.getPath().replace("+", " ");
/*  43 */       Validation.validateRootPathString(pathString);
/*  44 */       Path path = new Path(pathString);
/*  45 */       String scheme = uri.getScheme();
/*     */       
/*  47 */       RepoInfo repoInfo = new RepoInfo();
/*  48 */       repoInfo.host = uri.getHost().toLowerCase();
/*     */       
/*  50 */       int port = uri.getPort();
/*  51 */       if (port != -1) {
/*  52 */         repoInfo.secure = scheme.equals("https"); RepoInfo 
/*  53 */           tmp255_253 = repoInfo;tmp255_253.host = (tmp255_253.host + ":" + port);
/*     */       } else {
/*  55 */         repoInfo.secure = true;
/*     */       }
/*  57 */       String[] parts = repoInfo.host.split("\\.");
/*     */       
/*  59 */       repoInfo.namespace = parts[0].toLowerCase();
/*  60 */       repoInfo.internalHost = repoInfo.host;
/*  61 */       ParsedUrl parsedUrl = new ParsedUrl();
/*  62 */       parsedUrl.path = path;
/*  63 */       parsedUrl.repoInfo = repoInfo;
/*  64 */       return parsedUrl;
/*     */     }
/*     */     catch (URISyntaxException e) {
/*  67 */       throw new FirebaseException("Invalid Firebase url specified", e);
/*     */     } catch (UnsupportedEncodingException e) {
/*  69 */       throw new FirebaseException("Failed to URLEncode the path", e);
/*     */     }
/*     */   }
/*     */   
/*     */   public static String[] splitIntoFrames(String src, int maxFrameSize) {
/*  74 */     if (src.length() <= maxFrameSize) {
/*  75 */       return new String[] { src };
/*     */     }
/*  77 */     ArrayList<String> segs = new ArrayList();
/*  78 */     for (int i = 0; i < src.length(); i += maxFrameSize) {
/*  79 */       int end = Math.min(i + maxFrameSize, src.length());
/*  80 */       String seg = src.substring(i, end);
/*  81 */       segs.add(seg);
/*     */     }
/*  83 */     return (String[])segs.toArray(new String[segs.size()]);
/*     */   }
/*     */   
/*     */   public static String sha1HexDigest(String input)
/*     */   {
/*     */     try {
/*  89 */       MessageDigest md = MessageDigest.getInstance("SHA-1");
/*  90 */       md.update(input.getBytes("UTF-8"));
/*  91 */       byte[] bytes = md.digest();
/*  92 */       return Base64.encodeBytes(bytes);
/*     */     } catch (NoSuchAlgorithmException e) {
/*  94 */       throw new RuntimeException("Missing SHA-1 MessageDigest provider.", e);
/*     */     } catch (UnsupportedEncodingException e) {
/*  96 */       throw new RuntimeException("UTF-8 encoding is required for Firebase to run!");
/*     */     }
/*     */   }
/*     */   
/*     */   public static String doubleToHashString(double value) {
/* 101 */     StringBuilder sb = new StringBuilder(16);
/* 102 */     byte[] bytes = new byte[8];
/* 103 */     ByteBuffer.wrap(bytes).putDouble(value);
/* 104 */     for (int i = 0; i < 8; i++) {
/* 105 */       sb.append(String.format("%02x", new Object[] { Byte.valueOf(bytes[i]) }));
/*     */     }
/* 107 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public static Integer tryParseInt(String num)
/*     */   {
/* 113 */     if ((num.length() > 11) || (num.length() == 0)) {
/* 114 */       return null;
/*     */     }
/* 116 */     int i = 0;
/* 117 */     boolean negative = false;
/* 118 */     if (num.charAt(0) == '-') {
/* 119 */       if (num.length() == 1) {
/* 120 */         return null;
/*     */       }
/* 122 */       negative = true;
/* 123 */       i = 1;
/*     */     }
/*     */     
/* 126 */     long number = 0L;
/* 127 */     while (i < num.length()) {
/* 128 */       char c = num.charAt(i);
/* 129 */       if ((c < '0') || (c > '9')) {
/* 130 */         return null;
/*     */       }
/* 132 */       number = number * 10L + (c - '0');
/* 133 */       i++;
/*     */     }
/* 135 */     if (negative) {
/* 136 */       if (-number < -2147483648L) {
/* 137 */         return null;
/*     */       }
/* 139 */       return Integer.valueOf((int)-number);
/*     */     }
/*     */     
/* 142 */     if (number > 2147483647L) {
/* 143 */       return null;
/*     */     }
/* 145 */     return Integer.valueOf((int)number);
/*     */   }
/*     */   
/*     */   public static int compareInts(int i, int j)
/*     */   {
/* 150 */     if (i < j)
/* 151 */       return -1;
/* 152 */     if (i == j) {
/* 153 */       return 0;
/*     */     }
/* 155 */     return 1;
/*     */   }
/*     */   
/*     */   public static int compareLongs(long i, long j)
/*     */   {
/* 160 */     if (i < j)
/* 161 */       return -1;
/* 162 */     if (i == j) {
/* 163 */       return 0;
/*     */     }
/* 165 */     return 1;
/*     */   }
/*     */   
/*     */   public static <C> C castOrNull(Object o, Class<C> clazz)
/*     */   {
/* 170 */     if (clazz.isAssignableFrom(o.getClass())) {
/* 171 */       return (C)o;
/*     */     }
/* 173 */     return null;
/*     */   }
/*     */   
/*     */   public static <C> C getOrNull(Object o, String key, Class<C> clazz)
/*     */   {
/* 178 */     if (o == null) {
/* 179 */       return null;
/*     */     }
/* 181 */     Map map = (Map)castOrNull(o, Map.class);
/* 182 */     Object result = map.get(key);
/* 183 */     if (result != null) {
/* 184 */       return (C)castOrNull(result, clazz);
/*     */     }
/* 186 */     return null;
/*     */   }
/*     */   
/*     */   public static Long longFromObject(Object o)
/*     */   {
/* 191 */     if ((o instanceof Integer))
/* 192 */       return Long.valueOf(((Integer)o).intValue());
/* 193 */     if ((o instanceof Long)) {
/* 194 */       return (Long)o;
/*     */     }
/* 196 */     return null;
/*     */   }
/*     */   
/*     */   public static void hardAssert(boolean condition)
/*     */   {
/* 201 */     hardAssert(condition, "");
/*     */   }
/*     */   
/*     */   public static void hardAssert(boolean condition, String message) {
/* 205 */     if (!condition) {
/* 206 */       throw new AssertionError("hardAssert failed: " + message);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/Utilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */