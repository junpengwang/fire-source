/*     */ package com.firebase.client.collection;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public abstract class LLRBValueNode<K, V> implements LLRBNode<K, V>
/*     */ {
/*     */   private final K key;
/*     */   private final V value;
/*     */   private LLRBNode<K, V> left;
/*     */   private final LLRBNode<K, V> right;
/*     */   
/*     */   private static LLRBNode.Color oppositeColor(LLRBNode node) {
/*  13 */     return node.isRed() ? LLRBNode.Color.BLACK : LLRBNode.Color.RED;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   LLRBValueNode(K key, V value, LLRBNode<K, V> left, LLRBNode<K, V> right)
/*     */   {
/*  22 */     this.key = key;
/*  23 */     this.value = value;
/*  24 */     this.left = (left == null ? LLRBEmptyNode.getInstance() : left);
/*  25 */     this.right = (right == null ? LLRBEmptyNode.getInstance() : right);
/*     */   }
/*     */   
/*     */   public LLRBNode<K, V> getLeft()
/*     */   {
/*  30 */     return this.left;
/*     */   }
/*     */   
/*     */   public LLRBNode<K, V> getRight()
/*     */   {
/*  35 */     return this.right;
/*     */   }
/*     */   
/*     */   public K getKey()
/*     */   {
/*  40 */     return (K)this.key;
/*     */   }
/*     */   
/*     */   public V getValue()
/*     */   {
/*  45 */     return (V)this.value;
/*     */   }
/*     */   
/*     */   protected abstract LLRBNode.Color getColor();
/*     */   
/*     */   protected abstract LLRBValueNode<K, V> copy(K paramK, V paramV, LLRBNode<K, V> paramLLRBNode1, LLRBNode<K, V> paramLLRBNode2);
/*     */   
/*     */   public LLRBValueNode<K, V> copy(K key, V value, LLRBNode.Color color, LLRBNode<K, V> left, LLRBNode<K, V> right)
/*     */   {
/*  54 */     K newKey = key == null ? this.key : key;
/*  55 */     V newValue = value == null ? this.value : value;
/*  56 */     LLRBNode<K, V> newLeft = left == null ? this.left : left;
/*  57 */     LLRBNode<K, V> newRight = right == null ? this.right : right;
/*  58 */     if (color == LLRBNode.Color.RED) {
/*  59 */       return new LLRBRedValueNode(newKey, newValue, newLeft, newRight);
/*     */     }
/*  61 */     return new LLRBBlackValueNode(newKey, newValue, newLeft, newRight);
/*     */   }
/*     */   
/*     */ 
/*     */   public LLRBNode<K, V> insert(K key, V value, Comparator<K> comparator)
/*     */   {
/*  67 */     int cmp = comparator.compare(key, this.key);
/*     */     LLRBValueNode<K, V> n;
/*  69 */     LLRBValueNode<K, V> n; if (cmp < 0)
/*     */     {
/*  71 */       LLRBNode<K, V> newLeft = this.left.insert(key, value, comparator);
/*  72 */       n = copy(null, null, newLeft, null); } else { LLRBValueNode<K, V> n;
/*  73 */       if (cmp == 0)
/*     */       {
/*  75 */         n = copy(key, value, null, null);
/*     */       }
/*     */       else {
/*  78 */         LLRBNode<K, V> newRight = this.right.insert(key, value, comparator);
/*  79 */         n = copy(null, null, null, newRight);
/*     */       } }
/*  81 */     return n.fixUp();
/*     */   }
/*     */   
/*     */   public LLRBNode<K, V> remove(K key, Comparator<K> comparator)
/*     */   {
/*  86 */     LLRBValueNode<K, V> n = this;
/*     */     
/*  88 */     if (comparator.compare(key, n.key) < 0) {
/*  89 */       if ((!n.left.isEmpty()) && (!n.left.isRed()) && (!((LLRBValueNode)n.left).left.isRed())) {
/*  90 */         n = n.moveRedLeft();
/*     */       }
/*  92 */       n = n.copy(null, null, n.left.remove(key, comparator), null);
/*     */     } else {
/*  94 */       if (n.left.isRed()) {
/*  95 */         n = n.rotateRight();
/*     */       }
/*     */       
/*  98 */       if ((!n.right.isEmpty()) && (!n.right.isRed()) && (!((LLRBValueNode)n.right).left.isRed())) {
/*  99 */         n = n.moveRedRight();
/*     */       }
/*     */       
/* 102 */       if (comparator.compare(key, n.key) == 0) {
/* 103 */         if (n.right.isEmpty()) {
/* 104 */           return LLRBEmptyNode.getInstance();
/*     */         }
/* 106 */         LLRBNode<K, V> smallest = n.right.getMin();
/* 107 */         n = n.copy(smallest.getKey(), smallest.getValue(), null, ((LLRBValueNode)n.right).removeMin());
/*     */       }
/*     */       
/*     */ 
/* 111 */       n = n.copy(null, null, null, n.right.remove(key, comparator));
/*     */     }
/* 113 */     return n.fixUp();
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 118 */     return false;
/*     */   }
/*     */   
/*     */   public LLRBNode<K, V> getMin()
/*     */   {
/* 123 */     if (this.left.isEmpty()) {
/* 124 */       return this;
/*     */     }
/* 126 */     return this.left.getMin();
/*     */   }
/*     */   
/*     */ 
/*     */   public LLRBNode<K, V> getMax()
/*     */   {
/* 132 */     if (this.right.isEmpty()) {
/* 133 */       return this;
/*     */     }
/* 135 */     return this.right.getMax();
/*     */   }
/*     */   
/*     */ 
/*     */   public int count()
/*     */   {
/* 141 */     return this.left.count() + 1 + this.right.count();
/*     */   }
/*     */   
/*     */   public void inOrderTraversal(LLRBNode.NodeVisitor<K, V> visitor)
/*     */   {
/* 146 */     this.left.inOrderTraversal(visitor);
/* 147 */     visitor.visitEntry(this.key, this.value);
/* 148 */     this.right.inOrderTraversal(visitor);
/*     */   }
/*     */   
/*     */   public boolean shortCircuitingInOrderTraversal(LLRBNode.ShortCircuitingNodeVisitor<K, V> visitor)
/*     */   {
/* 153 */     if ((this.left.shortCircuitingInOrderTraversal(visitor)) && 
/* 154 */       (visitor.shouldContinue(this.key, this.value))) {
/* 155 */       return this.right.shortCircuitingInOrderTraversal(visitor);
/*     */     }
/*     */     
/* 158 */     return false;
/*     */   }
/*     */   
/*     */   public boolean shortCircuitingReverseOrderTraversal(LLRBNode.ShortCircuitingNodeVisitor<K, V> visitor)
/*     */   {
/* 163 */     if ((this.right.shortCircuitingReverseOrderTraversal(visitor)) && 
/* 164 */       (visitor.shouldContinue(this.key, this.value))) {
/* 165 */       return this.left.shortCircuitingReverseOrderTraversal(visitor);
/*     */     }
/*     */     
/* 168 */     return false;
/*     */   }
/*     */   
/*     */   void setLeft(LLRBNode<K, V> left)
/*     */   {
/* 173 */     this.left = left;
/*     */   }
/*     */   
/*     */   private LLRBNode<K, V> removeMin() {
/* 177 */     if (this.left.isEmpty()) {
/* 178 */       return LLRBEmptyNode.getInstance();
/*     */     }
/* 180 */     LLRBValueNode<K, V> n = this;
/* 181 */     if ((!n.getLeft().isRed()) && (!n.getLeft().getLeft().isRed())) {
/* 182 */       n = n.moveRedLeft();
/*     */     }
/*     */     
/* 185 */     n = n.copy(null, null, ((LLRBValueNode)n.left).removeMin(), null);
/* 186 */     return n.fixUp();
/*     */   }
/*     */   
/*     */   private LLRBValueNode<K, V> moveRedLeft()
/*     */   {
/* 191 */     LLRBValueNode<K, V> n = colorFlip();
/* 192 */     if (n.getRight().getLeft().isRed()) {
/* 193 */       n = n.copy(null, null, null, ((LLRBValueNode)n.getRight()).rotateRight());
/* 194 */       n = n.rotateLeft();
/* 195 */       n = n.colorFlip();
/*     */     }
/* 197 */     return n;
/*     */   }
/*     */   
/*     */   private LLRBValueNode<K, V> moveRedRight() {
/* 201 */     LLRBValueNode<K, V> n = colorFlip();
/* 202 */     if (n.getLeft().getLeft().isRed()) {
/* 203 */       n = n.rotateRight();
/* 204 */       n = n.colorFlip();
/*     */     }
/* 206 */     return n;
/*     */   }
/*     */   
/*     */   private LLRBValueNode<K, V> fixUp() {
/* 210 */     LLRBValueNode<K, V> n = this;
/* 211 */     if ((n.right.isRed()) && (!n.left.isRed())) {
/* 212 */       n = n.rotateLeft();
/*     */     }
/* 214 */     if ((n.left.isRed()) && (((LLRBValueNode)n.left).left.isRed())) {
/* 215 */       n = n.rotateRight();
/*     */     }
/* 217 */     if ((n.left.isRed()) && (n.right.isRed())) {
/* 218 */       n = n.colorFlip();
/*     */     }
/* 220 */     return n;
/*     */   }
/*     */   
/*     */   private LLRBValueNode<K, V> rotateLeft() {
/* 224 */     LLRBValueNode<K, V> newLeft = copy(null, null, LLRBNode.Color.RED, null, ((LLRBValueNode)this.right).left);
/* 225 */     return (LLRBValueNode)this.right.copy(null, null, getColor(), newLeft, null);
/*     */   }
/*     */   
/*     */   private LLRBValueNode<K, V> rotateRight() {
/* 229 */     LLRBValueNode<K, V> newRight = copy(null, null, LLRBNode.Color.RED, ((LLRBValueNode)this.left).right, null);
/* 230 */     return (LLRBValueNode)this.left.copy(null, null, getColor(), null, newRight);
/*     */   }
/*     */   
/*     */   private LLRBValueNode<K, V> colorFlip() {
/* 234 */     LLRBNode<K, V> newLeft = this.left.copy(null, null, oppositeColor(this.left), null, null);
/* 235 */     LLRBNode<K, V> newRight = this.right.copy(null, null, oppositeColor(this.right), null, null);
/*     */     
/* 237 */     return copy(null, null, oppositeColor(this), newLeft, newRight);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/collection/LLRBValueNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */