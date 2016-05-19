/*     */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.BeanPropertyFilter;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public abstract class SimpleBeanPropertyFilter
/*     */   implements BeanPropertyFilter
/*     */ {
/*     */   public static SimpleBeanPropertyFilter filterOutAllExcept(Set<String> paramSet)
/*     */   {
/*  33 */     return new FilterExceptFilter(paramSet);
/*     */   }
/*     */   
/*     */   public static SimpleBeanPropertyFilter filterOutAllExcept(String... paramVarArgs) {
/*  37 */     HashSet localHashSet = new HashSet(paramVarArgs.length);
/*  38 */     Collections.addAll(localHashSet, paramVarArgs);
/*  39 */     return new FilterExceptFilter(localHashSet);
/*     */   }
/*     */   
/*     */   public static SimpleBeanPropertyFilter serializeAllExcept(Set<String> paramSet) {
/*  43 */     return new SerializeExceptFilter(paramSet);
/*     */   }
/*     */   
/*     */   public static SimpleBeanPropertyFilter serializeAllExcept(String... paramVarArgs) {
/*  47 */     HashSet localHashSet = new HashSet(paramVarArgs.length);
/*  48 */     Collections.addAll(localHashSet, paramVarArgs);
/*  49 */     return new SerializeExceptFilter(localHashSet);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract boolean include(BeanPropertyWriter paramBeanPropertyWriter);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serializeAsField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, BeanPropertyWriter paramBeanPropertyWriter)
/*     */     throws Exception
/*     */   {
/*  68 */     if (include(paramBeanPropertyWriter)) {
/*  69 */       paramBeanPropertyWriter.serializeAsField(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void depositSchemaProperty(BeanPropertyWriter paramBeanPropertyWriter, ObjectNode paramObjectNode, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/*  78 */     if (include(paramBeanPropertyWriter)) {
/*  79 */       paramBeanPropertyWriter.depositSchemaProperty(paramObjectNode, paramSerializerProvider);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void depositSchemaProperty(BeanPropertyWriter paramBeanPropertyWriter, JsonObjectFormatVisitor paramJsonObjectFormatVisitor, SerializerProvider paramSerializerProvider)
/*     */     throws JsonMappingException
/*     */   {
/*  88 */     if (include(paramBeanPropertyWriter)) {
/*  89 */       paramBeanPropertyWriter.depositSchemaProperty(paramJsonObjectFormatVisitor);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class FilterExceptFilter
/*     */     extends SimpleBeanPropertyFilter
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected final Set<String> _propertiesToInclude;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public FilterExceptFilter(Set<String> paramSet)
/*     */     {
/* 115 */       this._propertiesToInclude = paramSet;
/*     */     }
/*     */     
/*     */     protected boolean include(BeanPropertyWriter paramBeanPropertyWriter)
/*     */     {
/* 120 */       return this._propertiesToInclude.contains(paramBeanPropertyWriter.getName());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class SerializeExceptFilter
/*     */     extends SimpleBeanPropertyFilter
/*     */   {
/*     */     protected final Set<String> _propertiesToExclude;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public SerializeExceptFilter(Set<String> paramSet)
/*     */     {
/* 137 */       this._propertiesToExclude = paramSet;
/*     */     }
/*     */     
/*     */     protected boolean include(BeanPropertyWriter paramBeanPropertyWriter)
/*     */     {
/* 142 */       return !this._propertiesToExclude.contains(paramBeanPropertyWriter.getName());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/SimpleBeanPropertyFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */