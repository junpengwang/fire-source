/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringNode
/*    */   extends LeafNode<StringNode>
/*    */ {
/*    */   private final String value;
/*    */   
/*    */ 
/*    */ 
/*    */   public StringNode(String value, Node priority)
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
/* 26 */     return getPriorityHash() + "string:" + this.value;
/*    */   }
/*    */   
/*    */   public StringNode updatePriority(Node priority)
/*    */   {
/* 31 */     return new StringNode(this.value, priority);
/*    */   }
/*    */   
/*    */   protected LeafNode.LeafType getLeafType()
/*    */   {
/* 36 */     return LeafNode.LeafType.String;
/*    */   }
/*    */   
/*    */   protected int compareLeafValues(StringNode other)
/*    */   {
/* 41 */     return this.value.compareTo(other.value);
/*    */   }
/*    */   
/*    */   public boolean equals(Object other)
/*    */   {
/* 46 */     if (!(other instanceof StringNode)) {
/* 47 */       return false;
/*    */     }
/* 49 */     StringNode otherStringNode = (StringNode)other;
/* 50 */     return (this.value.equals(otherStringNode.value)) && (this.priority.equals(otherStringNode.priority));
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 55 */     return this.value.hashCode() + this.priority.hashCode();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/StringNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */