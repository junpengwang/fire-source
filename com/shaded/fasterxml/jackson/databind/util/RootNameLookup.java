/*    */ package com.shaded.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.io.SerializedString;
/*    */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*    */ import com.shaded.fasterxml.jackson.databind.BeanDescription;
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.PropertyName;
/*    */ import com.shaded.fasterxml.jackson.databind.cfg.MapperConfig;
/*    */ import com.shaded.fasterxml.jackson.databind.introspect.AnnotatedClass;
/*    */ import com.shaded.fasterxml.jackson.databind.type.ClassKey;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RootNameLookup
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected LRUMap<ClassKey, SerializedString> _rootNames;
/*    */   
/*    */   public SerializedString findRootName(JavaType paramJavaType, MapperConfig<?> paramMapperConfig)
/*    */   {
/* 28 */     return findRootName(paramJavaType.getRawClass(), paramMapperConfig);
/*    */   }
/*    */   
/*    */   public synchronized SerializedString findRootName(Class<?> paramClass, MapperConfig<?> paramMapperConfig)
/*    */   {
/* 33 */     ClassKey localClassKey = new ClassKey(paramClass);
/*    */     
/* 35 */     if (this._rootNames == null) {
/* 36 */       this._rootNames = new LRUMap(20, 200);
/*    */     } else {
/* 38 */       localObject = (SerializedString)this._rootNames.get(localClassKey);
/* 39 */       if (localObject != null) {
/* 40 */         return (SerializedString)localObject;
/*    */       }
/*    */     }
/* 43 */     Object localObject = paramMapperConfig.introspectClassAnnotations(paramClass);
/* 44 */     AnnotationIntrospector localAnnotationIntrospector = paramMapperConfig.getAnnotationIntrospector();
/* 45 */     AnnotatedClass localAnnotatedClass = ((BeanDescription)localObject).getClassInfo();
/* 46 */     PropertyName localPropertyName = localAnnotationIntrospector.findRootName(localAnnotatedClass);
/*    */     
/*    */     String str;
/* 49 */     if ((localPropertyName == null) || (!localPropertyName.hasSimpleName()))
/*    */     {
/* 51 */       str = paramClass.getSimpleName();
/*    */     } else {
/* 53 */       str = localPropertyName.getSimpleName();
/*    */     }
/* 55 */     SerializedString localSerializedString = new SerializedString(str);
/* 56 */     this._rootNames.put(localClassKey, localSerializedString);
/* 57 */     return localSerializedString;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/RootNameLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */