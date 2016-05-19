/*     */ package com.shaded.fasterxml.jackson.core.sym;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.util.InternCache;
/*     */ import java.util.Arrays;
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
/*     */ public final class CharsToNameCanonicalizer
/*     */ {
/*     */   public static final int HASH_MULT = 33;
/*     */   protected static final int DEFAULT_TABLE_SIZE = 64;
/*     */   protected static final int MAX_TABLE_SIZE = 65536;
/*     */   static final int MAX_ENTRIES_FOR_REUSE = 12000;
/*     */   static final int MAX_COLL_CHAIN_LENGTH = 255;
/*     */   static final int MAX_COLL_CHAIN_FOR_REUSE = 63;
/*  97 */   static final CharsToNameCanonicalizer sBootstrapSymbolTable = new CharsToNameCanonicalizer();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected CharsToNameCanonicalizer _parent;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int _hashSeed;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final boolean _intern;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final boolean _canonicalize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String[] _symbols;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Bucket[] _buckets;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int _size;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int _sizeThreshold;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int _indexMask;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int _longestCollisionList;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean _dirty;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CharsToNameCanonicalizer createRoot()
/*     */   {
/* 217 */     long l = System.currentTimeMillis();
/*     */     
/* 219 */     int i = (int)l + (int)(l >>> 32) | 0x1;
/* 220 */     return createRoot(i);
/*     */   }
/*     */   
/*     */   protected static CharsToNameCanonicalizer createRoot(int paramInt) {
/* 224 */     return sBootstrapSymbolTable.makeOrphan(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private CharsToNameCanonicalizer()
/*     */   {
/* 236 */     this._canonicalize = true;
/* 237 */     this._intern = true;
/*     */     
/* 239 */     this._dirty = true;
/* 240 */     this._hashSeed = 0;
/* 241 */     this._longestCollisionList = 0;
/* 242 */     initTables(64);
/*     */   }
/*     */   
/*     */   private void initTables(int paramInt)
/*     */   {
/* 247 */     this._symbols = new String[paramInt];
/* 248 */     this._buckets = new Bucket[paramInt >> 1];
/*     */     
/* 250 */     this._indexMask = (paramInt - 1);
/* 251 */     this._size = 0;
/* 252 */     this._longestCollisionList = 0;
/*     */     
/* 254 */     this._sizeThreshold = _thresholdSize(paramInt);
/*     */   }
/*     */   
/*     */   private static int _thresholdSize(int paramInt) {
/* 258 */     return paramInt - (paramInt >> 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private CharsToNameCanonicalizer(CharsToNameCanonicalizer paramCharsToNameCanonicalizer, boolean paramBoolean1, boolean paramBoolean2, String[] paramArrayOfString, Bucket[] paramArrayOfBucket, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 269 */     this._parent = paramCharsToNameCanonicalizer;
/* 270 */     this._canonicalize = paramBoolean1;
/* 271 */     this._intern = paramBoolean2;
/*     */     
/* 273 */     this._symbols = paramArrayOfString;
/* 274 */     this._buckets = paramArrayOfBucket;
/* 275 */     this._size = paramInt1;
/* 276 */     this._hashSeed = paramInt2;
/*     */     
/* 278 */     int i = paramArrayOfString.length;
/* 279 */     this._sizeThreshold = _thresholdSize(i);
/* 280 */     this._indexMask = (i - 1);
/* 281 */     this._longestCollisionList = paramInt3;
/*     */     
/*     */ 
/* 284 */     this._dirty = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharsToNameCanonicalizer makeChild(boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*     */     String[] arrayOfString;
/*     */     
/*     */ 
/*     */ 
/*     */     Bucket[] arrayOfBucket;
/*     */     
/*     */ 
/*     */ 
/*     */     int i;
/*     */     
/*     */ 
/*     */ 
/*     */     int j;
/*     */     
/*     */ 
/*     */ 
/*     */     int k;
/*     */     
/*     */ 
/*     */ 
/* 312 */     synchronized (this) {
/* 313 */       arrayOfString = this._symbols;
/* 314 */       arrayOfBucket = this._buckets;
/* 315 */       i = this._size;
/* 316 */       j = this._hashSeed;
/* 317 */       k = this._longestCollisionList;
/*     */     }
/*     */     
/* 320 */     return new CharsToNameCanonicalizer(this, paramBoolean1, paramBoolean2, arrayOfString, arrayOfBucket, i, j, k);
/*     */   }
/*     */   
/*     */ 
/*     */   private CharsToNameCanonicalizer makeOrphan(int paramInt)
/*     */   {
/* 326 */     return new CharsToNameCanonicalizer(null, true, true, this._symbols, this._buckets, this._size, paramInt, this._longestCollisionList);
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
/*     */   private void mergeChild(CharsToNameCanonicalizer paramCharsToNameCanonicalizer)
/*     */   {
/* 345 */     if ((paramCharsToNameCanonicalizer.size() > 12000) || (paramCharsToNameCanonicalizer._longestCollisionList > 63))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 350 */       synchronized (this) {
/* 351 */         initTables(64);
/*     */         
/*     */ 
/* 354 */         this._dirty = false;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 359 */       if (paramCharsToNameCanonicalizer.size() <= size()) {
/* 360 */         return;
/*     */       }
/*     */       
/* 363 */       synchronized (this) {
/* 364 */         this._symbols = paramCharsToNameCanonicalizer._symbols;
/* 365 */         this._buckets = paramCharsToNameCanonicalizer._buckets;
/* 366 */         this._size = paramCharsToNameCanonicalizer._size;
/* 367 */         this._sizeThreshold = paramCharsToNameCanonicalizer._sizeThreshold;
/* 368 */         this._indexMask = paramCharsToNameCanonicalizer._indexMask;
/* 369 */         this._longestCollisionList = paramCharsToNameCanonicalizer._longestCollisionList;
/*     */         
/*     */ 
/* 372 */         this._dirty = false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void release()
/*     */   {
/* 380 */     if (!maybeDirty()) {
/* 381 */       return;
/*     */     }
/* 383 */     if (this._parent != null) {
/* 384 */       this._parent.mergeChild(this);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 389 */       this._dirty = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 399 */     return this._size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 408 */   public int bucketCount() { return this._symbols.length; }
/*     */   
/* 410 */   public boolean maybeDirty() { return this._dirty; }
/*     */   
/* 412 */   public int hashSeed() { return this._hashSeed; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int collisionCount()
/*     */   {
/* 423 */     int i = 0;
/*     */     
/* 425 */     for (Bucket localBucket : this._buckets) {
/* 426 */       if (localBucket != null) {
/* 427 */         i += localBucket.length();
/*     */       }
/*     */     }
/* 430 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int maxCollisionLength()
/*     */   {
/* 442 */     return this._longestCollisionList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String findSymbol(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 453 */     if (paramInt2 < 1) {
/* 454 */       return "";
/*     */     }
/* 456 */     if (!this._canonicalize) {
/* 457 */       return new String(paramArrayOfChar, paramInt1, paramInt2);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 465 */     int i = _hashToIndex(paramInt3);
/* 466 */     String str = this._symbols[i];
/*     */     
/*     */ 
/* 469 */     if (str != null)
/*     */     {
/* 471 */       if (str.length() == paramInt2) {
/* 472 */         int j = 0;
/*     */         do {
/* 474 */           if (str.charAt(j) != paramArrayOfChar[(paramInt1 + j)]) {
/*     */             break;
/*     */           }
/* 477 */           j++; } while (j < paramInt2);
/*     */         
/* 479 */         if (j == paramInt2) {
/* 480 */           return str;
/*     */         }
/*     */       }
/*     */       
/* 484 */       localObject = this._buckets[(i >> 1)];
/* 485 */       if (localObject != null) {
/* 486 */         str = ((Bucket)localObject).find(paramArrayOfChar, paramInt1, paramInt2);
/* 487 */         if (str != null) {
/* 488 */           return str;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 493 */     if (!this._dirty) {
/* 494 */       copyArrays();
/* 495 */       this._dirty = true;
/* 496 */     } else if (this._size >= this._sizeThreshold) {
/* 497 */       rehash();
/*     */       
/*     */ 
/*     */ 
/* 501 */       i = _hashToIndex(calcHash(paramArrayOfChar, paramInt1, paramInt2));
/*     */     }
/*     */     
/* 504 */     Object localObject = new String(paramArrayOfChar, paramInt1, paramInt2);
/* 505 */     if (this._intern) {
/* 506 */       localObject = InternCache.instance.intern((String)localObject);
/*     */     }
/* 508 */     this._size += 1;
/*     */     
/* 510 */     if (this._symbols[i] == null) {
/* 511 */       this._symbols[i] = localObject;
/*     */     } else {
/* 513 */       int k = i >> 1;
/* 514 */       Bucket localBucket = new Bucket((String)localObject, this._buckets[k]);
/* 515 */       this._buckets[k] = localBucket;
/* 516 */       this._longestCollisionList = Math.max(localBucket.length(), this._longestCollisionList);
/* 517 */       if (this._longestCollisionList > 255) {
/* 518 */         reportTooManyCollisions(255);
/*     */       }
/*     */     }
/*     */     
/* 522 */     return (String)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int _hashToIndex(int paramInt)
/*     */   {
/* 531 */     paramInt += (paramInt >>> 15);
/* 532 */     return paramInt & this._indexMask;
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
/*     */   public int calcHash(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 546 */     int i = this._hashSeed;
/* 547 */     for (int j = 0; j < paramInt2; j++) {
/* 548 */       i = i * 33 + paramArrayOfChar[j];
/*     */     }
/*     */     
/* 551 */     return i == 0 ? 1 : i;
/*     */   }
/*     */   
/*     */   public int calcHash(String paramString)
/*     */   {
/* 556 */     int i = paramString.length();
/*     */     
/* 558 */     int j = this._hashSeed;
/* 559 */     for (int k = 0; k < i; k++) {
/* 560 */       j = j * 33 + paramString.charAt(k);
/*     */     }
/*     */     
/* 563 */     return j == 0 ? 1 : j;
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
/*     */   private void copyArrays()
/*     */   {
/* 577 */     String[] arrayOfString = this._symbols;
/* 578 */     int i = arrayOfString.length;
/* 579 */     this._symbols = new String[i];
/* 580 */     System.arraycopy(arrayOfString, 0, this._symbols, 0, i);
/* 581 */     Bucket[] arrayOfBucket = this._buckets;
/* 582 */     i = arrayOfBucket.length;
/* 583 */     this._buckets = new Bucket[i];
/* 584 */     System.arraycopy(arrayOfBucket, 0, this._buckets, 0, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void rehash()
/*     */   {
/* 596 */     int i = this._symbols.length;
/* 597 */     int j = i + i;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 603 */     if (j > 65536)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 608 */       this._size = 0;
/* 609 */       Arrays.fill(this._symbols, null);
/* 610 */       Arrays.fill(this._buckets, null);
/* 611 */       this._dirty = true;
/* 612 */       return;
/*     */     }
/*     */     
/* 615 */     String[] arrayOfString = this._symbols;
/* 616 */     Bucket[] arrayOfBucket = this._buckets;
/* 617 */     this._symbols = new String[j];
/* 618 */     this._buckets = new Bucket[j >> 1];
/*     */     
/* 620 */     this._indexMask = (j - 1);
/* 621 */     this._sizeThreshold = _thresholdSize(j);
/*     */     
/* 623 */     int k = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 628 */     int m = 0;
/* 629 */     Object localObject; int i2; for (int n = 0; n < i; n++) {
/* 630 */       localObject = arrayOfString[n];
/* 631 */       if (localObject != null) {
/* 632 */         k++;
/* 633 */         int i1 = _hashToIndex(calcHash((String)localObject));
/* 634 */         if (this._symbols[i1] == null) {
/* 635 */           this._symbols[i1] = localObject;
/*     */         } else {
/* 637 */           i2 = i1 >> 1;
/* 638 */           Bucket localBucket1 = new Bucket((String)localObject, this._buckets[i2]);
/* 639 */           this._buckets[i2] = localBucket1;
/* 640 */           m = Math.max(m, localBucket1.length());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 645 */     i >>= 1;
/* 646 */     for (n = 0; n < i; n++) {
/* 647 */       localObject = arrayOfBucket[n];
/* 648 */       while (localObject != null) {
/* 649 */         k++;
/* 650 */         String str = ((Bucket)localObject).getSymbol();
/* 651 */         i2 = _hashToIndex(calcHash(str));
/* 652 */         if (this._symbols[i2] == null) {
/* 653 */           this._symbols[i2] = str;
/*     */         } else {
/* 655 */           int i3 = i2 >> 1;
/* 656 */           Bucket localBucket2 = new Bucket(str, this._buckets[i3]);
/* 657 */           this._buckets[i3] = localBucket2;
/* 658 */           m = Math.max(m, localBucket2.length());
/*     */         }
/* 660 */         localObject = ((Bucket)localObject).getNext();
/*     */       }
/*     */     }
/* 663 */     this._longestCollisionList = m;
/*     */     
/* 665 */     if (k != this._size) {
/* 666 */       throw new Error("Internal error on SymbolTable.rehash(): had " + this._size + " entries; now have " + k + ".");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void reportTooManyCollisions(int paramInt)
/*     */   {
/* 675 */     throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._size + ") now exceeds maximum, " + paramInt + " -- suspect a DoS attack based on hash collisions");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class Bucket
/*     */   {
/*     */     private final String _symbol;
/*     */     
/*     */ 
/*     */ 
/*     */     private final Bucket _next;
/*     */     
/*     */ 
/*     */     private final int _length;
/*     */     
/*     */ 
/*     */ 
/*     */     public Bucket(String paramString, Bucket paramBucket)
/*     */     {
/* 696 */       this._symbol = paramString;
/* 697 */       this._next = paramBucket;
/* 698 */       this._length = (paramBucket == null ? 1 : paramBucket._length + 1);
/*     */     }
/*     */     
/* 701 */     public String getSymbol() { return this._symbol; }
/* 702 */     public Bucket getNext() { return this._next; }
/* 703 */     public int length() { return this._length; }
/*     */     
/*     */     public String find(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
/* 706 */       String str = this._symbol;
/* 707 */       Bucket localBucket = this._next;
/*     */       for (;;)
/*     */       {
/* 710 */         if (str.length() == paramInt2) {
/* 711 */           int i = 0;
/*     */           do {
/* 713 */             if (str.charAt(i) != paramArrayOfChar[(paramInt1 + i)]) {
/*     */               break;
/*     */             }
/* 716 */             i++; } while (i < paramInt2);
/* 717 */           if (i == paramInt2) {
/* 718 */             return str;
/*     */           }
/*     */         }
/* 721 */         if (localBucket == null) {
/*     */           break;
/*     */         }
/* 724 */         str = localBucket.getSymbol();
/* 725 */         localBucket = localBucket.getNext();
/*     */       }
/* 727 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/sym/CharsToNameCanonicalizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */