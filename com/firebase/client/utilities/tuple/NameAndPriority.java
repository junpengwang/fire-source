/*    */ package com.firebase.client.utilities.tuple;
/*    */ 
/*    */ import com.firebase.client.snapshot.ChildKey;
/*    */ import com.firebase.client.snapshot.Node;
/*    */ import com.firebase.client.snapshot.NodeUtilities;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NameAndPriority
/*    */   implements Comparable<NameAndPriority>
/*    */ {
/*    */   private ChildKey name;
/*    */   private Node priority;
/*    */   
/*    */   public NameAndPriority(ChildKey name, Node priority)
/*    */   {
/* 19 */     this.name = name;
/* 20 */     this.priority = priority;
/*    */   }
/*    */   
/*    */   public ChildKey getName() {
/* 24 */     return this.name;
/*    */   }
/*    */   
/*    */   public Node getPriority() {
/* 28 */     return this.priority;
/*    */   }
/*    */   
/*    */   public int compareTo(NameAndPriority o)
/*    */   {
/* 33 */     return NodeUtilities.nameAndPriorityCompare(this.name, this.priority, o.name, o.priority);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/tuple/NameAndPriority.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */