/*     */ package com.shaded.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MapType
/*     */   extends MapLikeType
/*     */ {
/*     */   private static final long serialVersionUID = -811146779148281500L;
/*     */   
/*     */   private MapType(Class<?> paramClass, JavaType paramJavaType1, JavaType paramJavaType2, Object paramObject1, Object paramObject2, boolean paramBoolean)
/*     */   {
/*  20 */     super(paramClass, paramJavaType1, paramJavaType2, paramObject1, paramObject2, paramBoolean);
/*     */   }
/*     */   
/*     */   public static MapType construct(Class<?> paramClass, JavaType paramJavaType1, JavaType paramJavaType2)
/*     */   {
/*  25 */     return new MapType(paramClass, paramJavaType1, paramJavaType2, null, null, false);
/*     */   }
/*     */   
/*     */   protected JavaType _narrow(Class<?> paramClass)
/*     */   {
/*  30 */     return new MapType(paramClass, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType narrowContentsBy(Class<?> paramClass)
/*     */   {
/*  38 */     if (paramClass == this._valueType.getRawClass()) {
/*  39 */       return this;
/*     */     }
/*  41 */     return new MapType(this._class, this._keyType, this._valueType.narrowBy(paramClass), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JavaType widenContentsBy(Class<?> paramClass)
/*     */   {
/*  48 */     if (paramClass == this._valueType.getRawClass()) {
/*  49 */       return this;
/*     */     }
/*  51 */     return new MapType(this._class, this._keyType, this._valueType.widenBy(paramClass), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType narrowKey(Class<?> paramClass)
/*     */   {
/*  59 */     if (paramClass == this._keyType.getRawClass()) {
/*  60 */       return this;
/*     */     }
/*  62 */     return new MapType(this._class, this._keyType.narrowBy(paramClass), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType widenKey(Class<?> paramClass)
/*     */   {
/*  70 */     if (paramClass == this._keyType.getRawClass()) {
/*  71 */       return this;
/*     */     }
/*  73 */     return new MapType(this._class, this._keyType.widenBy(paramClass), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public MapType withTypeHandler(Object paramObject)
/*     */   {
/*  79 */     return new MapType(this._class, this._keyType, this._valueType, this._valueHandler, paramObject, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public MapType withContentTypeHandler(Object paramObject)
/*     */   {
/*  85 */     return new MapType(this._class, this._keyType, this._valueType.withTypeHandler(paramObject), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public MapType withValueHandler(Object paramObject)
/*     */   {
/*  91 */     return new MapType(this._class, this._keyType, this._valueType, paramObject, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */   public MapType withContentValueHandler(Object paramObject)
/*     */   {
/*  96 */     return new MapType(this._class, this._keyType, this._valueType.withValueHandler(paramObject), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public MapType withStaticTyping()
/*     */   {
/* 102 */     if (this._asStatic) {
/* 103 */       return this;
/*     */     }
/* 105 */     return new MapType(this._class, this._keyType.withStaticTyping(), this._valueType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
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
/*     */   public MapType withKeyTypeHandler(Object paramObject)
/*     */   {
/* 118 */     return new MapType(this._class, this._keyType.withTypeHandler(paramObject), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public MapType withKeyValueHandler(Object paramObject)
/*     */   {
/* 124 */     return new MapType(this._class, this._keyType.withValueHandler(paramObject), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
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
/*     */   public String toString()
/*     */   {
/* 137 */     return "[map type; class " + this._class.getName() + ", " + this._keyType + " -> " + this._valueType + "]";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/MapType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */