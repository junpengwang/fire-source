/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ import com.firebase.client.FirebaseException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PriorityUtilities
/*    */ {
/*    */   public static Node NullPriority()
/*    */   {
/* 17 */     return EmptyNode.Empty();
/*    */   }
/*    */   
/*    */   public static boolean isValidPriority(Node priority) {
/* 21 */     return (priority.getPriority().isEmpty()) && ((priority.isEmpty()) || ((priority instanceof DoubleNode)) || ((priority instanceof StringNode)) || ((priority instanceof DeferredValueNode)));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static Node parsePriority(Object value)
/*    */   {
/* 29 */     Node priority = NodeUtilities.NodeFromJSON(value);
/* 30 */     if ((priority instanceof LongNode)) {
/* 31 */       priority = new DoubleNode(Double.valueOf(((Long)priority.getValue()).longValue()), NullPriority());
/*    */     }
/* 33 */     if (!isValidPriority(priority)) {
/* 34 */       throw new FirebaseException("Invalid Firebase priority (must be a string, double, ServerValue, or null)");
/*    */     }
/* 36 */     return priority;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/PriorityUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */