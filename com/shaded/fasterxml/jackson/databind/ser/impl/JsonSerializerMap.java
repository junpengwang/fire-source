/*    */ package com.shaded.fasterxml.jackson.databind.ser.impl;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.shaded.fasterxml.jackson.databind.ser.SerializerCache.TypeKey;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JsonSerializerMap
/*    */ {
/*    */   private final Bucket[] _buckets;
/*    */   private final int _size;
/*    */   
/*    */   public JsonSerializerMap(Map<SerializerCache.TypeKey, JsonSerializer<Object>> paramMap)
/*    */   {
/* 19 */     int i = findSize(paramMap.size());
/* 20 */     this._size = i;
/* 21 */     int j = i - 1;
/* 22 */     Bucket[] arrayOfBucket = new Bucket[i];
/* 23 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 24 */       SerializerCache.TypeKey localTypeKey = (SerializerCache.TypeKey)localEntry.getKey();
/* 25 */       int k = localTypeKey.hashCode() & j;
/* 26 */       arrayOfBucket[k] = new Bucket(arrayOfBucket[k], localTypeKey, (JsonSerializer)localEntry.getValue());
/*    */     }
/* 28 */     this._buckets = arrayOfBucket;
/*    */   }
/*    */   
/*    */ 
/*    */   private static final int findSize(int paramInt)
/*    */   {
/* 34 */     int i = paramInt <= 64 ? paramInt + paramInt : paramInt + (paramInt >> 2);
/* 35 */     int j = 8;
/* 36 */     while (j < i) {
/* 37 */       j += j;
/*    */     }
/* 39 */     return j;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int size()
/*    */   {
/* 48 */     return this._size;
/*    */   }
/*    */   
/*    */   public JsonSerializer<Object> find(SerializerCache.TypeKey paramTypeKey) {
/* 52 */     int i = paramTypeKey.hashCode() & this._buckets.length - 1;
/* 53 */     Bucket localBucket = this._buckets[i];
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 58 */     if (localBucket == null) {
/* 59 */       return null;
/*    */     }
/* 61 */     if (paramTypeKey.equals(localBucket.key)) {
/* 62 */       return localBucket.value;
/*    */     }
/* 64 */     while ((localBucket = localBucket.next) != null) {
/* 65 */       if (paramTypeKey.equals(localBucket.key)) {
/* 66 */         return localBucket.value;
/*    */       }
/*    */     }
/* 69 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private static final class Bucket
/*    */   {
/*    */     public final SerializerCache.TypeKey key;
/*    */     
/*    */ 
/*    */     public final JsonSerializer<Object> value;
/*    */     
/*    */     public final Bucket next;
/*    */     
/*    */ 
/*    */     public Bucket(Bucket paramBucket, SerializerCache.TypeKey paramTypeKey, JsonSerializer<Object> paramJsonSerializer)
/*    */     {
/* 86 */       this.next = paramBucket;
/* 87 */       this.key = paramTypeKey;
/* 88 */       this.value = paramJsonSerializer;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/ser/impl/JsonSerializerMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */