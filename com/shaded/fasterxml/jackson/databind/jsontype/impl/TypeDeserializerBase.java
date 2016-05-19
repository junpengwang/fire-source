/*     */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.NoClass;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.std.NullifyingDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
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
/*     */ public abstract class TypeDeserializerBase
/*     */   extends TypeDeserializer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 278445030337366675L;
/*     */   protected final TypeIdResolver _idResolver;
/*     */   protected final JavaType _baseType;
/*     */   protected final BeanProperty _property;
/*     */   protected final JavaType _defaultImpl;
/*     */   protected final String _typePropertyName;
/*     */   protected final boolean _typeIdVisible;
/*     */   protected final HashMap<String, JsonDeserializer<Object>> _deserializers;
/*     */   protected JsonDeserializer<Object> _defaultImplDeserializer;
/*     */   
/*     */   protected TypeDeserializerBase(JavaType paramJavaType, TypeIdResolver paramTypeIdResolver, String paramString, boolean paramBoolean, Class<?> paramClass)
/*     */   {
/*  72 */     this._baseType = paramJavaType;
/*  73 */     this._idResolver = paramTypeIdResolver;
/*  74 */     this._typePropertyName = paramString;
/*  75 */     this._typeIdVisible = paramBoolean;
/*  76 */     this._deserializers = new HashMap();
/*  77 */     if (paramClass == null) {
/*  78 */       this._defaultImpl = null;
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/*  84 */       this._defaultImpl = paramJavaType.forcedNarrowBy(paramClass);
/*     */     }
/*     */     
/*  87 */     this._property = null;
/*     */   }
/*     */   
/*     */   protected TypeDeserializerBase(TypeDeserializerBase paramTypeDeserializerBase, BeanProperty paramBeanProperty)
/*     */   {
/*  92 */     this._baseType = paramTypeDeserializerBase._baseType;
/*  93 */     this._idResolver = paramTypeDeserializerBase._idResolver;
/*  94 */     this._typePropertyName = paramTypeDeserializerBase._typePropertyName;
/*  95 */     this._typeIdVisible = paramTypeDeserializerBase._typeIdVisible;
/*  96 */     this._deserializers = paramTypeDeserializerBase._deserializers;
/*  97 */     this._defaultImpl = paramTypeDeserializerBase._defaultImpl;
/*  98 */     this._defaultImplDeserializer = paramTypeDeserializerBase._defaultImplDeserializer;
/*     */     
/* 100 */     this._property = paramBeanProperty;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract TypeDeserializer forProperty(BeanProperty paramBeanProperty);
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract JsonTypeInfo.As getTypeInclusion();
/*     */   
/*     */ 
/*     */ 
/*     */   public String baseTypeName()
/*     */   {
/* 115 */     return this._baseType.getRawClass().getName();
/*     */   }
/*     */   
/* 118 */   public final String getPropertyName() { return this._typePropertyName; }
/*     */   
/*     */   public TypeIdResolver getTypeIdResolver() {
/* 121 */     return this._idResolver;
/*     */   }
/*     */   
/*     */   public Class<?> getDefaultImpl() {
/* 125 */     return this._defaultImpl == null ? null : this._defaultImpl.getRawClass();
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 131 */     StringBuilder localStringBuilder = new StringBuilder();
/* 132 */     localStringBuilder.append('[').append(getClass().getName());
/* 133 */     localStringBuilder.append("; base-type:").append(this._baseType);
/* 134 */     localStringBuilder.append("; id-resolver: ").append(this._idResolver);
/* 135 */     localStringBuilder.append(']');
/* 136 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JsonDeserializer<Object> _findDeserializer(DeserializationContext paramDeserializationContext, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*     */     JsonDeserializer localJsonDeserializer;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 151 */     synchronized (this._deserializers) {
/* 152 */       localJsonDeserializer = (JsonDeserializer)this._deserializers.get(paramString);
/* 153 */       if (localJsonDeserializer == null) {
/* 154 */         JavaType localJavaType = this._idResolver.typeFromId(paramString);
/* 155 */         if (localJavaType == null)
/*     */         {
/* 157 */           if (this._defaultImpl == null) {
/* 158 */             throw paramDeserializationContext.unknownTypeException(this._baseType, paramString);
/*     */           }
/* 160 */           localJsonDeserializer = _findDefaultImplDeserializer(paramDeserializationContext);
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 170 */           if ((this._baseType != null) && (this._baseType.getClass() == localJavaType.getClass())) {
/* 171 */             localJavaType = this._baseType.narrowBy(localJavaType.getRawClass());
/*     */           }
/* 173 */           localJsonDeserializer = paramDeserializationContext.findContextualValueDeserializer(localJavaType, this._property);
/*     */         }
/* 175 */         this._deserializers.put(paramString, localJsonDeserializer);
/*     */       }
/*     */     }
/* 178 */     return localJsonDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JsonDeserializer<Object> _findDefaultImplDeserializer(DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 188 */     if (this._defaultImpl == null) {
/* 189 */       if (!paramDeserializationContext.isEnabled(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)) {
/* 190 */         return NullifyingDeserializer.instance;
/*     */       }
/* 192 */       return null;
/*     */     }
/* 194 */     if (this._defaultImpl.getRawClass() == NoClass.class) {
/* 195 */       return NullifyingDeserializer.instance;
/*     */     }
/*     */     
/* 198 */     synchronized (this._defaultImpl) {
/* 199 */       if (this._defaultImplDeserializer == null) {
/* 200 */         this._defaultImplDeserializer = paramDeserializationContext.findContextualValueDeserializer(this._defaultImpl, this._property);
/*     */       }
/*     */       
/* 203 */       return this._defaultImplDeserializer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/TypeDeserializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */