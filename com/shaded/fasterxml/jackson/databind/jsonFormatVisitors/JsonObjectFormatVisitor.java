/*    */ package com.shaded.fasterxml.jackson.databind.jsonFormatVisitors;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*    */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*    */ 
/*    */ public abstract interface JsonObjectFormatVisitor
/*    */   extends JsonFormatVisitorWithSerializerProvider
/*    */ {
/*    */   public abstract void property(BeanProperty paramBeanProperty)
/*    */     throws JsonMappingException;
/*    */   
/*    */   public abstract void property(String paramString, JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType)
/*    */     throws JsonMappingException;
/*    */   
/*    */   @Deprecated
/*    */   public abstract void property(String paramString) throws JsonMappingException;
/*    */   
/*    */   public abstract void optionalProperty(BeanProperty paramBeanProperty) throws JsonMappingException;
/*    */   
/*    */   public abstract void optionalProperty(String paramString, JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType) throws JsonMappingException;
/*    */   
/*    */   @Deprecated
/*    */   public abstract void optionalProperty(String paramString) throws JsonMappingException;
/*    */   
/*    */   public static class Base implements JsonObjectFormatVisitor
/*    */   {
/*    */     protected SerializerProvider _provider;
/*    */     
/*    */     public Base() {}
/*    */     
/*    */     public Base(SerializerProvider paramSerializerProvider)
/*    */     {
/* 35 */       this._provider = paramSerializerProvider;
/*    */     }
/*    */     
/* 38 */     public SerializerProvider getProvider() { return this._provider; }
/*    */     
/*    */     public void setProvider(SerializerProvider paramSerializerProvider) {
/* 41 */       this._provider = paramSerializerProvider;
/*    */     }
/*    */     
/*    */     public void property(BeanProperty paramBeanProperty)
/*    */       throws JsonMappingException
/*    */     {}
/*    */     
/*    */     public void property(String paramString, JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType)
/*    */       throws JsonMappingException
/*    */     {}
/*    */     
/*    */     @Deprecated
/*    */     public void property(String paramString)
/*    */       throws JsonMappingException
/*    */     {}
/*    */     
/*    */     public void optionalProperty(BeanProperty paramBeanProperty)
/*    */       throws JsonMappingException
/*    */     {}
/*    */     
/*    */     public void optionalProperty(String paramString, JsonFormatVisitable paramJsonFormatVisitable, JavaType paramJavaType)
/*    */       throws JsonMappingException
/*    */     {}
/*    */     
/*    */     @Deprecated
/*    */     public void optionalProperty(String paramString)
/*    */       throws JsonMappingException
/*    */     {}
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsonFormatVisitors/JsonObjectFormatVisitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */