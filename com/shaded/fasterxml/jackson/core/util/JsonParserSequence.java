/*     */ package com.shaded.fasterxml.jackson.core.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParseException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class JsonParserSequence
/*     */   extends JsonParserDelegate
/*     */ {
/*     */   protected final JsonParser[] _parsers;
/*     */   protected int _nextParser;
/*     */   
/*     */   protected JsonParserSequence(JsonParser[] paramArrayOfJsonParser)
/*     */   {
/*  37 */     super(paramArrayOfJsonParser[0]);
/*  38 */     this._parsers = paramArrayOfJsonParser;
/*  39 */     this._nextParser = 1;
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
/*     */   public static JsonParserSequence createFlattened(JsonParser paramJsonParser1, JsonParser paramJsonParser2)
/*     */   {
/*  53 */     if ((!(paramJsonParser1 instanceof JsonParserSequence)) && (!(paramJsonParser2 instanceof JsonParserSequence)))
/*     */     {
/*  55 */       return new JsonParserSequence(new JsonParser[] { paramJsonParser1, paramJsonParser2 });
/*     */     }
/*  57 */     ArrayList localArrayList = new ArrayList();
/*  58 */     if ((paramJsonParser1 instanceof JsonParserSequence)) {
/*  59 */       ((JsonParserSequence)paramJsonParser1).addFlattenedActiveParsers(localArrayList);
/*     */     } else {
/*  61 */       localArrayList.add(paramJsonParser1);
/*     */     }
/*  63 */     if ((paramJsonParser2 instanceof JsonParserSequence)) {
/*  64 */       ((JsonParserSequence)paramJsonParser2).addFlattenedActiveParsers(localArrayList);
/*     */     } else {
/*  66 */       localArrayList.add(paramJsonParser2);
/*     */     }
/*  68 */     return new JsonParserSequence((JsonParser[])localArrayList.toArray(new JsonParser[localArrayList.size()]));
/*     */   }
/*     */   
/*     */   protected void addFlattenedActiveParsers(List<JsonParser> paramList)
/*     */   {
/*  73 */     int i = this._nextParser - 1; for (int j = this._parsers.length; i < j; i++) {
/*  74 */       JsonParser localJsonParser = this._parsers[i];
/*  75 */       if ((localJsonParser instanceof JsonParserSequence)) {
/*  76 */         ((JsonParserSequence)localJsonParser).addFlattenedActiveParsers(paramList);
/*     */       } else {
/*  78 */         paramList.add(localJsonParser);
/*     */       }
/*     */     }
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
/*     */     do
/*     */     {
/*  94 */       this.delegate.close();
/*  95 */     } while (switchToNext());
/*     */   }
/*     */   
/*     */   public JsonToken nextToken()
/*     */     throws IOException, JsonParseException
/*     */   {
/* 101 */     JsonToken localJsonToken = this.delegate.nextToken();
/* 102 */     if (localJsonToken != null) return localJsonToken;
/* 103 */     while (switchToNext()) {
/* 104 */       localJsonToken = this.delegate.nextToken();
/* 105 */       if (localJsonToken != null) return localJsonToken;
/*     */     }
/* 107 */     return null;
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
/*     */   public int containedParsersCount()
/*     */   {
/* 122 */     return this._parsers.length;
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
/*     */   protected boolean switchToNext()
/*     */   {
/* 141 */     if (this._nextParser >= this._parsers.length) {
/* 142 */       return false;
/*     */     }
/* 144 */     this.delegate = this._parsers[(this._nextParser++)];
/* 145 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/util/JsonParserSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */