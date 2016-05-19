/*     */ package com.shaded.fasterxml.jackson.databind.deser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonFactory;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.format.InputAccessor.Std;
/*     */ import com.shaded.fasterxml.jackson.core.format.MatchStrength;
/*     */ import com.shaded.fasterxml.jackson.core.io.MergedStream;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.ObjectReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataFormatReaders
/*     */ {
/*     */   public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
/*     */   protected final ObjectReader[] _readers;
/*     */   protected final MatchStrength _optimalMatch;
/*     */   protected final MatchStrength _minimalMatch;
/*     */   protected final int _maxInputLookahead;
/*     */   
/*     */   public DataFormatReaders(ObjectReader... paramVarArgs)
/*     */   {
/*  65 */     this(paramVarArgs, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
/*     */   }
/*     */   
/*     */   public DataFormatReaders(Collection<ObjectReader> paramCollection)
/*     */   {
/*  70 */     this((ObjectReader[])paramCollection.toArray(new ObjectReader[paramCollection.size()]));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private DataFormatReaders(ObjectReader[] paramArrayOfObjectReader, MatchStrength paramMatchStrength1, MatchStrength paramMatchStrength2, int paramInt)
/*     */   {
/*  77 */     this._readers = paramArrayOfObjectReader;
/*  78 */     this._optimalMatch = paramMatchStrength1;
/*  79 */     this._minimalMatch = paramMatchStrength2;
/*  80 */     this._maxInputLookahead = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DataFormatReaders withOptimalMatch(MatchStrength paramMatchStrength)
/*     */   {
/*  90 */     if (paramMatchStrength == this._optimalMatch) {
/*  91 */       return this;
/*     */     }
/*  93 */     return new DataFormatReaders(this._readers, paramMatchStrength, this._minimalMatch, this._maxInputLookahead);
/*     */   }
/*     */   
/*     */   public DataFormatReaders withMinimalMatch(MatchStrength paramMatchStrength) {
/*  97 */     if (paramMatchStrength == this._minimalMatch) {
/*  98 */       return this;
/*     */     }
/* 100 */     return new DataFormatReaders(this._readers, this._optimalMatch, paramMatchStrength, this._maxInputLookahead);
/*     */   }
/*     */   
/*     */   public DataFormatReaders with(ObjectReader[] paramArrayOfObjectReader) {
/* 104 */     return new DataFormatReaders(paramArrayOfObjectReader, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
/*     */   }
/*     */   
/*     */   public DataFormatReaders withMaxInputLookahead(int paramInt)
/*     */   {
/* 109 */     if (paramInt == this._maxInputLookahead) {
/* 110 */       return this;
/*     */     }
/* 112 */     return new DataFormatReaders(this._readers, this._optimalMatch, this._minimalMatch, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DataFormatReaders with(DeserializationConfig paramDeserializationConfig)
/*     */   {
/* 123 */     int i = this._readers.length;
/* 124 */     ObjectReader[] arrayOfObjectReader = new ObjectReader[i];
/* 125 */     for (int j = 0; j < i; j++) {
/* 126 */       arrayOfObjectReader[j] = this._readers[j].with(paramDeserializationConfig);
/*     */     }
/* 128 */     return new DataFormatReaders(arrayOfObjectReader, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
/*     */   }
/*     */   
/*     */   public DataFormatReaders withType(JavaType paramJavaType)
/*     */   {
/* 133 */     int i = this._readers.length;
/* 134 */     ObjectReader[] arrayOfObjectReader = new ObjectReader[i];
/* 135 */     for (int j = 0; j < i; j++) {
/* 136 */       arrayOfObjectReader[j] = this._readers[j].withType(paramJavaType);
/*     */     }
/* 138 */     return new DataFormatReaders(arrayOfObjectReader, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
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
/*     */   public Match findFormat(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 157 */     return _findFormat(new AccessorForReader(paramInputStream, new byte[this._maxInputLookahead]));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Match findFormat(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 169 */     return _findFormat(new AccessorForReader(paramArrayOfByte));
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
/*     */   public Match findFormat(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 183 */     return _findFormat(new AccessorForReader(paramArrayOfByte, paramInt1, paramInt2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 195 */     StringBuilder localStringBuilder = new StringBuilder();
/* 196 */     localStringBuilder.append('[');
/* 197 */     int i = this._readers.length;
/* 198 */     if (i > 0) {
/* 199 */       localStringBuilder.append(this._readers[0].getFactory().getFormatName());
/* 200 */       for (int j = 1; j < i; j++) {
/* 201 */         localStringBuilder.append(", ");
/* 202 */         localStringBuilder.append(this._readers[j].getFactory().getFormatName());
/*     */       }
/*     */     }
/* 205 */     localStringBuilder.append(']');
/* 206 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Match _findFormat(AccessorForReader paramAccessorForReader)
/*     */     throws IOException
/*     */   {
/* 217 */     Object localObject1 = null;
/* 218 */     Object localObject2 = null;
/* 219 */     for (ObjectReader localObjectReader : this._readers) {
/* 220 */       paramAccessorForReader.reset();
/* 221 */       MatchStrength localMatchStrength = localObjectReader.getFactory().hasFormat(paramAccessorForReader);
/*     */       
/* 223 */       if ((localMatchStrength != null) && (localMatchStrength.ordinal() >= this._minimalMatch.ordinal()))
/*     */       {
/*     */ 
/*     */ 
/* 227 */         if ((localObject1 == null) || 
/* 228 */           (((MatchStrength)localObject2).ordinal() < localMatchStrength.ordinal()))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 233 */           localObject1 = localObjectReader;
/* 234 */           localObject2 = localMatchStrength;
/* 235 */           if (localMatchStrength.ordinal() >= this._optimalMatch.ordinal())
/*     */             break;
/*     */         } }
/*     */     }
/* 239 */     return paramAccessorForReader.createMatcher((ObjectReader)localObject1, (MatchStrength)localObject2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected class AccessorForReader
/*     */     extends InputAccessor.Std
/*     */   {
/*     */     public AccessorForReader(InputStream paramInputStream, byte[] paramArrayOfByte)
/*     */     {
/* 254 */       super(paramArrayOfByte);
/*     */     }
/*     */     
/* 257 */     public AccessorForReader(byte[] paramArrayOfByte) { super(); }
/*     */     
/*     */     public AccessorForReader(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/* 260 */       super(paramInt1, paramInt2);
/*     */     }
/*     */     
/*     */     public DataFormatReaders.Match createMatcher(ObjectReader paramObjectReader, MatchStrength paramMatchStrength)
/*     */     {
/* 265 */       return new DataFormatReaders.Match(this._in, this._buffer, this._bufferedStart, this._bufferedEnd - this._bufferedStart, paramObjectReader, paramMatchStrength);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class Match
/*     */   {
/*     */     protected final InputStream _originalStream;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected final byte[] _bufferedData;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected final int _bufferedStart;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected final int _bufferedLength;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected final ObjectReader _match;
/*     */     
/*     */ 
/*     */ 
/*     */     protected final MatchStrength _matchStrength;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected Match(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2, ObjectReader paramObjectReader, MatchStrength paramMatchStrength)
/*     */     {
/* 306 */       this._originalStream = paramInputStream;
/* 307 */       this._bufferedData = paramArrayOfByte;
/* 308 */       this._bufferedStart = paramInt1;
/* 309 */       this._bufferedLength = paramInt2;
/* 310 */       this._match = paramObjectReader;
/* 311 */       this._matchStrength = paramMatchStrength;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean hasMatch()
/*     */     {
/* 324 */       return this._match != null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public MatchStrength getMatchStrength()
/*     */     {
/* 331 */       return this._matchStrength == null ? MatchStrength.INCONCLUSIVE : this._matchStrength;
/*     */     }
/*     */     
/*     */ 
/*     */     public ObjectReader getReader()
/*     */     {
/* 337 */       return this._match;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getMatchedFormatName()
/*     */     {
/* 347 */       return this._match.getFactory().getFormatName();
/*     */     }
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
/*     */     public JsonParser createParserWithMatch()
/*     */       throws IOException
/*     */     {
/* 363 */       if (this._match == null) {
/* 364 */         return null;
/*     */       }
/* 366 */       JsonFactory localJsonFactory = this._match.getFactory();
/* 367 */       if (this._originalStream == null) {
/* 368 */         return localJsonFactory.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength);
/*     */       }
/* 370 */       return localJsonFactory.createParser(getDataStream());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public InputStream getDataStream()
/*     */     {
/* 381 */       if (this._originalStream == null) {
/* 382 */         return new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength);
/*     */       }
/* 384 */       return new MergedStream(null, this._originalStream, this._bufferedData, this._bufferedStart, this._bufferedLength);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/DataFormatReaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */