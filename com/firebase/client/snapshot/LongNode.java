/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ import com.firebase.client.utilities.Utilities;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LongNode
/*    */   extends LeafNode<LongNode>
/*    */ {
/*    */   private final long value;
/*    */   
/*    */   public LongNode(Long value, Node priority)
/*    */   {
/* 15 */     super(priority);
/* 16 */     this.value = value.longValue();
/*    */   }
/*    */   
/*    */   public Object getValue()
/*    */   {
/* 21 */     return Long.valueOf(this.value);
/*    */   }
/*    */   
/*    */   public String getHashString()
/*    */   {
/* 26 */     String toHash = getPriorityHash();
/* 27 */     toHash = toHash + "number:";
/* 28 */     toHash = toHash + Utilities.doubleToHashString(this.value);
/* 29 */     return toHash;
/*    */   }
/*    */   
/*    */   public LongNode updatePriority(Node priority)
/*    */   {
/* 34 */     return new LongNode(Long.valueOf(this.value), priority);
/*    */   }
/*    */   
/*    */ 
/*    */   protected LeafNode.LeafType getLeafType()
/*    */   {
/* 40 */     return LeafNode.LeafType.Number;
/*    */   }
/*    */   
/*    */   protected int compareLeafValues(LongNode other)
/*    */   {
/* 45 */     return Utilities.compareLongs(this.value, other.value);
/*    */   }
/*    */   
/*    */   public boolean equals(Object other)
/*    */   {
/* 50 */     if (!(other instanceof LongNode)) {
/* 51 */       return false;
/*    */     }
/* 53 */     LongNode otherLongNode = (LongNode)other;
/* 54 */     return (this.value == otherLongNode.value) && (this.priority.equals(otherLongNode.priority));
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 59 */     return (int)(this.value ^ this.value >>> 32) + this.priority.hashCode();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/LongNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */