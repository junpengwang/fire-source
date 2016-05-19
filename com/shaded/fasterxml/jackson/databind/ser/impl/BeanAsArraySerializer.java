/*     */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException.Reference;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.BeanPropertyWriter;
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
/*     */ public class BeanAsArraySerializer
/*     */   extends BeanSerializerBase
/*     */ {
/*     */   protected final BeanSerializerBase _defaultSerializer;
/*     */   
/*     */   public BeanAsArraySerializer(BeanSerializerBase paramBeanSerializerBase)
/*     */   {
/*  64 */     super(paramBeanSerializerBase, (ObjectIdWriter)null);
/*  65 */     this._defaultSerializer = paramBeanSerializerBase;
/*     */   }
/*     */   
/*     */   protected BeanAsArraySerializer(BeanSerializerBase paramBeanSerializerBase, String[] paramArrayOfString) {
/*  69 */     super(paramBeanSerializerBase, paramArrayOfString);
/*  70 */     this._defaultSerializer = paramBeanSerializerBase;
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
/*     */   public JsonSerializer<Object> unwrappingSerializer(NameTransformer paramNameTransformer)
/*     */   {
/*  84 */     return this._defaultSerializer.unwrappingSerializer(paramNameTransformer);
/*     */   }
/*     */   
/*     */   public boolean isUnwrappingSerializer()
/*     */   {
/*  89 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public BeanSerializerBase withObjectIdWriter(ObjectIdWriter paramObjectIdWriter)
/*     */   {
/*  95 */     return this._defaultSerializer.withObjectIdWriter(paramObjectIdWriter);
/*     */   }
/*     */   
/*     */   protected BeanAsArraySerializer withIgnorals(String[] paramArrayOfString)
/*     */   {
/* 100 */     return new BeanAsArraySerializer(this, paramArrayOfString);
/*     */   }
/*     */   
/*     */ 
/*     */   protected BeanSerializerBase asArraySerializer()
/*     */   {
/* 106 */     return this;
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
/*     */   public void serializeWithType(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 124 */     this._defaultSerializer.serializeWithType(paramObject, paramJsonGenerator, paramSerializerProvider, paramTypeSerializer);
/*     */   }
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
/* 137 */     if ((paramSerializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) && (hasSingleElement(paramSerializerProvider)))
/*     */     {
/* 139 */       serializeAsArray(paramObject, paramJsonGenerator, paramSerializerProvider);
/* 140 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 146 */     paramJsonGenerator.writeStartArray();
/* 147 */     serializeAsArray(paramObject, paramJsonGenerator, paramSerializerProvider);
/* 148 */     paramJsonGenerator.writeEndArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean hasSingleElement(SerializerProvider paramSerializerProvider)
/*     */   {
/*     */     BeanPropertyWriter[] arrayOfBeanPropertyWriter;
/*     */     
/*     */ 
/* 158 */     if ((this._filteredProps != null) && (paramSerializerProvider.getActiveView() != null)) {
/* 159 */       arrayOfBeanPropertyWriter = this._filteredProps;
/*     */     } else {
/* 161 */       arrayOfBeanPropertyWriter = this._props;
/*     */     }
/* 163 */     return arrayOfBeanPropertyWriter.length == 1;
/*     */   }
/*     */   
/*     */   protected final void serializeAsArray(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*     */     BeanPropertyWriter[] arrayOfBeanPropertyWriter;
/* 170 */     if ((this._filteredProps != null) && (paramSerializerProvider.getActiveView() != null)) {
/* 171 */       arrayOfBeanPropertyWriter = this._filteredProps;
/*     */     } else {
/* 173 */       arrayOfBeanPropertyWriter = this._props;
/*     */     }
/*     */     
/* 176 */     int i = 0;
/*     */     try {
/* 178 */       for (int j = arrayOfBeanPropertyWriter.length; i < j; i++) {
/* 179 */         localObject = arrayOfBeanPropertyWriter[i];
/* 180 */         if (localObject == null) {
/* 181 */           paramJsonGenerator.writeNull();
/*     */         } else {
/* 183 */           ((BeanPropertyWriter)localObject).serializeAsColumn(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */         }
/*     */         
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 191 */       localObject = i == arrayOfBeanPropertyWriter.length ? "[anySetter]" : arrayOfBeanPropertyWriter[i].getName();
/* 192 */       wrapAndThrow(paramSerializerProvider, localException, paramObject, (String)localObject);
/*     */     } catch (StackOverflowError localStackOverflowError) {
/* 194 */       Object localObject = new JsonMappingException("Infinite recursion (StackOverflowError)", localStackOverflowError);
/* 195 */       String str = i == arrayOfBeanPropertyWriter.length ? "[anySetter]" : arrayOfBeanPropertyWriter[i].getName();
/* 196 */       ((JsonMappingException)localObject).prependPath(new JsonMappingException.Reference(paramObject, str));
/* 197 */       throw ((Throwable)localObject);
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
/* 208 */     return "BeanAsArraySerializer for " + handledType().getName();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/BeanAsArraySerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */