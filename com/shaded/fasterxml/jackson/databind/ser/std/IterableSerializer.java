/*    */ package com.shaded.fasterxml.jackson.databind.ser.std;
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
/*    */ import java.io.IOException;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public class IterableSerializer
/*    */   extends AsArraySerializerBase<Iterable<?>>
/*    */ {
/*    */   public IterableSerializer(JavaType paramJavaType, boolean paramBoolean, TypeSerializer paramTypeSerializer, BeanProperty paramBeanProperty)
/*    */   {
/* 23 */     super(Iterable.class, paramJavaType, paramBoolean, paramTypeSerializer, paramBeanProperty, null);
/*    */   }
/*    */   
/*    */ 
/*    */   public IterableSerializer(IterableSerializer paramIterableSerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*    */   {
/* 29 */     super(paramIterableSerializer, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
/*    */   }
/*    */   
/*    */   public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*    */   {
/* 34 */     return new IterableSerializer(this._elementType, this._staticTyping, paramTypeSerializer, this._property);
/*    */   }
/*    */   
/*    */ 
/*    */   public IterableSerializer withResolved(BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer, JsonSerializer<?> paramJsonSerializer)
/*    */   {
/* 40 */     return new IterableSerializer(this, paramBeanProperty, paramTypeSerializer, paramJsonSerializer);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isEmpty(Iterable<?> paramIterable)
/*    */   {
/* 46 */     return (paramIterable == null) || (!paramIterable.iterator().hasNext());
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean hasSingleElement(Iterable<?> paramIterable)
/*    */   {
/* 52 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public void serializeContents(Iterable<?> paramIterable, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*    */     throws IOException, JsonGenerationException
/*    */   {
/* 59 */     Iterator localIterator = paramIterable.iterator();
/* 60 */     if (localIterator.hasNext()) {
/* 61 */       TypeSerializer localTypeSerializer = this._valueTypeSerializer;
/* 62 */       Object localObject1 = null;
/* 63 */       Object localObject2 = null;
/*    */       do
/*    */       {
/* 66 */         Object localObject3 = localIterator.next();
/* 67 */         if (localObject3 == null) {
/* 68 */           paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
/*    */         }
/*    */         else {
/* 71 */           Class localClass = localObject3.getClass();
/*    */           Object localObject4;
/* 73 */           if (localClass == localObject2) {
/* 74 */             localObject4 = localObject1;
/*    */           } else {
/* 76 */             localObject4 = paramSerializerProvider.findValueSerializer(localClass, this._property);
/* 77 */             localObject1 = localObject4;
/* 78 */             localObject2 = localClass;
/*    */           }
/* 80 */           if (localTypeSerializer == null) {
/* 81 */             ((JsonSerializer)localObject4).serialize(localObject3, paramJsonGenerator, paramSerializerProvider);
/*    */           } else {
/* 83 */             ((JsonSerializer)localObject4).serializeWithType(localObject3, paramJsonGenerator, paramSerializerProvider, localTypeSerializer);
/*    */           }
/*    */         }
/* 86 */       } while (localIterator.hasNext());
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/IterableSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */