/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class NumberSerializers
/*     */ {
/*     */   public static void addAll(Map<String, JsonSerializer<?>> paramMap)
/*     */   {
/*  26 */     IntegerSerializer localIntegerSerializer = new IntegerSerializer();
/*  27 */     paramMap.put(Integer.class.getName(), localIntegerSerializer);
/*  28 */     paramMap.put(Integer.TYPE.getName(), localIntegerSerializer);
/*  29 */     paramMap.put(Long.class.getName(), LongSerializer.instance);
/*  30 */     paramMap.put(Long.TYPE.getName(), LongSerializer.instance);
/*  31 */     paramMap.put(Byte.class.getName(), IntLikeSerializer.instance);
/*  32 */     paramMap.put(Byte.TYPE.getName(), IntLikeSerializer.instance);
/*  33 */     paramMap.put(Short.class.getName(), ShortSerializer.instance);
/*  34 */     paramMap.put(Short.TYPE.getName(), ShortSerializer.instance);
/*     */     
/*     */ 
/*  37 */     paramMap.put(Float.class.getName(), FloatSerializer.instance);
/*  38 */     paramMap.put(Float.TYPE.getName(), FloatSerializer.instance);
/*  39 */     paramMap.put(Double.class.getName(), DoubleSerializer.instance);
/*  40 */     paramMap.put(Double.TYPE.getName(), DoubleSerializer.instance);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class ShortSerializer
/*     */     extends StdScalarSerializer<Short>
/*     */   {
/*  53 */     static final ShortSerializer instance = new ShortSerializer();
/*     */     
/*  55 */     public ShortSerializer() { super(); }
/*     */     
/*     */ 
/*     */     public void serialize(Short paramShort, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/*  61 */       paramJsonGenerator.writeNumber(paramShort.shortValue());
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/*  67 */       return createSchemaNode("number", true);
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/*  74 */       JsonIntegerFormatVisitor localJsonIntegerFormatVisitor = paramJsonFormatVisitorWrapper.expectIntegerFormat(paramJavaType);
/*  75 */       if (localJsonIntegerFormatVisitor != null) {
/*  76 */         localJsonIntegerFormatVisitor.numberType(JsonParser.NumberType.INT);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class IntegerSerializer
/*     */     extends NonTypedScalarSerializerBase<Integer>
/*     */   {
/*     */     public IntegerSerializer()
/*     */     {
/*  92 */       super();
/*     */     }
/*     */     
/*     */     public void serialize(Integer paramInteger, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/*  98 */       paramJsonGenerator.writeNumber(paramInteger.intValue());
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 104 */       return createSchemaNode("integer", true);
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 111 */       JsonIntegerFormatVisitor localJsonIntegerFormatVisitor = paramJsonFormatVisitorWrapper.expectIntegerFormat(paramJavaType);
/* 112 */       if (localJsonIntegerFormatVisitor != null) {
/* 113 */         localJsonIntegerFormatVisitor.numberType(JsonParser.NumberType.INT);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class IntLikeSerializer
/*     */     extends StdScalarSerializer<Number>
/*     */   {
/* 127 */     static final IntLikeSerializer instance = new IntLikeSerializer();
/*     */     
/* 129 */     public IntLikeSerializer() { super(); }
/*     */     
/*     */ 
/*     */     public void serialize(Number paramNumber, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 135 */       paramJsonGenerator.writeNumber(paramNumber.intValue());
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 141 */       return createSchemaNode("integer", true);
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 148 */       JsonIntegerFormatVisitor localJsonIntegerFormatVisitor = paramJsonFormatVisitorWrapper.expectIntegerFormat(paramJavaType);
/* 149 */       if (localJsonIntegerFormatVisitor != null) {
/* 150 */         localJsonIntegerFormatVisitor.numberType(JsonParser.NumberType.INT);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static final class LongSerializer
/*     */     extends StdScalarSerializer<Long>
/*     */   {
/* 159 */     static final LongSerializer instance = new LongSerializer();
/*     */     
/* 161 */     public LongSerializer() { super(); }
/*     */     
/*     */ 
/*     */     public void serialize(Long paramLong, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 167 */       paramJsonGenerator.writeNumber(paramLong.longValue());
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 173 */       return createSchemaNode("number", true);
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 180 */       JsonIntegerFormatVisitor localJsonIntegerFormatVisitor = paramJsonFormatVisitorWrapper.expectIntegerFormat(paramJavaType);
/* 181 */       if (localJsonIntegerFormatVisitor != null) {
/* 182 */         localJsonIntegerFormatVisitor.numberType(JsonParser.NumberType.LONG);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @JacksonStdImpl
/*     */   public static final class FloatSerializer
/*     */     extends StdScalarSerializer<Float>
/*     */   {
/* 191 */     static final FloatSerializer instance = new FloatSerializer();
/*     */     
/* 193 */     public FloatSerializer() { super(); }
/*     */     
/*     */ 
/*     */     public void serialize(Float paramFloat, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 199 */       paramJsonGenerator.writeNumber(paramFloat.floatValue());
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 205 */       return createSchemaNode("number", true);
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 212 */       JsonNumberFormatVisitor localJsonNumberFormatVisitor = paramJsonFormatVisitorWrapper.expectNumberFormat(paramJavaType);
/* 213 */       if (localJsonNumberFormatVisitor != null) {
/* 214 */         localJsonNumberFormatVisitor.numberType(JsonParser.NumberType.FLOAT);
/*     */       }
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
/*     */   @JacksonStdImpl
/*     */   public static final class DoubleSerializer
/*     */     extends NonTypedScalarSerializerBase<Double>
/*     */   {
/* 230 */     static final DoubleSerializer instance = new DoubleSerializer();
/*     */     
/* 232 */     public DoubleSerializer() { super(); }
/*     */     
/*     */ 
/*     */     public void serialize(Double paramDouble, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 238 */       paramJsonGenerator.writeNumber(paramDouble.doubleValue());
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 244 */       return createSchemaNode("number", true);
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 251 */       JsonNumberFormatVisitor localJsonNumberFormatVisitor = paramJsonFormatVisitorWrapper.expectNumberFormat(paramJavaType);
/* 252 */       if (localJsonNumberFormatVisitor != null) {
/* 253 */         localJsonNumberFormatVisitor.numberType(JsonParser.NumberType.DOUBLE);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class NumberSerializer
/*     */     extends StdScalarSerializer<Number>
/*     */   {
/* 266 */     public static final NumberSerializer instance = new NumberSerializer();
/*     */     
/* 268 */     public NumberSerializer() { super(); }
/*     */     
/*     */ 
/*     */ 
/*     */     public void serialize(Number paramNumber, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 275 */       if ((paramNumber instanceof BigDecimal)) {
/* 276 */         if (paramSerializerProvider.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN))
/*     */         {
/* 278 */           if (!(paramJsonGenerator instanceof com.shaded.fasterxml.jackson.databind.util.TokenBuffer)) {
/* 279 */             paramJsonGenerator.writeNumber(((BigDecimal)paramNumber).toPlainString());
/* 280 */             return;
/*     */           }
/*     */         }
/* 283 */         paramJsonGenerator.writeNumber((BigDecimal)paramNumber);
/* 284 */       } else if ((paramNumber instanceof BigInteger)) {
/* 285 */         paramJsonGenerator.writeNumber((BigInteger)paramNumber);
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 290 */       else if ((paramNumber instanceof Integer)) {
/* 291 */         paramJsonGenerator.writeNumber(paramNumber.intValue());
/* 292 */       } else if ((paramNumber instanceof Long)) {
/* 293 */         paramJsonGenerator.writeNumber(paramNumber.longValue());
/* 294 */       } else if ((paramNumber instanceof Double)) {
/* 295 */         paramJsonGenerator.writeNumber(paramNumber.doubleValue());
/* 296 */       } else if ((paramNumber instanceof Float)) {
/* 297 */         paramJsonGenerator.writeNumber(paramNumber.floatValue());
/* 298 */       } else if (((paramNumber instanceof Byte)) || ((paramNumber instanceof Short))) {
/* 299 */         paramJsonGenerator.writeNumber(paramNumber.intValue());
/*     */       }
/*     */       else {
/* 302 */         paramJsonGenerator.writeNumber(paramNumber.toString());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 309 */       return createSchemaNode("number", true);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 318 */       JsonNumberFormatVisitor localJsonNumberFormatVisitor = paramJsonFormatVisitorWrapper.expectNumberFormat(paramJavaType);
/* 319 */       if (localJsonNumberFormatVisitor != null) {
/* 320 */         localJsonNumberFormatVisitor.numberType(JsonParser.NumberType.BIG_DECIMAL);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/NumberSerializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */