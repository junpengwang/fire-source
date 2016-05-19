/*    */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*    */ import com.shaded.fasterxml.jackson.core.Base64Variants;
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public final class StringDeserializer
/*    */   extends StdScalarDeserializer<String>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 20 */   public static final StringDeserializer instance = new StringDeserializer();
/*    */   
/* 22 */   public StringDeserializer() { super(String.class); }
/*    */   
/*    */ 
/*    */ 
/*    */   public String deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 29 */     String str = paramJsonParser.getValueAsString();
/* 30 */     if (str != null) {
/* 31 */       return str;
/*    */     }
/*    */     
/* 34 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 35 */     if (localJsonToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
/* 36 */       Object localObject = paramJsonParser.getEmbeddedObject();
/* 37 */       if (localObject == null) {
/* 38 */         return null;
/*    */       }
/* 40 */       if ((localObject instanceof byte[])) {
/* 41 */         return Base64Variants.getDefaultVariant().encode((byte[])localObject, false);
/*    */       }
/*    */       
/* 44 */       return localObject.toString();
/*    */     }
/* 46 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 56 */     return deserialize(paramJsonParser, paramDeserializationContext);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/StringDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */