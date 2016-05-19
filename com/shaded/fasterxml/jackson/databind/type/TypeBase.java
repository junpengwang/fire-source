/*     */ package com.shaded.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.JsonGenerator;
/*     */ import com.shaded.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializable;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializerProvider;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
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
/*     */ public abstract class TypeBase
/*     */   extends JavaType
/*     */   implements JsonSerializable
/*     */ {
/*     */   private static final long serialVersionUID = -3581199092426900829L;
/*     */   volatile transient String _canonicalName;
/*     */   
/*     */   @Deprecated
/*     */   protected TypeBase(Class<?> paramClass, int paramInt, Object paramObject1, Object paramObject2)
/*     */   {
/*  30 */     this(paramClass, paramInt, paramObject1, paramObject2, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected TypeBase(Class<?> paramClass, int paramInt, Object paramObject1, Object paramObject2, boolean paramBoolean)
/*     */   {
/*  39 */     super(paramClass, paramInt, paramObject1, paramObject2, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */   public String toCanonical()
/*     */   {
/*  45 */     String str = this._canonicalName;
/*  46 */     if (str == null) {
/*  47 */       str = buildCanonicalName();
/*     */     }
/*  49 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */   protected abstract String buildCanonicalName();
/*     */   
/*     */ 
/*     */   public abstract StringBuilder getGenericSignature(StringBuilder paramStringBuilder);
/*     */   
/*     */   public abstract StringBuilder getErasedSignature(StringBuilder paramStringBuilder);
/*     */   
/*     */   public <T> T getValueHandler()
/*     */   {
/*  62 */     return (T)this._valueHandler;
/*     */   }
/*     */   
/*     */   public <T> T getTypeHandler() {
/*  66 */     return (T)this._typeHandler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void serializeWithType(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  79 */     paramTypeSerializer.writeTypePrefixForScalar(this, paramJsonGenerator);
/*  80 */     serialize(paramJsonGenerator, paramSerializerProvider);
/*  81 */     paramTypeSerializer.writeTypeSuffixForScalar(this, paramJsonGenerator);
/*     */   }
/*     */   
/*     */ 
/*     */   public void serialize(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
/*     */     throws IOException, JsonProcessingException
/*     */   {
/*  88 */     paramJsonGenerator.writeString(toCanonical());
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
/*     */   protected static StringBuilder _classSignature(Class<?> paramClass, StringBuilder paramStringBuilder, boolean paramBoolean)
/*     */   {
/* 104 */     if (paramClass.isPrimitive()) {
/* 105 */       if (paramClass == Boolean.TYPE) {
/* 106 */         paramStringBuilder.append('Z');
/* 107 */       } else if (paramClass == Byte.TYPE) {
/* 108 */         paramStringBuilder.append('B');
/*     */       }
/* 110 */       else if (paramClass == Short.TYPE) {
/* 111 */         paramStringBuilder.append('S');
/*     */       }
/* 113 */       else if (paramClass == Character.TYPE) {
/* 114 */         paramStringBuilder.append('C');
/*     */       }
/* 116 */       else if (paramClass == Integer.TYPE) {
/* 117 */         paramStringBuilder.append('I');
/*     */       }
/* 119 */       else if (paramClass == Long.TYPE) {
/* 120 */         paramStringBuilder.append('J');
/*     */       }
/* 122 */       else if (paramClass == Float.TYPE) {
/* 123 */         paramStringBuilder.append('F');
/*     */       }
/* 125 */       else if (paramClass == Double.TYPE) {
/* 126 */         paramStringBuilder.append('D');
/*     */       }
/* 128 */       else if (paramClass == Void.TYPE) {
/* 129 */         paramStringBuilder.append('V');
/*     */       } else {
/* 131 */         throw new IllegalStateException("Unrecognized primitive type: " + paramClass.getName());
/*     */       }
/*     */     } else {
/* 134 */       paramStringBuilder.append('L');
/* 135 */       String str = paramClass.getName();
/* 136 */       int i = 0; for (int j = str.length(); i < j; i++) {
/* 137 */         char c = str.charAt(i);
/* 138 */         if (c == '.') c = '/';
/* 139 */         paramStringBuilder.append(c);
/*     */       }
/* 141 */       if (paramBoolean) {
/* 142 */         paramStringBuilder.append(';');
/*     */       }
/*     */     }
/* 145 */     return paramStringBuilder;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/TypeBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */