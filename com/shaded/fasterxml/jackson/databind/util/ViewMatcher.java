/*    */ package com.shaded.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ViewMatcher
/*    */ {
/*    */   public abstract boolean isVisibleForView(Class<?> paramClass);
/*    */   
/*    */   public static ViewMatcher construct(Class<?>[] paramArrayOfClass)
/*    */   {
/* 13 */     if (paramArrayOfClass == null) {
/* 14 */       return Empty.instance;
/*    */     }
/* 16 */     switch (paramArrayOfClass.length) {
/*    */     case 0: 
/* 18 */       return Empty.instance;
/*    */     case 1: 
/* 20 */       return new Single(paramArrayOfClass[0]);
/*    */     }
/* 22 */     return new Multi(paramArrayOfClass);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private static final class Empty
/*    */     extends ViewMatcher
/*    */     implements Serializable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */ 
/*    */ 
/* 36 */     static final Empty instance = new Empty();
/*    */     
/*    */     public boolean isVisibleForView(Class<?> paramClass) {
/* 39 */       return false;
/*    */     }
/*    */   }
/*    */   
/*    */   private static final class Single extends ViewMatcher implements Serializable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     private final Class<?> _view;
/*    */     
/*    */     public Single(Class<?> paramClass) {
/* 49 */       this._view = paramClass;
/*    */     }
/*    */     
/* 52 */     public boolean isVisibleForView(Class<?> paramClass) { return (paramClass == this._view) || (this._view.isAssignableFrom(paramClass)); }
/*    */   }
/*    */   
/*    */   private static final class Multi
/*    */     extends ViewMatcher implements Serializable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     private final Class<?>[] _views;
/*    */     
/*    */     public Multi(Class<?>[] paramArrayOfClass)
/*    */     {
/* 63 */       this._views = paramArrayOfClass;
/*    */     }
/*    */     
/*    */     public boolean isVisibleForView(Class<?> paramClass)
/*    */     {
/* 68 */       int i = 0; for (int j = this._views.length; i < j; i++) {
/* 69 */         Class localClass = this._views[i];
/* 70 */         if ((paramClass == localClass) || (localClass.isAssignableFrom(paramClass))) {
/* 71 */           return true;
/*    */         }
/*    */       }
/* 74 */       return false;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/ViewMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */