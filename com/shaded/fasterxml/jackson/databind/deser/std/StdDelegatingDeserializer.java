/*     */ package com.shaded.fasterxml.jackson.databind.deser.std;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonParser;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationContext;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonMappingException;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ContextualDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ResolvableDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Converter;
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
/*     */ public class StdDelegatingDeserializer<T>
/*     */   extends StdDeserializer<T>
/*     */   implements ContextualDeserializer, ResolvableDeserializer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final Converter<Object, T> _converter;
/*     */   protected final JavaType _delegateType;
/*     */   protected final JsonDeserializer<Object> _delegateDeserializer;
/*     */   
/*     */   public StdDelegatingDeserializer(Converter<?, T> paramConverter)
/*     */   {
/*  56 */     super(Object.class);
/*  57 */     this._converter = paramConverter;
/*  58 */     this._delegateType = null;
/*  59 */     this._delegateDeserializer = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public StdDelegatingDeserializer(Converter<Object, T> paramConverter, JavaType paramJavaType, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  66 */     super(paramJavaType);
/*  67 */     this._converter = paramConverter;
/*  68 */     this._delegateType = paramJavaType;
/*  69 */     this._delegateDeserializer = paramJsonDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected StdDelegatingDeserializer<T> withDelegate(Converter<Object, T> paramConverter, JavaType paramJavaType, JsonDeserializer<?> paramJsonDeserializer)
/*     */   {
/*  79 */     if (getClass() != StdDelegatingDeserializer.class) {
/*  80 */       throw new IllegalStateException("Sub-class " + getClass().getName() + " must override 'withDelegate'");
/*     */     }
/*  82 */     return new StdDelegatingDeserializer(paramConverter, paramJavaType, paramJsonDeserializer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resolve(DeserializationContext paramDeserializationContext)
/*     */     throws JsonMappingException
/*     */   {
/*  95 */     if ((this._delegateDeserializer != null) && ((this._delegateDeserializer instanceof ResolvableDeserializer))) {
/*  96 */       ((ResolvableDeserializer)this._delegateDeserializer).resolve(paramDeserializationContext);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> createContextual(DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty)
/*     */     throws JsonMappingException
/*     */   {
/* 105 */     if (this._delegateDeserializer != null) {
/* 106 */       if ((this._delegateDeserializer instanceof ContextualDeserializer)) {
/* 107 */         localObject = ((ContextualDeserializer)this._delegateDeserializer).createContextual(paramDeserializationContext, paramBeanProperty);
/* 108 */         if (localObject != this._delegateDeserializer) {
/* 109 */           return withDelegate(this._converter, this._delegateType, (JsonDeserializer)localObject);
/*     */         }
/*     */       }
/* 112 */       return this;
/*     */     }
/*     */     
/* 115 */     Object localObject = this._converter.getInputType(paramDeserializationContext.getTypeFactory());
/* 116 */     return withDelegate(this._converter, (JavaType)localObject, paramDeserializationContext.findContextualValueDeserializer((JavaType)localObject, paramBeanProperty));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JsonDeserializer<?> getDelegatee()
/*     */   {
/* 128 */     return this._delegateDeserializer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 141 */     Object localObject = this._delegateDeserializer.deserialize(paramJsonParser, paramDeserializationContext);
/* 142 */     if (localObject == null) {
/* 143 */       return null;
/*     */     }
/* 145 */     return (T)convertValue(localObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object deserializeWithType(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, TypeDeserializer paramTypeDeserializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/* 156 */     Object localObject = this._delegateDeserializer.deserializeWithType(paramJsonParser, paramDeserializationContext, paramTypeDeserializer);
/*     */     
/* 158 */     if (localObject == null) {
/* 159 */       return null;
/*     */     }
/* 161 */     return convertValue(localObject);
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
/*     */   protected T convertValue(Object paramObject)
/*     */   {
/* 183 */     return (T)this._converter.convert(paramObject);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/std/StdDelegatingDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */