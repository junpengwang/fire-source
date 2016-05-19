/*    */ package com.firebase.client.utilities;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PushIdGenerator
/*    */ {
/*    */   private static final String PUSH_CHARS = "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
/* 15 */   private static final Random randGen = new Random();
/*    */   
/* 17 */   private static long lastPushTime = 0L;
/*    */   
/* 19 */   private static final int[] lastRandChars = new int[12];
/*    */   
/*    */   public static synchronized String generatePushChildName(long now) {
/* 22 */     boolean duplicateTime = now == lastPushTime;
/* 23 */     lastPushTime = now;
/*    */     
/* 25 */     char[] timeStampChars = new char[8];
/* 26 */     StringBuilder result = new StringBuilder(20);
/* 27 */     for (int i = 7; i >= 0; i--) {
/* 28 */       timeStampChars[i] = "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".charAt((int)(now % 64L));
/* 29 */       now /= 64L;
/*    */     }
/* 31 */     assert (now == 0L);
/*    */     
/* 33 */     result.append(timeStampChars);
/*    */     
/* 35 */     if (!duplicateTime) {
/* 36 */       for (int i = 0; i < 12; i++) {
/* 37 */         lastRandChars[i] = randGen.nextInt(64);
/*    */       }
/*    */       
/*    */     } else {
/* 41 */       incrementArray();
/*    */     }
/* 43 */     for (int i = 0; i < 12; i++) {
/* 44 */       result.append("-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".charAt(lastRandChars[i]));
/*    */     }
/* 46 */     assert (result.length() == 20);
/* 47 */     return result.toString();
/*    */   }
/*    */   
/*    */   private static void incrementArray() {
/* 51 */     for (int i = 11; i >= 0; i--) {
/* 52 */       if (lastRandChars[i] != 63) {
/* 53 */         lastRandChars[i] += 1;
/* 54 */         return;
/*    */       }
/* 56 */       lastRandChars[i] = 0;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/PushIdGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */