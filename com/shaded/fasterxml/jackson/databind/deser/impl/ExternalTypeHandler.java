/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeIdResolver;
/*     */ import com.shaded.fasterxml.jackson.databind.util.TokenBuffer;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExternalTypeHandler
/*     */ {
/*     */   private final ExtTypedProperty[] _properties;
/*     */   private final HashMap<String, Integer> _nameToPropertyIndex;
/*     */   private final String[] _typeIds;
/*     */   private final TokenBuffer[] _tokens;
/*     */   
/*     */   protected ExternalTypeHandler(ExtTypedProperty[] paramArrayOfExtTypedProperty, HashMap<String, Integer> paramHashMap, String[] paramArrayOfString, TokenBuffer[] paramArrayOfTokenBuffer)
/*     */   {
/*  32 */     this._properties = paramArrayOfExtTypedProperty;
/*  33 */     this._nameToPropertyIndex = paramHashMap;
/*  34 */     this._typeIds = paramArrayOfString;
/*  35 */     this._tokens = paramArrayOfTokenBuffer;
/*     */   }
/*     */   
/*     */   protected ExternalTypeHandler(ExternalTypeHandler paramExternalTypeHandler)
/*     */   {
/*  40 */     this._properties = paramExternalTypeHandler._properties;
/*  41 */     this._nameToPropertyIndex = paramExternalTypeHandler._nameToPropertyIndex;
/*  42 */     int i = this._properties.length;
/*  43 */     this._typeIds = new String[i];
/*  44 */     this._tokens = new TokenBuffer[i];
/*     */   }
/*     */   
/*     */   public ExternalTypeHandler start() {
/*  48 */     return new ExternalTypeHandler(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean handleTypePropertyValue(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, String paramString, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  61 */     Integer localInteger = (Integer)this._nameToPropertyIndex.get(paramString);
/*  62 */     if (localInteger == null) {
/*  63 */       return false;
/*     */     }
/*  65 */     int i = localInteger.intValue();
/*  66 */     ExtTypedProperty localExtTypedProperty = this._properties[i];
/*  67 */     if (!localExtTypedProperty.hasTypePropertyName(paramString)) {
/*  68 */       return false;
/*     */     }
/*  70 */     String str = paramJsonParser.getText();
/*     */     
/*  72 */     int j = (paramObject != null) && (this._tokens[i] != null) ? 1 : 0;
/*     */     
/*  74 */     if (j != 0) {
/*  75 */       _deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, i, str);
/*     */       
/*  77 */       this._tokens[i] = null;
/*     */     } else {
/*  79 */       this._typeIds[i] = str;
/*     */     }
/*  81 */     return true;
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
/*     */   public boolean handlePropertyValue(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, String paramString, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  96 */     Integer localInteger = (Integer)this._nameToPropertyIndex.get(paramString);
/*  97 */     if (localInteger == null) {
/*  98 */       return false;
/*     */     }
/* 100 */     int i = localInteger.intValue();
/* 101 */     ExtTypedProperty localExtTypedProperty = this._properties[i];
/*     */     int j;
/* 103 */     Object localObject; if (localExtTypedProperty.hasTypePropertyName(paramString)) {
/* 104 */       this._typeIds[i] = paramJsonParser.getText();
/* 105 */       paramJsonParser.skipChildren();
/* 106 */       j = (paramObject != null) && (this._tokens[i] != null) ? 1 : 0;
/*     */     }
/*     */     else {
/* 109 */       localObject = new TokenBuffer(paramJsonParser.getCodec());
/* 110 */       ((TokenBuffer)localObject).copyCurrentStructure(paramJsonParser);
/* 111 */       this._tokens[i] = localObject;
/* 112 */       j = (paramObject != null) && (this._typeIds[i] != null) ? 1 : 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 117 */     if (j != 0) {
/* 118 */       localObject = this._typeIds[i];
/*     */       
/* 120 */       this._typeIds[i] = null;
/* 121 */       _deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, i, (String)localObject);
/* 122 */       this._tokens[i] = null;
/*     */     }
/* 124 */     return true;
/*     */   }
/*     */   
/*     */   public Object complete(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 130 */     int i = 0; for (int j = this._properties.length; i < j; i++) {
/* 131 */       String str = this._typeIds[i];
/* 132 */       Object localObject1; if (str == null) {
/* 133 */         localObject1 = this._tokens[i];
/*     */         
/*     */ 
/* 136 */         if (localObject1 == null) {
/*     */           continue;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 142 */         JsonToken localJsonToken = ((TokenBuffer)localObject1).firstToken();
/* 143 */         if ((localJsonToken != null) && (localJsonToken.isScalarValue())) {
/* 144 */           JsonParser localJsonParser = ((TokenBuffer)localObject1).asParser(paramJsonParser);
/* 145 */           localJsonParser.nextToken();
/* 146 */           SettableBeanProperty localSettableBeanProperty = this._properties[i].getProperty();
/* 147 */           Object localObject2 = TypeDeserializer.deserializeIfNatural(localJsonParser, paramDeserializationContext, localSettableBeanProperty.getType());
/* 148 */           if (localObject2 != null) {
/* 149 */             localSettableBeanProperty.set(paramObject, localObject2);
/* 150 */             continue;
/*     */           }
/*     */           
/* 153 */           if (!this._properties[i].hasDefaultType()) {
/* 154 */             throw paramDeserializationContext.mappingException("Missing external type id property '" + this._properties[i].getTypePropertyName() + "'");
/*     */           }
/* 156 */           str = this._properties[i].getDefaultTypeId();
/*     */         }
/* 158 */       } else if (this._tokens[i] == null) {
/* 159 */         localObject1 = this._properties[i].getProperty();
/* 160 */         throw paramDeserializationContext.mappingException("Missing property '" + ((SettableBeanProperty)localObject1).getName() + "' for external type id '" + this._properties[i].getTypePropertyName());
/*     */       }
/* 162 */       _deserializeAndSet(paramJsonParser, paramDeserializationContext, paramObject, i, str);
/*     */     }
/* 164 */     return paramObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object complete(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, PropertyValueBuffer paramPropertyValueBuffer, PropertyBasedCreator paramPropertyBasedCreator)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 176 */     int i = this._properties.length;
/* 177 */     Object[] arrayOfObject = new Object[i];
/* 178 */     Object localObject2; SettableBeanProperty localSettableBeanProperty; for (int j = 0; j < i; j++) {
/* 179 */       localObject2 = this._typeIds[j];
/* 180 */       if (localObject2 == null)
/*     */       {
/* 182 */         if (this._tokens[j] == null) {
/*     */           continue;
/*     */         }
/*     */         
/*     */ 
/* 187 */         if (!this._properties[j].hasDefaultType()) {
/* 188 */           throw paramDeserializationContext.mappingException("Missing external type id property '" + this._properties[j].getTypePropertyName() + "'");
/*     */         }
/* 190 */         localObject2 = this._properties[j].getDefaultTypeId();
/* 191 */       } else if (this._tokens[j] == null) {
/* 192 */         localSettableBeanProperty = this._properties[j].getProperty();
/* 193 */         throw paramDeserializationContext.mappingException("Missing property '" + localSettableBeanProperty.getName() + "' for external type id '" + this._properties[j].getTypePropertyName());
/*     */       }
/* 195 */       arrayOfObject[j] = _deserialize(paramJsonParser, paramDeserializationContext, j, (String)localObject2);
/*     */     }
/*     */     
/* 198 */     for (j = 0; j < i; j++) {
/* 199 */       localObject2 = this._properties[j].getProperty();
/* 200 */       if (paramPropertyBasedCreator.findCreatorProperty(((SettableBeanProperty)localObject2).getName()) != null) {
/* 201 */         paramPropertyValueBuffer.assignParameter(((SettableBeanProperty)localObject2).getCreatorIndex(), arrayOfObject[j]);
/*     */       }
/*     */     }
/* 204 */     Object localObject1 = paramPropertyBasedCreator.build(paramDeserializationContext, paramPropertyValueBuffer);
/*     */     
/* 206 */     for (int k = 0; k < i; k++) {
/* 207 */       localSettableBeanProperty = this._properties[k].getProperty();
/* 208 */       if (paramPropertyBasedCreator.findCreatorProperty(localSettableBeanProperty.getName()) == null) {
/* 209 */         localSettableBeanProperty.set(localObject1, arrayOfObject[k]);
/*     */       }
/*     */     }
/* 212 */     return localObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected final Object _deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, int paramInt, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 220 */     TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser.getCodec());
/* 221 */     localTokenBuffer.writeStartArray();
/* 222 */     localTokenBuffer.writeString(paramString);
/* 223 */     JsonParser localJsonParser = this._tokens[paramInt].asParser(paramJsonParser);
/* 224 */     localJsonParser.nextToken();
/* 225 */     localTokenBuffer.copyCurrentStructure(localJsonParser);
/* 226 */     localTokenBuffer.writeEndArray();
/*     */     
/*     */ 
/* 229 */     localJsonParser = localTokenBuffer.asParser(paramJsonParser);
/* 230 */     localJsonParser.nextToken();
/* 231 */     return this._properties[paramInt].getProperty().deserialize(localJsonParser, paramDeserializationContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void _deserializeAndSet(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, Object paramObject, int paramInt, String paramString)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 242 */     TokenBuffer localTokenBuffer = new TokenBuffer(paramJsonParser.getCodec());
/* 243 */     localTokenBuffer.writeStartArray();
/* 244 */     localTokenBuffer.writeString(paramString);
/* 245 */     JsonParser localJsonParser = this._tokens[paramInt].asParser(paramJsonParser);
/* 246 */     localJsonParser.nextToken();
/* 247 */     localTokenBuffer.copyCurrentStructure(localJsonParser);
/* 248 */     localTokenBuffer.writeEndArray();
/*     */     
/*     */ 
/* 251 */     localJsonParser = localTokenBuffer.asParser(paramJsonParser);
/* 252 */     localJsonParser.nextToken();
/* 253 */     this._properties[paramInt].getProperty().deserializeAndSet(localJsonParser, paramDeserializationContext, paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class Builder
/*     */   {
/* 264 */     private final ArrayList<ExternalTypeHandler.ExtTypedProperty> _properties = new ArrayList();
/* 265 */     private final HashMap<String, Integer> _nameToPropertyIndex = new HashMap();
/*     */     
/*     */ 
/*     */     public void addExternal(SettableBeanProperty paramSettableBeanProperty, TypeDeserializer paramTypeDeserializer)
/*     */     {
/* 270 */       Integer localInteger = Integer.valueOf(this._properties.size());
/* 271 */       this._properties.add(new ExternalTypeHandler.ExtTypedProperty(paramSettableBeanProperty, paramTypeDeserializer));
/* 272 */       this._nameToPropertyIndex.put(paramSettableBeanProperty.getName(), localInteger);
/* 273 */       this._nameToPropertyIndex.put(paramTypeDeserializer.getPropertyName(), localInteger);
/*     */     }
/*     */     
/*     */     public ExternalTypeHandler build() {
/* 277 */       return new ExternalTypeHandler((ExternalTypeHandler.ExtTypedProperty[])this._properties.toArray(new ExternalTypeHandler.ExtTypedProperty[this._properties.size()]), this._nameToPropertyIndex, null, null);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class ExtTypedProperty
/*     */   {
/*     */     private final SettableBeanProperty _property;
/*     */     private final TypeDeserializer _typeDeserializer;
/*     */     private final String _typePropertyName;
/*     */     
/*     */     public ExtTypedProperty(SettableBeanProperty paramSettableBeanProperty, TypeDeserializer paramTypeDeserializer)
/*     */     {
/* 290 */       this._property = paramSettableBeanProperty;
/* 291 */       this._typeDeserializer = paramTypeDeserializer;
/* 292 */       this._typePropertyName = paramTypeDeserializer.getPropertyName();
/*     */     }
/*     */     
/*     */     public boolean hasTypePropertyName(String paramString) {
/* 296 */       return paramString.equals(this._typePropertyName);
/*     */     }
/*     */     
/*     */     public boolean hasDefaultType() {
/* 300 */       return this._typeDeserializer.getDefaultImpl() != null;
/*     */     }
/*     */     
/*     */     public String getDefaultTypeId() {
/* 304 */       Class localClass = this._typeDeserializer.getDefaultImpl();
/* 305 */       if (localClass == null) {
/* 306 */         return null;
/*     */       }
/* 308 */       return this._typeDeserializer.getTypeIdResolver().idFromValueAndType(null, localClass);
/*     */     }
/*     */     
/* 311 */     public String getTypePropertyName() { return this._typePropertyName; }
/*     */     
/*     */     public SettableBeanProperty getProperty() {
/* 314 */       return this._property;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/ExternalTypeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */