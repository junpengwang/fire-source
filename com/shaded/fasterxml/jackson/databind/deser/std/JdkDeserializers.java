/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Currency;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class JdkDeserializers
/*     */ {
/*  26 */   private static final HashSet<String> _classNames = new HashSet();
/*     */   
/*     */   static {
/*  29 */     Class[] arrayOfClass1 = { UUID.class, URL.class, URI.class, File.class, Currency.class, Pattern.class, Locale.class, InetAddress.class, Charset.class, AtomicBoolean.class, Class.class, StackTraceElement.class };
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
/*  44 */     for (Class localClass : arrayOfClass1) {
/*  45 */       _classNames.add(localClass.getName());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static StdDeserializer<?>[] all()
/*     */   {
/*  55 */     return new StdDeserializer[] { UUIDDeserializer.instance, URLDeserializer.instance, URIDeserializer.instance, FileDeserializer.instance, CurrencyDeserializer.instance, PatternDeserializer.instance, LocaleDeserializer.instance, InetAddressDeserializer.instance, CharsetDeserializer.instance, AtomicBooleanDeserializer.instance, ClassDeserializer.instance, StackTraceElementDeserializer.instance };
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
/*     */   public static JsonDeserializer<?> find(Class<?> paramClass, String paramString)
/*     */   {
/*  78 */     if (!_classNames.contains(paramString)) {
/*  79 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  84 */     if (paramClass == URI.class) {
/*  85 */       return URIDeserializer.instance;
/*     */     }
/*  87 */     if (paramClass == URL.class) {
/*  88 */       return URLDeserializer.instance;
/*     */     }
/*  90 */     if (paramClass == File.class) {
/*  91 */       return FileDeserializer.instance;
/*     */     }
/*     */     
/*     */ 
/*  95 */     if (paramClass == UUID.class) {
/*  96 */       return UUIDDeserializer.instance;
/*     */     }
/*  98 */     if (paramClass == Currency.class) {
/*  99 */       return CurrencyDeserializer.instance;
/*     */     }
/* 101 */     if (paramClass == Pattern.class) {
/* 102 */       return PatternDeserializer.instance;
/*     */     }
/* 104 */     if (paramClass == Locale.class) {
/* 105 */       return LocaleDeserializer.instance;
/*     */     }
/* 107 */     if (paramClass == InetAddress.class) {
/* 108 */       return InetAddressDeserializer.instance;
/*     */     }
/* 110 */     if (paramClass == Charset.class) {
/* 111 */       return CharsetDeserializer.instance;
/*     */     }
/* 113 */     if (paramClass == Class.class) {
/* 114 */       return ClassDeserializer.instance;
/*     */     }
/* 116 */     if (paramClass == StackTraceElement.class) {
/* 117 */       return StackTraceElementDeserializer.instance;
/*     */     }
/* 119 */     if (paramClass == AtomicBoolean.class)
/*     */     {
/* 121 */       return AtomicBooleanDeserializer.instance;
/*     */     }
/*     */     
/* 124 */     throw new IllegalArgumentException("Internal error: can't find deserializer for " + paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class UUIDDeserializer
/*     */     extends FromStringDeserializer<UUID>
/*     */   {
/* 136 */     public static final UUIDDeserializer instance = new UUIDDeserializer();
/*     */     
/* 138 */     public UUIDDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     protected UUID _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 144 */       return UUID.fromString(paramString);
/*     */     }
/*     */     
/*     */ 
/*     */     protected UUID _deserializeEmbedded(Object paramObject, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 151 */       if ((paramObject instanceof byte[])) {
/* 152 */         byte[] arrayOfByte = (byte[])paramObject;
/* 153 */         if (arrayOfByte.length != 16) {
/* 154 */           paramDeserializationContext.mappingException("Can only construct UUIDs from 16 byte arrays; got " + arrayOfByte.length + " bytes");
/*     */         }
/*     */         
/* 157 */         DataInputStream localDataInputStream = new DataInputStream(new java.io.ByteArrayInputStream(arrayOfByte));
/* 158 */         long l1 = localDataInputStream.readLong();
/* 159 */         long l2 = localDataInputStream.readLong();
/* 160 */         return new UUID(l1, l2);
/*     */       }
/* 162 */       super._deserializeEmbedded(paramObject, paramDeserializationContext);
/* 163 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class URLDeserializer
/*     */     extends FromStringDeserializer<URL>
/*     */   {
/* 170 */     public static final URLDeserializer instance = new URLDeserializer();
/*     */     
/* 172 */     public URLDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     protected URL _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IOException
/*     */     {
/* 178 */       return new URL(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class URIDeserializer
/*     */     extends FromStringDeserializer<URI>
/*     */   {
/* 185 */     public static final URIDeserializer instance = new URIDeserializer();
/*     */     
/* 187 */     public URIDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     protected URI _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IllegalArgumentException
/*     */     {
/* 193 */       return URI.create(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CurrencyDeserializer
/*     */     extends FromStringDeserializer<Currency>
/*     */   {
/* 200 */     public static final CurrencyDeserializer instance = new CurrencyDeserializer();
/*     */     
/* 202 */     public CurrencyDeserializer() { super(); }
/*     */     
/*     */ 
/*     */ 
/*     */     protected Currency _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IllegalArgumentException
/*     */     {
/* 209 */       return Currency.getInstance(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PatternDeserializer
/*     */     extends FromStringDeserializer<Pattern>
/*     */   {
/* 216 */     public static final PatternDeserializer instance = new PatternDeserializer();
/*     */     
/* 218 */     public PatternDeserializer() { super(); }
/*     */     
/*     */ 
/*     */ 
/*     */     protected Pattern _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IllegalArgumentException
/*     */     {
/* 225 */       return Pattern.compile(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static class LocaleDeserializer
/*     */     extends FromStringDeserializer<Locale>
/*     */   {
/* 235 */     public static final LocaleDeserializer instance = new LocaleDeserializer();
/*     */     
/* 237 */     public LocaleDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     protected Locale _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IOException
/*     */     {
/* 243 */       int i = paramString.indexOf('_');
/* 244 */       if (i < 0) {
/* 245 */         return new Locale(paramString);
/*     */       }
/* 247 */       String str1 = paramString.substring(0, i);
/* 248 */       paramString = paramString.substring(i + 1);
/* 249 */       i = paramString.indexOf('_');
/* 250 */       if (i < 0) {
/* 251 */         return new Locale(str1, paramString);
/*     */       }
/* 253 */       String str2 = paramString.substring(0, i);
/* 254 */       return new Locale(str1, str2, paramString.substring(i + 1));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static class InetAddressDeserializer
/*     */     extends FromStringDeserializer<InetAddress>
/*     */   {
/* 264 */     public static final InetAddressDeserializer instance = new InetAddressDeserializer();
/*     */     
/* 266 */     public InetAddressDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     protected InetAddress _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IOException
/*     */     {
/* 272 */       return InetAddress.getByName(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected static class CharsetDeserializer
/*     */     extends FromStringDeserializer<Charset>
/*     */   {
/* 280 */     public static final CharsetDeserializer instance = new CharsetDeserializer();
/*     */     
/* 282 */     public CharsetDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     protected Charset _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*     */       throws IOException
/*     */     {
/* 288 */       return Charset.forName(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FileDeserializer
/*     */     extends FromStringDeserializer<File>
/*     */   {
/* 295 */     public static final FileDeserializer instance = new FileDeserializer();
/*     */     
/* 297 */     public FileDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     protected File _deserialize(String paramString, DeserializationContext paramDeserializationContext)
/*     */     {
/* 302 */       return new File(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class AtomicReferenceDeserializer
/*     */     extends StdScalarDeserializer<AtomicReference<?>>
/*     */     implements com.shaded.fasterxml.jackson.databind.deser.ContextualDeserializer
/*     */   {
/*     */     protected final JavaType _referencedType;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected final JsonDeserializer<?> _valueDeserializer;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public AtomicReferenceDeserializer(JavaType paramJavaType)
/*     */     {
/* 327 */       this(paramJavaType, null);
/*     */     }
/*     */     
/*     */ 
/*     */     public AtomicReferenceDeserializer(JavaType paramJavaType, JsonDeserializer<?> paramJsonDeserializer)
/*     */     {
/* 333 */       super();
/* 334 */       this._referencedType = paramJavaType;
/* 335 */       this._valueDeserializer = paramJsonDeserializer;
/*     */     }
/*     */     
/*     */ 
/*     */     public AtomicReference<?> deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 342 */       return new AtomicReference(this._valueDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/*     */     }
/*     */     
/*     */ 
/*     */     public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, com.shaded.fasterxml.jackson.databind.BeanProperty paramBeanProperty)
/*     */       throws com.shaded.fasterxml.jackson.databind.JsonMappingException
/*     */     {
/* 349 */       JsonDeserializer localJsonDeserializer = this._valueDeserializer;
/* 350 */       if (localJsonDeserializer != null) {
/* 351 */         return this;
/*     */       }
/* 353 */       return new AtomicReferenceDeserializer(this._referencedType, paramDeserializationContext.findContextualValueDeserializer(this._referencedType, paramBeanProperty));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static class AtomicBooleanDeserializer
/*     */     extends StdScalarDeserializer<AtomicBoolean>
/*     */   {
/* 361 */     public static final AtomicBooleanDeserializer instance = new AtomicBooleanDeserializer();
/*     */     
/* 363 */     public AtomicBooleanDeserializer() { super(); }
/*     */     
/*     */ 
/*     */ 
/*     */     public AtomicBoolean deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 370 */       return new AtomicBoolean(_parseBooleanPrimitive(paramJsonParser, paramDeserializationContext));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class StackTraceElementDeserializer
/*     */     extends StdScalarDeserializer<StackTraceElement>
/*     */   {
/* 383 */     public static final StackTraceElementDeserializer instance = new StackTraceElementDeserializer();
/*     */     
/* 385 */     public StackTraceElementDeserializer() { super(); }
/*     */     
/*     */ 
/*     */     public StackTraceElement deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */       throws IOException, JsonProcessingException
/*     */     {
/* 391 */       JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*     */       
/* 393 */       if (localJsonToken == JsonToken.START_OBJECT) {
/* 394 */         String str1 = "";String str2 = "";String str3 = "";
/* 395 */         int i = -1;
/*     */         
/* 397 */         while ((localJsonToken = paramJsonParser.nextValue()) != JsonToken.END_OBJECT) {
/* 398 */           String str4 = paramJsonParser.getCurrentName();
/* 399 */           if ("className".equals(str4)) {
/* 400 */             str1 = paramJsonParser.getText();
/* 401 */           } else if ("fileName".equals(str4)) {
/* 402 */             str3 = paramJsonParser.getText();
/* 403 */           } else if ("lineNumber".equals(str4)) {
/* 404 */             if (localJsonToken.isNumeric()) {
/* 405 */               i = paramJsonParser.getIntValue();
/*     */             } else {
/* 407 */               throw com.shaded.fasterxml.jackson.databind.JsonMappingException.from(paramJsonParser, "Non-numeric token (" + localJsonToken + ") for property 'lineNumber'");
/*     */             }
/* 409 */           } else if ("methodName".equals(str4)) {
/* 410 */             str2 = paramJsonParser.getText();
/* 411 */           } else if (!"nativeMethod".equals(str4))
/*     */           {
/*     */ 
/* 414 */             handleUnknownProperty(paramJsonParser, paramDeserializationContext, this._valueClass, str4);
/*     */           }
/*     */         }
/* 417 */         return new StackTraceElement(str1, str2, str3, i);
/*     */       }
/* 419 */       throw paramDeserializationContext.mappingException(this._valueClass, localJsonToken);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/JdkDeserializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */