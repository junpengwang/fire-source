/*     */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.ser.BeanPropertyWriter;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FilteredBeanPropertyWriter
/*     */ {
/*     */   public static BeanPropertyWriter constructViewBased(BeanPropertyWriter paramBeanPropertyWriter, Class<?>[] paramArrayOfClass)
/*     */   {
/*  17 */     if (paramArrayOfClass.length == 1) {
/*  18 */       return new SingleView(paramBeanPropertyWriter, paramArrayOfClass[0]);
/*     */     }
/*  20 */     return new MultiView(paramBeanPropertyWriter, paramArrayOfClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class SingleView
/*     */     extends BeanPropertyWriter
/*     */   {
/*     */     protected final BeanPropertyWriter _delegate;
/*     */     
/*     */ 
/*     */     protected final Class<?> _view;
/*     */     
/*     */ 
/*     */ 
/*     */     protected SingleView(BeanPropertyWriter paramBeanPropertyWriter, Class<?> paramClass)
/*     */     {
/*  38 */       super();
/*  39 */       this._delegate = paramBeanPropertyWriter;
/*  40 */       this._view = paramClass;
/*     */     }
/*     */     
/*     */     public SingleView rename(NameTransformer paramNameTransformer)
/*     */     {
/*  45 */       return new SingleView(this._delegate.rename(paramNameTransformer), this._view);
/*     */     }
/*     */     
/*     */     public void assignSerializer(JsonSerializer<Object> paramJsonSerializer)
/*     */     {
/*  50 */       this._delegate.assignSerializer(paramJsonSerializer);
/*     */     }
/*     */     
/*     */     public void assignNullSerializer(JsonSerializer<Object> paramJsonSerializer)
/*     */     {
/*  55 */       this._delegate.assignNullSerializer(paramJsonSerializer);
/*     */     }
/*     */     
/*     */ 
/*     */     public void serializeAsField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws Exception
/*     */     {
/*  62 */       Class localClass = paramSerializerProvider.getActiveView();
/*  63 */       if ((localClass == null) || (this._view.isAssignableFrom(localClass))) {
/*  64 */         this._delegate.serializeAsField(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public void serializeAsColumn(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws Exception
/*     */     {
/*  72 */       Class localClass = paramSerializerProvider.getActiveView();
/*  73 */       if ((localClass == null) || (this._view.isAssignableFrom(localClass))) {
/*  74 */         this._delegate.serializeAsColumn(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */       } else {
/*  76 */         this._delegate.serializeAsPlaceholder(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class MultiView
/*     */     extends BeanPropertyWriter
/*     */   {
/*     */     protected final BeanPropertyWriter _delegate;
/*     */     protected final Class<?>[] _views;
/*     */     
/*     */     protected MultiView(BeanPropertyWriter paramBeanPropertyWriter, Class<?>[] paramArrayOfClass)
/*     */     {
/*  89 */       super();
/*  90 */       this._delegate = paramBeanPropertyWriter;
/*  91 */       this._views = paramArrayOfClass;
/*     */     }
/*     */     
/*     */     public MultiView rename(NameTransformer paramNameTransformer)
/*     */     {
/*  96 */       return new MultiView(this._delegate.rename(paramNameTransformer), this._views);
/*     */     }
/*     */     
/*     */     public void assignSerializer(JsonSerializer<Object> paramJsonSerializer)
/*     */     {
/* 101 */       this._delegate.assignSerializer(paramJsonSerializer);
/*     */     }
/*     */     
/*     */     public void assignNullSerializer(JsonSerializer<Object> paramJsonSerializer)
/*     */     {
/* 106 */       this._delegate.assignNullSerializer(paramJsonSerializer);
/*     */     }
/*     */     
/*     */ 
/*     */     public void serializeAsField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws Exception
/*     */     {
/* 113 */       Class localClass = paramSerializerProvider.getActiveView();
/* 114 */       if (localClass != null) {
/* 115 */         int i = 0;int j = this._views.length;
/* 116 */         for (; i < j; i++) {
/* 117 */           if (this._views[i].isAssignableFrom(localClass))
/*     */             break;
/*     */         }
/* 120 */         if (i == j) {
/* 121 */           return;
/*     */         }
/*     */       }
/* 124 */       this._delegate.serializeAsField(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/*     */     
/*     */ 
/*     */     public void serializeAsColumn(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */       throws Exception
/*     */     {
/* 131 */       Class localClass = paramSerializerProvider.getActiveView();
/* 132 */       if (localClass != null) {
/* 133 */         int i = 0;int j = this._views.length;
/* 134 */         for (; i < j; i++) {
/* 135 */           if (this._views[i].isAssignableFrom(localClass))
/*     */             break;
/*     */         }
/* 138 */         if (i == j) {
/* 139 */           this._delegate.serializeAsPlaceholder(paramObject, paramJsonGenerator, paramSerializerProvider);
/* 140 */           return;
/*     */         }
/*     */       }
/* 143 */       this._delegate.serializeAsColumn(paramObject, paramJsonGenerator, paramSerializerProvider);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/FilteredBeanPropertyWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */