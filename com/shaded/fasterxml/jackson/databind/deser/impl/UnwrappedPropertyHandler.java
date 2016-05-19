/*    */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*    */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*    */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*    */ import com.shaded.fasterxml.jackson.databind.util.TokenBuffer;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnwrappedPropertyHandler
/*    */ {
/*    */   protected final List<SettableBeanProperty> _properties;
/*    */   
/*    */   public UnwrappedPropertyHandler()
/*    */   {
/* 23 */     this._properties = new ArrayList();
/*    */   }
/*    */   
/* 26 */   protected UnwrappedPropertyHandler(List<SettableBeanProperty> paramList) { this._properties = paramList; }
/*    */   
/*    */   public void addProperty(SettableBeanProperty paramSettableBeanProperty)
/*    */   {
/* 30 */     this._properties.add(paramSettableBeanProperty);
/*    */   }
/*    */   
/*    */   public UnwrappedPropertyHandler renameAll(NameTransformer paramNameTransformer)
/*    */   {
/* 35 */     ArrayList localArrayList = new ArrayList(this._properties.size());
/* 36 */     for (SettableBeanProperty localSettableBeanProperty : this._properties) {
/* 37 */       String str = paramNameTransformer.transform(localSettableBeanProperty.getName());
/* 38 */       localSettableBeanProperty = localSettableBeanProperty.withName(str);
/* 39 */       JsonDeserializer localJsonDeserializer1 = localSettableBeanProperty.getValueDeserializer();
/* 40 */       if (localJsonDeserializer1 != null)
/*    */       {
/* 42 */         JsonDeserializer localJsonDeserializer2 = localJsonDeserializer1.unwrappingDeserializer(paramNameTransformer);
/*    */         
/* 44 */         if (localJsonDeserializer2 != localJsonDeserializer1) {
/* 45 */           localSettableBeanProperty = localSettableBeanProperty.withValueDeserializer(localJsonDeserializer2);
/*    */         }
/*    */       }
/* 48 */       localArrayList.add(localSettableBeanProperty);
/*    */     }
/* 50 */     return new UnwrappedPropertyHandler(localArrayList);
/*    */   }
/*    */   
/*    */ 
/*    */   public Object processUnwrapped(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, TokenBuffer paramTokenBuffer)
/*    */     throws IOException, JsonProcessingException
/*    */   {
/* 57 */     int i = 0; for (int j = this._properties.size(); i < j; i++) {
/* 58 */       SettableBeanProperty localSettableBeanProperty = (SettableBeanProperty)this._properties.get(i);
/* 59 */       JsonParser localJsonParser = paramTokenBuffer.asParser();
/* 60 */       localJsonParser.nextToken();
/* 61 */       localSettableBeanProperty.deserializeAndSet(localJsonParser, paramDeserializationContext, paramObject);
/*    */     }
/* 63 */     return paramObject;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/UnwrappedPropertyHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */