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
/*     */ 
/*     */ 
/*     */ public final class CollectionType
/*     */   extends CollectionLikeType
/*     */ {
/*     */   private static final long serialVersionUID = -7834910259750909424L;
/*     */   
/*     */   private CollectionType(Class<?> paramClass, JavaType paramJavaType, Object paramObject1, Object paramObject2, boolean paramBoolean)
/*     */   {
/*  22 */     super(paramClass, paramJavaType, paramObject1, paramObject2, paramBoolean);
/*     */   }
/*     */   
/*     */   protected JavaType _narrow(Class<?> paramClass)
/*     */   {
/*  27 */     return new CollectionType(paramClass, this._elementType, null, null, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JavaType narrowContentsBy(Class<?> paramClass)
/*     */   {
/*  34 */     if (paramClass == this._elementType.getRawClass()) {
/*  35 */       return this;
/*     */     }
/*  37 */     return new CollectionType(this._class, this._elementType.narrowBy(paramClass), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType widenContentsBy(Class<?> paramClass)
/*     */   {
/*  45 */     if (paramClass == this._elementType.getRawClass()) {
/*  46 */       return this;
/*     */     }
/*  48 */     return new CollectionType(this._class, this._elementType.widenBy(paramClass), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static CollectionType construct(Class<?> paramClass, JavaType paramJavaType)
/*     */   {
/*  55 */     return new CollectionType(paramClass, paramJavaType, null, null, false);
/*     */   }
/*     */   
/*     */ 
/*     */   public CollectionType withTypeHandler(Object paramObject)
/*     */   {
/*  61 */     return new CollectionType(this._class, this._elementType, this._valueHandler, paramObject, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CollectionType withContentTypeHandler(Object paramObject)
/*     */   {
/*  68 */     return new CollectionType(this._class, this._elementType.withTypeHandler(paramObject), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public CollectionType withValueHandler(Object paramObject)
/*     */   {
/*  74 */     return new CollectionType(this._class, this._elementType, paramObject, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */   public CollectionType withContentValueHandler(Object paramObject)
/*     */   {
/*  79 */     return new CollectionType(this._class, this._elementType.withValueHandler(paramObject), this._valueHandler, this._typeHandler, this._asStatic);
/*     */   }
/*     */   
/*     */ 
/*     */   public CollectionType withStaticTyping()
/*     */   {
/*  85 */     if (this._asStatic) {
/*  86 */       return this;
/*     */     }
/*  88 */     return new CollectionType(this._class, this._elementType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
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
/* 101 */     return "[collection type; class " + this._class.getName() + ", contains " + this._elementType + "]";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/CollectionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */