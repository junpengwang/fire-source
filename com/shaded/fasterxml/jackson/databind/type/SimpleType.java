/*     */ package com.shaded.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import java.util.Collection;
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
/*     */ public final class SimpleType
/*     */   extends TypeBase
/*     */ {
/*     */   private static final long serialVersionUID = -800374828948534376L;
/*     */   protected final JavaType[] _typeParameters;
/*     */   protected final String[] _typeNames;
/*     */   
/*     */   protected SimpleType(Class<?> paramClass)
/*     */   {
/*  36 */     this(paramClass, null, null, null, null, false);
/*     */   }
/*     */   
/*     */ 
/*     */   protected SimpleType(Class<?> paramClass, String[] paramArrayOfString, JavaType[] paramArrayOfJavaType, Object paramObject1, Object paramObject2, boolean paramBoolean)
/*     */   {
/*  42 */     super(paramClass, 0, paramObject1, paramObject2, paramBoolean);
/*  43 */     if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
/*  44 */       this._typeNames = null;
/*  45 */       this._typeParameters = null;
/*     */     } else {
/*  47 */       this._typeNames = paramArrayOfString;
/*  48 */       this._typeParameters = paramArrayOfJavaType;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static SimpleType constructUnsafe(Class<?> paramClass)
/*     */   {
/*  59 */     return new SimpleType(paramClass, null, null, null, null, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected JavaType _narrow(Class<?> paramClass)
/*     */   {
/*  66 */     return new SimpleType(paramClass, this._typeNames, this._typeParameters, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType narrowContentsBy(Class<?> paramClass)
/*     */   {
/*  74 */     throw new IllegalArgumentException("Internal error: SimpleType.narrowContentsBy() should never be called");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JavaType widenContentsBy(Class<?> paramClass)
/*     */   {
/*  81 */     throw new IllegalArgumentException("Internal error: SimpleType.widenContentsBy() should never be called");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static SimpleType construct(Class<?> paramClass)
/*     */   {
/*  89 */     if (Map.class.isAssignableFrom(paramClass)) {
/*  90 */       throw new IllegalArgumentException("Can not construct SimpleType for a Map (class: " + paramClass.getName() + ")");
/*     */     }
/*  92 */     if (Collection.class.isAssignableFrom(paramClass)) {
/*  93 */       throw new IllegalArgumentException("Can not construct SimpleType for a Collection (class: " + paramClass.getName() + ")");
/*     */     }
/*     */     
/*  96 */     if (paramClass.isArray()) {
/*  97 */       throw new IllegalArgumentException("Can not construct SimpleType for an array (class: " + paramClass.getName() + ")");
/*     */     }
/*  99 */     return new SimpleType(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */   public SimpleType withTypeHandler(Object paramObject)
/*     */   {
/* 105 */     return new SimpleType(this._class, this._typeNames, this._typeParameters, this._valueHandler, paramObject, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public JavaType withContentTypeHandler(Object paramObject)
/*     */   {
/* 111 */     throw new IllegalArgumentException("Simple types have no content types; can not call withContenTypeHandler()");
/*     */   }
/*     */   
/*     */   public SimpleType withValueHandler(Object paramObject)
/*     */   {
/* 116 */     if (paramObject == this._valueHandler) {
/* 117 */       return this;
/*     */     }
/* 119 */     return new SimpleType(this._class, this._typeNames, this._typeParameters, paramObject, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public SimpleType withContentValueHandler(Object paramObject)
/*     */   {
/* 125 */     throw new IllegalArgumentException("Simple types have no content types; can not call withContenValueHandler()");
/*     */   }
/*     */   
/*     */   public SimpleType withStaticTyping()
/*     */   {
/* 130 */     return this._asStatic ? this : new SimpleType(this._class, this._typeNames, this._typeParameters, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected String buildCanonicalName()
/*     */   {
/* 137 */     StringBuilder localStringBuilder = new StringBuilder();
/* 138 */     localStringBuilder.append(this._class.getName());
/* 139 */     if ((this._typeParameters != null) && (this._typeParameters.length > 0)) {
/* 140 */       localStringBuilder.append('<');
/* 141 */       int i = 1;
/* 142 */       for (JavaType localJavaType : this._typeParameters) {
/* 143 */         if (i != 0) {
/* 144 */           i = 0;
/*     */         } else {
/* 146 */           localStringBuilder.append(',');
/*     */         }
/* 148 */         localStringBuilder.append(localJavaType.toCanonical());
/*     */       }
/* 150 */       localStringBuilder.append('>');
/*     */     }
/* 152 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isContainerType()
/*     */   {
/* 162 */     return false;
/*     */   }
/*     */   
/*     */   public int containedTypeCount() {
/* 166 */     return this._typeParameters == null ? 0 : this._typeParameters.length;
/*     */   }
/*     */   
/*     */ 
/*     */   public JavaType containedType(int paramInt)
/*     */   {
/* 172 */     if ((paramInt < 0) || (this._typeParameters == null) || (paramInt >= this._typeParameters.length)) {
/* 173 */       return null;
/*     */     }
/* 175 */     return this._typeParameters[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */   public String containedTypeName(int paramInt)
/*     */   {
/* 181 */     if ((paramInt < 0) || (this._typeNames == null) || (paramInt >= this._typeNames.length)) {
/* 182 */       return null;
/*     */     }
/* 184 */     return this._typeNames[paramInt];
/*     */   }
/*     */   
/*     */   public StringBuilder getErasedSignature(StringBuilder paramStringBuilder)
/*     */   {
/* 189 */     return _classSignature(this._class, paramStringBuilder, true);
/*     */   }
/*     */   
/*     */ 
/*     */   public StringBuilder getGenericSignature(StringBuilder paramStringBuilder)
/*     */   {
/* 195 */     _classSignature(this._class, paramStringBuilder, false);
/* 196 */     if (this._typeParameters != null) {
/* 197 */       paramStringBuilder.append('<');
/* 198 */       for (JavaType localJavaType : this._typeParameters) {
/* 199 */         paramStringBuilder = localJavaType.getGenericSignature(paramStringBuilder);
/*     */       }
/* 201 */       paramStringBuilder.append('>');
/*     */     }
/* 203 */     paramStringBuilder.append(';');
/* 204 */     return paramStringBuilder;
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
/* 216 */     StringBuilder localStringBuilder = new StringBuilder(40);
/* 217 */     localStringBuilder.append("[simple type, class ").append(buildCanonicalName()).append(']');
/* 218 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 224 */     if (paramObject == this) return true;
/* 225 */     if (paramObject == null) return false;
/* 226 */     if (paramObject.getClass() != getClass()) { return false;
/*     */     }
/* 228 */     SimpleType localSimpleType = (SimpleType)paramObject;
/*     */     
/*     */ 
/* 231 */     if (localSimpleType._class != this._class) { return false;
/*     */     }
/*     */     
/* 234 */     JavaType[] arrayOfJavaType1 = this._typeParameters;
/* 235 */     JavaType[] arrayOfJavaType2 = localSimpleType._typeParameters;
/* 236 */     if (arrayOfJavaType1 == null) {
/* 237 */       return (arrayOfJavaType2 == null) || (arrayOfJavaType2.length == 0);
/*     */     }
/* 239 */     if (arrayOfJavaType2 == null) { return false;
/*     */     }
/* 241 */     if (arrayOfJavaType1.length != arrayOfJavaType2.length) return false;
/* 242 */     int i = 0; for (int j = arrayOfJavaType1.length; i < j; i++) {
/* 243 */       if (!arrayOfJavaType1[i].equals(arrayOfJavaType2[i])) {
/* 244 */         return false;
/*     */       }
/*     */     }
/* 247 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/SimpleType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */