/*    */ package com.shaded.fasterxml.jackson.databind.introspect;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.Version;
/*    */ import com.shaded.fasterxml.jackson.databind.AnnotationIntrospector;
/*    */ import com.shaded.fasterxml.jackson.databind.cfg.PackageVersion;
/*    */ import java.io.Serializable;
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
/*    */ public abstract class NopAnnotationIntrospector
/*    */   extends AnnotationIntrospector
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 23 */   public static final NopAnnotationIntrospector instance = new NopAnnotationIntrospector()
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public Version version() {
/* 28 */       return PackageVersion.VERSION;
/*    */     }
/*    */   };
/*    */   
/*    */   public Version version()
/*    */   {
/* 34 */     return Version.unknownVersion();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/introspect/NopAnnotationIntrospector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */