/*     */ package com.shaded.fasterxml.jackson.databind.deser.impl;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.databind.JsonDeserializer;
/*     */ import com.shaded.fasterxml.jackson.databind.deser.SettableBeanProperty;
/*     */ import com.shaded.fasterxml.jackson.databind.util.NameTransformer;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BeanPropertyMap
/*     */   implements Iterable<SettableBeanProperty>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Bucket[] _buckets;
/*     */   private final int _hashMask;
/*     */   private final int _size;
/*  39 */   private int _nextBucketIndex = 0;
/*     */   
/*     */   public BeanPropertyMap(Collection<SettableBeanProperty> paramCollection)
/*     */   {
/*  43 */     this._size = paramCollection.size();
/*  44 */     int i = findSize(this._size);
/*  45 */     this._hashMask = (i - 1);
/*  46 */     Bucket[] arrayOfBucket = new Bucket[i];
/*  47 */     for (SettableBeanProperty localSettableBeanProperty : paramCollection) {
/*  48 */       String str = localSettableBeanProperty.getName();
/*  49 */       int j = str.hashCode() & this._hashMask;
/*  50 */       arrayOfBucket[j] = new Bucket(arrayOfBucket[j], str, localSettableBeanProperty, this._nextBucketIndex++);
/*     */     }
/*  52 */     this._buckets = arrayOfBucket;
/*     */   }
/*     */   
/*     */   private BeanPropertyMap(Bucket[] paramArrayOfBucket, int paramInt1, int paramInt2)
/*     */   {
/*  57 */     this._buckets = paramArrayOfBucket;
/*  58 */     this._size = paramInt1;
/*  59 */     this._hashMask = (paramArrayOfBucket.length - 1);
/*  60 */     this._nextBucketIndex = paramInt2;
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
/*     */   public BeanPropertyMap withProperty(SettableBeanProperty paramSettableBeanProperty)
/*     */   {
/*  75 */     int i = this._buckets.length;
/*  76 */     Bucket[] arrayOfBucket = new Bucket[i];
/*  77 */     System.arraycopy(this._buckets, 0, arrayOfBucket, 0, i);
/*  78 */     String str = paramSettableBeanProperty.getName();
/*     */     
/*  80 */     SettableBeanProperty localSettableBeanProperty = find(paramSettableBeanProperty.getName());
/*  81 */     if (localSettableBeanProperty == null)
/*     */     {
/*     */ 
/*     */ 
/*  85 */       int j = str.hashCode() & this._hashMask;
/*  86 */       arrayOfBucket[j] = new Bucket(arrayOfBucket[j], str, paramSettableBeanProperty, this._nextBucketIndex++);
/*     */       
/*  88 */       return new BeanPropertyMap(arrayOfBucket, this._size + 1, this._nextBucketIndex);
/*     */     }
/*     */     
/*  91 */     BeanPropertyMap localBeanPropertyMap = new BeanPropertyMap(arrayOfBucket, i, this._nextBucketIndex);
/*  92 */     localBeanPropertyMap.replace(paramSettableBeanProperty);
/*  93 */     return localBeanPropertyMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanPropertyMap renameAll(NameTransformer paramNameTransformer)
/*     */   {
/* 102 */     if ((paramNameTransformer == null) || (paramNameTransformer == NameTransformer.NOP)) {
/* 103 */       return this;
/*     */     }
/* 105 */     Iterator localIterator = iterator();
/* 106 */     ArrayList localArrayList = new ArrayList();
/* 107 */     while (localIterator.hasNext()) {
/* 108 */       SettableBeanProperty localSettableBeanProperty = (SettableBeanProperty)localIterator.next();
/* 109 */       String str = paramNameTransformer.transform(localSettableBeanProperty.getName());
/* 110 */       localSettableBeanProperty = localSettableBeanProperty.withName(str);
/* 111 */       JsonDeserializer localJsonDeserializer1 = localSettableBeanProperty.getValueDeserializer();
/* 112 */       if (localJsonDeserializer1 != null)
/*     */       {
/* 114 */         JsonDeserializer localJsonDeserializer2 = localJsonDeserializer1.unwrappingDeserializer(paramNameTransformer);
/*     */         
/* 116 */         if (localJsonDeserializer2 != localJsonDeserializer1) {
/* 117 */           localSettableBeanProperty = localSettableBeanProperty.withValueDeserializer(localJsonDeserializer2);
/*     */         }
/*     */       }
/* 120 */       localArrayList.add(localSettableBeanProperty);
/*     */     }
/*     */     
/* 123 */     return new BeanPropertyMap(localArrayList);
/*     */   }
/*     */   
/*     */ 
/*     */   public BeanPropertyMap assignIndexes()
/*     */   {
/* 129 */     int i = 0;
/* 130 */     for (Bucket localBucket : this._buckets) {
/* 131 */       while (localBucket != null) {
/* 132 */         localBucket.value.assignIndex(i++);
/* 133 */         localBucket = localBucket.next;
/*     */       }
/*     */     }
/* 136 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   private static final int findSize(int paramInt)
/*     */   {
/* 142 */     int i = paramInt <= 32 ? paramInt + paramInt : paramInt + (paramInt >> 2);
/* 143 */     int j = 2;
/* 144 */     while (j < i) {
/* 145 */       j += j;
/*     */     }
/* 147 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 159 */     StringBuilder localStringBuilder = new StringBuilder();
/* 160 */     localStringBuilder.append("Properties=[");
/* 161 */     int i = 0;
/* 162 */     for (SettableBeanProperty localSettableBeanProperty : getPropertiesInInsertionOrder())
/* 163 */       if (localSettableBeanProperty != null)
/*     */       {
/*     */ 
/* 166 */         if (i++ > 0) {
/* 167 */           localStringBuilder.append(", ");
/*     */         }
/* 169 */         localStringBuilder.append(localSettableBeanProperty.getName());
/* 170 */         localStringBuilder.append('(');
/* 171 */         localStringBuilder.append(localSettableBeanProperty.getType());
/* 172 */         localStringBuilder.append(')');
/*     */       }
/* 174 */     localStringBuilder.append(']');
/* 175 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<SettableBeanProperty> iterator()
/*     */   {
/* 183 */     return new IteratorImpl(this._buckets);
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
/*     */   public SettableBeanProperty[] getPropertiesInInsertionOrder()
/*     */   {
/* 196 */     int i = this._nextBucketIndex;
/* 197 */     SettableBeanProperty[] arrayOfSettableBeanProperty = new SettableBeanProperty[i];
/* 198 */     for (Bucket localBucket1 : this._buckets) {
/* 199 */       for (Bucket localBucket2 = localBucket1; localBucket2 != null; localBucket2 = localBucket2.next) {
/* 200 */         arrayOfSettableBeanProperty[localBucket2.index] = localBucket2.value;
/*     */       }
/*     */     }
/* 203 */     return arrayOfSettableBeanProperty;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 212 */     return this._size;
/*     */   }
/*     */   
/*     */   public SettableBeanProperty find(String paramString) {
/* 216 */     int i = paramString.hashCode() & this._hashMask;
/* 217 */     Bucket localBucket = this._buckets[i];
/*     */     
/* 219 */     if (localBucket == null) {
/* 220 */       return null;
/*     */     }
/*     */     
/* 223 */     if (localBucket.key == paramString) {
/* 224 */       return localBucket.value;
/*     */     }
/* 226 */     while ((localBucket = localBucket.next) != null) {
/* 227 */       if (localBucket.key == paramString) {
/* 228 */         return localBucket.value;
/*     */       }
/*     */     }
/*     */     
/* 232 */     return _findWithEquals(paramString, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void replace(SettableBeanProperty paramSettableBeanProperty)
/*     */   {
/* 242 */     String str = paramSettableBeanProperty.getName();
/* 243 */     int i = str.hashCode() & this._buckets.length - 1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 248 */     Bucket localBucket1 = null;
/* 249 */     int j = -1;
/*     */     
/* 251 */     for (Bucket localBucket2 = this._buckets[i]; localBucket2 != null; localBucket2 = localBucket2.next)
/*     */     {
/* 253 */       if ((j < 0) && (localBucket2.key.equals(str))) {
/* 254 */         j = localBucket2.index;
/*     */       } else {
/* 256 */         localBucket1 = new Bucket(localBucket1, localBucket2.key, localBucket2.value, localBucket2.index);
/*     */       }
/*     */     }
/*     */     
/* 260 */     if (j < 0) {
/* 261 */       throw new NoSuchElementException("No entry '" + paramSettableBeanProperty + "' found, can't replace");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 266 */     this._buckets[i] = new Bucket(localBucket1, str, paramSettableBeanProperty, j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void remove(SettableBeanProperty paramSettableBeanProperty)
/*     */   {
/* 276 */     String str = paramSettableBeanProperty.getName();
/* 277 */     int i = str.hashCode() & this._buckets.length - 1;
/* 278 */     Bucket localBucket1 = null;
/* 279 */     int j = 0;
/*     */     
/* 281 */     for (Bucket localBucket2 = this._buckets[i]; localBucket2 != null; localBucket2 = localBucket2.next)
/*     */     {
/* 283 */       if ((j == 0) && (localBucket2.key.equals(str))) {
/* 284 */         j = 1;
/*     */       } else {
/* 286 */         localBucket1 = new Bucket(localBucket1, localBucket2.key, localBucket2.value, localBucket2.index);
/*     */       }
/*     */     }
/* 289 */     if (j == 0) {
/* 290 */       throw new NoSuchElementException("No entry '" + paramSettableBeanProperty + "' found, can't remove");
/*     */     }
/* 292 */     this._buckets[i] = localBucket1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private SettableBeanProperty _findWithEquals(String paramString, int paramInt)
/*     */   {
/* 303 */     Bucket localBucket = this._buckets[paramInt];
/* 304 */     while (localBucket != null) {
/* 305 */       if (paramString.equals(localBucket.key)) {
/* 306 */         return localBucket.value;
/*     */       }
/* 308 */       localBucket = localBucket.next;
/*     */     }
/* 310 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class Bucket
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */     public final Bucket next;
/*     */     
/*     */ 
/*     */     public final String key;
/*     */     
/*     */ 
/*     */     public final SettableBeanProperty value;
/*     */     
/*     */ 
/*     */     public final int index;
/*     */     
/*     */ 
/*     */     public Bucket(Bucket paramBucket, String paramString, SettableBeanProperty paramSettableBeanProperty, int paramInt)
/*     */     {
/* 335 */       this.next = paramBucket;
/* 336 */       this.key = paramString;
/* 337 */       this.value = paramSettableBeanProperty;
/* 338 */       this.index = paramInt;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class IteratorImpl
/*     */     implements Iterator<SettableBeanProperty>
/*     */   {
/*     */     private final BeanPropertyMap.Bucket[] _buckets;
/*     */     
/*     */ 
/*     */ 
/*     */     private BeanPropertyMap.Bucket _currentBucket;
/*     */     
/*     */ 
/*     */     private int _nextBucketIndex;
/*     */     
/*     */ 
/*     */ 
/*     */     public IteratorImpl(BeanPropertyMap.Bucket[] paramArrayOfBucket)
/*     */     {
/* 361 */       this._buckets = paramArrayOfBucket;
/*     */       
/* 363 */       int i = 0;
/* 364 */       for (int j = this._buckets.length; i < j;) {
/* 365 */         BeanPropertyMap.Bucket localBucket = this._buckets[(i++)];
/* 366 */         if (localBucket != null) {
/* 367 */           this._currentBucket = localBucket;
/* 368 */           break;
/*     */         }
/*     */       }
/* 371 */       this._nextBucketIndex = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext()
/*     */     {
/* 376 */       return this._currentBucket != null;
/*     */     }
/*     */     
/*     */ 
/*     */     public SettableBeanProperty next()
/*     */     {
/* 382 */       BeanPropertyMap.Bucket localBucket1 = this._currentBucket;
/* 383 */       if (localBucket1 == null) {
/* 384 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 387 */       BeanPropertyMap.Bucket localBucket2 = localBucket1.next;
/* 388 */       while ((localBucket2 == null) && (this._nextBucketIndex < this._buckets.length)) {
/* 389 */         localBucket2 = this._buckets[(this._nextBucketIndex++)];
/*     */       }
/* 391 */       this._currentBucket = localBucket2;
/* 392 */       return localBucket1.value;
/*     */     }
/*     */     
/*     */     public void remove()
/*     */     {
/* 397 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/deser/impl/BeanPropertyMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */