/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ 
/*     */ public class StdJdkSerializers
/*     */ {
/*     */   public static Collection<java.util.Map.Entry<Class<?>, Object>> all()
/*     */   {
/*  28 */     HashMap localHashMap = new HashMap();
/*     */     
/*     */ 
/*  31 */     ToStringSerializer localToStringSerializer = ToStringSerializer.instance;
/*     */     
/*  33 */     localHashMap.put(java.net.URL.class, localToStringSerializer);
/*  34 */     localHashMap.put(java.net.URI.class, localToStringSerializer);
/*     */     
/*  36 */     localHashMap.put(java.util.Currency.class, localToStringSerializer);
/*  37 */     localHashMap.put(UUID.class, localToStringSerializer);
/*  38 */     localHashMap.put(java.util.regex.Pattern.class, localToStringSerializer);
/*  39 */     localHashMap.put(Locale.class, localToStringSerializer);
/*     */     
/*     */ 
/*  42 */     localHashMap.put(Locale.class, localToStringSerializer);
/*     */     
/*     */ 
/*  45 */     localHashMap.put(AtomicReference.class, AtomicReferenceSerializer.class);
/*  46 */     localHashMap.put(AtomicBoolean.class, AtomicBooleanSerializer.class);
/*  47 */     localHashMap.put(AtomicInteger.class, AtomicIntegerSerializer.class);
/*  48 */     localHashMap.put(AtomicLong.class, AtomicLongSerializer.class);
/*     */     
/*     */ 
/*  51 */     localHashMap.put(File.class, FileSerializer.class);
/*  52 */     localHashMap.put(Class.class, ClassSerializer.class);
/*     */     
/*     */ 
/*  55 */     localHashMap.put(Void.TYPE, NullSerializer.class);
/*     */     
/*  57 */     return localHashMap.entrySet();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final class AtomicBooleanSerializer
/*     */     extends StdScalarSerializer<AtomicBoolean>
/*     */   {
/*     */     public AtomicBooleanSerializer()
/*     */     {
/*  69 */       super(false);
/*     */     }
/*     */     
/*     */     public void serialize(AtomicBoolean paramAtomicBoolean, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/*  75 */       paramJsonGenerator.writeBoolean(paramAtomicBoolean.get());
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/*  81 */       return createSchemaNode("boolean", true);
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/*  88 */       paramJsonFormatVisitorWrapper.expectBooleanFormat(paramJavaType);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class AtomicIntegerSerializer extends StdScalarSerializer<AtomicInteger>
/*     */   {
/*     */     public AtomicIntegerSerializer() {
/*  95 */       super(false);
/*     */     }
/*     */     
/*     */     public void serialize(AtomicInteger paramAtomicInteger, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 101 */       paramJsonGenerator.writeNumber(paramAtomicInteger.get());
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 107 */       return createSchemaNode("integer", true);
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 114 */       JsonIntegerFormatVisitor localJsonIntegerFormatVisitor = paramJsonFormatVisitorWrapper.expectIntegerFormat(paramJavaType);
/* 115 */       if (localJsonIntegerFormatVisitor != null) {
/* 116 */         localJsonIntegerFormatVisitor.numberType(JsonParser.NumberType.INT);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class AtomicLongSerializer extends StdScalarSerializer<AtomicLong>
/*     */   {
/*     */     public AtomicLongSerializer() {
/* 124 */       super(false);
/*     */     }
/*     */     
/*     */     public void serialize(AtomicLong paramAtomicLong, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 130 */       paramJsonGenerator.writeNumber(paramAtomicLong.get());
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 136 */       return createSchemaNode("integer", true);
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 143 */       JsonIntegerFormatVisitor localJsonIntegerFormatVisitor = paramJsonFormatVisitorWrapper.expectIntegerFormat(paramJavaType);
/* 144 */       if (localJsonIntegerFormatVisitor != null) {
/* 145 */         localJsonIntegerFormatVisitor.numberType(JsonParser.NumberType.LONG);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class AtomicReferenceSerializer extends StdSerializer<AtomicReference<?>>
/*     */   {
/*     */     public AtomicReferenceSerializer() {
/* 153 */       super(false);
/*     */     }
/*     */     
/*     */     public void serialize(AtomicReference<?> paramAtomicReference, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 159 */       paramSerializerProvider.defaultSerializeValue(paramAtomicReference.get(), paramJsonGenerator);
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 165 */       return createSchemaNode("any", true);
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 172 */       paramJsonFormatVisitorWrapper.expectAnyFormat(paramJavaType);
/*     */     }
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
/*     */   public static final class FileSerializer
/*     */     extends StdScalarSerializer<File>
/*     */   {
/*     */     public FileSerializer()
/*     */     {
/* 189 */       super();
/*     */     }
/*     */     
/*     */     public void serialize(File paramFile, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 195 */       paramJsonGenerator.writeString(paramFile.getAbsolutePath());
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 201 */       return createSchemaNode("string", true);
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 208 */       paramJsonFormatVisitorWrapper.expectStringFormat(paramJavaType);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static final class ClassSerializer
/*     */     extends StdScalarSerializer<Class<?>>
/*     */   {
/*     */     public ClassSerializer()
/*     */     {
/* 219 */       super(false);
/*     */     }
/*     */     
/*     */     public void serialize(Class<?> paramClass, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 225 */       paramJsonGenerator.writeString(paramClass.getName());
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 231 */       return createSchemaNode("string", true);
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 238 */       paramJsonFormatVisitorWrapper.expectStringFormat(paramJavaType);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/StdJdkSerializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */