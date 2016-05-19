/*     */ package com.shaded.fasterxml.jackson.databind.module;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.Version;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.KeyDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.Module;
/*     */ import com.shaded.fasterxml.jackson.databind.Module.SetupContext;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.NamedType;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.BeanSerializerModifier;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleModule
/*     */   extends Module
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3132264350026957446L;
/*     */   protected final String _name;
/*     */   protected final Version _version;
/*  30 */   protected SimpleSerializers _serializers = null;
/*  31 */   protected SimpleDeserializers _deserializers = null;
/*     */   
/*  33 */   protected SimpleSerializers _keySerializers = null;
/*  34 */   protected SimpleKeyDeserializers _keyDeserializers = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  41 */   protected SimpleAbstractTypeResolver _abstractTypes = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  48 */   protected SimpleValueInstantiators _valueInstantiators = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  53 */   protected BeanDeserializerModifier _deserializerModifier = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  58 */   protected BeanSerializerModifier _serializerModifier = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  64 */   protected HashMap<Class<?>, Class<?>> _mixins = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  69 */   protected LinkedHashSet<NamedType> _subtypes = null;
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
/*     */   public SimpleModule()
/*     */   {
/*  84 */     this._name = ("SimpleModule-" + System.identityHashCode(this));
/*  85 */     this._version = Version.unknownVersion();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleModule(String paramString)
/*     */   {
/*  93 */     this(paramString, Version.unknownVersion());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleModule(Version paramVersion)
/*     */   {
/* 101 */     this._name = paramVersion.getArtifactId();
/* 102 */     this._version = paramVersion;
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
/*     */   public SimpleModule(String paramString, Version paramVersion)
/*     */   {
/* 115 */     this._name = paramString;
/* 116 */     this._version = paramVersion;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleModule(String paramString, Version paramVersion, Map<Class<?>, JsonDeserializer<?>> paramMap)
/*     */   {
/* 124 */     this(paramString, paramVersion, paramMap, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleModule(String paramString, Version paramVersion, List<JsonSerializer<?>> paramList)
/*     */   {
/* 132 */     this(paramString, paramVersion, null, paramList);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleModule(String paramString, Version paramVersion, Map<Class<?>, JsonDeserializer<?>> paramMap, List<JsonSerializer<?>> paramList)
/*     */   {
/* 142 */     this._name = paramString;
/* 143 */     this._version = paramVersion;
/* 144 */     if (paramMap != null) {
/* 145 */       this._deserializers = new SimpleDeserializers(paramMap);
/*     */     }
/* 147 */     if (paramList != null) {
/* 148 */       this._serializers = new SimpleSerializers(paramList);
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
/*     */   public void setSerializers(SimpleSerializers paramSimpleSerializers)
/*     */   {
/* 162 */     this._serializers = paramSimpleSerializers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDeserializers(SimpleDeserializers paramSimpleDeserializers)
/*     */   {
/* 169 */     this._deserializers = paramSimpleDeserializers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setKeySerializers(SimpleSerializers paramSimpleSerializers)
/*     */   {
/* 176 */     this._keySerializers = paramSimpleSerializers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setKeyDeserializers(SimpleKeyDeserializers paramSimpleKeyDeserializers)
/*     */   {
/* 183 */     this._keyDeserializers = paramSimpleKeyDeserializers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setAbstractTypes(SimpleAbstractTypeResolver paramSimpleAbstractTypeResolver)
/*     */   {
/* 190 */     this._abstractTypes = paramSimpleAbstractTypeResolver;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setValueInstantiators(SimpleValueInstantiators paramSimpleValueInstantiators)
/*     */   {
/* 197 */     this._valueInstantiators = paramSimpleValueInstantiators;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public SimpleModule setDeserializerModifier(BeanDeserializerModifier paramBeanDeserializerModifier)
/*     */   {
/* 204 */     this._deserializerModifier = paramBeanDeserializerModifier;
/* 205 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public SimpleModule setSerializerModifier(BeanSerializerModifier paramBeanSerializerModifier)
/*     */   {
/* 212 */     this._serializerModifier = paramBeanSerializerModifier;
/* 213 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleModule addSerializer(JsonSerializer<?> paramJsonSerializer)
/*     */   {
/* 224 */     if (this._serializers == null) {
/* 225 */       this._serializers = new SimpleSerializers();
/*     */     }
/* 227 */     this._serializers.addSerializer(paramJsonSerializer);
/* 228 */     return this;
/*     */   }
/*     */   
/*     */   public <T> SimpleModule addSerializer(Class<? extends T> paramClass, JsonSerializer<T> paramJsonSerializer)
/*     */   {
/* 233 */     if (this._serializers == null) {
/* 234 */       this._serializers = new SimpleSerializers();
/*     */     }
/* 236 */     this._serializers.addSerializer(paramClass, paramJsonSerializer);
/* 237 */     return this;
/*     */   }
/*     */   
/*     */   public <T> SimpleModule addKeySerializer(Class<? extends T> paramClass, JsonSerializer<T> paramJsonSerializer)
/*     */   {
/* 242 */     if (this._keySerializers == null) {
/* 243 */       this._keySerializers = new SimpleSerializers();
/*     */     }
/* 245 */     this._keySerializers.addSerializer(paramClass, paramJsonSerializer);
/* 246 */     return this;
/*     */   }
/*     */   
/*     */   public <T> SimpleModule addDeserializer(Class<T> paramClass, JsonDeserializer<? extends T> paramJsonDeserializer)
/*     */   {
/* 251 */     if (this._deserializers == null) {
/* 252 */       this._deserializers = new SimpleDeserializers();
/*     */     }
/* 254 */     this._deserializers.addDeserializer(paramClass, paramJsonDeserializer);
/* 255 */     return this;
/*     */   }
/*     */   
/*     */   public SimpleModule addKeyDeserializer(Class<?> paramClass, KeyDeserializer paramKeyDeserializer)
/*     */   {
/* 260 */     if (this._keyDeserializers == null) {
/* 261 */       this._keyDeserializers = new SimpleKeyDeserializers();
/*     */     }
/* 263 */     this._keyDeserializers.addDeserializer(paramClass, paramKeyDeserializer);
/* 264 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> SimpleModule addAbstractTypeMapping(Class<T> paramClass, Class<? extends T> paramClass1)
/*     */   {
/* 275 */     if (this._abstractTypes == null) {
/* 276 */       this._abstractTypes = new SimpleAbstractTypeResolver();
/*     */     }
/*     */     
/* 279 */     this._abstractTypes = this._abstractTypes.addMapping(paramClass, paramClass1);
/* 280 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleModule addValueInstantiator(Class<?> paramClass, ValueInstantiator paramValueInstantiator)
/*     */   {
/* 292 */     if (this._valueInstantiators == null) {
/* 293 */       this._valueInstantiators = new SimpleValueInstantiators();
/*     */     }
/* 295 */     this._valueInstantiators = this._valueInstantiators.addValueInstantiator(paramClass, paramValueInstantiator);
/* 296 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleModule registerSubtypes(Class<?>... paramVarArgs)
/*     */   {
/* 306 */     if (this._subtypes == null) {
/* 307 */       this._subtypes = new LinkedHashSet(Math.max(16, paramVarArgs.length));
/*     */     }
/* 309 */     for (Class<?> localClass : paramVarArgs) {
/* 310 */       this._subtypes.add(new NamedType(localClass));
/*     */     }
/* 312 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleModule registerSubtypes(NamedType... paramVarArgs)
/*     */   {
/* 322 */     if (this._subtypes == null) {
/* 323 */       this._subtypes = new LinkedHashSet(Math.max(16, paramVarArgs.length));
/*     */     }
/* 325 */     for (NamedType localNamedType : paramVarArgs) {
/* 326 */       this._subtypes.add(localNamedType);
/*     */     }
/* 328 */     return this;
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
/*     */   public SimpleModule setMixInAnnotation(Class<?> paramClass1, Class<?> paramClass2)
/*     */   {
/* 341 */     if (this._mixins == null) {
/* 342 */       this._mixins = new HashMap();
/*     */     }
/* 344 */     this._mixins.put(paramClass1, paramClass2);
/* 345 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getModuleName()
/*     */   {
/* 356 */     return this._name;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setupModule(Module.SetupContext paramSetupContext)
/*     */   {
/* 362 */     if (this._serializers != null) {
/* 363 */       paramSetupContext.addSerializers(this._serializers);
/*     */     }
/* 365 */     if (this._deserializers != null) {
/* 366 */       paramSetupContext.addDeserializers(this._deserializers);
/*     */     }
/* 368 */     if (this._keySerializers != null) {
/* 369 */       paramSetupContext.addKeySerializers(this._keySerializers);
/*     */     }
/* 371 */     if (this._keyDeserializers != null) {
/* 372 */       paramSetupContext.addKeyDeserializers(this._keyDeserializers);
/*     */     }
/* 374 */     if (this._abstractTypes != null) {
/* 375 */       paramSetupContext.addAbstractTypeResolver(this._abstractTypes);
/*     */     }
/* 377 */     if (this._valueInstantiators != null) {
/* 378 */       paramSetupContext.addValueInstantiators(this._valueInstantiators);
/*     */     }
/* 380 */     if (this._deserializerModifier != null) {
/* 381 */       paramSetupContext.addBeanDeserializerModifier(this._deserializerModifier);
/*     */     }
/* 383 */     if (this._serializerModifier != null) {
/* 384 */       paramSetupContext.addBeanSerializerModifier(this._serializerModifier);
/*     */     }
/* 386 */     if ((this._subtypes != null) && (this._subtypes.size() > 0)) {
/* 387 */       paramSetupContext.registerSubtypes((NamedType[])this._subtypes.toArray(new NamedType[this._subtypes.size()]));
/*     */     }
/* 389 */     if (this._mixins != null) {
/* 390 */       for (Map.Entry localEntry : this._mixins.entrySet()) {
/* 391 */         paramSetupContext.setMixInAnnotations((Class)localEntry.getKey(), (Class)localEntry.getValue());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Version version()
/*     */   {
/* 398 */     return this._version;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/module/SimpleModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */