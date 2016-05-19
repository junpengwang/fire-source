/*     */ package com.firebase.client;
/*     */ 
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.core.SnapshotHolder;
/*     */ import com.firebase.client.core.ValidationPath;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import com.firebase.client.snapshot.IndexedNode;
/*     */ import com.firebase.client.snapshot.NamedNode;
/*     */ import com.firebase.client.snapshot.Node;
/*     */ import com.firebase.client.snapshot.NodeUtilities;
/*     */ import com.firebase.client.snapshot.PriorityUtilities;
/*     */ import com.firebase.client.utilities.Validation;
/*     */ import com.firebase.client.utilities.encoding.JsonHelpers;
/*     */ import com.shaded.fasterxml.jackson.databind.ObjectMapper;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class MutableData
/*     */ {
/*     */   private final SnapshotHolder holder;
/*     */   private final Path prefixPath;
/*     */   
/*     */   public MutableData(Node node)
/*     */   {
/*  37 */     this(new SnapshotHolder(node), new Path(""));
/*     */   }
/*     */   
/*     */   private MutableData(SnapshotHolder holder, Path path) {
/*  41 */     this.holder = holder;
/*  42 */     this.prefixPath = path;
/*  43 */     ValidationPath.validateWithObject(this.prefixPath, getValue());
/*     */   }
/*     */   
/*     */   Node getNode() {
/*  47 */     return this.holder.getNode(this.prefixPath);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasChildren()
/*     */   {
/*  54 */     Node node = getNode();
/*  55 */     return (!node.isLeafNode()) && (!node.isEmpty());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasChild(String path)
/*     */   {
/*  63 */     return !getNode().getChild(new Path(path)).isEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MutableData child(String path)
/*     */   {
/*  73 */     Validation.validatePathString(path);
/*  74 */     return new MutableData(this.holder, this.prefixPath.child(new Path(path)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getChildrenCount()
/*     */   {
/*  81 */     return getNode().getChildCount();
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
/*     */   public Iterable<MutableData> getChildren()
/*     */   {
/*  94 */     Node node = getNode();
/*  95 */     if ((node.isEmpty()) || (node.isLeafNode())) {
/*  96 */       new Iterable()
/*     */       {
/*     */         public Iterator<MutableData> iterator() {
/*  99 */           new Iterator()
/*     */           {
/*     */             public boolean hasNext() {
/* 102 */               return false;
/*     */             }
/*     */             
/*     */             public MutableData next()
/*     */             {
/* 107 */               throw new NoSuchElementException();
/*     */             }
/*     */             
/*     */             public void remove()
/*     */             {
/* 112 */               throw new UnsupportedOperationException("remove called on immutable collection");
/*     */             }
/*     */           };
/*     */         }
/*     */       };
/*     */     }
/* 118 */     final Iterator<NamedNode> iter = IndexedNode.from(node).iterator();
/* 119 */     new Iterable()
/*     */     {
/*     */       public Iterator<MutableData> iterator() {
/* 122 */         new Iterator()
/*     */         {
/*     */           public boolean hasNext() {
/* 125 */             return MutableData.2.this.val$iter.hasNext();
/*     */           }
/*     */           
/*     */           public MutableData next()
/*     */           {
/* 130 */             NamedNode namedNode = (NamedNode)MutableData.2.this.val$iter.next();
/* 131 */             return new MutableData(MutableData.this.holder, MutableData.this.prefixPath.child(namedNode.getName()), null);
/*     */           }
/*     */           
/*     */ 
/*     */           public void remove()
/*     */           {
/* 137 */             throw new UnsupportedOperationException("remove called on immutable collection");
/*     */           }
/*     */         };
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public MutableData getParent()
/*     */   {
/* 152 */     Path path = this.prefixPath.getParent();
/* 153 */     if (path != null) {
/* 154 */       return new MutableData(this.holder, path);
/*     */     }
/* 156 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getKey()
/*     */   {
/* 163 */     return this.prefixPath.getBack() != null ? this.prefixPath.getBack().asString() : null;
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
/*     */   public Object getValue()
/*     */   {
/* 182 */     return getNode().getValue();
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
/*     */   public <T> T getValue(Class<T> valueType)
/*     */   {
/* 226 */     Object value = getNode().getValue();
/*     */     try {
/* 228 */       return (T)JsonHelpers.getMapper().convertValue(value, valueType);
/*     */     }
/*     */     catch (IllegalArgumentException e) {
/* 231 */       throw new FirebaseException("Failed to bounce to type", e);
/*     */     }
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
/*     */   public <T> T getValue(GenericTypeIndicator<T> t)
/*     */   {
/* 251 */     Object value = getNode().getValue();
/*     */     try {
/* 253 */       return (T)JsonHelpers.getMapper().convertValue(value, t);
/*     */     }
/*     */     catch (IllegalArgumentException e) {
/* 256 */       throw new FirebaseException("Failed to bounce to type", e);
/*     */     }
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
/*     */   public void setValue(Object value)
/*     */     throws FirebaseException
/*     */   {
/*     */     try
/*     */     {
/* 289 */       ValidationPath.validateWithObject(this.prefixPath, value);
/* 290 */       Object bouncedValue = JsonHelpers.getMapper().convertValue(value, Object.class);
/* 291 */       Validation.validateWritableObject(bouncedValue);
/* 292 */       this.holder.update(this.prefixPath, NodeUtilities.NodeFromJSON(bouncedValue));
/*     */     } catch (IllegalArgumentException e) {
/* 294 */       throw new FirebaseException("Failed to parse to snapshot", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPriority(Object priority)
/*     */   {
/* 303 */     this.holder.update(this.prefixPath, getNode().updatePriority(PriorityUtilities.parsePriority(priority)));
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
/*     */   public Object getPriority()
/*     */   {
/* 316 */     return getNode().getPriority().getValue();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 322 */     return ((o instanceof MutableData)) && (this.holder.equals(((MutableData)o).holder)) && (this.prefixPath.equals(((MutableData)o).prefixPath));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 329 */     ChildKey front = this.prefixPath.getFront();
/* 330 */     return "MutableData { key = " + (front != null ? front.asString() : "<none>") + ", value = " + this.holder.getRootNode().getValue(true) + " }";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/MutableData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */