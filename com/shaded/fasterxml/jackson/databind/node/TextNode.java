/*     */ package com.shaded.fasterxml.jackson.databind.node;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variant;
/*     */ import com.shaded.fasterxml.jackson.core.Base64Variants;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonLocation;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParseException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.core.io.CharTypes;
/*     */ import com.shaded.fasterxml.jackson.core.io.NumberInput;
/*     */ import com.shaded.fasterxml.jackson.core.util.ByteArrayBuilder;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public final class TextNode
/*     */   extends ValueNode
/*     */ {
/*     */   static final int INT_SPACE = 32;
/*  20 */   static final TextNode EMPTY_STRING_NODE = new TextNode("");
/*     */   final String _value;
/*     */   
/*     */   public TextNode(String paramString) {
/*  24 */     this._value = paramString;
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
/*     */   public static TextNode valueOf(String paramString)
/*     */   {
/*  37 */     if (paramString == null) {
/*  38 */       return null;
/*     */     }
/*  40 */     if (paramString.length() == 0) {
/*  41 */       return EMPTY_STRING_NODE;
/*     */     }
/*  43 */     return new TextNode(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonNodeType getNodeType()
/*     */   {
/*  49 */     return JsonNodeType.STRING;
/*     */   }
/*     */   
/*  52 */   public JsonToken asToken() { return JsonToken.VALUE_STRING; }
/*     */   
/*     */   public String textValue()
/*     */   {
/*  56 */     return this._value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] getBinaryValue(Base64Variant paramBase64Variant)
/*     */     throws IOException
/*     */   {
/*  68 */     ByteArrayBuilder localByteArrayBuilder = new ByteArrayBuilder(100);
/*  69 */     String str = this._value;
/*  70 */     int i = 0;
/*  71 */     int j = str.length();
/*     */     
/*     */ 
/*  74 */     while (i < j)
/*     */     {
/*     */       do
/*     */       {
/*  78 */         c = str.charAt(i++);
/*  79 */         if (i >= j) {
/*     */           break;
/*     */         }
/*  82 */       } while (c <= ' ');
/*  83 */       int k = paramBase64Variant.decodeBase64Char(c);
/*  84 */       if (k < 0) {
/*  85 */         _reportInvalidBase64(paramBase64Variant, c, 0);
/*     */       }
/*  87 */       int m = k;
/*     */       
/*  89 */       if (i >= j) {
/*  90 */         _reportBase64EOF();
/*     */       }
/*  92 */       char c = str.charAt(i++);
/*  93 */       k = paramBase64Variant.decodeBase64Char(c);
/*  94 */       if (k < 0) {
/*  95 */         _reportInvalidBase64(paramBase64Variant, c, 1);
/*     */       }
/*  97 */       m = m << 6 | k;
/*     */       
/*  99 */       if (i >= j)
/*     */       {
/* 101 */         if (!paramBase64Variant.usesPadding())
/*     */         {
/* 103 */           m >>= 4;
/* 104 */           localByteArrayBuilder.append(m);
/* 105 */           break;
/*     */         }
/* 107 */         _reportBase64EOF();
/*     */       }
/* 109 */       c = str.charAt(i++);
/* 110 */       k = paramBase64Variant.decodeBase64Char(c);
/*     */       
/*     */ 
/* 113 */       if (k < 0) {
/* 114 */         if (k != -2) {
/* 115 */           _reportInvalidBase64(paramBase64Variant, c, 2);
/*     */         }
/*     */         
/* 118 */         if (i >= j) {
/* 119 */           _reportBase64EOF();
/*     */         }
/* 121 */         c = str.charAt(i++);
/* 122 */         if (!paramBase64Variant.usesPaddingChar(c)) {
/* 123 */           _reportInvalidBase64(paramBase64Variant, c, 3, "expected padding character '" + paramBase64Variant.getPaddingChar() + "'");
/*     */         }
/*     */         
/* 126 */         m >>= 4;
/* 127 */         localByteArrayBuilder.append(m);
/*     */       }
/*     */       else
/*     */       {
/* 131 */         m = m << 6 | k;
/*     */         
/* 133 */         if (i >= j)
/*     */         {
/* 135 */           if (!paramBase64Variant.usesPadding()) {
/* 136 */             m >>= 2;
/* 137 */             localByteArrayBuilder.appendTwoBytes(m);
/* 138 */             break;
/*     */           }
/* 140 */           _reportBase64EOF();
/*     */         }
/* 142 */         c = str.charAt(i++);
/* 143 */         k = paramBase64Variant.decodeBase64Char(c);
/* 144 */         if (k < 0) {
/* 145 */           if (k != -2) {
/* 146 */             _reportInvalidBase64(paramBase64Variant, c, 3);
/*     */           }
/* 148 */           m >>= 2;
/* 149 */           localByteArrayBuilder.appendTwoBytes(m);
/*     */         }
/*     */         else {
/* 152 */           m = m << 6 | k;
/* 153 */           localByteArrayBuilder.appendThreeBytes(m);
/*     */         }
/*     */       } }
/* 156 */     return localByteArrayBuilder.toByteArray();
/*     */   }
/*     */   
/*     */   public byte[] binaryValue()
/*     */     throws IOException
/*     */   {
/* 162 */     return getBinaryValue(Base64Variants.getDefaultVariant());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String asText()
/*     */   {
/* 173 */     return this._value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean asBoolean(boolean paramBoolean)
/*     */   {
/* 180 */     if ((this._value != null) && 
/* 181 */       ("true".equals(this._value.trim()))) {
/* 182 */       return true;
/*     */     }
/*     */     
/* 185 */     return paramBoolean;
/*     */   }
/*     */   
/*     */   public int asInt(int paramInt)
/*     */   {
/* 190 */     return NumberInput.parseAsInt(this._value, paramInt);
/*     */   }
/*     */   
/*     */   public long asLong(long paramLong)
/*     */   {
/* 195 */     return NumberInput.parseAsLong(this._value, paramLong);
/*     */   }
/*     */   
/*     */   public double asDouble(double paramDouble)
/*     */   {
/* 200 */     return NumberInput.parseAsDouble(this._value, paramDouble);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 213 */     if (this._value == null) {
/* 214 */       paramJsonGenerator.writeNull();
/*     */     } else {
/* 216 */       paramJsonGenerator.writeString(this._value);
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 229 */     if (paramObject == this) return true;
/* 230 */     if (paramObject == null) return false;
/* 231 */     if (paramObject.getClass() != getClass()) {
/* 232 */       return false;
/*     */     }
/* 234 */     return ((TextNode)paramObject)._value.equals(this._value);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 238 */     return this._value.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 246 */     int i = this._value.length();
/* 247 */     i = i + 2 + (i >> 4);
/* 248 */     StringBuilder localStringBuilder = new StringBuilder(i);
/* 249 */     appendQuoted(localStringBuilder, this._value);
/* 250 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   protected static void appendQuoted(StringBuilder paramStringBuilder, String paramString)
/*     */   {
/* 255 */     paramStringBuilder.append('"');
/* 256 */     CharTypes.appendQuoted(paramStringBuilder, paramString);
/* 257 */     paramStringBuilder.append('"');
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _reportInvalidBase64(Base64Variant paramBase64Variant, char paramChar, int paramInt)
/*     */     throws JsonParseException
/*     */   {
/* 269 */     _reportInvalidBase64(paramBase64Variant, paramChar, paramInt, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void _reportInvalidBase64(Base64Variant paramBase64Variant, char paramChar, int paramInt, String paramString)
/*     */     throws JsonParseException
/*     */   {
/*     */     String str;
/*     */     
/*     */ 
/* 280 */     if (paramChar <= ' ') {
/* 281 */       str = "Illegal white space character (code 0x" + Integer.toHexString(paramChar) + ") as character #" + (paramInt + 1) + " of 4-char base64 unit: can only used between units";
/* 282 */     } else if (paramBase64Variant.usesPaddingChar(paramChar)) {
/* 283 */       str = "Unexpected padding character ('" + paramBase64Variant.getPaddingChar() + "') as character #" + (paramInt + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
/* 284 */     } else if ((!Character.isDefined(paramChar)) || (Character.isISOControl(paramChar)))
/*     */     {
/* 286 */       str = "Illegal character (code 0x" + Integer.toHexString(paramChar) + ") in base64 content";
/*     */     } else {
/* 288 */       str = "Illegal character '" + paramChar + "' (code 0x" + Integer.toHexString(paramChar) + ") in base64 content";
/*     */     }
/* 290 */     if (paramString != null) {
/* 291 */       str = str + ": " + paramString;
/*     */     }
/* 293 */     throw new JsonParseException(str, JsonLocation.NA);
/*     */   }
/*     */   
/*     */   protected void _reportBase64EOF()
/*     */     throws JsonParseException
/*     */   {
/* 299 */     throw new JsonParseException("Unexpected end-of-String when base64 content", JsonLocation.NA);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/node/TextNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */