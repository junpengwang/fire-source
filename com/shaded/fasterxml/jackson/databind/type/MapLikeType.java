/*     */ package com.shaded.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import java.util.Map;
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
/*     */ public class MapLikeType
/*     */   extends TypeBase
/*     */ {
/*     */   private static final long serialVersionUID = 416067702302823522L;
/*     */   protected final JavaType _keyType;
/*     */   protected final JavaType _valueType;
/*     */   
/*     */   protected MapLikeType(Class<?> paramClass, JavaType paramJavaType1, JavaType paramJavaType2, Object paramObject1, Object paramObject2, boolean paramBoolean)
/*     */   {
/*  38 */     super(paramClass, paramJavaType1.hashCode() ^ paramJavaType2.hashCode(), paramObject1, paramObject2, paramBoolean);
/*  39 */     this._keyType = paramJavaType1;
/*  40 */     this._valueType = paramJavaType2;
/*     */   }
/*     */   
/*     */ 
/*     */   public static MapLikeType construct(Class<?> paramClass, JavaType paramJavaType1, JavaType paramJavaType2)
/*     */   {
/*  46 */     return new MapLikeType(paramClass, paramJavaType1, paramJavaType2, null, null, false);
/*     */   }
/*     */   
/*     */   protected JavaType _narrow(Class<?> paramClass)
/*     */   {
/*  51 */     return new MapLikeType(paramClass, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JavaType narrowContentsBy(Class<?> paramClass)
/*     */   {
/*  58 */     if (paramClass == this._valueType.getRawClass()) {
/*  59 */       return this;
/*     */     }
/*  61 */     return new MapLikeType(this._class, this._keyType, this._valueType.narrowBy(paramClass), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JavaType widenContentsBy(Class<?> paramClass)
/*     */   {
/*  68 */     if (paramClass == this._valueType.getRawClass()) {
/*  69 */       return this;
/*     */     }
/*  71 */     return new MapLikeType(this._class, this._keyType, this._valueType.widenBy(paramClass), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JavaType narrowKey(Class<?> paramClass)
/*     */   {
/*  78 */     if (paramClass == this._keyType.getRawClass()) {
/*  79 */       return this;
/*     */     }
/*  81 */     return new MapLikeType(this._class, this._keyType.narrowBy(paramClass), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JavaType widenKey(Class<?> paramClass)
/*     */   {
/*  88 */     if (paramClass == this._keyType.getRawClass()) {
/*  89 */       return this;
/*     */     }
/*  91 */     return new MapLikeType(this._class, this._keyType.widenBy(paramClass), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public MapLikeType withTypeHandler(Object paramObject)
/*     */   {
/*  98 */     return new MapLikeType(this._class, this._keyType, this._valueType, this._valueHandler, paramObject, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public MapLikeType withContentTypeHandler(Object paramObject)
/*     */   {
/* 104 */     return new MapLikeType(this._class, this._keyType, this._valueType.withTypeHandler(paramObject), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public MapLikeType withValueHandler(Object paramObject)
/*     */   {
/* 110 */     return new MapLikeType(this._class, this._keyType, this._valueType, paramObject, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */   public MapLikeType withContentValueHandler(Object paramObject)
/*     */   {
/* 115 */     return new MapLikeType(this._class, this._keyType, this._valueType.withValueHandler(paramObject), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public MapLikeType withStaticTyping()
/*     */   {
/* 121 */     if (this._asStatic) {
/* 122 */       return this;
/*     */     }
/* 124 */     return new MapLikeType(this._class, this._keyType, this._valueType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
/*     */   }
/*     */   
/*     */ 
/*     */   protected String buildCanonicalName()
/*     */   {
/* 130 */     StringBuilder localStringBuilder = new StringBuilder();
/* 131 */     localStringBuilder.append(this._class.getName());
/* 132 */     if (this._keyType != null) {
/* 133 */       localStringBuilder.append('<');
/* 134 */       localStringBuilder.append(this._keyType.toCanonical());
/* 135 */       localStringBuilder.append(',');
/* 136 */       localStringBuilder.append(this._valueType.toCanonical());
/* 137 */       localStringBuilder.append('>');
/*     */     }
/* 139 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isContainerType()
/*     */   {
/* 149 */     return true;
/*     */   }
/*     */   
/* 152 */   public boolean isMapLikeType() { return true; }
/*     */   
/*     */   public JavaType getKeyType() {
/* 155 */     return this._keyType;
/*     */   }
/*     */   
/* 158 */   public JavaType getContentType() { return this._valueType; }
/*     */   
/*     */   public int containedTypeCount() {
/* 161 */     return 2;
/*     */   }
/*     */   
/*     */   public JavaType containedType(int paramInt) {
/* 165 */     if (paramInt == 0) return this._keyType;
/* 166 */     if (paramInt == 1) return this._valueType;
/* 167 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String containedTypeName(int paramInt)
/*     */   {
/* 177 */     if (paramInt == 0) return "K";
/* 178 */     if (paramInt == 1) return "V";
/* 179 */     return null;
/*     */   }
/*     */   
/*     */   public StringBuilder getErasedSignature(StringBuilder paramStringBuilder)
/*     */   {
/* 184 */     return _classSignature(this._class, paramStringBuilder, true);
/*     */   }
/*     */   
/*     */ 
/*     */   public StringBuilder getGenericSignature(StringBuilder paramStringBuilder)
/*     */   {
/* 190 */     _classSignature(this._class, paramStringBuilder, false);
/* 191 */     paramStringBuilder.append('<');
/* 192 */     this._keyType.getGenericSignature(paramStringBuilder);
/* 193 */     this._valueType.getGenericSignature(paramStringBuilder);
/* 194 */     paramStringBuilder.append(">;");
/* 195 */     return paramStringBuilder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MapLikeType withKeyTypeHandler(Object paramObject)
/*     */   {
/* 206 */     return new MapLikeType(this._class, this._keyType.withTypeHandler(paramObject), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */   public MapLikeType withKeyValueHandler(Object paramObject)
/*     */   {
/* 211 */     return new MapLikeType(this._class, this._keyType.withValueHandler(paramObject), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isTrueMapType()
/*     */   {
/* 222 */     return Map.class.isAssignableFrom(this._class);
/*     */   }
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
/* 234 */     return "[map-like type; class " + this._class.getName() + ", " + this._keyType + " -> " + this._valueType + "]";
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 240 */     if (paramObject == this) return true;
/* 241 */     if (paramObject == null) return false;
/* 242 */     if (paramObject.getClass() != getClass()) { return false;
/*     */     }
/* 244 */     MapLikeType localMapLikeType = (MapLikeType)paramObject;
/* 245 */     return (this._class == localMapLikeType._class) && (this._keyType.equals(localMapLikeType._keyType)) && (this._valueType.equals(localMapLikeType._valueType));
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/MapLikeType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */