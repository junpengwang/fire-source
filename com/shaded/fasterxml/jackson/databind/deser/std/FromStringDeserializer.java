/*    */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class FromStringDeserializer<T>
/*    */   extends StdScalarDeserializer<T>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   protected FromStringDeserializer(Class<?> paramClass)
/*    */   {
/* 19 */     super(paramClass);
/*    */   }
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
/*    */   public final T deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 34 */     String str = paramJsonParser.getValueAsString();
/* 35 */     if (str != null) {
/* 36 */       if ((str.length() == 0) || ((str = str.trim()).length() == 0))
/*    */       {
/* 38 */         return null;
/*    */       }
/*    */       try {
/* 41 */         Object localObject1 = _deserialize(str, paramDeserializationContext);
/* 42 */         if (localObject1 != null) {
/* 43 */           return (T)localObject1;
/*    */         }
/*    */       }
/*    */       catch (IllegalArgumentException localIllegalArgumentException) {}
/*    */       
/* 48 */       throw paramDeserializationContext.weirdStringException(str, this._valueClass, "not a valid textual representation");
/*    */     }
/* 50 */     if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_EMBEDDED_OBJECT)
/*    */     {
/* 52 */       Object localObject2 = paramJsonParser.getEmbeddedObject();
/* 53 */       if (localObject2 == null) {
/* 54 */         return null;
/*    */       }
/* 56 */       if (this._valueClass.isAssignableFrom(localObject2.getClass())) {
/* 57 */         return (T)localObject2;
/*    */       }
/* 59 */       return (T)_deserializeEmbedded(localObject2, paramDeserializationContext);
/*    */     }
/* 61 */     throw paramDeserializationContext.mappingException(this._valueClass);
/*    */   }
/*    */   
/*    */ 
/*    */   protected abstract T _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*    */     throws IOException, JsonProcessingException;
/*    */   
/*    */   protected T _deserializeEmbedded(Object paramObject, DeserializationContext paramDeserializationContext)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 71 */     throw paramDeserializationContext.mappingException("Don't know how to convert embedded Object of type " + paramObject.getClass().getName() + " into " + this._valueClass.getName());
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/FromStringDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */