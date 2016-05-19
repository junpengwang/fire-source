/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContainerSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class StdArraySerializers
/*     */ {
/*  25 */   protected static final HashMap<String, JsonSerializer<?>> _arraySerializers = new HashMap();
/*     */   
/*     */   static
/*     */   {
/*  29 */     _arraySerializers.put(boolean[].class.getName(), new BooleanArraySerializer());
/*  30 */     _arraySerializers.put(byte[].class.getName(), new ByteArraySerializer());
/*  31 */     _arraySerializers.put(char[].class.getName(), new CharArraySerializer());
/*  32 */     _arraySerializers.put(short[].class.getName(), new ShortArraySerializer());
/*  33 */     _arraySerializers.put(int[].class.getName(), new IntArraySerializer());
/*  34 */     _arraySerializers.put(long[].class.getName(), new LongArraySerializer());
/*  35 */     _arraySerializers.put(float[].class.getName(), new FloatArraySerializer());
/*  36 */     _arraySerializers.put(double[].class.getName(), new DoubleArraySerializer());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JsonSerializer<?> findStandardImpl(Class<?> paramClass)
/*     */   {
/*  47 */     return (JsonSerializer)_arraySerializers.get(paramClass.getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static abstract class TypedPrimitiveArraySerializer<T>
/*     */     extends ArraySerializerBase<T>
/*     */   {
/*     */     protected final TypeSerializer _valueTypeSerializer;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected TypedPrimitiveArraySerializer(Class<T> paramClass)
/*     */     {
/*  69 */       super();
/*  70 */       this._valueTypeSerializer = null;
/*     */     }
/*     */     
/*     */     protected TypedPrimitiveArraySerializer(TypedPrimitiveArraySerializer<T> paramTypedPrimitiveArraySerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer)
/*     */     {
/*  75 */       super(paramBeanProperty);
/*  76 */       this._valueTypeSerializer = paramTypeSerializer;
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
/*     */   public static final class BooleanArraySerializer
/*     */     extends ArraySerializerBase<boolean[]>
/*     */   {
/*  91 */     private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Boolean.class);
/*     */     
/*  93 */     public BooleanArraySerializer() { super(null); }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*     */     {
/* 101 */       return this;
/*     */     }
/*     */     
/*     */     public JavaType getContentType()
/*     */     {
/* 106 */       return VALUE_TYPE;
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonSerializer<?> getContentSerializer()
/*     */     {
/* 112 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isEmpty(boolean[] paramArrayOfBoolean)
/*     */     {
/* 117 */       return (paramArrayOfBoolean == null) || (paramArrayOfBoolean.length == 0);
/*     */     }
/*     */     
/*     */     public boolean hasSingleElement(boolean[] paramArrayOfBoolean)
/*     */     {
/* 122 */       return paramArrayOfBoolean.length == 1;
/*     */     }
/*     */     
/*     */ 
/*     */     public void serializeContents(boolean[] paramArrayOfBoolean, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 129 */       int i = 0; for (int j = paramArrayOfBoolean.length; i < j; i++) {
/* 130 */         paramJsonGenerator.writeBoolean(paramArrayOfBoolean[i]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 137 */       ObjectNode localObjectNode = createSchemaNode("array", true);
/* 138 */       localObjectNode.put("items", createSchemaNode("boolean"));
/* 139 */       return localObjectNode;
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 146 */       if (paramJsonFormatVisitorWrapper != null) {
/* 147 */         JsonArrayFormatVisitor localJsonArrayFormatVisitor = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
/* 148 */         if (localJsonArrayFormatVisitor != null) {
/* 149 */           localJsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.BOOLEAN);
/*     */         }
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
/*     */   @JacksonStdImpl
/*     */   public static final class ByteArraySerializer
/*     */     extends StdSerializer<byte[]>
/*     */   {
/*     */     public ByteArraySerializer()
/*     */     {
/* 167 */       super();
/*     */     }
/*     */     
/*     */     public boolean isEmpty(byte[] paramArrayOfByte)
/*     */     {
/* 172 */       return (paramArrayOfByte == null) || (paramArrayOfByte.length == 0);
/*     */     }
/*     */     
/*     */ 
/*     */     public void serialize(byte[] paramArrayOfByte, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 179 */       paramJsonGenerator.writeBinary(paramSerializerProvider.getConfig().getBase64Variant(), paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void serializeWithType(byte[] paramArrayOfByte, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 188 */       paramTypeSerializer.writeTypePrefixForScalar(paramArrayOfByte, paramJsonGenerator);
/* 189 */       paramJsonGenerator.writeBinary(paramSerializerProvider.getConfig().getBase64Variant(), paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */       
/* 191 */       paramTypeSerializer.writeTypeSuffixForScalar(paramArrayOfByte, paramJsonGenerator);
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 197 */       ObjectNode localObjectNode1 = createSchemaNode("array", true);
/* 198 */       ObjectNode localObjectNode2 = createSchemaNode("string");
/* 199 */       localObjectNode1.put("items", localObjectNode2);
/* 200 */       return localObjectNode1;
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 207 */       if (paramJsonFormatVisitorWrapper != null) {
/* 208 */         JsonArrayFormatVisitor localJsonArrayFormatVisitor = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
/* 209 */         if (localJsonArrayFormatVisitor != null) {
/* 210 */           localJsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.STRING);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class ShortArraySerializer
/*     */     extends StdArraySerializers.TypedPrimitiveArraySerializer<short[]>
/*     */   {
/* 221 */     private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Short.TYPE);
/*     */     
/* 223 */     public ShortArraySerializer() { super(); }
/*     */     
/* 225 */     public ShortArraySerializer(ShortArraySerializer paramShortArraySerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer) { super(paramBeanProperty, paramTypeSerializer); }
/*     */     
/*     */ 
/*     */     public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*     */     {
/* 230 */       return new ShortArraySerializer(this, this._property, paramTypeSerializer);
/*     */     }
/*     */     
/*     */     public JavaType getContentType()
/*     */     {
/* 235 */       return VALUE_TYPE;
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonSerializer<?> getContentSerializer()
/*     */     {
/* 241 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isEmpty(short[] paramArrayOfShort)
/*     */     {
/* 246 */       return (paramArrayOfShort == null) || (paramArrayOfShort.length == 0);
/*     */     }
/*     */     
/*     */     public boolean hasSingleElement(short[] paramArrayOfShort)
/*     */     {
/* 251 */       return paramArrayOfShort.length == 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void serializeContents(short[] paramArrayOfShort, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 259 */       if (this._valueTypeSerializer != null) {
/* 260 */         i = 0; for (j = paramArrayOfShort.length; i < j; i++) {
/* 261 */           this._valueTypeSerializer.writeTypePrefixForScalar(null, paramJsonGenerator, Short.TYPE);
/* 262 */           paramJsonGenerator.writeNumber(paramArrayOfShort[i]);
/* 263 */           this._valueTypeSerializer.writeTypeSuffixForScalar(null, paramJsonGenerator);
/*     */         }
/* 265 */         return;
/*     */       }
/* 267 */       int i = 0; for (int j = paramArrayOfShort.length; i < j; i++) {
/* 268 */         paramJsonGenerator.writeNumber(paramArrayOfShort[i]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 276 */       ObjectNode localObjectNode = createSchemaNode("array", true);
/* 277 */       localObjectNode.put("items", createSchemaNode("integer"));
/* 278 */       return localObjectNode;
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 285 */       if (paramJsonFormatVisitorWrapper != null) {
/* 286 */         JsonArrayFormatVisitor localJsonArrayFormatVisitor = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
/* 287 */         if (localJsonArrayFormatVisitor != null) {
/* 288 */           localJsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.INTEGER);
/*     */         }
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
/*     */   public static final class CharArraySerializer
/*     */     extends StdSerializer<char[]>
/*     */   {
/*     */     public CharArraySerializer()
/*     */     {
/* 305 */       super();
/*     */     }
/*     */     
/*     */     public boolean isEmpty(char[] paramArrayOfChar) {
/* 309 */       return (paramArrayOfChar == null) || (paramArrayOfChar.length == 0);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void serialize(char[] paramArrayOfChar, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 317 */       if (paramSerializerProvider.isEnabled(com.shaded.fasterxml.jackson.databind.SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS)) {
/* 318 */         paramJsonGenerator.writeStartArray();
/* 319 */         _writeArrayContents(paramJsonGenerator, paramArrayOfChar);
/* 320 */         paramJsonGenerator.writeEndArray();
/*     */       } else {
/* 322 */         paramJsonGenerator.writeString(paramArrayOfChar, 0, paramArrayOfChar.length);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void serializeWithType(char[] paramArrayOfChar, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 332 */       if (paramSerializerProvider.isEnabled(com.shaded.fasterxml.jackson.databind.SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS)) {
/* 333 */         paramTypeSerializer.writeTypePrefixForArray(paramArrayOfChar, paramJsonGenerator);
/* 334 */         _writeArrayContents(paramJsonGenerator, paramArrayOfChar);
/* 335 */         paramTypeSerializer.writeTypeSuffixForArray(paramArrayOfChar, paramJsonGenerator);
/*     */       } else {
/* 337 */         paramTypeSerializer.writeTypePrefixForScalar(paramArrayOfChar, paramJsonGenerator);
/* 338 */         paramJsonGenerator.writeString(paramArrayOfChar, 0, paramArrayOfChar.length);
/* 339 */         paramTypeSerializer.writeTypeSuffixForScalar(paramArrayOfChar, paramJsonGenerator);
/*     */       }
/*     */     }
/*     */     
/*     */     private final void _writeArrayContents(JsonGenerator paramJsonGenerator, char[] paramArrayOfChar)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 346 */       int i = 0; for (int j = paramArrayOfChar.length; i < j; i++) {
/* 347 */         paramJsonGenerator.writeString(paramArrayOfChar, i, 1);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 354 */       ObjectNode localObjectNode1 = createSchemaNode("array", true);
/* 355 */       ObjectNode localObjectNode2 = createSchemaNode("string");
/* 356 */       localObjectNode2.put("type", "string");
/* 357 */       localObjectNode1.put("items", localObjectNode2);
/* 358 */       return localObjectNode1;
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 365 */       if (paramJsonFormatVisitorWrapper != null) {
/* 366 */         JsonArrayFormatVisitor localJsonArrayFormatVisitor = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
/* 367 */         if (localJsonArrayFormatVisitor != null) {
/* 368 */           localJsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.STRING);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class IntArraySerializer
/*     */     extends ArraySerializerBase<int[]>
/*     */   {
/* 379 */     private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Integer.TYPE);
/*     */     
/* 381 */     public IntArraySerializer() { super(null); }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*     */     {
/* 389 */       return this;
/*     */     }
/*     */     
/*     */     public JavaType getContentType()
/*     */     {
/* 394 */       return VALUE_TYPE;
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonSerializer<?> getContentSerializer()
/*     */     {
/* 400 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isEmpty(int[] paramArrayOfInt)
/*     */     {
/* 405 */       return (paramArrayOfInt == null) || (paramArrayOfInt.length == 0);
/*     */     }
/*     */     
/*     */     public boolean hasSingleElement(int[] paramArrayOfInt)
/*     */     {
/* 410 */       return paramArrayOfInt.length == 1;
/*     */     }
/*     */     
/*     */ 
/*     */     public void serializeContents(int[] paramArrayOfInt, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 417 */       int i = 0; for (int j = paramArrayOfInt.length; i < j; i++) {
/* 418 */         paramJsonGenerator.writeNumber(paramArrayOfInt[i]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 425 */       ObjectNode localObjectNode = createSchemaNode("array", true);
/* 426 */       localObjectNode.put("items", createSchemaNode("integer"));
/* 427 */       return localObjectNode;
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 434 */       if (paramJsonFormatVisitorWrapper != null) {
/* 435 */         JsonArrayFormatVisitor localJsonArrayFormatVisitor = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
/* 436 */         if (localJsonArrayFormatVisitor != null) {
/* 437 */           localJsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.INTEGER);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class LongArraySerializer
/*     */     extends StdArraySerializers.TypedPrimitiveArraySerializer<long[]>
/*     */   {
/* 448 */     private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Long.TYPE);
/*     */     
/* 450 */     public LongArraySerializer() { super(); }
/*     */     
/*     */     public LongArraySerializer(LongArraySerializer paramLongArraySerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer) {
/* 453 */       super(paramBeanProperty, paramTypeSerializer);
/*     */     }
/*     */     
/*     */     public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*     */     {
/* 458 */       return new LongArraySerializer(this, this._property, paramTypeSerializer);
/*     */     }
/*     */     
/*     */     public JavaType getContentType()
/*     */     {
/* 463 */       return VALUE_TYPE;
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonSerializer<?> getContentSerializer()
/*     */     {
/* 469 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isEmpty(long[] paramArrayOfLong)
/*     */     {
/* 474 */       return (paramArrayOfLong == null) || (paramArrayOfLong.length == 0);
/*     */     }
/*     */     
/*     */     public boolean hasSingleElement(long[] paramArrayOfLong)
/*     */     {
/* 479 */       return paramArrayOfLong.length == 1;
/*     */     }
/*     */     
/*     */ 
/*     */     public void serializeContents(long[] paramArrayOfLong, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 486 */       if (this._valueTypeSerializer != null) {
/* 487 */         i = 0; for (j = paramArrayOfLong.length; i < j; i++) {
/* 488 */           this._valueTypeSerializer.writeTypePrefixForScalar(null, paramJsonGenerator, Long.TYPE);
/* 489 */           paramJsonGenerator.writeNumber(paramArrayOfLong[i]);
/* 490 */           this._valueTypeSerializer.writeTypeSuffixForScalar(null, paramJsonGenerator);
/*     */         }
/* 492 */         return;
/*     */       }
/*     */       
/* 495 */       int i = 0; for (int j = paramArrayOfLong.length; i < j; i++) {
/* 496 */         paramJsonGenerator.writeNumber(paramArrayOfLong[i]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 503 */       ObjectNode localObjectNode = createSchemaNode("array", true);
/* 504 */       localObjectNode.put("items", createSchemaNode("number", true));
/* 505 */       return localObjectNode;
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 512 */       if (paramJsonFormatVisitorWrapper != null) {
/* 513 */         JsonArrayFormatVisitor localJsonArrayFormatVisitor = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
/* 514 */         if (localJsonArrayFormatVisitor != null) {
/* 515 */           localJsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.NUMBER);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class FloatArraySerializer
/*     */     extends StdArraySerializers.TypedPrimitiveArraySerializer<float[]>
/*     */   {
/* 526 */     private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Float.TYPE);
/*     */     
/*     */     public FloatArraySerializer() {
/* 529 */       super();
/*     */     }
/*     */     
/*     */     public FloatArraySerializer(FloatArraySerializer paramFloatArraySerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer) {
/* 533 */       super(paramBeanProperty, paramTypeSerializer);
/*     */     }
/*     */     
/*     */     public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*     */     {
/* 538 */       return new FloatArraySerializer(this, this._property, paramTypeSerializer);
/*     */     }
/*     */     
/*     */     public JavaType getContentType()
/*     */     {
/* 543 */       return VALUE_TYPE;
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonSerializer<?> getContentSerializer()
/*     */     {
/* 549 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isEmpty(float[] paramArrayOfFloat)
/*     */     {
/* 554 */       return (paramArrayOfFloat == null) || (paramArrayOfFloat.length == 0);
/*     */     }
/*     */     
/*     */     public boolean hasSingleElement(float[] paramArrayOfFloat)
/*     */     {
/* 559 */       return paramArrayOfFloat.length == 1;
/*     */     }
/*     */     
/*     */ 
/*     */     public void serializeContents(float[] paramArrayOfFloat, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 566 */       if (this._valueTypeSerializer != null) {
/* 567 */         i = 0; for (j = paramArrayOfFloat.length; i < j; i++) {
/* 568 */           this._valueTypeSerializer.writeTypePrefixForScalar(null, paramJsonGenerator, Float.TYPE);
/* 569 */           paramJsonGenerator.writeNumber(paramArrayOfFloat[i]);
/* 570 */           this._valueTypeSerializer.writeTypeSuffixForScalar(null, paramJsonGenerator);
/*     */         }
/* 572 */         return;
/*     */       }
/* 574 */       int i = 0; for (int j = paramArrayOfFloat.length; i < j; i++) {
/* 575 */         paramJsonGenerator.writeNumber(paramArrayOfFloat[i]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 582 */       ObjectNode localObjectNode = createSchemaNode("array", true);
/* 583 */       localObjectNode.put("items", createSchemaNode("number"));
/* 584 */       return localObjectNode;
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 591 */       if (paramJsonFormatVisitorWrapper != null) {
/* 592 */         JsonArrayFormatVisitor localJsonArrayFormatVisitor = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
/* 593 */         if (localJsonArrayFormatVisitor != null) {
/* 594 */           localJsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.NUMBER);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @JacksonStdImpl
/*     */   public static final class DoubleArraySerializer
/*     */     extends ArraySerializerBase<double[]>
/*     */   {
/* 605 */     private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Double.TYPE);
/*     */     
/* 607 */     public DoubleArraySerializer() { super(null); }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
/*     */     {
/* 615 */       return this;
/*     */     }
/*     */     
/*     */     public JavaType getContentType()
/*     */     {
/* 620 */       return VALUE_TYPE;
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonSerializer<?> getContentSerializer()
/*     */     {
/* 626 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isEmpty(double[] paramArrayOfDouble)
/*     */     {
/* 631 */       return (paramArrayOfDouble == null) || (paramArrayOfDouble.length == 0);
/*     */     }
/*     */     
/*     */     public boolean hasSingleElement(double[] paramArrayOfDouble)
/*     */     {
/* 636 */       return paramArrayOfDouble.length == 1;
/*     */     }
/*     */     
/*     */ 
/*     */     public void serializeContents(double[] paramArrayOfDouble, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws IOException, JsonGenerationException
/*     */     {
/* 643 */       int i = 0; for (int j = paramArrayOfDouble.length; i < j; i++) {
/* 644 */         paramJsonGenerator.writeNumber(paramArrayOfDouble[i]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */     {
/* 651 */       ObjectNode localObjectNode = createSchemaNode("array", true);
/* 652 */       localObjectNode.put("items", createSchemaNode("number"));
/* 653 */       return localObjectNode;
/*     */     }
/*     */     
/*     */ 
/*     */     public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */       throws JsonMappingException
/*     */     {
/* 660 */       if (paramJsonFormatVisitorWrapper != null) {
/* 661 */         JsonArrayFormatVisitor localJsonArrayFormatVisitor = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
/* 662 */         if (localJsonArrayFormatVisitor != null) {
/* 663 */           localJsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.NUMBER);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/StdArraySerializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */