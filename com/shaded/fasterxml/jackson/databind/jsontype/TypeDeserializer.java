/*     */ package com.shaded.fasterxml.jackson.databind.jsontype;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TypeDeserializer
/*     */ {
/*     */   public abstract TypeDeserializer forProperty(BeanProperty paramBeanProperty);
/*     */   
/*     */   public abstract JsonTypeInfo.As getTypeInclusion();
/*     */   
/*     */   public abstract String getPropertyName();
/*     */   
/*     */   public abstract TypeIdResolver getTypeIdResolver();
/*     */   
/*     */   public abstract Class<?> getDefaultImpl();
/*     */   
/*     */   public abstract Object deserializeTypedFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract Object deserializeTypedFromArray(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract Object deserializeTypedFromScalar(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public abstract Object deserializeTypedFromAny(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException;
/*     */   
/*     */   public static Object deserializeIfNatural(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JavaType paramJavaType)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 150 */     return deserializeIfNatural(paramJsonParser, paramDeserializationContext, paramJavaType.getRawClass());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static Object deserializeIfNatural(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Class<?> paramClass)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 158 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 159 */     if (localJsonToken == null) {
/* 160 */       return null;
/*     */     }
/* 162 */     switch (localJsonToken) {
/*     */     case VALUE_STRING: 
/* 164 */       if (paramClass.isAssignableFrom(String.class)) {
/* 165 */         return paramJsonParser.getText();
/*     */       }
/*     */       break;
/*     */     case VALUE_NUMBER_INT: 
/* 169 */       if (paramClass.isAssignableFrom(Integer.class)) {
/* 170 */         return Integer.valueOf(paramJsonParser.getIntValue());
/*     */       }
/*     */       
/*     */       break;
/*     */     case VALUE_NUMBER_FLOAT: 
/* 175 */       if (paramClass.isAssignableFrom(Double.class)) {
/* 176 */         return Double.valueOf(paramJsonParser.getDoubleValue());
/*     */       }
/*     */       break;
/*     */     case VALUE_TRUE: 
/* 180 */       if (paramClass.isAssignableFrom(Boolean.class)) {
/* 181 */         return Boolean.TRUE;
/*     */       }
/*     */       break;
/*     */     case VALUE_FALSE: 
/* 185 */       if (paramClass.isAssignableFrom(Boolean.class)) {
/* 186 */         return Boolean.FALSE;
/*     */       }
/*     */       break;
/*     */     }
/* 190 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/jsontype/TypeDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */