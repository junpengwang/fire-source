/*    */ package com.shaded.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.io.SerializedString;
/*    */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*    */ import java.util.Collection;
/*    */ import java.util.EnumMap;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public final class EnumValues
/*    */ {
/*    */   private final Class<Enum<?>> _enumClass;
/*    */   private final EnumMap<?, SerializedString> _values;
/*    */   
/*    */   private EnumValues(Class<Enum<?>> paramClass, Map<Enum<?>, SerializedString> paramMap)
/*    */   {
/* 27 */     this._enumClass = paramClass;
/* 28 */     this._values = new EnumMap(paramMap);
/*    */   }
/*    */   
/*    */   public static EnumValues construct(Class<Enum<?>> paramClass, AnnotationIntrospector paramAnnotationIntrospector)
/*    */   {
/* 33 */     return constructFromName(paramClass, paramAnnotationIntrospector);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static EnumValues constructFromName(Class<Enum<?>> paramClass, AnnotationIntrospector paramAnnotationIntrospector)
/*    */   {
/* 41 */     Class localClass = ClassUtil.findEnumType(paramClass);
/* 42 */     Enum[] arrayOfEnum1 = (Enum[])localClass.getEnumConstants();
/* 43 */     if (arrayOfEnum1 != null)
/*    */     {
/* 45 */       HashMap localHashMap = new HashMap();
/* 46 */       for (Enum localEnum : arrayOfEnum1) {
/* 47 */         String str = paramAnnotationIntrospector.findEnumValue(localEnum);
/* 48 */         localHashMap.put(localEnum, new SerializedString(str));
/*    */       }
/* 50 */       return new EnumValues(paramClass, localHashMap);
/*    */     }
/* 52 */     throw new IllegalArgumentException("Can not determine enum constants for Class " + paramClass.getName());
/*    */   }
/*    */   
/*    */   public static EnumValues constructFromToString(Class<Enum<?>> paramClass, AnnotationIntrospector paramAnnotationIntrospector)
/*    */   {
/* 57 */     Class localClass = ClassUtil.findEnumType(paramClass);
/* 58 */     Enum[] arrayOfEnum1 = (Enum[])localClass.getEnumConstants();
/* 59 */     if (arrayOfEnum1 != null)
/*    */     {
/* 61 */       HashMap localHashMap = new HashMap();
/* 62 */       for (Enum localEnum : arrayOfEnum1) {
/* 63 */         localHashMap.put(localEnum, new SerializedString(localEnum.toString()));
/*    */       }
/* 65 */       return new EnumValues(paramClass, localHashMap);
/*    */     }
/* 67 */     throw new IllegalArgumentException("Can not determine enum constants for Class " + paramClass.getName());
/*    */   }
/*    */   
/*    */   public SerializedString serializedValueFor(Enum<?> paramEnum)
/*    */   {
/* 72 */     return (SerializedString)this._values.get(paramEnum);
/*    */   }
/*    */   
/*    */   public Collection<SerializedString> values() {
/* 76 */     return this._values.values();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EnumMap<?, SerializedString> internalMap()
/*    */   {
/* 86 */     return this._values;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Class<Enum<?>> getEnumClass()
/*    */   {
/* 93 */     return this._enumClass;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/EnumValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */