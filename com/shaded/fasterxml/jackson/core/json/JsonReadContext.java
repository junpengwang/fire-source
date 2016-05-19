/*     */ package com.shaded.fasterxml.jackson.core.json;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonLocation;
/*     */ import com.shaded.fasterxml.jackson.core.JsonStreamContext;
/*     */ import com.shaded.fasterxml.jackson.core.io.CharTypes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JsonReadContext
/*     */   extends JsonStreamContext
/*     */ {
/*     */   protected final JsonReadContext _parent;
/*     */   protected int _lineNr;
/*     */   protected int _columnNr;
/*     */   protected String _currentName;
/*  34 */   protected JsonReadContext _child = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonReadContext(JsonReadContext paramJsonReadContext, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  45 */     this._type = paramInt1;
/*  46 */     this._parent = paramJsonReadContext;
/*  47 */     this._lineNr = paramInt2;
/*  48 */     this._columnNr = paramInt3;
/*  49 */     this._index = -1;
/*     */   }
/*     */   
/*     */   protected void reset(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  54 */     this._type = paramInt1;
/*  55 */     this._index = -1;
/*  56 */     this._lineNr = paramInt2;
/*  57 */     this._columnNr = paramInt3;
/*  58 */     this._currentName = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static JsonReadContext createRootContext(int paramInt1, int paramInt2)
/*     */   {
/*  65 */     return new JsonReadContext(null, 0, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public static JsonReadContext createRootContext()
/*     */   {
/*  70 */     return new JsonReadContext(null, 0, 1, 0);
/*     */   }
/*     */   
/*     */   public JsonReadContext createChildArrayContext(int paramInt1, int paramInt2)
/*     */   {
/*  75 */     JsonReadContext localJsonReadContext = this._child;
/*  76 */     if (localJsonReadContext == null) {
/*  77 */       this._child = (localJsonReadContext = new JsonReadContext(this, 1, paramInt1, paramInt2));
/*  78 */       return localJsonReadContext;
/*     */     }
/*  80 */     localJsonReadContext.reset(1, paramInt1, paramInt2);
/*  81 */     return localJsonReadContext;
/*     */   }
/*     */   
/*     */   public JsonReadContext createChildObjectContext(int paramInt1, int paramInt2)
/*     */   {
/*  86 */     JsonReadContext localJsonReadContext = this._child;
/*  87 */     if (localJsonReadContext == null) {
/*  88 */       this._child = (localJsonReadContext = new JsonReadContext(this, 2, paramInt1, paramInt2));
/*  89 */       return localJsonReadContext;
/*     */     }
/*  91 */     localJsonReadContext.reset(2, paramInt1, paramInt2);
/*  92 */     return localJsonReadContext;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCurrentName()
/*     */   {
/* 102 */     return this._currentName;
/*     */   }
/*     */   
/* 105 */   public JsonReadContext getParent() { return this._parent; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonLocation getStartLocation(Object paramObject)
/*     */   {
/* 122 */     long l = -1L;
/*     */     
/* 124 */     return new JsonLocation(paramObject, l, this._lineNr, this._columnNr);
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
/*     */   public boolean expectComma()
/*     */   {
/* 139 */     int i = ++this._index;
/* 140 */     return (this._type != 0) && (i > 0);
/*     */   }
/*     */   
/*     */   public void setCurrentName(String paramString)
/*     */   {
/* 145 */     this._currentName = paramString;
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
/*     */   public String toString()
/*     */   {
/* 161 */     StringBuilder localStringBuilder = new StringBuilder(64);
/* 162 */     switch (this._type) {
/*     */     case 0: 
/* 164 */       localStringBuilder.append("/");
/* 165 */       break;
/*     */     case 1: 
/* 167 */       localStringBuilder.append('[');
/* 168 */       localStringBuilder.append(getCurrentIndex());
/* 169 */       localStringBuilder.append(']');
/* 170 */       break;
/*     */     case 2: 
/* 172 */       localStringBuilder.append('{');
/* 173 */       if (this._currentName != null) {
/* 174 */         localStringBuilder.append('"');
/* 175 */         CharTypes.appendQuoted(localStringBuilder, this._currentName);
/* 176 */         localStringBuilder.append('"');
/*     */       } else {
/* 178 */         localStringBuilder.append('?');
/*     */       }
/* 180 */       localStringBuilder.append('}');
/*     */     }
/*     */     
/* 183 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/json/JsonReadContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */