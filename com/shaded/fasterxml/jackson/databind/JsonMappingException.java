/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonLocation;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
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
/*     */ public class JsonMappingException
/*     */   extends JsonProcessingException
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   static final int MAX_REFS_TO_LIST = 1000;
/*     */   protected LinkedList<Reference> _path;
/*     */   
/*     */   public static class Reference
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     protected Object _from;
/*     */     protected String _fieldName;
/*  65 */     protected int _index = -1;
/*     */     
/*     */ 
/*     */ 
/*     */     protected Reference() {}
/*     */     
/*     */ 
/*  72 */     public Reference(Object paramObject) { this._from = paramObject; }
/*     */     
/*     */     public Reference(Object paramObject, String paramString) {
/*  75 */       this._from = paramObject;
/*  76 */       if (paramString == null) {
/*  77 */         throw new NullPointerException("Can not pass null fieldName");
/*     */       }
/*  79 */       this._fieldName = paramString;
/*     */     }
/*     */     
/*     */     public Reference(Object paramObject, int paramInt) {
/*  83 */       this._from = paramObject;
/*  84 */       this._index = paramInt;
/*     */     }
/*     */     
/*  87 */     public void setFrom(Object paramObject) { this._from = paramObject; }
/*  88 */     public void setFieldName(String paramString) { this._fieldName = paramString; }
/*  89 */     public void setIndex(int paramInt) { this._index = paramInt; }
/*     */     
/*  91 */     public Object getFrom() { return this._from; }
/*  92 */     public String getFieldName() { return this._fieldName; }
/*  93 */     public int getIndex() { return this._index; }
/*     */     
/*     */     public String toString() {
/*  96 */       StringBuilder localStringBuilder = new StringBuilder();
/*  97 */       Class localClass = (this._from instanceof Class) ? (Class)this._from : this._from.getClass();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 103 */       Package localPackage = localClass.getPackage();
/* 104 */       if (localPackage != null) {
/* 105 */         localStringBuilder.append(localPackage.getName());
/* 106 */         localStringBuilder.append('.');
/*     */       }
/* 108 */       localStringBuilder.append(localClass.getSimpleName());
/* 109 */       localStringBuilder.append('[');
/* 110 */       if (this._fieldName != null) {
/* 111 */         localStringBuilder.append('"');
/* 112 */         localStringBuilder.append(this._fieldName);
/* 113 */         localStringBuilder.append('"');
/* 114 */       } else if (this._index >= 0) {
/* 115 */         localStringBuilder.append(this._index);
/*     */       } else {
/* 117 */         localStringBuilder.append('?');
/*     */       }
/* 119 */       localStringBuilder.append(']');
/* 120 */       return localStringBuilder.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonMappingException(String paramString)
/*     */   {
/* 144 */     super(paramString);
/*     */   }
/*     */   
/*     */   public JsonMappingException(String paramString, Throwable paramThrowable)
/*     */   {
/* 149 */     super(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public JsonMappingException(String paramString, JsonLocation paramJsonLocation)
/*     */   {
/* 154 */     super(paramString, paramJsonLocation);
/*     */   }
/*     */   
/*     */   public JsonMappingException(String paramString, JsonLocation paramJsonLocation, Throwable paramThrowable)
/*     */   {
/* 159 */     super(paramString, paramJsonLocation, paramThrowable);
/*     */   }
/*     */   
/*     */   public static JsonMappingException from(JsonParser paramJsonParser, String paramString)
/*     */   {
/* 164 */     return new JsonMappingException(paramString, paramJsonParser == null ? null : paramJsonParser.getTokenLocation());
/*     */   }
/*     */   
/*     */ 
/*     */   public static JsonMappingException from(JsonParser paramJsonParser, String paramString, Throwable paramThrowable)
/*     */   {
/* 170 */     return new JsonMappingException(paramString, paramJsonParser == null ? null : paramJsonParser.getTokenLocation(), paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JsonMappingException fromUnexpectedIOE(IOException paramIOException)
/*     */   {
/* 182 */     return new JsonMappingException("Unexpected IOException (of type " + paramIOException.getClass().getName() + "): " + paramIOException.getMessage(), (JsonLocation)null, paramIOException);
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
/*     */   public static JsonMappingException wrapWithPath(Throwable paramThrowable, Object paramObject, String paramString)
/*     */   {
/* 197 */     return wrapWithPath(paramThrowable, new Reference(paramObject, paramString));
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
/*     */   public static JsonMappingException wrapWithPath(Throwable paramThrowable, Object paramObject, int paramInt)
/*     */   {
/* 211 */     return wrapWithPath(paramThrowable, new Reference(paramObject, paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JsonMappingException wrapWithPath(Throwable paramThrowable, Reference paramReference)
/*     */   {
/*     */     JsonMappingException localJsonMappingException;
/*     */     
/*     */ 
/* 222 */     if ((paramThrowable instanceof JsonMappingException)) {
/* 223 */       localJsonMappingException = (JsonMappingException)paramThrowable;
/*     */     } else {
/* 225 */       String str = paramThrowable.getMessage();
/*     */       
/*     */ 
/*     */ 
/* 229 */       if ((str == null) || (str.length() == 0)) {
/* 230 */         str = "(was " + paramThrowable.getClass().getName() + ")";
/*     */       }
/* 232 */       localJsonMappingException = new JsonMappingException(str, null, paramThrowable);
/*     */     }
/* 234 */     localJsonMappingException.prependPath(paramReference);
/* 235 */     return localJsonMappingException;
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
/*     */   public List<Reference> getPath()
/*     */   {
/* 250 */     if (this._path == null) {
/* 251 */       return Collections.emptyList();
/*     */     }
/* 253 */     return Collections.unmodifiableList(this._path);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPathReference()
/*     */   {
/* 262 */     return getPathReference(new StringBuilder()).toString();
/*     */   }
/*     */   
/*     */   public StringBuilder getPathReference(StringBuilder paramStringBuilder)
/*     */   {
/* 267 */     _appendPathDesc(paramStringBuilder);
/* 268 */     return paramStringBuilder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void prependPath(Object paramObject, String paramString)
/*     */   {
/* 277 */     Reference localReference = new Reference(paramObject, paramString);
/* 278 */     prependPath(localReference);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void prependPath(Object paramObject, int paramInt)
/*     */   {
/* 286 */     Reference localReference = new Reference(paramObject, paramInt);
/* 287 */     prependPath(localReference);
/*     */   }
/*     */   
/*     */   public void prependPath(Reference paramReference)
/*     */   {
/* 292 */     if (this._path == null) {
/* 293 */       this._path = new LinkedList();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 299 */     if (this._path.size() < 1000) {
/* 300 */       this._path.addFirst(paramReference);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLocalizedMessage()
/*     */   {
/* 312 */     return _buildMessage();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 321 */     return _buildMessage();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String _buildMessage()
/*     */   {
/* 329 */     String str = super.getMessage();
/* 330 */     if (this._path == null) {
/* 331 */       return str;
/*     */     }
/* 333 */     StringBuilder localStringBuilder = str == null ? new StringBuilder() : new StringBuilder(str);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 339 */     localStringBuilder.append(" (through reference chain: ");
/* 340 */     localStringBuilder = getPathReference(localStringBuilder);
/* 341 */     localStringBuilder.append(')');
/* 342 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 348 */     return getClass().getName() + ": " + getMessage();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _appendPathDesc(StringBuilder paramStringBuilder)
/*     */   {
/* 359 */     if (this._path == null) {
/* 360 */       return;
/*     */     }
/* 362 */     Iterator localIterator = this._path.iterator();
/* 363 */     while (localIterator.hasNext()) {
/* 364 */       paramStringBuilder.append(((Reference)localIterator.next()).toString());
/* 365 */       if (localIterator.hasNext()) {
/* 366 */         paramStringBuilder.append("->");
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/JsonMappingException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */