/*    */ package com.shaded.fasterxml.jackson.databind.jsontype.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.deser.std.StdDeserializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FailingDeserializer
/*    */   extends StdDeserializer<Object>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final String _message;
/*    */   
/*    */   public FailingDeserializer(String paramString)
/*    */   {
/* 21 */     super(Object.class);
/* 22 */     this._message = paramString;
/*    */   }
/*    */   
/*    */ 
/*    */   public Object deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*    */     throws JsonMappingException
/*    */   {
/* 29 */     throw paramDeserializationContext.mappingException(this._message);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/impl/FailingDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */