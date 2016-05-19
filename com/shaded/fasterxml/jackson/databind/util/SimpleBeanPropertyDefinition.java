/*     */ package com.shaded.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyName;
/*     */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedField;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
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
/*     */ public class SimpleBeanPropertyDefinition
/*     */   extends BeanPropertyDefinition
/*     */ {
/*     */   protected final AnnotationIntrospector _introspector;
/*     */   protected final AnnotatedMember _member;
/*     */   protected final String _name;
/*     */   
/*     */   @Deprecated
/*     */   public SimpleBeanPropertyDefinition(AnnotatedMember paramAnnotatedMember)
/*     */   {
/*  39 */     this(paramAnnotatedMember, paramAnnotatedMember.getName(), null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public SimpleBeanPropertyDefinition(AnnotatedMember paramAnnotatedMember, String paramString)
/*     */   {
/*  47 */     this(paramAnnotatedMember, paramString, null);
/*     */   }
/*     */   
/*     */   private SimpleBeanPropertyDefinition(AnnotatedMember paramAnnotatedMember, String paramString, AnnotationIntrospector paramAnnotationIntrospector)
/*     */   {
/*  52 */     this._introspector = paramAnnotationIntrospector;
/*  53 */     this._member = paramAnnotatedMember;
/*  54 */     this._name = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static SimpleBeanPropertyDefinition construct(MapperConfig<?> paramMapperConfig, AnnotatedMember paramAnnotatedMember)
/*     */   {
/*  62 */     return new SimpleBeanPropertyDefinition(paramAnnotatedMember, paramAnnotatedMember.getName(), paramMapperConfig == null ? null : paramMapperConfig.getAnnotationIntrospector());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static SimpleBeanPropertyDefinition construct(MapperConfig<?> paramMapperConfig, AnnotatedMember paramAnnotatedMember, String paramString)
/*     */   {
/*  71 */     return new SimpleBeanPropertyDefinition(paramAnnotatedMember, paramString, paramMapperConfig == null ? null : paramMapperConfig.getAnnotationIntrospector());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleBeanPropertyDefinition withName(String paramString)
/*     */   {
/*  83 */     if (this._name.equals(paramString)) {
/*  84 */       return this;
/*     */     }
/*  86 */     return new SimpleBeanPropertyDefinition(this._member, paramString, this._introspector);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  96 */     return this._name;
/*     */   }
/*     */   
/*  99 */   public String getInternalName() { return getName(); }
/*     */   
/*     */   public PropertyName getWrapperName()
/*     */   {
/* 103 */     return this._introspector == null ? null : this._introspector.findWrapperName(this._member);
/*     */   }
/*     */   
/*     */   public boolean isExplicitlyIncluded()
/*     */   {
/* 108 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasGetter()
/*     */   {
/* 118 */     return getGetter() != null;
/*     */   }
/*     */   
/*     */   public boolean hasSetter()
/*     */   {
/* 123 */     return getSetter() != null;
/*     */   }
/*     */   
/*     */   public boolean hasField()
/*     */   {
/* 128 */     return this._member instanceof AnnotatedField;
/*     */   }
/*     */   
/*     */   public boolean hasConstructorParameter()
/*     */   {
/* 133 */     return this._member instanceof AnnotatedParameter;
/*     */   }
/*     */   
/*     */   public AnnotatedMethod getGetter()
/*     */   {
/* 138 */     if (((this._member instanceof AnnotatedMethod)) && (((AnnotatedMethod)this._member).getParameterCount() == 0))
/*     */     {
/* 140 */       return (AnnotatedMethod)this._member;
/*     */     }
/* 142 */     return null;
/*     */   }
/*     */   
/*     */   public AnnotatedMethod getSetter()
/*     */   {
/* 147 */     if (((this._member instanceof AnnotatedMethod)) && (((AnnotatedMethod)this._member).getParameterCount() == 1))
/*     */     {
/* 149 */       return (AnnotatedMethod)this._member;
/*     */     }
/* 151 */     return null;
/*     */   }
/*     */   
/*     */   public AnnotatedField getField()
/*     */   {
/* 156 */     return (this._member instanceof AnnotatedField) ? (AnnotatedField)this._member : null;
/*     */   }
/*     */   
/*     */ 
/*     */   public AnnotatedParameter getConstructorParameter()
/*     */   {
/* 162 */     return (this._member instanceof AnnotatedParameter) ? (AnnotatedParameter)this._member : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedMember getAccessor()
/*     */   {
/* 173 */     Object localObject = getGetter();
/* 174 */     if (localObject == null) {
/* 175 */       localObject = getField();
/*     */     }
/* 177 */     return (AnnotatedMember)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AnnotatedMember getMutator()
/*     */   {
/* 187 */     Object localObject = getConstructorParameter();
/* 188 */     if (localObject == null) {
/* 189 */       localObject = getSetter();
/* 190 */       if (localObject == null) {
/* 191 */         localObject = getField();
/*     */       }
/*     */     }
/* 194 */     return (AnnotatedMember)localObject;
/*     */   }
/*     */   
/*     */   public AnnotatedMember getPrimaryMember()
/*     */   {
/* 199 */     return this._member;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/SimpleBeanPropertyDefinition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */