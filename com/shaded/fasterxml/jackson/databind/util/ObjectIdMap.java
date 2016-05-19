/*    */ package com.shaded.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import java.util.IdentityHashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectIdMap
/*    */   extends IdentityHashMap<Object, Object>
/*    */ {
/*    */   public ObjectIdMap()
/*    */   {
/* 14 */     super(16);
/*    */   }
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
/*    */   public Object findId(Object paramObject)
/*    */   {
/* 31 */     return get(paramObject);
/*    */   }
/*    */   
/*    */   public void insertId(Object paramObject1, Object paramObject2)
/*    */   {
/* 36 */     put(paramObject1, paramObject2);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/ObjectIdMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */