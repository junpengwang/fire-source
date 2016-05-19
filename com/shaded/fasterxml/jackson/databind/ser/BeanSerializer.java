/*     */ package com.shaded.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.BeanAsArraySerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.impl.UnwrappingBeanSerializer;
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
/*     */ public class BeanSerializer
/*     */   extends BeanSerializerBase
/*     */ {
/*     */   public BeanSerializer(JavaType paramJavaType, BeanSerializerBuilder paramBeanSerializerBuilder, BeanPropertyWriter[] paramArrayOfBeanPropertyWriter1, BeanPropertyWriter[] paramArrayOfBeanPropertyWriter2)
/*     */   {
/*  44 */     super(paramJavaType, paramBeanSerializerBuilder, paramArrayOfBeanPropertyWriter1, paramArrayOfBeanPropertyWriter2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BeanSerializer(BeanSerializerBase paramBeanSerializerBase)
/*     */   {
/*  53 */     super(paramBeanSerializerBase);
/*     */   }
/*     */   
/*     */   protected BeanSerializer(BeanSerializerBase paramBeanSerializerBase, ObjectIdWriter paramObjectIdWriter) {
/*  57 */     super(paramBeanSerializerBase, paramObjectIdWriter);
/*     */   }
/*     */   
/*     */   protected BeanSerializer(BeanSerializerBase paramBeanSerializerBase, String[] paramArrayOfString) {
/*  61 */     super(paramBeanSerializerBase, paramArrayOfString);
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
/*     */   public static BeanSerializer createDummy(JavaType paramJavaType)
/*     */   {
/*  76 */     return new BeanSerializer(paramJavaType, null, NO_PROPS, null);
/*     */   }
/*     */   
/*     */   public JsonSerializer<Object> unwrappingSerializer(NameTransformer paramNameTransformer)
/*     */   {
/*  81 */     return new UnwrappingBeanSerializer(this, paramNameTransformer);
/*     */   }
/*     */   
/*     */   public BeanSerializer withObjectIdWriter(ObjectIdWriter paramObjectIdWriter)
/*     */   {
/*  86 */     return new BeanSerializer(this, paramObjectIdWriter);
/*     */   }
/*     */   
/*     */   protected BeanSerializer withIgnorals(String[] paramArrayOfString)
/*     */   {
/*  91 */     return new BeanSerializer(this, paramArrayOfString);
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
/*     */   protected BeanSerializerBase asArraySerializer()
/*     */   {
/* 109 */     if ((this._objectIdWriter == null) && (this._anyGetterWriter == null) && (this._propertyFilterId == null))
/*     */     {
/*     */ 
/*     */ 
/* 113 */       return new BeanAsArraySerializer(this);
/*     */     }
/*     */     
/* 116 */     return this;
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
/* 134 */     if (this._objectIdWriter != null) {
/* 135 */       _serializeWithObjectId(paramObject, paramJsonGenerator, paramSerializerProvider, true);
/* 136 */       return;
/*     */     }
/* 138 */     paramJsonGenerator.writeStartObject();
/* 139 */     if (this._propertyFilterId != null) {
/* 140 */       serializeFieldsFiltered(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     } else {
/* 142 */       serializeFields(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/* 144 */     paramJsonGenerator.writeEndObject();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 154 */     return "BeanSerializer for " + handledType().getName();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/BeanSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */