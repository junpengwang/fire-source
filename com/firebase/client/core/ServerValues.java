/*    */ package com.firebase.client.core;
/*    */ 
/*    */ import com.firebase.client.snapshot.ChildKey;
/*    */ import com.firebase.client.snapshot.ChildrenNode;
/*    */ import com.firebase.client.snapshot.ChildrenNode.ChildVisitor;
/*    */ import com.firebase.client.snapshot.Node;
/*    */ import com.firebase.client.snapshot.NodeUtilities;
/*    */ import com.firebase.client.snapshot.PriorityUtilities;
/*    */ import com.firebase.client.utilities.Clock;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ public class ServerValues
/*    */ {
/*    */   public static final String NAME_SUBKEY_SERVERVALUE = ".sv";
/*    */   
/*    */   public static Map<String, Object> generateServerValues(Clock clock)
/*    */   {
/* 20 */     Map<String, Object> values = new HashMap();
/* 21 */     values.put("timestamp", Long.valueOf(clock.millis()));
/* 22 */     return values;
/*    */   }
/*    */   
/*    */   public static Object resolveDeferredValue(Object value, Map<String, Object> serverValues) {
/* 26 */     if ((value instanceof Map)) {
/* 27 */       Map mapValue = (Map)value;
/* 28 */       if (mapValue.containsKey(".sv")) {
/* 29 */         String serverValueKey = (String)mapValue.get(".sv");
/* 30 */         if (serverValues.containsKey(serverValueKey)) {
/* 31 */           return serverValues.get(serverValueKey);
/*    */         }
/*    */       }
/*    */     }
/* 35 */     return value;
/*    */   }
/*    */   
/*    */   public static SparseSnapshotTree resolveDeferredValueTree(SparseSnapshotTree tree, final Map<String, Object> serverValues) {
/* 39 */     SparseSnapshotTree resolvedTree = new SparseSnapshotTree();
/* 40 */     tree.forEachTree(new Path(""), new SparseSnapshotTree.SparseSnapshotTreeVisitor()
/*    */     {
/*    */       public void visitTree(Path prefixPath, Node tree) {
/* 43 */         this.val$resolvedTree.remember(prefixPath, ServerValues.resolveDeferredValueSnapshot(tree, serverValues));
/*    */       }
/* 45 */     });
/* 46 */     return resolvedTree;
/*    */   }
/*    */   
/*    */   public static Node resolveDeferredValueSnapshot(Node data, Map<String, Object> serverValues) {
/* 50 */     Object priorityVal = data.getPriority().getValue();
/* 51 */     if ((priorityVal instanceof Map)) {
/* 52 */       Map priorityMapValue = (Map)priorityVal;
/* 53 */       if (priorityMapValue.containsKey(".sv")) {
/* 54 */         String serverValueKey = (String)priorityMapValue.get(".sv");
/* 55 */         priorityVal = serverValues.get(serverValueKey);
/*    */       }
/*    */     }
/* 58 */     Node priority = PriorityUtilities.parsePriority(priorityVal);
/*    */     
/* 60 */     if (data.isLeafNode()) {
/* 61 */       Object value = resolveDeferredValue(data.getValue(), serverValues);
/* 62 */       if ((!value.equals(data.getValue())) || (!priority.equals(data.getPriority()))) {
/* 63 */         return NodeUtilities.NodeFromJSON(value, priority);
/*    */       }
/* 65 */       return data; }
/* 66 */     if (data.isEmpty()) {
/* 67 */       return data;
/*    */     }
/* 69 */     ChildrenNode childNode = (ChildrenNode)data;
/* 70 */     final SnapshotHolder holder = new SnapshotHolder(childNode);
/* 71 */     childNode.forEachChild(new ChildrenNode.ChildVisitor()
/*    */     {
/*    */       public void visitChild(ChildKey name, Node child) {
/* 74 */         Node newChildNode = ServerValues.resolveDeferredValueSnapshot(child, this.val$serverValues);
/* 75 */         if (newChildNode != child) {
/* 76 */           holder.update(new Path(name.asString()), newChildNode);
/*    */         }
/*    */       }
/*    */     });
/* 80 */     if (!holder.getRootNode().getPriority().equals(priority)) {
/* 81 */       return holder.getRootNode().updatePriority(priority);
/*    */     }
/* 83 */     return holder.getRootNode();
/*    */   }
/*    */   
/*    */ 
/*    */   public static CompoundWrite resolveDeferredValueMerge(CompoundWrite merge, Map<String, Object> serverValues)
/*    */   {
/* 89 */     CompoundWrite write = CompoundWrite.emptyWrite();
/* 90 */     for (Map.Entry<Path, Node> entry : merge) {
/* 91 */       write = write.addWrite((Path)entry.getKey(), resolveDeferredValueSnapshot((Node)entry.getValue(), serverValues));
/*    */     }
/* 93 */     return write;
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/ServerValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */