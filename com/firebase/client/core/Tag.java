/*    */ package com.firebase.client.core;
/*    */ 
/*    */ public class Tag {
/*    */   private final long tagNumber;
/*    */   
/*    */   public Tag(long tagNumber) {
/*  7 */     this.tagNumber = tagNumber;
/*    */   }
/*    */   
/*    */   public long getTagNumber() {
/* 11 */     return this.tagNumber;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 16 */     return "Tag{tagNumber=" + this.tagNumber + '}';
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 23 */     if (this == o) return true;
/* 24 */     if ((o == null) || (getClass() != o.getClass())) { return false;
/*    */     }
/* 26 */     Tag tag = (Tag)o;
/*    */     
/* 28 */     if (this.tagNumber != tag.tagNumber) { return false;
/*    */     }
/* 30 */     return true;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 35 */     return (int)(this.tagNumber ^ this.tagNumber >>> 32);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/Tag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */