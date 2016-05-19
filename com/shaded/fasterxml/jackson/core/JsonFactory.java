/*      */ package com.shaded.fasterxml.jackson.core;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.core.format.InputAccessor;
/*      */ import com.shaded.fasterxml.jackson.core.format.MatchStrength;
/*      */ import com.shaded.fasterxml.jackson.core.io.CharacterEscapes;
/*      */ import com.shaded.fasterxml.jackson.core.io.IOContext;
/*      */ import com.shaded.fasterxml.jackson.core.io.InputDecorator;
/*      */ import com.shaded.fasterxml.jackson.core.io.OutputDecorator;
/*      */ import com.shaded.fasterxml.jackson.core.io.SerializedString;
/*      */ import com.shaded.fasterxml.jackson.core.io.UTF8Writer;
/*      */ import com.shaded.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
/*      */ import com.shaded.fasterxml.jackson.core.json.PackageVersion;
/*      */ import com.shaded.fasterxml.jackson.core.json.ReaderBasedJsonParser;
/*      */ import com.shaded.fasterxml.jackson.core.json.UTF8JsonGenerator;
/*      */ import com.shaded.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
/*      */ import com.shaded.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
/*      */ import com.shaded.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
/*      */ import com.shaded.fasterxml.jackson.core.util.BufferRecycler;
/*      */ import com.shaded.fasterxml.jackson.core.util.DefaultPrettyPrinter;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.StringReader;
/*      */ import java.io.Writer;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.net.URL;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JsonFactory
/*      */   implements Versioned, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 8726401676402117450L;
/*      */   public static final String FORMAT_NAME_JSON = "JSON";
/*      */   
/*      */   public static enum Feature
/*      */   {
/*   78 */     INTERN_FIELD_NAMES(true), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   88 */     CANONICALIZE_FIELD_NAMES(true);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final boolean _defaultState;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public static int collectDefaults()
/*      */     {
/*  103 */       int i = 0;
/*  104 */       for (Feature localFeature : values()) {
/*  105 */         if (localFeature.enabledByDefault()) {
/*  106 */           i |= localFeature.getMask();
/*      */         }
/*      */       }
/*  109 */       return i;
/*      */     }
/*      */     
/*      */     private Feature(boolean paramBoolean)
/*      */     {
/*  114 */       this._defaultState = paramBoolean;
/*      */     }
/*      */     
/*  117 */     public boolean enabledByDefault() { return this._defaultState; }
/*      */     
/*  119 */     public boolean enabledIn(int paramInt) { return (paramInt & getMask()) != 0; }
/*      */     
/*  121 */     public int getMask() { return 1 << ordinal(); }
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
/*  139 */   protected static final int DEFAULT_FACTORY_FEATURE_FLAGS = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  145 */   protected static final int DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  151 */   protected static final int DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
/*      */   
/*  153 */   private static final SerializableString DEFAULT_ROOT_VALUE_SEPARATOR = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  166 */   protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef = new ThreadLocal();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  174 */   protected final transient CharsToNameCanonicalizer _rootCharSymbols = CharsToNameCanonicalizer.createRoot();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  183 */   protected final transient BytesToNameCanonicalizer _rootByteSymbols = BytesToNameCanonicalizer.createRoot();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ObjectCodec _objectCodec;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  203 */   protected int _factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  208 */   protected int _parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  213 */   protected int _generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected CharacterEscapes _characterEscapes;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected InputDecorator _inputDecorator;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected OutputDecorator _outputDecorator;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  241 */   protected SerializableString _rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  259 */   public JsonFactory() { this((ObjectCodec)null); }
/*      */   
/*  261 */   public JsonFactory(ObjectCodec paramObjectCodec) { this._objectCodec = paramObjectCodec; }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonFactory(JsonFactory paramJsonFactory, ObjectCodec paramObjectCodec)
/*      */   {
/*  270 */     this._objectCodec = null;
/*  271 */     this._factoryFeatures = paramJsonFactory._factoryFeatures;
/*  272 */     this._parserFeatures = paramJsonFactory._parserFeatures;
/*  273 */     this._generatorFeatures = paramJsonFactory._generatorFeatures;
/*  274 */     this._characterEscapes = paramJsonFactory._characterEscapes;
/*  275 */     this._inputDecorator = paramJsonFactory._inputDecorator;
/*  276 */     this._outputDecorator = paramJsonFactory._outputDecorator;
/*  277 */     this._rootValueSeparator = paramJsonFactory._rootValueSeparator;
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
/*      */   public JsonFactory copy()
/*      */   {
/*  301 */     _checkInvalidCopy(JsonFactory.class);
/*      */     
/*  303 */     return new JsonFactory(this, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _checkInvalidCopy(Class<?> paramClass)
/*      */   {
/*  312 */     if (getClass() != paramClass) {
/*  313 */       throw new IllegalStateException("Failed copy(): " + getClass().getName() + " (version: " + version() + ") does not override copy(); it has to");
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
/*      */   protected Object readResolve()
/*      */   {
/*  330 */     return new JsonFactory(this, this._objectCodec);
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
/*      */   public boolean canUseSchema(FormatSchema paramFormatSchema)
/*      */   {
/*  350 */     String str = getFormatName();
/*  351 */     return (str != null) && (str.equals(paramFormatSchema.getSchemaType()));
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
/*      */   public String getFormatName()
/*      */   {
/*  367 */     if (getClass() == JsonFactory.class) {
/*  368 */       return "JSON";
/*      */     }
/*  370 */     return null;
/*      */   }
/*      */   
/*      */   public MatchStrength hasFormat(InputAccessor paramInputAccessor)
/*      */     throws IOException
/*      */   {
/*  376 */     if (getClass() == JsonFactory.class) {
/*  377 */       return hasJSONFormat(paramInputAccessor);
/*      */     }
/*  379 */     return null;
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
/*      */   public boolean requiresCustomCodec()
/*      */   {
/*  396 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected MatchStrength hasJSONFormat(InputAccessor paramInputAccessor)
/*      */     throws IOException
/*      */   {
/*  405 */     return ByteSourceJsonBootstrapper.hasJSONFormat(paramInputAccessor);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Version version()
/*      */   {
/*  416 */     return PackageVersion.VERSION;
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
/*      */   public final JsonFactory configure(Feature paramFeature, boolean paramBoolean)
/*      */   {
/*  430 */     return paramBoolean ? enable(paramFeature) : disable(paramFeature);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonFactory enable(Feature paramFeature)
/*      */   {
/*  438 */     this._factoryFeatures |= paramFeature.getMask();
/*  439 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonFactory disable(Feature paramFeature)
/*      */   {
/*  447 */     this._factoryFeatures &= (paramFeature.getMask() ^ 0xFFFFFFFF);
/*  448 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final boolean isEnabled(Feature paramFeature)
/*      */   {
/*  455 */     return (this._factoryFeatures & paramFeature.getMask()) != 0;
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
/*      */   public final JsonFactory configure(JsonParser.Feature paramFeature, boolean paramBoolean)
/*      */   {
/*  469 */     return paramBoolean ? enable(paramFeature) : disable(paramFeature);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonFactory enable(JsonParser.Feature paramFeature)
/*      */   {
/*  477 */     this._parserFeatures |= paramFeature.getMask();
/*  478 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonFactory disable(JsonParser.Feature paramFeature)
/*      */   {
/*  486 */     this._parserFeatures &= (paramFeature.getMask() ^ 0xFFFFFFFF);
/*  487 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final boolean isEnabled(JsonParser.Feature paramFeature)
/*      */   {
/*  494 */     return (this._parserFeatures & paramFeature.getMask()) != 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public InputDecorator getInputDecorator()
/*      */   {
/*  502 */     return this._inputDecorator;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public JsonFactory setInputDecorator(InputDecorator paramInputDecorator)
/*      */   {
/*  509 */     this._inputDecorator = paramInputDecorator;
/*  510 */     return this;
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
/*      */   public final JsonFactory configure(JsonGenerator.Feature paramFeature, boolean paramBoolean)
/*      */   {
/*  524 */     return paramBoolean ? enable(paramFeature) : disable(paramFeature);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonFactory enable(JsonGenerator.Feature paramFeature)
/*      */   {
/*  533 */     this._generatorFeatures |= paramFeature.getMask();
/*  534 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonFactory disable(JsonGenerator.Feature paramFeature)
/*      */   {
/*  542 */     this._generatorFeatures &= (paramFeature.getMask() ^ 0xFFFFFFFF);
/*  543 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final boolean isEnabled(JsonGenerator.Feature paramFeature)
/*      */   {
/*  550 */     return (this._generatorFeatures & paramFeature.getMask()) != 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public CharacterEscapes getCharacterEscapes()
/*      */   {
/*  558 */     return this._characterEscapes;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonFactory setCharacterEscapes(CharacterEscapes paramCharacterEscapes)
/*      */   {
/*  566 */     this._characterEscapes = paramCharacterEscapes;
/*  567 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public OutputDecorator getOutputDecorator()
/*      */   {
/*  575 */     return this._outputDecorator;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public JsonFactory setOutputDecorator(OutputDecorator paramOutputDecorator)
/*      */   {
/*  582 */     this._outputDecorator = paramOutputDecorator;
/*  583 */     return this;
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
/*      */   public JsonFactory setRootValueSeparator(String paramString)
/*      */   {
/*  596 */     this._rootValueSeparator = (paramString == null ? null : new SerializedString(paramString));
/*  597 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getRootValueSeparator()
/*      */   {
/*  604 */     return this._rootValueSeparator == null ? null : this._rootValueSeparator.getValue();
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
/*      */   public JsonFactory setCodec(ObjectCodec paramObjectCodec)
/*      */   {
/*  621 */     this._objectCodec = paramObjectCodec;
/*  622 */     return this;
/*      */   }
/*      */   
/*  625 */   public ObjectCodec getCodec() { return this._objectCodec; }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonParser createParser(File paramFile)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  652 */     IOContext localIOContext = _createContext(paramFile, true);
/*  653 */     Object localObject = new FileInputStream(paramFile);
/*      */     
/*  655 */     if (this._inputDecorator != null) {
/*  656 */       localObject = this._inputDecorator.decorate(localIOContext, (InputStream)localObject);
/*      */     }
/*  658 */     return _createParser((InputStream)localObject, localIOContext);
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
/*      */   public JsonParser createParser(URL paramURL)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  680 */     IOContext localIOContext = _createContext(paramURL, true);
/*  681 */     InputStream localInputStream = _optimizedStreamFromURL(paramURL);
/*      */     
/*  683 */     if (this._inputDecorator != null) {
/*  684 */       localInputStream = this._inputDecorator.decorate(localIOContext, localInputStream);
/*      */     }
/*  686 */     return _createParser(localInputStream, localIOContext);
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
/*      */   public JsonParser createParser(InputStream paramInputStream)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  709 */     IOContext localIOContext = _createContext(paramInputStream, false);
/*      */     
/*  711 */     if (this._inputDecorator != null) {
/*  712 */       paramInputStream = this._inputDecorator.decorate(localIOContext, paramInputStream);
/*      */     }
/*  714 */     return _createParser(paramInputStream, localIOContext);
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
/*      */   public JsonParser createParser(Reader paramReader)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  735 */     IOContext localIOContext = _createContext(paramReader, false);
/*      */     
/*  737 */     if (this._inputDecorator != null) {
/*  738 */       paramReader = this._inputDecorator.decorate(localIOContext, paramReader);
/*      */     }
/*  740 */     return _createParser(paramReader, localIOContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonParser createParser(byte[] paramArrayOfByte)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  752 */     IOContext localIOContext = _createContext(paramArrayOfByte, true);
/*      */     
/*  754 */     if (this._inputDecorator != null) {
/*  755 */       InputStream localInputStream = this._inputDecorator.decorate(localIOContext, paramArrayOfByte, 0, paramArrayOfByte.length);
/*  756 */       if (localInputStream != null) {
/*  757 */         return _createParser(localInputStream, localIOContext);
/*      */       }
/*      */     }
/*  760 */     return _createParser(paramArrayOfByte, 0, paramArrayOfByte.length, localIOContext);
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
/*      */   public JsonParser createParser(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  776 */     IOContext localIOContext = _createContext(paramArrayOfByte, true);
/*      */     
/*  778 */     if (this._inputDecorator != null) {
/*  779 */       InputStream localInputStream = this._inputDecorator.decorate(localIOContext, paramArrayOfByte, paramInt1, paramInt2);
/*  780 */       if (localInputStream != null) {
/*  781 */         return _createParser(localInputStream, localIOContext);
/*      */       }
/*      */     }
/*  784 */     return _createParser(paramArrayOfByte, paramInt1, paramInt2, localIOContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonParser createParser(String paramString)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  796 */     Object localObject = new StringReader(paramString);
/*      */     
/*  798 */     IOContext localIOContext = _createContext(localObject, true);
/*      */     
/*  800 */     if (this._inputDecorator != null) {
/*  801 */       localObject = this._inputDecorator.decorate(localIOContext, (Reader)localObject);
/*      */     }
/*  803 */     return _createParser((Reader)localObject, localIOContext);
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
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(File paramFile)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  833 */     return createParser(paramFile);
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
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(URL paramURL)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  858 */     return createParser(paramURL);
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
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(InputStream paramInputStream)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  885 */     return createParser(paramInputStream);
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
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(Reader paramReader)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  909 */     return createParser(paramReader);
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
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(byte[] paramArrayOfByte)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  925 */     return createParser(paramArrayOfByte);
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
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  945 */     return createParser(paramArrayOfByte, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public JsonParser createJsonParser(String paramString)
/*      */     throws IOException, JsonParseException
/*      */   {
/*  958 */     return createParser(paramString);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonGenerator createGenerator(OutputStream paramOutputStream, JsonEncoding paramJsonEncoding)
/*      */     throws IOException
/*      */   {
/*  993 */     IOContext localIOContext = _createContext(paramOutputStream, false);
/*  994 */     localIOContext.setEncoding(paramJsonEncoding);
/*  995 */     if (paramJsonEncoding == JsonEncoding.UTF8)
/*      */     {
/*  997 */       if (this._outputDecorator != null) {
/*  998 */         paramOutputStream = this._outputDecorator.decorate(localIOContext, paramOutputStream);
/*      */       }
/* 1000 */       return _createUTF8Generator(paramOutputStream, localIOContext);
/*      */     }
/* 1002 */     Writer localWriter = _createWriter(paramOutputStream, paramJsonEncoding, localIOContext);
/*      */     
/* 1004 */     if (this._outputDecorator != null) {
/* 1005 */       localWriter = this._outputDecorator.decorate(localIOContext, localWriter);
/*      */     }
/* 1007 */     return _createGenerator(localWriter, localIOContext);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonGenerator createGenerator(OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 1019 */     return createGenerator(paramOutputStream, JsonEncoding.UTF8);
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
/*      */   public JsonGenerator createGenerator(Writer paramWriter)
/*      */     throws IOException
/*      */   {
/* 1040 */     IOContext localIOContext = _createContext(paramWriter, false);
/*      */     
/* 1042 */     if (this._outputDecorator != null) {
/* 1043 */       paramWriter = this._outputDecorator.decorate(localIOContext, paramWriter);
/*      */     }
/* 1045 */     return _createGenerator(paramWriter, localIOContext);
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
/*      */   public JsonGenerator createGenerator(File paramFile, JsonEncoding paramJsonEncoding)
/*      */     throws IOException
/*      */   {
/* 1067 */     Object localObject = new FileOutputStream(paramFile);
/*      */     
/* 1069 */     IOContext localIOContext = _createContext(localObject, true);
/* 1070 */     localIOContext.setEncoding(paramJsonEncoding);
/* 1071 */     if (paramJsonEncoding == JsonEncoding.UTF8)
/*      */     {
/* 1073 */       if (this._outputDecorator != null) {
/* 1074 */         localObject = this._outputDecorator.decorate(localIOContext, (OutputStream)localObject);
/*      */       }
/* 1076 */       return _createUTF8Generator((OutputStream)localObject, localIOContext);
/*      */     }
/* 1078 */     Writer localWriter = _createWriter((OutputStream)localObject, paramJsonEncoding, localIOContext);
/*      */     
/* 1080 */     if (this._outputDecorator != null) {
/* 1081 */       localWriter = this._outputDecorator.decorate(localIOContext, localWriter);
/*      */     }
/* 1083 */     return _createGenerator(localWriter, localIOContext);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public JsonGenerator createJsonGenerator(OutputStream paramOutputStream, JsonEncoding paramJsonEncoding)
/*      */     throws IOException
/*      */   {
/* 1118 */     return createGenerator(paramOutputStream, paramJsonEncoding);
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
/*      */   @Deprecated
/*      */   public JsonGenerator createJsonGenerator(Writer paramWriter)
/*      */     throws IOException
/*      */   {
/* 1140 */     return createGenerator(paramWriter);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public JsonGenerator createJsonGenerator(OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 1153 */     return createGenerator(paramOutputStream, JsonEncoding.UTF8);
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
/*      */   @Deprecated
/*      */   public JsonGenerator createJsonGenerator(File paramFile, JsonEncoding paramJsonEncoding)
/*      */     throws IOException
/*      */   {
/* 1177 */     return createGenerator(paramFile, paramJsonEncoding);
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
/*      */   protected JsonParser _createParser(InputStream paramInputStream, IOContext paramIOContext)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1203 */     return new ByteSourceJsonBootstrapper(paramIOContext, paramInputStream).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols, isEnabled(Feature.CANONICALIZE_FIELD_NAMES), isEnabled(Feature.INTERN_FIELD_NAMES));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   protected JsonParser _createJsonParser(InputStream paramInputStream, IOContext paramIOContext)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1214 */     return _createParser(paramInputStream, paramIOContext);
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
/*      */   protected JsonParser _createParser(Reader paramReader, IOContext paramIOContext)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1232 */     return new ReaderBasedJsonParser(paramIOContext, this._parserFeatures, paramReader, this._objectCodec, this._rootCharSymbols.makeChild(isEnabled(Feature.CANONICALIZE_FIELD_NAMES), isEnabled(Feature.INTERN_FIELD_NAMES)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   protected JsonParser _createJsonParser(Reader paramReader, IOContext paramIOContext)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1242 */     return _createParser(paramReader, paramIOContext);
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
/*      */   protected JsonParser _createParser(byte[] paramArrayOfByte, int paramInt1, int paramInt2, IOContext paramIOContext)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1259 */     return new ByteSourceJsonBootstrapper(paramIOContext, paramArrayOfByte, paramInt1, paramInt2).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols, isEnabled(Feature.CANONICALIZE_FIELD_NAMES), isEnabled(Feature.INTERN_FIELD_NAMES));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   protected JsonParser _createJsonParser(byte[] paramArrayOfByte, int paramInt1, int paramInt2, IOContext paramIOContext)
/*      */     throws IOException, JsonParseException
/*      */   {
/* 1270 */     return _createParser(paramArrayOfByte, paramInt1, paramInt2, paramIOContext);
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
/*      */   protected JsonGenerator _createGenerator(Writer paramWriter, IOContext paramIOContext)
/*      */     throws IOException
/*      */   {
/* 1293 */     WriterBasedJsonGenerator localWriterBasedJsonGenerator = new WriterBasedJsonGenerator(paramIOContext, this._generatorFeatures, this._objectCodec, paramWriter);
/*      */     
/* 1295 */     if (this._characterEscapes != null) {
/* 1296 */       localWriterBasedJsonGenerator.setCharacterEscapes(this._characterEscapes);
/*      */     }
/* 1298 */     SerializableString localSerializableString = this._rootValueSeparator;
/* 1299 */     if (localSerializableString != DEFAULT_ROOT_VALUE_SEPARATOR) {
/* 1300 */       localWriterBasedJsonGenerator.setRootValueSeparator(localSerializableString);
/*      */     }
/* 1302 */     return localWriterBasedJsonGenerator;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   protected JsonGenerator _createJsonGenerator(Writer paramWriter, IOContext paramIOContext)
/*      */     throws IOException
/*      */   {
/* 1315 */     return _createGenerator(paramWriter, paramIOContext);
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
/*      */   protected JsonGenerator _createUTF8Generator(OutputStream paramOutputStream, IOContext paramIOContext)
/*      */     throws IOException
/*      */   {
/* 1329 */     UTF8JsonGenerator localUTF8JsonGenerator = new UTF8JsonGenerator(paramIOContext, this._generatorFeatures, this._objectCodec, paramOutputStream);
/*      */     
/* 1331 */     if (this._characterEscapes != null) {
/* 1332 */       localUTF8JsonGenerator.setCharacterEscapes(this._characterEscapes);
/*      */     }
/* 1334 */     SerializableString localSerializableString = this._rootValueSeparator;
/* 1335 */     if (localSerializableString != DEFAULT_ROOT_VALUE_SEPARATOR) {
/* 1336 */       localUTF8JsonGenerator.setRootValueSeparator(localSerializableString);
/*      */     }
/* 1338 */     return localUTF8JsonGenerator;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   protected JsonGenerator _createUTF8JsonGenerator(OutputStream paramOutputStream, IOContext paramIOContext)
/*      */     throws IOException
/*      */   {
/* 1348 */     return _createUTF8Generator(paramOutputStream, paramIOContext);
/*      */   }
/*      */   
/*      */   protected Writer _createWriter(OutputStream paramOutputStream, JsonEncoding paramJsonEncoding, IOContext paramIOContext)
/*      */     throws IOException
/*      */   {
/* 1354 */     if (paramJsonEncoding == JsonEncoding.UTF8) {
/* 1355 */       return new UTF8Writer(paramIOContext, paramOutputStream);
/*      */     }
/*      */     
/* 1358 */     return new OutputStreamWriter(paramOutputStream, paramJsonEncoding.getJavaName());
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
/*      */   protected IOContext _createContext(Object paramObject, boolean paramBoolean)
/*      */   {
/* 1373 */     return new IOContext(_getBufferRecycler(), paramObject, paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BufferRecycler _getBufferRecycler()
/*      */   {
/* 1384 */     SoftReference localSoftReference = (SoftReference)_recyclerRef.get();
/* 1385 */     BufferRecycler localBufferRecycler = localSoftReference == null ? null : (BufferRecycler)localSoftReference.get();
/*      */     
/* 1387 */     if (localBufferRecycler == null) {
/* 1388 */       localBufferRecycler = new BufferRecycler();
/* 1389 */       _recyclerRef.set(new SoftReference(localBufferRecycler));
/*      */     }
/* 1391 */     return localBufferRecycler;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected InputStream _optimizedStreamFromURL(URL paramURL)
/*      */     throws IOException
/*      */   {
/* 1402 */     if ("file".equals(paramURL.getProtocol()))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1409 */       String str1 = paramURL.getHost();
/* 1410 */       if ((str1 == null) || (str1.length() == 0))
/*      */       {
/* 1412 */         String str2 = paramURL.getPath();
/* 1413 */         if (str2.indexOf('%') < 0) {
/* 1414 */           return new FileInputStream(paramURL.getPath());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1420 */     return paramURL.openStream();
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/JsonFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */