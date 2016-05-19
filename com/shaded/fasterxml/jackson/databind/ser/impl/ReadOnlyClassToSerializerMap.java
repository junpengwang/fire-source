/*    */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.JavaType;
/*    */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.SerializerCache.TypeKey;
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
/*    */ public final class ReadOnlyClassToSerializerMap
/*    */ {
/*    */   protected final JsonSerializerMap _map;
/* 27 */   protected SerializerCache.TypeKey _cacheKey = null;
/*    */   
/*    */   private ReadOnlyClassToSerializerMap(JsonSerializerMap paramJsonSerializerMap)
/*    */   {
/* 31 */     this._map = paramJsonSerializerMap;
/*    */   }
/*    */   
/*    */   public ReadOnlyClassToSerializerMap instance()
/*    */   {
/* 36 */     return new ReadOnlyClassToSerializerMap(this._map);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ReadOnlyClassToSerializerMap from(HashMap<SerializerCache.TypeKey, JsonSerializer<Object>> paramHashMap)
/*    */   {
/* 46 */     return new ReadOnlyClassToSerializerMap(new JsonSerializerMap(paramHashMap));
/*    */   }
/*    */   
/*    */   public JsonSerializer<Object> typedValueSerializer(JavaType paramJavaType)
/*    */   {
/* 51 */     if (this._cacheKey == null) {
/* 52 */       this._cacheKey = new SerializerCache.TypeKey(paramJavaType, true);
/*    */     } else {
/* 54 */       this._cacheKey.resetTyped(paramJavaType);
/*    */     }
/* 56 */     return this._map.find(this._cacheKey);
/*    */   }
/*    */   
/*    */   public JsonSerializer<Object> typedValueSerializer(Class<?> paramClass)
/*    */   {
/* 61 */     if (this._cacheKey == null) {
/* 62 */       this._cacheKey = new SerializerCache.TypeKey(paramClass, true);
/*    */     } else {
/* 64 */       this._cacheKey.resetTyped(paramClass);
/*    */     }
/* 66 */     return this._map.find(this._cacheKey);
/*    */   }
/*    */   
/*    */   public JsonSerializer<Object> untypedValueSerializer(JavaType paramJavaType)
/*    */   {
/* 71 */     if (this._cacheKey == null) {
/* 72 */       this._cacheKey = new SerializerCache.TypeKey(paramJavaType, false);
/*    */     } else {
/* 74 */       this._cacheKey.resetUntyped(paramJavaType);
/*    */     }
/* 76 */     return this._map.find(this._cacheKey);
/*    */   }
/*    */   
/*    */   public JsonSerializer<Object> untypedValueSerializer(Class<?> paramClass)
/*    */   {
/* 81 */     if (this._cacheKey == null) {
/* 82 */       this._cacheKey = new SerializerCache.TypeKey(paramClass, false);
/*    */     } else {
/* 84 */       this._cacheKey.resetUntyped(paramClass);
/*    */     }
/* 86 */     return this._map.find(this._cacheKey);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/ReadOnlyClassToSerializerMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */