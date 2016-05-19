/*    */ package com.firebase.client.utilities;
/*    */ 
/*    */ import com.firebase.client.Logger;
/*    */ import com.firebase.client.Logger.Level;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Date;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultLogger
/*    */   implements Logger
/*    */ {
/*    */   private final Set<String> enabledComponents;
/*    */   private final Logger.Level minLevel;
/*    */   
/*    */   public DefaultLogger(Logger.Level level, List<String> enabledComponents)
/*    */   {
/* 21 */     if (enabledComponents != null) {
/* 22 */       this.enabledComponents = new HashSet(enabledComponents);
/*    */     } else {
/* 24 */       this.enabledComponents = null;
/*    */     }
/* 26 */     this.minLevel = level;
/*    */   }
/*    */   
/*    */   public Logger.Level getLogLevel()
/*    */   {
/* 31 */     return this.minLevel;
/*    */   }
/*    */   
/*    */   public void onLogMessage(Logger.Level level, String tag, String message, long msTimestamp)
/*    */   {
/* 36 */     if (shouldLog(level, tag)) {
/* 37 */       String toLog = buildLogMessage(level, tag, message, msTimestamp);
/* 38 */       switch (level) {
/*    */       case ERROR: 
/* 40 */         error(tag, toLog);
/* 41 */         break;
/*    */       case WARN: 
/* 43 */         warn(tag, toLog);
/* 44 */         break;
/*    */       case INFO: 
/* 46 */         info(tag, toLog);
/* 47 */         break;
/*    */       case DEBUG: 
/* 49 */         debug(tag, toLog);
/* 50 */         break;
/*    */       default: 
/* 52 */         throw new RuntimeException("Should not reach here!");
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   protected String buildLogMessage(Logger.Level level, String tag, String message, long msTimestamp) {
/* 58 */     Date now = new Date(msTimestamp);
/* 59 */     return now.toString() + " " + "[" + level + "] " + tag + ": " + message;
/*    */   }
/*    */   
/*    */   protected void error(String tag, String toLog) {
/* 63 */     System.err.println(toLog);
/*    */   }
/*    */   
/*    */   protected void warn(String tag, String toLog) {
/* 67 */     System.out.println(toLog);
/*    */   }
/*    */   
/*    */   protected void info(String tag, String toLog) {
/* 71 */     System.out.println(toLog);
/*    */   }
/*    */   
/*    */   protected void debug(String tag, String toLog) {
/* 75 */     System.out.println(toLog);
/*    */   }
/*    */   
/*    */   protected boolean shouldLog(Logger.Level level, String tag) {
/* 79 */     return (level.ordinal() >= this.minLevel.ordinal()) && ((this.enabledComponents == null) || (level.ordinal() > Logger.Level.DEBUG.ordinal()) || (this.enabledComponents.contains(tag)));
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/DefaultLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */