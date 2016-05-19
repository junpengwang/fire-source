/*     */ package com.shaded.fasterxml.jackson.databind.ser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Shape;
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Value;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*     */ import com.shaded.fasterxml.jackson.core.io.SerializedString;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JacksonStdImpl;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ArrayNode;
/*     */ import com.shaded.fasterxml.jackson.databind.node.ObjectNode;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.ContextualSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.EnumValues;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.LinkedHashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @JacksonStdImpl
/*     */ public class EnumSerializer
/*     */   extends StdScalarSerializer<Enum<?>>
/*     */   implements ContextualSerializer
/*     */ {
/*     */   protected final EnumValues _values;
/*     */   protected final Boolean _serializeAsIndex;
/*     */   
/*     */   @Deprecated
/*     */   public EnumSerializer(EnumValues paramEnumValues)
/*     */   {
/*  63 */     this(paramEnumValues, null);
/*     */   }
/*     */   
/*     */   public EnumSerializer(EnumValues paramEnumValues, Boolean paramBoolean)
/*     */   {
/*  68 */     super(Enum.class, false);
/*  69 */     this._values = paramEnumValues;
/*  70 */     this._serializeAsIndex = paramBoolean;
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
/*     */   public static EnumSerializer construct(Class<Enum<?>> paramClass, SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription, JsonFormat.Value paramValue)
/*     */   {
/*  83 */     AnnotationIntrospector localAnnotationIntrospector = paramSerializationConfig.getAnnotationIntrospector();
/*  84 */     EnumValues localEnumValues = paramSerializationConfig.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING) ? EnumValues.constructFromToString(paramClass, localAnnotationIntrospector) : EnumValues.constructFromName(paramClass, localAnnotationIntrospector);
/*     */     
/*  86 */     Boolean localBoolean = _isShapeWrittenUsingIndex(paramClass, paramValue, true);
/*  87 */     return new EnumSerializer(localEnumValues, localBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static EnumSerializer construct(Class<Enum<?>> paramClass, SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription)
/*     */   {
/*  97 */     return construct(paramClass, paramSerializationConfig, paramBeanDescription, paramBeanDescription.findExpectedFormat(null));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 109 */     if (paramBeanProperty != null) {
/* 110 */       JsonFormat.Value localValue = paramSerializerProvider.getAnnotationIntrospector().findFormat(paramBeanProperty.getMember());
/* 111 */       if (localValue != null) {
/* 112 */         Boolean localBoolean = _isShapeWrittenUsingIndex(paramBeanProperty.getType().getRawClass(), localValue, false);
/* 113 */         if (localBoolean != this._serializeAsIndex) {
/* 114 */           return new EnumSerializer(this._values, localBoolean);
/*     */         }
/*     */       }
/*     */     }
/* 118 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumValues getEnumValues()
/*     */   {
/* 127 */     return this._values;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void serialize(Enum<?> paramEnum, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 140 */     if (_serializeAsIndex(paramSerializerProvider)) {
/* 141 */       paramJsonGenerator.writeNumber(paramEnum.ordinal());
/* 142 */       return;
/*     */     }
/* 144 */     paramJsonGenerator.writeString(this._values.serializedValueFor(paramEnum));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
/*     */   {
/* 151 */     if (_serializeAsIndex(paramSerializerProvider)) {
/* 152 */       return createSchemaNode("integer", true);
/*     */     }
/* 154 */     ObjectNode localObjectNode = createSchemaNode("string", true);
/* 155 */     ArrayNode localArrayNode; if (paramType != null) {
/* 156 */       JavaType localJavaType = paramSerializerProvider.constructType(paramType);
/* 157 */       if (localJavaType.isEnumType()) {
/* 158 */         localArrayNode = localObjectNode.putArray("enum");
/* 159 */         for (SerializedString localSerializedString : this._values.values()) {
/* 160 */           localArrayNode.add(localSerializedString.getValue());
/*     */         }
/*     */       }
/*     */     }
/* 164 */     return localObjectNode;
/*     */   }
/*     */   
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
/*     */     throws JsonMappingException
/*     */   {
/*     */     Object localObject;
/* 172 */     if (paramJsonFormatVisitorWrapper.getProvider().isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX)) {
/* 173 */       localObject = paramJsonFormatVisitorWrapper.expectIntegerFormat(paramJavaType);
/* 174 */       if (localObject != null) {
/* 175 */         ((JsonIntegerFormatVisitor)localObject).numberType(JsonParser.NumberType.INT);
/*     */       }
/*     */     } else {
/* 178 */       localObject = paramJsonFormatVisitorWrapper.expectStringFormat(paramJavaType);
/* 179 */       if ((paramJavaType != null) && (localObject != null) && 
/* 180 */         (paramJavaType.isEnumType())) {
/* 181 */         LinkedHashSet localLinkedHashSet = new LinkedHashSet();
/* 182 */         for (SerializedString localSerializedString : this._values.values()) {
/* 183 */           localLinkedHashSet.add(localSerializedString.getValue());
/*     */         }
/* 185 */         ((JsonStringFormatVisitor)localObject).enumTypes(localLinkedHashSet);
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
/*     */   protected final boolean _serializeAsIndex(SerializerProvider paramSerializerProvider)
/*     */   {
/* 199 */     if (this._serializeAsIndex != null) {
/* 200 */       return this._serializeAsIndex.booleanValue();
/*     */     }
/* 202 */     return paramSerializerProvider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static Boolean _isShapeWrittenUsingIndex(Class<?> paramClass, JsonFormat.Value paramValue, boolean paramBoolean)
/*     */   {
/* 212 */     JsonFormat.Shape localShape = paramValue == null ? null : paramValue.getShape();
/* 213 */     if (localShape == null) {
/* 214 */       return null;
/*     */     }
/* 216 */     if ((localShape == JsonFormat.Shape.ANY) || (localShape == JsonFormat.Shape.SCALAR)) {
/* 217 */       return null;
/*     */     }
/* 219 */     if (localShape == JsonFormat.Shape.STRING) {
/* 220 */       return Boolean.FALSE;
/*     */     }
/* 222 */     if (localShape.isNumeric()) {
/* 223 */       return Boolean.TRUE;
/*     */     }
/* 225 */     throw new IllegalArgumentException("Unsupported serialization shape (" + localShape + ") for Enum " + paramClass.getName() + ", not supported as " + (paramBoolean ? "class" : "property") + " annotation");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/std/EnumSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */