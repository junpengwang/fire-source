/*    */ package com.firebase.client.utilities;
/*    */ 
/*    */ import com.firebase.client.Logger;
/*    */ import com.firebase.client.Logger.Level;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ 
/*    */ public class LogWrapper
/*    */ {
/*    */   private final Logger logger;
/*    */   private final String component;
/*    */   private final String prefix;
/*    */   
/*    */   private static String exceptionStacktrace(Throwable e)
/*    */   {
/* 16 */     StringWriter writer = new StringWriter();
/* 17 */     PrintWriter printWriter = new PrintWriter(writer);
/* 18 */     e.printStackTrace(printWriter);
/* 19 */     return writer.toString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public LogWrapper(Logger logger, String component)
/*    */   {
/* 27 */     this(logger, component, null);
/*    */   }
/*    */   
/*    */   public LogWrapper(Logger logger, String component, String prefix) {
/* 31 */     this.logger = logger;
/* 32 */     this.component = component;
/* 33 */     this.prefix = prefix;
/*    */   }
/*    */   
/*    */   public void error(String message, Throwable e) {
/* 37 */     String logMsg = toLog(message) + "\n" + exceptionStacktrace(e);
/* 38 */     this.logger.onLogMessage(Logger.Level.ERROR, this.component, logMsg, now());
/*    */   }
/*    */   
/*    */   public void warn(String message) {
/* 42 */     warn(message, null);
/*    */   }
/*    */   
/*    */   public void warn(String message, Throwable e) {
/* 46 */     String logMsg = toLog(message);
/* 47 */     if (e != null) {
/* 48 */       logMsg = logMsg + "\n" + exceptionStacktrace(e);
/*    */     }
/* 50 */     this.logger.onLogMessage(Logger.Level.WARN, this.component, logMsg, now());
/*    */   }
/*    */   
/*    */   public void info(String message) {
/* 54 */     this.logger.onLogMessage(Logger.Level.INFO, this.component, toLog(message), now());
/*    */   }
/*    */   
/*    */   public void debug(String message) {
/* 58 */     debug(message, null);
/*    */   }
/*    */   
/*    */   public boolean logsDebug() {
/* 62 */     return this.logger.getLogLevel().ordinal() <= Logger.Level.DEBUG.ordinal();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void debug(String message, Throwable e)
/*    */   {
/* 69 */     String logMsg = toLog(message);
/* 70 */     if (e != null) {
/* 71 */       logMsg = logMsg + "\n" + exceptionStacktrace(e);
/*    */     }
/* 73 */     assert (logsDebug());
/* 74 */     this.logger.onLogMessage(Logger.Level.DEBUG, this.component, logMsg, now());
/*    */   }
/*    */   
/*    */   private long now() {
/* 78 */     return System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   private String toLog(String message) {
/* 82 */     return this.prefix + " - " + message;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/LogWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */