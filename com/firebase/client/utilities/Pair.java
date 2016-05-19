/*    */ package com.firebase.client.utilities;
/*    */ 
/*    */ public class Pair<T, U>
/*    */ {
/*    */   private final T first;
/*    */   private final U second;
/*    */   
/*    */   public Pair(T first, U second) {
/*  9 */     this.first = first;
/* 10 */     this.second = second;
/*    */   }
/*    */   
/*    */   public T getFirst() {
/* 14 */     return (T)this.first;
/*    */   }
/*    */   
/*    */   public U getSecond() {
/* 18 */     return (U)this.second;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o)
/*    */   {
/* 23 */     if (this == o) return true;
/* 24 */     if ((o == null) || (getClass() != o.getClass())) { return false;
/*    */     }
/* 26 */     Pair pair = (Pair)o;
/*    */     
/* 28 */     if (this.first != null ? !this.first.equals(pair.first) : pair.first != null) return false;
/* 29 */     if (this.second != null ? !this.second.equals(pair.second) : pair.second != null) { return false;
/*    */     }
/* 31 */     return true;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 36 */     int result = this.first != null ? this.first.hashCode() : 0;
/* 37 */     result = 31 * result + (this.second != null ? this.second.hashCode() : 0);
/* 38 */     return result;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 43 */     return "Pair(" + this.first + "," + this.second + ")";
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/Pair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */