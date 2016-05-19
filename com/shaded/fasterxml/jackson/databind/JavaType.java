/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.type.ResolvedType;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
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
/*     */ 
/*     */ 
/*     */ public abstract class JavaType
/*     */   extends ResolvedType
/*     */   implements Serializable, Type
/*     */ {
/*     */   private static final long serialVersionUID = 6774285981275451126L;
/*     */   protected final Class<?> _class;
/*     */   protected final int _hashCode;
/*     */   protected final Object _valueHandler;
/*     */   protected final Object _typeHandler;
/*     */   protected final boolean _asStatic;
/*     */   
/*     */   protected JavaType(Class<?> paramClass, int paramInt, Object paramObject1, Object paramObject2, boolean paramBoolean)
/*     */   {
/*  76 */     this._class = paramClass;
/*  77 */     this._hashCode = (paramClass.getName().hashCode() + paramInt);
/*  78 */     this._valueHandler = paramObject1;
/*  79 */     this._typeHandler = paramObject2;
/*  80 */     this._asStatic = paramBoolean;
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
/*     */   public abstract JavaType withTypeHandler(Object paramObject);
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
/*     */   public abstract JavaType withContentTypeHandler(Object paramObject);
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
/*     */   public abstract JavaType withValueHandler(Object paramObject);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract JavaType withContentValueHandler(Object paramObject);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract JavaType withStaticTyping();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType narrowBy(Class<?> paramClass)
/*     */   {
/* 146 */     if (paramClass == this._class) {
/* 147 */       return this;
/*     */     }
/*     */     
/* 150 */     _assertSubclass(paramClass, this._class);
/* 151 */     JavaType localJavaType = _narrow(paramClass);
/*     */     
/*     */ 
/* 154 */     if (this._valueHandler != localJavaType.getValueHandler()) {
/* 155 */       localJavaType = localJavaType.withValueHandler(this._valueHandler);
/*     */     }
/* 157 */     if (this._typeHandler != localJavaType.getTypeHandler()) {
/* 158 */       localJavaType = localJavaType.withTypeHandler(this._typeHandler);
/*     */     }
/* 160 */     return localJavaType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType forcedNarrowBy(Class<?> paramClass)
/*     */   {
/* 170 */     if (paramClass == this._class) {
/* 171 */       return this;
/*     */     }
/* 173 */     JavaType localJavaType = _narrow(paramClass);
/*     */     
/* 175 */     if (this._valueHandler != localJavaType.getValueHandler()) {
/* 176 */       localJavaType = localJavaType.withValueHandler(this._valueHandler);
/*     */     }
/* 178 */     if (this._typeHandler != localJavaType.getTypeHandler()) {
/* 179 */       localJavaType = localJavaType.withTypeHandler(this._typeHandler);
/*     */     }
/* 181 */     return localJavaType;
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
/*     */   public JavaType widenBy(Class<?> paramClass)
/*     */   {
/* 196 */     if (paramClass == this._class) {
/* 197 */       return this;
/*     */     }
/*     */     
/* 200 */     _assertSubclass(this._class, paramClass);
/* 201 */     return _widen(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected abstract JavaType _narrow(Class<?> paramClass);
/*     */   
/*     */ 
/*     */ 
/*     */   protected JavaType _widen(Class<?> paramClass)
/*     */   {
/* 212 */     return _narrow(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract JavaType narrowContentsBy(Class<?> paramClass);
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract JavaType widenContentsBy(Class<?> paramClass);
/*     */   
/*     */ 
/*     */   public final Class<?> getRawClass()
/*     */   {
/* 226 */     return this._class;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean hasRawClass(Class<?> paramClass)
/*     */   {
/* 235 */     return this._class == paramClass;
/*     */   }
/*     */   
/*     */   public boolean isAbstract()
/*     */   {
/* 240 */     return Modifier.isAbstract(this._class.getModifiers());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConcrete()
/*     */   {
/* 250 */     int i = this._class.getModifiers();
/* 251 */     if ((i & 0x600) == 0) {
/* 252 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 257 */     if (this._class.isPrimitive()) {
/* 258 */       return true;
/*     */     }
/* 260 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isThrowable()
/*     */   {
/* 265 */     return Throwable.class.isAssignableFrom(this._class);
/*     */   }
/*     */   
/*     */   public boolean isArrayType() {
/* 269 */     return false;
/*     */   }
/*     */   
/* 272 */   public final boolean isEnumType() { return this._class.isEnum(); }
/*     */   
/*     */   public final boolean isInterface() {
/* 275 */     return this._class.isInterface();
/*     */   }
/*     */   
/* 278 */   public final boolean isPrimitive() { return this._class.isPrimitive(); }
/*     */   
/*     */   public final boolean isFinal() {
/* 281 */     return Modifier.isFinal(this._class.getModifiers());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean isContainerType();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCollectionLikeType()
/*     */   {
/* 296 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isMapLikeType()
/*     */   {
/* 304 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean useStaticType()
/*     */   {
/* 315 */     return this._asStatic;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasGenericTypes()
/*     */   {
/* 327 */     return containedTypeCount() > 0;
/*     */   }
/*     */   
/*     */   public JavaType getKeyType() {
/* 331 */     return null;
/*     */   }
/*     */   
/* 334 */   public JavaType getContentType() { return null; }
/*     */   
/*     */   public int containedTypeCount() {
/* 337 */     return 0;
/*     */   }
/*     */   
/* 340 */   public JavaType containedType(int paramInt) { return null; }
/*     */   
/*     */   public String containedTypeName(int paramInt) {
/* 343 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T getValueHandler()
/*     */   {
/* 355 */     return (T)this._valueHandler;
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> T getTypeHandler()
/*     */   {
/* 361 */     return (T)this._typeHandler;
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
/*     */   public String getGenericSignature()
/*     */   {
/* 380 */     StringBuilder localStringBuilder = new StringBuilder(40);
/* 381 */     getGenericSignature(localStringBuilder);
/* 382 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract StringBuilder getGenericSignature(StringBuilder paramStringBuilder);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getErasedSignature()
/*     */   {
/* 401 */     StringBuilder localStringBuilder = new StringBuilder(40);
/* 402 */     getErasedSignature(localStringBuilder);
/* 403 */     return localStringBuilder.toString();
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
/*     */   public abstract StringBuilder getErasedSignature(StringBuilder paramStringBuilder);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _assertSubclass(Class<?> paramClass1, Class<?> paramClass2)
/*     */   {
/* 427 */     if (!this._class.isAssignableFrom(paramClass1)) {
/* 428 */       throw new IllegalArgumentException("Class " + paramClass1.getName() + " is not assignable to " + this._class.getName());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract String toString();
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean equals(Object paramObject);
/*     */   
/*     */ 
/*     */ 
/*     */   public final int hashCode()
/*     */   {
/* 445 */     return this._hashCode;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/JavaType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */