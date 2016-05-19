/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
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
/*     */ public abstract class ValueInstantiator
/*     */ {
/*     */   public abstract String getValueTypeDesc();
/*     */   
/*     */   public boolean canInstantiate()
/*     */   {
/*  52 */     return (canCreateUsingDefault()) || (canCreateUsingDelegate()) || (canCreateFromObjectWith()) || (canCreateFromString()) || (canCreateFromInt()) || (canCreateFromLong()) || (canCreateFromDouble()) || (canCreateFromBoolean());
/*     */   }
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
/*     */   public boolean canCreateFromString()
/*     */   {
/*  69 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canCreateFromInt()
/*     */   {
/*  77 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canCreateFromLong()
/*     */   {
/*  85 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canCreateFromDouble()
/*     */   {
/*  93 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canCreateFromBoolean()
/*     */   {
/* 101 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canCreateUsingDefault()
/*     */   {
/* 110 */     return getDefaultCreator() != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canCreateUsingDelegate()
/*     */   {
/* 119 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canCreateFromObjectWith()
/*     */   {
/* 128 */     return false;
/*     */   }
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
/*     */   public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig paramDeserializationConfig)
/*     */   {
/* 142 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType getDelegateType(DeserializationConfig paramDeserializationConfig)
/*     */   {
/* 153 */     return null;
/*     */   }
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
/*     */   public Object createUsingDefault(DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 174 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + "; no default creator found");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object createFromObjectWith(DeserializationContext paramDeserializationContext, Object[] paramArrayOfObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 188 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " with arguments");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object createUsingDelegate(DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 199 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " using delegate");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object createFromString(DeserializationContext paramDeserializationContext, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 212 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from String value");
/*     */   }
/*     */   
/*     */   public Object createFromInt(DeserializationContext paramDeserializationContext, int paramInt)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 218 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Integer number (int)");
/*     */   }
/*     */   
/*     */   public Object createFromLong(DeserializationContext paramDeserializationContext, long paramLong)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 224 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Integer number (long)");
/*     */   }
/*     */   
/*     */   public Object createFromDouble(DeserializationContext paramDeserializationContext, double paramDouble)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 230 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Floating-point number (double)");
/*     */   }
/*     */   
/*     */   public Object createFromBoolean(DeserializationContext paramDeserializationContext, boolean paramBoolean)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 236 */     throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Boolean value");
/*     */   }
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
/*     */   public AnnotatedWithParams getDefaultCreator()
/*     */   {
/* 257 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedWithParams getDelegateCreator()
/*     */   {
/* 269 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedWithParams getWithArgsCreator()
/*     */   {
/* 282 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedParameter getIncompleteParameter()
/*     */   {
/* 290 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/ValueInstantiator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */