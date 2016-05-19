/*     */ package com.shaded.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectBuffer
/*     */ {
/*     */   static final int INITIAL_CHUNK_SIZE = 12;
/*     */   static final int SMALL_CHUNK_SIZE = 16384;
/*     */   static final int MAX_CHUNK_SIZE = 262144;
/*     */   private Node _bufferHead;
/*     */   private Node _bufferTail;
/*     */   private int _bufferedEntryCount;
/*     */   private Object[] _freeBuffer;
/*     */   
/*     */   public Object[] resetAndStart()
/*     */   {
/*  73 */     _reset();
/*  74 */     if (this._freeBuffer == null) {
/*  75 */       return new Object[12];
/*     */     }
/*  77 */     return this._freeBuffer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] appendCompletedChunk(Object[] paramArrayOfObject)
/*     */   {
/*  96 */     Node localNode = new Node(paramArrayOfObject);
/*  97 */     if (this._bufferHead == null) {
/*  98 */       this._bufferHead = (this._bufferTail = localNode);
/*     */     } else {
/* 100 */       this._bufferTail.linkNext(localNode);
/* 101 */       this._bufferTail = localNode;
/*     */     }
/* 103 */     int i = paramArrayOfObject.length;
/* 104 */     this._bufferedEntryCount += i;
/*     */     
/* 106 */     if (i < 16384) {
/* 107 */       i += i;
/*     */     } else {
/* 109 */       i += (i >> 2);
/*     */     }
/* 111 */     return new Object[i];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] completeAndClearBuffer(Object[] paramArrayOfObject, int paramInt)
/*     */   {
/* 126 */     int i = paramInt + this._bufferedEntryCount;
/* 127 */     Object[] arrayOfObject = new Object[i];
/* 128 */     _copyTo(arrayOfObject, i, paramArrayOfObject, paramInt);
/* 129 */     return arrayOfObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T[] completeAndClearBuffer(Object[] paramArrayOfObject, int paramInt, Class<T> paramClass)
/*     */   {
/* 142 */     int i = paramInt + this._bufferedEntryCount;
/*     */     
/* 144 */     Object[] arrayOfObject = (Object[])Array.newInstance(paramClass, i);
/* 145 */     _copyTo(arrayOfObject, i, paramArrayOfObject, paramInt);
/* 146 */     _reset();
/* 147 */     return arrayOfObject;
/*     */   }
/*     */   
/*     */   public void completeAndClearBuffer(Object[] paramArrayOfObject, int paramInt, List<Object> paramList)
/*     */   {
/* 152 */     for (Node localNode = this._bufferHead; localNode != null; localNode = localNode.next()) {
/* 153 */       Object[] arrayOfObject = localNode.getData();
/* 154 */       int j = 0; for (int k = arrayOfObject.length; j < k; j++) {
/* 155 */         paramList.add(arrayOfObject[j]);
/*     */       }
/*     */     }
/*     */     
/* 159 */     for (int i = 0; i < paramInt; i++) {
/* 160 */       paramList.add(paramArrayOfObject[i]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int initialCapacity()
/*     */   {
/* 172 */     return this._freeBuffer == null ? 0 : this._freeBuffer.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int bufferedSize()
/*     */   {
/* 179 */     return this._bufferedEntryCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _reset()
/*     */   {
/* 190 */     if (this._bufferTail != null) {
/* 191 */       this._freeBuffer = this._bufferTail.getData();
/*     */     }
/*     */     
/* 194 */     this._bufferHead = (this._bufferTail = null);
/* 195 */     this._bufferedEntryCount = 0;
/*     */   }
/*     */   
/*     */ 
/*     */   protected final void _copyTo(Object paramObject, int paramInt1, Object[] paramArrayOfObject, int paramInt2)
/*     */   {
/* 201 */     int i = 0;
/*     */     
/* 203 */     for (Node localNode = this._bufferHead; localNode != null; localNode = localNode.next()) {
/* 204 */       Object[] arrayOfObject = localNode.getData();
/* 205 */       int j = arrayOfObject.length;
/* 206 */       System.arraycopy(arrayOfObject, 0, paramObject, i, j);
/* 207 */       i += j;
/*     */     }
/* 209 */     System.arraycopy(paramArrayOfObject, 0, paramObject, i, paramInt2);
/* 210 */     i += paramInt2;
/*     */     
/*     */ 
/* 213 */     if (i != paramInt1) {
/* 214 */       throw new IllegalStateException("Should have gotten " + paramInt1 + " entries, got " + i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class Node
/*     */   {
/*     */     final Object[] _data;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     Node _next;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Node(Object[] paramArrayOfObject)
/*     */     {
/* 237 */       this._data = paramArrayOfObject;
/*     */     }
/*     */     
/* 240 */     public Object[] getData() { return this._data; }
/*     */     
/* 242 */     public Node next() { return this._next; }
/*     */     
/*     */     public void linkNext(Node paramNode)
/*     */     {
/* 246 */       if (this._next != null) {
/* 247 */         throw new IllegalStateException();
/*     */       }
/* 249 */       this._next = paramNode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/ObjectBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */