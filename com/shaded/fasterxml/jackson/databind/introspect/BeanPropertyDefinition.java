/*     */ package com.shaded.fasterxml.jackson.databind.introspect;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.PropertyName;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Named;
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
/*     */ public abstract class BeanPropertyDefinition
/*     */   implements Named
/*     */ {
/*     */   public abstract BeanPropertyDefinition withName(String paramString);
/*     */   
/*     */   public abstract String getName();
/*     */   
/*     */   public abstract String getInternalName();
/*     */   
/*     */   public abstract PropertyName getWrapperName();
/*     */   
/*     */   public abstract boolean isExplicitlyIncluded();
/*     */   
/*     */   public boolean couldDeserialize()
/*     */   {
/*  79 */     return getMutator() != null;
/*     */   }
/*     */   
/*  82 */   public boolean couldSerialize() { return getAccessor() != null; }
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean hasGetter();
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean hasSetter();
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean hasField();
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract boolean hasConstructorParameter();
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract AnnotatedMethod getGetter();
/*     */   
/*     */ 
/*     */ 
/*     */   public abstract AnnotatedMethod getSetter();
/*     */   
/*     */ 
/*     */   public abstract AnnotatedField getField();
/*     */   
/*     */ 
/*     */   public abstract AnnotatedParameter getConstructorParameter();
/*     */   
/*     */ 
/*     */   public abstract AnnotatedMember getAccessor();
/*     */   
/*     */ 
/*     */   public abstract AnnotatedMember getMutator();
/*     */   
/*     */ 
/*     */   public AnnotatedMember getPrimaryMember()
/*     */   {
/* 123 */     return null;
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
/*     */   public Class<?>[] findViews()
/*     */   {
/* 138 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public AnnotationIntrospector.ReferenceProperty findReferenceType()
/*     */   {
/* 145 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isTypeId()
/*     */   {
/* 154 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectIdInfo findObjectIdInfo()
/*     */   {
/* 163 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isRequired()
/*     */   {
/* 173 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/BeanPropertyDefinition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */