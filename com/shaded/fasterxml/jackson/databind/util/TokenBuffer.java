/*      */ package com.shaded.fasterxml.jackson.databind.util;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*      */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*      */ import com.shaded.fasterxml.jackson.core.JsonGenerator.Feature;
/*      */ import com.shaded.fasterxml.jackson.core.JsonLocation;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParseException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParser.Feature;
/*      */ import com.shaded.fasterxml.jackson.core.JsonParser.NumberType;
/*      */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*      */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*      */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*      */ import com.shaded.fasterxml.jackson.core.SerializableString;
/*      */ import com.shaded.fasterxml.jackson.core.TreeNode;
/*      */ import com.shaded.fasterxml.jackson.core.Version;
/*      */ import com.shaded.fasterxml.jackson.core.base.ParserMinimalBase;
/*      */ import com.shaded.fasterxml.jackson.core.json.JsonReadContext;
/*      */ import com.shaded.fasterxml.jackson.core.json.JsonWriteContext;
/*      */ import com.shaded.fasterxml.jackson.core.util.ByteArrayBuilder;
/*      */ import com.shaded.fasterxml.jackson.databind.cfg.PackageVersion;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ 
/*      */ public class TokenBuffer extends JsonGenerator
/*      */ {
/*   31 */   protected static final int DEFAULT_PARSER_FEATURES = ;
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
/*      */   protected int _generatorFeatures;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean _closed;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Segment _first;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Segment _last;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int _appendOffset;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JsonWriteContext _writeContext;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TokenBuffer(ObjectCodec paramObjectCodec)
/*      */   {
/*  100 */     this._objectCodec = paramObjectCodec;
/*  101 */     this._generatorFeatures = DEFAULT_PARSER_FEATURES;
/*  102 */     this._writeContext = JsonWriteContext.createRootContext();
/*      */     
/*  104 */     this._first = (this._last = new Segment());
/*  105 */     this._appendOffset = 0;
/*      */   }
/*      */   
/*      */   public Version version()
/*      */   {
/*  110 */     return PackageVersion.VERSION;
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
/*      */   public JsonParser asParser()
/*      */   {
/*  125 */     return asParser(this._objectCodec);
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
/*      */   public JsonParser asParser(ObjectCodec paramObjectCodec)
/*      */   {
/*  143 */     return new Parser(this._first, paramObjectCodec);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonParser asParser(JsonParser paramJsonParser)
/*      */   {
/*  152 */     Parser localParser = new Parser(this._first, paramJsonParser.getCodec());
/*  153 */     localParser.setLocation(paramJsonParser.getTokenLocation());
/*  154 */     return localParser;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonToken firstToken()
/*      */   {
/*  164 */     if (this._first != null) {
/*  165 */       return this._first.type(0);
/*      */     }
/*  167 */     return null;
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
/*      */   public TokenBuffer append(TokenBuffer paramTokenBuffer)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  186 */     JsonParser localJsonParser = paramTokenBuffer.asParser();
/*  187 */     while (localJsonParser.nextToken() != null) {
/*  188 */       copyCurrentEvent(localJsonParser);
/*      */     }
/*  190 */     return this;
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
/*      */   public void serialize(JsonGenerator paramJsonGenerator)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  206 */     Segment localSegment = this._first;
/*  207 */     int i = -1;
/*      */     for (;;)
/*      */     {
/*  210 */       i++; if (i >= 16) {
/*  211 */         i = 0;
/*  212 */         localSegment = localSegment.next();
/*  213 */         if (localSegment == null) break;
/*      */       }
/*  215 */       JsonToken localJsonToken = localSegment.type(i);
/*  216 */       if (localJsonToken == null)
/*      */         break;
/*      */       Object localObject;
/*  219 */       switch (localJsonToken) {
/*      */       case START_OBJECT: 
/*  221 */         paramJsonGenerator.writeStartObject();
/*  222 */         break;
/*      */       case END_OBJECT: 
/*  224 */         paramJsonGenerator.writeEndObject();
/*  225 */         break;
/*      */       case START_ARRAY: 
/*  227 */         paramJsonGenerator.writeStartArray();
/*  228 */         break;
/*      */       case END_ARRAY: 
/*  230 */         paramJsonGenerator.writeEndArray();
/*  231 */         break;
/*      */       
/*      */ 
/*      */       case FIELD_NAME: 
/*  235 */         localObject = localSegment.get(i);
/*  236 */         if ((localObject instanceof SerializableString)) {
/*  237 */           paramJsonGenerator.writeFieldName((SerializableString)localObject);
/*      */         } else {
/*  239 */           paramJsonGenerator.writeFieldName((String)localObject);
/*      */         }
/*      */         
/*  242 */         break;
/*      */       
/*      */       case VALUE_STRING: 
/*  245 */         localObject = localSegment.get(i);
/*  246 */         if ((localObject instanceof SerializableString)) {
/*  247 */           paramJsonGenerator.writeString((SerializableString)localObject);
/*      */         } else {
/*  249 */           paramJsonGenerator.writeString((String)localObject);
/*      */         }
/*      */         
/*  252 */         break;
/*      */       
/*      */       case VALUE_NUMBER_INT: 
/*  255 */         localObject = localSegment.get(i);
/*  256 */         if ((localObject instanceof Integer)) {
/*  257 */           paramJsonGenerator.writeNumber(((Integer)localObject).intValue());
/*  258 */         } else if ((localObject instanceof BigInteger)) {
/*  259 */           paramJsonGenerator.writeNumber((BigInteger)localObject);
/*  260 */         } else if ((localObject instanceof Long)) {
/*  261 */           paramJsonGenerator.writeNumber(((Long)localObject).longValue());
/*  262 */         } else if ((localObject instanceof Short)) {
/*  263 */           paramJsonGenerator.writeNumber(((Short)localObject).shortValue());
/*      */         } else {
/*  265 */           paramJsonGenerator.writeNumber(((Number)localObject).intValue());
/*      */         }
/*      */         
/*  268 */         break;
/*      */       
/*      */       case VALUE_NUMBER_FLOAT: 
/*  271 */         localObject = localSegment.get(i);
/*  272 */         if ((localObject instanceof Double)) {
/*  273 */           paramJsonGenerator.writeNumber(((Double)localObject).doubleValue());
/*  274 */         } else if ((localObject instanceof BigDecimal)) {
/*  275 */           paramJsonGenerator.writeNumber((BigDecimal)localObject);
/*  276 */         } else if ((localObject instanceof Float)) {
/*  277 */           paramJsonGenerator.writeNumber(((Float)localObject).floatValue());
/*  278 */         } else if (localObject == null) {
/*  279 */           paramJsonGenerator.writeNull();
/*  280 */         } else if ((localObject instanceof String)) {
/*  281 */           paramJsonGenerator.writeNumber((String)localObject);
/*      */         } else {
/*  283 */           throw new JsonGenerationException("Unrecognized value type for VALUE_NUMBER_FLOAT: " + localObject.getClass().getName() + ", can not serialize");
/*      */         }
/*      */         
/*  286 */         break;
/*      */       case VALUE_TRUE: 
/*  288 */         paramJsonGenerator.writeBoolean(true);
/*  289 */         break;
/*      */       case VALUE_FALSE: 
/*  291 */         paramJsonGenerator.writeBoolean(false);
/*  292 */         break;
/*      */       case VALUE_NULL: 
/*  294 */         paramJsonGenerator.writeNull();
/*  295 */         break;
/*      */       case VALUE_EMBEDDED_OBJECT: 
/*  297 */         paramJsonGenerator.writeObject(localSegment.get(i));
/*  298 */         break;
/*      */       default: 
/*  300 */         throw new RuntimeException("Internal error: should never end up through this code path");
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/*  311 */     StringBuilder localStringBuilder = new StringBuilder();
/*  312 */     localStringBuilder.append("[TokenBuffer: ");
/*  313 */     JsonParser localJsonParser = asParser();
/*  314 */     int i = 0;
/*      */     for (;;)
/*      */     {
/*      */       try
/*      */       {
/*  319 */         JsonToken localJsonToken = localJsonParser.nextToken();
/*  320 */         if (localJsonToken == null) break;
/*  321 */         if (i < 100) {
/*  322 */           if (i > 0) {
/*  323 */             localStringBuilder.append(", ");
/*      */           }
/*  325 */           localStringBuilder.append(localJsonToken.toString());
/*  326 */           if (localJsonToken == JsonToken.FIELD_NAME) {
/*  327 */             localStringBuilder.append('(');
/*  328 */             localStringBuilder.append(localJsonParser.getCurrentName());
/*  329 */             localStringBuilder.append(')');
/*      */           }
/*      */         }
/*      */       } catch (IOException localIOException) {
/*  333 */         throw new IllegalStateException(localIOException);
/*      */       }
/*  335 */       i++;
/*      */     }
/*      */     
/*  338 */     if (i >= 100) {
/*  339 */       localStringBuilder.append(" ... (truncated ").append(i - 100).append(" entries)");
/*      */     }
/*  341 */     localStringBuilder.append(']');
/*  342 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonGenerator enable(JsonGenerator.Feature paramFeature)
/*      */   {
/*  353 */     this._generatorFeatures |= paramFeature.getMask();
/*  354 */     return this;
/*      */   }
/*      */   
/*      */   public JsonGenerator disable(JsonGenerator.Feature paramFeature)
/*      */   {
/*  359 */     this._generatorFeatures &= (paramFeature.getMask() ^ 0xFFFFFFFF);
/*  360 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isEnabled(JsonGenerator.Feature paramFeature)
/*      */   {
/*  367 */     return (this._generatorFeatures & paramFeature.getMask()) != 0;
/*      */   }
/*      */   
/*      */ 
/*      */   public JsonGenerator useDefaultPrettyPrinter()
/*      */   {
/*  373 */     return this;
/*      */   }
/*      */   
/*      */   public JsonGenerator setCodec(ObjectCodec paramObjectCodec)
/*      */   {
/*  378 */     this._objectCodec = paramObjectCodec;
/*  379 */     return this;
/*      */   }
/*      */   
/*      */   public ObjectCodec getCodec() {
/*  383 */     return this._objectCodec;
/*      */   }
/*      */   
/*  386 */   public final JsonWriteContext getOutputContext() { return this._writeContext; }
/*      */   
/*      */ 
/*      */ 
/*      */   public void flush()
/*      */     throws IOException
/*      */   {}
/*      */   
/*      */ 
/*      */ 
/*      */   public void close()
/*      */     throws IOException
/*      */   {
/*  399 */     this._closed = true;
/*      */   }
/*      */   
/*      */   public boolean isClosed() {
/*  403 */     return this._closed;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void writeStartArray()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  415 */     _append(JsonToken.START_ARRAY);
/*  416 */     this._writeContext = this._writeContext.createChildArrayContext();
/*      */   }
/*      */   
/*      */ 
/*      */   public final void writeEndArray()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  423 */     _append(JsonToken.END_ARRAY);
/*      */     
/*  425 */     JsonWriteContext localJsonWriteContext = this._writeContext.getParent();
/*  426 */     if (localJsonWriteContext != null) {
/*  427 */       this._writeContext = localJsonWriteContext;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public final void writeStartObject()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  435 */     _append(JsonToken.START_OBJECT);
/*  436 */     this._writeContext = this._writeContext.createChildObjectContext();
/*      */   }
/*      */   
/*      */ 
/*      */   public final void writeEndObject()
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  443 */     _append(JsonToken.END_OBJECT);
/*      */     
/*  445 */     JsonWriteContext localJsonWriteContext = this._writeContext.getParent();
/*  446 */     if (localJsonWriteContext != null) {
/*  447 */       this._writeContext = localJsonWriteContext;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public final void writeFieldName(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  455 */     _append(JsonToken.FIELD_NAME, paramString);
/*  456 */     this._writeContext.writeFieldName(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeFieldName(SerializableString paramSerializableString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  463 */     _append(JsonToken.FIELD_NAME, paramSerializableString);
/*  464 */     this._writeContext.writeFieldName(paramSerializableString.getValue());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeString(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  475 */     if (paramString == null) {
/*  476 */       writeNull();
/*      */     } else {
/*  478 */       _append(JsonToken.VALUE_STRING, paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   public void writeString(char[] paramArrayOfChar, int paramInt1, int paramInt2) throws IOException, JsonGenerationException
/*      */   {
/*  484 */     writeString(new String(paramArrayOfChar, paramInt1, paramInt2));
/*      */   }
/*      */   
/*      */   public void writeString(SerializableString paramSerializableString) throws IOException, JsonGenerationException
/*      */   {
/*  489 */     if (paramSerializableString == null) {
/*  490 */       writeNull();
/*      */     } else {
/*  492 */       _append(JsonToken.VALUE_STRING, paramSerializableString);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeRawUTF8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  501 */     _reportUnsupportedOperation();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeUTF8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  509 */     _reportUnsupportedOperation();
/*      */   }
/*      */   
/*      */   public void writeRaw(String paramString) throws IOException, JsonGenerationException
/*      */   {
/*  514 */     _reportUnsupportedOperation();
/*      */   }
/*      */   
/*      */   public void writeRaw(String paramString, int paramInt1, int paramInt2) throws IOException, JsonGenerationException
/*      */   {
/*  519 */     _reportUnsupportedOperation();
/*      */   }
/*      */   
/*      */   public void writeRaw(SerializableString paramSerializableString) throws IOException, JsonGenerationException
/*      */   {
/*  524 */     _reportUnsupportedOperation();
/*      */   }
/*      */   
/*      */   public void writeRaw(char[] paramArrayOfChar, int paramInt1, int paramInt2) throws IOException, JsonGenerationException
/*      */   {
/*  529 */     _reportUnsupportedOperation();
/*      */   }
/*      */   
/*      */   public void writeRaw(char paramChar) throws IOException, JsonGenerationException
/*      */   {
/*  534 */     _reportUnsupportedOperation();
/*      */   }
/*      */   
/*      */   public void writeRawValue(String paramString) throws IOException, JsonGenerationException
/*      */   {
/*  539 */     _reportUnsupportedOperation();
/*      */   }
/*      */   
/*      */   public void writeRawValue(String paramString, int paramInt1, int paramInt2) throws IOException, JsonGenerationException
/*      */   {
/*  544 */     _reportUnsupportedOperation();
/*      */   }
/*      */   
/*      */   public void writeRawValue(char[] paramArrayOfChar, int paramInt1, int paramInt2) throws IOException, JsonGenerationException
/*      */   {
/*  549 */     _reportUnsupportedOperation();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeNumber(short paramShort)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  560 */     _append(JsonToken.VALUE_NUMBER_INT, Short.valueOf(paramShort));
/*      */   }
/*      */   
/*      */   public void writeNumber(int paramInt) throws IOException, JsonGenerationException
/*      */   {
/*  565 */     _append(JsonToken.VALUE_NUMBER_INT, Integer.valueOf(paramInt));
/*      */   }
/*      */   
/*      */   public void writeNumber(long paramLong) throws IOException, JsonGenerationException
/*      */   {
/*  570 */     _append(JsonToken.VALUE_NUMBER_INT, Long.valueOf(paramLong));
/*      */   }
/*      */   
/*      */   public void writeNumber(double paramDouble) throws IOException, JsonGenerationException
/*      */   {
/*  575 */     _append(JsonToken.VALUE_NUMBER_FLOAT, Double.valueOf(paramDouble));
/*      */   }
/*      */   
/*      */   public void writeNumber(float paramFloat) throws IOException, JsonGenerationException
/*      */   {
/*  580 */     _append(JsonToken.VALUE_NUMBER_FLOAT, Float.valueOf(paramFloat));
/*      */   }
/*      */   
/*      */   public void writeNumber(BigDecimal paramBigDecimal) throws IOException, JsonGenerationException
/*      */   {
/*  585 */     if (paramBigDecimal == null) {
/*  586 */       writeNull();
/*      */     } else {
/*  588 */       _append(JsonToken.VALUE_NUMBER_FLOAT, paramBigDecimal);
/*      */     }
/*      */   }
/*      */   
/*      */   public void writeNumber(BigInteger paramBigInteger) throws IOException, JsonGenerationException
/*      */   {
/*  594 */     if (paramBigInteger == null) {
/*  595 */       writeNull();
/*      */     } else {
/*  597 */       _append(JsonToken.VALUE_NUMBER_INT, paramBigInteger);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeNumber(String paramString)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  606 */     _append(JsonToken.VALUE_NUMBER_FLOAT, paramString);
/*      */   }
/*      */   
/*      */   public void writeBoolean(boolean paramBoolean) throws IOException, JsonGenerationException
/*      */   {
/*  611 */     _append(paramBoolean ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE);
/*      */   }
/*      */   
/*      */   public void writeNull() throws IOException, JsonGenerationException
/*      */   {
/*  616 */     _append(JsonToken.VALUE_NULL);
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
/*      */   public void writeObject(Object paramObject)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  630 */     _append(JsonToken.VALUE_EMBEDDED_OBJECT, paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeTree(TreeNode paramTreeNode)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  640 */     _append(JsonToken.VALUE_EMBEDDED_OBJECT, paramTreeNode);
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
/*      */   public void writeBinary(Base64Variant paramBase64Variant, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException, JsonGenerationException
/*      */   {
/*  659 */     byte[] arrayOfByte = new byte[paramInt2];
/*  660 */     System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
/*  661 */     writeObject(arrayOfByte);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int writeBinary(Base64Variant paramBase64Variant, InputStream paramInputStream, int paramInt)
/*      */   {
/*  672 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void copyCurrentEvent(JsonParser paramJsonParser)
/*      */     throws IOException, JsonProcessingException
/*      */   {
/*  684 */     switch (paramJsonParser.getCurrentToken()) {
/*      */     case START_OBJECT: 
/*  686 */       writeStartObject();
/*  687 */       break;
/*      */     case END_OBJECT: 
/*  689 */       writeEndObject();
/*  690 */       break;
/*      */     case START_ARRAY: 
/*  692 */       writeStartArray();
/*  693 */       break;
/*      */     case END_ARRAY: 
/*  695 */       writeEndArray();
/*  696 */       break;
/*      */     case FIELD_NAME: 
/*  698 */       writeFieldName(paramJsonParser.getCurrentName());
/*  699 */       break;
/*      */     case VALUE_STRING: 
/*  701 */       if (paramJsonParser.hasTextCharacters()) {
/*  702 */         writeString(paramJsonParser.getTextCharacters(), paramJsonParser.getTextOffset(), paramJsonParser.getTextLength());
/*      */       } else {
/*  704 */         writeString(paramJsonParser.getText());
/*      */       }
/*  706 */       break;
/*      */     case VALUE_NUMBER_INT: 
/*  708 */       switch (paramJsonParser.getNumberType()) {
/*      */       case INT: 
/*  710 */         writeNumber(paramJsonParser.getIntValue());
/*  711 */         break;
/*      */       case BIG_INTEGER: 
/*  713 */         writeNumber(paramJsonParser.getBigIntegerValue());
/*  714 */         break;
/*      */       default: 
/*  716 */         writeNumber(paramJsonParser.getLongValue());
/*      */       }
/*  718 */       break;
/*      */     case VALUE_NUMBER_FLOAT: 
/*  720 */       switch (paramJsonParser.getNumberType()) {
/*      */       case BIG_DECIMAL: 
/*  722 */         writeNumber(paramJsonParser.getDecimalValue());
/*  723 */         break;
/*      */       case FLOAT: 
/*  725 */         writeNumber(paramJsonParser.getFloatValue());
/*  726 */         break;
/*      */       default: 
/*  728 */         writeNumber(paramJsonParser.getDoubleValue());
/*      */       }
/*  730 */       break;
/*      */     case VALUE_TRUE: 
/*  732 */       writeBoolean(true);
/*  733 */       break;
/*      */     case VALUE_FALSE: 
/*  735 */       writeBoolean(false);
/*  736 */       break;
/*      */     case VALUE_NULL: 
/*  738 */       writeNull();
/*  739 */       break;
/*      */     case VALUE_EMBEDDED_OBJECT: 
/*  741 */       writeObject(paramJsonParser.getEmbeddedObject());
/*  742 */       break;
/*      */     default: 
/*  744 */       throw new RuntimeException("Internal error: should never end up through this code path");
/*      */     }
/*      */   }
/*      */   
/*      */   public void copyCurrentStructure(JsonParser paramJsonParser) throws IOException, JsonProcessingException
/*      */   {
/*  750 */     JsonToken localJsonToken = paramJsonParser.getCurrentToken();
/*      */     
/*      */ 
/*  753 */     if (localJsonToken == JsonToken.FIELD_NAME) {
/*  754 */       writeFieldName(paramJsonParser.getCurrentName());
/*  755 */       localJsonToken = paramJsonParser.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  759 */     switch (localJsonToken) {
/*      */     case START_ARRAY: 
/*  761 */       writeStartArray();
/*  762 */       while (paramJsonParser.nextToken() != JsonToken.END_ARRAY) {
/*  763 */         copyCurrentStructure(paramJsonParser);
/*      */       }
/*  765 */       writeEndArray();
/*  766 */       break;
/*      */     case START_OBJECT: 
/*  768 */       writeStartObject();
/*  769 */       while (paramJsonParser.nextToken() != JsonToken.END_OBJECT) {
/*  770 */         copyCurrentStructure(paramJsonParser);
/*      */       }
/*  772 */       writeEndObject();
/*  773 */       break;
/*      */     default: 
/*  775 */       copyCurrentEvent(paramJsonParser);
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void _append(JsonToken paramJsonToken)
/*      */   {
/*  785 */     Segment localSegment = this._last.append(this._appendOffset, paramJsonToken);
/*  786 */     if (localSegment == null) {
/*  787 */       this._appendOffset += 1;
/*      */     } else {
/*  789 */       this._last = localSegment;
/*  790 */       this._appendOffset = 1;
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void _append(JsonToken paramJsonToken, Object paramObject) {
/*  795 */     Segment localSegment = this._last.append(this._appendOffset, paramJsonToken, paramObject);
/*  796 */     if (localSegment == null) {
/*  797 */       this._appendOffset += 1;
/*      */     } else {
/*  799 */       this._last = localSegment;
/*  800 */       this._appendOffset = 1;
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void _appendRaw(int paramInt, Object paramObject) {
/*  805 */     Segment localSegment = this._last.appendRaw(this._appendOffset, paramInt, paramObject);
/*  806 */     if (localSegment == null) {
/*  807 */       this._appendOffset += 1;
/*      */     } else {
/*  809 */       this._last = localSegment;
/*  810 */       this._appendOffset = 1;
/*      */     }
/*      */   }
/*      */   
/*      */   protected void _reportUnsupportedOperation() {
/*  815 */     throw new UnsupportedOperationException("Called operation not supported for TokenBuffer");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final class Parser
/*      */     extends ParserMinimalBase
/*      */   {
/*      */     protected ObjectCodec _codec;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     protected TokenBuffer.Segment _segment;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     protected int _segmentPtr;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     protected JsonReadContext _parsingContext;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     protected boolean _closed;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     protected transient ByteArrayBuilder _byteBuilder;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  855 */     protected JsonLocation _location = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Parser(TokenBuffer.Segment paramSegment, ObjectCodec paramObjectCodec)
/*      */     {
/*  865 */       super();
/*  866 */       this._segment = paramSegment;
/*  867 */       this._segmentPtr = -1;
/*  868 */       this._codec = paramObjectCodec;
/*  869 */       this._parsingContext = JsonReadContext.createRootContext(-1, -1);
/*      */     }
/*      */     
/*      */     public void setLocation(JsonLocation paramJsonLocation) {
/*  873 */       this._location = paramJsonLocation;
/*      */     }
/*      */     
/*      */     public ObjectCodec getCodec() {
/*  877 */       return this._codec;
/*      */     }
/*      */     
/*  880 */     public void setCodec(ObjectCodec paramObjectCodec) { this._codec = paramObjectCodec; }
/*      */     
/*      */     public Version version()
/*      */     {
/*  884 */       return PackageVersion.VERSION;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public JsonToken peekNextToken()
/*      */       throws IOException, JsonParseException
/*      */     {
/*  897 */       if (this._closed) return null;
/*  898 */       TokenBuffer.Segment localSegment = this._segment;
/*  899 */       int i = this._segmentPtr + 1;
/*  900 */       if (i >= 16) {
/*  901 */         i = 0;
/*  902 */         localSegment = localSegment == null ? null : localSegment.next();
/*      */       }
/*  904 */       return localSegment == null ? null : localSegment.type(i);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void close()
/*      */       throws IOException
/*      */     {
/*  915 */       if (!this._closed) {
/*  916 */         this._closed = true;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public JsonToken nextToken()
/*      */       throws IOException, JsonParseException
/*      */     {
/*  930 */       if ((this._closed) || (this._segment == null)) { return null;
/*      */       }
/*      */       
/*  933 */       if (++this._segmentPtr >= 16) {
/*  934 */         this._segmentPtr = 0;
/*  935 */         this._segment = this._segment.next();
/*  936 */         if (this._segment == null) {
/*  937 */           return null;
/*      */         }
/*      */       }
/*  940 */       this._currToken = this._segment.type(this._segmentPtr);
/*      */       
/*  942 */       if (this._currToken == JsonToken.FIELD_NAME) {
/*  943 */         Object localObject = _currentObject();
/*  944 */         String str = (localObject instanceof String) ? (String)localObject : localObject.toString();
/*  945 */         this._parsingContext.setCurrentName(str);
/*  946 */       } else if (this._currToken == JsonToken.START_OBJECT) {
/*  947 */         this._parsingContext = this._parsingContext.createChildObjectContext(-1, -1);
/*  948 */       } else if (this._currToken == JsonToken.START_ARRAY) {
/*  949 */         this._parsingContext = this._parsingContext.createChildArrayContext(-1, -1);
/*  950 */       } else if ((this._currToken == JsonToken.END_OBJECT) || (this._currToken == JsonToken.END_ARRAY))
/*      */       {
/*      */ 
/*  953 */         this._parsingContext = this._parsingContext.getParent();
/*      */         
/*  955 */         if (this._parsingContext == null) {
/*  956 */           this._parsingContext = JsonReadContext.createRootContext(-1, -1);
/*      */         }
/*      */       }
/*  959 */       return this._currToken;
/*      */     }
/*      */     
/*      */     public boolean isClosed() {
/*  963 */       return this._closed;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public com.shaded.fasterxml.jackson.core.JsonStreamContext getParsingContext()
/*      */     {
/*  972 */       return this._parsingContext;
/*      */     }
/*      */     
/*  975 */     public JsonLocation getTokenLocation() { return getCurrentLocation(); }
/*      */     
/*      */     public JsonLocation getCurrentLocation()
/*      */     {
/*  979 */       return this._location == null ? JsonLocation.NA : this._location;
/*      */     }
/*      */     
/*      */     public String getCurrentName() {
/*  983 */       return this._parsingContext.getCurrentName();
/*      */     }
/*      */     
/*      */ 
/*      */     public void overrideCurrentName(String paramString)
/*      */     {
/*  989 */       JsonReadContext localJsonReadContext = this._parsingContext;
/*  990 */       if ((this._currToken == JsonToken.START_OBJECT) || (this._currToken == JsonToken.START_ARRAY)) {
/*  991 */         localJsonReadContext = localJsonReadContext.getParent();
/*      */       }
/*  993 */       localJsonReadContext.setCurrentName(paramString);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public String getText()
/*      */     {
/*      */       Object localObject;
/*      */       
/*      */ 
/*      */ 
/* 1006 */       if ((this._currToken == JsonToken.VALUE_STRING) || (this._currToken == JsonToken.FIELD_NAME))
/*      */       {
/* 1008 */         localObject = _currentObject();
/* 1009 */         if ((localObject instanceof String)) {
/* 1010 */           return (String)localObject;
/*      */         }
/* 1012 */         return localObject == null ? null : localObject.toString();
/*      */       }
/* 1014 */       if (this._currToken == null) {
/* 1015 */         return null;
/*      */       }
/* 1017 */       switch (TokenBuffer.1.$SwitchMap$com$fasterxml$jackson$core$JsonToken[this._currToken.ordinal()]) {
/*      */       case 7: 
/*      */       case 8: 
/* 1020 */         localObject = _currentObject();
/* 1021 */         return localObject == null ? null : localObject.toString();
/*      */       }
/* 1023 */       return this._currToken.asString();
/*      */     }
/*      */     
/*      */ 
/*      */     public char[] getTextCharacters()
/*      */     {
/* 1029 */       String str = getText();
/* 1030 */       return str == null ? null : str.toCharArray();
/*      */     }
/*      */     
/*      */     public int getTextLength()
/*      */     {
/* 1035 */       String str = getText();
/* 1036 */       return str == null ? 0 : str.length();
/*      */     }
/*      */     
/*      */     public int getTextOffset() {
/* 1040 */       return 0;
/*      */     }
/*      */     
/*      */     public boolean hasTextCharacters()
/*      */     {
/* 1045 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public BigInteger getBigIntegerValue()
/*      */       throws IOException, JsonParseException
/*      */     {
/* 1057 */       Number localNumber = getNumberValue();
/* 1058 */       if ((localNumber instanceof BigInteger)) {
/* 1059 */         return (BigInteger)localNumber;
/*      */       }
/* 1061 */       if (getNumberType() == JsonParser.NumberType.BIG_DECIMAL) {
/* 1062 */         return ((BigDecimal)localNumber).toBigInteger();
/*      */       }
/*      */       
/* 1065 */       return BigInteger.valueOf(localNumber.longValue());
/*      */     }
/*      */     
/*      */     public BigDecimal getDecimalValue()
/*      */       throws IOException, JsonParseException
/*      */     {
/* 1071 */       Number localNumber = getNumberValue();
/* 1072 */       if ((localNumber instanceof BigDecimal)) {
/* 1073 */         return (BigDecimal)localNumber;
/*      */       }
/* 1075 */       switch (TokenBuffer.1.$SwitchMap$com$fasterxml$jackson$core$JsonParser$NumberType[getNumberType().ordinal()]) {
/*      */       case 1: 
/*      */       case 5: 
/* 1078 */         return BigDecimal.valueOf(localNumber.longValue());
/*      */       case 2: 
/* 1080 */         return new BigDecimal((BigInteger)localNumber);
/*      */       }
/*      */       
/*      */       
/* 1084 */       return BigDecimal.valueOf(localNumber.doubleValue());
/*      */     }
/*      */     
/*      */     public double getDoubleValue() throws IOException, JsonParseException
/*      */     {
/* 1089 */       return getNumberValue().doubleValue();
/*      */     }
/*      */     
/*      */     public float getFloatValue() throws IOException, JsonParseException
/*      */     {
/* 1094 */       return getNumberValue().floatValue();
/*      */     }
/*      */     
/*      */ 
/*      */     public int getIntValue()
/*      */       throws IOException, JsonParseException
/*      */     {
/* 1101 */       if (this._currToken == JsonToken.VALUE_NUMBER_INT) {
/* 1102 */         return ((Number)_currentObject()).intValue();
/*      */       }
/* 1104 */       return getNumberValue().intValue();
/*      */     }
/*      */     
/*      */     public long getLongValue() throws IOException, JsonParseException
/*      */     {
/* 1109 */       return getNumberValue().longValue();
/*      */     }
/*      */     
/*      */     public JsonParser.NumberType getNumberType()
/*      */       throws IOException, JsonParseException
/*      */     {
/* 1115 */       Number localNumber = getNumberValue();
/* 1116 */       if ((localNumber instanceof Integer)) return JsonParser.NumberType.INT;
/* 1117 */       if ((localNumber instanceof Long)) return JsonParser.NumberType.LONG;
/* 1118 */       if ((localNumber instanceof Double)) return JsonParser.NumberType.DOUBLE;
/* 1119 */       if ((localNumber instanceof BigDecimal)) return JsonParser.NumberType.BIG_DECIMAL;
/* 1120 */       if ((localNumber instanceof BigInteger)) return JsonParser.NumberType.BIG_INTEGER;
/* 1121 */       if ((localNumber instanceof Float)) return JsonParser.NumberType.FLOAT;
/* 1122 */       if ((localNumber instanceof Short)) return JsonParser.NumberType.INT;
/* 1123 */       return null;
/*      */     }
/*      */     
/*      */     public final Number getNumberValue() throws IOException, JsonParseException
/*      */     {
/* 1128 */       _checkIsNumber();
/* 1129 */       Object localObject = _currentObject();
/* 1130 */       if ((localObject instanceof Number)) {
/* 1131 */         return (Number)localObject;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1136 */       if ((localObject instanceof String)) {
/* 1137 */         String str = (String)localObject;
/* 1138 */         if (str.indexOf('.') >= 0) {
/* 1139 */           return Double.valueOf(Double.parseDouble(str));
/*      */         }
/* 1141 */         return Long.valueOf(Long.parseLong(str));
/*      */       }
/* 1143 */       if (localObject == null) {
/* 1144 */         return null;
/*      */       }
/* 1146 */       throw new IllegalStateException("Internal error: entry should be a Number, but is of type " + localObject.getClass().getName());
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
/*      */     public Object getEmbeddedObject()
/*      */     {
/* 1159 */       if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
/* 1160 */         return _currentObject();
/*      */       }
/* 1162 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */     public byte[] getBinaryValue(Base64Variant paramBase64Variant)
/*      */       throws IOException, JsonParseException
/*      */     {
/* 1169 */       if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT)
/*      */       {
/* 1171 */         localObject = _currentObject();
/* 1172 */         if ((localObject instanceof byte[])) {
/* 1173 */           return (byte[])localObject;
/*      */         }
/*      */       }
/*      */       
/* 1177 */       if (this._currToken != JsonToken.VALUE_STRING) {
/* 1178 */         throw _constructError("Current token (" + this._currToken + ") not VALUE_STRING (or VALUE_EMBEDDED_OBJECT with byte[]), can not access as binary");
/*      */       }
/* 1180 */       Object localObject = getText();
/* 1181 */       if (localObject == null) {
/* 1182 */         return null;
/*      */       }
/* 1184 */       ByteArrayBuilder localByteArrayBuilder = this._byteBuilder;
/* 1185 */       if (localByteArrayBuilder == null) {
/* 1186 */         this._byteBuilder = (localByteArrayBuilder = new ByteArrayBuilder(100));
/*      */       } else {
/* 1188 */         this._byteBuilder.reset();
/*      */       }
/* 1190 */       _decodeBase64((String)localObject, localByteArrayBuilder, paramBase64Variant);
/* 1191 */       return localByteArrayBuilder.toByteArray();
/*      */     }
/*      */     
/*      */     public int readBinaryValue(Base64Variant paramBase64Variant, OutputStream paramOutputStream)
/*      */       throws IOException, JsonParseException
/*      */     {
/* 1197 */       byte[] arrayOfByte = getBinaryValue(paramBase64Variant);
/* 1198 */       if (arrayOfByte != null) {
/* 1199 */         paramOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
/* 1200 */         return arrayOfByte.length;
/*      */       }
/* 1202 */       return 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected final Object _currentObject()
/*      */     {
/* 1212 */       return this._segment.get(this._segmentPtr);
/*      */     }
/*      */     
/*      */     protected final void _checkIsNumber() throws JsonParseException
/*      */     {
/* 1217 */       if ((this._currToken == null) || (!this._currToken.isNumeric())) {
/* 1218 */         throw _constructError("Current token (" + this._currToken + ") not numeric, can not use numeric value accessors");
/*      */       }
/*      */     }
/*      */     
/*      */     protected void _handleEOF() throws JsonParseException
/*      */     {
/* 1224 */       _throwInternal();
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
/*      */   protected static final class Segment
/*      */   {
/*      */     public static final int TOKENS_PER_SEGMENT = 16;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1246 */     private static final JsonToken[] TOKEN_TYPES_BY_INDEX = new JsonToken[16];
/* 1247 */     static { JsonToken[] arrayOfJsonToken = JsonToken.values();
/* 1248 */       System.arraycopy(arrayOfJsonToken, 1, TOKEN_TYPES_BY_INDEX, 1, Math.min(15, arrayOfJsonToken.length - 1));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected Segment _next;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     protected long _tokenTypes;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1266 */     protected final Object[] _tokens = new Object[16];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public JsonToken type(int paramInt)
/*      */     {
/* 1274 */       long l = this._tokenTypes;
/* 1275 */       if (paramInt > 0) {
/* 1276 */         l >>= paramInt << 2;
/*      */       }
/* 1278 */       int i = (int)l & 0xF;
/* 1279 */       return TOKEN_TYPES_BY_INDEX[i];
/*      */     }
/*      */     
/*      */     public int rawType(int paramInt)
/*      */     {
/* 1284 */       long l = this._tokenTypes;
/* 1285 */       if (paramInt > 0) {
/* 1286 */         l >>= paramInt << 2;
/*      */       }
/* 1288 */       int i = (int)l & 0xF;
/* 1289 */       return i;
/*      */     }
/*      */     
/*      */     public Object get(int paramInt) {
/* 1293 */       return this._tokens[paramInt];
/*      */     }
/*      */     
/* 1296 */     public Segment next() { return this._next; }
/*      */     
/*      */ 
/*      */ 
/*      */     public Segment append(int paramInt, JsonToken paramJsonToken)
/*      */     {
/* 1302 */       if (paramInt < 16) {
/* 1303 */         set(paramInt, paramJsonToken);
/* 1304 */         return null;
/*      */       }
/* 1306 */       this._next = new Segment();
/* 1307 */       this._next.set(0, paramJsonToken);
/* 1308 */       return this._next;
/*      */     }
/*      */     
/*      */     public Segment append(int paramInt, JsonToken paramJsonToken, Object paramObject)
/*      */     {
/* 1313 */       if (paramInt < 16) {
/* 1314 */         set(paramInt, paramJsonToken, paramObject);
/* 1315 */         return null;
/*      */       }
/* 1317 */       this._next = new Segment();
/* 1318 */       this._next.set(0, paramJsonToken, paramObject);
/* 1319 */       return this._next;
/*      */     }
/*      */     
/*      */     public Segment appendRaw(int paramInt1, int paramInt2, Object paramObject)
/*      */     {
/* 1324 */       if (paramInt1 < 16) {
/* 1325 */         set(paramInt1, paramInt2, paramObject);
/* 1326 */         return null;
/*      */       }
/* 1328 */       this._next = new Segment();
/* 1329 */       this._next.set(0, paramInt2, paramObject);
/* 1330 */       return this._next;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void set(int paramInt, JsonToken paramJsonToken)
/*      */     {
/* 1338 */       long l = paramJsonToken.ordinal();
/* 1339 */       if (paramInt > 0) {
/* 1340 */         l <<= paramInt << 2;
/*      */       }
/* 1342 */       this._tokenTypes |= l;
/*      */     }
/*      */     
/*      */     public void set(int paramInt, JsonToken paramJsonToken, Object paramObject)
/*      */     {
/* 1347 */       this._tokens[paramInt] = paramObject;
/* 1348 */       long l = paramJsonToken.ordinal();
/*      */       
/*      */ 
/*      */ 
/* 1352 */       if (paramInt > 0) {
/* 1353 */         l <<= paramInt << 2;
/*      */       }
/* 1355 */       this._tokenTypes |= l;
/*      */     }
/*      */     
/*      */     private void set(int paramInt1, int paramInt2, Object paramObject)
/*      */     {
/* 1360 */       this._tokens[paramInt1] = paramObject;
/* 1361 */       long l = paramInt2;
/* 1362 */       if (paramInt1 > 0) {
/* 1363 */         l <<= paramInt1 << 2;
/*      */       }
/* 1365 */       this._tokenTypes |= l;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/TokenBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */