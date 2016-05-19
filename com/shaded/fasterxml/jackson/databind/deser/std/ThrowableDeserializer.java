/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.core.JsonToken;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.BeanDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableAnyProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*     */ import java.io.IOException;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThrowableDeserializer
/*     */   extends BeanDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected static final String PROP_NAME_MESSAGE = "message";
/*     */   
/*     */   public ThrowableDeserializer(BeanDeserializer paramBeanDeserializer)
/*     */   {
/*  31 */     super(paramBeanDeserializer);
/*     */     
/*  33 */     this._vanillaProcessing = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ThrowableDeserializer(BeanDeserializer paramBeanDeserializer, NameTransformer paramNameTransformer)
/*     */   {
/*  41 */     super(paramBeanDeserializer, paramNameTransformer);
/*     */   }
/*     */   
/*     */ 
/*     */   public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer paramNameTransformer)
/*     */   {
/*  47 */     if (getClass() != ThrowableDeserializer.class) {
/*  48 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  54 */     return new ThrowableDeserializer(this, paramNameTransformer);
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
/*     */   public Object deserializeFromObject(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  69 */     if (this._propertyBasedCreator != null) {
/*  70 */       return _deserializeUsingPropertyBased(paramJsonParser, paramDeserializationContext);
/*     */     }
/*  72 */     if (this._delegateDeserializer != null) {
/*  73 */       return this._valueInstantiator.createUsingDelegate(paramDeserializationContext, this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext));
/*     */     }
/*     */     
/*  76 */     if (this._beanType.isAbstract()) {
/*  77 */       throw JsonMappingException.from(paramJsonParser, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
/*     */     }
/*     */     
/*  80 */     boolean bool1 = this._valueInstantiator.canCreateFromString();
/*  81 */     boolean bool2 = this._valueInstantiator.canCreateUsingDefault();
/*     */     
/*  83 */     if ((!bool1) && (!bool2)) {
/*  84 */       throw new JsonMappingException("Can not deserialize Throwable of type " + this._beanType + " without having a default contructor, a single-String-arg constructor; or explicit @JsonCreator");
/*     */     }
/*     */     
/*     */ 
/*  88 */     Object localObject = null;
/*  89 */     Object[] arrayOfObject = null;
/*  90 */     SettableBeanProperty localSettableBeanProperty1 = 0;
/*     */     SettableBeanProperty localSettableBeanProperty3;
/*  92 */     for (; paramJsonParser.getCurrentToken() != JsonToken.END_OBJECT; paramJsonParser.nextToken()) {
/*  93 */       String str = paramJsonParser.getCurrentName();
/*  94 */       localSettableBeanProperty3 = this._beanProperties.find(str);
/*  95 */       paramJsonParser.nextToken();
/*     */       int i;
/*  97 */       if (localSettableBeanProperty3 != null) {
/*  98 */         if (localObject != null) {
/*  99 */           localSettableBeanProperty3.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject);
/*     */         }
/*     */         else
/*     */         {
/* 103 */           if (arrayOfObject == null) {
/* 104 */             i = this._beanProperties.size();
/* 105 */             arrayOfObject = new Object[i + i];
/*     */           }
/* 107 */           arrayOfObject[(localSettableBeanProperty1++)] = localSettableBeanProperty3;
/* 108 */           arrayOfObject[(localSettableBeanProperty1++)] = localSettableBeanProperty3.deserialize(paramJsonParser, paramDeserializationContext);
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 113 */       else if (("message".equals(str)) && 
/* 114 */         (bool1)) {
/* 115 */         localObject = this._valueInstantiator.createFromString(paramDeserializationContext, paramJsonParser.getText());
/*     */         
/* 117 */         if (arrayOfObject != null) {
/* 118 */           i = 0; for (int j = localSettableBeanProperty1; i < j; i += 2) {
/* 119 */             localSettableBeanProperty3 = (SettableBeanProperty)arrayOfObject[i];
/* 120 */             localSettableBeanProperty3.set(localObject, arrayOfObject[(i + 1)]);
/*     */           }
/* 122 */           arrayOfObject = null;
/*     */ 
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */       }
/* 130 */       else if ((this._ignorableProps != null) && (this._ignorableProps.contains(str))) {
/* 131 */         paramJsonParser.skipChildren();
/*     */ 
/*     */       }
/* 134 */       else if (this._anySetter != null) {
/* 135 */         this._anySetter.deserializeAndSet(paramJsonParser, paramDeserializationContext, localObject, str);
/*     */       }
/*     */       else
/*     */       {
/* 139 */         handleUnknownProperty(paramJsonParser, paramDeserializationContext, localObject, str);
/*     */       }
/*     */     }
/* 142 */     if (localObject == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 149 */       if (bool1) {
/* 150 */         localObject = this._valueInstantiator.createFromString(paramDeserializationContext, null);
/*     */       } else {
/* 152 */         localObject = this._valueInstantiator.createUsingDefault(paramDeserializationContext);
/*     */       }
/*     */       
/* 155 */       if (arrayOfObject != null) {
/* 156 */         SettableBeanProperty localSettableBeanProperty2 = 0; for (localSettableBeanProperty3 = localSettableBeanProperty1; localSettableBeanProperty2 < localSettableBeanProperty3; localSettableBeanProperty2 += 2) {
/* 157 */           SettableBeanProperty localSettableBeanProperty4 = (SettableBeanProperty)arrayOfObject[localSettableBeanProperty2];
/* 158 */           localSettableBeanProperty4.set(localObject, arrayOfObject[(localSettableBeanProperty2 + 1)]);
/*     */         }
/*     */       }
/*     */     }
/* 162 */     return localObject;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/ThrowableDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */