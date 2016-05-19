/*     */ package com.shaded.fasterxml.jackson.core;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ public class JsonProcessingException
/*     */   extends IOException
/*     */ {
/*     */   static final long serialVersionUID = 123L;
/*     */   protected JsonLocation _location;
/*     */   
/*     */   protected JsonProcessingException(String paramString, JsonLocation paramJsonLocation, Throwable paramThrowable)
/*     */   {
/*  27 */     super(paramString);
/*  28 */     if (paramThrowable != null) {
/*  29 */       initCause(paramThrowable);
/*     */     }
/*  31 */     this._location = paramJsonLocation;
/*     */   }
/*     */   
/*     */   protected JsonProcessingException(String paramString)
/*     */   {
/*  36 */     super(paramString);
/*     */   }
/*     */   
/*     */   protected JsonProcessingException(String paramString, JsonLocation paramJsonLocation)
/*     */   {
/*  41 */     this(paramString, paramJsonLocation, null);
/*     */   }
/*     */   
/*     */   protected JsonProcessingException(String paramString, Throwable paramThrowable)
/*     */   {
/*  46 */     this(paramString, null, paramThrowable);
/*     */   }
/*     */   
/*     */   protected JsonProcessingException(Throwable paramThrowable)
/*     */   {
/*  51 */     this(null, null, paramThrowable);
/*     */   }
/*     */   
/*     */   public JsonLocation getLocation() {
/*  55 */     return this._location;
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
/*     */   public String getOriginalMessage()
/*     */   {
/*  73 */     return super.getMessage();
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
/*     */   protected String getMessageSuffix()
/*     */   {
/*  88 */     return null;
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
/*     */   public String getMessage()
/*     */   {
/* 103 */     String str1 = super.getMessage();
/* 104 */     if (str1 == null) {
/* 105 */       str1 = "N/A";
/*     */     }
/* 107 */     JsonLocation localJsonLocation = getLocation();
/* 108 */     String str2 = getMessageSuffix();
/*     */     
/* 110 */     if ((localJsonLocation != null) || (str2 != null)) {
/* 111 */       StringBuilder localStringBuilder = new StringBuilder(100);
/* 112 */       localStringBuilder.append(str1);
/* 113 */       if (str2 != null) {
/* 114 */         localStringBuilder.append(str2);
/*     */       }
/* 116 */       if (localJsonLocation != null) {
/* 117 */         localStringBuilder.append('\n');
/* 118 */         localStringBuilder.append(" at ");
/* 119 */         localStringBuilder.append(localJsonLocation.toString());
/*     */       }
/* 121 */       str1 = localStringBuilder.toString();
/*     */     }
/* 123 */     return str1;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 128 */     return getClass().getName() + ": " + getMessage();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/JsonProcessingException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */