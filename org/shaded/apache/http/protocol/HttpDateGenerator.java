/*    */ package org.shaded.apache.http.protocol;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ import java.util.TimeZone;
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
/*    */ public class HttpDateGenerator
/*    */ {
/*    */   public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
/* 56 */   public static final TimeZone GMT = TimeZone.getTimeZone("GMT");
/*    */   
/*    */ 
/*    */   private final DateFormat dateformat;
/*    */   
/* 61 */   private long dateAsLong = 0L;
/* 62 */   private String dateAsText = null;
/*    */   
/*    */   public HttpDateGenerator()
/*    */   {
/* 66 */     this.dateformat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
/* 67 */     this.dateformat.setTimeZone(GMT);
/*    */   }
/*    */   
/*    */   public synchronized String getCurrentDate() {
/* 71 */     long now = System.currentTimeMillis();
/* 72 */     if (now - this.dateAsLong > 1000L)
/*    */     {
/* 74 */       this.dateAsText = this.dateformat.format(new Date(now));
/* 75 */       this.dateAsLong = now;
/*    */     }
/* 77 */     return this.dateAsText;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/protocol/HttpDateGenerator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */