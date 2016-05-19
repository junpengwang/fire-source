/*     */ package com.shaded.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import java.util.Collection;
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
/*     */ public class CollectionLikeType
/*     */   extends TypeBase
/*     */ {
/*     */   private static final long serialVersionUID = 4611641304150899138L;
/*     */   protected final JavaType _elementType;
/*     */   
/*     */   protected CollectionLikeType(Class<?> paramClass, JavaType paramJavaType, Object paramObject1, Object paramObject2, boolean paramBoolean)
/*     */   {
/*  32 */     super(paramClass, paramJavaType.hashCode(), paramObject1, paramObject2, paramBoolean);
/*  33 */     this._elementType = paramJavaType;
/*     */   }
/*     */   
/*     */   protected JavaType _narrow(Class<?> paramClass)
/*     */   {
/*  38 */     return new CollectionLikeType(paramClass, this._elementType, this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType narrowContentsBy(Class<?> paramClass)
/*     */   {
/*  46 */     if (paramClass == this._elementType.getRawClass()) {
/*  47 */       return this;
/*     */     }
/*  49 */     return new CollectionLikeType(this._class, this._elementType.narrowBy(paramClass), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType widenContentsBy(Class<?> paramClass)
/*     */   {
/*  57 */     if (paramClass == this._elementType.getRawClass()) {
/*  58 */       return this;
/*     */     }
/*  60 */     return new CollectionLikeType(this._class, this._elementType.widenBy(paramClass), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static CollectionLikeType construct(Class<?> paramClass, JavaType paramJavaType)
/*     */   {
/*  67 */     return new CollectionLikeType(paramClass, paramJavaType, null, null, false);
/*     */   }
/*     */   
/*     */ 
/*     */   public CollectionLikeType withTypeHandler(Object paramObject)
/*     */   {
/*  73 */     return new CollectionLikeType(this._class, this._elementType, this._valueHandler, paramObject, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public CollectionLikeType withContentTypeHandler(Object paramObject)
/*     */   {
/*  79 */     return new CollectionLikeType(this._class, this._elementType.withTypeHandler(paramObject), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public CollectionLikeType withValueHandler(Object paramObject)
/*     */   {
/*  85 */     return new CollectionLikeType(this._class, this._elementType, paramObject, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */   public CollectionLikeType withContentValueHandler(Object paramObject)
/*     */   {
/*  90 */     return new CollectionLikeType(this._class, this._elementType.withValueHandler(paramObject), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public CollectionLikeType withStaticTyping()
/*     */   {
/*  96 */     if (this._asStatic) {
/*  97 */       return this;
/*     */     }
/*  99 */     return new CollectionLikeType(this._class, this._elementType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isContainerType()
/*     */   {
/* 110 */     return true;
/*     */   }
/*     */   
/* 113 */   public boolean isCollectionLikeType() { return true; }
/*     */   
/*     */   public JavaType getContentType() {
/* 116 */     return this._elementType;
/*     */   }
/*     */   
/* 119 */   public int containedTypeCount() { return 1; }
/*     */   
/*     */   public JavaType containedType(int paramInt)
/*     */   {
/* 123 */     return paramInt == 0 ? this._elementType : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String containedTypeName(int paramInt)
/*     */   {
/* 132 */     if (paramInt == 0) return "E";
/* 133 */     return null;
/*     */   }
/*     */   
/*     */   public StringBuilder getErasedSignature(StringBuilder paramStringBuilder)
/*     */   {
/* 138 */     return _classSignature(this._class, paramStringBuilder, true);
/*     */   }
/*     */   
/*     */   public StringBuilder getGenericSignature(StringBuilder paramStringBuilder)
/*     */   {
/* 143 */     _classSignature(this._class, paramStringBuilder, false);
/* 144 */     paramStringBuilder.append('<');
/* 145 */     this._elementType.getGenericSignature(paramStringBuilder);
/* 146 */     paramStringBuilder.append(">;");
/* 147 */     return paramStringBuilder;
/*     */   }
/*     */   
/*     */   protected String buildCanonicalName()
/*     */   {
/* 152 */     StringBuilder localStringBuilder = new StringBuilder();
/* 153 */     localStringBuilder.append(this._class.getName());
/* 154 */     if (this._elementType != null) {
/* 155 */       localStringBuilder.append('<');
/* 156 */       localStringBuilder.append(this._elementType.toCanonical());
/* 157 */       localStringBuilder.append('>');
/*     */     }
/* 159 */     return localStringBuilder.toString();
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
/*     */   public boolean isTrueCollectionType()
/*     */   {
/* 175 */     return Collection.class.isAssignableFrom(this._class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 187 */     if (paramObject == this) return true;
/* 188 */     if (paramObject == null) return false;
/* 189 */     if (paramObject.getClass() != getClass()) { return false;
/*     */     }
/* 191 */     CollectionLikeType localCollectionLikeType = (CollectionLikeType)paramObject;
/* 192 */     return (this._class == localCollectionLikeType._class) && (this._elementType.equals(localCollectionLikeType._elementType));
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 198 */     return "[collection-like type; class " + this._class.getName() + ", contains " + this._elementType + "]";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/CollectionLikeType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */