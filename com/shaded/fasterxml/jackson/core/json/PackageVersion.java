/*    */ package com.shaded.fasterxml.jackson.core.json;
/*    */ 
/*    */ import com.shaded.fasterxml.jackson.core.Version;
/*    */ import com.shaded.fasterxml.jackson.core.Versioned;
/*    */ import com.shaded.fasterxml.jackson.core.util.VersionUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PackageVersion
/*    */   implements Versioned
/*    */ {
/* 13 */   public static final Version VERSION = VersionUtil.parseVersion("2.2.2", "com.shaded.fasterxml.jackson.core", "jackson-core");
/*    */   
/*    */ 
/*    */   public Version version()
/*    */   {
/* 18 */     return VERSION;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/json/PackageVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */