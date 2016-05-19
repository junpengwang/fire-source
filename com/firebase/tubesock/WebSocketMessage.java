/*    */ package com.firebase.tubesock;
/*    */ 
/*    */ public class WebSocketMessage {
/*    */   private byte[] byteMessage;
/*    */   private String stringMessage;
/*    */   private byte opcode;
/*    */   
/*    */   public WebSocketMessage(byte[] message) {
/*  9 */     this.byteMessage = message;
/* 10 */     this.opcode = 2;
/*    */   }
/*    */   
/*    */   public WebSocketMessage(String message) {
/* 14 */     this.stringMessage = message;
/* 15 */     this.opcode = 1;
/*    */   }
/*    */   
/*    */   public boolean isText() {
/* 19 */     return this.opcode == 1;
/*    */   }
/*    */   
/*    */   public boolean isBinary() {
/* 23 */     return this.opcode == 2;
/*    */   }
/*    */   
/*    */   public byte[] getBytes() {
/* 27 */     return this.byteMessage;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 31 */     return this.stringMessage;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/tubesock/WebSocketMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */