/*    */ package com.firebase.client.android;
/*    */ 
/*    */ import android.util.Log;
/*    */ import com.firebase.client.Logger.Level;
/*    */ import com.firebase.client.utilities.DefaultLogger;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AndroidLogger
/*    */   extends DefaultLogger
/*    */ {
/*    */   public AndroidLogger(Logger.Level level, List<String> enabledComponents)
/*    */   {
/* 16 */     super(level, enabledComponents);
/*    */   }
/*    */   
/*    */ 
/*    */   protected String buildLogMessage(Logger.Level level, String tag, String message, long msTimestamp)
/*    */   {
/* 22 */     return message;
/*    */   }
/*    */   
/*    */   protected void error(String tag, String toLog)
/*    */   {
/* 27 */     Log.e(tag, toLog);
/*    */   }
/*    */   
/*    */   protected void warn(String tag, String toLog)
/*    */   {
/* 32 */     Log.w(tag, toLog);
/*    */   }
/*    */   
/*    */   protected void info(String tag, String toLog)
/*    */   {
/* 37 */     Log.i(tag, toLog);
/*    */   }
/*    */   
/*    */   protected void debug(String tag, String toLog)
/*    */   {
/* 42 */     Log.d(tag, toLog);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/android/AndroidLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */