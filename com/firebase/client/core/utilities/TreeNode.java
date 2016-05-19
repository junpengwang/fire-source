/*    */ package com.firebase.client.core.utilities;
/*    */ 
/*    */ import com.firebase.client.snapshot.ChildKey;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ public class TreeNode<T>
/*    */ {
/*    */   public Map<ChildKey, TreeNode<T>> children;
/*    */   public T value;
/*    */   
/*    */   public TreeNode()
/*    */   {
/* 15 */     this.children = new java.util.HashMap();
/*    */   }
/*    */   
/*    */   String toString(String prefix) {
/* 19 */     String result = prefix + "<value>: " + this.value + "\n";
/* 20 */     if (this.children.isEmpty()) {
/* 21 */       return result + prefix + "<empty>";
/*    */     }
/* 23 */     Iterator<Map.Entry<ChildKey, TreeNode<T>>> iter = this.children.entrySet().iterator();
/* 24 */     while (iter.hasNext()) {
/* 25 */       Map.Entry<ChildKey, TreeNode<T>> entry = (Map.Entry)iter.next();
/* 26 */       result = result + prefix + entry.getKey() + ":\n" + ((TreeNode)entry.getValue()).toString(new StringBuilder().append(prefix).append("\t").toString()) + "\n";
/*    */     }
/*    */     
/* 29 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/utilities/TreeNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */