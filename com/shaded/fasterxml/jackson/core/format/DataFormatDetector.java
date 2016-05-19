/*     */ package com.shaded.fasterxml.jackson.core.format;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonFactory;
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
/*     */ 
/*     */ public class DataFormatDetector
/*     */ {
/*     */   public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
/*     */   protected final JsonFactory[] _detectors;
/*     */   protected final MatchStrength _optimalMatch;
/*     */   protected final MatchStrength _minimalMatch;
/*     */   protected final int _maxInputLookahead;
/*     */   
/*     */   public DataFormatDetector(JsonFactory... paramVarArgs)
/*     */   {
/*  58 */     this(paramVarArgs, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
/*     */   }
/*     */   
/*     */   public DataFormatDetector(Collection<JsonFactory> paramCollection)
/*     */   {
/*  63 */     this((JsonFactory[])paramCollection.toArray(new JsonFactory[paramCollection.size()]));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DataFormatDetector withOptimalMatch(MatchStrength paramMatchStrength)
/*     */   {
/*  72 */     if (paramMatchStrength == this._optimalMatch) {
/*  73 */       return this;
/*     */     }
/*  75 */     return new DataFormatDetector(this._detectors, paramMatchStrength, this._minimalMatch, this._maxInputLookahead);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DataFormatDetector withMinimalMatch(MatchStrength paramMatchStrength)
/*     */   {
/*  83 */     if (paramMatchStrength == this._minimalMatch) {
/*  84 */       return this;
/*     */     }
/*  86 */     return new DataFormatDetector(this._detectors, this._optimalMatch, paramMatchStrength, this._maxInputLookahead);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DataFormatDetector withMaxInputLookahead(int paramInt)
/*     */   {
/*  95 */     if (paramInt == this._maxInputLookahead) {
/*  96 */       return this;
/*     */     }
/*  98 */     return new DataFormatDetector(this._detectors, this._optimalMatch, this._minimalMatch, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private DataFormatDetector(JsonFactory[] paramArrayOfJsonFactory, MatchStrength paramMatchStrength1, MatchStrength paramMatchStrength2, int paramInt)
/*     */   {
/* 105 */     this._detectors = paramArrayOfJsonFactory;
/* 106 */     this._optimalMatch = paramMatchStrength1;
/* 107 */     this._minimalMatch = paramMatchStrength2;
/* 108 */     this._maxInputLookahead = paramInt;
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
/*     */   public DataFormatMatcher findFormat(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 127 */     return _findFormat(new InputAccessor.Std(paramInputStream, new byte[this._maxInputLookahead]));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DataFormatMatcher findFormat(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 139 */     return _findFormat(new InputAccessor.Std(paramArrayOfByte));
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
/*     */   public DataFormatMatcher findFormat(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 153 */     return _findFormat(new InputAccessor.Std(paramArrayOfByte, paramInt1, paramInt2));
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
/* 165 */     StringBuilder localStringBuilder = new StringBuilder();
/* 166 */     localStringBuilder.append('[');
/* 167 */     int i = this._detectors.length;
/* 168 */     if (i > 0) {
/* 169 */       localStringBuilder.append(this._detectors[0].getFormatName());
/* 170 */       for (int j = 1; j < i; j++) {
/* 171 */         localStringBuilder.append(", ");
/* 172 */         localStringBuilder.append(this._detectors[j].getFormatName());
/*     */       }
/*     */     }
/* 175 */     localStringBuilder.append(']');
/* 176 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private DataFormatMatcher _findFormat(InputAccessor.Std paramStd)
/*     */     throws IOException
/*     */   {
/* 187 */     Object localObject1 = null;
/* 188 */     Object localObject2 = null;
/* 189 */     for (JsonFactory localJsonFactory : this._detectors) {
/* 190 */       paramStd.reset();
/* 191 */       MatchStrength localMatchStrength = localJsonFactory.hasFormat(paramStd);
/*     */       
/* 193 */       if ((localMatchStrength != null) && (localMatchStrength.ordinal() >= this._minimalMatch.ordinal()))
/*     */       {
/*     */ 
/*     */ 
/* 197 */         if ((localObject1 == null) || 
/* 198 */           (((MatchStrength)localObject2).ordinal() < localMatchStrength.ordinal()))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 203 */           localObject1 = localJsonFactory;
/* 204 */           localObject2 = localMatchStrength;
/* 205 */           if (localMatchStrength.ordinal() >= this._optimalMatch.ordinal())
/*     */             break;
/*     */         } }
/*     */     }
/* 209 */     return paramStd.createMatcher((JsonFactory)localObject1, (MatchStrength)localObject2);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/format/DataFormatDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */