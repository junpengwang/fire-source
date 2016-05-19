/*    */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NullifyingDeserializer
/*    */   extends StdDeserializer<Object>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 20 */   public static final NullifyingDeserializer instance = new NullifyingDeserializer();
/*    */   
/* 22 */   public NullifyingDeserializer() { super(Object.class); }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 34 */     paramJsonParser.skipChildren();
/* 35 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 45 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 46 */     switch (localJsonToken) {
/*    */     case START_ARRAY: 
/*    */     case START_OBJECT: 
/*    */     case FIELD_NAME: 
/* 50 */       return paramTypeDeserializer.deserializeTypedFromAny(paramJsonParser, paramDeserializationContext);
/*    */     }
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/NullifyingDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */