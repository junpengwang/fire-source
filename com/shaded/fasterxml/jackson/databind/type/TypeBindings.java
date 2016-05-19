/*     */ package com.shaded.fasterxml.jackson.databind.type;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class TypeBindings
/*     */ {
/*  13 */   private static final JavaType[] NO_TYPES = new JavaType[0];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  18 */   public static final JavaType UNBOUND = new SimpleType(Object.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final TypeFactory _typeFactory;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final JavaType _contextType;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Class<?> _contextClass;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Map<String, JavaType> _bindings;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected HashSet<String> _placeholders;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final TypeBindings _parentBindings;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TypeBindings(TypeFactory paramTypeFactory, Class<?> paramClass)
/*     */   {
/*  64 */     this(paramTypeFactory, null, paramClass, null);
/*     */   }
/*     */   
/*     */   public TypeBindings(TypeFactory paramTypeFactory, JavaType paramJavaType)
/*     */   {
/*  69 */     this(paramTypeFactory, null, paramJavaType.getRawClass(), paramJavaType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TypeBindings childInstance()
/*     */   {
/*  79 */     return new TypeBindings(this._typeFactory, this, this._contextClass, this._contextType);
/*     */   }
/*     */   
/*     */ 
/*     */   private TypeBindings(TypeFactory paramTypeFactory, TypeBindings paramTypeBindings, Class<?> paramClass, JavaType paramJavaType)
/*     */   {
/*  85 */     this._typeFactory = paramTypeFactory;
/*  86 */     this._parentBindings = paramTypeBindings;
/*  87 */     this._contextClass = paramClass;
/*  88 */     this._contextType = paramJavaType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaType resolveType(Class<?> paramClass)
/*     */   {
/*  98 */     return this._typeFactory._constructType(paramClass, this);
/*     */   }
/*     */   
/*     */   public JavaType resolveType(Type paramType) {
/* 102 */     return this._typeFactory._constructType(paramType, this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBindingCount()
/*     */   {
/* 112 */     if (this._bindings == null) {
/* 113 */       _resolve();
/*     */     }
/* 115 */     return this._bindings.size();
/*     */   }
/*     */   
/*     */   public JavaType findType(String paramString)
/*     */   {
/* 120 */     if (this._bindings == null) {
/* 121 */       _resolve();
/*     */     }
/* 123 */     JavaType localJavaType = (JavaType)this._bindings.get(paramString);
/* 124 */     if (localJavaType != null) {
/* 125 */       return localJavaType;
/*     */     }
/* 127 */     if ((this._placeholders != null) && (this._placeholders.contains(paramString))) {
/* 128 */       return UNBOUND;
/*     */     }
/* 130 */     if (this._parentBindings != null) {
/* 131 */       return this._parentBindings.findType(paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     Object localObject;
/*     */     
/*     */ 
/*     */ 
/* 140 */     if (this._contextClass != null) {
/* 141 */       localObject = this._contextClass.getEnclosingClass();
/* 142 */       if (localObject != null)
/*     */       {
/*     */ 
/* 145 */         if (!java.lang.reflect.Modifier.isStatic(this._contextClass.getModifiers())) {
/* 146 */           return UNBOUND;
/*     */         }
/*     */       }
/*     */     }
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
/* 163 */     if (this._contextClass != null) {
/* 164 */       localObject = this._contextClass.getName();
/* 165 */     } else if (this._contextType != null) {
/* 166 */       localObject = this._contextType.toString();
/*     */     } else {
/* 168 */       localObject = "UNKNOWN";
/*     */     }
/* 170 */     throw new IllegalArgumentException("Type variable '" + paramString + "' can not be resolved (with context of class " + (String)localObject + ")");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addBinding(String paramString, JavaType paramJavaType)
/*     */   {
/* 178 */     if ((this._bindings == null) || (this._bindings.size() == 0)) {
/* 179 */       this._bindings = new LinkedHashMap();
/*     */     }
/* 181 */     this._bindings.put(paramString, paramJavaType);
/*     */   }
/*     */   
/*     */   public JavaType[] typesAsArray()
/*     */   {
/* 186 */     if (this._bindings == null) {
/* 187 */       _resolve();
/*     */     }
/* 189 */     if (this._bindings.size() == 0) {
/* 190 */       return NO_TYPES;
/*     */     }
/* 192 */     return (JavaType[])this._bindings.values().toArray(new JavaType[this._bindings.size()]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _resolve()
/*     */   {
/* 203 */     _resolveBindings(this._contextClass);
/*     */     
/*     */ 
/* 206 */     if (this._contextType != null) {
/* 207 */       int i = this._contextType.containedTypeCount();
/* 208 */       if (i > 0) {
/* 209 */         for (int j = 0; j < i; j++) {
/* 210 */           String str = this._contextType.containedTypeName(j);
/* 211 */           JavaType localJavaType = this._contextType.containedType(j);
/* 212 */           addBinding(str, localJavaType);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 218 */     if (this._bindings == null) {
/* 219 */       this._bindings = java.util.Collections.emptyMap();
/*     */     }
/*     */   }
/*     */   
/*     */   public void _addPlaceholder(String paramString) {
/* 224 */     if (this._placeholders == null) {
/* 225 */       this._placeholders = new HashSet();
/*     */     }
/* 227 */     this._placeholders.add(paramString);
/*     */   }
/*     */   
/*     */   protected void _resolveBindings(Type paramType)
/*     */   {
/* 232 */     if (paramType == null) return;
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 235 */     Object localObject3; Object localObject5; Class localClass; if ((paramType instanceof ParameterizedType)) {
/* 236 */       localObject1 = (ParameterizedType)paramType;
/* 237 */       localObject2 = ((ParameterizedType)localObject1).getActualTypeArguments();
/* 238 */       if ((localObject2 != null) && (localObject2.length > 0)) {
/* 239 */         localObject3 = (Class)((ParameterizedType)localObject1).getRawType();
/* 240 */         TypeVariable[] arrayOfTypeVariable = ((Class)localObject3).getTypeParameters();
/* 241 */         if (arrayOfTypeVariable.length != localObject2.length) {
/* 242 */           throw new IllegalArgumentException("Strange parametrized type (in class " + ((Class)localObject3).getName() + "): number of type arguments != number of type parameters (" + localObject2.length + " vs " + arrayOfTypeVariable.length + ")");
/*     */         }
/* 244 */         int m = 0; for (int n = localObject2.length; m < n; m++) {
/* 245 */           localObject5 = arrayOfTypeVariable[m];
/* 246 */           String str2 = ((TypeVariable)localObject5).getName();
/* 247 */           if (this._bindings == null) {
/* 248 */             this._bindings = new LinkedHashMap();
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 253 */             if (this._bindings.containsKey(str2))
/*     */               continue;
/*     */           }
/* 256 */           _addPlaceholder(str2);
/*     */           
/* 258 */           this._bindings.put(str2, this._typeFactory._constructType(localObject2[m], this));
/*     */         }
/*     */       }
/* 261 */       localClass = (Class)((ParameterizedType)localObject1).getRawType();
/* 262 */     } else if ((paramType instanceof Class)) {
/* 263 */       localClass = (Class)paramType;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 269 */       localObject1 = localClass.getDeclaringClass();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 274 */       if ((localObject1 != null) && (!((Class)localObject1).isAssignableFrom(localClass))) {
/* 275 */         _resolveBindings(localClass.getDeclaringClass());
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 281 */       localObject2 = localClass.getTypeParameters();
/* 282 */       if ((localObject2 != null) && (localObject2.length > 0)) {
/* 283 */         localObject3 = null;
/*     */         
/* 285 */         if ((this._contextType != null) && (localClass.isAssignableFrom(this._contextType.getRawClass()))) {
/* 286 */           localObject3 = this._typeFactory.findTypeParameters(this._contextType, localClass);
/*     */         }
/*     */         
/* 289 */         for (int k = 0; k < localObject2.length; k++) {
/* 290 */           Object localObject4 = localObject2[k];
/*     */           
/* 292 */           String str1 = ((TypeVariable)localObject4).getName();
/* 293 */           localObject5 = localObject4.getBounds()[0];
/* 294 */           if (localObject5 != null) {
/* 295 */             if (this._bindings == null) {
/* 296 */               this._bindings = new LinkedHashMap();
/*     */             } else
/* 298 */               if (this._bindings.containsKey(str1))
/*     */                 continue;
/* 300 */             _addPlaceholder(str1);
/*     */             
/* 302 */             if (localObject3 != null) {
/* 303 */               this._bindings.put(str1, localObject3[k]);
/*     */             } else {
/* 305 */               this._bindings.put(str1, this._typeFactory._constructType((Type)localObject5, this));
/*     */             }
/*     */             
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 314 */       return;
/*     */     }
/*     */     
/* 317 */     _resolveBindings(localClass.getGenericSuperclass());
/* 318 */     for (Type localType : localClass.getGenericInterfaces()) {
/* 319 */       _resolveBindings(localType);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 326 */     if (this._bindings == null) {
/* 327 */       _resolve();
/*     */     }
/* 329 */     StringBuilder localStringBuilder = new StringBuilder("[TypeBindings for ");
/* 330 */     if (this._contextType != null) {
/* 331 */       localStringBuilder.append(this._contextType.toString());
/*     */     } else {
/* 333 */       localStringBuilder.append(this._contextClass.getName());
/*     */     }
/* 335 */     localStringBuilder.append(": ").append(this._bindings).append("]");
/* 336 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/type/TypeBindings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */