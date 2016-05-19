/*    */ package com.shaded.fasterxml.jackson.databind.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.Serializable;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LRUMap<K, V>
/*    */   extends LinkedHashMap<K, V>
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected final int _maxEntries;
/*    */   protected transient int _jdkSerializeMaxEntries;
/*    */   
/*    */   public LRUMap(int paramInt1, int paramInt2)
/*    */   {
/* 23 */     super(paramInt1, 0.8F, true);
/* 24 */     this._maxEntries = paramInt2;
/*    */   }
/*    */   
/*    */ 
/*    */   protected boolean removeEldestEntry(Map.Entry<K, V> paramEntry)
/*    */   {
/* 30 */     return size() > this._maxEntries;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private void readObject(ObjectInputStream paramObjectInputStream)
/*    */     throws IOException
/*    */   {
/* 48 */     this._jdkSerializeMaxEntries = paramObjectInputStream.readInt();
/*    */   }
/*    */   
/*    */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 52 */     paramObjectOutputStream.writeInt(this._jdkSerializeMaxEntries);
/*    */   }
/*    */   
/*    */   protected Object readResolve() {
/* 56 */     return new LRUMap(this._jdkSerializeMaxEntries, this._jdkSerializeMaxEntries);
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/LRUMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */