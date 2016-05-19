/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.FormatSchema;
/*     */ import com.shaded.fasterxml.jackson.core.JsonLocation;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public class MappingIterator<T> implements Iterator<T>, Closeable
/*     */ {
/*  16 */   protected static final MappingIterator<?> EMPTY_ITERATOR = new MappingIterator(null, null, null, null, false, null);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JavaType _type;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final DeserializationContext _context;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final JsonDeserializer<T> _deserializer;
/*     */   
/*     */ 
/*     */ 
/*     */   protected JsonParser _parser;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final boolean _closeParser;
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean _hasNextChecked;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final T _updatedValue;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected MappingIterator(JavaType paramJavaType, JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  53 */     this(paramJavaType, paramJsonParser, paramDeserializationContext, paramJsonDeserializer, true, null);
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
/*     */   protected MappingIterator(JavaType paramJavaType, JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JsonDeserializer<?> paramJsonDeserializer, boolean paramBoolean, Object paramObject)
/*     */   {
/*  67 */     this._type = paramJavaType;
/*  68 */     this._parser = paramJsonParser;
/*  69 */     this._context = paramDeserializationContext;
/*  70 */     this._deserializer = paramJsonDeserializer;
/*  71 */     this._closeParser = paramBoolean;
/*  72 */     if (paramObject == null) {
/*  73 */       this._updatedValue = null;
/*     */     } else {
/*  75 */       this._updatedValue = paramObject;
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
/*  88 */     if ((paramBoolean) && (paramJsonParser != null) && (paramJsonParser.getCurrentToken() == JsonToken.START_ARRAY)) {
/*  89 */       paramJsonParser.clearCurrentToken();
/*     */     }
/*     */   }
/*     */   
/*     */   protected static <T> MappingIterator<T> emptyIterator()
/*     */   {
/*  95 */     return EMPTY_ITERATOR;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasNext()
/*     */   {
/*     */     try
/*     */     {
/* 108 */       return hasNextValue();
/*     */     } catch (JsonMappingException localJsonMappingException) {
/* 110 */       throw new RuntimeJsonMappingException(localJsonMappingException.getMessage(), localJsonMappingException);
/*     */     } catch (IOException localIOException) {
/* 112 */       throw new RuntimeException(localIOException.getMessage(), localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public T next()
/*     */   {
/*     */     try
/*     */     {
/* 120 */       return (T)nextValue();
/*     */     } catch (JsonMappingException localJsonMappingException) {
/* 122 */       throw new RuntimeJsonMappingException(localJsonMappingException.getMessage(), localJsonMappingException);
/*     */     } catch (IOException localIOException) {
/* 124 */       throw new RuntimeException(localIOException.getMessage(), localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove()
/*     */   {
/* 130 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void close() throws IOException
/*     */   {
/* 135 */     if (this._parser != null) {
/* 136 */       this._parser.close();
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
/*     */ 
/*     */ 
/*     */   public boolean hasNextValue()
/*     */     throws IOException
/*     */   {
/* 152 */     if (this._parser == null) {
/* 153 */       return false;
/*     */     }
/* 155 */     if (!this._hasNextChecked) {
/* 156 */       JsonToken localJsonToken = this._parser.getCurrentToken();
/* 157 */       this._hasNextChecked = true;
/* 158 */       if (localJsonToken == null) {
/* 159 */         localJsonToken = this._parser.nextToken();
/*     */         
/* 161 */         if ((localJsonToken == null) || (localJsonToken == JsonToken.END_ARRAY)) {
/* 162 */           JsonParser localJsonParser = this._parser;
/* 163 */           this._parser = null;
/* 164 */           if (this._closeParser) {
/* 165 */             localJsonParser.close();
/*     */           }
/* 167 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 171 */     return true;
/*     */   }
/*     */   
/*     */   public T nextValue()
/*     */     throws IOException
/*     */   {
/* 177 */     if ((!this._hasNextChecked) && 
/* 178 */       (!hasNextValue())) {
/* 179 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/* 182 */     if (this._parser == null) {
/* 183 */       throw new NoSuchElementException();
/*     */     }
/* 185 */     this._hasNextChecked = false;
/*     */     
/*     */     Object localObject;
/* 188 */     if (this._updatedValue == null) {
/* 189 */       localObject = this._deserializer.deserialize(this._parser, this._context);
/*     */     } else {
/* 191 */       this._deserializer.deserialize(this._parser, this._context, this._updatedValue);
/* 192 */       localObject = this._updatedValue;
/*     */     }
/*     */     
/* 195 */     this._parser.clearCurrentToken();
/* 196 */     return (T)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<T> readAll()
/*     */     throws IOException
/*     */   {
/* 208 */     return readAll(new ArrayList());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<T> readAll(List<T> paramList)
/*     */     throws IOException
/*     */   {
/* 221 */     while (hasNextValue()) {
/* 222 */       paramList.add(nextValue());
/*     */     }
/* 224 */     return paramList;
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
/*     */   public JsonParser getParser()
/*     */   {
/* 239 */     return this._parser;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FormatSchema getParserSchema()
/*     */   {
/* 250 */     return this._parser.getSchema();
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
/*     */   public JsonLocation getCurrentLocation()
/*     */   {
/* 264 */     return this._parser.getCurrentLocation();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/MappingIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */