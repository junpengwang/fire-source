/*     */ package com.shaded.fasterxml.jackson.databind.exc;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonLocation;
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
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
/*     */ public class UnrecognizedPropertyException
/*     */   extends JsonMappingException
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Class<?> _referringClass;
/*     */   protected final String _unrecognizedPropertyName;
/*     */   protected final Collection<Object> _propertyIds;
/*     */   protected transient String _propertiesAsString;
/*     */   private static final int MAX_DESC_LENGTH = 200;
/*     */   
/*     */   public UnrecognizedPropertyException(String paramString1, JsonLocation paramJsonLocation, Class<?> paramClass, String paramString2, Collection<Object> paramCollection)
/*     */   {
/*  49 */     super(paramString1, paramJsonLocation);
/*  50 */     this._referringClass = paramClass;
/*  51 */     this._unrecognizedPropertyName = paramString2;
/*  52 */     this._propertyIds = paramCollection;
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
/*     */   public static UnrecognizedPropertyException from(JsonParser paramJsonParser, Object paramObject, String paramString, Collection<Object> paramCollection)
/*     */   {
/*  69 */     if (paramObject == null) {
/*  70 */       throw new IllegalArgumentException();
/*     */     }
/*     */     Class localClass;
/*  73 */     if ((paramObject instanceof Class)) {
/*  74 */       localClass = (Class)paramObject;
/*     */     } else {
/*  76 */       localClass = paramObject.getClass();
/*     */     }
/*  78 */     String str = "Unrecognized field \"" + paramString + "\" (class " + localClass.getName() + "), not marked as ignorable";
/*  79 */     UnrecognizedPropertyException localUnrecognizedPropertyException = new UnrecognizedPropertyException(str, paramJsonParser.getCurrentLocation(), localClass, paramString, paramCollection);
/*     */     
/*     */ 
/*  82 */     localUnrecognizedPropertyException.prependPath(paramObject, paramString);
/*  83 */     return localUnrecognizedPropertyException;
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
/*     */   public String getMessageSuffix()
/*     */   {
/*  97 */     String str = this._propertiesAsString;
/*  98 */     if ((str == null) && (this._propertyIds != null)) {
/*  99 */       StringBuilder localStringBuilder = new StringBuilder(100);
/* 100 */       int i = this._propertyIds.size();
/* 101 */       if (i == 1) {
/* 102 */         localStringBuilder.append(" (one known property: \"");
/* 103 */         localStringBuilder.append(String.valueOf(this._propertyIds.iterator().next()));
/* 104 */         localStringBuilder.append('"');
/*     */       } else {
/* 106 */         localStringBuilder.append(" (").append(i).append(" known properties: ");
/* 107 */         Iterator localIterator = this._propertyIds.iterator();
/* 108 */         while (localIterator.hasNext()) {
/* 109 */           localStringBuilder.append(", \"");
/* 110 */           localStringBuilder.append(String.valueOf(localIterator.next()));
/* 111 */           localStringBuilder.append('"');
/*     */           
/* 113 */           if (localStringBuilder.length() > 200) {
/* 114 */             localStringBuilder.append(" [truncated]");
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 119 */       localStringBuilder.append("])");
/* 120 */       this._propertiesAsString = (str = localStringBuilder.toString());
/*     */     }
/* 122 */     return str;
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
/*     */   public Class<?> getReferringClass()
/*     */   {
/* 136 */     return this._referringClass;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getUnrecognizedPropertyName()
/*     */   {
/* 145 */     return this._unrecognizedPropertyName;
/*     */   }
/*     */   
/*     */   public Collection<Object> getKnownPropertyIds()
/*     */   {
/* 150 */     if (this._propertyIds == null) {
/* 151 */       return null;
/*     */     }
/* 153 */     return Collections.unmodifiableCollection(this._propertyIds);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/exc/UnrecognizedPropertyException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */