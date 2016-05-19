/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*     */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.CreatorProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.ValueInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
/*     */ import com.shaded.fasterxml.jackson.databind.type.TypeBindings;
/*     */ import com.shaded.fasterxml.jackson.databind.util.ClassUtil;
/*     */ import java.lang.reflect.Member;
/*     */ import java.util.HashMap;
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
/*     */ public class CreatorCollector
/*     */ {
/*     */   protected final BeanDescription _beanDesc;
/*     */   protected final boolean _canFixAccess;
/*     */   protected AnnotatedWithParams _defaultConstructor;
/*     */   protected AnnotatedWithParams _stringCreator;
/*     */   protected AnnotatedWithParams _intCreator;
/*     */   protected AnnotatedWithParams _longCreator;
/*     */   protected AnnotatedWithParams _doubleCreator;
/*     */   protected AnnotatedWithParams _booleanCreator;
/*     */   protected AnnotatedWithParams _delegateCreator;
/*     */   protected CreatorProperty[] _delegateArgs;
/*     */   protected AnnotatedWithParams _propertyBasedCreator;
/*  44 */   protected CreatorProperty[] _propertyBasedArgs = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AnnotatedParameter _incompleteParameter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CreatorCollector(BeanDescription paramBeanDescription, boolean paramBoolean)
/*     */   {
/*  56 */     this._beanDesc = paramBeanDescription;
/*  57 */     this._canFixAccess = paramBoolean;
/*     */   }
/*     */   
/*     */   public ValueInstantiator constructValueInstantiator(DeserializationConfig paramDeserializationConfig)
/*     */   {
/*  62 */     StdValueInstantiator localStdValueInstantiator = new StdValueInstantiator(paramDeserializationConfig, this._beanDesc.getType());
/*     */     
/*     */     JavaType localJavaType;
/*     */     
/*  66 */     if (this._delegateCreator == null) {
/*  67 */       localJavaType = null;
/*     */     }
/*     */     else {
/*  70 */       int i = 0;
/*  71 */       if (this._delegateArgs != null) {
/*  72 */         int j = 0; for (int k = this._delegateArgs.length; j < k; j++) {
/*  73 */           if (this._delegateArgs[j] == null) {
/*  74 */             i = j;
/*  75 */             break;
/*     */           }
/*     */         }
/*     */       }
/*  79 */       TypeBindings localTypeBindings = this._beanDesc.bindingsForBeanType();
/*  80 */       localJavaType = localTypeBindings.resolveType(this._delegateCreator.getGenericParameterType(i));
/*     */     }
/*     */     
/*  83 */     localStdValueInstantiator.configureFromObjectSettings(this._defaultConstructor, this._delegateCreator, localJavaType, this._delegateArgs, this._propertyBasedCreator, this._propertyBasedArgs);
/*     */     
/*     */ 
/*  86 */     localStdValueInstantiator.configureFromStringCreator(this._stringCreator);
/*  87 */     localStdValueInstantiator.configureFromIntCreator(this._intCreator);
/*  88 */     localStdValueInstantiator.configureFromLongCreator(this._longCreator);
/*  89 */     localStdValueInstantiator.configureFromDoubleCreator(this._doubleCreator);
/*  90 */     localStdValueInstantiator.configureFromBooleanCreator(this._booleanCreator);
/*  91 */     localStdValueInstantiator.configureIncompleteParameter(this._incompleteParameter);
/*  92 */     return localStdValueInstantiator;
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
/*     */   @Deprecated
/*     */   public void setDefaultConstructor(AnnotatedConstructor paramAnnotatedConstructor)
/*     */   {
/* 106 */     this._defaultConstructor = ((AnnotatedWithParams)_fixAccess(paramAnnotatedConstructor));
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
/*     */   public void setDefaultCreator(AnnotatedWithParams paramAnnotatedWithParams)
/*     */   {
/* 122 */     if ((paramAnnotatedWithParams instanceof AnnotatedConstructor)) {
/* 123 */       setDefaultConstructor((AnnotatedConstructor)paramAnnotatedWithParams);
/* 124 */       return;
/*     */     }
/* 126 */     this._defaultConstructor = ((AnnotatedWithParams)_fixAccess(paramAnnotatedWithParams));
/*     */   }
/*     */   
/*     */   public void addStringCreator(AnnotatedWithParams paramAnnotatedWithParams) {
/* 130 */     this._stringCreator = verifyNonDup(paramAnnotatedWithParams, this._stringCreator, "String");
/*     */   }
/*     */   
/* 133 */   public void addIntCreator(AnnotatedWithParams paramAnnotatedWithParams) { this._intCreator = verifyNonDup(paramAnnotatedWithParams, this._intCreator, "int"); }
/*     */   
/*     */   public void addLongCreator(AnnotatedWithParams paramAnnotatedWithParams) {
/* 136 */     this._longCreator = verifyNonDup(paramAnnotatedWithParams, this._longCreator, "long");
/*     */   }
/*     */   
/* 139 */   public void addDoubleCreator(AnnotatedWithParams paramAnnotatedWithParams) { this._doubleCreator = verifyNonDup(paramAnnotatedWithParams, this._doubleCreator, "double"); }
/*     */   
/*     */   public void addBooleanCreator(AnnotatedWithParams paramAnnotatedWithParams) {
/* 142 */     this._booleanCreator = verifyNonDup(paramAnnotatedWithParams, this._booleanCreator, "boolean");
/*     */   }
/*     */   
/*     */ 
/*     */   public void addDelegatingCreator(AnnotatedWithParams paramAnnotatedWithParams, CreatorProperty[] paramArrayOfCreatorProperty)
/*     */   {
/* 148 */     this._delegateCreator = verifyNonDup(paramAnnotatedWithParams, this._delegateCreator, "delegate");
/* 149 */     this._delegateArgs = paramArrayOfCreatorProperty;
/*     */   }
/*     */   
/*     */   public void addPropertyCreator(AnnotatedWithParams paramAnnotatedWithParams, CreatorProperty[] paramArrayOfCreatorProperty)
/*     */   {
/* 154 */     this._propertyBasedCreator = verifyNonDup(paramAnnotatedWithParams, this._propertyBasedCreator, "property-based");
/*     */     
/* 156 */     if (paramArrayOfCreatorProperty.length > 1) {
/* 157 */       HashMap localHashMap = new HashMap();
/* 158 */       int i = 0; for (int j = paramArrayOfCreatorProperty.length; i < j; i++) {
/* 159 */         String str = paramArrayOfCreatorProperty[i].getName();
/*     */         
/*     */ 
/*     */ 
/* 163 */         if ((str.length() != 0) || (paramArrayOfCreatorProperty[i].getInjectableValueId() == null))
/*     */         {
/*     */ 
/* 166 */           Integer localInteger = (Integer)localHashMap.put(str, Integer.valueOf(i));
/* 167 */           if (localInteger != null)
/* 168 */             throw new IllegalArgumentException("Duplicate creator property \"" + str + "\" (index " + localInteger + " vs " + i + ")");
/*     */         }
/*     */       }
/*     */     }
/* 172 */     this._propertyBasedArgs = paramArrayOfCreatorProperty;
/*     */   }
/*     */   
/*     */   public void addIncompeteParameter(AnnotatedParameter paramAnnotatedParameter) {
/* 176 */     if (this._incompleteParameter == null) {
/* 177 */       this._incompleteParameter = paramAnnotatedParameter;
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
/*     */   public boolean hasDefaultCreator()
/*     */   {
/* 191 */     return this._defaultConstructor != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private <T extends AnnotatedMember> T _fixAccess(T paramT)
/*     */   {
/* 202 */     if ((paramT != null) && (this._canFixAccess)) {
/* 203 */       ClassUtil.checkAndFixAccess((Member)paramT.getAnnotated());
/*     */     }
/* 205 */     return paramT;
/*     */   }
/*     */   
/*     */ 
/*     */   protected AnnotatedWithParams verifyNonDup(AnnotatedWithParams paramAnnotatedWithParams1, AnnotatedWithParams paramAnnotatedWithParams2, String paramString)
/*     */   {
/* 211 */     if (paramAnnotatedWithParams2 != null)
/*     */     {
/* 213 */       if (paramAnnotatedWithParams2.getClass() == paramAnnotatedWithParams1.getClass()) {
/* 214 */         throw new IllegalArgumentException("Conflicting " + paramString + " creators: already had " + paramAnnotatedWithParams2 + ", encountered " + paramAnnotatedWithParams1);
/*     */       }
/*     */     }
/* 217 */     return (AnnotatedWithParams)_fixAccess(paramAnnotatedWithParams1);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/CreatorCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */