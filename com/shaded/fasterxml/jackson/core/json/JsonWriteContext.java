/*     */ package com.shaded.fasterxml.jackson.core.json;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonStreamContext;
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
/*     */ public class JsonWriteContext
/*     */   extends JsonStreamContext
/*     */ {
/*     */   public static final int STATUS_OK_AS_IS = 0;
/*     */   public static final int STATUS_OK_AFTER_COMMA = 1;
/*     */   public static final int STATUS_OK_AFTER_COLON = 2;
/*     */   public static final int STATUS_OK_AFTER_SPACE = 3;
/*     */   public static final int STATUS_EXPECT_VALUE = 4;
/*     */   public static final int STATUS_EXPECT_NAME = 5;
/*     */   protected final JsonWriteContext _parent;
/*     */   protected String _currentName;
/*  38 */   protected JsonWriteContext _child = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonWriteContext(int paramInt, JsonWriteContext paramJsonWriteContext)
/*     */   {
/*  49 */     this._type = paramInt;
/*  50 */     this._parent = paramJsonWriteContext;
/*  51 */     this._index = -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static JsonWriteContext createRootContext()
/*     */   {
/*  58 */     return new JsonWriteContext(0, null);
/*     */   }
/*     */   
/*     */   private JsonWriteContext reset(int paramInt) {
/*  62 */     this._type = paramInt;
/*  63 */     this._index = -1;
/*  64 */     this._currentName = null;
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   public final JsonWriteContext createChildArrayContext()
/*     */   {
/*  70 */     JsonWriteContext localJsonWriteContext = this._child;
/*  71 */     if (localJsonWriteContext == null) {
/*  72 */       this._child = (localJsonWriteContext = new JsonWriteContext(1, this));
/*  73 */       return localJsonWriteContext;
/*     */     }
/*  75 */     return localJsonWriteContext.reset(1);
/*     */   }
/*     */   
/*     */   public final JsonWriteContext createChildObjectContext()
/*     */   {
/*  80 */     JsonWriteContext localJsonWriteContext = this._child;
/*  81 */     if (localJsonWriteContext == null) {
/*  82 */       this._child = (localJsonWriteContext = new JsonWriteContext(2, this));
/*  83 */       return localJsonWriteContext;
/*     */     }
/*  85 */     return localJsonWriteContext.reset(2);
/*     */   }
/*     */   
/*     */ 
/*     */   public final JsonWriteContext getParent()
/*     */   {
/*  91 */     return this._parent;
/*     */   }
/*     */   
/*  94 */   public final String getCurrentName() { return this._currentName; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int writeFieldName(String paramString)
/*     */   {
/* 105 */     if (this._type == 2) {
/* 106 */       if (this._currentName != null) {
/* 107 */         return 4;
/*     */       }
/* 109 */       this._currentName = paramString;
/* 110 */       return this._index < 0 ? 0 : 1;
/*     */     }
/* 112 */     return 4;
/*     */   }
/*     */   
/*     */ 
/*     */   public final int writeValue()
/*     */   {
/* 118 */     if (this._type == 2) {
/* 119 */       if (this._currentName == null) {
/* 120 */         return 5;
/*     */       }
/* 122 */       this._currentName = null;
/* 123 */       this._index += 1;
/* 124 */       return 2;
/*     */     }
/*     */     
/*     */ 
/* 128 */     if (this._type == 1) {
/* 129 */       int i = this._index;
/* 130 */       this._index += 1;
/* 131 */       return i < 0 ? 0 : 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 136 */     this._index += 1;
/* 137 */     return this._index == 0 ? 0 : 3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected final void appendDesc(StringBuilder paramStringBuilder)
/*     */   {
/* 144 */     if (this._type == 2) {
/* 145 */       paramStringBuilder.append('{');
/* 146 */       if (this._currentName != null) {
/* 147 */         paramStringBuilder.append('"');
/*     */         
/* 149 */         paramStringBuilder.append(this._currentName);
/* 150 */         paramStringBuilder.append('"');
/*     */       } else {
/* 152 */         paramStringBuilder.append('?');
/*     */       }
/* 154 */       paramStringBuilder.append('}');
/* 155 */     } else if (this._type == 1) {
/* 156 */       paramStringBuilder.append('[');
/* 157 */       paramStringBuilder.append(getCurrentIndex());
/* 158 */       paramStringBuilder.append(']');
/*     */     }
/*     */     else {
/* 161 */       paramStringBuilder.append("/");
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
/*     */   public final String toString()
/*     */   {
/* 174 */     StringBuilder localStringBuilder = new StringBuilder(64);
/* 175 */     appendDesc(localStringBuilder);
/* 176 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/json/JsonWriteContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */