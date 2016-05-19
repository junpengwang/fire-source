/*     */ package com.shaded.fasterxml.jackson.annotation;
/*     */ 
/*     */ import java.util.UUID;
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
/*     */ public class ObjectIdGenerators
/*     */ {
/*     */   private static abstract class Base<T>
/*     */     extends ObjectIdGenerator<T>
/*     */   {
/*     */     protected final Class<?> _scope;
/*     */     
/*     */     protected Base(Class<?> paramClass)
/*     */     {
/*  25 */       this._scope = paramClass;
/*     */     }
/*     */     
/*     */     public final Class<?> getScope()
/*     */     {
/*  30 */       return this._scope;
/*     */     }
/*     */     
/*     */     public boolean canUseFor(ObjectIdGenerator<?> paramObjectIdGenerator)
/*     */     {
/*  35 */       return (paramObjectIdGenerator.getClass() == getClass()) && (paramObjectIdGenerator.getScope() == this._scope);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public abstract T generateId(Object paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract class None
/*     */     extends ObjectIdGenerator<Object>
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract class PropertyGenerator
/*     */     extends ObjectIdGenerators.Base<Object>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected PropertyGenerator(Class<?> paramClass)
/*     */     {
/*  68 */       super();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static final class IntSequenceGenerator
/*     */     extends ObjectIdGenerators.Base<Integer>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected transient int _nextValue;
/*     */     
/*  81 */     public IntSequenceGenerator() { this(Object.class, -1); }
/*     */     
/*  83 */     public IntSequenceGenerator(Class<?> paramClass, int paramInt) { super();
/*  84 */       this._nextValue = paramInt;
/*     */     }
/*     */     
/*  87 */     protected int initialValue() { return 1; }
/*     */     
/*     */     public ObjectIdGenerator<Integer> forScope(Class<?> paramClass)
/*     */     {
/*  91 */       return this._scope == paramClass ? this : new IntSequenceGenerator(paramClass, this._nextValue);
/*     */     }
/*     */     
/*     */     public ObjectIdGenerator<Integer> newForSerialization(Object paramObject)
/*     */     {
/*  96 */       return new IntSequenceGenerator(this._scope, initialValue());
/*     */     }
/*     */     
/*     */     public ObjectIdGenerator.IdKey key(Object paramObject)
/*     */     {
/* 101 */       return new ObjectIdGenerator.IdKey(getClass(), this._scope, paramObject);
/*     */     }
/*     */     
/*     */     public Integer generateId(Object paramObject)
/*     */     {
/* 106 */       int i = this._nextValue;
/* 107 */       this._nextValue += 1;
/* 108 */       return Integer.valueOf(i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final class UUIDGenerator
/*     */     extends ObjectIdGenerators.Base<UUID>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 125 */     public UUIDGenerator() { this(Object.class); }
/*     */     
/* 127 */     private UUIDGenerator(Class<?> paramClass) { super(); }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public ObjectIdGenerator<UUID> forScope(Class<?> paramClass)
/*     */     {
/* 135 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public ObjectIdGenerator<UUID> newForSerialization(Object paramObject)
/*     */     {
/* 143 */       return this;
/*     */     }
/*     */     
/*     */     public UUID generateId(Object paramObject)
/*     */     {
/* 148 */       return UUID.randomUUID();
/*     */     }
/*     */     
/*     */     public ObjectIdGenerator.IdKey key(Object paramObject)
/*     */     {
/* 153 */       return new ObjectIdGenerator.IdKey(getClass(), null, paramObject);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean canUseFor(ObjectIdGenerator<?> paramObjectIdGenerator)
/*     */     {
/* 161 */       return paramObjectIdGenerator.getClass() == getClass();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/annotation/ObjectIdGenerators.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */