/*     */ package com.shaded.fasterxml.jackson.core.json;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerationException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator.Feature;
/*     */ import com.shaded.fasterxml.jackson.core.ObjectCodec;
/*     */ import com.shaded.fasterxml.jackson.core.SerializableString;
/*     */ import com.shaded.fasterxml.jackson.core.Version;
/*     */ import com.shaded.fasterxml.jackson.core.base.GeneratorBase;
/*     */ import com.shaded.fasterxml.jackson.core.io.CharTypes;
/*     */ import com.shaded.fasterxml.jackson.core.io.CharacterEscapes;
/*     */ import com.shaded.fasterxml.jackson.core.io.IOContext;
/*     */ import com.shaded.fasterxml.jackson.core.util.DefaultPrettyPrinter;
/*     */ import com.shaded.fasterxml.jackson.core.util.VersionUtil;
/*     */ import java.io.IOException;
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
/*     */ public abstract class JsonGeneratorImpl
/*     */   extends GeneratorBase
/*     */ {
/*  31 */   protected static final int[] sOutputEscapes = ;
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
/*     */   protected final IOContext _ioContext;
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
/*  53 */   protected int[] _outputEscapes = sOutputEscapes;
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
/*     */   protected int _maximumNonEscapedChar;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected CharacterEscapes _characterEscapes;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  84 */   protected SerializableString _rootValueSeparator = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonGeneratorImpl(IOContext paramIOContext, int paramInt, ObjectCodec paramObjectCodec)
/*     */   {
/*  95 */     super(paramInt, paramObjectCodec);
/*  96 */     this._ioContext = paramIOContext;
/*  97 */     if (isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) {
/*  98 */       setHighestNonEscapedChar(127);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonGenerator setHighestNonEscapedChar(int paramInt)
/*     */   {
/* 110 */     this._maximumNonEscapedChar = (paramInt < 0 ? 0 : paramInt);
/* 111 */     return this;
/*     */   }
/*     */   
/*     */   public int getHighestEscapedChar()
/*     */   {
/* 116 */     return this._maximumNonEscapedChar;
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonGenerator setCharacterEscapes(CharacterEscapes paramCharacterEscapes)
/*     */   {
/* 122 */     this._characterEscapes = paramCharacterEscapes;
/* 123 */     if (paramCharacterEscapes == null) {
/* 124 */       this._outputEscapes = sOutputEscapes;
/*     */     } else {
/* 126 */       this._outputEscapes = paramCharacterEscapes.getEscapeCodesForAscii();
/*     */     }
/* 128 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharacterEscapes getCharacterEscapes()
/*     */   {
/* 137 */     return this._characterEscapes;
/*     */   }
/*     */   
/*     */   public JsonGenerator setRootValueSeparator(SerializableString paramSerializableString)
/*     */   {
/* 142 */     this._rootValueSeparator = paramSerializableString;
/* 143 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Version version()
/*     */   {
/* 154 */     return VersionUtil.versionFor(getClass());
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
/*     */   public final void writeStringField(String paramString1, String paramString2)
/*     */     throws IOException, JsonGenerationException
/*     */   {
/* 169 */     writeFieldName(paramString1);
/* 170 */     writeString(paramString2);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/json/JsonGeneratorImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */