/*     */ package com.shaded.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import java.lang.reflect.Array;
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
/*     */ public final class ArrayType
/*     */   extends TypeBase
/*     */ {
/*     */   private static final long serialVersionUID = 9040058063449087477L;
/*     */   protected final JavaType _componentType;
/*     */   protected final Object _emptyArray;
/*     */   
/*     */   private ArrayType(JavaType paramJavaType, Object paramObject1, Object paramObject2, Object paramObject3, boolean paramBoolean)
/*     */   {
/*  32 */     super(paramObject1.getClass(), paramJavaType.hashCode(), paramObject2, paramObject3, paramBoolean);
/*     */     
/*  34 */     this._componentType = paramJavaType;
/*  35 */     this._emptyArray = paramObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ArrayType construct(JavaType paramJavaType, Object paramObject1, Object paramObject2)
/*     */   {
/*  47 */     Object localObject = Array.newInstance(paramJavaType.getRawClass(), 0);
/*  48 */     return new ArrayType(paramJavaType, localObject, null, null, false);
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayType withTypeHandler(Object paramObject)
/*     */   {
/*  54 */     if (paramObject == this._typeHandler) {
/*  55 */       return this;
/*     */     }
/*  57 */     return new ArrayType(this._componentType, this._emptyArray, this._valueHandler, paramObject, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayType withContentTypeHandler(Object paramObject)
/*     */   {
/*  63 */     if (paramObject == this._componentType.getTypeHandler()) {
/*  64 */       return this;
/*     */     }
/*  66 */     return new ArrayType(this._componentType.withTypeHandler(paramObject), this._emptyArray, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayType withValueHandler(Object paramObject)
/*     */   {
/*  72 */     if (paramObject == this._valueHandler) {
/*  73 */       return this;
/*     */     }
/*  75 */     return new ArrayType(this._componentType, this._emptyArray, paramObject, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */   public ArrayType withContentValueHandler(Object paramObject)
/*     */   {
/*  80 */     if (paramObject == this._componentType.getValueHandler()) {
/*  81 */       return this;
/*     */     }
/*  83 */     return new ArrayType(this._componentType.withValueHandler(paramObject), this._emptyArray, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayType withStaticTyping()
/*     */   {
/*  89 */     if (this._asStatic) {
/*  90 */       return this;
/*     */     }
/*  92 */     return new ArrayType(this._componentType.withStaticTyping(), this._emptyArray, this._valueHandler, this._typeHandler, true);
/*     */   }
/*     */   
/*     */ 
/*     */   protected String buildCanonicalName()
/*     */   {
/*  98 */     return this._class.getName();
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
/*     */   protected JavaType _narrow(Class<?> paramClass)
/*     */   {
/* 117 */     if (!paramClass.isArray()) {
/* 118 */       throw new IllegalArgumentException("Incompatible narrowing operation: trying to narrow " + toString() + " to class " + paramClass.getName());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 123 */     Class localClass = paramClass.getComponentType();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 131 */     JavaType localJavaType = TypeFactory.defaultInstance().constructType(localClass);
/* 132 */     return construct(localJavaType, this._valueHandler, this._typeHandler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType narrowContentsBy(Class<?> paramClass)
/*     */   {
/* 143 */     if (paramClass == this._componentType.getRawClass()) {
/* 144 */       return this;
/*     */     }
/* 146 */     return construct(this._componentType.narrowBy(paramClass), this._valueHandler, this._typeHandler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType widenContentsBy(Class<?> paramClass)
/*     */   {
/* 154 */     if (paramClass == this._componentType.getRawClass()) {
/* 155 */       return this;
/*     */     }
/* 157 */     return construct(this._componentType.widenBy(paramClass), this._valueHandler, this._typeHandler);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isArrayType()
/*     */   {
/* 168 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAbstract()
/*     */   {
/* 176 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConcrete()
/*     */   {
/* 184 */     return true;
/*     */   }
/*     */   
/*     */   public boolean hasGenericTypes()
/*     */   {
/* 189 */     return this._componentType.hasGenericTypes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String containedTypeName(int paramInt)
/*     */   {
/* 200 */     if (paramInt == 0) return "E";
/* 201 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isContainerType()
/*     */   {
/* 211 */     return true;
/*     */   }
/*     */   
/* 214 */   public JavaType getContentType() { return this._componentType; }
/*     */   
/*     */   public int containedTypeCount() {
/* 217 */     return 1;
/*     */   }
/*     */   
/* 220 */   public JavaType containedType(int paramInt) { return paramInt == 0 ? this._componentType : null; }
/*     */   
/*     */ 
/*     */   public StringBuilder getGenericSignature(StringBuilder paramStringBuilder)
/*     */   {
/* 225 */     paramStringBuilder.append('[');
/* 226 */     return this._componentType.getGenericSignature(paramStringBuilder);
/*     */   }
/*     */   
/*     */   public StringBuilder getErasedSignature(StringBuilder paramStringBuilder)
/*     */   {
/* 231 */     paramStringBuilder.append('[');
/* 232 */     return this._componentType.getErasedSignature(paramStringBuilder);
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
/* 244 */     return "[array type, component type: " + this._componentType + "]";
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 250 */     if (paramObject == this) return true;
/* 251 */     if (paramObject == null) return false;
/* 252 */     if (paramObject.getClass() != getClass()) { return false;
/*     */     }
/* 254 */     ArrayType localArrayType = (ArrayType)paramObject;
/* 255 */     return this._componentType.equals(localArrayType._componentType);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/ArrayType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */