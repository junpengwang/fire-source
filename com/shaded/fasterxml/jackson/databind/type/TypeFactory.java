/*      */ package com.shaded.fasterxml.jackson.databind.type;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.core.type.TypeReference;
/*      */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*      */ import com.shaded.fasterxml.jackson.databind.util.ArrayBuilders;
/*      */ import com.shaded.fasterxml.jackson.databind.util.LRUMap;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.GenericArrayType;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.lang.reflect.TypeVariable;
/*      */ import java.lang.reflect.WildcardType;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
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
/*      */ public final class TypeFactory
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*   38 */   private static final JavaType[] NO_TYPES = new JavaType[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   45 */   protected static final TypeFactory instance = new TypeFactory();
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
/*   57 */   protected static final SimpleType CORE_TYPE_STRING = new SimpleType(String.class);
/*   58 */   protected static final SimpleType CORE_TYPE_BOOL = new SimpleType(Boolean.TYPE);
/*   59 */   protected static final SimpleType CORE_TYPE_INT = new SimpleType(Integer.TYPE);
/*   60 */   protected static final SimpleType CORE_TYPE_LONG = new SimpleType(Long.TYPE);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   67 */   protected final LRUMap<ClassKey, JavaType> _typeCache = new LRUMap(16, 100);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected transient HierarchicType _cachedHashMapType;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected transient HierarchicType _cachedArrayListType;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final TypeModifier[] _modifiers;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final TypeParser _parser;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private TypeFactory()
/*      */   {
/*  108 */     this._parser = new TypeParser(this);
/*  109 */     this._modifiers = null;
/*      */   }
/*      */   
/*      */   protected TypeFactory(TypeParser paramTypeParser, TypeModifier[] paramArrayOfTypeModifier) {
/*  113 */     this._parser = paramTypeParser;
/*  114 */     this._modifiers = paramArrayOfTypeModifier;
/*      */   }
/*      */   
/*      */   public TypeFactory withModifier(TypeModifier paramTypeModifier)
/*      */   {
/*  119 */     if (this._modifiers == null) {
/*  120 */       return new TypeFactory(this._parser, new TypeModifier[] { paramTypeModifier });
/*      */     }
/*  122 */     return new TypeFactory(this._parser, (TypeModifier[])ArrayBuilders.insertInListNoDup(this._modifiers, paramTypeModifier));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TypeFactory defaultInstance()
/*      */   {
/*  130 */     return instance;
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
/*      */   public static JavaType unknownType()
/*      */   {
/*  144 */     return defaultInstance()._unknownType();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Class<?> rawClass(Type paramType)
/*      */   {
/*  154 */     if ((paramType instanceof Class)) {
/*  155 */       return (Class)paramType;
/*      */     }
/*      */     
/*  158 */     return defaultInstance().constructType(paramType).getRawClass();
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
/*      */   public JavaType constructSpecializedType(JavaType paramJavaType, Class<?> paramClass)
/*      */   {
/*  177 */     if ((paramJavaType instanceof SimpleType))
/*      */     {
/*  179 */       if ((paramClass.isArray()) || (Map.class.isAssignableFrom(paramClass)) || (Collection.class.isAssignableFrom(paramClass)))
/*      */       {
/*      */ 
/*      */ 
/*  183 */         if (!paramJavaType.getRawClass().isAssignableFrom(paramClass)) {
/*  184 */           throw new IllegalArgumentException("Class " + paramClass.getClass().getName() + " not subtype of " + paramJavaType);
/*      */         }
/*      */         
/*  187 */         JavaType localJavaType = _fromClass(paramClass, new TypeBindings(this, paramJavaType.getRawClass()));
/*      */         
/*  189 */         Object localObject = paramJavaType.getValueHandler();
/*  190 */         if (localObject != null) {
/*  191 */           localJavaType = localJavaType.withValueHandler(localObject);
/*      */         }
/*  193 */         localObject = paramJavaType.getTypeHandler();
/*  194 */         if (localObject != null) {
/*  195 */           localJavaType = localJavaType.withTypeHandler(localObject);
/*      */         }
/*  197 */         return localJavaType;
/*      */       }
/*      */     }
/*      */     
/*  201 */     return paramJavaType.narrowBy(paramClass);
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
/*      */   public JavaType constructFromCanonical(String paramString)
/*      */     throws IllegalArgumentException
/*      */   {
/*  216 */     return this._parser.parse(paramString);
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
/*      */   public JavaType[] findTypeParameters(JavaType paramJavaType, Class<?> paramClass)
/*      */   {
/*  238 */     Class localClass = paramJavaType.getRawClass();
/*  239 */     if (localClass == paramClass)
/*      */     {
/*  241 */       int i = paramJavaType.containedTypeCount();
/*  242 */       if (i == 0) return null;
/*  243 */       JavaType[] arrayOfJavaType = new JavaType[i];
/*  244 */       for (int j = 0; j < i; j++) {
/*  245 */         arrayOfJavaType[j] = paramJavaType.containedType(j);
/*      */       }
/*  247 */       return arrayOfJavaType;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  255 */     return findTypeParameters(localClass, paramClass, new TypeBindings(this, paramJavaType));
/*      */   }
/*      */   
/*      */   public JavaType[] findTypeParameters(Class<?> paramClass1, Class<?> paramClass2) {
/*  259 */     return findTypeParameters(paramClass1, paramClass2, new TypeBindings(this, paramClass1));
/*      */   }
/*      */   
/*      */ 
/*      */   public JavaType[] findTypeParameters(Class<?> paramClass1, Class<?> paramClass2, TypeBindings paramTypeBindings)
/*      */   {
/*  265 */     HierarchicType localHierarchicType1 = _findSuperTypeChain(paramClass1, paramClass2);
/*      */     
/*  267 */     if (localHierarchicType1 == null) {
/*  268 */       throw new IllegalArgumentException("Class " + paramClass1.getName() + " is not a subtype of " + paramClass2.getName());
/*      */     }
/*      */     
/*  271 */     HierarchicType localHierarchicType2 = localHierarchicType1;
/*  272 */     while (localHierarchicType2.getSuperType() != null) {
/*  273 */       localHierarchicType2 = localHierarchicType2.getSuperType();
/*  274 */       Class localClass = localHierarchicType2.getRawClass();
/*  275 */       TypeBindings localTypeBindings = new TypeBindings(this, localClass);
/*  276 */       if (localHierarchicType2.isGeneric()) {
/*  277 */         ParameterizedType localParameterizedType = localHierarchicType2.asGeneric();
/*  278 */         Type[] arrayOfType = localParameterizedType.getActualTypeArguments();
/*  279 */         TypeVariable[] arrayOfTypeVariable = localClass.getTypeParameters();
/*  280 */         int i = arrayOfType.length;
/*  281 */         for (int j = 0; j < i; j++) {
/*  282 */           String str = arrayOfTypeVariable[j].getName();
/*  283 */           JavaType localJavaType = _constructType(arrayOfType[j], paramTypeBindings);
/*  284 */           localTypeBindings.addBinding(str, localJavaType);
/*      */         }
/*      */       }
/*  287 */       paramTypeBindings = localTypeBindings;
/*      */     }
/*      */     
/*      */ 
/*  291 */     if (!localHierarchicType2.isGeneric()) {
/*  292 */       return null;
/*      */     }
/*  294 */     return paramTypeBindings.typesAsArray();
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
/*      */   public JavaType moreSpecificType(JavaType paramJavaType1, JavaType paramJavaType2)
/*      */   {
/*  309 */     if (paramJavaType1 == null) {
/*  310 */       return paramJavaType2;
/*      */     }
/*  312 */     if (paramJavaType2 == null) {
/*  313 */       return paramJavaType1;
/*      */     }
/*  315 */     Class localClass1 = paramJavaType1.getRawClass();
/*  316 */     Class localClass2 = paramJavaType2.getRawClass();
/*  317 */     if (localClass1 == localClass2) {
/*  318 */       return paramJavaType1;
/*      */     }
/*      */     
/*  321 */     if (localClass1.isAssignableFrom(localClass2)) {
/*  322 */       return paramJavaType2;
/*      */     }
/*  324 */     return paramJavaType1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JavaType constructType(Type paramType)
/*      */   {
/*  334 */     return _constructType(paramType, null);
/*      */   }
/*      */   
/*      */   public JavaType constructType(Type paramType, TypeBindings paramTypeBindings) {
/*  338 */     return _constructType(paramType, paramTypeBindings);
/*      */   }
/*      */   
/*      */   public JavaType constructType(TypeReference<?> paramTypeReference) {
/*  342 */     return _constructType(paramTypeReference.getType(), null);
/*      */   }
/*      */   
/*      */   public JavaType constructType(Type paramType, Class<?> paramClass) {
/*  346 */     TypeBindings localTypeBindings = paramClass == null ? null : new TypeBindings(this, paramClass);
/*  347 */     return _constructType(paramType, localTypeBindings);
/*      */   }
/*      */   
/*      */   public JavaType constructType(Type paramType, JavaType paramJavaType) {
/*  351 */     TypeBindings localTypeBindings = paramJavaType == null ? null : new TypeBindings(this, paramJavaType);
/*  352 */     return _constructType(paramType, localTypeBindings);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected JavaType _constructType(Type paramType, TypeBindings paramTypeBindings)
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */     JavaType localJavaType;
/*      */     
/*      */ 
/*  365 */     if ((paramType instanceof Class)) {
/*  366 */       localObject1 = (Class)paramType;
/*  367 */       localJavaType = _fromClass((Class)localObject1, paramTypeBindings);
/*      */ 
/*      */     }
/*  370 */     else if ((paramType instanceof ParameterizedType)) {
/*  371 */       localJavaType = _fromParamType((ParameterizedType)paramType, paramTypeBindings);
/*      */     } else {
/*  373 */       if ((paramType instanceof JavaType)) {
/*  374 */         return (JavaType)paramType;
/*      */       }
/*  376 */       if ((paramType instanceof GenericArrayType)) {
/*  377 */         localJavaType = _fromArrayType((GenericArrayType)paramType, paramTypeBindings);
/*      */       }
/*  379 */       else if ((paramType instanceof TypeVariable)) {
/*  380 */         localJavaType = _fromVariable((TypeVariable)paramType, paramTypeBindings);
/*      */       }
/*  382 */       else if ((paramType instanceof WildcardType)) {
/*  383 */         localJavaType = _fromWildcard((WildcardType)paramType, paramTypeBindings);
/*      */       }
/*      */       else {
/*  386 */         throw new IllegalArgumentException("Unrecognized Type: " + (paramType == null ? "[null]" : paramType.toString()));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  392 */     if ((this._modifiers != null) && (!localJavaType.isContainerType())) {
/*  393 */       for (Object localObject2 : this._modifiers) {
/*  394 */         localJavaType = ((TypeModifier)localObject2).modifyType(localJavaType, paramType, paramTypeBindings, this);
/*      */       }
/*      */     }
/*  397 */     return localJavaType;
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
/*      */   public ArrayType constructArrayType(Class<?> paramClass)
/*      */   {
/*  413 */     return ArrayType.construct(_constructType(paramClass, null), null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayType constructArrayType(JavaType paramJavaType)
/*      */   {
/*  423 */     return ArrayType.construct(paramJavaType, null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CollectionType constructCollectionType(Class<? extends Collection> paramClass, Class<?> paramClass1)
/*      */   {
/*  433 */     return CollectionType.construct(paramClass, constructType(paramClass1));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CollectionType constructCollectionType(Class<? extends Collection> paramClass, JavaType paramJavaType)
/*      */   {
/*  443 */     return CollectionType.construct(paramClass, paramJavaType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CollectionLikeType constructCollectionLikeType(Class<?> paramClass1, Class<?> paramClass2)
/*      */   {
/*  453 */     return CollectionLikeType.construct(paramClass1, constructType(paramClass2));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CollectionLikeType constructCollectionLikeType(Class<?> paramClass, JavaType paramJavaType)
/*      */   {
/*  463 */     return CollectionLikeType.construct(paramClass, paramJavaType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MapType constructMapType(Class<? extends Map> paramClass, JavaType paramJavaType1, JavaType paramJavaType2)
/*      */   {
/*  473 */     return MapType.construct(paramClass, paramJavaType1, paramJavaType2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MapType constructMapType(Class<? extends Map> paramClass, Class<?> paramClass1, Class<?> paramClass2)
/*      */   {
/*  483 */     return MapType.construct(paramClass, constructType(paramClass1), constructType(paramClass2));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MapLikeType constructMapLikeType(Class<?> paramClass, JavaType paramJavaType1, JavaType paramJavaType2)
/*      */   {
/*  493 */     return MapLikeType.construct(paramClass, paramJavaType1, paramJavaType2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MapLikeType constructMapLikeType(Class<?> paramClass1, Class<?> paramClass2, Class<?> paramClass3)
/*      */   {
/*  503 */     return MapType.construct(paramClass1, constructType(paramClass2), constructType(paramClass3));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JavaType constructSimpleType(Class<?> paramClass, JavaType[] paramArrayOfJavaType)
/*      */   {
/*  512 */     TypeVariable[] arrayOfTypeVariable = paramClass.getTypeParameters();
/*  513 */     if (arrayOfTypeVariable.length != paramArrayOfJavaType.length) {
/*  514 */       throw new IllegalArgumentException("Parameter type mismatch for " + paramClass.getName() + ": expected " + arrayOfTypeVariable.length + " parameters, was given " + paramArrayOfJavaType.length);
/*      */     }
/*      */     
/*  517 */     String[] arrayOfString = new String[arrayOfTypeVariable.length];
/*  518 */     int i = 0; for (int j = arrayOfTypeVariable.length; i < j; i++) {
/*  519 */       arrayOfString[i] = arrayOfTypeVariable[i].getName();
/*      */     }
/*  521 */     SimpleType localSimpleType = new SimpleType(paramClass, arrayOfString, paramArrayOfJavaType, null, null, false);
/*  522 */     return localSimpleType;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public JavaType uncheckedSimpleType(Class<?> paramClass)
/*      */   {
/*  533 */     return new SimpleType(paramClass);
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
/*      */   public JavaType constructParametricType(Class<?> paramClass, Class<?>... paramVarArgs)
/*      */   {
/*  550 */     int i = paramVarArgs.length;
/*  551 */     JavaType[] arrayOfJavaType = new JavaType[i];
/*  552 */     for (int j = 0; j < i; j++) {
/*  553 */       arrayOfJavaType[j] = _fromClass(paramVarArgs[j], null);
/*      */     }
/*  555 */     return constructParametricType(paramClass, arrayOfJavaType);
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
/*      */   public JavaType constructParametricType(Class<?> paramClass, JavaType... paramVarArgs)
/*      */   {
/*      */     Object localObject;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  576 */     if (paramClass.isArray())
/*      */     {
/*  578 */       if (paramVarArgs.length != 1) {
/*  579 */         throw new IllegalArgumentException("Need exactly 1 parameter type for arrays (" + paramClass.getName() + ")");
/*      */       }
/*  581 */       localObject = constructArrayType(paramVarArgs[0]);
/*      */     }
/*  583 */     else if (Map.class.isAssignableFrom(paramClass)) {
/*  584 */       if (paramVarArgs.length != 2) {
/*  585 */         throw new IllegalArgumentException("Need exactly 2 parameter types for Map types (" + paramClass.getName() + ")");
/*      */       }
/*  587 */       localObject = constructMapType(paramClass, paramVarArgs[0], paramVarArgs[1]);
/*      */     }
/*  589 */     else if (Collection.class.isAssignableFrom(paramClass)) {
/*  590 */       if (paramVarArgs.length != 1) {
/*  591 */         throw new IllegalArgumentException("Need exactly 1 parameter type for Collection types (" + paramClass.getName() + ")");
/*      */       }
/*  593 */       localObject = constructCollectionType(paramClass, paramVarArgs[0]);
/*      */     } else {
/*  595 */       localObject = constructSimpleType(paramClass, paramVarArgs);
/*      */     }
/*  597 */     return (JavaType)localObject;
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
/*      */   public CollectionType constructRawCollectionType(Class<? extends Collection> paramClass)
/*      */   {
/*  619 */     return CollectionType.construct(paramClass, unknownType());
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
/*      */   public CollectionLikeType constructRawCollectionLikeType(Class<?> paramClass)
/*      */   {
/*  634 */     return CollectionLikeType.construct(paramClass, unknownType());
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
/*      */   public MapType constructRawMapType(Class<? extends Map> paramClass)
/*      */   {
/*  649 */     return MapType.construct(paramClass, unknownType(), unknownType());
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
/*      */   public MapLikeType constructRawMapLikeType(Class<?> paramClass)
/*      */   {
/*  664 */     return MapLikeType.construct(paramClass, unknownType(), unknownType());
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
/*      */   protected JavaType _fromClass(Class<?> paramClass, TypeBindings paramTypeBindings)
/*      */   {
/*  680 */     if (paramClass == String.class) return CORE_TYPE_STRING;
/*  681 */     if (paramClass == Boolean.TYPE) return CORE_TYPE_BOOL;
/*  682 */     if (paramClass == Integer.TYPE) return CORE_TYPE_INT;
/*  683 */     if (paramClass == Long.TYPE) { return CORE_TYPE_LONG;
/*      */     }
/*      */     
/*  686 */     ClassKey localClassKey = new ClassKey(paramClass);
/*      */     
/*      */     Object localObject1;
/*  689 */     synchronized (this._typeCache) {
/*  690 */       localObject1 = (JavaType)this._typeCache.get(localClassKey);
/*      */     }
/*  692 */     if (localObject1 != null) {
/*  693 */       return (JavaType)localObject1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  704 */     if (paramClass.isArray()) {
/*  705 */       localObject1 = ArrayType.construct(_constructType(paramClass.getComponentType(), null), null, null);
/*      */ 
/*      */ 
/*      */     }
/*  709 */     else if (paramClass.isEnum()) {
/*  710 */       localObject1 = new SimpleType(paramClass);
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*  715 */     else if (Map.class.isAssignableFrom(paramClass)) {
/*  716 */       localObject1 = _mapType(paramClass);
/*  717 */     } else if (Collection.class.isAssignableFrom(paramClass)) {
/*  718 */       localObject1 = _collectionType(paramClass);
/*      */     } else {
/*  720 */       localObject1 = new SimpleType(paramClass);
/*      */     }
/*      */     
/*  723 */     synchronized (this._typeCache) {
/*  724 */       this._typeCache.put(localClassKey, localObject1);
/*      */     }
/*      */     
/*  727 */     return (JavaType)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JavaType _fromParameterizedClass(Class<?> paramClass, List<JavaType> paramList)
/*      */   {
/*  736 */     if (paramClass.isArray()) {
/*  737 */       return ArrayType.construct(_constructType(paramClass.getComponentType(), null), null, null);
/*      */     }
/*  739 */     if (paramClass.isEnum()) {
/*  740 */       return new SimpleType(paramClass);
/*      */     }
/*  742 */     if (Map.class.isAssignableFrom(paramClass))
/*      */     {
/*      */ 
/*  745 */       if (paramList.size() > 0) {
/*  746 */         localObject = (JavaType)paramList.get(0);
/*  747 */         JavaType localJavaType = paramList.size() >= 2 ? (JavaType)paramList.get(1) : _unknownType();
/*      */         
/*  749 */         return MapType.construct(paramClass, (JavaType)localObject, localJavaType);
/*      */       }
/*  751 */       return _mapType(paramClass);
/*      */     }
/*  753 */     if (Collection.class.isAssignableFrom(paramClass)) {
/*  754 */       if (paramList.size() >= 1) {
/*  755 */         return CollectionType.construct(paramClass, (JavaType)paramList.get(0));
/*      */       }
/*  757 */       return _collectionType(paramClass);
/*      */     }
/*  759 */     if (paramList.size() == 0) {
/*  760 */       return new SimpleType(paramClass);
/*      */     }
/*  762 */     Object localObject = (JavaType[])paramList.toArray(new JavaType[paramList.size()]);
/*  763 */     return constructSimpleType(paramClass, (JavaType[])localObject);
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
/*      */   protected JavaType _fromParamType(ParameterizedType paramParameterizedType, TypeBindings paramTypeBindings)
/*      */   {
/*  778 */     Class localClass = (Class)paramParameterizedType.getRawType();
/*  779 */     Type[] arrayOfType = paramParameterizedType.getActualTypeArguments();
/*  780 */     int i = arrayOfType == null ? 0 : arrayOfType.length;
/*      */     
/*      */     JavaType[] arrayOfJavaType1;
/*      */     
/*  784 */     if (i == 0) {
/*  785 */       arrayOfJavaType1 = NO_TYPES;
/*      */     } else {
/*  787 */       arrayOfJavaType1 = new JavaType[i];
/*  788 */       for (int j = 0; j < i; j++) {
/*  789 */         arrayOfJavaType1[j] = _constructType(arrayOfType[j], paramTypeBindings);
/*      */       }
/*      */     }
/*      */     JavaType localJavaType;
/*      */     JavaType[] arrayOfJavaType2;
/*  794 */     if (Map.class.isAssignableFrom(localClass)) {
/*  795 */       localJavaType = constructSimpleType(localClass, arrayOfJavaType1);
/*  796 */       arrayOfJavaType2 = findTypeParameters(localJavaType, Map.class);
/*  797 */       if (arrayOfJavaType2.length != 2) {
/*  798 */         throw new IllegalArgumentException("Could not find 2 type parameters for Map class " + localClass.getName() + " (found " + arrayOfJavaType2.length + ")");
/*      */       }
/*  800 */       return MapType.construct(localClass, arrayOfJavaType2[0], arrayOfJavaType2[1]);
/*      */     }
/*  802 */     if (Collection.class.isAssignableFrom(localClass)) {
/*  803 */       localJavaType = constructSimpleType(localClass, arrayOfJavaType1);
/*  804 */       arrayOfJavaType2 = findTypeParameters(localJavaType, Collection.class);
/*  805 */       if (arrayOfJavaType2.length != 1) {
/*  806 */         throw new IllegalArgumentException("Could not find 1 type parameter for Collection class " + localClass.getName() + " (found " + arrayOfJavaType2.length + ")");
/*      */       }
/*  808 */       return CollectionType.construct(localClass, arrayOfJavaType2[0]);
/*      */     }
/*  810 */     if (i == 0) {
/*  811 */       return new SimpleType(localClass);
/*      */     }
/*  813 */     return constructSimpleType(localClass, arrayOfJavaType1);
/*      */   }
/*      */   
/*      */ 
/*      */   protected JavaType _fromArrayType(GenericArrayType paramGenericArrayType, TypeBindings paramTypeBindings)
/*      */   {
/*  819 */     JavaType localJavaType = _constructType(paramGenericArrayType.getGenericComponentType(), paramTypeBindings);
/*  820 */     return ArrayType.construct(localJavaType, null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected JavaType _fromVariable(TypeVariable<?> paramTypeVariable, TypeBindings paramTypeBindings)
/*      */   {
/*  829 */     if (paramTypeBindings == null) {
/*  830 */       return _unknownType();
/*      */     }
/*      */     
/*      */ 
/*  834 */     String str = paramTypeVariable.getName();
/*  835 */     JavaType localJavaType = paramTypeBindings.findType(str);
/*  836 */     if (localJavaType != null) {
/*  837 */       return localJavaType;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  845 */     Type[] arrayOfType = paramTypeVariable.getBounds();
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
/*  860 */     paramTypeBindings._addPlaceholder(str);
/*  861 */     return _constructType(arrayOfType[0], paramTypeBindings);
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
/*      */   protected JavaType _fromWildcard(WildcardType paramWildcardType, TypeBindings paramTypeBindings)
/*      */   {
/*  874 */     return _constructType(paramWildcardType.getUpperBounds()[0], paramTypeBindings);
/*      */   }
/*      */   
/*      */   private JavaType _mapType(Class<?> paramClass)
/*      */   {
/*  879 */     JavaType[] arrayOfJavaType = findTypeParameters(paramClass, Map.class);
/*      */     
/*  881 */     if (arrayOfJavaType == null) {
/*  882 */       return MapType.construct(paramClass, _unknownType(), _unknownType());
/*      */     }
/*      */     
/*  885 */     if (arrayOfJavaType.length != 2) {
/*  886 */       throw new IllegalArgumentException("Strange Map type " + paramClass.getName() + ": can not determine type parameters");
/*      */     }
/*  888 */     return MapType.construct(paramClass, arrayOfJavaType[0], arrayOfJavaType[1]);
/*      */   }
/*      */   
/*      */   private JavaType _collectionType(Class<?> paramClass)
/*      */   {
/*  893 */     JavaType[] arrayOfJavaType = findTypeParameters(paramClass, Collection.class);
/*      */     
/*  895 */     if (arrayOfJavaType == null) {
/*  896 */       return CollectionType.construct(paramClass, _unknownType());
/*      */     }
/*      */     
/*  899 */     if (arrayOfJavaType.length != 1) {
/*  900 */       throw new IllegalArgumentException("Strange Collection type " + paramClass.getName() + ": can not determine type parameters");
/*      */     }
/*  902 */     return CollectionType.construct(paramClass, arrayOfJavaType[0]);
/*      */   }
/*      */   
/*      */ 
/*      */   protected JavaType _resolveVariableViaSubTypes(HierarchicType paramHierarchicType, String paramString, TypeBindings paramTypeBindings)
/*      */   {
/*  908 */     if ((paramHierarchicType != null) && (paramHierarchicType.isGeneric())) {
/*  909 */       TypeVariable[] arrayOfTypeVariable = paramHierarchicType.getRawClass().getTypeParameters();
/*  910 */       int i = 0; for (int j = arrayOfTypeVariable.length; i < j; i++) {
/*  911 */         TypeVariable localTypeVariable = arrayOfTypeVariable[i];
/*  912 */         if (paramString.equals(localTypeVariable.getName()))
/*      */         {
/*  914 */           Type localType = paramHierarchicType.asGeneric().getActualTypeArguments()[i];
/*  915 */           if ((localType instanceof TypeVariable)) {
/*  916 */             return _resolveVariableViaSubTypes(paramHierarchicType.getSubType(), ((TypeVariable)localType).getName(), paramTypeBindings);
/*      */           }
/*      */           
/*  919 */           return _constructType(localType, paramTypeBindings);
/*      */         }
/*      */       }
/*      */     }
/*  923 */     return _unknownType();
/*      */   }
/*      */   
/*      */   protected JavaType _unknownType() {
/*  927 */     return new SimpleType(Object.class);
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
/*      */   protected HierarchicType _findSuperTypeChain(Class<?> paramClass1, Class<?> paramClass2)
/*      */   {
/*  945 */     if (paramClass2.isInterface()) {
/*  946 */       return _findSuperInterfaceChain(paramClass1, paramClass2);
/*      */     }
/*  948 */     return _findSuperClassChain(paramClass1, paramClass2);
/*      */   }
/*      */   
/*      */   protected HierarchicType _findSuperClassChain(Type paramType, Class<?> paramClass)
/*      */   {
/*  953 */     HierarchicType localHierarchicType1 = new HierarchicType(paramType);
/*  954 */     Class localClass = localHierarchicType1.getRawClass();
/*  955 */     if (localClass == paramClass) {
/*  956 */       return localHierarchicType1;
/*      */     }
/*      */     
/*  959 */     Type localType = localClass.getGenericSuperclass();
/*  960 */     if (localType != null) {
/*  961 */       HierarchicType localHierarchicType2 = _findSuperClassChain(localType, paramClass);
/*  962 */       if (localHierarchicType2 != null) {
/*  963 */         localHierarchicType2.setSubType(localHierarchicType1);
/*  964 */         localHierarchicType1.setSuperType(localHierarchicType2);
/*  965 */         return localHierarchicType1;
/*      */       }
/*      */     }
/*  968 */     return null;
/*      */   }
/*      */   
/*      */   protected HierarchicType _findSuperInterfaceChain(Type paramType, Class<?> paramClass)
/*      */   {
/*  973 */     HierarchicType localHierarchicType = new HierarchicType(paramType);
/*  974 */     Class localClass = localHierarchicType.getRawClass();
/*  975 */     if (localClass == paramClass) {
/*  976 */       return new HierarchicType(paramType);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  982 */     if ((localClass == HashMap.class) && 
/*  983 */       (paramClass == Map.class)) {
/*  984 */       return _hashMapSuperInterfaceChain(localHierarchicType);
/*      */     }
/*      */     
/*  987 */     if ((localClass == ArrayList.class) && 
/*  988 */       (paramClass == List.class)) {
/*  989 */       return _arrayListSuperInterfaceChain(localHierarchicType);
/*      */     }
/*      */     
/*  992 */     return _doFindSuperInterfaceChain(localHierarchicType, paramClass);
/*      */   }
/*      */   
/*      */   protected HierarchicType _doFindSuperInterfaceChain(HierarchicType paramHierarchicType, Class<?> paramClass)
/*      */   {
/*  997 */     Class localClass = paramHierarchicType.getRawClass();
/*  998 */     Type[] arrayOfType = localClass.getGenericInterfaces();
/*      */     
/*      */ 
/* 1001 */     if (arrayOfType != null) {
/* 1002 */       for (Type localType : arrayOfType) {
/* 1003 */         HierarchicType localHierarchicType2 = _findSuperInterfaceChain(localType, paramClass);
/* 1004 */         if (localHierarchicType2 != null) {
/* 1005 */           localHierarchicType2.setSubType(paramHierarchicType);
/* 1006 */           paramHierarchicType.setSuperType(localHierarchicType2);
/* 1007 */           return paramHierarchicType;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1012 */     ??? = localClass.getGenericSuperclass();
/* 1013 */     if (??? != null) {
/* 1014 */       HierarchicType localHierarchicType1 = _findSuperInterfaceChain((Type)???, paramClass);
/* 1015 */       if (localHierarchicType1 != null) {
/* 1016 */         localHierarchicType1.setSubType(paramHierarchicType);
/* 1017 */         paramHierarchicType.setSuperType(localHierarchicType1);
/* 1018 */         return paramHierarchicType;
/*      */       }
/*      */     }
/* 1021 */     return null;
/*      */   }
/*      */   
/*      */   protected synchronized HierarchicType _hashMapSuperInterfaceChain(HierarchicType paramHierarchicType)
/*      */   {
/* 1026 */     if (this._cachedHashMapType == null) {
/* 1027 */       localHierarchicType = paramHierarchicType.deepCloneWithoutSubtype();
/* 1028 */       _doFindSuperInterfaceChain(localHierarchicType, Map.class);
/* 1029 */       this._cachedHashMapType = localHierarchicType.getSuperType();
/*      */     }
/* 1031 */     HierarchicType localHierarchicType = this._cachedHashMapType.deepCloneWithoutSubtype();
/* 1032 */     paramHierarchicType.setSuperType(localHierarchicType);
/* 1033 */     localHierarchicType.setSubType(paramHierarchicType);
/* 1034 */     return paramHierarchicType;
/*      */   }
/*      */   
/*      */   protected synchronized HierarchicType _arrayListSuperInterfaceChain(HierarchicType paramHierarchicType)
/*      */   {
/* 1039 */     if (this._cachedArrayListType == null) {
/* 1040 */       localHierarchicType = paramHierarchicType.deepCloneWithoutSubtype();
/* 1041 */       _doFindSuperInterfaceChain(localHierarchicType, List.class);
/* 1042 */       this._cachedArrayListType = localHierarchicType.getSuperType();
/*      */     }
/* 1044 */     HierarchicType localHierarchicType = this._cachedArrayListType.deepCloneWithoutSubtype();
/* 1045 */     paramHierarchicType.setSuperType(localHierarchicType);
/* 1046 */     localHierarchicType.setSubType(paramHierarchicType);
/* 1047 */     return paramHierarchicType;
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/TypeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */