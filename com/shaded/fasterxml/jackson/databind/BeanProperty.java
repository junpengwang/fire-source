/*     */ package com.shaded.fasterxml.jackson.databind;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*     */ import com.shaded.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*     */ import com.shaded.fasterxml.jackson.databind.util.Named;
/*     */ import java.lang.annotation.Annotation;
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
/*     */ public abstract interface BeanProperty
/*     */   extends Named
/*     */ {
/*     */   public abstract String getName();
/*     */   
/*     */   public abstract JavaType getType();
/*     */   
/*     */   public abstract PropertyName getWrapperName();
/*     */   
/*     */   public abstract boolean isRequired();
/*     */   
/*     */   public abstract <A extends Annotation> A getAnnotation(Class<A> paramClass);
/*     */   
/*     */   public abstract <A extends Annotation> A getContextAnnotation(Class<A> paramClass);
/*     */   
/*     */   public abstract AnnotatedMember getMember();
/*     */   
/*     */   public abstract void depositSchemaProperty(JsonObjectFormatVisitor paramJsonObjectFormatVisitor)
/*     */     throws JsonMappingException;
/*     */   
/*     */   public static class Std
/*     */     implements BeanProperty
/*     */   {
/*     */     protected final String _name;
/*     */     protected final JavaType _type;
/*     */     protected final PropertyName _wrapperName;
/*     */     protected final boolean _isRequired;
/*     */     protected final AnnotatedMember _member;
/*     */     protected final Annotations _contextAnnotations;
/*     */     
/*     */     public Std(String paramString, JavaType paramJavaType, PropertyName paramPropertyName, Annotations paramAnnotations, AnnotatedMember paramAnnotatedMember, boolean paramBoolean)
/*     */     {
/* 127 */       this._name = paramString;
/* 128 */       this._type = paramJavaType;
/* 129 */       this._wrapperName = paramPropertyName;
/* 130 */       this._isRequired = paramBoolean;
/* 131 */       this._member = paramAnnotatedMember;
/* 132 */       this._contextAnnotations = paramAnnotations;
/*     */     }
/*     */     
/*     */     public Std withType(JavaType paramJavaType) {
/* 136 */       return new Std(this._name, paramJavaType, this._wrapperName, this._contextAnnotations, this._member, this._isRequired);
/*     */     }
/*     */     
/*     */     public <A extends Annotation> A getAnnotation(Class<A> paramClass)
/*     */     {
/* 141 */       return this._member == null ? null : this._member.getAnnotation(paramClass);
/*     */     }
/*     */     
/*     */     public <A extends Annotation> A getContextAnnotation(Class<A> paramClass)
/*     */     {
/* 146 */       return this._contextAnnotations == null ? null : this._contextAnnotations.get(paramClass);
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 151 */       return this._name;
/*     */     }
/*     */     
/*     */     public JavaType getType()
/*     */     {
/* 156 */       return this._type;
/*     */     }
/*     */     
/*     */     public PropertyName getWrapperName()
/*     */     {
/* 161 */       return this._wrapperName;
/*     */     }
/*     */     
/*     */     public boolean isRequired()
/*     */     {
/* 166 */       return this._isRequired;
/*     */     }
/*     */     
/*     */     public AnnotatedMember getMember()
/*     */     {
/* 171 */       return this._member;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void depositSchemaProperty(JsonObjectFormatVisitor paramJsonObjectFormatVisitor)
/*     */     {
/* 182 */       throw new UnsupportedOperationException("Instances of " + getClass().getName() + " should not get visited");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/BeanProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */