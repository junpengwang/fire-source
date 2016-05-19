/*      */ package com.shaded.fasterxml.jackson.core.sym;
/*      */ 
/*      */ import com.shaded.fasterxml.jackson.core.util.InternCache;
/*      */ import java.util.Arrays;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class BytesToNameCanonicalizer
/*      */ {
/*      */   protected static final int DEFAULT_TABLE_SIZE = 64;
/*      */   protected static final int MAX_TABLE_SIZE = 65536;
/*      */   static final int MAX_ENTRIES_FOR_REUSE = 6000;
/*      */   static final int MAX_COLL_CHAIN_LENGTH = 255;
/*      */   static final int MAX_COLL_CHAIN_FOR_REUSE = 63;
/*      */   static final int MIN_HASH_SIZE = 16;
/*      */   static final int INITIAL_COLLISION_LEN = 32;
/*      */   static final int LAST_VALID_BUCKET = 254;
/*      */   protected final BytesToNameCanonicalizer _parent;
/*      */   protected final AtomicReference<TableInfo> _tableInfo;
/*      */   private final int _hashSeed;
/*      */   protected final boolean _intern;
/*      */   protected int _count;
/*      */   protected int _longestCollisionList;
/*      */   protected int _mainHashMask;
/*      */   protected int[] _mainHash;
/*      */   protected Name[] _mainNames;
/*      */   protected Bucket[] _collList;
/*      */   protected int _collCount;
/*      */   protected int _collEnd;
/*      */   private transient boolean _needRehash;
/*      */   private boolean _mainHashShared;
/*      */   private boolean _mainNamesShared;
/*      */   private boolean _collListShared;
/*      */   private static final int MULT = 33;
/*      */   private static final int MULT2 = 65599;
/*      */   private static final int MULT3 = 31;
/*      */   
/*      */   private BytesToNameCanonicalizer(int paramInt1, boolean paramBoolean, int paramInt2)
/*      */   {
/*  241 */     this._parent = null;
/*  242 */     this._hashSeed = paramInt2;
/*  243 */     this._intern = paramBoolean;
/*      */     
/*  245 */     if (paramInt1 < 16) {
/*  246 */       paramInt1 = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*  251 */     else if ((paramInt1 & paramInt1 - 1) != 0) {
/*  252 */       int i = 16;
/*  253 */       while (i < paramInt1) {
/*  254 */         i += i;
/*      */       }
/*  256 */       paramInt1 = i;
/*      */     }
/*      */     
/*  259 */     this._tableInfo = new AtomicReference(initTableInfo(paramInt1));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BytesToNameCanonicalizer(BytesToNameCanonicalizer paramBytesToNameCanonicalizer, boolean paramBoolean, int paramInt, TableInfo paramTableInfo)
/*      */   {
/*  268 */     this._parent = paramBytesToNameCanonicalizer;
/*  269 */     this._hashSeed = paramInt;
/*  270 */     this._intern = paramBoolean;
/*  271 */     this._tableInfo = null;
/*      */     
/*      */ 
/*  274 */     this._count = paramTableInfo.count;
/*  275 */     this._mainHashMask = paramTableInfo.mainHashMask;
/*  276 */     this._mainHash = paramTableInfo.mainHash;
/*  277 */     this._mainNames = paramTableInfo.mainNames;
/*  278 */     this._collList = paramTableInfo.collList;
/*  279 */     this._collCount = paramTableInfo.collCount;
/*  280 */     this._collEnd = paramTableInfo.collEnd;
/*  281 */     this._longestCollisionList = paramTableInfo.longestCollisionList;
/*      */     
/*      */ 
/*  284 */     this._needRehash = false;
/*  285 */     this._mainHashShared = true;
/*  286 */     this._mainNamesShared = true;
/*  287 */     this._collListShared = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private TableInfo initTableInfo(int paramInt)
/*      */   {
/*  296 */     return new TableInfo(0, paramInt - 1, new int[paramInt], new Name[paramInt], null, 0, 0, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static BytesToNameCanonicalizer createRoot()
/*      */   {
/*  322 */     long l = System.currentTimeMillis();
/*      */     
/*  324 */     int i = (int)l + (int)(l >>> 32) | 0x1;
/*  325 */     return createRoot(i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static BytesToNameCanonicalizer createRoot(int paramInt)
/*      */   {
/*  333 */     return new BytesToNameCanonicalizer(64, true, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BytesToNameCanonicalizer makeChild(boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  346 */     return new BytesToNameCanonicalizer(this, paramBoolean2, this._hashSeed, (TableInfo)this._tableInfo.get());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void release()
/*      */   {
/*  359 */     if ((this._parent != null) && (maybeDirty())) {
/*  360 */       this._parent.mergeChild(new TableInfo(this));
/*      */       
/*      */ 
/*      */ 
/*  364 */       this._mainHashShared = true;
/*  365 */       this._mainNamesShared = true;
/*  366 */       this._collListShared = true;
/*      */     }
/*      */   }
/*      */   
/*      */   private void mergeChild(TableInfo paramTableInfo)
/*      */   {
/*  372 */     int i = paramTableInfo.count;
/*  373 */     TableInfo localTableInfo = (TableInfo)this._tableInfo.get();
/*      */     
/*      */ 
/*  376 */     if (i <= localTableInfo.count) {
/*  377 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  386 */     if ((i > 6000) || (paramTableInfo.longestCollisionList > 63))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  393 */       paramTableInfo = initTableInfo(64);
/*      */     }
/*  395 */     this._tableInfo.compareAndSet(localTableInfo, paramTableInfo);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/*  406 */     if (this._tableInfo != null) {
/*  407 */       return ((TableInfo)this._tableInfo.get()).count;
/*      */     }
/*      */     
/*  410 */     return this._count;
/*      */   }
/*      */   
/*      */ 
/*      */   public int bucketCount()
/*      */   {
/*  416 */     return this._mainHash.length;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean maybeDirty()
/*      */   {
/*  424 */     return !this._mainHashShared;
/*      */   }
/*      */   
/*      */ 
/*      */   public int hashSeed()
/*      */   {
/*  430 */     return this._hashSeed;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int collisionCount()
/*      */   {
/*  440 */     return this._collCount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int maxCollisionLength()
/*      */   {
/*  451 */     return this._longestCollisionList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Name getEmptyName()
/*      */   {
/*  462 */     return Name1.getEmptyName();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Name findName(int paramInt)
/*      */   {
/*  482 */     int i = calcHash(paramInt);
/*  483 */     int j = i & this._mainHashMask;
/*  484 */     int k = this._mainHash[j];
/*      */     
/*      */ 
/*      */     Object localObject;
/*      */     
/*  489 */     if ((k >> 8 ^ i) << 8 == 0)
/*      */     {
/*  491 */       localObject = this._mainNames[j];
/*  492 */       if (localObject == null) {
/*  493 */         return null;
/*      */       }
/*  495 */       if (((Name)localObject).equals(paramInt)) {
/*  496 */         return (Name)localObject;
/*      */       }
/*  498 */     } else if (k == 0) {
/*  499 */       return null;
/*      */     }
/*      */     
/*  502 */     k &= 0xFF;
/*  503 */     if (k > 0) {
/*  504 */       k--;
/*  505 */       localObject = this._collList[k];
/*  506 */       if (localObject != null) {
/*  507 */         return ((Bucket)localObject).find(i, paramInt, 0);
/*      */       }
/*      */     }
/*      */     
/*  511 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Name findName(int paramInt1, int paramInt2)
/*      */   {
/*  531 */     int i = paramInt2 == 0 ? calcHash(paramInt1) : calcHash(paramInt1, paramInt2);
/*  532 */     int j = i & this._mainHashMask;
/*  533 */     int k = this._mainHash[j];
/*      */     
/*      */ 
/*      */     Object localObject;
/*      */     
/*  538 */     if ((k >> 8 ^ i) << 8 == 0)
/*      */     {
/*  540 */       localObject = this._mainNames[j];
/*  541 */       if (localObject == null) {
/*  542 */         return null;
/*      */       }
/*  544 */       if (((Name)localObject).equals(paramInt1, paramInt2)) {
/*  545 */         return (Name)localObject;
/*      */       }
/*  547 */     } else if (k == 0) {
/*  548 */       return null;
/*      */     }
/*      */     
/*  551 */     k &= 0xFF;
/*  552 */     if (k > 0) {
/*  553 */       k--;
/*  554 */       localObject = this._collList[k];
/*  555 */       if (localObject != null) {
/*  556 */         return ((Bucket)localObject).find(i, paramInt1, paramInt2);
/*      */       }
/*      */     }
/*      */     
/*  560 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Name findName(int[] paramArrayOfInt, int paramInt)
/*      */   {
/*  582 */     if (paramInt < 3) {
/*  583 */       return findName(paramArrayOfInt[0], paramInt < 2 ? 0 : paramArrayOfInt[1]);
/*      */     }
/*  585 */     int i = calcHash(paramArrayOfInt, paramInt);
/*      */     
/*  587 */     int j = i & this._mainHashMask;
/*  588 */     int k = this._mainHash[j];
/*  589 */     Object localObject; if ((k >> 8 ^ i) << 8 == 0) {
/*  590 */       localObject = this._mainNames[j];
/*  591 */       if ((localObject == null) || (((Name)localObject).equals(paramArrayOfInt, paramInt)))
/*      */       {
/*  593 */         return (Name)localObject;
/*      */       }
/*  595 */     } else if (k == 0) {
/*  596 */       return null;
/*      */     }
/*  598 */     k &= 0xFF;
/*  599 */     if (k > 0) {
/*  600 */       k--;
/*  601 */       localObject = this._collList[k];
/*  602 */       if (localObject != null) {
/*  603 */         return ((Bucket)localObject).find(i, paramArrayOfInt, paramInt);
/*      */       }
/*      */     }
/*  606 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Name addName(String paramString, int paramInt1, int paramInt2)
/*      */   {
/*  617 */     if (this._intern) {
/*  618 */       paramString = InternCache.instance.intern(paramString);
/*      */     }
/*  620 */     int i = paramInt2 == 0 ? calcHash(paramInt1) : calcHash(paramInt1, paramInt2);
/*  621 */     Name localName = constructName(i, paramString, paramInt1, paramInt2);
/*  622 */     _addSymbol(i, localName);
/*  623 */     return localName;
/*      */   }
/*      */   
/*      */   public Name addName(String paramString, int[] paramArrayOfInt, int paramInt)
/*      */   {
/*  628 */     if (this._intern) {
/*  629 */       paramString = InternCache.instance.intern(paramString);
/*      */     }
/*      */     int i;
/*  632 */     if (paramInt < 3) {
/*  633 */       i = paramInt == 1 ? calcHash(paramArrayOfInt[0]) : calcHash(paramArrayOfInt[0], paramArrayOfInt[1]);
/*      */     } else {
/*  635 */       i = calcHash(paramArrayOfInt, paramInt);
/*      */     }
/*  637 */     Name localName = constructName(i, paramString, paramArrayOfInt, paramInt);
/*  638 */     _addSymbol(i, localName);
/*  639 */     return localName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int calcHash(int paramInt)
/*      */   {
/*  664 */     int i = paramInt ^ this._hashSeed;
/*  665 */     i += (i >>> 15);
/*  666 */     i ^= i >>> 9;
/*  667 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int calcHash(int paramInt1, int paramInt2)
/*      */   {
/*  675 */     int i = paramInt1;
/*  676 */     i ^= i >>> 15;
/*  677 */     i += paramInt2 * 33;
/*  678 */     i ^= this._hashSeed;
/*  679 */     i += (i >>> 7);
/*  680 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */   public int calcHash(int[] paramArrayOfInt, int paramInt)
/*      */   {
/*  686 */     if (paramInt < 3) {
/*  687 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  695 */     int i = paramArrayOfInt[0] ^ this._hashSeed;
/*  696 */     i += (i >>> 9);
/*  697 */     i *= 33;
/*  698 */     i += paramArrayOfInt[1];
/*  699 */     i *= 65599;
/*  700 */     i += (i >>> 15);
/*  701 */     i ^= paramArrayOfInt[2];
/*  702 */     i += (i >>> 17);
/*      */     
/*  704 */     for (int j = 3; j < paramInt; j++) {
/*  705 */       i = i * 31 ^ paramArrayOfInt[j];
/*      */       
/*  707 */       i += (i >>> 3);
/*  708 */       i ^= i << 7;
/*      */     }
/*      */     
/*  711 */     i += (i >>> 15);
/*  712 */     i ^= i << 9;
/*  713 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */   protected static int[] calcQuads(byte[] paramArrayOfByte)
/*      */   {
/*  719 */     int i = paramArrayOfByte.length;
/*  720 */     int[] arrayOfInt = new int[(i + 3) / 4];
/*  721 */     for (int j = 0; j < i; j++) {
/*  722 */       int k = paramArrayOfByte[j] & 0xFF;
/*      */       
/*  724 */       j++; if (j < i) {
/*  725 */         k = k << 8 | paramArrayOfByte[j] & 0xFF;
/*  726 */         j++; if (j < i) {
/*  727 */           k = k << 8 | paramArrayOfByte[j] & 0xFF;
/*  728 */           j++; if (j < i) {
/*  729 */             k = k << 8 | paramArrayOfByte[j] & 0xFF;
/*      */           }
/*      */         }
/*      */       }
/*  733 */       arrayOfInt[(j >> 2)] = k;
/*      */     }
/*  735 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void _addSymbol(int paramInt, Name paramName)
/*      */   {
/*  791 */     if (this._mainHashShared) {
/*  792 */       unshareMain();
/*      */     }
/*      */     
/*  795 */     if (this._needRehash) {
/*  796 */       rehash();
/*      */     }
/*      */     
/*  799 */     this._count += 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  804 */     int i = paramInt & this._mainHashMask;
/*  805 */     int k; if (this._mainNames[i] == null) {
/*  806 */       this._mainHash[i] = (paramInt << 8);
/*  807 */       if (this._mainNamesShared) {
/*  808 */         unshareNames();
/*      */       }
/*  810 */       this._mainNames[i] = paramName;
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  815 */       if (this._collListShared) {
/*  816 */         unshareCollision();
/*      */       }
/*  818 */       this._collCount += 1;
/*  819 */       j = this._mainHash[i];
/*  820 */       k = j & 0xFF;
/*  821 */       if (k == 0) {
/*  822 */         if (this._collEnd <= 254) {
/*  823 */           k = this._collEnd;
/*  824 */           this._collEnd += 1;
/*      */           
/*  826 */           if (k >= this._collList.length) {
/*  827 */             expandCollision();
/*      */           }
/*      */         } else {
/*  830 */           k = findBestBucket();
/*      */         }
/*      */         
/*  833 */         this._mainHash[i] = (j & 0xFF00 | k + 1);
/*      */       } else {
/*  835 */         k--;
/*      */       }
/*      */       
/*      */ 
/*  839 */       Bucket localBucket = new Bucket(paramName, this._collList[k]);
/*  840 */       this._collList[k] = localBucket;
/*      */       
/*  842 */       this._longestCollisionList = Math.max(localBucket.length(), this._longestCollisionList);
/*  843 */       if (this._longestCollisionList > 255) {
/*  844 */         reportTooManyCollisions(255);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  852 */     int j = this._mainHash.length;
/*  853 */     if (this._count > j >> 1) {
/*  854 */       k = j >> 2;
/*      */       
/*      */ 
/*      */ 
/*  858 */       if (this._count > j - k) {
/*  859 */         this._needRehash = true;
/*  860 */       } else if (this._collCount >= k) {
/*  861 */         this._needRehash = true;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void rehash()
/*      */   {
/*  869 */     this._needRehash = false;
/*      */     
/*  871 */     this._mainNamesShared = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  877 */     int[] arrayOfInt = this._mainHash;
/*  878 */     int i = arrayOfInt.length;
/*  879 */     int j = i + i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  884 */     if (j > 65536) {
/*  885 */       nukeSymbols();
/*  886 */       return;
/*      */     }
/*      */     
/*  889 */     this._mainHash = new int[j];
/*  890 */     this._mainHashMask = (j - 1);
/*  891 */     Name[] arrayOfName = this._mainNames;
/*  892 */     this._mainNames = new Name[j];
/*  893 */     int k = 0;
/*  894 */     for (int m = 0; m < i; m++) {
/*  895 */       Name localName1 = arrayOfName[m];
/*  896 */       if (localName1 != null) {
/*  897 */         k++;
/*  898 */         int i1 = localName1.hashCode();
/*  899 */         i2 = i1 & this._mainHashMask;
/*  900 */         this._mainNames[i2] = localName1;
/*  901 */         this._mainHash[i2] = (i1 << 8);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  909 */     m = this._collEnd;
/*  910 */     if (m == 0) {
/*  911 */       this._longestCollisionList = 0;
/*  912 */       return;
/*      */     }
/*      */     
/*  915 */     this._collCount = 0;
/*  916 */     this._collEnd = 0;
/*  917 */     this._collListShared = false;
/*      */     
/*  919 */     int n = 0;
/*      */     
/*  921 */     Bucket[] arrayOfBucket = this._collList;
/*  922 */     this._collList = new Bucket[arrayOfBucket.length];
/*  923 */     for (int i2 = 0; i2 < m; i2++) {
/*  924 */       for (Bucket localBucket1 = arrayOfBucket[i2]; localBucket1 != null; localBucket1 = localBucket1._next) {
/*  925 */         k++;
/*  926 */         Name localName2 = localBucket1._name;
/*  927 */         int i3 = localName2.hashCode();
/*  928 */         int i4 = i3 & this._mainHashMask;
/*  929 */         int i5 = this._mainHash[i4];
/*  930 */         if (this._mainNames[i4] == null) {
/*  931 */           this._mainHash[i4] = (i3 << 8);
/*  932 */           this._mainNames[i4] = localName2;
/*      */         } else {
/*  934 */           this._collCount += 1;
/*  935 */           int i6 = i5 & 0xFF;
/*  936 */           if (i6 == 0) {
/*  937 */             if (this._collEnd <= 254) {
/*  938 */               i6 = this._collEnd;
/*  939 */               this._collEnd += 1;
/*      */               
/*  941 */               if (i6 >= this._collList.length) {
/*  942 */                 expandCollision();
/*      */               }
/*      */             } else {
/*  945 */               i6 = findBestBucket();
/*      */             }
/*      */             
/*  948 */             this._mainHash[i4] = (i5 & 0xFF00 | i6 + 1);
/*      */           } else {
/*  950 */             i6--;
/*      */           }
/*      */           
/*  953 */           Bucket localBucket2 = new Bucket(localName2, this._collList[i6]);
/*  954 */           this._collList[i6] = localBucket2;
/*  955 */           n = Math.max(n, localBucket2.length());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  960 */     this._longestCollisionList = n;
/*      */     
/*  962 */     if (k != this._count) {
/*  963 */       throw new RuntimeException("Internal error: count after rehash " + k + "; should be " + this._count);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void nukeSymbols()
/*      */   {
/*  973 */     this._count = 0;
/*  974 */     this._longestCollisionList = 0;
/*  975 */     Arrays.fill(this._mainHash, 0);
/*  976 */     Arrays.fill(this._mainNames, null);
/*  977 */     Arrays.fill(this._collList, null);
/*  978 */     this._collCount = 0;
/*  979 */     this._collEnd = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int findBestBucket()
/*      */   {
/*  989 */     Bucket[] arrayOfBucket = this._collList;
/*  990 */     int i = Integer.MAX_VALUE;
/*  991 */     int j = -1;
/*      */     
/*  993 */     int k = 0; for (int m = this._collEnd; k < m; k++) {
/*  994 */       int n = arrayOfBucket[k].length();
/*  995 */       if (n < i) {
/*  996 */         if (n == 1) {
/*  997 */           return k;
/*      */         }
/*  999 */         i = n;
/* 1000 */         j = k;
/*      */       }
/*      */     }
/* 1003 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void unshareMain()
/*      */   {
/* 1014 */     int[] arrayOfInt = this._mainHash;
/* 1015 */     int i = this._mainHash.length;
/*      */     
/* 1017 */     this._mainHash = new int[i];
/* 1018 */     System.arraycopy(arrayOfInt, 0, this._mainHash, 0, i);
/* 1019 */     this._mainHashShared = false;
/*      */   }
/*      */   
/*      */   private void unshareCollision()
/*      */   {
/* 1024 */     Bucket[] arrayOfBucket = this._collList;
/* 1025 */     if (arrayOfBucket == null) {
/* 1026 */       this._collList = new Bucket[32];
/*      */     } else {
/* 1028 */       int i = arrayOfBucket.length;
/* 1029 */       this._collList = new Bucket[i];
/* 1030 */       System.arraycopy(arrayOfBucket, 0, this._collList, 0, i);
/*      */     }
/* 1032 */     this._collListShared = false;
/*      */   }
/*      */   
/*      */   private void unshareNames()
/*      */   {
/* 1037 */     Name[] arrayOfName = this._mainNames;
/* 1038 */     int i = arrayOfName.length;
/* 1039 */     this._mainNames = new Name[i];
/* 1040 */     System.arraycopy(arrayOfName, 0, this._mainNames, 0, i);
/* 1041 */     this._mainNamesShared = false;
/*      */   }
/*      */   
/*      */   private void expandCollision()
/*      */   {
/* 1046 */     Bucket[] arrayOfBucket = this._collList;
/* 1047 */     int i = arrayOfBucket.length;
/* 1048 */     this._collList = new Bucket[i + i];
/* 1049 */     System.arraycopy(arrayOfBucket, 0, this._collList, 0, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Name constructName(int paramInt1, String paramString, int paramInt2, int paramInt3)
/*      */   {
/* 1061 */     if (paramInt3 == 0) {
/* 1062 */       return new Name1(paramString, paramInt1, paramInt2);
/*      */     }
/* 1064 */     return new Name2(paramString, paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */   
/*      */   private static Name constructName(int paramInt1, String paramString, int[] paramArrayOfInt, int paramInt2)
/*      */   {
/* 1069 */     if (paramInt2 < 4) {
/* 1070 */       switch (paramInt2) {
/*      */       case 1: 
/* 1072 */         return new Name1(paramString, paramInt1, paramArrayOfInt[0]);
/*      */       case 2: 
/* 1074 */         return new Name2(paramString, paramInt1, paramArrayOfInt[0], paramArrayOfInt[1]);
/*      */       case 3: 
/* 1076 */         return new Name3(paramString, paramInt1, paramArrayOfInt[0], paramArrayOfInt[1], paramArrayOfInt[2]);
/*      */       }
/*      */       
/*      */     }
/*      */     
/* 1081 */     int[] arrayOfInt = new int[paramInt2];
/* 1082 */     for (int i = 0; i < paramInt2; i++) {
/* 1083 */       arrayOfInt[i] = paramArrayOfInt[i];
/*      */     }
/* 1085 */     return new NameN(paramString, paramInt1, arrayOfInt, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void reportTooManyCollisions(int paramInt)
/*      */   {
/* 1099 */     throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._count + ") now exceeds maximum, " + paramInt + " -- suspect a DoS attack based on hash collisions");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static final class TableInfo
/*      */   {
/*      */     public final int count;
/*      */     
/*      */ 
/*      */     public final int mainHashMask;
/*      */     
/*      */ 
/*      */     public final int[] mainHash;
/*      */     
/*      */ 
/*      */     public final Name[] mainNames;
/*      */     
/*      */ 
/*      */     public final BytesToNameCanonicalizer.Bucket[] collList;
/*      */     
/*      */ 
/*      */     public final int collCount;
/*      */     
/*      */     public final int collEnd;
/*      */     
/*      */     public final int longestCollisionList;
/*      */     
/*      */ 
/*      */     public TableInfo(int paramInt1, int paramInt2, int[] paramArrayOfInt, Name[] paramArrayOfName, BytesToNameCanonicalizer.Bucket[] paramArrayOfBucket, int paramInt3, int paramInt4, int paramInt5)
/*      */     {
/* 1130 */       this.count = paramInt1;
/* 1131 */       this.mainHashMask = paramInt2;
/* 1132 */       this.mainHash = paramArrayOfInt;
/* 1133 */       this.mainNames = paramArrayOfName;
/* 1134 */       this.collList = paramArrayOfBucket;
/* 1135 */       this.collCount = paramInt3;
/* 1136 */       this.collEnd = paramInt4;
/* 1137 */       this.longestCollisionList = paramInt5;
/*      */     }
/*      */     
/*      */     public TableInfo(BytesToNameCanonicalizer paramBytesToNameCanonicalizer)
/*      */     {
/* 1142 */       this.count = paramBytesToNameCanonicalizer._count;
/* 1143 */       this.mainHashMask = paramBytesToNameCanonicalizer._mainHashMask;
/* 1144 */       this.mainHash = paramBytesToNameCanonicalizer._mainHash;
/* 1145 */       this.mainNames = paramBytesToNameCanonicalizer._mainNames;
/* 1146 */       this.collList = paramBytesToNameCanonicalizer._collList;
/* 1147 */       this.collCount = paramBytesToNameCanonicalizer._collCount;
/* 1148 */       this.collEnd = paramBytesToNameCanonicalizer._collEnd;
/* 1149 */       this.longestCollisionList = paramBytesToNameCanonicalizer._longestCollisionList;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class Bucket
/*      */   {
/*      */     protected final Name _name;
/*      */     
/*      */     protected final Bucket _next;
/*      */     
/*      */     private final int _length;
/*      */     
/*      */ 
/*      */     Bucket(Name paramName, Bucket paramBucket)
/*      */     {
/* 1165 */       this._name = paramName;
/* 1166 */       this._next = paramBucket;
/* 1167 */       this._length = (paramBucket == null ? 1 : paramBucket._length + 1);
/*      */     }
/*      */     
/* 1170 */     public int length() { return this._length; }
/*      */     
/*      */     public Name find(int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 1174 */       if ((this._name.hashCode() == paramInt1) && 
/* 1175 */         (this._name.equals(paramInt2, paramInt3))) {
/* 1176 */         return this._name;
/*      */       }
/*      */       
/* 1179 */       for (Bucket localBucket = this._next; localBucket != null; localBucket = localBucket._next) {
/* 1180 */         Name localName = localBucket._name;
/* 1181 */         if ((localName.hashCode() == paramInt1) && 
/* 1182 */           (localName.equals(paramInt2, paramInt3))) {
/* 1183 */           return localName;
/*      */         }
/*      */       }
/*      */       
/* 1187 */       return null;
/*      */     }
/*      */     
/*      */     public Name find(int paramInt1, int[] paramArrayOfInt, int paramInt2)
/*      */     {
/* 1192 */       if ((this._name.hashCode() == paramInt1) && 
/* 1193 */         (this._name.equals(paramArrayOfInt, paramInt2))) {
/* 1194 */         return this._name;
/*      */       }
/*      */       
/* 1197 */       for (Bucket localBucket = this._next; localBucket != null; localBucket = localBucket._next) {
/* 1198 */         Name localName = localBucket._name;
/* 1199 */         if ((localName.hashCode() == paramInt1) && 
/* 1200 */           (localName.equals(paramArrayOfInt, paramInt2))) {
/* 1201 */           return localName;
/*      */         }
/*      */       }
/*      */       
/* 1205 */       return null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/sym/BytesToNameCanonicalizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */