/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ import com.firebase.client.utilities.Utilities;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DoubleNode
/*    */   extends LeafNode<DoubleNode>
/*    */ {
/*    */   private final Double value;
/*    */   
/*    */   public DoubleNode(Double value, Node priority)
/*    */   {
/* 15 */     super(priority);
/* 16 */     this.value = value;
/*    */   }
/*    */   
/*    */   public Object getValue()
/*    */   {
/* 21 */     return this.value;
/*    */   }
/*    */   
/*    */   public String getHashString()
/*    */   {
/* 26 */     String toHash = getPriorityHash();
/* 27 */     toHash = toHash + "number:";
/* 28 */     toHash = toHash + Utilities.doubleToHashString(this.value.doubleValue());
/* 29 */     return toHash;
/*    */   }
/*    */   
/*    */   public DoubleNode updatePriority(Node priority)
/*    */   {
/* 34 */     assert (PriorityUtilities.isValidPriority(priority));
/* 35 */     return new DoubleNode(this.value, priority);
/*    */   }
/*    */   
/*    */   protected LeafNode.LeafType getLeafType()
/*    */   {
/* 40 */     return LeafNode.LeafType.Number;
/*    */   }
/*    */   
/*    */ 
/*    */   protected int compareLeafValues(DoubleNode other)
/*    */   {
/* 46 */     return this.value.compareTo(other.value);
/*    */   }
/*    */   
/*    */   public boolean equals(Object other)
/*    */   {
/* 51 */     if (!(other instanceof DoubleNode)) {
/* 52 */       return false;
/*    */     }
/* 54 */     DoubleNode otherDoubleNode = (DoubleNode)other;
/* 55 */     return (this.value.equals(otherDoubleNode.value)) && (this.priority.equals(otherDoubleNode.priority));
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 60 */     return this.value.hashCode() + this.priority.hashCode();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/DoubleNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */