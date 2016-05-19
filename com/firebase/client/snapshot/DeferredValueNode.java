/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DeferredValueNode
/*    */   extends LeafNode<DeferredValueNode>
/*    */ {
/*    */   private Map<Object, Object> value;
/*    */   
/*    */   public DeferredValueNode(Map<Object, Object> value, Node priority)
/*    */   {
/* 12 */     super(priority);
/* 13 */     this.value = value;
/*    */   }
/*    */   
/*    */   public Object getValue()
/*    */   {
/* 18 */     return this.value;
/*    */   }
/*    */   
/*    */   public String getHashString()
/*    */   {
/* 23 */     return getPriorityHash() + "deferredValue:" + this.value;
/*    */   }
/*    */   
/*    */   public DeferredValueNode updatePriority(Node priority)
/*    */   {
/* 28 */     assert (PriorityUtilities.isValidPriority(priority));
/* 29 */     return new DeferredValueNode(this.value, priority);
/*    */   }
/*    */   
/*    */   protected LeafNode.LeafType getLeafType()
/*    */   {
/* 34 */     return LeafNode.LeafType.DeferredValue;
/*    */   }
/*    */   
/*    */ 
/*    */   protected int compareLeafValues(DeferredValueNode other)
/*    */   {
/* 40 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean equals(Object other)
/*    */   {
/* 45 */     if (!(other instanceof DeferredValueNode)) {
/* 46 */       return false;
/*    */     }
/* 48 */     DeferredValueNode otherDeferredValueNode = (DeferredValueNode)other;
/* 49 */     return (this.value.equals(otherDeferredValueNode.value)) && (this.priority.equals(otherDeferredValueNode.priority));
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 54 */     return this.value.hashCode() + this.priority.hashCode();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/DeferredValueNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */