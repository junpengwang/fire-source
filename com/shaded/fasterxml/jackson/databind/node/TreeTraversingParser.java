/*     */ package com.shaded.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.core.JsonLocation;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParseException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*     */ import com.shaded.fasterxml.jackson.core.JsonStreamContext;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*     */ import com.shaded.fasterxml.jackson.core.Version;
/*     */ import com.shaded.fasterxml.jackson.core.base.ParserMinimalBase;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonNode;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.PackageVersion;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
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
/*     */ public class TreeTraversingParser
/*     */   extends ParserMinimalBase
/*     */ {
/*     */   protected ObjectCodec _objectCodec;
/*     */   protected NodeCursor _nodeCursor;
/*     */   protected JsonToken _nextToken;
/*     */   protected boolean _startContainer;
/*     */   protected boolean _closed;
/*     */   
/*     */   public TreeTraversingParser(JsonNode paramJsonNode)
/*     */   {
/*  67 */     this(paramJsonNode, null);
/*     */   }
/*     */   
/*     */   public TreeTraversingParser(JsonNode paramJsonNode, ObjectCodec paramObjectCodec) {
/*  71 */     super(0);
/*  72 */     this._objectCodec = paramObjectCodec;
/*  73 */     if (paramJsonNode.isArray()) {
/*  74 */       this._nextToken = JsonToken.START_ARRAY;
/*  75 */       this._nodeCursor = new NodeCursor.Array(paramJsonNode, null);
/*  76 */     } else if (paramJsonNode.isObject()) {
/*  77 */       this._nextToken = JsonToken.START_OBJECT;
/*  78 */       this._nodeCursor = new NodeCursor.Object(paramJsonNode, null);
/*     */     } else {
/*  80 */       this._nodeCursor = new NodeCursor.RootValue(paramJsonNode, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setCodec(ObjectCodec paramObjectCodec)
/*     */   {
/*  86 */     this._objectCodec = paramObjectCodec;
/*     */   }
/*     */   
/*     */   public ObjectCodec getCodec()
/*     */   {
/*  91 */     return this._objectCodec;
/*     */   }
/*     */   
/*     */   public Version version()
/*     */   {
/*  96 */     return PackageVersion.VERSION;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 108 */     if (!this._closed) {
/* 109 */       this._closed = true;
/* 110 */       this._nodeCursor = null;
/* 111 */       this._currToken = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonToken nextToken()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 124 */     if (this._nextToken != null) {
/* 125 */       this._currToken = this._nextToken;
/* 126 */       this._nextToken = null;
/* 127 */       return this._currToken;
/*     */     }
/*     */     
/* 130 */     if (this._startContainer) {
/* 131 */       this._startContainer = false;
/*     */       
/* 133 */       if (!this._nodeCursor.currentHasChildren()) {
/* 134 */         this._currToken = (this._currToken == JsonToken.START_OBJECT ? JsonToken.END_OBJECT : JsonToken.END_ARRAY);
/*     */         
/* 136 */         return this._currToken;
/*     */       }
/* 138 */       this._nodeCursor = this._nodeCursor.iterateChildren();
/* 139 */       this._currToken = this._nodeCursor.nextToken();
/* 140 */       if ((this._currToken == JsonToken.START_OBJECT) || (this._currToken == JsonToken.START_ARRAY)) {
/* 141 */         this._startContainer = true;
/*     */       }
/* 143 */       return this._currToken;
/*     */     }
/*     */     
/* 146 */     if (this._nodeCursor == null) {
/* 147 */       this._closed = true;
/* 148 */       return null;
/*     */     }
/*     */     
/* 151 */     this._currToken = this._nodeCursor.nextToken();
/* 152 */     if (this._currToken != null) {
/* 153 */       if ((this._currToken == JsonToken.START_OBJECT) || (this._currToken == JsonToken.START_ARRAY)) {
/* 154 */         this._startContainer = true;
/*     */       }
/* 156 */       return this._currToken;
/*     */     }
/*     */     
/* 159 */     this._currToken = this._nodeCursor.endToken();
/* 160 */     this._nodeCursor = this._nodeCursor.getParent();
/* 161 */     return this._currToken;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonParser skipChildren()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 170 */     if (this._currToken == JsonToken.START_OBJECT) {
/* 171 */       this._startContainer = false;
/* 172 */       this._currToken = JsonToken.END_OBJECT;
/* 173 */     } else if (this._currToken == JsonToken.START_ARRAY) {
/* 174 */       this._startContainer = false;
/* 175 */       this._currToken = JsonToken.END_ARRAY;
/*     */     }
/* 177 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isClosed()
/*     */   {
/* 182 */     return this._closed;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCurrentName()
/*     */   {
/* 193 */     return this._nodeCursor == null ? null : this._nodeCursor.getCurrentName();
/*     */   }
/*     */   
/*     */ 
/*     */   public void overrideCurrentName(String paramString)
/*     */   {
/* 199 */     if (this._nodeCursor != null) {
/* 200 */       this._nodeCursor.overrideCurrentName(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public JsonStreamContext getParsingContext()
/*     */   {
/* 206 */     return this._nodeCursor;
/*     */   }
/*     */   
/*     */   public JsonLocation getTokenLocation()
/*     */   {
/* 211 */     return JsonLocation.NA;
/*     */   }
/*     */   
/*     */   public JsonLocation getCurrentLocation()
/*     */   {
/* 216 */     return JsonLocation.NA;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getText()
/*     */   {
/* 228 */     if (this._closed) {
/* 229 */       return null;
/*     */     }
/*     */     
/* 232 */     switch (this._currToken) {
/*     */     case FIELD_NAME: 
/* 234 */       return this._nodeCursor.getCurrentName();
/*     */     case VALUE_STRING: 
/* 236 */       return currentNode().textValue();
/*     */     case VALUE_NUMBER_INT: 
/*     */     case VALUE_NUMBER_FLOAT: 
/* 239 */       return String.valueOf(currentNode().numberValue());
/*     */     case VALUE_EMBEDDED_OBJECT: 
/* 241 */       JsonNode localJsonNode = currentNode();
/* 242 */       if ((localJsonNode != null) && (localJsonNode.isBinary()))
/*     */       {
/* 244 */         return localJsonNode.asText(); }
/*     */       break;
/*     */     }
/* 247 */     return this._currToken == null ? null : this._currToken.asString();
/*     */   }
/*     */   
/*     */   public char[] getTextCharacters()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 253 */     return getText().toCharArray();
/*     */   }
/*     */   
/*     */   public int getTextLength() throws IOException, JsonParseException
/*     */   {
/* 258 */     return getText().length();
/*     */   }
/*     */   
/*     */   public int getTextOffset() throws IOException, JsonParseException
/*     */   {
/* 263 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasTextCharacters()
/*     */   {
/* 269 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonParser.NumberType getNumberType()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 282 */     JsonNode localJsonNode = currentNumericNode();
/* 283 */     return localJsonNode == null ? null : localJsonNode.numberType();
/*     */   }
/*     */   
/*     */   public BigInteger getBigIntegerValue()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 289 */     return currentNumericNode().bigIntegerValue();
/*     */   }
/*     */   
/*     */   public BigDecimal getDecimalValue() throws IOException, JsonParseException
/*     */   {
/* 294 */     return currentNumericNode().decimalValue();
/*     */   }
/*     */   
/*     */   public double getDoubleValue() throws IOException, JsonParseException
/*     */   {
/* 299 */     return currentNumericNode().doubleValue();
/*     */   }
/*     */   
/*     */   public float getFloatValue() throws IOException, JsonParseException
/*     */   {
/* 304 */     return (float)currentNumericNode().doubleValue();
/*     */   }
/*     */   
/*     */   public long getLongValue() throws IOException, JsonParseException
/*     */   {
/* 309 */     return currentNumericNode().longValue();
/*     */   }
/*     */   
/*     */   public int getIntValue() throws IOException, JsonParseException
/*     */   {
/* 314 */     return currentNumericNode().intValue();
/*     */   }
/*     */   
/*     */   public Number getNumberValue() throws IOException, JsonParseException
/*     */   {
/* 319 */     return currentNumericNode().numberValue();
/*     */   }
/*     */   
/*     */ 
/*     */   public Object getEmbeddedObject()
/*     */   {
/* 325 */     if (!this._closed) {
/* 326 */       JsonNode localJsonNode = currentNode();
/* 327 */       if (localJsonNode != null) {
/* 328 */         if (localJsonNode.isPojo()) {
/* 329 */           return ((POJONode)localJsonNode).getPojo();
/*     */         }
/* 331 */         if (localJsonNode.isBinary()) {
/* 332 */           return ((BinaryNode)localJsonNode).binaryValue();
/*     */         }
/*     */       }
/*     */     }
/* 336 */     return null;
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
/*     */   public byte[] getBinaryValue(Base64Variant paramBase64Variant)
/*     */     throws IOException, JsonParseException
/*     */   {
/* 350 */     JsonNode localJsonNode = currentNode();
/* 351 */     if (localJsonNode != null) {
/* 352 */       byte[] arrayOfByte = localJsonNode.binaryValue();
/*     */       
/* 354 */       if (arrayOfByte != null) {
/* 355 */         return arrayOfByte;
/*     */       }
/*     */       
/* 358 */       if (localJsonNode.isPojo()) {
/* 359 */         Object localObject = ((POJONode)localJsonNode).getPojo();
/* 360 */         if ((localObject instanceof byte[])) {
/* 361 */           return (byte[])localObject;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 366 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int readBinaryValue(Base64Variant paramBase64Variant, OutputStream paramOutputStream)
/*     */     throws IOException, JsonParseException
/*     */   {
/* 374 */     byte[] arrayOfByte = getBinaryValue(paramBase64Variant);
/* 375 */     if (arrayOfByte != null) {
/* 376 */       paramOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
/* 377 */       return arrayOfByte.length;
/*     */     }
/* 379 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected JsonNode currentNode()
/*     */   {
/* 389 */     if ((this._closed) || (this._nodeCursor == null)) {
/* 390 */       return null;
/*     */     }
/* 392 */     return this._nodeCursor.currentNode();
/*     */   }
/*     */   
/*     */   protected JsonNode currentNumericNode()
/*     */     throws JsonParseException
/*     */   {
/* 398 */     JsonNode localJsonNode = currentNode();
/* 399 */     if ((localJsonNode == null) || (!localJsonNode.isNumber())) {
/* 400 */       JsonToken localJsonToken = localJsonNode == null ? null : localJsonNode.asToken();
/* 401 */       throw _constructError("Current token (" + localJsonToken + ") not numeric, can not use numeric value accessors");
/*     */     }
/* 403 */     return localJsonNode;
/*     */   }
/*     */   
/*     */   protected void _handleEOF() throws JsonParseException
/*     */   {
/* 408 */     _throwInternal();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/TreeTraversingParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */