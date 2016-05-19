/*      */ package com.shaded.fasterxml.jackson.databind;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*      */ import com.shaded.fasterxml.jackson.core.FormatSchema;
/*      */ import com.shaded.fasterxml.jackson.core.JsonFactory;
/*      */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*      */ import com.shaded.fasterxml.jackson.core.JsonLocation;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParseException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParser.Feature;
/*      */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*      */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*      */ import com.shaded.fasterxml.jackson.core.TreeNode;
/*      */ import com.shaded.fasterxml.jackson.core.Versioned;
/*      */ import com.shaded.fasterxml.jackson.core.io.SerializedString;
/*      */ import com.shaded.fasterxml.jackson.core.type.ResolvedType;
/*      */ import com.shaded.fasterxml.jackson.core.type.TypeReference;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.DataFormatReaders;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.DataFormatReaders.Match;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
/*      */ import com.shaded.fasterxml.jackson.databind.node.JsonNodeFactory;
/*      */ import com.shaded.fasterxml.jackson.databind.node.NullNode;
/*      */ import com.shaded.fasterxml.jackson.databind.node.TreeTraversingParser;
/*      */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*      */ import com.shaded.fasterxml.jackson.databind.util.RootNameLookup;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.lang.reflect.Type;
/*      */ import java.net.URL;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ 
/*      */ public class ObjectReader extends ObjectCodec implements Versioned, java.io.Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -4251443320039569153L;
/*   43 */   private static final JavaType JSON_NODE_TYPE = com.shaded.fasterxml.jackson.databind.type.SimpleType.constructUnsafe(JsonNode.class);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final DeserializationConfig _config;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final DefaultDeserializationContext _context;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final JsonFactory _jsonFactory;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final boolean _unwrapRoot;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final JavaType _valueType;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final JsonDeserializer<Object> _rootDeserializer;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final Object _valueToUpdate;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final FormatSchema _schema;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final InjectableValues _injectableValues;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final DataFormatReaders _dataFormatReaders;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _rootDeserializers;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final RootNameLookup _rootNames;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ObjectReader(ObjectMapper paramObjectMapper, DeserializationConfig paramDeserializationConfig)
/*      */   {
/*  161 */     this(paramObjectMapper, paramDeserializationConfig, null, null, null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ObjectReader(ObjectMapper paramObjectMapper, DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, Object paramObject, FormatSchema paramFormatSchema, InjectableValues paramInjectableValues)
/*      */   {
/*  172 */     this._config = paramDeserializationConfig;
/*  173 */     this._context = paramObjectMapper._deserializationContext;
/*  174 */     this._rootDeserializers = paramObjectMapper._rootDeserializers;
/*  175 */     this._jsonFactory = paramObjectMapper._jsonFactory;
/*  176 */     this._rootNames = paramObjectMapper._rootNames;
/*  177 */     this._valueType = paramJavaType;
/*  178 */     this._valueToUpdate = paramObject;
/*  179 */     if ((paramObject != null) && (paramJavaType.isArrayType())) {
/*  180 */       throw new IllegalArgumentException("Can not update an array value");
/*      */     }
/*  182 */     this._schema = paramFormatSchema;
/*  183 */     this._injectableValues = paramInjectableValues;
/*  184 */     this._unwrapRoot = paramDeserializationConfig.useRootWrapping();
/*      */     
/*  186 */     this._rootDeserializer = _prefetchRootDeserializer(paramDeserializationConfig, paramJavaType);
/*  187 */     this._dataFormatReaders = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ObjectReader(ObjectReader paramObjectReader, DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, JsonDeserializer<Object> paramJsonDeserializer, Object paramObject, FormatSchema paramFormatSchema, InjectableValues paramInjectableValues, DataFormatReaders paramDataFormatReaders)
/*      */   {
/*  198 */     this._config = paramDeserializationConfig;
/*  199 */     this._context = paramObjectReader._context;
/*      */     
/*  201 */     this._rootDeserializers = paramObjectReader._rootDeserializers;
/*  202 */     this._jsonFactory = paramObjectReader._jsonFactory;
/*  203 */     this._rootNames = paramObjectReader._rootNames;
/*      */     
/*  205 */     this._valueType = paramJavaType;
/*  206 */     this._rootDeserializer = paramJsonDeserializer;
/*  207 */     this._valueToUpdate = paramObject;
/*  208 */     if ((paramObject != null) && (paramJavaType.isArrayType())) {
/*  209 */       throw new IllegalArgumentException("Can not update an array value");
/*      */     }
/*  211 */     this._schema = paramFormatSchema;
/*  212 */     this._injectableValues = paramInjectableValues;
/*  213 */     this._unwrapRoot = paramDeserializationConfig.useRootWrapping();
/*  214 */     this._dataFormatReaders = paramDataFormatReaders;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ObjectReader(ObjectReader paramObjectReader, DeserializationConfig paramDeserializationConfig)
/*      */   {
/*  222 */     this._config = paramDeserializationConfig;
/*  223 */     this._context = paramObjectReader._context;
/*      */     
/*  225 */     this._rootDeserializers = paramObjectReader._rootDeserializers;
/*  226 */     this._jsonFactory = paramObjectReader._jsonFactory;
/*  227 */     this._rootNames = paramObjectReader._rootNames;
/*      */     
/*  229 */     this._valueType = paramObjectReader._valueType;
/*  230 */     this._rootDeserializer = paramObjectReader._rootDeserializer;
/*  231 */     this._valueToUpdate = paramObjectReader._valueToUpdate;
/*  232 */     this._schema = paramObjectReader._schema;
/*  233 */     this._injectableValues = paramObjectReader._injectableValues;
/*  234 */     this._unwrapRoot = paramDeserializationConfig.useRootWrapping();
/*  235 */     this._dataFormatReaders = paramObjectReader._dataFormatReaders;
/*      */   }
/*      */   
/*      */   protected ObjectReader(ObjectReader paramObjectReader, JsonFactory paramJsonFactory)
/*      */   {
/*  240 */     this._config = paramObjectReader._config;
/*  241 */     this._context = paramObjectReader._context;
/*      */     
/*  243 */     this._rootDeserializers = paramObjectReader._rootDeserializers;
/*  244 */     this._jsonFactory = paramJsonFactory;
/*  245 */     this._rootNames = paramObjectReader._rootNames;
/*      */     
/*  247 */     this._valueType = paramObjectReader._valueType;
/*  248 */     this._rootDeserializer = paramObjectReader._rootDeserializer;
/*  249 */     this._valueToUpdate = paramObjectReader._valueToUpdate;
/*  250 */     this._schema = paramObjectReader._schema;
/*  251 */     this._injectableValues = paramObjectReader._injectableValues;
/*  252 */     this._unwrapRoot = paramObjectReader._unwrapRoot;
/*  253 */     this._dataFormatReaders = paramObjectReader._dataFormatReaders;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public com.shaded.fasterxml.jackson.core.Version version()
/*      */   {
/*  262 */     return com.shaded.fasterxml.jackson.databind.cfg.PackageVersion.VERSION;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader with(DeserializationConfig paramDeserializationConfig)
/*      */   {
/*  272 */     return _with(paramDeserializationConfig);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader with(DeserializationFeature paramDeserializationFeature)
/*      */   {
/*  280 */     return _with(this._config.with(paramDeserializationFeature));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader with(DeserializationFeature paramDeserializationFeature, DeserializationFeature... paramVarArgs)
/*      */   {
/*  290 */     return _with(this._config.with(paramDeserializationFeature, paramVarArgs));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader withFeatures(DeserializationFeature... paramVarArgs)
/*      */   {
/*  298 */     return _with(this._config.withFeatures(paramVarArgs));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader without(DeserializationFeature paramDeserializationFeature)
/*      */   {
/*  306 */     return _with(this._config.without(paramDeserializationFeature));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader without(DeserializationFeature paramDeserializationFeature, DeserializationFeature... paramVarArgs)
/*      */   {
/*  316 */     return _with(this._config.without(paramDeserializationFeature, paramVarArgs));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader withoutFeatures(DeserializationFeature... paramVarArgs)
/*      */   {
/*  324 */     return _with(this._config.withoutFeatures(paramVarArgs));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader with(InjectableValues paramInjectableValues)
/*      */   {
/*  336 */     if (this._injectableValues == paramInjectableValues) {
/*  337 */       return this;
/*      */     }
/*  339 */     return new ObjectReader(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, this._schema, paramInjectableValues, this._dataFormatReaders);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader with(JsonNodeFactory paramJsonNodeFactory)
/*      */   {
/*  353 */     return _with(this._config.with(paramJsonNodeFactory));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader with(JsonFactory paramJsonFactory)
/*      */   {
/*  368 */     if (paramJsonFactory == this._jsonFactory) {
/*  369 */       return this;
/*      */     }
/*  371 */     ObjectReader localObjectReader = new ObjectReader(this, paramJsonFactory);
/*      */     
/*  373 */     if (paramJsonFactory.getCodec() == null) {
/*  374 */       paramJsonFactory.setCodec(localObjectReader);
/*      */     }
/*  376 */     return localObjectReader;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader withRootName(String paramString)
/*      */   {
/*  389 */     return _with(this._config.withRootName(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader with(FormatSchema paramFormatSchema)
/*      */   {
/*  402 */     if (this._schema == paramFormatSchema) {
/*  403 */       return this;
/*      */     }
/*  405 */     _verifySchemaType(paramFormatSchema);
/*  406 */     return new ObjectReader(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, paramFormatSchema, this._injectableValues, this._dataFormatReaders);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader withType(JavaType paramJavaType)
/*      */   {
/*  419 */     if ((paramJavaType != null) && (paramJavaType.equals(this._valueType))) {
/*  420 */       return this;
/*      */     }
/*  422 */     JsonDeserializer localJsonDeserializer = _prefetchRootDeserializer(this._config, paramJavaType);
/*      */     
/*  424 */     DataFormatReaders localDataFormatReaders = this._dataFormatReaders;
/*  425 */     if (localDataFormatReaders != null) {
/*  426 */       localDataFormatReaders = localDataFormatReaders.withType(paramJavaType);
/*      */     }
/*  428 */     return new ObjectReader(this, this._config, paramJavaType, localJsonDeserializer, this._valueToUpdate, this._schema, this._injectableValues, localDataFormatReaders);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader withType(Class<?> paramClass)
/*      */   {
/*  440 */     return withType(this._config.constructType(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader withType(Type paramType)
/*      */   {
/*  451 */     return withType(this._config.getTypeFactory().constructType(paramType));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader withType(TypeReference<?> paramTypeReference)
/*      */   {
/*  462 */     return withType(this._config.getTypeFactory().constructType(paramTypeReference.getType()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader withValueToUpdate(Object paramObject)
/*      */   {
/*  475 */     if (paramObject == this._valueToUpdate) return this;
/*  476 */     if (paramObject == null) {
/*  477 */       throw new IllegalArgumentException("cat not update null value");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     JavaType localJavaType;
/*      */     
/*      */ 
/*  485 */     if (this._valueType == null) {
/*  486 */       localJavaType = this._config.constructType(paramObject.getClass());
/*      */     } else {
/*  488 */       localJavaType = this._valueType;
/*      */     }
/*  490 */     return new ObjectReader(this, this._config, localJavaType, this._rootDeserializer, paramObject, this._schema, this._injectableValues, this._dataFormatReaders);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader withView(Class<?> paramClass)
/*      */   {
/*  502 */     return _with(this._config.withView(paramClass));
/*      */   }
/*      */   
/*      */   public ObjectReader with(Locale paramLocale) {
/*  506 */     return _with(this._config.with(paramLocale));
/*      */   }
/*      */   
/*      */   public ObjectReader with(TimeZone paramTimeZone) {
/*  510 */     return _with(this._config.with(paramTimeZone));
/*      */   }
/*      */   
/*      */   public ObjectReader withHandler(DeserializationProblemHandler paramDeserializationProblemHandler) {
/*  514 */     return _with(this._config.withHandler(paramDeserializationProblemHandler));
/*      */   }
/*      */   
/*      */   public ObjectReader with(Base64Variant paramBase64Variant) {
/*  518 */     return _with(this._config.with(paramBase64Variant));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader withFormatDetection(ObjectReader... paramVarArgs)
/*      */   {
/*  545 */     return withFormatDetection(new DataFormatReaders(paramVarArgs));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader withFormatDetection(DataFormatReaders paramDataFormatReaders)
/*      */   {
/*  565 */     return new ObjectReader(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, this._schema, this._injectableValues, paramDataFormatReaders);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEnabled(DeserializationFeature paramDeserializationFeature)
/*      */   {
/*  576 */     return this._config.isEnabled(paramDeserializationFeature);
/*      */   }
/*      */   
/*      */   public boolean isEnabled(MapperFeature paramMapperFeature) {
/*  580 */     return this._config.isEnabled(paramMapperFeature);
/*      */   }
/*      */   
/*      */   public boolean isEnabled(JsonParser.Feature paramFeature) {
/*  584 */     return this._jsonFactory.isEnabled(paramFeature);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public DeserializationConfig getConfig()
/*      */   {
/*  591 */     return this._config;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonFactory getFactory()
/*      */   {
/*  599 */     return this._jsonFactory;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public JsonFactory getJsonFactory()
/*      */   {
/*  608 */     return this._jsonFactory;
/*      */   }
/*      */   
/*      */   public TypeFactory getTypeFactory() {
/*  612 */     return this._config.getTypeFactory();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(JsonParser paramJsonParser)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  635 */     return (T)_bind(paramJsonParser, this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(JsonParser paramJsonParser, Class<T> paramClass)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  653 */     return (T)withType(paramClass).readValue(paramJsonParser);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(JsonParser paramJsonParser, TypeReference<?> paramTypeReference)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  671 */     return (T)withType(paramTypeReference).readValue(paramJsonParser);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(JsonParser paramJsonParser, ResolvedType paramResolvedType)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  687 */     return (T)withType((JavaType)paramResolvedType).readValue(paramJsonParser);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(JsonParser paramJsonParser, JavaType paramJavaType)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  698 */     return (T)withType(paramJavaType).readValue(paramJsonParser);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T extends TreeNode> T readTree(JsonParser paramJsonParser)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  717 */     return _bindAsTree(paramJsonParser);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> Iterator<T> readValues(JsonParser paramJsonParser, Class<T> paramClass)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  732 */     return withType(paramClass).readValues(paramJsonParser);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> Iterator<T> readValues(JsonParser paramJsonParser, TypeReference<?> paramTypeReference)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  747 */     return withType(paramTypeReference).readValues(paramJsonParser);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> Iterator<T> readValues(JsonParser paramJsonParser, ResolvedType paramResolvedType)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  762 */     return readValues(paramJsonParser, (JavaType)paramResolvedType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> Iterator<T> readValues(JsonParser paramJsonParser, JavaType paramJavaType)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  776 */     return withType(paramJavaType).readValues(paramJsonParser);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(InputStream paramInputStream)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  795 */     if (this._dataFormatReaders != null) {
/*  796 */       return (T)_detectBindAndClose(this._dataFormatReaders.findFormat(paramInputStream), false);
/*      */     }
/*  798 */     return (T)_bindAndClose(this._jsonFactory.createParser(paramInputStream), this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(Reader paramReader)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  811 */     if (this._dataFormatReaders != null) {
/*  812 */       _reportUndetectableSource(paramReader);
/*      */     }
/*  814 */     return (T)_bindAndClose(this._jsonFactory.createParser(paramReader), this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(String paramString)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  827 */     if (this._dataFormatReaders != null) {
/*  828 */       _reportUndetectableSource(paramString);
/*      */     }
/*  830 */     return (T)_bindAndClose(this._jsonFactory.createParser(paramString), this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(byte[] paramArrayOfByte)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  843 */     if (this._dataFormatReaders != null) {
/*  844 */       return (T)_detectBindAndClose(paramArrayOfByte, 0, paramArrayOfByte.length);
/*      */     }
/*  846 */     return (T)_bindAndClose(this._jsonFactory.createParser(paramArrayOfByte), this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  859 */     if (this._dataFormatReaders != null) {
/*  860 */       return (T)_detectBindAndClose(paramArrayOfByte, paramInt1, paramInt2);
/*      */     }
/*  862 */     return (T)_bindAndClose(this._jsonFactory.createParser(paramArrayOfByte, paramInt1, paramInt2), this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(File paramFile)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  869 */     if (this._dataFormatReaders != null) {
/*  870 */       return (T)_detectBindAndClose(this._dataFormatReaders.findFormat(_inputStream(paramFile)), true);
/*      */     }
/*  872 */     return (T)_bindAndClose(this._jsonFactory.createParser(paramFile), this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(URL paramURL)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  885 */     if (this._dataFormatReaders != null) {
/*  886 */       return (T)_detectBindAndClose(this._dataFormatReaders.findFormat(_inputStream(paramURL)), true);
/*      */     }
/*  888 */     return (T)_bindAndClose(this._jsonFactory.createParser(paramURL), this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(JsonNode paramJsonNode)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  902 */     if (this._dataFormatReaders != null) {
/*  903 */       _reportUndetectableSource(paramJsonNode);
/*      */     }
/*  905 */     return (T)_bindAndClose(treeAsTokens(paramJsonNode), this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonNode readTree(InputStream paramInputStream)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  920 */     if (this._dataFormatReaders != null) {
/*  921 */       return _detectBindAndCloseAsTree(paramInputStream);
/*      */     }
/*  923 */     return _bindAndCloseAsTree(this._jsonFactory.createParser(paramInputStream));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonNode readTree(Reader paramReader)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  938 */     if (this._dataFormatReaders != null) {
/*  939 */       _reportUndetectableSource(paramReader);
/*      */     }
/*  941 */     return _bindAndCloseAsTree(this._jsonFactory.createParser(paramReader));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonNode readTree(String paramString)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  956 */     if (this._dataFormatReaders != null) {
/*  957 */       _reportUndetectableSource(paramString);
/*      */     }
/*  959 */     return _bindAndCloseAsTree(this._jsonFactory.createParser(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> MappingIterator<T> readValues(JsonParser paramJsonParser)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  982 */     DefaultDeserializationContext localDefaultDeserializationContext = createDeserializationContext(paramJsonParser, this._config);
/*      */     
/*  984 */     return new MappingIterator(this._valueType, paramJsonParser, localDefaultDeserializationContext, _findRootDeserializer(localDefaultDeserializationContext, this._valueType), false, this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> MappingIterator<T> readValues(InputStream paramInputStream)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1012 */     if (this._dataFormatReaders != null) {
/* 1013 */       return _detectBindAndReadValues(this._dataFormatReaders.findFormat(paramInputStream), false);
/*      */     }
/* 1015 */     return _bindAndReadValues(this._jsonFactory.createParser(paramInputStream), this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> MappingIterator<T> readValues(Reader paramReader)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1024 */     if (this._dataFormatReaders != null) {
/* 1025 */       _reportUndetectableSource(paramReader);
/*      */     }
/* 1027 */     JsonParser localJsonParser = this._jsonFactory.createParser(paramReader);
/* 1028 */     if (this._schema != null) {
/* 1029 */       localJsonParser.setSchema(this._schema);
/*      */     }
/* 1031 */     localJsonParser.nextToken();
/* 1032 */     DefaultDeserializationContext localDefaultDeserializationContext = createDeserializationContext(localJsonParser, this._config);
/* 1033 */     return new MappingIterator(this._valueType, localJsonParser, localDefaultDeserializationContext, _findRootDeserializer(localDefaultDeserializationContext, this._valueType), true, this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> MappingIterator<T> readValues(String paramString)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1045 */     if (this._dataFormatReaders != null) {
/* 1046 */       _reportUndetectableSource(paramString);
/*      */     }
/* 1048 */     JsonParser localJsonParser = this._jsonFactory.createParser(paramString);
/* 1049 */     if (this._schema != null) {
/* 1050 */       localJsonParser.setSchema(this._schema);
/*      */     }
/* 1052 */     localJsonParser.nextToken();
/* 1053 */     DefaultDeserializationContext localDefaultDeserializationContext = createDeserializationContext(localJsonParser, this._config);
/* 1054 */     return new MappingIterator(this._valueType, localJsonParser, localDefaultDeserializationContext, _findRootDeserializer(localDefaultDeserializationContext, this._valueType), true, this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> MappingIterator<T> readValues(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1064 */     if (this._dataFormatReaders != null) {
/* 1065 */       return _detectBindAndReadValues(this._dataFormatReaders.findFormat(paramArrayOfByte, paramInt1, paramInt2), false);
/*      */     }
/* 1067 */     return _bindAndReadValues(this._jsonFactory.createParser(paramArrayOfByte), this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final <T> MappingIterator<T> readValues(byte[] paramArrayOfByte)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1075 */     return readValues(paramArrayOfByte, 0, paramArrayOfByte.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> MappingIterator<T> readValues(File paramFile)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1084 */     if (this._dataFormatReaders != null) {
/* 1085 */       return _detectBindAndReadValues(this._dataFormatReaders.findFormat(_inputStream(paramFile)), false);
/*      */     }
/*      */     
/* 1088 */     return _bindAndReadValues(this._jsonFactory.createParser(paramFile), this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> MappingIterator<T> readValues(URL paramURL)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1099 */     if (this._dataFormatReaders != null) {
/* 1100 */       return _detectBindAndReadValues(this._dataFormatReaders.findFormat(_inputStream(paramURL)), true);
/*      */     }
/*      */     
/* 1103 */     return _bindAndReadValues(this._jsonFactory.createParser(paramURL), this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonNode createArrayNode()
/*      */   {
/* 1114 */     return this._config.getNodeFactory().arrayNode();
/*      */   }
/*      */   
/*      */   public JsonNode createObjectNode()
/*      */   {
/* 1119 */     return this._config.getNodeFactory().objectNode();
/*      */   }
/*      */   
/*      */   public JsonParser treeAsTokens(TreeNode paramTreeNode)
/*      */   {
/* 1124 */     return new TreeTraversingParser((JsonNode)paramTreeNode, this);
/*      */   }
/*      */   
/*      */   public <T> T treeToValue(TreeNode paramTreeNode, Class<T> paramClass)
/*      */     throws JsonProcessingException
/*      */   {
/*      */     try
/*      */     {
/* 1132 */       return (T)readValue(treeAsTokens(paramTreeNode), paramClass);
/*      */     } catch (JsonProcessingException localJsonProcessingException) {
/* 1134 */       throw localJsonProcessingException;
/*      */     } catch (IOException localIOException) {
/* 1136 */       throw new IllegalArgumentException(localIOException.getMessage(), localIOException);
/*      */     }
/*      */   }
/*      */   
/*      */   public void writeValue(JsonGenerator paramJsonGenerator, Object paramObject)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1143 */     throw new UnsupportedOperationException("Not implemented for ObjectReader");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Object _bind(JsonParser paramJsonParser, Object paramObject)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 1162 */     JsonToken localJsonToken = _initForReading(paramJsonParser);
/* 1163 */     DefaultDeserializationContext localDefaultDeserializationContext; Object localObject; if (localJsonToken == JsonToken.VALUE_NULL) {
/* 1164 */       if (paramObject == null) {
/* 1165 */         localDefaultDeserializationContext = createDeserializationContext(paramJsonParser, this._config);
/* 1166 */         localObject = _findRootDeserializer(localDefaultDeserializationContext, this._valueType).getNullValue();
/*      */       } else {
/* 1168 */         localObject = paramObject;
/*      */       }
/* 1170 */     } else if ((localJsonToken == JsonToken.END_ARRAY) || (localJsonToken == JsonToken.END_OBJECT)) {
/* 1171 */       localObject = paramObject;
/*      */     } else {
/* 1173 */       localDefaultDeserializationContext = createDeserializationContext(paramJsonParser, this._config);
/* 1174 */       JsonDeserializer localJsonDeserializer = _findRootDeserializer(localDefaultDeserializationContext, this._valueType);
/* 1175 */       if (this._unwrapRoot) {
/* 1176 */         localObject = _unwrapAndDeserialize(paramJsonParser, localDefaultDeserializationContext, this._valueType, localJsonDeserializer);
/*      */       }
/* 1178 */       else if (paramObject == null) {
/* 1179 */         localObject = localJsonDeserializer.deserialize(paramJsonParser, localDefaultDeserializationContext);
/*      */       } else {
/* 1181 */         localJsonDeserializer.deserialize(paramJsonParser, localDefaultDeserializationContext, paramObject);
/* 1182 */         localObject = paramObject;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1187 */     paramJsonParser.clearCurrentToken();
/* 1188 */     return localObject;
/*      */   }
/*      */   
/*      */   protected Object _bindAndClose(JsonParser paramJsonParser, Object paramObject)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 1194 */     if (this._schema != null) {
/* 1195 */       paramJsonParser.setSchema(this._schema);
/*      */     }
/*      */     try
/*      */     {
/* 1199 */       JsonToken localJsonToken = _initForReading(paramJsonParser);
/* 1200 */       Object localObject1; Object localObject2; if (localJsonToken == JsonToken.VALUE_NULL) {
/* 1201 */         if (paramObject == null) {
/* 1202 */           localObject1 = createDeserializationContext(paramJsonParser, this._config);
/* 1203 */           localObject2 = _findRootDeserializer((DeserializationContext)localObject1, this._valueType).getNullValue();
/*      */         } else {
/* 1205 */           localObject2 = paramObject;
/*      */         }
/* 1207 */       } else if ((localJsonToken == JsonToken.END_ARRAY) || (localJsonToken == JsonToken.END_OBJECT)) {
/* 1208 */         localObject2 = paramObject;
/*      */       } else {
/* 1210 */         localObject1 = createDeserializationContext(paramJsonParser, this._config);
/* 1211 */         JsonDeserializer localJsonDeserializer = _findRootDeserializer((DeserializationContext)localObject1, this._valueType);
/* 1212 */         if (this._unwrapRoot) {
/* 1213 */           localObject2 = _unwrapAndDeserialize(paramJsonParser, (DeserializationContext)localObject1, this._valueType, localJsonDeserializer);
/*      */         }
/* 1215 */         else if (paramObject == null) {
/* 1216 */           localObject2 = localJsonDeserializer.deserialize(paramJsonParser, (DeserializationContext)localObject1);
/*      */         } else {
/* 1218 */           localJsonDeserializer.deserialize(paramJsonParser, (DeserializationContext)localObject1, paramObject);
/* 1219 */           localObject2 = paramObject;
/*      */         }
/*      */       }
/*      */       
/* 1223 */       return localObject2;
/*      */     } finally {
/*      */       try {
/* 1226 */         paramJsonParser.close();
/*      */       }
/*      */       catch (IOException localIOException2) {}
/*      */     }
/*      */   }
/*      */   
/*      */   protected JsonNode _bindAsTree(JsonParser paramJsonParser)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 1235 */     JsonToken localJsonToken = _initForReading(paramJsonParser);
/* 1236 */     Object localObject; if ((localJsonToken == JsonToken.VALUE_NULL) || (localJsonToken == JsonToken.END_ARRAY) || (localJsonToken == JsonToken.END_OBJECT)) {
/* 1237 */       localObject = NullNode.instance;
/*      */     } else {
/* 1239 */       DefaultDeserializationContext localDefaultDeserializationContext = createDeserializationContext(paramJsonParser, this._config);
/* 1240 */       JsonDeserializer localJsonDeserializer = _findRootDeserializer(localDefaultDeserializationContext, JSON_NODE_TYPE);
/* 1241 */       if (this._unwrapRoot) {
/* 1242 */         localObject = (JsonNode)_unwrapAndDeserialize(paramJsonParser, localDefaultDeserializationContext, JSON_NODE_TYPE, localJsonDeserializer);
/*      */       } else {
/* 1244 */         localObject = (JsonNode)localJsonDeserializer.deserialize(paramJsonParser, localDefaultDeserializationContext);
/*      */       }
/*      */     }
/*      */     
/* 1248 */     paramJsonParser.clearCurrentToken();
/* 1249 */     return (JsonNode)localObject;
/*      */   }
/*      */   
/*      */   protected JsonNode _bindAndCloseAsTree(JsonParser paramJsonParser)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 1255 */     if (this._schema != null) {
/* 1256 */       paramJsonParser.setSchema(this._schema);
/*      */     }
/*      */     try {
/* 1259 */       return _bindAsTree(paramJsonParser);
/*      */     } finally {
/*      */       try {
/* 1262 */         paramJsonParser.close();
/*      */       }
/*      */       catch (IOException localIOException2) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected <T> MappingIterator<T> _bindAndReadValues(JsonParser paramJsonParser, Object paramObject)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1274 */     if (this._schema != null) {
/* 1275 */       paramJsonParser.setSchema(this._schema);
/*      */     }
/* 1277 */     paramJsonParser.nextToken();
/* 1278 */     DefaultDeserializationContext localDefaultDeserializationContext = createDeserializationContext(paramJsonParser, this._config);
/* 1279 */     return new MappingIterator(this._valueType, paramJsonParser, localDefaultDeserializationContext, _findRootDeserializer(localDefaultDeserializationContext, this._valueType), true, this._valueToUpdate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static JsonToken _initForReading(JsonParser paramJsonParser)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 1291 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 1292 */     if (localJsonToken == null) {
/* 1293 */       localJsonToken = paramJsonParser.nextToken();
/* 1294 */       if (localJsonToken == null)
/*      */       {
/*      */ 
/*      */ 
/* 1298 */         throw JsonMappingException.from(paramJsonParser, "No content to map due to end-of-input");
/*      */       }
/*      */     }
/* 1301 */     return localJsonToken;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonDeserializer<Object> _findRootDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType)
/*      */     throws JsonMappingException
/*      */   {
/* 1311 */     if (this._rootDeserializer != null) {
/* 1312 */       return this._rootDeserializer;
/*      */     }
/*      */     
/*      */ 
/* 1316 */     if (paramJavaType == null) {
/* 1317 */       throw new JsonMappingException("No value type configured for ObjectReader");
/*      */     }
/*      */     
/*      */ 
/* 1321 */     JsonDeserializer localJsonDeserializer = (JsonDeserializer)this._rootDeserializers.get(paramJavaType);
/* 1322 */     if (localJsonDeserializer != null) {
/* 1323 */       return localJsonDeserializer;
/*      */     }
/*      */     
/* 1326 */     localJsonDeserializer = paramDeserializationContext.findRootValueDeserializer(paramJavaType);
/* 1327 */     if (localJsonDeserializer == null) {
/* 1328 */       throw new JsonMappingException("Can not find a deserializer for type " + paramJavaType);
/*      */     }
/* 1330 */     this._rootDeserializers.put(paramJavaType, localJsonDeserializer);
/* 1331 */     return localJsonDeserializer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonDeserializer<Object> _prefetchRootDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
/*      */   {
/* 1342 */     if ((paramJavaType == null) || (!this._config.isEnabled(DeserializationFeature.EAGER_DESERIALIZER_FETCH))) {
/* 1343 */       return null;
/*      */     }
/*      */     
/* 1346 */     JsonDeserializer localJsonDeserializer = (JsonDeserializer)this._rootDeserializers.get(paramJavaType);
/* 1347 */     if (localJsonDeserializer == null) {
/*      */       try
/*      */       {
/* 1350 */         DefaultDeserializationContext localDefaultDeserializationContext = createDeserializationContext(null, this._config);
/* 1351 */         localJsonDeserializer = localDefaultDeserializationContext.findRootValueDeserializer(paramJavaType);
/* 1352 */         if (localJsonDeserializer != null) {
/* 1353 */           this._rootDeserializers.put(paramJavaType, localJsonDeserializer);
/*      */         }
/* 1355 */         return localJsonDeserializer;
/*      */       }
/*      */       catch (JsonProcessingException localJsonProcessingException) {}
/*      */     }
/*      */     
/*      */ 
/* 1361 */     return localJsonDeserializer;
/*      */   }
/*      */   
/*      */ 
/*      */   protected Object _unwrapAndDeserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JavaType paramJavaType, JsonDeserializer<Object> paramJsonDeserializer)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 1368 */     String str = this._config.getRootName();
/* 1369 */     if (str == null) {
/* 1370 */       localObject1 = this._rootNames.findRootName(paramJavaType, this._config);
/* 1371 */       str = ((SerializedString)localObject1).getValue();
/*      */     }
/* 1373 */     if (paramJsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
/* 1374 */       throw JsonMappingException.from(paramJsonParser, "Current token not START_OBJECT (needed to unwrap root name '" + str + "'), but " + paramJsonParser.getCurrentToken());
/*      */     }
/*      */     
/* 1377 */     if (paramJsonParser.nextToken() != JsonToken.FIELD_NAME) {
/* 1378 */       throw JsonMappingException.from(paramJsonParser, "Current token not FIELD_NAME (to contain expected root name '" + str + "'), but " + paramJsonParser.getCurrentToken());
/*      */     }
/*      */     
/* 1381 */     Object localObject1 = paramJsonParser.getCurrentName();
/* 1382 */     if (!str.equals(localObject1)) {
/* 1383 */       throw JsonMappingException.from(paramJsonParser, "Root name '" + (String)localObject1 + "' does not match expected ('" + str + "') for type " + paramJavaType);
/*      */     }
/*      */     
/*      */ 
/* 1387 */     paramJsonParser.nextToken();
/*      */     Object localObject2;
/* 1389 */     if (this._valueToUpdate == null) {
/* 1390 */       localObject2 = paramJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*      */     } else {
/* 1392 */       paramJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext, this._valueToUpdate);
/* 1393 */       localObject2 = this._valueToUpdate;
/*      */     }
/*      */     
/* 1396 */     if (paramJsonParser.nextToken() != JsonToken.END_OBJECT) {
/* 1397 */       throw JsonMappingException.from(paramJsonParser, "Current token not END_OBJECT (to match wrapper object with root name '" + str + "'), but " + paramJsonParser.getCurrentToken());
/*      */     }
/*      */     
/* 1400 */     return localObject2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Object _detectBindAndClose(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/* 1411 */     DataFormatReaders.Match localMatch = this._dataFormatReaders.findFormat(paramArrayOfByte, paramInt1, paramInt2);
/* 1412 */     if (!localMatch.hasMatch()) {
/* 1413 */       _reportUnkownFormat(this._dataFormatReaders, localMatch);
/*      */     }
/* 1415 */     JsonParser localJsonParser = localMatch.createParserWithMatch();
/* 1416 */     return localMatch.getReader()._bindAndClose(localJsonParser, this._valueToUpdate);
/*      */   }
/*      */   
/*      */   protected Object _detectBindAndClose(DataFormatReaders.Match paramMatch, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1422 */     if (!paramMatch.hasMatch()) {
/* 1423 */       _reportUnkownFormat(this._dataFormatReaders, paramMatch);
/*      */     }
/* 1425 */     JsonParser localJsonParser = paramMatch.createParserWithMatch();
/*      */     
/*      */ 
/* 1428 */     if (paramBoolean) {
/* 1429 */       localJsonParser.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
/*      */     }
/*      */     
/* 1432 */     return paramMatch.getReader()._bindAndClose(localJsonParser, this._valueToUpdate);
/*      */   }
/*      */   
/*      */   protected <T> MappingIterator<T> _detectBindAndReadValues(DataFormatReaders.Match paramMatch, boolean paramBoolean)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1438 */     if (!paramMatch.hasMatch()) {
/* 1439 */       _reportUnkownFormat(this._dataFormatReaders, paramMatch);
/*      */     }
/* 1441 */     JsonParser localJsonParser = paramMatch.createParserWithMatch();
/*      */     
/*      */ 
/* 1444 */     if (paramBoolean) {
/* 1445 */       localJsonParser.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
/*      */     }
/*      */     
/* 1448 */     return paramMatch.getReader()._bindAndReadValues(localJsonParser, this._valueToUpdate);
/*      */   }
/*      */   
/*      */   protected JsonNode _detectBindAndCloseAsTree(InputStream paramInputStream) throws IOException
/*      */   {
/* 1453 */     DataFormatReaders.Match localMatch = this._dataFormatReaders.findFormat(paramInputStream);
/* 1454 */     if (!localMatch.hasMatch()) {
/* 1455 */       _reportUnkownFormat(this._dataFormatReaders, localMatch);
/*      */     }
/* 1457 */     JsonParser localJsonParser = localMatch.createParserWithMatch();
/* 1458 */     localJsonParser.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
/* 1459 */     return localMatch.getReader()._bindAndCloseAsTree(localJsonParser);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _reportUnkownFormat(DataFormatReaders paramDataFormatReaders, DataFormatReaders.Match paramMatch)
/*      */     throws JsonProcessingException
/*      */   {
/* 1468 */     throw new JsonParseException("Can not detect format from input, does not look like any of detectable formats " + paramDataFormatReaders.toString(), JsonLocation.NA);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _verifySchemaType(FormatSchema paramFormatSchema)
/*      */   {
/* 1484 */     if ((paramFormatSchema != null) && 
/* 1485 */       (!this._jsonFactory.canUseSchema(paramFormatSchema))) {
/* 1486 */       throw new IllegalArgumentException("Can not use FormatSchema of type " + paramFormatSchema.getClass().getName() + " for format " + this._jsonFactory.getFormatName());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected DefaultDeserializationContext createDeserializationContext(JsonParser paramJsonParser, DeserializationConfig paramDeserializationConfig)
/*      */   {
/* 1500 */     return this._context.createInstance(paramDeserializationConfig, paramJsonParser, this._injectableValues);
/*      */   }
/*      */   
/*      */   protected ObjectReader _with(DeserializationConfig paramDeserializationConfig) {
/* 1504 */     if (paramDeserializationConfig == this._config) {
/* 1505 */       return this;
/*      */     }
/* 1507 */     if (this._dataFormatReaders != null) {
/* 1508 */       return new ObjectReader(this, paramDeserializationConfig).withFormatDetection(this._dataFormatReaders.with(paramDeserializationConfig));
/*      */     }
/*      */     
/* 1511 */     return new ObjectReader(this, paramDeserializationConfig);
/*      */   }
/*      */   
/*      */   protected void _reportUndetectableSource(Object paramObject) throws JsonProcessingException
/*      */   {
/* 1516 */     throw new JsonParseException("Can not use source of type " + paramObject.getClass().getName() + " with format auto-detection: must be byte- not char-based", JsonLocation.NA);
/*      */   }
/*      */   
/*      */   protected InputStream _inputStream(URL paramURL)
/*      */     throws IOException
/*      */   {
/* 1522 */     return paramURL.openStream();
/*      */   }
/*      */   
/*      */   protected InputStream _inputStream(File paramFile) throws IOException {
/* 1526 */     return new FileInputStream(paramFile);
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ObjectReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */