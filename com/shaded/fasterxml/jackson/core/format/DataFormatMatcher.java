/*     */ package com.shaded.fasterxml.jackson.core.format;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonFactory;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.io.MergedStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class DataFormatMatcher
/*     */ {
/*     */   protected final InputStream _originalStream;
/*     */   protected final byte[] _bufferedData;
/*     */   protected final int _bufferedStart;
/*     */   protected final int _bufferedLength;
/*     */   protected final JsonFactory _match;
/*     */   protected final MatchStrength _matchStrength;
/*     */   
/*     */   protected DataFormatMatcher(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2, JsonFactory paramJsonFactory, MatchStrength paramMatchStrength)
/*     */   {
/*  46 */     this._originalStream = paramInputStream;
/*  47 */     this._bufferedData = paramArrayOfByte;
/*  48 */     this._bufferedStart = paramInt1;
/*  49 */     this._bufferedLength = paramInt2;
/*  50 */     this._match = paramJsonFactory;
/*  51 */     this._matchStrength = paramMatchStrength;
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
/*     */   public boolean hasMatch()
/*     */   {
/*  64 */     return this._match != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public MatchStrength getMatchStrength()
/*     */   {
/*  71 */     return this._matchStrength == null ? MatchStrength.INCONCLUSIVE : this._matchStrength;
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonFactory getMatch()
/*     */   {
/*  77 */     return this._match;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getMatchedFormatName()
/*     */   {
/*  87 */     return this._match.getFormatName();
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
/*     */   public JsonParser createParserWithMatch()
/*     */     throws IOException
/*     */   {
/* 102 */     if (this._match == null) {
/* 103 */       return null;
/*     */     }
/* 105 */     if (this._originalStream == null) {
/* 106 */       return this._match.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength);
/*     */     }
/* 108 */     return this._match.createParser(getDataStream());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InputStream getDataStream()
/*     */   {
/* 119 */     if (this._originalStream == null) {
/* 120 */       return new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength);
/*     */     }
/* 122 */     return new MergedStream(null, this._originalStream, this._bufferedData, this._bufferedStart, this._bufferedLength);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/format/DataFormatMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */