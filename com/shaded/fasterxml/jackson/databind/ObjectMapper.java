/*      */ package com.shaded.fasterxml.jackson.databind;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
/*      */ import com.shaded.fasterxml.jackson.annotation.JsonInclude.Include;
/*      */ import com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.As;
/*      */ import com.shaded.fasterxml.jackson.annotation.PropertyAccessor;
/*      */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*      */ import com.shaded.fasterxml.jackson.core.FormatSchema;
/*      */ import com.shaded.fasterxml.jackson.core.JsonEncoding;
/*      */ import com.shaded.fasterxml.jackson.core.JsonFactory;
/*      */ import com.shaded.fasterxml.jackson.core.JsonFactory.Feature;
/*      */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*      */ import com.shaded.fasterxml.jackson.core.JsonGenerator.Feature;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParseException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParser.Feature;
/*      */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*      */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*      */ import com.shaded.fasterxml.jackson.core.PrettyPrinter;
/*      */ import com.shaded.fasterxml.jackson.core.TreeNode;
/*      */ import com.shaded.fasterxml.jackson.core.Version;
/*      */ import com.shaded.fasterxml.jackson.core.io.SegmentedStringWriter;
/*      */ import com.shaded.fasterxml.jackson.core.type.ResolvedType;
/*      */ import com.shaded.fasterxml.jackson.core.type.TypeReference;
/*      */ import com.shaded.fasterxml.jackson.core.util.ByteArrayBuilder;
/*      */ import com.shaded.fasterxml.jackson.databind.cfg.BaseSettings;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.DeserializerFactory;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.Deserializers;
/*      */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiators;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.ClassIntrospector;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*      */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
/*      */ import com.shaded.fasterxml.jackson.databind.jsontype.NamedType;
/*      */ import com.shaded.fasterxml.jackson.databind.jsontype.SubtypeResolver;
/*      */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*      */ import com.shaded.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
/*      */ import com.shaded.fasterxml.jackson.databind.node.JsonNodeFactory;
/*      */ import com.shaded.fasterxml.jackson.databind.node.NullNode;
/*      */ import com.shaded.fasterxml.jackson.databind.ser.BeanSerializerModifier;
/*      */ import com.shaded.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
/*      */ import com.shaded.fasterxml.jackson.databind.ser.FilterProvider;
/*      */ import com.shaded.fasterxml.jackson.databind.ser.SerializerFactory;
/*      */ import com.shaded.fasterxml.jackson.databind.ser.Serializers;
/*      */ import com.shaded.fasterxml.jackson.databind.type.ClassKey;
/*      */ import com.shaded.fasterxml.jackson.databind.type.TypeFactory;
/*      */ import com.shaded.fasterxml.jackson.databind.util.RootNameLookup;
/*      */ import com.shaded.fasterxml.jackson.databind.util.TokenBuffer;
/*      */ import java.io.Closeable;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Type;
/*      */ import java.net.URL;
/*      */ import java.text.DateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.ServiceLoader;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ 
/*      */ public class ObjectMapper extends ObjectCodec implements com.shaded.fasterxml.jackson.core.Versioned, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   
/*      */   public static enum DefaultTyping
/*      */   {
/*   80 */     JAVA_LANG_OBJECT, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   88 */     OBJECT_AND_NON_CONCRETE, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   95 */     NON_CONCRETE_AND_ARRAYS, 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  104 */     NON_FINAL;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private DefaultTyping() {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class DefaultTypeResolverBuilder
/*      */     extends StdTypeResolverBuilder
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */ 
/*      */ 
/*      */     protected final ObjectMapper.DefaultTyping _appliesFor;
/*      */     
/*      */ 
/*      */ 
/*      */     public DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping paramDefaultTyping)
/*      */     {
/*  129 */       this._appliesFor = paramDefaultTyping;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer buildTypeDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, Collection<NamedType> paramCollection)
/*      */     {
/*  136 */       return useForType(paramJavaType) ? super.buildTypeDeserializer(paramDeserializationConfig, paramJavaType, paramCollection) : null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer buildTypeSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, Collection<NamedType> paramCollection)
/*      */     {
/*  143 */       return useForType(paramJavaType) ? super.buildTypeSerializer(paramSerializationConfig, paramJavaType, paramCollection) : null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean useForType(JavaType paramJavaType)
/*      */     {
/*  156 */       switch (ObjectMapper.2.$SwitchMap$com$fasterxml$jackson$databind$ObjectMapper$DefaultTyping[this._appliesFor.ordinal()]) {
/*      */       case 1: 
/*  158 */         while (paramJavaType.isArrayType()) {
/*  159 */           paramJavaType = paramJavaType.getContentType();
/*      */         }
/*      */       
/*      */       case 2: 
/*  163 */         return (paramJavaType.getRawClass() == Object.class) || (!paramJavaType.isConcrete());
/*      */       case 3: 
/*  165 */         while (paramJavaType.isArrayType()) {
/*  166 */           paramJavaType = paramJavaType.getContentType();
/*      */         }
/*  168 */         return !paramJavaType.isFinal();
/*      */       }
/*      */       
/*  171 */       return paramJavaType.getRawClass() == Object.class;
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
/*  183 */   private static final JavaType JSON_NODE_TYPE = com.shaded.fasterxml.jackson.databind.type.SimpleType.constructUnsafe(JsonNode.class);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  188 */   protected static final ClassIntrospector DEFAULT_INTROSPECTOR = com.shaded.fasterxml.jackson.databind.introspect.BasicClassIntrospector.instance;
/*      */   
/*      */ 
/*  191 */   protected static final AnnotationIntrospector DEFAULT_ANNOTATION_INTROSPECTOR = new com.shaded.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector();
/*      */   
/*  193 */   protected static final VisibilityChecker<?> STD_VISIBILITY_CHECKER = com.shaded.fasterxml.jackson.databind.introspect.VisibilityChecker.Std.defaultInstance();
/*      */   
/*  195 */   protected static final PrettyPrinter _defaultPrettyPrinter = new com.shaded.fasterxml.jackson.core.util.DefaultPrettyPrinter();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  201 */   protected static final BaseSettings DEFAULT_BASE = new BaseSettings(DEFAULT_INTROSPECTOR, DEFAULT_ANNOTATION_INTROSPECTOR, STD_VISIBILITY_CHECKER, null, TypeFactory.defaultInstance(), null, com.shaded.fasterxml.jackson.databind.util.StdDateFormat.instance, null, Locale.getDefault(), TimeZone.getTimeZone("GMT"), com.shaded.fasterxml.jackson.core.Base64Variants.getDefaultVariant());
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
/*      */   protected final JsonFactory _jsonFactory;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected TypeFactory _typeFactory;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected InjectableValues _injectableValues;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected SubtypeResolver _subtypeResolver;
/*      */   
/*      */ 
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
/*      */ 
/*  262 */   protected final HashMap<ClassKey, Class<?>> _mixInAnnotations = new HashMap();
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
/*      */   protected SerializationConfig _serializationConfig;
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
/*      */   protected DefaultSerializerProvider _serializerProvider;
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
/*      */   protected SerializerFactory _serializerFactory;
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
/*      */   protected DeserializationConfig _deserializationConfig;
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
/*      */   protected DefaultDeserializationContext _deserializationContext;
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
/*  341 */   protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _rootDeserializers = new ConcurrentHashMap(64, 0.6F, 2);
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
/*      */   public ObjectMapper()
/*      */   {
/*  364 */     this(null, null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper(JsonFactory paramJsonFactory)
/*      */   {
/*  374 */     this(paramJsonFactory, null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ObjectMapper(ObjectMapper paramObjectMapper)
/*      */   {
/*  384 */     this._jsonFactory = paramObjectMapper._jsonFactory.copy();
/*  385 */     this._jsonFactory.setCodec(this);
/*  386 */     this._subtypeResolver = paramObjectMapper._subtypeResolver;
/*  387 */     this._rootNames = new RootNameLookup();
/*  388 */     this._typeFactory = paramObjectMapper._typeFactory;
/*  389 */     this._serializationConfig = paramObjectMapper._serializationConfig;
/*  390 */     HashMap localHashMap = new HashMap(paramObjectMapper._mixInAnnotations);
/*  391 */     this._serializationConfig = new SerializationConfig(paramObjectMapper._serializationConfig, localHashMap);
/*  392 */     this._deserializationConfig = new DeserializationConfig(paramObjectMapper._deserializationConfig, localHashMap);
/*  393 */     this._serializerProvider = paramObjectMapper._serializerProvider;
/*  394 */     this._deserializationContext = paramObjectMapper._deserializationContext;
/*      */     
/*      */ 
/*  397 */     this._serializerFactory = paramObjectMapper._serializerFactory;
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
/*      */   public ObjectMapper(JsonFactory paramJsonFactory, DefaultSerializerProvider paramDefaultSerializerProvider, DefaultDeserializationContext paramDefaultDeserializationContext)
/*      */   {
/*  421 */     if (paramJsonFactory == null) {
/*  422 */       this._jsonFactory = new MappingJsonFactory(this);
/*      */     } else {
/*  424 */       this._jsonFactory = paramJsonFactory;
/*  425 */       if (paramJsonFactory.getCodec() == null) {
/*  426 */         this._jsonFactory.setCodec(this);
/*      */       }
/*      */     }
/*  429 */     this._subtypeResolver = new com.shaded.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver();
/*  430 */     this._rootNames = new RootNameLookup();
/*      */     
/*  432 */     this._typeFactory = TypeFactory.defaultInstance();
/*  433 */     this._serializationConfig = new SerializationConfig(DEFAULT_BASE, this._subtypeResolver, this._mixInAnnotations);
/*      */     
/*  435 */     this._deserializationConfig = new DeserializationConfig(DEFAULT_BASE, this._subtypeResolver, this._mixInAnnotations);
/*      */     
/*  437 */     this._serializerProvider = (paramDefaultSerializerProvider == null ? new com.shaded.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl() : paramDefaultSerializerProvider);
/*  438 */     this._deserializationContext = (paramDefaultDeserializationContext == null ? new com.shaded.fasterxml.jackson.databind.deser.DefaultDeserializationContext.Impl(com.shaded.fasterxml.jackson.databind.deser.BeanDeserializerFactory.instance) : paramDefaultDeserializationContext);
/*      */     
/*      */ 
/*      */ 
/*  442 */     this._serializerFactory = com.shaded.fasterxml.jackson.databind.ser.BeanSerializerFactory.instance;
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
/*      */   public ObjectMapper copy()
/*      */   {
/*  462 */     _checkInvalidCopy(ObjectMapper.class);
/*  463 */     return new ObjectMapper(this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _checkInvalidCopy(Class<?> paramClass)
/*      */   {
/*  472 */     if (getClass() != paramClass) {
/*  473 */       throw new IllegalStateException("Failed copy(): " + getClass().getName() + " (version: " + version() + ") does not override copy(); it has to");
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public Version version()
/*      */   {
/*  490 */     return com.shaded.fasterxml.jackson.databind.cfg.PackageVersion.VERSION;
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
/*      */   public ObjectMapper registerModule(Module paramModule)
/*      */   {
/*  512 */     String str = paramModule.getModuleName();
/*  513 */     if (str == null) {
/*  514 */       throw new IllegalArgumentException("Module without defined name");
/*      */     }
/*  516 */     Version localVersion = paramModule.version();
/*  517 */     if (localVersion == null) {
/*  518 */       throw new IllegalArgumentException("Module without defined version");
/*      */     }
/*      */     
/*  521 */     final ObjectMapper localObjectMapper = this;
/*      */     
/*      */ 
/*  524 */     paramModule.setupModule(new Module.SetupContext()
/*      */     {
/*      */ 
/*      */       public Version getMapperVersion()
/*      */       {
/*      */ 
/*  530 */         return ObjectMapper.this.version();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       public <C extends ObjectCodec> C getOwner()
/*      */       {
/*  537 */         return localObjectMapper;
/*      */       }
/*      */       
/*      */       public TypeFactory getTypeFactory()
/*      */       {
/*  542 */         return ObjectMapper.this._typeFactory;
/*      */       }
/*      */       
/*      */       public boolean isEnabled(MapperFeature paramAnonymousMapperFeature)
/*      */       {
/*  547 */         return localObjectMapper.isEnabled(paramAnonymousMapperFeature);
/*      */       }
/*      */       
/*      */       public boolean isEnabled(DeserializationFeature paramAnonymousDeserializationFeature)
/*      */       {
/*  552 */         return localObjectMapper.isEnabled(paramAnonymousDeserializationFeature);
/*      */       }
/*      */       
/*      */       public boolean isEnabled(SerializationFeature paramAnonymousSerializationFeature)
/*      */       {
/*  557 */         return localObjectMapper.isEnabled(paramAnonymousSerializationFeature);
/*      */       }
/*      */       
/*      */       public boolean isEnabled(JsonFactory.Feature paramAnonymousFeature)
/*      */       {
/*  562 */         return localObjectMapper.isEnabled(paramAnonymousFeature);
/*      */       }
/*      */       
/*      */       public boolean isEnabled(JsonParser.Feature paramAnonymousFeature)
/*      */       {
/*  567 */         return localObjectMapper.isEnabled(paramAnonymousFeature);
/*      */       }
/*      */       
/*      */       public boolean isEnabled(JsonGenerator.Feature paramAnonymousFeature)
/*      */       {
/*  572 */         return localObjectMapper.isEnabled(paramAnonymousFeature);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       public void addDeserializers(Deserializers paramAnonymousDeserializers)
/*      */       {
/*  579 */         DeserializerFactory localDeserializerFactory = localObjectMapper._deserializationContext._factory.withAdditionalDeserializers(paramAnonymousDeserializers);
/*  580 */         localObjectMapper._deserializationContext = localObjectMapper._deserializationContext.with(localDeserializerFactory);
/*      */       }
/*      */       
/*      */       public void addKeyDeserializers(com.shaded.fasterxml.jackson.databind.deser.KeyDeserializers paramAnonymousKeyDeserializers)
/*      */       {
/*  585 */         DeserializerFactory localDeserializerFactory = localObjectMapper._deserializationContext._factory.withAdditionalKeyDeserializers(paramAnonymousKeyDeserializers);
/*  586 */         localObjectMapper._deserializationContext = localObjectMapper._deserializationContext.with(localDeserializerFactory);
/*      */       }
/*      */       
/*      */       public void addBeanDeserializerModifier(BeanDeserializerModifier paramAnonymousBeanDeserializerModifier)
/*      */       {
/*  591 */         DeserializerFactory localDeserializerFactory = localObjectMapper._deserializationContext._factory.withDeserializerModifier(paramAnonymousBeanDeserializerModifier);
/*  592 */         localObjectMapper._deserializationContext = localObjectMapper._deserializationContext.with(localDeserializerFactory);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       public void addSerializers(Serializers paramAnonymousSerializers)
/*      */       {
/*  599 */         localObjectMapper._serializerFactory = localObjectMapper._serializerFactory.withAdditionalSerializers(paramAnonymousSerializers);
/*      */       }
/*      */       
/*      */       public void addKeySerializers(Serializers paramAnonymousSerializers)
/*      */       {
/*  604 */         localObjectMapper._serializerFactory = localObjectMapper._serializerFactory.withAdditionalKeySerializers(paramAnonymousSerializers);
/*      */       }
/*      */       
/*      */       public void addBeanSerializerModifier(BeanSerializerModifier paramAnonymousBeanSerializerModifier)
/*      */       {
/*  609 */         localObjectMapper._serializerFactory = localObjectMapper._serializerFactory.withSerializerModifier(paramAnonymousBeanSerializerModifier);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       public void addAbstractTypeResolver(AbstractTypeResolver paramAnonymousAbstractTypeResolver)
/*      */       {
/*  616 */         DeserializerFactory localDeserializerFactory = localObjectMapper._deserializationContext._factory.withAbstractTypeResolver(paramAnonymousAbstractTypeResolver);
/*  617 */         localObjectMapper._deserializationContext = localObjectMapper._deserializationContext.with(localDeserializerFactory);
/*      */       }
/*      */       
/*      */       public void addTypeModifier(com.shaded.fasterxml.jackson.databind.type.TypeModifier paramAnonymousTypeModifier)
/*      */       {
/*  622 */         TypeFactory localTypeFactory = localObjectMapper._typeFactory;
/*  623 */         localTypeFactory = localTypeFactory.withModifier(paramAnonymousTypeModifier);
/*  624 */         localObjectMapper.setTypeFactory(localTypeFactory);
/*      */       }
/*      */       
/*      */       public void addValueInstantiators(ValueInstantiators paramAnonymousValueInstantiators)
/*      */       {
/*  629 */         DeserializerFactory localDeserializerFactory = localObjectMapper._deserializationContext._factory.withValueInstantiators(paramAnonymousValueInstantiators);
/*  630 */         localObjectMapper._deserializationContext = localObjectMapper._deserializationContext.with(localDeserializerFactory);
/*      */       }
/*      */       
/*      */       public void setClassIntrospector(ClassIntrospector paramAnonymousClassIntrospector)
/*      */       {
/*  635 */         localObjectMapper._deserializationConfig = localObjectMapper._deserializationConfig.with(paramAnonymousClassIntrospector);
/*  636 */         localObjectMapper._serializationConfig = localObjectMapper._serializationConfig.with(paramAnonymousClassIntrospector);
/*      */       }
/*      */       
/*      */       public void insertAnnotationIntrospector(AnnotationIntrospector paramAnonymousAnnotationIntrospector)
/*      */       {
/*  641 */         localObjectMapper._deserializationConfig = localObjectMapper._deserializationConfig.withInsertedAnnotationIntrospector(paramAnonymousAnnotationIntrospector);
/*  642 */         localObjectMapper._serializationConfig = localObjectMapper._serializationConfig.withInsertedAnnotationIntrospector(paramAnonymousAnnotationIntrospector);
/*      */       }
/*      */       
/*      */       public void appendAnnotationIntrospector(AnnotationIntrospector paramAnonymousAnnotationIntrospector)
/*      */       {
/*  647 */         localObjectMapper._deserializationConfig = localObjectMapper._deserializationConfig.withAppendedAnnotationIntrospector(paramAnonymousAnnotationIntrospector);
/*  648 */         localObjectMapper._serializationConfig = localObjectMapper._serializationConfig.withAppendedAnnotationIntrospector(paramAnonymousAnnotationIntrospector);
/*      */       }
/*      */       
/*      */       public void registerSubtypes(Class<?>... paramAnonymousVarArgs)
/*      */       {
/*  653 */         localObjectMapper.registerSubtypes(paramAnonymousVarArgs);
/*      */       }
/*      */       
/*      */       public void registerSubtypes(NamedType... paramAnonymousVarArgs)
/*      */       {
/*  658 */         localObjectMapper.registerSubtypes(paramAnonymousVarArgs);
/*      */       }
/*      */       
/*      */       public void setMixInAnnotations(Class<?> paramAnonymousClass1, Class<?> paramAnonymousClass2)
/*      */       {
/*  663 */         localObjectMapper.addMixInAnnotations(paramAnonymousClass1, paramAnonymousClass2);
/*      */       }
/*      */       
/*      */       public void addDeserializationProblemHandler(DeserializationProblemHandler paramAnonymousDeserializationProblemHandler)
/*      */       {
/*  668 */         localObjectMapper.addHandler(paramAnonymousDeserializationProblemHandler);
/*      */       }
/*  670 */     });
/*  671 */     return this;
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
/*      */   public ObjectMapper registerModules(Module... paramVarArgs)
/*      */   {
/*  687 */     for (Module localModule : paramVarArgs) {
/*  688 */       registerModule(localModule);
/*      */     }
/*  690 */     return this;
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
/*      */   public ObjectMapper registerModules(Iterable<Module> paramIterable)
/*      */   {
/*  706 */     for (Module localModule : paramIterable) {
/*  707 */       registerModule(localModule);
/*      */     }
/*  709 */     return this;
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
/*      */   public static List<Module> findModules()
/*      */   {
/*  722 */     return findModules(null);
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
/*      */   public static List<Module> findModules(ClassLoader paramClassLoader)
/*      */   {
/*  736 */     ArrayList localArrayList = new ArrayList();
/*  737 */     ServiceLoader localServiceLoader = paramClassLoader == null ? ServiceLoader.load(Module.class) : ServiceLoader.load(Module.class, paramClassLoader);
/*      */     
/*  739 */     for (Module localModule : localServiceLoader) {
/*  740 */       localArrayList.add(localModule);
/*      */     }
/*  742 */     return localArrayList;
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
/*      */   public ObjectMapper findAndRegisterModules()
/*      */   {
/*  758 */     return registerModules(findModules());
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
/*      */   public SerializationConfig getSerializationConfig()
/*      */   {
/*  776 */     return this._serializationConfig;
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
/*      */   public DeserializationConfig getDeserializationConfig()
/*      */   {
/*  789 */     return this._deserializationConfig;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public DeserializationContext getDeserializationContext()
/*      */   {
/*  800 */     return this._deserializationContext;
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
/*      */   public ObjectMapper setSerializerFactory(SerializerFactory paramSerializerFactory)
/*      */   {
/*  814 */     this._serializerFactory = paramSerializerFactory;
/*  815 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SerializerFactory getSerializerFactory()
/*      */   {
/*  826 */     return this._serializerFactory;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper setSerializerProvider(DefaultSerializerProvider paramDefaultSerializerProvider)
/*      */   {
/*  834 */     this._serializerProvider = paramDefaultSerializerProvider;
/*  835 */     return this;
/*      */   }
/*      */   
/*      */   public SerializerProvider getSerializerProvider() {
/*  839 */     return this._serializerProvider;
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
/*      */   public final void setMixInAnnotations(Map<Class<?>, Class<?>> paramMap)
/*      */   {
/*  862 */     this._mixInAnnotations.clear();
/*  863 */     if ((paramMap != null) && (paramMap.size() > 0)) {
/*  864 */       for (Map.Entry localEntry : paramMap.entrySet()) {
/*  865 */         this._mixInAnnotations.put(new ClassKey((Class)localEntry.getKey()), localEntry.getValue());
/*      */       }
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
/*      */ 
/*      */ 
/*      */   public final void addMixInAnnotations(Class<?> paramClass1, Class<?> paramClass2)
/*      */   {
/*  882 */     this._mixInAnnotations.put(new ClassKey(paramClass1), paramClass2);
/*      */   }
/*      */   
/*      */   public final Class<?> findMixInClassFor(Class<?> paramClass) {
/*  886 */     return this._mixInAnnotations == null ? null : (Class)this._mixInAnnotations.get(new ClassKey(paramClass));
/*      */   }
/*      */   
/*      */   public final int mixInCount() {
/*  890 */     return this._mixInAnnotations == null ? 0 : this._mixInAnnotations.size();
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
/*      */   public VisibilityChecker<?> getVisibilityChecker()
/*      */   {
/*  905 */     return this._serializationConfig.getDefaultVisibilityChecker();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVisibilityChecker(VisibilityChecker<?> paramVisibilityChecker)
/*      */   {
/*  916 */     this._deserializationConfig = this._deserializationConfig.with(paramVisibilityChecker);
/*  917 */     this._serializationConfig = this._serializationConfig.with(paramVisibilityChecker);
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
/*      */ 
/*      */ 
/*      */   public ObjectMapper setVisibility(PropertyAccessor paramPropertyAccessor, JsonAutoDetect.Visibility paramVisibility)
/*      */   {
/*  946 */     this._deserializationConfig = this._deserializationConfig.withVisibility(paramPropertyAccessor, paramVisibility);
/*  947 */     this._serializationConfig = this._serializationConfig.withVisibility(paramPropertyAccessor, paramVisibility);
/*  948 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public SubtypeResolver getSubtypeResolver()
/*      */   {
/*  955 */     return this._subtypeResolver;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ObjectMapper setSubtypeResolver(SubtypeResolver paramSubtypeResolver)
/*      */   {
/*  962 */     this._subtypeResolver = paramSubtypeResolver;
/*  963 */     this._deserializationConfig = this._deserializationConfig.with(paramSubtypeResolver);
/*  964 */     this._serializationConfig = this._serializationConfig.with(paramSubtypeResolver);
/*  965 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper setAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector)
/*      */   {
/*  973 */     this._serializationConfig = this._serializationConfig.with(paramAnnotationIntrospector);
/*  974 */     this._deserializationConfig = this._deserializationConfig.with(paramAnnotationIntrospector);
/*  975 */     return this;
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
/*      */   public ObjectMapper setAnnotationIntrospectors(AnnotationIntrospector paramAnnotationIntrospector1, AnnotationIntrospector paramAnnotationIntrospector2)
/*      */   {
/*  993 */     this._serializationConfig = this._serializationConfig.with(paramAnnotationIntrospector1);
/*  994 */     this._deserializationConfig = this._deserializationConfig.with(paramAnnotationIntrospector2);
/*  995 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ObjectMapper setPropertyNamingStrategy(PropertyNamingStrategy paramPropertyNamingStrategy)
/*      */   {
/* 1002 */     this._serializationConfig = this._serializationConfig.with(paramPropertyNamingStrategy);
/* 1003 */     this._deserializationConfig = this._deserializationConfig.with(paramPropertyNamingStrategy);
/* 1004 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ObjectMapper setSerializationInclusion(JsonInclude.Include paramInclude)
/*      */   {
/* 1011 */     this._serializationConfig = this._serializationConfig.withSerializationInclusion(paramInclude);
/* 1012 */     return this;
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
/*      */   public ObjectMapper enableDefaultTyping()
/*      */   {
/* 1028 */     return enableDefaultTyping(DefaultTyping.OBJECT_AND_NON_CONCRETE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper enableDefaultTyping(DefaultTyping paramDefaultTyping)
/*      */   {
/* 1038 */     return enableDefaultTyping(paramDefaultTyping, JsonTypeInfo.As.WRAPPER_ARRAY);
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
/*      */   public ObjectMapper enableDefaultTyping(DefaultTyping paramDefaultTyping, JsonTypeInfo.As paramAs)
/*      */   {
/* 1051 */     Object localObject = new DefaultTypeResolverBuilder(paramDefaultTyping);
/*      */     
/* 1053 */     localObject = ((TypeResolverBuilder)localObject).init(com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS, null);
/* 1054 */     localObject = ((TypeResolverBuilder)localObject).inclusion(paramAs);
/* 1055 */     return setDefaultTyping((TypeResolverBuilder)localObject);
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
/*      */   public ObjectMapper enableDefaultTypingAsProperty(DefaultTyping paramDefaultTyping, String paramString)
/*      */   {
/* 1068 */     Object localObject = new DefaultTypeResolverBuilder(paramDefaultTyping);
/*      */     
/* 1070 */     localObject = ((TypeResolverBuilder)localObject).init(com.shaded.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS, null);
/* 1071 */     localObject = ((TypeResolverBuilder)localObject).inclusion(JsonTypeInfo.As.PROPERTY);
/* 1072 */     localObject = ((TypeResolverBuilder)localObject).typeProperty(paramString);
/* 1073 */     return setDefaultTyping((TypeResolverBuilder)localObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper disableDefaultTyping()
/*      */   {
/* 1083 */     return setDefaultTyping(null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper setDefaultTyping(TypeResolverBuilder<?> paramTypeResolverBuilder)
/*      */   {
/* 1094 */     this._deserializationConfig = this._deserializationConfig.with(paramTypeResolverBuilder);
/* 1095 */     this._serializationConfig = this._serializationConfig.with(paramTypeResolverBuilder);
/* 1096 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void registerSubtypes(Class<?>... paramVarArgs)
/*      */   {
/* 1107 */     getSubtypeResolver().registerSubtypes(paramVarArgs);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void registerSubtypes(NamedType... paramVarArgs)
/*      */   {
/* 1119 */     getSubtypeResolver().registerSubtypes(paramVarArgs);
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
/*      */   public TypeFactory getTypeFactory()
/*      */   {
/* 1132 */     return this._typeFactory;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper setTypeFactory(TypeFactory paramTypeFactory)
/*      */   {
/* 1144 */     this._typeFactory = paramTypeFactory;
/* 1145 */     this._deserializationConfig = this._deserializationConfig.with(paramTypeFactory);
/* 1146 */     this._serializationConfig = this._serializationConfig.with(paramTypeFactory);
/* 1147 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JavaType constructType(Type paramType)
/*      */   {
/* 1156 */     return this._typeFactory.constructType(paramType);
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
/*      */   public ObjectMapper setNodeFactory(JsonNodeFactory paramJsonNodeFactory)
/*      */   {
/* 1171 */     this._deserializationConfig = this._deserializationConfig.with(paramJsonNodeFactory);
/* 1172 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper addHandler(DeserializationProblemHandler paramDeserializationProblemHandler)
/*      */   {
/* 1180 */     this._deserializationConfig = this._deserializationConfig.withHandler(paramDeserializationProblemHandler);
/* 1181 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper clearProblemHandlers()
/*      */   {
/* 1189 */     this._deserializationConfig = this._deserializationConfig.withNoProblemHandlers();
/* 1190 */     return this;
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
/*      */   public void setFilters(FilterProvider paramFilterProvider)
/*      */   {
/* 1212 */     this._serializationConfig = this._serializationConfig.withFilters(paramFilterProvider);
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
/*      */   public ObjectMapper setBase64Variant(Base64Variant paramBase64Variant)
/*      */   {
/* 1226 */     this._serializationConfig = this._serializationConfig.with(paramBase64Variant);
/* 1227 */     this._deserializationConfig = this._deserializationConfig.with(paramBase64Variant);
/* 1228 */     return this;
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
/*      */   public JsonFactory getFactory()
/*      */   {
/* 1246 */     return this._jsonFactory;
/*      */   }
/*      */   
/*      */ 
/*      */   @Deprecated
/*      */   public JsonFactory getJsonFactory()
/*      */   {
/* 1253 */     return this._jsonFactory;
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
/*      */   public ObjectMapper setDateFormat(DateFormat paramDateFormat)
/*      */   {
/* 1267 */     this._deserializationConfig = this._deserializationConfig.with(paramDateFormat);
/* 1268 */     this._serializationConfig = this._serializationConfig.with(paramDateFormat);
/* 1269 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object setHandlerInstantiator(com.shaded.fasterxml.jackson.databind.cfg.HandlerInstantiator paramHandlerInstantiator)
/*      */   {
/* 1281 */     this._deserializationConfig = this._deserializationConfig.with(paramHandlerInstantiator);
/* 1282 */     this._serializationConfig = this._serializationConfig.with(paramHandlerInstantiator);
/* 1283 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper setInjectableValues(InjectableValues paramInjectableValues)
/*      */   {
/* 1291 */     this._injectableValues = paramInjectableValues;
/* 1292 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper setLocale(Locale paramLocale)
/*      */   {
/* 1300 */     this._deserializationConfig = this._deserializationConfig.with(paramLocale);
/* 1301 */     this._serializationConfig = this._serializationConfig.with(paramLocale);
/* 1302 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper setTimeZone(TimeZone paramTimeZone)
/*      */   {
/* 1310 */     this._deserializationConfig = this._deserializationConfig.with(paramTimeZone);
/* 1311 */     this._serializationConfig = this._serializationConfig.with(paramTimeZone);
/* 1312 */     return this;
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
/*      */   public ObjectMapper configure(MapperFeature paramMapperFeature, boolean paramBoolean)
/*      */   {
/* 1326 */     this._serializationConfig = (paramBoolean ? this._serializationConfig.with(new MapperFeature[] { paramMapperFeature }) : this._serializationConfig.without(new MapperFeature[] { paramMapperFeature }));
/*      */     
/* 1328 */     this._deserializationConfig = (paramBoolean ? this._deserializationConfig.with(new MapperFeature[] { paramMapperFeature }) : this._deserializationConfig.without(new MapperFeature[] { paramMapperFeature }));
/*      */     
/* 1330 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper configure(SerializationFeature paramSerializationFeature, boolean paramBoolean)
/*      */   {
/* 1338 */     this._serializationConfig = (paramBoolean ? this._serializationConfig.with(paramSerializationFeature) : this._serializationConfig.without(paramSerializationFeature));
/*      */     
/* 1340 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper configure(DeserializationFeature paramDeserializationFeature, boolean paramBoolean)
/*      */   {
/* 1348 */     this._deserializationConfig = (paramBoolean ? this._deserializationConfig.with(paramDeserializationFeature) : this._deserializationConfig.without(paramDeserializationFeature));
/*      */     
/* 1350 */     return this;
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
/*      */   public ObjectMapper configure(JsonParser.Feature paramFeature, boolean paramBoolean)
/*      */   {
/* 1363 */     this._jsonFactory.configure(paramFeature, paramBoolean);
/* 1364 */     return this;
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
/*      */   public ObjectMapper configure(JsonGenerator.Feature paramFeature, boolean paramBoolean)
/*      */   {
/* 1377 */     this._jsonFactory.configure(paramFeature, paramBoolean);
/* 1378 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper enable(MapperFeature... paramVarArgs)
/*      */   {
/* 1386 */     this._deserializationConfig = this._deserializationConfig.with(paramVarArgs);
/* 1387 */     this._serializationConfig = this._serializationConfig.with(paramVarArgs);
/* 1388 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper disable(MapperFeature... paramVarArgs)
/*      */   {
/* 1396 */     this._deserializationConfig = this._deserializationConfig.without(paramVarArgs);
/* 1397 */     this._serializationConfig = this._serializationConfig.without(paramVarArgs);
/* 1398 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper enable(DeserializationFeature paramDeserializationFeature)
/*      */   {
/* 1406 */     this._deserializationConfig = this._deserializationConfig.with(paramDeserializationFeature);
/* 1407 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper enable(DeserializationFeature paramDeserializationFeature, DeserializationFeature... paramVarArgs)
/*      */   {
/* 1416 */     this._deserializationConfig = this._deserializationConfig.with(paramDeserializationFeature, paramVarArgs);
/* 1417 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper disable(DeserializationFeature paramDeserializationFeature)
/*      */   {
/* 1425 */     this._deserializationConfig = this._deserializationConfig.without(paramDeserializationFeature);
/* 1426 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper disable(DeserializationFeature paramDeserializationFeature, DeserializationFeature... paramVarArgs)
/*      */   {
/* 1435 */     this._deserializationConfig = this._deserializationConfig.without(paramDeserializationFeature, paramVarArgs);
/* 1436 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper enable(SerializationFeature paramSerializationFeature)
/*      */   {
/* 1444 */     this._serializationConfig = this._serializationConfig.with(paramSerializationFeature);
/* 1445 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper enable(SerializationFeature paramSerializationFeature, SerializationFeature... paramVarArgs)
/*      */   {
/* 1454 */     this._serializationConfig = this._serializationConfig.with(paramSerializationFeature, paramVarArgs);
/* 1455 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper disable(SerializationFeature paramSerializationFeature)
/*      */   {
/* 1463 */     this._serializationConfig = this._serializationConfig.without(paramSerializationFeature);
/* 1464 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectMapper disable(SerializationFeature paramSerializationFeature, SerializationFeature... paramVarArgs)
/*      */   {
/* 1473 */     this._serializationConfig = this._serializationConfig.without(paramSerializationFeature, paramVarArgs);
/* 1474 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEnabled(MapperFeature paramMapperFeature)
/*      */   {
/* 1483 */     return this._serializationConfig.isEnabled(paramMapperFeature);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEnabled(SerializationFeature paramSerializationFeature)
/*      */   {
/* 1491 */     return this._serializationConfig.isEnabled(paramSerializationFeature);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEnabled(DeserializationFeature paramDeserializationFeature)
/*      */   {
/* 1499 */     return this._deserializationConfig.isEnabled(paramDeserializationFeature);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEnabled(JsonFactory.Feature paramFeature)
/*      */   {
/* 1509 */     return this._jsonFactory.isEnabled(paramFeature);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEnabled(JsonParser.Feature paramFeature)
/*      */   {
/* 1519 */     return this._jsonFactory.isEnabled(paramFeature);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEnabled(JsonGenerator.Feature paramFeature)
/*      */   {
/* 1529 */     return this._jsonFactory.isEnabled(paramFeature);
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
/*      */   public JsonNodeFactory getNodeFactory()
/*      */   {
/* 1543 */     return this._deserializationConfig.getNodeFactory();
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
/*      */   public <T> T readValue(JsonParser paramJsonParser, Class<T> paramClass)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 1569 */     return (T)_readValue(getDeserializationConfig(), paramJsonParser, this._typeFactory.constructType(paramClass));
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
/*      */   public <T> T readValue(JsonParser paramJsonParser, TypeReference<?> paramTypeReference)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 1584 */     return (T)_readValue(getDeserializationConfig(), paramJsonParser, this._typeFactory.constructType(paramTypeReference));
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
/*      */   public final <T> T readValue(JsonParser paramJsonParser, ResolvedType paramResolvedType)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 1598 */     return (T)_readValue(getDeserializationConfig(), paramJsonParser, (JavaType)paramResolvedType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(JsonParser paramJsonParser, JavaType paramJavaType)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 1608 */     return (T)_readValue(getDeserializationConfig(), paramJsonParser, paramJavaType);
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
/*      */   public <T extends TreeNode> T readTree(JsonParser paramJsonParser)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1629 */     DeserializationConfig localDeserializationConfig = getDeserializationConfig();
/* 1630 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 1631 */     if (localJsonToken == null) {
/* 1632 */       localJsonToken = paramJsonParser.nextToken();
/* 1633 */       if (localJsonToken == null) {
/* 1634 */         return null;
/*      */       }
/*      */     }
/* 1637 */     Object localObject1 = (JsonNode)_readValue(localDeserializationConfig, paramJsonParser, JSON_NODE_TYPE);
/* 1638 */     if (localObject1 == null) {
/* 1639 */       localObject1 = getNodeFactory().nullNode();
/*      */     }
/*      */     
/* 1642 */     Object localObject2 = localObject1;
/* 1643 */     return (T)localObject2;
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
/*      */   public <T> MappingIterator<T> readValues(JsonParser paramJsonParser, ResolvedType paramResolvedType)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1662 */     return readValues(paramJsonParser, (JavaType)paramResolvedType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> MappingIterator<T> readValues(JsonParser paramJsonParser, JavaType paramJavaType)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1671 */     DeserializationConfig localDeserializationConfig = getDeserializationConfig();
/* 1672 */     DefaultDeserializationContext localDefaultDeserializationContext = createDeserializationContext(paramJsonParser, localDeserializationConfig);
/* 1673 */     JsonDeserializer localJsonDeserializer = _findRootDeserializer(localDefaultDeserializationContext, paramJavaType);
/*      */     
/* 1675 */     return new MappingIterator(paramJavaType, paramJsonParser, localDefaultDeserializationContext, localJsonDeserializer, false, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> MappingIterator<T> readValues(JsonParser paramJsonParser, Class<T> paramClass)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1686 */     return readValues(paramJsonParser, this._typeFactory.constructType(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> MappingIterator<T> readValues(JsonParser paramJsonParser, TypeReference<?> paramTypeReference)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1696 */     return readValues(paramJsonParser, this._typeFactory.constructType(paramTypeReference));
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
/*      */   public JsonNode readTree(InputStream paramInputStream)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1719 */     JsonNode localJsonNode = (JsonNode)_readMapAndClose(this._jsonFactory.createParser(paramInputStream), JSON_NODE_TYPE);
/* 1720 */     return localJsonNode == null ? NullNode.instance : localJsonNode;
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
/*      */   public JsonNode readTree(Reader paramReader)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1736 */     JsonNode localJsonNode = (JsonNode)_readMapAndClose(this._jsonFactory.createParser(paramReader), JSON_NODE_TYPE);
/* 1737 */     return localJsonNode == null ? NullNode.instance : localJsonNode;
/*      */   }
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
/* 1750 */     JsonNode localJsonNode = (JsonNode)_readMapAndClose(this._jsonFactory.createParser(paramString), JSON_NODE_TYPE);
/* 1751 */     return localJsonNode == null ? NullNode.instance : localJsonNode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonNode readTree(byte[] paramArrayOfByte)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1764 */     JsonNode localJsonNode = (JsonNode)_readMapAndClose(this._jsonFactory.createParser(paramArrayOfByte), JSON_NODE_TYPE);
/* 1765 */     return localJsonNode == null ? NullNode.instance : localJsonNode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonNode readTree(File paramFile)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1778 */     JsonNode localJsonNode = (JsonNode)_readMapAndClose(this._jsonFactory.createParser(paramFile), JSON_NODE_TYPE);
/* 1779 */     return localJsonNode == null ? NullNode.instance : localJsonNode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonNode readTree(URL paramURL)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1792 */     JsonNode localJsonNode = (JsonNode)_readMapAndClose(this._jsonFactory.createParser(paramURL), JSON_NODE_TYPE);
/* 1793 */     return localJsonNode == null ? NullNode.instance : localJsonNode;
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
/*      */   public void writeValue(JsonGenerator paramJsonGenerator, Object paramObject)
/*      */     throws IOException, JsonGenerationException, JsonMappingException
/*      */   {
/* 1811 */     SerializationConfig localSerializationConfig = getSerializationConfig();
/*      */     
/* 1813 */     if (localSerializationConfig.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
/* 1814 */       paramJsonGenerator.useDefaultPrettyPrinter();
/*      */     }
/* 1816 */     if ((localSerializationConfig.isEnabled(SerializationFeature.CLOSE_CLOSEABLE)) && ((paramObject instanceof Closeable))) {
/* 1817 */       _writeCloseableValue(paramJsonGenerator, paramObject, localSerializationConfig);
/*      */     } else {
/* 1819 */       _serializerProvider(localSerializationConfig).serializeValue(paramJsonGenerator, paramObject);
/* 1820 */       if (localSerializationConfig.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
/* 1821 */         paramJsonGenerator.flush();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeTree(JsonGenerator paramJsonGenerator, JsonNode paramJsonNode)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/* 1833 */     SerializationConfig localSerializationConfig = getSerializationConfig();
/* 1834 */     _serializerProvider(localSerializationConfig).serializeValue(paramJsonGenerator, paramJsonNode);
/* 1835 */     if (localSerializationConfig.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
/* 1836 */       paramJsonGenerator.flush();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public com.shaded.fasterxml.jackson.databind.node.ObjectNode createObjectNode()
/*      */   {
/* 1855 */     return this._deserializationConfig.getNodeFactory().objectNode();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public com.shaded.fasterxml.jackson.databind.node.ArrayNode createArrayNode()
/*      */   {
/* 1867 */     return this._deserializationConfig.getNodeFactory().arrayNode();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonParser treeAsTokens(TreeNode paramTreeNode)
/*      */   {
/* 1879 */     return new com.shaded.fasterxml.jackson.databind.node.TreeTraversingParser((JsonNode)paramTreeNode, this);
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
/*      */   public <T> T treeToValue(TreeNode paramTreeNode, Class<T> paramClass)
/*      */     throws JsonProcessingException
/*      */   {
/*      */     try
/*      */     {
/* 1899 */       if ((paramClass != Object.class) && (paramClass.isAssignableFrom(paramTreeNode.getClass()))) {
/* 1900 */         return paramTreeNode;
/*      */       }
/* 1902 */       return (T)readValue(treeAsTokens(paramTreeNode), paramClass);
/*      */     } catch (JsonProcessingException localJsonProcessingException) {
/* 1904 */       throw localJsonProcessingException;
/*      */     } catch (IOException localIOException) {
/* 1906 */       throw new IllegalArgumentException(localIOException.getMessage(), localIOException);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T extends JsonNode> T valueToTree(Object paramObject)
/*      */     throws IllegalArgumentException
/*      */   {
/* 1925 */     if (paramObject == null) return null;
/* 1926 */     TokenBuffer localTokenBuffer = new TokenBuffer(this);
/*      */     JsonNode localJsonNode;
/*      */     try {
/* 1929 */       writeValue(localTokenBuffer, paramObject);
/* 1930 */       JsonParser localJsonParser = localTokenBuffer.asParser();
/* 1931 */       localJsonNode = (JsonNode)readTree(localJsonParser);
/* 1932 */       localJsonParser.close();
/*      */     } catch (IOException localIOException) {
/* 1934 */       throw new IllegalArgumentException(localIOException.getMessage(), localIOException);
/*      */     }
/* 1936 */     return localJsonNode;
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
/*      */   public boolean canSerialize(Class<?> paramClass)
/*      */   {
/* 1956 */     return _serializerProvider(getSerializationConfig()).hasSerializerFor(paramClass);
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
/*      */   public boolean canDeserialize(JavaType paramJavaType)
/*      */   {
/* 1971 */     return createDeserializationContext(null, getDeserializationConfig()).hasValueDeserializerFor(paramJavaType);
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
/*      */   public <T> T readValue(File paramFile, Class<T> paramClass)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 1988 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramFile), this._typeFactory.constructType(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(File paramFile, TypeReference paramTypeReference)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 1995 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramFile), this._typeFactory.constructType(paramTypeReference));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(File paramFile, JavaType paramJavaType)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2002 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramFile), paramJavaType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(URL paramURL, Class<T> paramClass)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2011 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramURL), this._typeFactory.constructType(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(URL paramURL, TypeReference paramTypeReference)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2018 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramURL), this._typeFactory.constructType(paramTypeReference));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(URL paramURL, JavaType paramJavaType)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2025 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramURL), paramJavaType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(String paramString, Class<T> paramClass)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2034 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramString), this._typeFactory.constructType(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(String paramString, TypeReference paramTypeReference)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2041 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramString), this._typeFactory.constructType(paramTypeReference));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(String paramString, JavaType paramJavaType)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2048 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramString), paramJavaType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(Reader paramReader, Class<T> paramClass)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2057 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramReader), this._typeFactory.constructType(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(Reader paramReader, TypeReference paramTypeReference)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2064 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramReader), this._typeFactory.constructType(paramTypeReference));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(Reader paramReader, JavaType paramJavaType)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2071 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramReader), paramJavaType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(InputStream paramInputStream, Class<T> paramClass)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2080 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramInputStream), this._typeFactory.constructType(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(InputStream paramInputStream, TypeReference paramTypeReference)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2087 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramInputStream), this._typeFactory.constructType(paramTypeReference));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(InputStream paramInputStream, JavaType paramJavaType)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2094 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramInputStream), paramJavaType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(byte[] paramArrayOfByte, Class<T> paramClass)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2103 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramArrayOfByte), this._typeFactory.constructType(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> T readValue(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Class<T> paramClass)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2113 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramArrayOfByte, paramInt1, paramInt2), this._typeFactory.constructType(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(byte[] paramArrayOfByte, TypeReference paramTypeReference)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2120 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramArrayOfByte), this._typeFactory.constructType(paramTypeReference));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public <T> T readValue(byte[] paramArrayOfByte, int paramInt1, int paramInt2, TypeReference paramTypeReference)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2128 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramArrayOfByte, paramInt1, paramInt2), this._typeFactory.constructType(paramTypeReference));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T readValue(byte[] paramArrayOfByte, JavaType paramJavaType)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2135 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramArrayOfByte), paramJavaType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public <T> T readValue(byte[] paramArrayOfByte, int paramInt1, int paramInt2, JavaType paramJavaType)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2143 */     return (T)_readMapAndClose(this._jsonFactory.createParser(paramArrayOfByte, paramInt1, paramInt2), paramJavaType);
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
/*      */   public void writeValue(File paramFile, Object paramObject)
/*      */     throws IOException, JsonGenerationException, JsonMappingException
/*      */   {
/* 2160 */     _configAndWriteValue(this._jsonFactory.createGenerator(paramFile, JsonEncoding.UTF8), paramObject);
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
/*      */   public void writeValue(java.io.OutputStream paramOutputStream, Object paramObject)
/*      */     throws IOException, JsonGenerationException, JsonMappingException
/*      */   {
/* 2177 */     _configAndWriteValue(this._jsonFactory.createGenerator(paramOutputStream, JsonEncoding.UTF8), paramObject);
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
/*      */   public void writeValue(Writer paramWriter, Object paramObject)
/*      */     throws IOException, JsonGenerationException, JsonMappingException
/*      */   {
/* 2193 */     _configAndWriteValue(this._jsonFactory.createGenerator(paramWriter), paramObject);
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
/*      */   public String writeValueAsString(Object paramObject)
/*      */     throws JsonProcessingException
/*      */   {
/* 2208 */     SegmentedStringWriter localSegmentedStringWriter = new SegmentedStringWriter(this._jsonFactory._getBufferRecycler());
/*      */     try {
/* 2210 */       _configAndWriteValue(this._jsonFactory.createGenerator(localSegmentedStringWriter), paramObject);
/*      */     } catch (JsonProcessingException localJsonProcessingException) {
/* 2212 */       throw localJsonProcessingException;
/*      */     } catch (IOException localIOException) {
/* 2214 */       throw JsonMappingException.fromUnexpectedIOE(localIOException);
/*      */     }
/* 2216 */     return localSegmentedStringWriter.getAndClear();
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
/*      */   public byte[] writeValueAsBytes(Object paramObject)
/*      */     throws JsonProcessingException
/*      */   {
/* 2231 */     ByteArrayBuilder localByteArrayBuilder = new ByteArrayBuilder(this._jsonFactory._getBufferRecycler());
/*      */     try {
/* 2233 */       _configAndWriteValue(this._jsonFactory.createGenerator(localByteArrayBuilder, JsonEncoding.UTF8), paramObject);
/*      */     } catch (JsonProcessingException localJsonProcessingException) {
/* 2235 */       throw localJsonProcessingException;
/*      */     } catch (IOException localIOException) {
/* 2237 */       throw JsonMappingException.fromUnexpectedIOE(localIOException);
/*      */     }
/* 2239 */     byte[] arrayOfByte = localByteArrayBuilder.toByteArray();
/* 2240 */     localByteArrayBuilder.release();
/* 2241 */     return arrayOfByte;
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
/*      */   public ObjectWriter writer()
/*      */   {
/* 2256 */     return new ObjectWriter(this, getSerializationConfig());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectWriter writer(SerializationFeature paramSerializationFeature)
/*      */   {
/* 2265 */     return new ObjectWriter(this, getSerializationConfig().with(paramSerializationFeature));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectWriter writer(SerializationFeature paramSerializationFeature, SerializationFeature... paramVarArgs)
/*      */   {
/* 2275 */     return new ObjectWriter(this, getSerializationConfig().with(paramSerializationFeature, paramVarArgs));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectWriter writer(DateFormat paramDateFormat)
/*      */   {
/* 2284 */     return new ObjectWriter(this, getSerializationConfig().with(paramDateFormat));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectWriter writerWithView(Class<?> paramClass)
/*      */   {
/* 2293 */     return new ObjectWriter(this, getSerializationConfig().withView(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectWriter writerWithType(Class<?> paramClass)
/*      */   {
/* 2303 */     return new ObjectWriter(this, getSerializationConfig(), paramClass == null ? null : this._typeFactory.constructType(paramClass), null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectWriter writerWithType(TypeReference<?> paramTypeReference)
/*      */   {
/* 2315 */     return new ObjectWriter(this, getSerializationConfig(), paramTypeReference == null ? null : this._typeFactory.constructType(paramTypeReference), null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectWriter writerWithType(JavaType paramJavaType)
/*      */   {
/* 2327 */     return new ObjectWriter(this, getSerializationConfig(), paramJavaType, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectWriter writer(PrettyPrinter paramPrettyPrinter)
/*      */   {
/* 2336 */     if (paramPrettyPrinter == null) {
/* 2337 */       paramPrettyPrinter = ObjectWriter.NULL_PRETTY_PRINTER;
/*      */     }
/* 2339 */     return new ObjectWriter(this, getSerializationConfig(), null, paramPrettyPrinter);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectWriter writerWithDefaultPrettyPrinter()
/*      */   {
/* 2347 */     return new ObjectWriter(this, getSerializationConfig(), null, _defaultPrettyPrinter());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectWriter writer(FilterProvider paramFilterProvider)
/*      */   {
/* 2356 */     return new ObjectWriter(this, getSerializationConfig().withFilters(paramFilterProvider));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectWriter writer(FormatSchema paramFormatSchema)
/*      */   {
/* 2368 */     _verifySchemaType(paramFormatSchema);
/* 2369 */     return new ObjectWriter(this, getSerializationConfig(), paramFormatSchema);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectWriter writer(Base64Variant paramBase64Variant)
/*      */   {
/* 2379 */     return new ObjectWriter(this, getSerializationConfig().with(paramBase64Variant));
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
/*      */   public ObjectReader reader()
/*      */   {
/* 2395 */     return new ObjectReader(this, getDeserializationConfig()).with(this._injectableValues);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader reader(DeserializationFeature paramDeserializationFeature)
/*      */   {
/* 2407 */     return new ObjectReader(this, getDeserializationConfig().with(paramDeserializationFeature));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader reader(DeserializationFeature paramDeserializationFeature, DeserializationFeature... paramVarArgs)
/*      */   {
/* 2419 */     return new ObjectReader(this, getDeserializationConfig().with(paramDeserializationFeature, paramVarArgs));
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
/*      */   public ObjectReader readerForUpdating(Object paramObject)
/*      */   {
/* 2434 */     JavaType localJavaType = this._typeFactory.constructType(paramObject.getClass());
/* 2435 */     return new ObjectReader(this, getDeserializationConfig(), localJavaType, paramObject, null, this._injectableValues);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader reader(JavaType paramJavaType)
/*      */   {
/* 2445 */     return new ObjectReader(this, getDeserializationConfig(), paramJavaType, null, null, this._injectableValues);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader reader(Class<?> paramClass)
/*      */   {
/* 2455 */     return reader(this._typeFactory.constructType(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader reader(TypeReference<?> paramTypeReference)
/*      */   {
/* 2464 */     return reader(this._typeFactory.constructType(paramTypeReference));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader reader(JsonNodeFactory paramJsonNodeFactory)
/*      */   {
/* 2473 */     return new ObjectReader(this, getDeserializationConfig()).with(paramJsonNodeFactory);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader reader(FormatSchema paramFormatSchema)
/*      */   {
/* 2484 */     _verifySchemaType(paramFormatSchema);
/* 2485 */     return new ObjectReader(this, getDeserializationConfig(), null, null, paramFormatSchema, this._injectableValues);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader reader(InjectableValues paramInjectableValues)
/*      */   {
/* 2496 */     return new ObjectReader(this, getDeserializationConfig(), null, null, null, paramInjectableValues);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader readerWithView(Class<?> paramClass)
/*      */   {
/* 2505 */     return new ObjectReader(this, getDeserializationConfig().withView(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectReader reader(Base64Variant paramBase64Variant)
/*      */   {
/* 2515 */     return new ObjectReader(this, getDeserializationConfig().with(paramBase64Variant));
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
/*      */   public <T> T convertValue(Object paramObject, Class<T> paramClass)
/*      */     throws IllegalArgumentException
/*      */   {
/* 2541 */     if (paramObject == null) return null;
/* 2542 */     return (T)_convert(paramObject, this._typeFactory.constructType(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */   public <T> T convertValue(Object paramObject, TypeReference<?> paramTypeReference)
/*      */     throws IllegalArgumentException
/*      */   {
/* 2549 */     return (T)convertValue(paramObject, this._typeFactory.constructType(paramTypeReference));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public <T> T convertValue(Object paramObject, JavaType paramJavaType)
/*      */     throws IllegalArgumentException
/*      */   {
/* 2557 */     if (paramObject == null) return null;
/* 2558 */     return (T)_convert(paramObject, paramJavaType);
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
/*      */   protected Object _convert(Object paramObject, JavaType paramJavaType)
/*      */     throws IllegalArgumentException
/*      */   {
/* 2577 */     Class localClass = paramJavaType.getRawClass();
/* 2578 */     if ((localClass != Object.class) && (!paramJavaType.hasGenericTypes()) && (localClass.isAssignableFrom(paramObject.getClass())))
/*      */     {
/*      */ 
/* 2581 */       return paramObject;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2587 */     TokenBuffer localTokenBuffer = new TokenBuffer(this);
/*      */     
/*      */     try
/*      */     {
/* 2591 */       SerializationConfig localSerializationConfig = getSerializationConfig().without(SerializationFeature.WRAP_ROOT_VALUE);
/*      */       
/* 2593 */       _serializerProvider(localSerializationConfig).serializeValue(localTokenBuffer, paramObject);
/*      */       
/*      */ 
/* 2596 */       JsonParser localJsonParser = localTokenBuffer.asParser();
/*      */       
/*      */ 
/* 2599 */       DeserializationConfig localDeserializationConfig = getDeserializationConfig();
/* 2600 */       JsonToken localJsonToken = _initForReading(localJsonParser);
/* 2601 */       DefaultDeserializationContext localDefaultDeserializationContext; Object localObject; if (localJsonToken == JsonToken.VALUE_NULL) {
/* 2602 */         localDefaultDeserializationContext = createDeserializationContext(localJsonParser, localDeserializationConfig);
/* 2603 */         localObject = _findRootDeserializer(localDefaultDeserializationContext, paramJavaType).getNullValue();
/* 2604 */       } else if ((localJsonToken == JsonToken.END_ARRAY) || (localJsonToken == JsonToken.END_OBJECT)) {
/* 2605 */         localObject = null;
/*      */       } else {
/* 2607 */         localDefaultDeserializationContext = createDeserializationContext(localJsonParser, localDeserializationConfig);
/* 2608 */         JsonDeserializer localJsonDeserializer = _findRootDeserializer(localDefaultDeserializationContext, paramJavaType);
/*      */         
/* 2610 */         localObject = localJsonDeserializer.deserialize(localJsonParser, localDefaultDeserializationContext);
/*      */       }
/* 2612 */       localJsonParser.close();
/* 2613 */       return localObject;
/*      */     } catch (IOException localIOException) {
/* 2615 */       throw new IllegalArgumentException(localIOException.getMessage(), localIOException);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public com.shaded.fasterxml.jackson.databind.jsonschema.JsonSchema generateJsonSchema(Class<?> paramClass)
/*      */     throws JsonMappingException
/*      */   {
/* 2635 */     return _serializerProvider(getSerializationConfig()).generateJsonSchema(paramClass);
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
/*      */   public void acceptJsonFormatVisitor(Class<?> paramClass, JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper)
/*      */     throws JsonMappingException
/*      */   {
/* 2652 */     acceptJsonFormatVisitor(this._typeFactory.constructType(paramClass), paramJsonFormatVisitorWrapper);
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
/*      */   public void acceptJsonFormatVisitor(JavaType paramJavaType, JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper)
/*      */     throws JsonMappingException
/*      */   {
/* 2670 */     if (paramJavaType == null) {
/* 2671 */       throw new IllegalArgumentException("type must be provided");
/*      */     }
/* 2673 */     _serializerProvider(getSerializationConfig()).acceptJsonFormatVisitor(paramJavaType, paramJsonFormatVisitorWrapper);
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
/*      */   protected DefaultSerializerProvider _serializerProvider(SerializationConfig paramSerializationConfig)
/*      */   {
/* 2687 */     return this._serializerProvider.createInstance(paramSerializationConfig, this._serializerFactory);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PrettyPrinter _defaultPrettyPrinter()
/*      */   {
/* 2696 */     return _defaultPrettyPrinter;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void _configAndWriteValue(JsonGenerator paramJsonGenerator, Object paramObject)
/*      */     throws IOException, JsonGenerationException, JsonMappingException
/*      */   {
/* 2706 */     SerializationConfig localSerializationConfig = getSerializationConfig();
/*      */     
/* 2708 */     if (localSerializationConfig.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
/* 2709 */       paramJsonGenerator.useDefaultPrettyPrinter();
/*      */     }
/*      */     
/* 2712 */     if ((localSerializationConfig.isEnabled(SerializationFeature.CLOSE_CLOSEABLE)) && ((paramObject instanceof Closeable))) {
/* 2713 */       _configAndWriteCloseable(paramJsonGenerator, paramObject, localSerializationConfig);
/* 2714 */       return;
/*      */     }
/* 2716 */     int i = 0;
/*      */     try {
/* 2718 */       _serializerProvider(localSerializationConfig).serializeValue(paramJsonGenerator, paramObject);
/* 2719 */       i = 1;
/* 2720 */       paramJsonGenerator.close(); return;
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 2725 */       if (i == 0) {
/*      */         try {
/* 2727 */           paramJsonGenerator.close();
/*      */         }
/*      */         catch (IOException localIOException2) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void _configAndWriteValue(JsonGenerator paramJsonGenerator, Object paramObject, Class<?> paramClass) throws IOException, JsonGenerationException, JsonMappingException
/*      */   {
/* 2736 */     SerializationConfig localSerializationConfig = getSerializationConfig().withView(paramClass);
/* 2737 */     if (localSerializationConfig.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
/* 2738 */       paramJsonGenerator.useDefaultPrettyPrinter();
/*      */     }
/*      */     
/* 2741 */     if ((localSerializationConfig.isEnabled(SerializationFeature.CLOSE_CLOSEABLE)) && ((paramObject instanceof Closeable))) {
/* 2742 */       _configAndWriteCloseable(paramJsonGenerator, paramObject, localSerializationConfig);
/* 2743 */       return;
/*      */     }
/* 2745 */     int i = 0;
/*      */     try {
/* 2747 */       _serializerProvider(localSerializationConfig).serializeValue(paramJsonGenerator, paramObject);
/* 2748 */       i = 1;
/* 2749 */       paramJsonGenerator.close(); return;
/*      */     } finally {
/* 2751 */       if (i == 0) {
/*      */         try {
/* 2753 */           paramJsonGenerator.close();
/*      */         }
/*      */         catch (IOException localIOException2) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void _configAndWriteCloseable(JsonGenerator paramJsonGenerator, Object paramObject, SerializationConfig paramSerializationConfig)
/*      */     throws IOException, JsonGenerationException, JsonMappingException
/*      */   {
/* 2766 */     Closeable localCloseable1 = (Closeable)paramObject;
/*      */     try {
/* 2768 */       _serializerProvider(paramSerializationConfig).serializeValue(paramJsonGenerator, paramObject);
/* 2769 */       JsonGenerator localJsonGenerator = paramJsonGenerator;
/* 2770 */       paramJsonGenerator = null;
/* 2771 */       localJsonGenerator.close();
/* 2772 */       Closeable localCloseable2 = localCloseable1;
/* 2773 */       localCloseable1 = null;
/* 2774 */       localCloseable2.close(); return;
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 2779 */       if (paramJsonGenerator != null) {
/*      */         try {
/* 2781 */           paramJsonGenerator.close();
/*      */         } catch (IOException localIOException3) {}
/*      */       }
/* 2784 */       if (localCloseable1 != null) {
/*      */         try {
/* 2786 */           localCloseable1.close();
/*      */         }
/*      */         catch (IOException localIOException4) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void _writeCloseableValue(JsonGenerator paramJsonGenerator, Object paramObject, SerializationConfig paramSerializationConfig)
/*      */     throws IOException, JsonGenerationException, JsonMappingException
/*      */   {
/* 2799 */     Closeable localCloseable1 = (Closeable)paramObject;
/*      */     try {
/* 2801 */       _serializerProvider(paramSerializationConfig).serializeValue(paramJsonGenerator, paramObject);
/* 2802 */       if (paramSerializationConfig.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
/* 2803 */         paramJsonGenerator.flush();
/*      */       }
/* 2805 */       Closeable localCloseable2 = localCloseable1;
/* 2806 */       localCloseable1 = null;
/* 2807 */       localCloseable2.close(); return;
/*      */     } finally {
/* 2809 */       if (localCloseable1 != null) {
/*      */         try {
/* 2811 */           localCloseable1.close();
/*      */         }
/*      */         catch (IOException localIOException2) {}
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */   protected DefaultDeserializationContext createDeserializationContext(JsonParser paramJsonParser, DeserializationConfig paramDeserializationConfig)
/*      */   {
/* 2831 */     return this._deserializationContext.createInstance(paramDeserializationConfig, paramJsonParser, this._injectableValues);
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
/*      */   protected Object _readValue(DeserializationConfig paramDeserializationConfig, JsonParser paramJsonParser, JavaType paramJavaType)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2846 */     JsonToken localJsonToken = _initForReading(paramJsonParser);
/* 2847 */     DefaultDeserializationContext localDefaultDeserializationContext; Object localObject; if (localJsonToken == JsonToken.VALUE_NULL)
/*      */     {
/* 2849 */       localDefaultDeserializationContext = createDeserializationContext(paramJsonParser, paramDeserializationConfig);
/* 2850 */       localObject = _findRootDeserializer(localDefaultDeserializationContext, paramJavaType).getNullValue();
/* 2851 */     } else if ((localJsonToken == JsonToken.END_ARRAY) || (localJsonToken == JsonToken.END_OBJECT)) {
/* 2852 */       localObject = null;
/*      */     } else {
/* 2854 */       localDefaultDeserializationContext = createDeserializationContext(paramJsonParser, paramDeserializationConfig);
/* 2855 */       JsonDeserializer localJsonDeserializer = _findRootDeserializer(localDefaultDeserializationContext, paramJavaType);
/*      */       
/* 2857 */       if (paramDeserializationConfig.useRootWrapping()) {
/* 2858 */         localObject = _unwrapAndDeserialize(paramJsonParser, localDefaultDeserializationContext, paramDeserializationConfig, paramJavaType, localJsonDeserializer);
/*      */       } else {
/* 2860 */         localObject = localJsonDeserializer.deserialize(paramJsonParser, localDefaultDeserializationContext);
/*      */       }
/*      */     }
/*      */     
/* 2864 */     paramJsonParser.clearCurrentToken();
/* 2865 */     return localObject;
/*      */   }
/*      */   
/*      */   protected Object _readMapAndClose(JsonParser paramJsonParser, JavaType paramJavaType)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/*      */     try
/*      */     {
/* 2873 */       JsonToken localJsonToken = _initForReading(paramJsonParser);
/* 2874 */       Object localObject1; Object localObject2; if (localJsonToken == JsonToken.VALUE_NULL)
/*      */       {
/* 2876 */         localObject1 = createDeserializationContext(paramJsonParser, getDeserializationConfig());
/*      */         
/* 2878 */         localObject2 = _findRootDeserializer((DeserializationContext)localObject1, paramJavaType).getNullValue();
/* 2879 */       } else if ((localJsonToken == JsonToken.END_ARRAY) || (localJsonToken == JsonToken.END_OBJECT)) {
/* 2880 */         localObject2 = null;
/*      */       } else {
/* 2882 */         localObject1 = getDeserializationConfig();
/* 2883 */         DefaultDeserializationContext localDefaultDeserializationContext = createDeserializationContext(paramJsonParser, (DeserializationConfig)localObject1);
/* 2884 */         JsonDeserializer localJsonDeserializer = _findRootDeserializer(localDefaultDeserializationContext, paramJavaType);
/* 2885 */         if (((DeserializationConfig)localObject1).useRootWrapping()) {
/* 2886 */           localObject2 = _unwrapAndDeserialize(paramJsonParser, localDefaultDeserializationContext, (DeserializationConfig)localObject1, paramJavaType, localJsonDeserializer);
/*      */         } else {
/* 2888 */           localObject2 = localJsonDeserializer.deserialize(paramJsonParser, localDefaultDeserializationContext);
/*      */         }
/*      */       }
/*      */       
/* 2892 */       paramJsonParser.clearCurrentToken();
/* 2893 */       return localObject2;
/*      */     } finally {
/*      */       try {
/* 2896 */         paramJsonParser.close();
/*      */       }
/*      */       catch (IOException localIOException2) {}
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
/*      */   protected JsonToken _initForReading(JsonParser paramJsonParser)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2923 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/* 2924 */     if (localJsonToken == null)
/*      */     {
/* 2926 */       localJsonToken = paramJsonParser.nextToken();
/* 2927 */       if (localJsonToken == null)
/*      */       {
/*      */ 
/*      */ 
/* 2931 */         throw JsonMappingException.from(paramJsonParser, "No content to map due to end-of-input");
/*      */       }
/*      */     }
/* 2934 */     return localJsonToken;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected Object _unwrapAndDeserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, JsonDeserializer<Object> paramJsonDeserializer)
/*      */     throws IOException, JsonParseException, JsonMappingException
/*      */   {
/* 2942 */     String str = paramDeserializationConfig.getRootName();
/* 2943 */     if (str == null) {
/* 2944 */       localObject1 = this._rootNames.findRootName(paramJavaType, paramDeserializationConfig);
/* 2945 */       str = ((com.shaded.fasterxml.jackson.core.io.SerializedString)localObject1).getValue();
/*      */     }
/* 2947 */     if (paramJsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
/* 2948 */       throw JsonMappingException.from(paramJsonParser, "Current token not START_OBJECT (needed to unwrap root name '" + str + "'), but " + paramJsonParser.getCurrentToken());
/*      */     }
/*      */     
/* 2951 */     if (paramJsonParser.nextToken() != JsonToken.FIELD_NAME) {
/* 2952 */       throw JsonMappingException.from(paramJsonParser, "Current token not FIELD_NAME (to contain expected root name '" + str + "'), but " + paramJsonParser.getCurrentToken());
/*      */     }
/*      */     
/* 2955 */     Object localObject1 = paramJsonParser.getCurrentName();
/* 2956 */     if (!str.equals(localObject1)) {
/* 2957 */       throw JsonMappingException.from(paramJsonParser, "Root name '" + (String)localObject1 + "' does not match expected ('" + str + "') for type " + paramJavaType);
/*      */     }
/*      */     
/*      */ 
/* 2961 */     paramJsonParser.nextToken();
/* 2962 */     Object localObject2 = paramJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/*      */     
/* 2964 */     if (paramJsonParser.nextToken() != JsonToken.END_OBJECT) {
/* 2965 */       throw JsonMappingException.from(paramJsonParser, "Current token not END_OBJECT (to match wrapper object with root name '" + str + "'), but " + paramJsonParser.getCurrentToken());
/*      */     }
/*      */     
/* 2968 */     return localObject2;
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
/*      */   protected JsonDeserializer<Object> _findRootDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType)
/*      */     throws JsonMappingException
/*      */   {
/* 2985 */     JsonDeserializer localJsonDeserializer = (JsonDeserializer)this._rootDeserializers.get(paramJavaType);
/* 2986 */     if (localJsonDeserializer != null) {
/* 2987 */       return localJsonDeserializer;
/*      */     }
/*      */     
/* 2990 */     localJsonDeserializer = paramDeserializationContext.findRootValueDeserializer(paramJavaType);
/* 2991 */     if (localJsonDeserializer == null) {
/* 2992 */       throw new JsonMappingException("Can not find a deserializer for type " + paramJavaType);
/*      */     }
/* 2994 */     this._rootDeserializers.put(paramJavaType, localJsonDeserializer);
/* 2995 */     return localJsonDeserializer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _verifySchemaType(FormatSchema paramFormatSchema)
/*      */   {
/* 3003 */     if ((paramFormatSchema != null) && 
/* 3004 */       (!this._jsonFactory.canUseSchema(paramFormatSchema))) {
/* 3005 */       throw new IllegalArgumentException("Can not use FormatSchema of type " + paramFormatSchema.getClass().getName() + " for format " + this._jsonFactory.getFormatName());
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ObjectMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */