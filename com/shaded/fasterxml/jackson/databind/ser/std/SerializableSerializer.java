/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializable;
/*     */ import com.shaded.fasterxml.jackson.databind.ObjectMapper;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class SerializableSerializer
/*     */   extends StdSerializer<JsonSerializable>
/*     */ {
/*  33 */   public static final SerializableSerializer instance = new SerializableSerializer();
/*     */   
/*     */ 
/*  36 */   private static final AtomicReference<ObjectMapper> _mapperReference = new AtomicReference();
/*     */   
/*  38 */   protected SerializableSerializer() { super(JsonSerializable.class); }
/*     */   
/*     */ 
/*     */   public void serialize(JsonSerializable paramJsonSerializable, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*  44 */     paramJsonSerializable.serialize(paramJsonGenerator, paramSerializerProvider);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final void serializeWithType(JsonSerializable paramJsonSerializable, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/*  52 */     paramJsonSerializable.serializeWithType(paramJsonGenerator, paramSerializerProvider, paramTypeSerializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     throws JsonMappingException
/*     */   {
/*  60 */     ObjectNode localObjectNode = createObjectNode();
/*  61 */     String str1 = "any";
/*  62 */     String str2 = null;
/*  63 */     String str3 = null;
/*  64 */     if (paramType != null) {
/*  65 */       Class localClass = TypeFactory.rawClass(paramType);
/*  66 */       if (localClass.isAnnotationPresent(JsonSerializableSchema.class)) {
/*  67 */         JsonSerializableSchema localJsonSerializableSchema = (JsonSerializableSchema)localClass.getAnnotation(JsonSerializableSchema.class);
/*  68 */         str1 = localJsonSerializableSchema.schemaType();
/*  69 */         if (!"##irrelevant".equals(localJsonSerializableSchema.schemaObjectPropertiesDefinition())) {
/*  70 */           str2 = localJsonSerializableSchema.schemaObjectPropertiesDefinition();
/*     */         }
/*  72 */         if (!"##irrelevant".equals(localJsonSerializableSchema.schemaItemDefinition())) {
/*  73 */           str3 = localJsonSerializableSchema.schemaItemDefinition();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  80 */     localObjectNode.put("type", str1);
/*  81 */     if (str2 != null) {
/*     */       try {
/*  83 */         localObjectNode.put("properties", _getObjectMapper().readTree(str2));
/*     */       } catch (IOException localIOException1) {
/*  85 */         throw new JsonMappingException("Failed to parse @JsonSerializableSchema.schemaObjectPropertiesDefinition value");
/*     */       }
/*     */     }
/*  88 */     if (str3 != null) {
/*     */       try {
/*  90 */         localObjectNode.put("items", _getObjectMapper().readTree(str3));
/*     */       } catch (IOException localIOException2) {
/*  92 */         throw new JsonMappingException("Failed to parse @JsonSerializableSchema.schemaItemDefinition value");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  97 */     return localObjectNode;
/*     */   }
/*     */   
/*     */   private static final synchronized ObjectMapper _getObjectMapper()
/*     */   {
/* 102 */     ObjectMapper localObjectMapper = (ObjectMapper)_mapperReference.get();
/* 103 */     if (localObjectMapper == null) {
/* 104 */       localObjectMapper = new ObjectMapper();
/* 105 */       _mapperReference.set(localObjectMapper);
/*     */     }
/* 107 */     return localObjectMapper;
/*     */   }
/*     */   
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/* 114 */     paramJsonFormatVisitorWrapper.expectAnyFormat(paramJavaType);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/SerializableSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */