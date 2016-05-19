/*    */ package com.shaded.fasterxml.jackson.databind.jsonFormatVisitors;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface JsonArrayFormatVisitor
/*    */   extends JsonFormatVisitorWithSerializerProvider
/*    */ {
/*    */   public abstract void itemsFormat(JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract void itemsFormat(JsonFormatTypes paramJsonFormatTypes)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public static class Base
/*    */     implements JsonArrayFormatVisitor
/*    */   {
/*    */     protected SerializerProvider _provider;
/*    */     
/*    */     public Base() {}
/*    */     
/*    */     public Base(SerializerProvider paramSerializerProvider)
/*    */     {
/* 37 */       this._provider = paramSerializerProvider;
/*    */     }
/*    */     
/* 40 */     public SerializerProvider getProvider() { return this._provider; }
/*    */     
/*    */     public void setProvider(SerializerProvider paramSerializerProvider) {
/* 43 */       this._provider = paramSerializerProvider;
/*    */     }
/*    */     
/*    */     public void itemsFormat(JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType)
/*    */       throws JsonMappingException
/*    */     {}
/*    */     
/*    */     public void itemsFormat(JsonFormatTypes paramJsonFormatTypes)
/*    */       throws JsonMappingException
/*    */     {}
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsonFormatVisitors/JsonArrayFormatVisitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */