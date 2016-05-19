/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.core.FormatSchema;
/*     */ import com.shaded.fasterxml.jackson.core.JsonEncoding;
/*     */ import com.shaded.fasterxml.jackson.core.JsonFactory;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.Feature;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.PrettyPrinter;
/*     */ import com.shaded.fasterxml.jackson.core.Version;
/*     */ import com.shaded.fasterxml.jackson.core.Versioned;
/*     */ import com.shaded.fasterxml.jackson.core.io.SegmentedStringWriter;
/*     */ import com.shaded.fasterxml.jackson.core.type.TypeReference;
/*     */ import com.shaded.fasterxml.jackson.core.util.ByteArrayBuilder;
/*     */ import com.shaded.fasterxml.jackson.core.util.DefaultPrettyPrinter;
/*     */ import com.shaded.fasterxml.jackson.core.util.Instantiatable;
/*     */ import com.shaded.fasterxml.jackson.core.util.MinimalPrettyPrinter;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.PackageVersion;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.FilterProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.SerializerFactory;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.io.Writer;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public class ObjectWriter
/*     */   implements Versioned, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7024829992408267532L;
/*  40 */   protected static final PrettyPrinter NULL_PRETTY_PRINTER = new MinimalPrettyPrinter();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final SerializationConfig _config;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final DefaultSerializerProvider _serializerProvider;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final SerializerFactory _serializerFactory;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JsonFactory _jsonFactory;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JavaType _rootType;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JsonSerializer<Object> _rootSerializer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final PrettyPrinter _prettyPrinter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final FormatSchema _schema;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ObjectWriter(ObjectMapper paramObjectMapper, SerializationConfig paramSerializationConfig, JavaType paramJavaType, PrettyPrinter paramPrettyPrinter)
/*     */   {
/* 109 */     this._config = paramSerializationConfig;
/*     */     
/* 111 */     this._serializerProvider = paramObjectMapper._serializerProvider;
/* 112 */     this._serializerFactory = paramObjectMapper._serializerFactory;
/* 113 */     this._jsonFactory = paramObjectMapper._jsonFactory;
/*     */     
/* 115 */     if (paramJavaType != null) {
/* 116 */       paramJavaType = paramJavaType.withStaticTyping();
/*     */     }
/* 118 */     this._rootType = paramJavaType;
/* 119 */     this._prettyPrinter = paramPrettyPrinter;
/* 120 */     this._schema = null;
/*     */     
/* 122 */     this._rootSerializer = _prefetchRootSerializer(paramSerializationConfig, paramJavaType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ObjectWriter(ObjectMapper paramObjectMapper, SerializationConfig paramSerializationConfig)
/*     */   {
/* 130 */     this._config = paramSerializationConfig;
/*     */     
/* 132 */     this._serializerProvider = paramObjectMapper._serializerProvider;
/* 133 */     this._serializerFactory = paramObjectMapper._serializerFactory;
/* 134 */     this._jsonFactory = paramObjectMapper._jsonFactory;
/*     */     
/* 136 */     this._rootType = null;
/* 137 */     this._rootSerializer = null;
/* 138 */     this._prettyPrinter = null;
/* 139 */     this._schema = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ObjectWriter(ObjectMapper paramObjectMapper, SerializationConfig paramSerializationConfig, FormatSchema paramFormatSchema)
/*     */   {
/* 148 */     this._config = paramSerializationConfig;
/*     */     
/* 150 */     this._serializerProvider = paramObjectMapper._serializerProvider;
/* 151 */     this._serializerFactory = paramObjectMapper._serializerFactory;
/* 152 */     this._jsonFactory = paramObjectMapper._jsonFactory;
/*     */     
/* 154 */     this._rootType = null;
/* 155 */     this._rootSerializer = null;
/* 156 */     this._prettyPrinter = null;
/* 157 */     this._schema = paramFormatSchema;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ObjectWriter(ObjectWriter paramObjectWriter, SerializationConfig paramSerializationConfig, JavaType paramJavaType, JsonSerializer<Object> paramJsonSerializer, PrettyPrinter paramPrettyPrinter, FormatSchema paramFormatSchema)
/*     */   {
/* 167 */     this._config = paramSerializationConfig;
/*     */     
/* 169 */     this._serializerProvider = paramObjectWriter._serializerProvider;
/* 170 */     this._serializerFactory = paramObjectWriter._serializerFactory;
/* 171 */     this._jsonFactory = paramObjectWriter._jsonFactory;
/*     */     
/* 173 */     this._rootType = paramJavaType;
/* 174 */     this._rootSerializer = paramJsonSerializer;
/* 175 */     this._prettyPrinter = paramPrettyPrinter;
/* 176 */     this._schema = paramFormatSchema;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ObjectWriter(ObjectWriter paramObjectWriter, SerializationConfig paramSerializationConfig)
/*     */   {
/* 184 */     this._config = paramSerializationConfig;
/*     */     
/* 186 */     this._serializerProvider = paramObjectWriter._serializerProvider;
/* 187 */     this._serializerFactory = paramObjectWriter._serializerFactory;
/* 188 */     this._jsonFactory = paramObjectWriter._jsonFactory;
/* 189 */     this._schema = paramObjectWriter._schema;
/*     */     
/* 191 */     this._rootType = paramObjectWriter._rootType;
/* 192 */     this._rootSerializer = paramObjectWriter._rootSerializer;
/* 193 */     this._prettyPrinter = paramObjectWriter._prettyPrinter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Version version()
/*     */   {
/* 202 */     return PackageVersion.VERSION;
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
/*     */   public ObjectWriter with(SerializationFeature paramSerializationFeature)
/*     */   {
/* 217 */     SerializationConfig localSerializationConfig = this._config.with(paramSerializationFeature);
/* 218 */     return localSerializationConfig == this._config ? this : new ObjectWriter(this, localSerializationConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectWriter with(SerializationFeature paramSerializationFeature, SerializationFeature... paramVarArgs)
/*     */   {
/* 228 */     SerializationConfig localSerializationConfig = this._config.with(paramSerializationFeature, paramVarArgs);
/* 229 */     return localSerializationConfig == this._config ? this : new ObjectWriter(this, localSerializationConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectWriter withFeatures(SerializationFeature... paramVarArgs)
/*     */   {
/* 238 */     SerializationConfig localSerializationConfig = this._config.withFeatures(paramVarArgs);
/* 239 */     return localSerializationConfig == this._config ? this : new ObjectWriter(this, localSerializationConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectWriter without(SerializationFeature paramSerializationFeature)
/*     */   {
/* 248 */     SerializationConfig localSerializationConfig = this._config.without(paramSerializationFeature);
/* 249 */     return localSerializationConfig == this._config ? this : new ObjectWriter(this, localSerializationConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectWriter without(SerializationFeature paramSerializationFeature, SerializationFeature... paramVarArgs)
/*     */   {
/* 259 */     SerializationConfig localSerializationConfig = this._config.without(paramSerializationFeature, paramVarArgs);
/* 260 */     return localSerializationConfig == this._config ? this : new ObjectWriter(this, localSerializationConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectWriter withoutFeatures(SerializationFeature... paramVarArgs)
/*     */   {
/* 269 */     SerializationConfig localSerializationConfig = this._config.withoutFeatures(paramVarArgs);
/* 270 */     return localSerializationConfig == this._config ? this : new ObjectWriter(this, localSerializationConfig);
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
/*     */   public ObjectWriter with(DateFormat paramDateFormat)
/*     */   {
/* 283 */     SerializationConfig localSerializationConfig = this._config.with(paramDateFormat);
/* 284 */     return localSerializationConfig == this._config ? this : new ObjectWriter(this, localSerializationConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectWriter withDefaultPrettyPrinter()
/*     */   {
/* 293 */     return with(new DefaultPrettyPrinter());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectWriter with(FilterProvider paramFilterProvider)
/*     */   {
/* 302 */     if (paramFilterProvider == this._config.getFilterProvider()) {
/* 303 */       return this;
/*     */     }
/* 305 */     return new ObjectWriter(this, this._config.withFilters(paramFilterProvider));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectWriter with(PrettyPrinter paramPrettyPrinter)
/*     */   {
/* 314 */     if (paramPrettyPrinter == this._prettyPrinter) {
/* 315 */       return this;
/*     */     }
/*     */     
/* 318 */     if (paramPrettyPrinter == null) {
/* 319 */       paramPrettyPrinter = NULL_PRETTY_PRINTER;
/*     */     }
/* 321 */     return new ObjectWriter(this, this._config, this._rootType, this._rootSerializer, paramPrettyPrinter, this._schema);
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
/*     */   public ObjectWriter withRootName(String paramString)
/*     */   {
/* 334 */     SerializationConfig localSerializationConfig = this._config.withRootName(paramString);
/* 335 */     return localSerializationConfig == this._config ? this : new ObjectWriter(this, localSerializationConfig);
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
/*     */   public ObjectWriter withSchema(FormatSchema paramFormatSchema)
/*     */   {
/* 348 */     if (this._schema == paramFormatSchema) {
/* 349 */       return this;
/*     */     }
/* 351 */     _verifySchemaType(paramFormatSchema);
/* 352 */     return new ObjectWriter(this, this._config, this._rootType, this._rootSerializer, this._prettyPrinter, paramFormatSchema);
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
/*     */   public ObjectWriter withType(JavaType paramJavaType)
/*     */   {
/* 366 */     paramJavaType = paramJavaType.withStaticTyping();
/* 367 */     JsonSerializer localJsonSerializer = _prefetchRootSerializer(this._config, paramJavaType);
/* 368 */     return new ObjectWriter(this, this._config, paramJavaType, localJsonSerializer, this._prettyPrinter, this._schema);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectWriter withType(Class<?> paramClass)
/*     */   {
/* 377 */     return withType(this._config.constructType(paramClass));
/*     */   }
/*     */   
/*     */   public ObjectWriter withType(TypeReference<?> paramTypeReference) {
/* 381 */     return withType(this._config.getTypeFactory().constructType(paramTypeReference.getType()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectWriter withView(Class<?> paramClass)
/*     */   {
/* 393 */     SerializationConfig localSerializationConfig = this._config.withView(paramClass);
/* 394 */     return localSerializationConfig == this._config ? this : new ObjectWriter(this, localSerializationConfig);
/*     */   }
/*     */   
/*     */   public ObjectWriter with(Locale paramLocale) {
/* 398 */     SerializationConfig localSerializationConfig = this._config.with(paramLocale);
/* 399 */     return localSerializationConfig == this._config ? this : new ObjectWriter(this, localSerializationConfig);
/*     */   }
/*     */   
/*     */   public ObjectWriter with(TimeZone paramTimeZone) {
/* 403 */     SerializationConfig localSerializationConfig = this._config.with(paramTimeZone);
/* 404 */     return localSerializationConfig == this._config ? this : new ObjectWriter(this, localSerializationConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectWriter with(Base64Variant paramBase64Variant)
/*     */   {
/* 414 */     SerializationConfig localSerializationConfig = this._config.with(paramBase64Variant);
/* 415 */     return localSerializationConfig == this._config ? this : new ObjectWriter(this, localSerializationConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEnabled(SerializationFeature paramSerializationFeature)
/*     */   {
/* 425 */     return this._config.isEnabled(paramSerializationFeature);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(MapperFeature paramMapperFeature) {
/* 429 */     return this._config.isEnabled(paramMapperFeature);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(JsonParser.Feature paramFeature) {
/* 433 */     return this._jsonFactory.isEnabled(paramFeature);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public SerializationConfig getConfig()
/*     */   {
/* 440 */     return this._config;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public JsonFactory getJsonFactory()
/*     */   {
/* 448 */     return this._jsonFactory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonFactory getFactory()
/*     */   {
/* 455 */     return this._jsonFactory;
/*     */   }
/*     */   
/*     */   public TypeFactory getTypeFactory() {
/* 459 */     return this._config.getTypeFactory();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasPrefetchedSerializer()
/*     */   {
/* 471 */     return this._rootSerializer != null;
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
/*     */   public void writeValue(JsonGenerator paramJsonGenerator, Object paramObject)
/*     */     throws IOException, JsonGenerationException, JsonMappingException
/*     */   {
/* 488 */     _configureJsonGenerator(paramJsonGenerator);
/* 489 */     if ((this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE)) && ((paramObject instanceof Closeable)))
/*     */     {
/* 491 */       _writeCloseableValue(paramJsonGenerator, paramObject, this._config);
/*     */     } else {
/* 493 */       if (this._rootType == null) {
/* 494 */         _serializerProvider(this._config).serializeValue(paramJsonGenerator, paramObject);
/*     */       } else {
/* 496 */         _serializerProvider(this._config).serializeValue(paramJsonGenerator, paramObject, this._rootType, this._rootSerializer);
/*     */       }
/* 498 */       if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
/* 499 */         paramJsonGenerator.flush();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeValue(File paramFile, Object paramObject)
/*     */     throws IOException, JsonGenerationException, JsonMappingException
/*     */   {
/* 517 */     _configAndWriteValue(this._jsonFactory.createGenerator(paramFile, JsonEncoding.UTF8), paramObject);
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
/*     */   public void writeValue(OutputStream paramOutputStream, Object paramObject)
/*     */     throws IOException, JsonGenerationException, JsonMappingException
/*     */   {
/* 534 */     _configAndWriteValue(this._jsonFactory.createGenerator(paramOutputStream, JsonEncoding.UTF8), paramObject);
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
/*     */   public void writeValue(Writer paramWriter, Object paramObject)
/*     */     throws IOException, JsonGenerationException, JsonMappingException
/*     */   {
/* 550 */     _configAndWriteValue(this._jsonFactory.createGenerator(paramWriter), paramObject);
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
/*     */   public String writeValueAsString(Object paramObject)
/*     */     throws JsonProcessingException
/*     */   {
/* 565 */     SegmentedStringWriter localSegmentedStringWriter = new SegmentedStringWriter(this._jsonFactory._getBufferRecycler());
/*     */     try {
/* 567 */       _configAndWriteValue(this._jsonFactory.createGenerator(localSegmentedStringWriter), paramObject);
/*     */     } catch (JsonProcessingException localJsonProcessingException) {
/* 569 */       throw localJsonProcessingException;
/*     */     } catch (IOException localIOException) {
/* 571 */       throw JsonMappingException.fromUnexpectedIOE(localIOException);
/*     */     }
/* 573 */     return localSegmentedStringWriter.getAndClear();
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
/*     */   public byte[] writeValueAsBytes(Object paramObject)
/*     */     throws JsonProcessingException
/*     */   {
/* 588 */     ByteArrayBuilder localByteArrayBuilder = new ByteArrayBuilder(this._jsonFactory._getBufferRecycler());
/*     */     try {
/* 590 */       _configAndWriteValue(this._jsonFactory.createGenerator(localByteArrayBuilder, JsonEncoding.UTF8), paramObject);
/*     */     } catch (JsonProcessingException localJsonProcessingException) {
/* 592 */       throw localJsonProcessingException;
/*     */     } catch (IOException localIOException) {
/* 594 */       throw JsonMappingException.fromUnexpectedIOE(localIOException);
/*     */     }
/* 596 */     byte[] arrayOfByte = localByteArrayBuilder.toByteArray();
/* 597 */     localByteArrayBuilder.release();
/* 598 */     return arrayOfByte;
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
/*     */ 
/*     */ 
/*     */   public void acceptJsonFormatVisitor(JavaType paramJavaType, JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper)
/*     */     throws JsonMappingException
/*     */   {
/* 622 */     if (paramJavaType == null) {
/* 623 */       throw new IllegalArgumentException("type must be provided");
/*     */     }
/* 625 */     _serializerProvider(this._config).acceptJsonFormatVisitor(paramJavaType, paramJsonFormatVisitorWrapper);
/*     */   }
/*     */   
/*     */   public boolean canSerialize(Class<?> paramClass) {
/* 629 */     return _serializerProvider(this._config).hasSerializerFor(paramClass);
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
/*     */   protected DefaultSerializerProvider _serializerProvider(SerializationConfig paramSerializationConfig)
/*     */   {
/* 643 */     return this._serializerProvider.createInstance(paramSerializationConfig, this._serializerFactory);
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
/*     */   protected void _verifySchemaType(FormatSchema paramFormatSchema)
/*     */   {
/* 657 */     if ((paramFormatSchema != null) && 
/* 658 */       (!this._jsonFactory.canUseSchema(paramFormatSchema))) {
/* 659 */       throw new IllegalArgumentException("Can not use FormatSchema of type " + paramFormatSchema.getClass().getName() + " for format " + this._jsonFactory.getFormatName());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void _configAndWriteValue(JsonGenerator paramJsonGenerator, Object paramObject)
/*     */     throws IOException, JsonGenerationException, JsonMappingException
/*     */   {
/* 672 */     _configureJsonGenerator(paramJsonGenerator);
/*     */     
/* 674 */     if ((this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE)) && ((paramObject instanceof Closeable))) {
/* 675 */       _writeCloseable(paramJsonGenerator, paramObject, this._config);
/* 676 */       return;
/*     */     }
/* 678 */     int i = 0;
/*     */     try {
/* 680 */       if (this._rootType == null) {
/* 681 */         _serializerProvider(this._config).serializeValue(paramJsonGenerator, paramObject);
/*     */       } else {
/* 683 */         _serializerProvider(this._config).serializeValue(paramJsonGenerator, paramObject, this._rootType, this._rootSerializer);
/*     */       }
/* 685 */       i = 1;
/* 686 */       paramJsonGenerator.close(); return;
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 691 */       if (i == 0) {
/*     */         try {
/* 693 */           paramJsonGenerator.close();
/*     */         }
/*     */         catch (IOException localIOException2) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final void _writeCloseable(JsonGenerator paramJsonGenerator, Object paramObject, SerializationConfig paramSerializationConfig)
/*     */     throws IOException, JsonGenerationException, JsonMappingException
/*     */   {
/* 706 */     Closeable localCloseable1 = (Closeable)paramObject;
/*     */     try {
/* 708 */       if (this._rootType == null) {
/* 709 */         _serializerProvider(paramSerializationConfig).serializeValue(paramJsonGenerator, paramObject);
/*     */       } else {
/* 711 */         _serializerProvider(paramSerializationConfig).serializeValue(paramJsonGenerator, paramObject, this._rootType, this._rootSerializer);
/*     */       }
/* 713 */       JsonGenerator localJsonGenerator = paramJsonGenerator;
/* 714 */       paramJsonGenerator = null;
/* 715 */       localJsonGenerator.close();
/* 716 */       Closeable localCloseable2 = localCloseable1;
/* 717 */       localCloseable1 = null;
/* 718 */       localCloseable2.close(); return;
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 723 */       if (paramJsonGenerator != null) {
/*     */         try {
/* 725 */           paramJsonGenerator.close();
/*     */         } catch (IOException localIOException3) {}
/*     */       }
/* 728 */       if (localCloseable1 != null) {
/*     */         try {
/* 730 */           localCloseable1.close();
/*     */         }
/*     */         catch (IOException localIOException4) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final void _writeCloseableValue(JsonGenerator paramJsonGenerator, Object paramObject, SerializationConfig paramSerializationConfig)
/*     */     throws IOException, JsonGenerationException, JsonMappingException
/*     */   {
/* 743 */     Closeable localCloseable1 = (Closeable)paramObject;
/*     */     try {
/* 745 */       if (this._rootType == null) {
/* 746 */         _serializerProvider(paramSerializationConfig).serializeValue(paramJsonGenerator, paramObject);
/*     */       } else {
/* 748 */         _serializerProvider(paramSerializationConfig).serializeValue(paramJsonGenerator, paramObject, this._rootType, this._rootSerializer);
/*     */       }
/* 750 */       if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
/* 751 */         paramJsonGenerator.flush();
/*     */       }
/* 753 */       Closeable localCloseable2 = localCloseable1;
/* 754 */       localCloseable1 = null;
/* 755 */       localCloseable2.close(); return;
/*     */     } finally {
/* 757 */       if (localCloseable1 != null) {
/*     */         try {
/* 759 */           localCloseable1.close();
/*     */         }
/*     */         catch (IOException localIOException2) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonSerializer<Object> _prefetchRootSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType)
/*     */   {
/* 773 */     if ((paramJavaType == null) || (!this._config.isEnabled(SerializationFeature.EAGER_SERIALIZER_FETCH))) {
/* 774 */       return null;
/*     */     }
/*     */     try {
/* 777 */       return _serializerProvider(paramSerializationConfig).findTypedValueSerializer(paramJavaType, true, null);
/*     */     }
/*     */     catch (JsonProcessingException localJsonProcessingException) {}
/* 780 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void _configureJsonGenerator(JsonGenerator paramJsonGenerator)
/*     */   {
/* 792 */     if (this._prettyPrinter != null) {
/* 793 */       PrettyPrinter localPrettyPrinter = this._prettyPrinter;
/* 794 */       if (localPrettyPrinter == NULL_PRETTY_PRINTER) {
/* 795 */         paramJsonGenerator.setPrettyPrinter(null);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 800 */         if ((localPrettyPrinter instanceof Instantiatable)) {
/* 801 */           localPrettyPrinter = (PrettyPrinter)((Instantiatable)localPrettyPrinter).createInstance();
/*     */         }
/* 803 */         paramJsonGenerator.setPrettyPrinter(localPrettyPrinter);
/*     */       }
/* 805 */     } else if (this._config.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
/* 806 */       paramJsonGenerator.useDefaultPrettyPrinter();
/*     */     }
/*     */     
/* 809 */     if (this._schema != null) {
/* 810 */       paramJsonGenerator.setSchema(this._schema);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ObjectWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */