/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.ContainerSerializer;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ArraySerializerBase<T>
/*    */   extends ContainerSerializer<T>
/*    */ {
/*    */   protected final BeanProperty _property;
/*    */   
/*    */   protected ArraySerializerBase(Class<T> paramClass)
/*    */   {
/* 24 */     super(paramClass);
/* 25 */     this._property = null;
/*    */   }
/*    */   
/*    */   protected ArraySerializerBase(Class<T> paramClass, BeanProperty paramBeanProperty)
/*    */   {
/* 30 */     super(paramClass);
/* 31 */     this._property = paramBeanProperty;
/*    */   }
/*    */   
/*    */   protected ArraySerializerBase(ArraySerializerBase<?> paramArraySerializerBase)
/*    */   {
/* 36 */     super(paramArraySerializerBase._handledType, false);
/* 37 */     this._property = paramArraySerializerBase._property;
/*    */   }
/*    */   
/*    */   protected ArraySerializerBase(ArraySerializerBase<?> paramArraySerializerBase, BeanProperty paramBeanProperty)
/*    */   {
/* 42 */     super(paramArraySerializerBase._handledType, false);
/* 43 */     this._property = paramBeanProperty;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public final void serialize(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 51 */     if ((paramSerializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) && (hasSingleElement(paramT)))
/*    */     {
/* 53 */       serializeContents(paramT, paramJsonGenerator, paramSerializerProvider);
/* 54 */       return;
/*    */     }
/* 56 */     paramJsonGenerator.writeStartArray();
/* 57 */     serializeContents(paramT, paramJsonGenerator, paramSerializerProvider);
/* 58 */     paramJsonGenerator.writeEndArray();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public final void serializeWithType(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 67 */     paramTypeSerializer.writeTypePrefixForArray(paramT, paramJsonGenerator);
/* 68 */     serializeContents(paramT, paramJsonGenerator, paramSerializerProvider);
/* 69 */     paramTypeSerializer.writeTypeSuffixForArray(paramT, paramJsonGenerator);
/*    */   }
/*    */   
/*    */   protected abstract void serializeContents(T paramT, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException;
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/ArraySerializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */