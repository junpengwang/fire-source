/*     */ package com.shaded.fasterxml.jackson.databind.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ArrayBuilders
/*     */ {
/*  15 */   private BooleanBuilder _booleanBuilder = null;
/*     */   
/*     */ 
/*     */ 
/*  19 */   private ByteBuilder _byteBuilder = null;
/*  20 */   private ShortBuilder _shortBuilder = null;
/*  21 */   private IntBuilder _intBuilder = null;
/*  22 */   private LongBuilder _longBuilder = null;
/*     */   
/*  24 */   private FloatBuilder _floatBuilder = null;
/*  25 */   private DoubleBuilder _doubleBuilder = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public BooleanBuilder getBooleanBuilder()
/*     */   {
/*  31 */     if (this._booleanBuilder == null) {
/*  32 */       this._booleanBuilder = new BooleanBuilder();
/*     */     }
/*  34 */     return this._booleanBuilder;
/*     */   }
/*     */   
/*     */   public ByteBuilder getByteBuilder()
/*     */   {
/*  39 */     if (this._byteBuilder == null) {
/*  40 */       this._byteBuilder = new ByteBuilder();
/*     */     }
/*  42 */     return this._byteBuilder;
/*     */   }
/*     */   
/*     */   public ShortBuilder getShortBuilder() {
/*  46 */     if (this._shortBuilder == null) {
/*  47 */       this._shortBuilder = new ShortBuilder();
/*     */     }
/*  49 */     return this._shortBuilder;
/*     */   }
/*     */   
/*     */   public IntBuilder getIntBuilder() {
/*  53 */     if (this._intBuilder == null) {
/*  54 */       this._intBuilder = new IntBuilder();
/*     */     }
/*  56 */     return this._intBuilder;
/*     */   }
/*     */   
/*     */   public LongBuilder getLongBuilder() {
/*  60 */     if (this._longBuilder == null) {
/*  61 */       this._longBuilder = new LongBuilder();
/*     */     }
/*  63 */     return this._longBuilder;
/*     */   }
/*     */   
/*     */   public FloatBuilder getFloatBuilder()
/*     */   {
/*  68 */     if (this._floatBuilder == null) {
/*  69 */       this._floatBuilder = new FloatBuilder();
/*     */     }
/*  71 */     return this._floatBuilder;
/*     */   }
/*     */   
/*     */   public DoubleBuilder getDoubleBuilder() {
/*  75 */     if (this._doubleBuilder == null) {
/*  76 */       this._doubleBuilder = new DoubleBuilder();
/*     */     }
/*  78 */     return this._doubleBuilder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final class BooleanBuilder
/*     */     extends PrimitiveArrayBuilder<boolean[]>
/*     */   {
/*     */     public final boolean[] _constructArray(int paramInt)
/*     */     {
/*  92 */       return new boolean[paramInt];
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class ByteBuilder extends PrimitiveArrayBuilder<byte[]>
/*     */   {
/*     */     public final byte[] _constructArray(int paramInt)
/*     */     {
/* 100 */       return new byte[paramInt];
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class ShortBuilder extends PrimitiveArrayBuilder<short[]>
/*     */   {
/*     */     public final short[] _constructArray(int paramInt) {
/* 107 */       return new short[paramInt];
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class IntBuilder extends PrimitiveArrayBuilder<int[]>
/*     */   {
/*     */     public final int[] _constructArray(int paramInt) {
/* 114 */       return new int[paramInt];
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class LongBuilder extends PrimitiveArrayBuilder<long[]>
/*     */   {
/*     */     public final long[] _constructArray(int paramInt) {
/* 121 */       return new long[paramInt];
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class FloatBuilder extends PrimitiveArrayBuilder<float[]>
/*     */   {
/*     */     public final float[] _constructArray(int paramInt)
/*     */     {
/* 129 */       return new float[paramInt];
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class DoubleBuilder extends PrimitiveArrayBuilder<double[]>
/*     */   {
/*     */     public final double[] _constructArray(int paramInt) {
/* 136 */       return new double[paramInt];
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
/*     */   public static Object getArrayComparator(final Object paramObject)
/*     */   {
/* 157 */     final int i = Array.getLength(paramObject);
/* 158 */     Class localClass = paramObject.getClass();
/* 159 */     new Object()
/*     */     {
/*     */       public boolean equals(Object paramAnonymousObject) {
/* 162 */         if (paramAnonymousObject == this) return true;
/* 163 */         if ((paramAnonymousObject == null) || (paramAnonymousObject.getClass() != this.val$defaultValueType)) {
/* 164 */           return false;
/*     */         }
/* 166 */         if (Array.getLength(paramAnonymousObject) != i) { return false;
/*     */         }
/* 168 */         for (int i = 0; i < i; i++) {
/* 169 */           Object localObject1 = Array.get(paramObject, i);
/* 170 */           Object localObject2 = Array.get(paramAnonymousObject, i);
/* 171 */           if ((localObject1 != localObject2) && 
/* 172 */             (localObject1 != null) && 
/* 173 */             (!localObject1.equals(localObject2))) {
/* 174 */             return false;
/*     */           }
/*     */         }
/*     */         
/* 178 */         return true;
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public static <T> HashSet<T> arrayToSet(T[] paramArrayOfT)
/*     */   {
/* 185 */     HashSet localHashSet = new HashSet();
/* 186 */     if (paramArrayOfT != null) {
/* 187 */       for (T ? : paramArrayOfT) {
/* 188 */         localHashSet.add(?);
/*     */       }
/*     */     }
/* 191 */     return localHashSet;
/*     */   }
/*     */   
/*     */   public static <T> ArrayList<T> arrayToList(T[] paramArrayOfT)
/*     */   {
/* 196 */     ArrayList localArrayList = new ArrayList();
/* 197 */     if (paramArrayOfT != null) {
/* 198 */       for (T ? : paramArrayOfT) {
/* 199 */         localArrayList.add(?);
/*     */       }
/*     */     }
/* 202 */     return localArrayList;
/*     */   }
/*     */   
/*     */   public static <T> HashSet<T> setAndArray(Set<T> paramSet, T[] paramArrayOfT)
/*     */   {
/* 207 */     HashSet localHashSet = new HashSet();
/* 208 */     if (paramSet != null) {
/* 209 */       localHashSet.addAll(paramSet);
/*     */     }
/* 211 */     if (paramArrayOfT != null) {
/* 212 */       for (T ? : paramArrayOfT) {
/* 213 */         localHashSet.add(?);
/*     */       }
/*     */     }
/* 216 */     return localHashSet;
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
/*     */   public static <T> List<T> addToList(List<T> paramList, T paramT)
/*     */   {
/* 233 */     if (paramList == null) {
/* 234 */       paramList = new ArrayList();
/*     */     }
/* 236 */     paramList.add(paramT);
/* 237 */     return paramList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> T[] insertInList(T[] paramArrayOfT, T paramT)
/*     */   {
/* 247 */     int i = paramArrayOfT.length;
/*     */     
/* 249 */     Object[] arrayOfObject = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i + 1);
/* 250 */     if (i > 0) {
/* 251 */       System.arraycopy(paramArrayOfT, 0, arrayOfObject, 1, i);
/*     */     }
/* 253 */     arrayOfObject[0] = paramT;
/* 254 */     return arrayOfObject;
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
/*     */   public static <T> T[] insertInListNoDup(T[] paramArrayOfT, T paramT)
/*     */   {
/* 268 */     int i = paramArrayOfT.length;
/*     */     
/*     */ 
/* 271 */     for (int j = 0; j < i; j++) {
/* 272 */       if (paramArrayOfT[j] == paramT)
/*     */       {
/* 274 */         if (j == 0) {
/* 275 */           return paramArrayOfT;
/*     */         }
/*     */         
/* 278 */         Object[] arrayOfObject2 = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
/* 279 */         System.arraycopy(paramArrayOfT, 0, arrayOfObject2, 1, j);
/* 280 */         arrayOfObject2[0] = paramT;
/* 281 */         j++;
/* 282 */         int k = i - j;
/* 283 */         if (k > 0) {
/* 284 */           System.arraycopy(paramArrayOfT, j, arrayOfObject2, j, k);
/*     */         }
/* 286 */         return arrayOfObject2;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 291 */     Object[] arrayOfObject1 = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i + 1);
/* 292 */     if (i > 0) {
/* 293 */       System.arraycopy(paramArrayOfT, 0, arrayOfObject1, 1, i);
/*     */     }
/* 295 */     arrayOfObject1[0] = paramT;
/* 296 */     return arrayOfObject1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> Iterator<T> arrayAsIterator(T[] paramArrayOfT)
/*     */   {
/* 304 */     return new ArrayIterator(paramArrayOfT);
/*     */   }
/*     */   
/*     */   public static <T> Iterable<T> arrayAsIterable(T[] paramArrayOfT)
/*     */   {
/* 309 */     return new ArrayIterator(paramArrayOfT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class ArrayIterator<T>
/*     */     implements Iterator<T>, Iterable<T>
/*     */   {
/*     */     private final T[] _array;
/*     */     
/*     */ 
/*     */ 
/*     */     private int _index;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public ArrayIterator(T[] paramArrayOfT)
/*     */     {
/* 330 */       this._array = paramArrayOfT;
/* 331 */       this._index = 0;
/*     */     }
/*     */     
/*     */     public boolean hasNext()
/*     */     {
/* 336 */       return this._index < this._array.length;
/*     */     }
/*     */     
/*     */ 
/*     */     public T next()
/*     */     {
/* 342 */       if (this._index >= this._array.length) {
/* 343 */         throw new NoSuchElementException();
/*     */       }
/* 345 */       return (T)this._array[(this._index++)];
/*     */     }
/*     */     
/*     */     public void remove()
/*     */     {
/* 350 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Iterator<T> iterator()
/*     */     {
/* 355 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/ArrayBuilders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */