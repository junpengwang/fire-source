/*    */ package com.shaded.fasterxml.jackson.databind;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public abstract class InjectableValues
/*    */ {
/*    */   public abstract Object findInjectableValue(Object paramObject1, DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty, Object paramObject2);
/*    */   
/*    */   public static class Std
/*    */     extends InjectableValues
/*    */     implements Serializable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     protected final Map<String, Object> _values;
/*    */     
/*    */     public Std()
/*    */     {
/* 48 */       this(new HashMap());
/*    */     }
/*    */     
/*    */     public Std(Map<String, Object> paramMap) {
/* 52 */       this._values = paramMap;
/*    */     }
/*    */     
/*    */     public Std addValue(String paramString, Object paramObject)
/*    */     {
/* 57 */       this._values.put(paramString, paramObject);
/* 58 */       return this;
/*    */     }
/*    */     
/*    */     public Std addValue(Class<?> paramClass, Object paramObject)
/*    */     {
/* 63 */       this._values.put(paramClass.getName(), paramObject);
/* 64 */       return this;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */     public Object findInjectableValue(Object paramObject1, DeserializationContext paramDeserializationContext, BeanProperty paramBeanProperty, Object paramObject2)
/*    */     {
/* 72 */       if (!(paramObject1 instanceof String)) {
/* 73 */         str = paramObject1 == null ? "[null]" : paramObject1.getClass().getName();
/* 74 */         throw new IllegalArgumentException("Unrecognized inject value id type (" + str + "), expecting String");
/*    */       }
/* 76 */       String str = (String)paramObject1;
/* 77 */       Object localObject = this._values.get(str);
/* 78 */       if ((localObject == null) && (!this._values.containsKey(str))) {
/* 79 */         throw new IllegalArgumentException("No injectable id with value '" + str + "' found (for property '" + paramBeanProperty.getName() + "')");
/*    */       }
/*    */       
/* 82 */       return localObject;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/InjectableValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */