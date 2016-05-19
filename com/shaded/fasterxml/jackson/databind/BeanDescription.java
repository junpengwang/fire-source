/*    */ package com.shaded.fasterxml.jackson.databind;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Value;
/*    */ import com.shaded.fasterxml.jackson.annotation.JsonInclude.Include;
/*    */ import com.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value;
/*    */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*    */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
/*    */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*    */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*    */ import com.shaded.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
/*    */ import com.shaded.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*    */ import com.shaded.fasterxml.jackson.databind.type.TypeBindings;
/*    */ import com.shaded.fasterxml.jackson.databind.util.Annotations;
/*    */ import com.shaded.fasterxml.jackson.databind.util.Converter;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BeanDescription
/*    */ {
/*    */   protected final JavaType _type;
/*    */   
/*    */   protected BeanDescription(JavaType paramJavaType)
/*    */   {
/* 44 */     this._type = paramJavaType;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 57 */   public JavaType getType() { return this._type; }
/*    */   
/* 59 */   public Class<?> getBeanClass() { return this._type.getRawClass(); }
/*    */   
/*    */   public abstract AnnotatedClass getClassInfo();
/*    */   
/*    */   public abstract ObjectIdInfo getObjectIdInfo();
/*    */   
/*    */   public abstract boolean hasKnownClassAnnotations();
/*    */   
/*    */   public abstract TypeBindings bindingsForBeanType();
/*    */   
/*    */   public abstract JavaType resolveType(Type paramType);
/*    */   
/*    */   public abstract Annotations getClassAnnotations();
/*    */   
/*    */   public abstract List<BeanPropertyDefinition> findProperties();
/*    */   
/*    */   public abstract Map<String, AnnotatedMember> findBackReferenceProperties();
/*    */   
/*    */   public abstract Set<String> getIgnoredPropertyNames();
/*    */   
/*    */   public abstract List<AnnotatedConstructor> getConstructors();
/*    */   
/*    */   public abstract List<AnnotatedMethod> getFactoryMethods();
/*    */   
/*    */   public abstract AnnotatedConstructor findDefaultConstructor();
/*    */   
/*    */   public abstract Constructor<?> findSingleArgConstructor(Class<?>... paramVarArgs);
/*    */   
/*    */   public abstract Method findFactoryMethod(Class<?>... paramVarArgs);
/*    */   
/*    */   public abstract AnnotatedMember findAnyGetter();
/*    */   
/*    */   public abstract AnnotatedMethod findAnySetter();
/*    */   
/*    */   public abstract AnnotatedMethod findJsonValueMethod();
/*    */   
/*    */   public abstract AnnotatedMethod findMethod(String paramString, Class<?>[] paramArrayOfClass);
/*    */   
/*    */   public abstract JsonInclude.Include findSerializationInclusion(JsonInclude.Include paramInclude);
/*    */   
/*    */   public abstract JsonFormat.Value findExpectedFormat(JsonFormat.Value paramValue);
/*    */   
/*    */   public abstract Converter<Object, Object> findSerializationConverter();
/*    */   
/*    */   public abstract Converter<Object, Object> findDeserializationConverter();
/*    */   
/*    */   public abstract Map<Object, AnnotatedMember> findInjectables();
/*    */   
/*    */   public abstract Class<?> findPOJOBuilder();
/*    */   
/*    */   public abstract JsonPOJOBuilder.Value findPOJOBuilderConfig();
/*    */   
/*    */   public abstract Object instantiateBean(boolean paramBoolean);
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/BeanDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */