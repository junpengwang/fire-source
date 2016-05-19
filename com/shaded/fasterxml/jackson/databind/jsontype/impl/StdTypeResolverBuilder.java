/*     */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.Id;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.NamedType;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
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
/*     */ public class StdTypeResolverBuilder
/*     */   implements TypeResolverBuilder<StdTypeResolverBuilder>
/*     */ {
/*     */   protected JsonTypeInfo.Id _idType;
/*     */   protected JsonTypeInfo.As _includeAs;
/*     */   protected String _typeProperty;
/*  33 */   protected boolean _typeIdVisible = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Class<?> _defaultImpl;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected TypeIdResolver _customIdResolver;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StdTypeResolverBuilder noTypeInfoBuilder()
/*     */   {
/*  54 */     return new StdTypeResolverBuilder().init(JsonTypeInfo.Id.NONE, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public StdTypeResolverBuilder init(JsonTypeInfo.Id paramId, TypeIdResolver paramTypeIdResolver)
/*     */   {
/*  61 */     if (paramId == null) {
/*  62 */       throw new IllegalArgumentException("idType can not be null");
/*     */     }
/*  64 */     this._idType = paramId;
/*  65 */     this._customIdResolver = paramTypeIdResolver;
/*     */     
/*  67 */     this._typeProperty = paramId.getDefaultPropertyName();
/*  68 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public TypeSerializer buildTypeSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, Collection<NamedType> paramCollection)
/*     */   {
/*  75 */     if (this._idType == JsonTypeInfo.Id.NONE) {
/*  76 */       return null;
/*     */     }
/*  78 */     TypeIdResolver localTypeIdResolver = idResolver(paramSerializationConfig, paramJavaType, paramCollection, true, false);
/*  79 */     switch (this._includeAs) {
/*     */     case WRAPPER_ARRAY: 
/*  81 */       return new AsArrayTypeSerializer(localTypeIdResolver, null);
/*     */     case PROPERTY: 
/*  83 */       return new AsPropertyTypeSerializer(localTypeIdResolver, null, this._typeProperty);
/*     */     
/*     */     case WRAPPER_OBJECT: 
/*  86 */       return new AsWrapperTypeSerializer(localTypeIdResolver, null);
/*     */     case EXTERNAL_PROPERTY: 
/*  88 */       return new AsExternalTypeSerializer(localTypeIdResolver, null, this._typeProperty);
/*     */     }
/*     */     
/*  91 */     throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + this._includeAs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public TypeDeserializer buildTypeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, Collection<NamedType> paramCollection)
/*     */   {
/*  98 */     if (this._idType == JsonTypeInfo.Id.NONE) {
/*  99 */       return null;
/*     */     }
/*     */     
/* 102 */     TypeIdResolver localTypeIdResolver = idResolver(paramDeserializationConfig, paramJavaType, paramCollection, false, true);
/*     */     
/*     */ 
/* 105 */     switch (this._includeAs) {
/*     */     case WRAPPER_ARRAY: 
/* 107 */       return new AsArrayTypeDeserializer(paramJavaType, localTypeIdResolver, this._typeProperty, this._typeIdVisible, this._defaultImpl);
/*     */     
/*     */     case PROPERTY: 
/* 110 */       return new AsPropertyTypeDeserializer(paramJavaType, localTypeIdResolver, this._typeProperty, this._typeIdVisible, this._defaultImpl);
/*     */     
/*     */     case WRAPPER_OBJECT: 
/* 113 */       return new AsWrapperTypeDeserializer(paramJavaType, localTypeIdResolver, this._typeProperty, this._typeIdVisible, this._defaultImpl);
/*     */     
/*     */     case EXTERNAL_PROPERTY: 
/* 116 */       return new AsExternalTypeDeserializer(paramJavaType, localTypeIdResolver, this._typeProperty, this._typeIdVisible, this._defaultImpl);
/*     */     }
/*     */     
/* 119 */     throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + this._includeAs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StdTypeResolverBuilder inclusion(JsonTypeInfo.As paramAs)
/*     */   {
/* 130 */     if (paramAs == null) {
/* 131 */       throw new IllegalArgumentException("includeAs can not be null");
/*     */     }
/* 133 */     this._includeAs = paramAs;
/* 134 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StdTypeResolverBuilder typeProperty(String paramString)
/*     */   {
/* 145 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 146 */       paramString = this._idType.getDefaultPropertyName();
/*     */     }
/* 148 */     this._typeProperty = paramString;
/* 149 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public StdTypeResolverBuilder defaultImpl(Class<?> paramClass)
/*     */   {
/* 155 */     this._defaultImpl = paramClass;
/* 156 */     return this;
/*     */   }
/*     */   
/*     */   public StdTypeResolverBuilder typeIdVisibility(boolean paramBoolean)
/*     */   {
/* 161 */     this._typeIdVisible = paramBoolean;
/* 162 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTypeProperty()
/*     */   {
/* 171 */     return this._typeProperty;
/*     */   }
/*     */   
/*     */   public Class<?> getDefaultImpl() {
/* 175 */     return this._defaultImpl;
/*     */   }
/*     */   
/* 178 */   public boolean isTypeIdVisible() { return this._typeIdVisible; }
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
/*     */   protected TypeIdResolver idResolver(MapperConfig<?> paramMapperConfig, JavaType paramJavaType, Collection<NamedType> paramCollection, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 196 */     if (this._customIdResolver != null) {
/* 197 */       return this._customIdResolver;
/*     */     }
/* 199 */     if (this._idType == null) {
/* 200 */       throw new IllegalStateException("Can not build, 'init()' not yet called");
/*     */     }
/* 202 */     switch (this._idType) {
/*     */     case CLASS: 
/* 204 */       return new ClassNameIdResolver(paramJavaType, paramMapperConfig.getTypeFactory());
/*     */     case MINIMAL_CLASS: 
/* 206 */       return new MinimalClassNameIdResolver(paramJavaType, paramMapperConfig.getTypeFactory());
/*     */     case NAME: 
/* 208 */       return TypeNameIdResolver.construct(paramMapperConfig, paramJavaType, paramCollection, paramBoolean1, paramBoolean2);
/*     */     case NONE: 
/* 210 */       return null;
/*     */     }
/*     */     
/* 213 */     throw new IllegalStateException("Do not know how to construct standard type id resolver for idType: " + this._idType);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/StdTypeResolverBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */