/*     */ package com.shaded.fasterxml.jackson.databind.ser;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.annotation.JsonInclude.Include;
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.SerializationFeature;
/*     */ import com.shaded.fasterxml.jackson.databind.annotation.JsonSerialize.Typing;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.Annotated;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*     */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeSerializer;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ArrayBuilders;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertyBuilder
/*     */ {
/*     */   protected final SerializationConfig _config;
/*     */   protected final BeanDescription _beanDesc;
/*     */   protected final JsonInclude.Include _outputProps;
/*     */   protected final AnnotationIntrospector _annotationIntrospector;
/*     */   protected Object _defaultBean;
/*     */   
/*     */   public PropertyBuilder(SerializationConfig paramSerializationConfig, BeanDescription paramBeanDescription)
/*     */   {
/*  34 */     this._config = paramSerializationConfig;
/*  35 */     this._beanDesc = paramBeanDescription;
/*  36 */     this._outputProps = paramBeanDescription.findSerializationInclusion(paramSerializationConfig.getSerializationInclusion());
/*  37 */     this._annotationIntrospector = this._config.getAnnotationIntrospector();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Annotations getClassAnnotations()
/*     */   {
/*  47 */     return this._beanDesc.getClassAnnotations();
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
/*     */   protected BeanPropertyWriter buildWriter(BeanPropertyDefinition paramBeanPropertyDefinition, JavaType paramJavaType, JsonSerializer<?> paramJsonSerializer, TypeSerializer paramTypeSerializer1, TypeSerializer paramTypeSerializer2, AnnotatedMember paramAnnotatedMember, boolean paramBoolean)
/*     */   {
/*  61 */     JavaType localJavaType = findSerializationType(paramAnnotatedMember, paramBoolean, paramJavaType);
/*     */     
/*     */ 
/*  64 */     if (paramTypeSerializer2 != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*  69 */       if (localJavaType == null)
/*     */       {
/*  71 */         localJavaType = paramJavaType;
/*     */       }
/*  73 */       localObject = localJavaType.getContentType();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  78 */       if (localObject == null) {
/*  79 */         throw new IllegalStateException("Problem trying to create BeanPropertyWriter for property '" + paramBeanPropertyDefinition.getName() + "' (of type " + this._beanDesc.getType() + "); serialization type " + localJavaType + " has no content");
/*     */       }
/*     */       
/*  82 */       localJavaType = localJavaType.withContentTypeHandler(paramTypeSerializer2);
/*  83 */       localObject = localJavaType.getContentType();
/*     */     }
/*     */     
/*  86 */     Object localObject = null;
/*  87 */     boolean bool = false;
/*     */     
/*  89 */     JsonInclude.Include localInclude = this._annotationIntrospector.findSerializationInclusion(paramAnnotatedMember, this._outputProps);
/*  90 */     if (localInclude != null) {
/*  91 */       switch (localInclude) {
/*     */       case NON_DEFAULT: 
/*  93 */         localObject = getDefaultValue(paramBeanPropertyDefinition.getName(), paramAnnotatedMember);
/*  94 */         if (localObject == null) {
/*  95 */           bool = true;
/*     */ 
/*     */         }
/*  98 */         else if (localObject.getClass().isArray()) {
/*  99 */           localObject = ArrayBuilders.getArrayComparator(localObject);
/*     */         }
/*     */         
/*     */ 
/*     */         break;
/*     */       case NON_EMPTY: 
/* 105 */         bool = true;
/*     */         
/* 107 */         localObject = BeanPropertyWriter.MARKER_FOR_EMPTY;
/* 108 */         break;
/*     */       case NON_NULL: 
/* 110 */         bool = true;
/*     */       
/*     */ 
/*     */       case ALWAYS: 
/* 114 */         if ((paramJavaType.isContainerType()) && (!this._config.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS)))
/*     */         {
/* 116 */           localObject = BeanPropertyWriter.MARKER_FOR_EMPTY;
/*     */         }
/*     */         break;
/*     */       }
/*     */       
/*     */     }
/* 122 */     BeanPropertyWriter localBeanPropertyWriter = new BeanPropertyWriter(paramBeanPropertyDefinition, paramAnnotatedMember, this._beanDesc.getClassAnnotations(), paramJavaType, paramJsonSerializer, paramTypeSerializer1, localJavaType, bool, localObject);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 127 */     NameTransformer localNameTransformer = this._annotationIntrospector.findUnwrappingNameTransformer(paramAnnotatedMember);
/* 128 */     if (localNameTransformer != null) {
/* 129 */       localBeanPropertyWriter = localBeanPropertyWriter.unwrappingWriter(localNameTransformer);
/*     */     }
/* 131 */     return localBeanPropertyWriter;
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
/*     */   protected JavaType findSerializationType(Annotated paramAnnotated, boolean paramBoolean, JavaType paramJavaType)
/*     */   {
/* 149 */     Class localClass = this._annotationIntrospector.findSerializationType(paramAnnotated);
/* 150 */     if (localClass != null)
/*     */     {
/* 152 */       localObject = paramJavaType.getRawClass();
/* 153 */       if (localClass.isAssignableFrom((Class)localObject)) {
/* 154 */         paramJavaType = paramJavaType.widenBy(localClass);
/*     */ 
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/*     */ 
/* 162 */         if (!((Class)localObject).isAssignableFrom(localClass)) {
/* 163 */           throw new IllegalArgumentException("Illegal concrete-type annotation for method '" + paramAnnotated.getName() + "': class " + localClass.getName() + " not a super-type of (declared) class " + ((Class)localObject).getName());
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 169 */         paramJavaType = this._config.constructSpecializedType(paramJavaType, localClass);
/*     */       }
/* 171 */       paramBoolean = true;
/*     */     }
/*     */     
/* 174 */     Object localObject = BeanSerializerFactory.modifySecondaryTypesByAnnotation(this._config, paramAnnotated, paramJavaType);
/* 175 */     if (localObject != paramJavaType) {
/* 176 */       paramBoolean = true;
/* 177 */       paramJavaType = (JavaType)localObject;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 183 */     if (!paramBoolean) {
/* 184 */       JsonSerialize.Typing localTyping = this._annotationIntrospector.findSerializationTyping(paramAnnotated);
/* 185 */       if (localTyping != null) {
/* 186 */         paramBoolean = localTyping == JsonSerialize.Typing.STATIC;
/*     */       }
/*     */     }
/* 189 */     return paramBoolean ? paramJavaType : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object getDefaultBean()
/*     */   {
/* 200 */     if (this._defaultBean == null)
/*     */     {
/*     */ 
/*     */ 
/* 204 */       this._defaultBean = this._beanDesc.instantiateBean(this._config.canOverrideAccessModifiers());
/* 205 */       if (this._defaultBean == null) {
/* 206 */         Class localClass = this._beanDesc.getClassInfo().getAnnotated();
/* 207 */         throw new IllegalArgumentException("Class " + localClass.getName() + " has no default constructor; can not instantiate default bean value to support 'properties=JsonSerialize.Inclusion.NON_DEFAULT' annotation");
/*     */       }
/*     */     }
/* 210 */     return this._defaultBean;
/*     */   }
/*     */   
/*     */   protected Object getDefaultValue(String paramString, AnnotatedMember paramAnnotatedMember)
/*     */   {
/* 215 */     Object localObject = getDefaultBean();
/*     */     try {
/* 217 */       return paramAnnotatedMember.getValue(localObject);
/*     */     } catch (Exception localException) {
/* 219 */       return _throwWrapped(localException, paramString, localObject);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object _throwWrapped(Exception paramException, String paramString, Object paramObject)
/*     */   {
/* 231 */     Object localObject = paramException;
/* 232 */     while (((Throwable)localObject).getCause() != null) {
/* 233 */       localObject = ((Throwable)localObject).getCause();
/*     */     }
/* 235 */     if ((localObject instanceof Error)) throw ((Error)localObject);
/* 236 */     if ((localObject instanceof RuntimeException)) throw ((RuntimeException)localObject);
/* 237 */     throw new IllegalArgumentException("Failed to get property '" + paramString + "' of default " + paramObject.getClass().getName() + " instance");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/PropertyBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */