/*    */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*    */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ @JacksonStdImpl
/*    */ public class ClassDeserializer
/*    */   extends StdScalarDeserializer<Class<?>>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   public static final ClassDeserializer instance = new ClassDeserializer();
/*    */   
/* 19 */   public ClassDeserializer() { super(Class.class); }
/*    */   
/*    */ 
/*    */   public Class<?> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 25 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*    */     
/* 27 */     if (localJsonToken == JsonToken.VALUE_STRING) {
/* 28 */       String str = paramJsonParser.getText().trim();
/*    */       try {
/* 30 */         return paramDeserializationContext.findClass(str);
/*    */       } catch (Exception localException) {
/* 32 */         throw paramDeserializationContext.instantiationException(this._valueClass, ClassUtil.getRootCause(localException));
/*    */       }
/*    */     }
/* 35 */     throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/ClassDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */