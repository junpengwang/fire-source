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
/*     */ public enum JsonToken
/*     */ {
/*  31 */   NOT_AVAILABLE(null), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  37 */   START_OBJECT("{"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  43 */   END_OBJECT("}"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  49 */   START_ARRAY("["), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  55 */   END_ARRAY("]"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  61 */   FIELD_NAME(null), 
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
/*  73 */   VALUE_EMBEDDED_OBJECT(null), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  80 */   VALUE_STRING(null), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  88 */   VALUE_NUMBER_INT(null), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  96 */   VALUE_NUMBER_FLOAT(null), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 102 */   VALUE_TRUE("true"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 108 */   VALUE_FALSE("false"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 114 */   VALUE_NULL("null");
/*     */   
/*     */ 
/*     */ 
/*     */   final String _serialized;
/*     */   
/*     */ 
/*     */   final char[] _serializedChars;
/*     */   
/*     */ 
/*     */   final byte[] _serializedBytes;
/*     */   
/*     */ 
/*     */   private JsonToken(String paramString)
/*     */   {
/* 129 */     if (paramString == null) {
/* 130 */       this._serialized = null;
/* 131 */       this._serializedChars = null;
/* 132 */       this._serializedBytes = null;
/*     */     } else {
/* 134 */       this._serialized = paramString;
/* 135 */       this._serializedChars = paramString.toCharArray();
/*     */       
/* 137 */       int j = this._serializedChars.length;
/* 138 */       this._serializedBytes = new byte[j];
/* 139 */       for (int k = 0; k < j; k++) {
/* 140 */         this._serializedBytes[k] = ((byte)this._serializedChars[k]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 145 */   public String asString() { return this._serialized; }
/* 146 */   public char[] asCharArray() { return this._serializedChars; }
/* 147 */   public byte[] asByteArray() { return this._serializedBytes; }
/*     */   
/*     */   public boolean isNumeric() {
/* 150 */     return (this == VALUE_NUMBER_INT) || (this == VALUE_NUMBER_FLOAT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isScalarValue()
/*     */   {
/* 160 */     return ordinal() >= VALUE_EMBEDDED_OBJECT.ordinal();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/JsonToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */