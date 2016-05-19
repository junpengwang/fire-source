/*    */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.ContainerSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
/*    */ import java.io.IOException;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public class IteratorSerializer
/*    */   extends AsArraySerializerBase<Iterator<?>>
/*    */ {
/*    */   public IteratorSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, BeanProperty paramBeanProperty)
/*    */   {
/* 22 */     super(Iterator.class, paramJavaType, paramBoolean, paramTypeSerializer, paramBeanProperty, null);
/*    */   }
/*    */   
/*    */ 
/*    */   public IteratorSerializer(IteratorSerializer paramIteratorSerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*    */   {
/* 28 */     super(paramIteratorSerializer, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
/*    */   }
/*    */   
/*    */   public boolean isEmpty(Iterator<?> paramIterator)
/*    */   {
/* 33 */     return (paramIterator == null) || (!paramIterator.hasNext());
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean hasSingleElement(Iterator<?> paramIterator)
/*    */   {
/* 39 */     return false;
/*    */   }
/*    */   
/*    */   public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*    */   {
/* 44 */     return new IteratorSerializer(this._elementType, this._staticTyping, paramTypeSerializer, this._property);
/*    */   }
/*    */   
/*    */ 
/*    */   public IteratorSerializer withResolved(BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*    */   {
/* 50 */     return new IteratorSerializer(this, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
/*    */   }
/*    */   
/*    */ 
/*    */   public void serializeContents(Iterator<?> paramIterator, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 57 */     if (paramIterator.hasNext()) {
/* 58 */       TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/* 59 */       Object localObject1 = null;
/* 60 */       Object localObject2 = null;
/*    */       do {
/* 62 */         Object localObject3 = paramIterator.next();
/* 63 */         if (localObject3 == null) {
/* 64 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*    */         }
/*    */         else {
/* 67 */           Class localClass = localObject3.getClass();
/*    */           Object localObject4;
/* 69 */           if (localClass == localObject2) {
/* 70 */             localObject4 = localObject1;
/*    */           } else {
/* 72 */             localObject4 = paramSerializerProvider.findValueSerializer(localClass, this._property);
/* 73 */             localObject1 = localObject4;
/* 74 */             localObject2 = localClass;
/*    */           }
/* 76 */           if (localTypeSerializer == null) {
/* 77 */             ((JsonSerializer)localObject4).serialize(localObject3, paramJsonGenerator, paramSerializerProvider);
/*    */           } else {
/* 79 */             ((JsonSerializer)localObject4).serializeWithType(localObject3, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
/*    */           }
/*    */         }
/* 82 */       } while (paramIterator.hasNext());
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/IteratorSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */