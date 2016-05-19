/*    */ package com.firebase.client.snapshot;
/*    */ 
/*    */ import com.firebase.client.FirebaseException;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class NodeUtilities
/*    */ {
/*    */   public static Node NodeFromJSON(Object value) throws FirebaseException
/*    */   {
/* 12 */     return NodeFromJSON(value, PriorityUtilities.NullPriority());
/*    */   }
/*    */   
/*    */   public static Node NodeFromJSON(Object value, Node priority) throws FirebaseException {
/*    */     try {
/* 17 */       if ((value instanceof Map)) {
/* 18 */         Map mapValue = (Map)value;
/* 19 */         if (mapValue.containsKey(".priority")) {
/* 20 */           priority = PriorityUtilities.parsePriority(mapValue.get(".priority"));
/*    */         }
/*    */         
/* 23 */         if (mapValue.containsKey(".value")) {
/* 24 */           value = mapValue.get(".value");
/*    */         }
/*    */       }
/*    */       
/* 28 */       if (value == null)
/* 29 */         return EmptyNode.Empty();
/* 30 */       if ((value instanceof String))
/* 31 */         return new StringNode((String)value, priority);
/* 32 */       if ((value instanceof Long))
/* 33 */         return new LongNode((Long)value, priority);
/* 34 */       if ((value instanceof Integer))
/* 35 */         return new LongNode(Long.valueOf(((Integer)value).intValue()), priority);
/* 36 */       if ((value instanceof Double))
/* 37 */         return new DoubleNode((Double)value, priority);
/* 38 */       if ((value instanceof Boolean))
/* 39 */         return new BooleanNode((Boolean)value, priority);
/* 40 */       if (((value instanceof Map)) || ((value instanceof List)))
/*    */       {
/*    */         Map<ChildKey, Node> childData;
/* 43 */         if ((value instanceof Map)) {
/* 44 */           Map mapValue = (Map)value;
/* 45 */           if (mapValue.containsKey(".sv")) {
/* 46 */             return new DeferredValueNode(mapValue, priority);
/*    */           }
/*    */           
/* 49 */           Map<ChildKey, Node> childData = new java.util.HashMap(mapValue.size());
/* 50 */           Iterator<String> keyIter = mapValue.keySet().iterator();
/*    */           
/* 52 */           while (keyIter.hasNext()) {
/* 53 */             String key = (String)keyIter.next();
/* 54 */             if (!key.startsWith(".")) {
/* 55 */               Node childNode = NodeFromJSON(mapValue.get(key));
/* 56 */               if (!childNode.isEmpty()) {
/* 57 */                 ChildKey childKey = ChildKey.fromString(key);
/* 58 */                 childData.put(childKey, childNode);
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */         else {
/* 64 */           List listValue = (List)value;
/* 65 */           childData = new java.util.HashMap(listValue.size());
/* 66 */           for (int i = 0; i < listValue.size(); i++) {
/* 67 */             String key = "" + i;
/* 68 */             Node childNode = NodeFromJSON(listValue.get(i));
/* 69 */             if (!childNode.isEmpty()) {
/* 70 */               ChildKey childKey = ChildKey.fromString(key);
/* 71 */               childData.put(childKey, childNode);
/*    */             }
/*    */           }
/*    */         }
/*    */         
/* 76 */         if (childData.isEmpty()) {
/* 77 */           return EmptyNode.Empty();
/*    */         }
/* 79 */         com.firebase.client.collection.ImmutableSortedMap<ChildKey, Node> childSet = com.firebase.client.collection.ImmutableSortedMap.Builder.fromMap(childData, ChildrenNode.NAME_ONLY_COMPARATOR);
/*    */         
/* 81 */         return new ChildrenNode(childSet, priority);
/*    */       }
/*    */       
/* 84 */       throw new FirebaseException("Failed to parse node with class " + value.getClass().toString());
/*    */     }
/*    */     catch (ClassCastException e) {
/* 87 */       throw new FirebaseException("Failed to parse node", e);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public static int nameAndPriorityCompare(ChildKey aKey, Node aPriority, ChildKey bKey, Node bPriority)
/*    */   {
/* 94 */     int priCmp = aPriority.compareTo(bPriority);
/* 95 */     if (priCmp != 0) {
/* 96 */       return priCmp;
/*    */     }
/* 98 */     return aKey.compareTo(bKey);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/NodeUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */