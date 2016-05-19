/*    */ package com.shaded.fasterxml.jackson.databind.module;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.AbstractTypeResolver;
/*    */ import com.shaded.fasterxml.jackson.databind.DeserializationConfig;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.type.ClassKey;
/*    */ import java.io.Serializable;
/*    */ import java.lang.reflect.Modifier;
/*    */ import java.util.HashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleAbstractTypeResolver
/*    */   extends AbstractTypeResolver
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 8635483102371490919L;
/* 38 */   protected final HashMap<ClassKey, Class<?>> _mappings = new HashMap();
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
/*    */   public <T> SimpleAbstractTypeResolver addMapping(Class<T> paramClass, Class<? extends T> paramClass1)
/*    */   {
/* 55 */     if (paramClass == paramClass1) {
/* 56 */       throw new IllegalArgumentException("Can not add mapping from class to itself");
/*    */     }
/* 58 */     if (!paramClass.isAssignableFrom(paramClass1)) {
/* 59 */       throw new IllegalArgumentException("Can not add mapping from class " + paramClass.getName() + " to " + paramClass1.getName() + ", as latter is not a subtype of former");
/*    */     }
/*    */     
/* 62 */     if (!Modifier.isAbstract(paramClass.getModifiers())) {
/* 63 */       throw new IllegalArgumentException("Can not add mapping from class " + paramClass.getName() + " since it is not abstract");
/*    */     }
/*    */     
/* 66 */     this._mappings.put(new ClassKey(paramClass), paramClass1);
/* 67 */     return this;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public JavaType findTypeMapping(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
/*    */   {
/* 74 */     Class localClass1 = paramJavaType.getRawClass();
/* 75 */     Class localClass2 = (Class)this._mappings.get(new ClassKey(localClass1));
/* 76 */     if (localClass2 == null) {
/* 77 */       return null;
/*    */     }
/* 79 */     return paramJavaType.narrowBy(localClass2);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public JavaType resolveAbstractType(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
/*    */   {
/* 87 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/module/SimpleAbstractTypeResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */