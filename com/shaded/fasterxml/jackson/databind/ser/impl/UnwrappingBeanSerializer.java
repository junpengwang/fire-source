/*     */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*     */ import java.io.IOException;
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
/*     */ public class UnwrappingBeanSerializer
/*     */   extends BeanSerializerBase
/*     */ {
/*     */   protected final NameTransformer _nameTransformer;
/*     */   
/*     */   public UnwrappingBeanSerializer(BeanSerializerBase paramBeanSerializerBase, NameTransformer paramNameTransformer)
/*     */   {
/*  31 */     super(paramBeanSerializerBase, paramNameTransformer);
/*  32 */     this._nameTransformer = paramNameTransformer;
/*     */   }
/*     */   
/*     */   public UnwrappingBeanSerializer(UnwrappingBeanSerializer paramUnwrappingBeanSerializer, ObjectIdWriter paramObjectIdWriter) {
/*  36 */     super(paramUnwrappingBeanSerializer, paramObjectIdWriter);
/*  37 */     this._nameTransformer = paramUnwrappingBeanSerializer._nameTransformer;
/*     */   }
/*     */   
/*     */   protected UnwrappingBeanSerializer(UnwrappingBeanSerializer paramUnwrappingBeanSerializer, String[] paramArrayOfString) {
/*  41 */     super(paramUnwrappingBeanSerializer, paramArrayOfString);
/*  42 */     this._nameTransformer = paramUnwrappingBeanSerializer._nameTransformer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonSerializer<Object> unwrappingSerializer(NameTransformer paramNameTransformer)
/*     */   {
/*  54 */     return new UnwrappingBeanSerializer(this, paramNameTransformer);
/*     */   }
/*     */   
/*     */   public boolean isUnwrappingSerializer()
/*     */   {
/*  59 */     return true;
/*     */   }
/*     */   
/*     */   public UnwrappingBeanSerializer withObjectIdWriter(ObjectIdWriter paramObjectIdWriter)
/*     */   {
/*  64 */     return new UnwrappingBeanSerializer(this, paramObjectIdWriter);
/*     */   }
/*     */   
/*     */   protected UnwrappingBeanSerializer withIgnorals(String[] paramArrayOfString)
/*     */   {
/*  69 */     return new UnwrappingBeanSerializer(this, paramArrayOfString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BeanSerializerBase asArraySerializer()
/*     */   {
/*  78 */     return this;
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
/*     */   public final void serialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*  96 */     if (this._objectIdWriter != null) {
/*  97 */       _serializeWithObjectId(paramObject, paramJsonGenerator, paramSerializerProvider, false);
/*  98 */       return;
/*     */     }
/* 100 */     if (this._propertyFilterId != null) {
/* 101 */       serializeFieldsFiltered(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/* 103 */       serializeFields(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 114 */     return "UnwrappingBeanSerializer for " + handledType().getName();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/UnwrappingBeanSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */