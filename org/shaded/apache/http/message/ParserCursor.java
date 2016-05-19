/*    */ package org.shaded.apache.http.message;
/*    */ 
/*    */ import org.shaded.apache.http.util.CharArrayBuffer;
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
/*    */ public class ParserCursor
/*    */ {
/*    */   private final int lowerBound;
/*    */   private final int upperBound;
/*    */   private int pos;
/*    */   
/*    */   public ParserCursor(int lowerBound, int upperBound)
/*    */   {
/* 53 */     if (lowerBound < 0) {
/* 54 */       throw new IndexOutOfBoundsException("Lower bound cannot be negative");
/*    */     }
/* 56 */     if (lowerBound > upperBound) {
/* 57 */       throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
/*    */     }
/* 59 */     this.lowerBound = lowerBound;
/* 60 */     this.upperBound = upperBound;
/* 61 */     this.pos = lowerBound;
/*    */   }
/*    */   
/*    */   public int getLowerBound() {
/* 65 */     return this.lowerBound;
/*    */   }
/*    */   
/*    */   public int getUpperBound() {
/* 69 */     return this.upperBound;
/*    */   }
/*    */   
/*    */   public int getPos() {
/* 73 */     return this.pos;
/*    */   }
/*    */   
/*    */   public void updatePos(int pos) {
/* 77 */     if (pos < this.lowerBound) {
/* 78 */       throw new IndexOutOfBoundsException();
/*    */     }
/* 80 */     if (pos > this.upperBound) {
/* 81 */       throw new IndexOutOfBoundsException();
/*    */     }
/* 83 */     this.pos = pos;
/*    */   }
/*    */   
/*    */   public boolean atEnd() {
/* 87 */     return this.pos >= this.upperBound;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 91 */     CharArrayBuffer buffer = new CharArrayBuffer(16);
/* 92 */     buffer.append('[');
/* 93 */     buffer.append(Integer.toString(this.lowerBound));
/* 94 */     buffer.append('>');
/* 95 */     buffer.append(Integer.toString(this.pos));
/* 96 */     buffer.append('>');
/* 97 */     buffer.append(Integer.toString(this.upperBound));
/* 98 */     buffer.append(']');
/* 99 */     return buffer.toString();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/ParserCursor.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */