/*    */ package com.shaded.fasterxml.jackson.databind.introspect;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AnnotatedMethodMap
/*    */   implements Iterable<AnnotatedMethod>
/*    */ {
/*    */   protected LinkedHashMap<MemberKey, AnnotatedMethod> _methods;
/*    */   
/*    */   public void add(AnnotatedMethod paramAnnotatedMethod)
/*    */   {
/* 23 */     if (this._methods == null) {
/* 24 */       this._methods = new LinkedHashMap();
/*    */     }
/* 26 */     this._methods.put(new MemberKey(paramAnnotatedMethod.getAnnotated()), paramAnnotatedMethod);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public AnnotatedMethod remove(AnnotatedMethod paramAnnotatedMethod)
/*    */   {
/* 35 */     return remove(paramAnnotatedMethod.getAnnotated());
/*    */   }
/*    */   
/*    */   public AnnotatedMethod remove(Method paramMethod)
/*    */   {
/* 40 */     if (this._methods != null) {
/* 41 */       return (AnnotatedMethod)this._methods.remove(new MemberKey(paramMethod));
/*    */     }
/* 43 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 47 */     return (this._methods == null) || (this._methods.size() == 0);
/*    */   }
/*    */   
/*    */   public int size() {
/* 51 */     return this._methods == null ? 0 : this._methods.size();
/*    */   }
/*    */   
/*    */   public AnnotatedMethod find(String paramString, Class<?>[] paramArrayOfClass)
/*    */   {
/* 56 */     if (this._methods == null) {
/* 57 */       return null;
/*    */     }
/* 59 */     return (AnnotatedMethod)this._methods.get(new MemberKey(paramString, paramArrayOfClass));
/*    */   }
/*    */   
/*    */   public AnnotatedMethod find(Method paramMethod)
/*    */   {
/* 64 */     if (this._methods == null) {
/* 65 */       return null;
/*    */     }
/* 67 */     return (AnnotatedMethod)this._methods.get(new MemberKey(paramMethod));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Iterator<AnnotatedMethod> iterator()
/*    */   {
/* 79 */     if (this._methods != null) {
/* 80 */       return this._methods.values().iterator();
/*    */     }
/* 82 */     List localList = Collections.emptyList();
/* 83 */     return localList.iterator();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/AnnotatedMethodMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */