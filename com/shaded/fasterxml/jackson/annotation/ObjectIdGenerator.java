/*     */ package com.shaded.fasterxml.jackson.annotation;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ObjectIdGenerator<T>
/*     */   implements Serializable
/*     */ {
/*     */   public abstract Class<?> getScope();
/*     */   
/*     */   public abstract boolean canUseFor(ObjectIdGenerator<?> paramObjectIdGenerator);
/*     */   
/*     */   public abstract ObjectIdGenerator<T> forScope(Class<?> paramClass);
/*     */   
/*     */   public abstract ObjectIdGenerator<T> newForSerialization(Object paramObject);
/*     */   
/*     */   public abstract IdKey key(Object paramObject);
/*     */   
/*     */   public abstract T generateId(Object paramObject);
/*     */   
/*     */   public static final class IdKey
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final Class<?> type;
/*     */     private final Class<?> scope;
/*     */     private final Object key;
/*     */     private final int hashCode;
/*     */     
/*     */     public IdKey(Class<?> paramClass1, Class<?> paramClass2, Object paramObject)
/*     */     {
/* 120 */       this.type = paramClass1;
/* 121 */       this.scope = paramClass2;
/* 122 */       this.key = paramObject;
/*     */       
/* 124 */       int i = paramObject.hashCode() + paramClass1.getName().hashCode();
/* 125 */       if (paramClass2 != null) {
/* 126 */         i ^= paramClass2.getName().hashCode();
/*     */       }
/* 128 */       this.hashCode = i;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 132 */       return this.hashCode;
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 137 */       if (paramObject == this) return true;
/* 138 */       if (paramObject == null) return false;
/* 139 */       if (paramObject.getClass() != getClass()) return false;
/* 140 */       IdKey localIdKey = (IdKey)paramObject;
/* 141 */       return (localIdKey.key.equals(this.key)) && (localIdKey.type == this.type) && (localIdKey.scope == this.scope);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/annotation/ObjectIdGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */