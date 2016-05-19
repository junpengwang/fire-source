/*    */ package com.firebase.client.utilities;
/*    */ 
/*    */ import com.firebase.client.snapshot.ChildKey;
/*    */ import com.firebase.client.snapshot.ChildrenNode;
/*    */ import com.firebase.client.snapshot.DoubleNode;
/*    */ import com.firebase.client.snapshot.LeafNode;
/*    */ import com.firebase.client.snapshot.NamedNode;
/*    */ import com.firebase.client.snapshot.Node;
/*    */ import com.firebase.client.snapshot.StringNode;
/*    */ 
/*    */ public class NodeSizeEstimator
/*    */ {
/*    */   private static final int LEAF_PRIORITY_OVERHEAD = 24;
/*    */   
/*    */   private static long estimateLeafNodeSize(LeafNode<?> node)
/*    */   {
/*    */     long valueSize;
/* 18 */     if ((node instanceof DoubleNode)) {
/* 19 */       valueSize = 8L; } else { long valueSize;
/* 20 */       if ((node instanceof com.firebase.client.snapshot.LongNode)) {
/* 21 */         valueSize = 8L; } else { long valueSize;
/* 22 */         if ((node instanceof com.firebase.client.snapshot.BooleanNode)) {
/* 23 */           valueSize = 4L; } else { long valueSize;
/* 24 */           if ((node instanceof StringNode)) {
/* 25 */             valueSize = 2 + ((String)node.getValue()).length();
/*    */           } else
/* 27 */             throw new IllegalArgumentException("Unknown leaf node type: " + node.getClass()); } } }
/*    */     long valueSize;
/* 29 */     if (node.getPriority().isEmpty()) {
/* 30 */       return valueSize;
/*    */     }
/* 32 */     return 24L + valueSize + estimateLeafNodeSize((LeafNode)node.getPriority());
/*    */   }
/*    */   
/*    */   public static long estimateSerializedNodeSize(Node node)
/*    */   {
/* 37 */     if (node.isEmpty())
/* 38 */       return 4L;
/* 39 */     if (node.isLeafNode()) {
/* 40 */       return estimateLeafNodeSize((LeafNode)node);
/*    */     }
/* 42 */     assert ((node instanceof ChildrenNode)) : ("Unexpected node type: " + node.getClass());
/* 43 */     long sum = 1L;
/* 44 */     for (NamedNode entry : node) {
/* 45 */       sum += entry.getName().asString().length();
/* 46 */       sum += 4L;
/* 47 */       sum += estimateSerializedNodeSize(entry.getNode());
/*    */     }
/* 49 */     if (!node.getPriority().isEmpty()) {
/* 50 */       sum += 12L;
/* 51 */       sum += estimateLeafNodeSize((LeafNode)node.getPriority());
/*    */     }
/* 53 */     return sum;
/*    */   }
/*    */   
/*    */   public static int nodeCount(Node node)
/*    */   {
/* 58 */     if (node.isEmpty())
/* 59 */       return 0;
/* 60 */     if (node.isLeafNode()) {
/* 61 */       return 1;
/*    */     }
/* 63 */     assert ((node instanceof ChildrenNode)) : ("Unexpected node type: " + node.getClass());
/* 64 */     int sum = 0;
/* 65 */     for (NamedNode entry : node) {
/* 66 */       sum += nodeCount(entry.getNode());
/*    */     }
/* 68 */     return sum;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/utilities/NodeSizeEstimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */