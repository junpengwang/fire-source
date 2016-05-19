/*      */ package com.shaded.fasterxml.jackson.databind;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.annotation.JsonFormat.Value;
/*      */ import com.shaded.fasterxml.jackson.annotation.JsonInclude.Include;
/*      */ import com.shaded.fasterxml.jackson.core.Version;
/*      */ import com.shaded.fasterxml.jackson.core.Versioned;
/*      */ import com.shaded.fasterxml.jackson.databind.annotation.JsonPOJOBuilder.Value;
/*      */ import com.shaded.fasterxml.jackson.databind.annotation.JsonSerialize.Typing;
/*      */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.Annotated;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedField;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMember;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedMethod;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedParameter;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.ObjectIdInfo;
/*      */ import com.shaded.fasterxml.jackson.databind.introspect.VisibilityChecker;
/*      */ import com.shaded.fasterxml.jackson.databind.jsontype.NamedType;
/*      */ import com.shaded.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
/*      */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*      */ import java.io.Serializable;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AnnotationIntrospector
/*      */   implements Versioned, Serializable
/*      */ {
/*      */   public static class ReferenceProperty
/*      */   {
/*      */     private final Type _type;
/*      */     private final String _name;
/*      */     
/*      */     public static enum Type
/*      */     {
/*   60 */       MANAGED_REFERENCE, 
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   68 */       BACK_REFERENCE;
/*      */       
/*      */ 
/*      */       private Type() {}
/*      */     }
/*      */     
/*      */     public ReferenceProperty(Type paramType, String paramString)
/*      */     {
/*   76 */       this._type = paramType;
/*   77 */       this._name = paramString;
/*      */     }
/*      */     
/*   80 */     public static ReferenceProperty managed(String paramString) { return new ReferenceProperty(Type.MANAGED_REFERENCE, paramString); }
/*   81 */     public static ReferenceProperty back(String paramString) { return new ReferenceProperty(Type.BACK_REFERENCE, paramString); }
/*      */     
/*   83 */     public Type getType() { return this._type; }
/*   84 */     public String getName() { return this._name; }
/*      */     
/*   86 */     public boolean isManagedReference() { return this._type == Type.MANAGED_REFERENCE; }
/*   87 */     public boolean isBackReference() { return this._type == Type.BACK_REFERENCE; }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static AnnotationIntrospector nopInstance()
/*      */   {
/*  102 */     return NopAnnotationIntrospector.instance;
/*      */   }
/*      */   
/*      */   public static AnnotationIntrospector pair(AnnotationIntrospector paramAnnotationIntrospector1, AnnotationIntrospector paramAnnotationIntrospector2) {
/*  106 */     return new AnnotationIntrospectorPair(paramAnnotationIntrospector1, paramAnnotationIntrospector2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Collection<AnnotationIntrospector> allIntrospectors()
/*      */   {
/*  127 */     return Collections.singletonList(this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Collection<AnnotationIntrospector> allIntrospectors(Collection<AnnotationIntrospector> paramCollection)
/*      */   {
/*  141 */     paramCollection.add(this);
/*  142 */     return paramCollection;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract Version version();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public boolean isHandled(Annotation paramAnnotation)
/*      */   {
/*  168 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isAnnotationBundle(Annotation paramAnnotation)
/*      */   {
/*  179 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectIdInfo findObjectIdInfo(Annotated paramAnnotated)
/*      */   {
/*  199 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectIdInfo findObjectReferenceInfo(Annotated paramAnnotated, ObjectIdInfo paramObjectIdInfo)
/*      */   {
/*  208 */     return paramObjectIdInfo;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PropertyName findRootName(AnnotatedClass paramAnnotatedClass)
/*      */   {
/*  228 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] findPropertiesToIgnore(Annotated paramAnnotated)
/*      */   {
/*  239 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Boolean findIgnoreUnknownProperties(AnnotatedClass paramAnnotatedClass)
/*      */   {
/*  246 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Boolean isIgnorableType(AnnotatedClass paramAnnotatedClass)
/*      */   {
/*  261 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findFilterId(AnnotatedClass paramAnnotatedClass)
/*      */   {
/*  272 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findNamingStrategy(AnnotatedClass paramAnnotatedClass)
/*      */   {
/*  287 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass paramAnnotatedClass, VisibilityChecker<?> paramVisibilityChecker)
/*      */   {
/*  305 */     return paramVisibilityChecker;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> paramMapperConfig, AnnotatedClass paramAnnotatedClass, JavaType paramJavaType)
/*      */   {
/*  330 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> paramMapperConfig, AnnotatedMember paramAnnotatedMember, JavaType paramJavaType)
/*      */   {
/*  350 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> paramMapperConfig, AnnotatedMember paramAnnotatedMember, JavaType paramJavaType)
/*      */   {
/*  372 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<NamedType> findSubtypes(Annotated paramAnnotated)
/*      */   {
/*  385 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String findTypeName(AnnotatedClass paramAnnotatedClass)
/*      */   {
/*  394 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ReferenceProperty findReferenceType(AnnotatedMember paramAnnotatedMember)
/*      */   {
/*  408 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public NameTransformer findUnwrappingNameTransformer(AnnotatedMember paramAnnotatedMember)
/*      */   {
/*  420 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasIgnoreMarker(AnnotatedMember paramAnnotatedMember)
/*      */   {
/*  431 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findInjectableValueId(AnnotatedMember paramAnnotatedMember)
/*      */   {
/*  448 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Boolean hasRequiredMarker(AnnotatedMember paramAnnotatedMember)
/*      */   {
/*  459 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?>[] findViews(Annotated paramAnnotated)
/*      */   {
/*  476 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public JsonFormat.Value findFormat(AnnotatedMember paramAnnotatedMember)
/*      */   {
/*  491 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonFormat.Value findFormat(Annotated paramAnnotated)
/*      */   {
/*  503 */     if ((paramAnnotated instanceof AnnotatedMember)) {
/*  504 */       return findFormat((AnnotatedMember)paramAnnotated);
/*      */     }
/*  506 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Boolean isTypeId(AnnotatedMember paramAnnotatedMember)
/*      */   {
/*  517 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PropertyName findWrapperName(Annotated paramAnnotated)
/*      */   {
/*  533 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findSerializer(Annotated paramAnnotated)
/*      */   {
/*  550 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findKeySerializer(Annotated paramAnnotated)
/*      */   {
/*  561 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findContentSerializer(Annotated paramAnnotated)
/*      */   {
/*  573 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonInclude.Include findSerializationInclusion(Annotated paramAnnotated, JsonInclude.Include paramInclude)
/*      */   {
/*  587 */     return paramInclude;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> findSerializationType(Annotated paramAnnotated)
/*      */   {
/*  600 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> findSerializationKeyType(Annotated paramAnnotated, JavaType paramJavaType)
/*      */   {
/*  612 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> findSerializationContentType(Annotated paramAnnotated, JavaType paramJavaType)
/*      */   {
/*  624 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JsonSerialize.Typing findSerializationTyping(Annotated paramAnnotated)
/*      */   {
/*  636 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findSerializationConverter(Annotated paramAnnotated)
/*      */   {
/*  661 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findSerializationContentConverter(AnnotatedMember paramAnnotatedMember)
/*      */   {
/*  683 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] findSerializationPropertyOrder(AnnotatedClass paramAnnotatedClass)
/*      */   {
/*  697 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Boolean findSerializationSortAlphabetically(AnnotatedClass paramAnnotatedClass)
/*      */   {
/*  706 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PropertyName findNameForSerialization(Annotated paramAnnotated)
/*      */   {
/*      */     String str;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  734 */     if ((paramAnnotated instanceof AnnotatedField)) {
/*  735 */       str = findSerializationName((AnnotatedField)paramAnnotated);
/*  736 */     } else if ((paramAnnotated instanceof AnnotatedMethod)) {
/*  737 */       str = findSerializationName((AnnotatedMethod)paramAnnotated);
/*      */     } else {
/*  739 */       str = null;
/*      */     }
/*  741 */     if (str != null) {
/*  742 */       if (str.length() == 0) {
/*  743 */         return PropertyName.USE_DEFAULT;
/*      */       }
/*  745 */       return new PropertyName(str);
/*      */     }
/*  747 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public String findSerializationName(AnnotatedMethod paramAnnotatedMethod)
/*      */   {
/*  764 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public String findSerializationName(AnnotatedField paramAnnotatedField)
/*      */   {
/*  781 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasAsValueAnnotation(AnnotatedMethod paramAnnotatedMethod)
/*      */   {
/*  794 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String findEnumValue(Enum<?> paramEnum)
/*      */   {
/*  806 */     return paramEnum.name();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findDeserializer(Annotated paramAnnotated)
/*      */   {
/*  823 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findKeyDeserializer(Annotated paramAnnotated)
/*      */   {
/*  835 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findContentDeserializer(Annotated paramAnnotated)
/*      */   {
/*  848 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> findDeserializationType(Annotated paramAnnotated, JavaType paramJavaType)
/*      */   {
/*  864 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> findDeserializationKeyType(Annotated paramAnnotated, JavaType paramJavaType)
/*      */   {
/*  878 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> findDeserializationContentType(Annotated paramAnnotated, JavaType paramJavaType)
/*      */   {
/*  893 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findDeserializationConverter(Annotated paramAnnotated)
/*      */   {
/*  919 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findDeserializationContentConverter(AnnotatedMember paramAnnotatedMember)
/*      */   {
/*  941 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object findValueInstantiator(AnnotatedClass paramAnnotatedClass)
/*      */   {
/*  956 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> findPOJOBuilder(AnnotatedClass paramAnnotatedClass)
/*      */   {
/*  973 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass paramAnnotatedClass)
/*      */   {
/*  980 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PropertyName findNameForDeserialization(Annotated paramAnnotated)
/*      */   {
/*      */     String str;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1008 */     if ((paramAnnotated instanceof AnnotatedField)) {
/* 1009 */       str = findDeserializationName((AnnotatedField)paramAnnotated);
/* 1010 */     } else if ((paramAnnotated instanceof AnnotatedMethod)) {
/* 1011 */       str = findDeserializationName((AnnotatedMethod)paramAnnotated);
/* 1012 */     } else if ((paramAnnotated instanceof AnnotatedParameter)) {
/* 1013 */       str = findDeserializationName((AnnotatedParameter)paramAnnotated);
/*      */     } else {
/* 1015 */       str = null;
/*      */     }
/* 1017 */     if (str != null) {
/* 1018 */       if (str.length() == 0) {
/* 1019 */         return PropertyName.USE_DEFAULT;
/*      */       }
/* 1021 */       return new PropertyName(str);
/*      */     }
/* 1023 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public String findDeserializationName(AnnotatedMethod paramAnnotatedMethod)
/*      */   {
/* 1040 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public String findDeserializationName(AnnotatedField paramAnnotatedField)
/*      */   {
/* 1057 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public String findDeserializationName(AnnotatedParameter paramAnnotatedParameter)
/*      */   {
/* 1070 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasAnySetterAnnotation(AnnotatedMethod paramAnnotatedMethod)
/*      */   {
/* 1083 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasAnyGetterAnnotation(AnnotatedMethod paramAnnotatedMethod)
/*      */   {
/* 1096 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasCreatorAnnotation(Annotated paramAnnotated)
/*      */   {
/* 1110 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public static class Pair
/*      */     extends AnnotationIntrospectorPair
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     @Deprecated
/*      */     public Pair(AnnotationIntrospector paramAnnotationIntrospector1, AnnotationIntrospector paramAnnotationIntrospector2)
/*      */     {
/* 1132 */       super(paramAnnotationIntrospector2);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/AnnotationIntrospector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */