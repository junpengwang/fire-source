/*     */ package com.shaded.fasterxml.jackson.core;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonLocation
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  22 */   public static final JsonLocation NA = new JsonLocation("N/A", -1L, -1L, -1, -1);
/*     */   
/*     */ 
/*     */ 
/*     */   final long _totalBytes;
/*     */   
/*     */ 
/*     */   final long _totalChars;
/*     */   
/*     */ 
/*     */   final int _lineNr;
/*     */   
/*     */ 
/*     */   final int _columnNr;
/*     */   
/*     */ 
/*     */   final transient Object _sourceRef;
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonLocation(Object paramObject, long paramLong, int paramInt1, int paramInt2)
/*     */   {
/*  44 */     this(paramObject, -1L, paramLong, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonLocation(Object paramObject, long paramLong1, long paramLong2, int paramInt1, int paramInt2)
/*     */   {
/*  50 */     this._sourceRef = paramObject;
/*  51 */     this._totalBytes = paramLong1;
/*  52 */     this._totalChars = paramLong2;
/*  53 */     this._lineNr = paramInt1;
/*  54 */     this._columnNr = paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getSourceRef()
/*     */   {
/*  65 */     return this._sourceRef;
/*     */   }
/*     */   
/*     */   public int getLineNr()
/*     */   {
/*  70 */     return this._lineNr;
/*     */   }
/*     */   
/*     */   public int getColumnNr()
/*     */   {
/*  75 */     return this._columnNr;
/*     */   }
/*     */   
/*     */ 
/*     */   public long getCharOffset()
/*     */   {
/*  81 */     return this._totalChars;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getByteOffset()
/*     */   {
/*  89 */     return this._totalBytes;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/*  95 */     StringBuilder localStringBuilder = new StringBuilder(80);
/*  96 */     localStringBuilder.append("[Source: ");
/*  97 */     if (this._sourceRef == null) {
/*  98 */       localStringBuilder.append("UNKNOWN");
/*     */     } else {
/* 100 */       localStringBuilder.append(this._sourceRef.toString());
/*     */     }
/* 102 */     localStringBuilder.append("; line: ");
/* 103 */     localStringBuilder.append(this._lineNr);
/* 104 */     localStringBuilder.append(", column: ");
/* 105 */     localStringBuilder.append(this._columnNr);
/* 106 */     localStringBuilder.append(']');
/* 107 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 113 */     int i = this._sourceRef == null ? 1 : this._sourceRef.hashCode();
/* 114 */     i ^= this._lineNr;
/* 115 */     i += this._columnNr;
/* 116 */     i ^= (int)this._totalChars;
/* 117 */     i += (int)this._totalBytes;
/* 118 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 124 */     if (paramObject == this) return true;
/* 125 */     if (paramObject == null) return false;
/* 126 */     if (!(paramObject instanceof JsonLocation)) return false;
/* 127 */     JsonLocation localJsonLocation = (JsonLocation)paramObject;
/*     */     
/* 129 */     if (this._sourceRef == null) {
/* 130 */       if (localJsonLocation._sourceRef != null) return false;
/* 131 */     } else if (!this._sourceRef.equals(localJsonLocation._sourceRef)) { return false;
/*     */     }
/* 133 */     return (this._lineNr == localJsonLocation._lineNr) && (this._columnNr == localJsonLocation._columnNr) && (this._totalChars == localJsonLocation._totalChars) && (getByteOffset() == localJsonLocation.getByteOffset());
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/JsonLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */