/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableAnyProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
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
/*     */ public final class PropertyValueBuffer
/*     */ {
/*     */   protected final JsonParser _parser;
/*     */   protected final DeserializationContext _context;
/*     */   protected final Object[] _creatorParameters;
/*     */   protected final ObjectIdReader _objectIdReader;
/*     */   private int _paramsNeeded;
/*     */   private PropertyValue _buffered;
/*     */   private Object _idValue;
/*     */   
/*     */   public PropertyValueBuffer(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, int paramInt, ObjectIdReader paramObjectIdReader)
/*     */   {
/*  54 */     this._parser = paramJsonParser;
/*  55 */     this._context = paramDeserializationContext;
/*  56 */     this._paramsNeeded = paramInt;
/*  57 */     this._objectIdReader = paramObjectIdReader;
/*  58 */     this._creatorParameters = new Object[paramInt];
/*     */   }
/*     */   
/*     */   public void inject(SettableBeanProperty[] paramArrayOfSettableBeanProperty)
/*     */   {
/*  63 */     int i = 0; for (int j = paramArrayOfSettableBeanProperty.length; i < j; i++) {
/*  64 */       SettableBeanProperty localSettableBeanProperty = paramArrayOfSettableBeanProperty[i];
/*  65 */       if (localSettableBeanProperty != null)
/*     */       {
/*  67 */         this._creatorParameters[i] = this._context.findInjectableValue(localSettableBeanProperty.getInjectableValueId(), localSettableBeanProperty, null);
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
/*     */   protected final Object[] getParameters(Object[] paramArrayOfObject)
/*     */   {
/*  80 */     if (paramArrayOfObject != null) {
/*  81 */       int i = 0; for (int j = this._creatorParameters.length; i < j; i++) {
/*  82 */         if (this._creatorParameters[i] == null) {
/*  83 */           Object localObject = paramArrayOfObject[i];
/*  84 */           if (localObject != null) {
/*  85 */             this._creatorParameters[i] = localObject;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  90 */     return this._creatorParameters;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean readIdProperty(String paramString)
/*     */     throws IOException
/*     */   {
/* 102 */     if ((this._objectIdReader != null) && (paramString.equals(this._objectIdReader.propertyName))) {
/* 103 */       this._idValue = this._objectIdReader.deserializer.deserialize(this._parser, this._context);
/* 104 */       return true;
/*     */     }
/* 106 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object handleIdValue(DeserializationContext paramDeserializationContext, Object paramObject)
/*     */     throws IOException
/*     */   {
/* 115 */     if ((this._objectIdReader != null) && 
/* 116 */       (this._idValue != null)) {
/* 117 */       ReadableObjectId localReadableObjectId = paramDeserializationContext.findObjectId(this._idValue, this._objectIdReader.generator);
/* 118 */       localReadableObjectId.bindItem(paramObject);
/*     */       
/* 120 */       SettableBeanProperty localSettableBeanProperty = this._objectIdReader.idProperty;
/* 121 */       if (localSettableBeanProperty != null) {
/* 122 */         return localSettableBeanProperty.setAndReturn(paramObject, this._idValue);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 128 */     return paramObject;
/*     */   }
/*     */   
/* 131 */   protected PropertyValue buffered() { return this._buffered; }
/*     */   
/* 133 */   public boolean isComplete() { return this._paramsNeeded <= 0; }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean assignParameter(int paramInt, Object paramObject)
/*     */   {
/* 139 */     this._creatorParameters[paramInt] = paramObject;
/* 140 */     return --this._paramsNeeded <= 0;
/*     */   }
/*     */   
/*     */   public void bufferProperty(SettableBeanProperty paramSettableBeanProperty, Object paramObject) {
/* 144 */     this._buffered = new PropertyValue.Regular(this._buffered, paramObject, paramSettableBeanProperty);
/*     */   }
/*     */   
/*     */   public void bufferAnyProperty(SettableAnyProperty paramSettableAnyProperty, String paramString, Object paramObject) {
/* 148 */     this._buffered = new PropertyValue.Any(this._buffered, paramObject, paramSettableAnyProperty, paramString);
/*     */   }
/*     */   
/*     */   public void bufferMapProperty(Object paramObject1, Object paramObject2) {
/* 152 */     this._buffered = new PropertyValue.Map(this._buffered, paramObject2, paramObject1);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/PropertyValueBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */