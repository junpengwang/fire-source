/*    */ package com.shaded.fasterxml.jackson.databind.deser;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.AbstractTypeResolver;
/*    */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*    */ import com.shaded.fasterxml.jackson.databind.type.ArrayType;
/*    */ import com.shaded.fasterxml.jackson.databind.type.CollectionLikeType;
/*    */ import com.shaded.fasterxml.jackson.databind.type.CollectionType;
/*    */ import com.shaded.fasterxml.jackson.databind.type.MapLikeType;
/*    */ import com.shaded.fasterxml.jackson.databind.type.MapType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DeserializerFactory
/*    */ {
/* 43 */   protected static final Deserializers[] NO_DESERIALIZERS = new Deserializers[0];
/*    */   
/*    */   public abstract DeserializerFactory withAdditionalDeserializers(Deserializers paramDeserializers);
/*    */   
/*    */   public abstract DeserializerFactory withAdditionalKeyDeserializers(KeyDeserializers paramKeyDeserializers);
/*    */   
/*    */   public abstract DeserializerFactory withDeserializerModifier(BeanDeserializerModifier paramBeanDeserializerModifier);
/*    */   
/*    */   public abstract DeserializerFactory withAbstractTypeResolver(AbstractTypeResolver paramAbstractTypeResolver);
/*    */   
/*    */   public abstract DeserializerFactory withValueInstantiators(ValueInstantiators paramValueInstantiators);
/*    */   
/*    */   public abstract JavaType mapAbstractType(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract ValueInstantiator findValueInstantiator(DeserializationContext paramDeserializationContext, BeanDescription paramBeanDescription)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<Object> createBeanDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<Object> createBuilderBasedDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription, Class<?> paramClass)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createArrayDeserializer(DeserializationContext paramDeserializationContext, ArrayType paramArrayType, BeanDescription paramBeanDescription)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createCollectionDeserializer(DeserializationContext paramDeserializationContext, CollectionType paramCollectionType, BeanDescription paramBeanDescription)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createCollectionLikeDeserializer(DeserializationContext paramDeserializationContext, CollectionLikeType paramCollectionLikeType, BeanDescription paramBeanDescription)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createEnumDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createMapDeserializer(DeserializationContext paramDeserializationContext, MapType paramMapType, BeanDescription paramBeanDescription)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createMapLikeDeserializer(DeserializationContext paramDeserializationContext, MapLikeType paramMapLikeType, BeanDescription paramBeanDescription)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract JsonDeserializer<?> createTreeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract KeyDeserializer createKeyDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract TypeDeserializer findTypeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
/*    */     throws JsonMappingException;
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/DeserializerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */