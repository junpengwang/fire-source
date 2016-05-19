/*    */ package com.shaded.fasterxml.jackson.core.util;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map.Entry;
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
/*    */ public final class InternCache
/*    */   extends LinkedHashMap<String, String>
/*    */ {
/*    */   private static final int MAX_ENTRIES = 100;
/* 29 */   public static final InternCache instance = new InternCache();
/*    */   
/*    */   private InternCache() {
/* 32 */     super(100, 0.8F, true);
/*    */   }
/*    */   
/*    */ 
/*    */   protected boolean removeEldestEntry(Map.Entry<String, String> paramEntry)
/*    */   {
/* 38 */     return size() > 100;
/*    */   }
/*    */   
/*    */   public synchronized String intern(String paramString)
/*    */   {
/* 43 */     String str = (String)get(paramString);
/* 44 */     if (str == null) {
/* 45 */       str = paramString.intern();
/* 46 */       put(str, str);
/*    */     }
/* 48 */     return str;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/util/InternCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */