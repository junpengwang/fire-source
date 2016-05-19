/*    */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReadableObjectId
/*    */ {
/*    */   public final Object id;
/*    */   public Object item;
/*    */   
/*    */   public ReadableObjectId(Object paramObject)
/*    */   {
/* 17 */     this.id = paramObject;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void bindItem(Object paramObject)
/*    */     throws IOException
/*    */   {
/* 26 */     if (this.item != null) {
/* 27 */       throw new IllegalStateException("Already had POJO for id (" + this.id.getClass().getName() + ") [" + this.id + "]");
/*    */     }
/* 29 */     this.item = paramObject;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/ReadableObjectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */