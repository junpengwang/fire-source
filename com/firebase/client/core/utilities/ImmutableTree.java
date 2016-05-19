/*     */ package com.firebase.client.core.utilities;
/*     */ 
/*     */ import com.firebase.client.collection.ImmutableSortedMap;
/*     */ import com.firebase.client.collection.ImmutableSortedMap.Builder;
/*     */ import com.firebase.client.collection.StandardComparator;
/*     */ import com.firebase.client.core.Path;
/*     */ import com.firebase.client.snapshot.ChildKey;
/*     */ import java.util.AbstractMap.SimpleImmutableEntry;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class ImmutableTree<T>
/*     */   implements Iterable<Map.Entry<Path, T>>
/*     */ {
/*     */   private final T value;
/*     */   private final ImmutableSortedMap<ChildKey, ImmutableTree<T>> children;
/*  20 */   private static final ImmutableSortedMap EMPTY_CHILDREN = ImmutableSortedMap.Builder.emptyMap(StandardComparator.getComparator(ChildKey.class));
/*     */   
/*     */ 
/*  23 */   private static final ImmutableTree EMPTY = new ImmutableTree(null, EMPTY_CHILDREN);
/*     */   
/*     */   public static <V> ImmutableTree<V> emptyInstance()
/*     */   {
/*  27 */     return EMPTY;
/*     */   }
/*     */   
/*     */   public ImmutableTree(T value, ImmutableSortedMap<ChildKey, ImmutableTree<T>> children) {
/*  31 */     this.value = value;
/*  32 */     this.children = children;
/*     */   }
/*     */   
/*     */   public ImmutableTree(T value)
/*     */   {
/*  37 */     this(value, EMPTY_CHILDREN);
/*     */   }
/*     */   
/*     */   public T getValue() {
/*  41 */     return (T)this.value;
/*     */   }
/*     */   
/*     */   public ImmutableSortedMap<ChildKey, ImmutableTree<T>> getChildren() {
/*  45 */     return this.children;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  49 */     return (this.value == null) && (this.children.isEmpty());
/*     */   }
/*     */   
/*     */   public Path findRootMostMatchingPath(Path relativePath, Predicate<? super T> predicate) {
/*  53 */     if ((this.value != null) && (predicate.evaluate(this.value))) {
/*  54 */       return Path.getEmptyPath();
/*     */     }
/*  56 */     if (relativePath.isEmpty()) {
/*  57 */       return null;
/*     */     }
/*  59 */     ChildKey front = relativePath.getFront();
/*  60 */     ImmutableTree<T> child = (ImmutableTree)this.children.get(front);
/*  61 */     if (child != null) {
/*  62 */       Path path = child.findRootMostMatchingPath(relativePath.popFront(), predicate);
/*  63 */       if (path != null)
/*     */       {
/*  65 */         return new Path(new ChildKey[] { front }).child(path);
/*     */       }
/*  67 */       return null;
/*     */     }
/*     */     
/*  70 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Path findRootMostPathWithValue(Path relativePath)
/*     */   {
/*  77 */     return findRootMostMatchingPath(relativePath, Predicate.TRUE);
/*     */   }
/*     */   
/*     */   public T rootMostValue(Path relativePath) {
/*  81 */     return (T)rootMostValueMatching(relativePath, Predicate.TRUE);
/*     */   }
/*     */   
/*     */   public T rootMostValueMatching(Path relativePath, Predicate<? super T> predicate) {
/*  85 */     if ((this.value != null) && (predicate.evaluate(this.value))) {
/*  86 */       return (T)this.value;
/*     */     }
/*  88 */     ImmutableTree<T> currentTree = this;
/*  89 */     for (ChildKey key : relativePath) {
/*  90 */       currentTree = (ImmutableTree)currentTree.children.get(key);
/*  91 */       if (currentTree == null)
/*  92 */         return null;
/*  93 */       if ((currentTree.value != null) && (predicate.evaluate(currentTree.value))) {
/*  94 */         return (T)currentTree.value;
/*     */       }
/*     */     }
/*  97 */     return null;
/*     */   }
/*     */   
/*     */   public T leafMostValue(Path relativePath)
/*     */   {
/* 102 */     return (T)leafMostValueMatching(relativePath, Predicate.TRUE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T leafMostValueMatching(Path path, Predicate<? super T> predicate)
/*     */   {
/* 113 */     T currentValue = (this.value != null) && (predicate.evaluate(this.value)) ? this.value : null;
/* 114 */     ImmutableTree<T> currentTree = this;
/* 115 */     for (ChildKey key : path) {
/* 116 */       currentTree = (ImmutableTree)currentTree.children.get(key);
/* 117 */       if (currentTree == null) {
/* 118 */         return currentValue;
/*     */       }
/* 120 */       if ((currentTree.value != null) && (predicate.evaluate(currentTree.value))) {
/* 121 */         currentValue = currentTree.value;
/*     */       }
/*     */     }
/*     */     
/* 125 */     return currentValue;
/*     */   }
/*     */   
/*     */   public boolean containsMatchingValue(Predicate<? super T> predicate) {
/* 129 */     if ((this.value != null) && (predicate.evaluate(this.value))) {
/* 130 */       return true;
/*     */     }
/* 132 */     for (Map.Entry<ChildKey, ImmutableTree<T>> subtree : this.children) {
/* 133 */       if (((ImmutableTree)subtree.getValue()).containsMatchingValue(predicate)) {
/* 134 */         return true;
/*     */       }
/*     */     }
/* 137 */     return false;
/*     */   }
/*     */   
/*     */   public ImmutableTree<T> getChild(ChildKey child)
/*     */   {
/* 142 */     ImmutableTree<T> childTree = (ImmutableTree)this.children.get(child);
/* 143 */     if (childTree != null) {
/* 144 */       return childTree;
/*     */     }
/* 146 */     return emptyInstance();
/*     */   }
/*     */   
/*     */   public ImmutableTree<T> subtree(Path relativePath)
/*     */   {
/* 151 */     if (relativePath.isEmpty()) {
/* 152 */       return this;
/*     */     }
/* 154 */     ChildKey front = relativePath.getFront();
/* 155 */     ImmutableTree<T> childTree = (ImmutableTree)this.children.get(front);
/* 156 */     if (childTree != null) {
/* 157 */       return childTree.subtree(relativePath.popFront());
/*     */     }
/* 159 */     return emptyInstance();
/*     */   }
/*     */   
/*     */ 
/*     */   public ImmutableTree<T> set(Path relativePath, T value)
/*     */   {
/* 165 */     if (relativePath.isEmpty()) {
/* 166 */       return new ImmutableTree(value, this.children);
/*     */     }
/* 168 */     ChildKey front = relativePath.getFront();
/* 169 */     ImmutableTree<T> child = (ImmutableTree)this.children.get(front);
/* 170 */     if (child == null) {
/* 171 */       child = emptyInstance();
/*     */     }
/* 173 */     ImmutableTree<T> newChild = child.set(relativePath.popFront(), value);
/* 174 */     ImmutableSortedMap<ChildKey, ImmutableTree<T>> newChildren = this.children.insert(front, newChild);
/* 175 */     return new ImmutableTree(this.value, newChildren);
/*     */   }
/*     */   
/*     */   public ImmutableTree<T> remove(Path relativePath)
/*     */   {
/* 180 */     if (relativePath.isEmpty()) {
/* 181 */       if (this.children.isEmpty()) {
/* 182 */         return emptyInstance();
/*     */       }
/* 184 */       return new ImmutableTree(null, this.children);
/*     */     }
/*     */     
/* 187 */     ChildKey front = relativePath.getFront();
/* 188 */     ImmutableTree<T> child = (ImmutableTree)this.children.get(front);
/* 189 */     if (child != null) {
/* 190 */       ImmutableTree<T> newChild = child.remove(relativePath.popFront());
/*     */       ImmutableSortedMap<ChildKey, ImmutableTree<T>> newChildren;
/* 192 */       ImmutableSortedMap<ChildKey, ImmutableTree<T>> newChildren; if (newChild.isEmpty()) {
/* 193 */         newChildren = this.children.remove(front);
/*     */       } else {
/* 195 */         newChildren = this.children.insert(front, newChild);
/*     */       }
/* 197 */       if ((this.value == null) && (newChildren.isEmpty())) {
/* 198 */         return emptyInstance();
/*     */       }
/* 200 */       return new ImmutableTree(this.value, newChildren);
/*     */     }
/*     */     
/* 203 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public T get(Path relativePath)
/*     */   {
/* 209 */     if (relativePath.isEmpty()) {
/* 210 */       return (T)this.value;
/*     */     }
/* 212 */     ChildKey front = relativePath.getFront();
/* 213 */     ImmutableTree<T> child = (ImmutableTree)this.children.get(front);
/* 214 */     if (child != null) {
/* 215 */       return (T)child.get(relativePath.popFront());
/*     */     }
/* 217 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public ImmutableTree<T> setTree(Path relativePath, ImmutableTree<T> newTree)
/*     */   {
/* 223 */     if (relativePath.isEmpty()) {
/* 224 */       return newTree;
/*     */     }
/* 226 */     ChildKey front = relativePath.getFront();
/* 227 */     ImmutableTree<T> child = (ImmutableTree)this.children.get(front);
/* 228 */     if (child == null) {
/* 229 */       child = emptyInstance();
/*     */     }
/* 231 */     ImmutableTree<T> newChild = child.setTree(relativePath.popFront(), newTree);
/*     */     ImmutableSortedMap<ChildKey, ImmutableTree<T>> newChildren;
/* 233 */     ImmutableSortedMap<ChildKey, ImmutableTree<T>> newChildren; if (newChild.isEmpty()) {
/* 234 */       newChildren = this.children.remove(front);
/*     */     } else {
/* 236 */       newChildren = this.children.insert(front, newChild);
/*     */     }
/* 238 */     return new ImmutableTree(this.value, newChildren);
/*     */   }
/*     */   
/*     */   public void foreach(TreeVisitor<T, Void> visitor)
/*     */   {
/* 243 */     fold(Path.getEmptyPath(), visitor, null);
/*     */   }
/*     */   
/*     */   public <R> R fold(R accum, TreeVisitor<? super T, R> visitor) {
/* 247 */     return (R)fold(Path.getEmptyPath(), visitor, accum);
/*     */   }
/*     */   
/*     */   private <R> R fold(Path relativePath, TreeVisitor<? super T, R> visitor, R accum) {
/* 251 */     for (Map.Entry<ChildKey, ImmutableTree<T>> subtree : this.children) {
/* 252 */       accum = ((ImmutableTree)subtree.getValue()).fold(relativePath.child((ChildKey)subtree.getKey()), visitor, accum);
/*     */     }
/* 254 */     if (this.value != null) {
/* 255 */       accum = visitor.onNodeValue(relativePath, this.value, accum);
/*     */     }
/* 257 */     return accum;
/*     */   }
/*     */   
/*     */   public Collection<T> values() {
/* 261 */     final ArrayList<T> list = new ArrayList();
/* 262 */     foreach(new TreeVisitor()
/*     */     {
/*     */       public Void onNodeValue(Path relativePath, T value, Void accum) {
/* 265 */         list.add(value);
/* 266 */         return null;
/*     */       }
/* 268 */     });
/* 269 */     return list;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<Map.Entry<Path, T>> iterator()
/*     */   {
/* 277 */     final List<Map.Entry<Path, T>> list = new ArrayList();
/* 278 */     foreach(new TreeVisitor()
/*     */     {
/*     */       public Void onNodeValue(Path relativePath, T value, Void accum) {
/* 281 */         list.add(new AbstractMap.SimpleImmutableEntry(relativePath, value));
/* 282 */         return null;
/*     */       }
/* 284 */     });
/* 285 */     return list.iterator();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 290 */     StringBuilder builder = new StringBuilder();
/* 291 */     builder.append("ImmutableTree { value=");
/* 292 */     builder.append(getValue());
/* 293 */     builder.append(", children={");
/* 294 */     for (Map.Entry<ChildKey, ImmutableTree<T>> child : this.children) {
/* 295 */       builder.append(((ChildKey)child.getKey()).asString());
/* 296 */       builder.append("=");
/* 297 */       builder.append(child.getValue());
/*     */     }
/* 299 */     builder.append("} }");
/* 300 */     return builder.toString();
/*     */   }
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/* 305 */     if (this == o) return true;
/* 306 */     if ((o == null) || (getClass() != o.getClass())) { return false;
/*     */     }
/* 308 */     ImmutableTree that = (ImmutableTree)o;
/*     */     
/* 310 */     if (this.children != null ? !this.children.equals(that.children) : that.children != null) return false;
/* 311 */     if (this.value != null ? !this.value.equals(that.value) : that.value != null) { return false;
/*     */     }
/* 313 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 318 */     int result = this.value != null ? this.value.hashCode() : 0;
/* 319 */     result = 31 * result + (this.children != null ? this.children.hashCode() : 0);
/* 320 */     return result;
/*     */   }
/*     */   
/*     */   public static abstract interface TreeVisitor<T, R>
/*     */   {
/*     */     public abstract R onNodeValue(Path paramPath, T paramT, R paramR);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/core/utilities/ImmutableTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */