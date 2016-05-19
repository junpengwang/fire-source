/*     */ package com.shaded.fasterxml.jackson.databind.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PrimitiveArrayBuilder<T>
/*     */ {
/*     */   static final int INITIAL_CHUNK_SIZE = 12;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int SMALL_CHUNK_SIZE = 16384;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int MAX_CHUNK_SIZE = 262144;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected T _freeBuffer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Node<T> _bufferHead;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Node<T> _bufferTail;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int _bufferedEntryCount;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T resetAndStart()
/*     */   {
/*  55 */     _reset();
/*  56 */     return (T)(this._freeBuffer == null ? _constructArray(12) : this._freeBuffer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final T appendCompletedChunk(T paramT, int paramInt)
/*     */   {
/*  65 */     Node localNode = new Node(paramT, paramInt);
/*  66 */     if (this._bufferHead == null) {
/*  67 */       this._bufferHead = (this._bufferTail = localNode);
/*     */     } else {
/*  69 */       this._bufferTail.linkNext(localNode);
/*  70 */       this._bufferTail = localNode;
/*     */     }
/*  72 */     this._bufferedEntryCount += paramInt;
/*  73 */     int i = paramInt;
/*     */     
/*  75 */     if (i < 16384) {
/*  76 */       i += i;
/*     */     } else {
/*  78 */       i += (i >> 2);
/*     */     }
/*  80 */     return (T)_constructArray(i);
/*     */   }
/*     */   
/*     */   public T completeAndClearBuffer(T paramT, int paramInt)
/*     */   {
/*  85 */     int i = paramInt + this._bufferedEntryCount;
/*  86 */     Object localObject = _constructArray(i);
/*     */     
/*  88 */     int j = 0;
/*     */     
/*  90 */     for (Node localNode = this._bufferHead; localNode != null; localNode = localNode.next()) {
/*  91 */       j = localNode.copyData(localObject, j);
/*     */     }
/*  93 */     System.arraycopy(paramT, 0, localObject, j, paramInt);
/*  94 */     j += paramInt;
/*     */     
/*     */ 
/*  97 */     if (j != i) {
/*  98 */       throw new IllegalStateException("Should have gotten " + i + " entries, got " + j);
/*     */     }
/* 100 */     return (T)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract T _constructArray(int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _reset()
/*     */   {
/* 120 */     if (this._bufferTail != null) {
/* 121 */       this._freeBuffer = this._bufferTail.getData();
/*     */     }
/*     */     
/* 124 */     this._bufferHead = (this._bufferTail = null);
/* 125 */     this._bufferedEntryCount = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class Node<T>
/*     */   {
/*     */     final T _data;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     final int _dataLength;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     Node<T> _next;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Node(T paramT, int paramInt)
/*     */     {
/* 157 */       this._data = paramT;
/* 158 */       this._dataLength = paramInt;
/*     */     }
/*     */     
/* 161 */     public T getData() { return (T)this._data; }
/*     */     
/*     */     public int copyData(T paramT, int paramInt)
/*     */     {
/* 165 */       System.arraycopy(this._data, 0, paramT, paramInt, this._dataLength);
/* 166 */       paramInt += this._dataLength;
/* 167 */       return paramInt;
/*     */     }
/*     */     
/* 170 */     public Node<T> next() { return this._next; }
/*     */     
/*     */     public void linkNext(Node<T> paramNode)
/*     */     {
/* 174 */       if (this._next != null) {
/* 175 */         throw new IllegalStateException();
/*     */       }
/* 177 */       this._next = paramNode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/PrimitiveArrayBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */