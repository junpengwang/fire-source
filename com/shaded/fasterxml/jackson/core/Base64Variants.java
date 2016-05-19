/*     */ package com.shaded.fasterxml.jackson.core;
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
/*     */ public final class Base64Variants
/*     */ {
/*     */   static final String STD_BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
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
/*  34 */   public static final Base64Variant MIME = new Base64Variant("MIME", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", true, '=', 76);
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
/*  45 */   public static final Base64Variant MIME_NO_LINEFEEDS = new Base64Variant(MIME, "MIME-NO-LINEFEEDS", Integer.MAX_VALUE);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  52 */   public static final Base64Variant PEM = new Base64Variant(MIME, "PEM", true, '=', 64);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final Base64Variant MODIFIED_FOR_URL;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*  67 */     StringBuffer localStringBuffer = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
/*     */     
/*  69 */     localStringBuffer.setCharAt(localStringBuffer.indexOf("+"), '-');
/*  70 */     localStringBuffer.setCharAt(localStringBuffer.indexOf("/"), '_');
/*     */     
/*     */ 
/*     */ 
/*  74 */     MODIFIED_FOR_URL = new Base64Variant("MODIFIED-FOR-URL", localStringBuffer.toString(), false, '\000', Integer.MAX_VALUE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Base64Variant getDefaultVariant()
/*     */   {
/*  84 */     return MIME_NO_LINEFEEDS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static Base64Variant valueOf(String paramString)
/*     */     throws IllegalArgumentException
/*     */   {
/*  92 */     if (MIME._name.equals(paramString)) {
/*  93 */       return MIME;
/*     */     }
/*  95 */     if (MIME_NO_LINEFEEDS._name.equals(paramString)) {
/*  96 */       return MIME_NO_LINEFEEDS;
/*     */     }
/*  98 */     if (PEM._name.equals(paramString)) {
/*  99 */       return PEM;
/*     */     }
/* 101 */     if (MODIFIED_FOR_URL._name.equals(paramString)) {
/* 102 */       return MODIFIED_FOR_URL;
/*     */     }
/* 104 */     if (paramString == null) {
/* 105 */       paramString = "<null>";
/*     */     } else {
/* 107 */       paramString = "'" + paramString + "'";
/*     */     }
/* 109 */     throw new IllegalArgumentException("No Base64Variant with name " + paramString);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/Base64Variants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */