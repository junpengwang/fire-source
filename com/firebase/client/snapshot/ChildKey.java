/*     */ package com.firebase.client.snapshot;
/*     */ 
/*     */ import com.firebase.client.utilities.Utilities;
/*     */ 
/*     */ public class ChildKey implements Comparable<ChildKey>
/*     */ {
/*     */   private final String key;
/*   8 */   private static final ChildKey MIN_KEY = new ChildKey("[MIN_KEY]");
/*   9 */   private static final ChildKey MAX_KEY = new ChildKey("[MAX_KEY]");
/*     */   
/*     */ 
/*  12 */   private static final ChildKey PRIORITY_CHILD_KEY = new ChildKey(".priority");
/*  13 */   private static final ChildKey INFO_CHILD_KEY = new ChildKey(".info");
/*     */   
/*     */   public static ChildKey getMinName() {
/*  16 */     return MIN_KEY;
/*     */   }
/*     */   
/*     */   public static ChildKey getMaxName() {
/*  20 */     return MAX_KEY;
/*     */   }
/*     */   
/*     */   public static ChildKey getPriorityKey() {
/*  24 */     return PRIORITY_CHILD_KEY;
/*     */   }
/*     */   
/*     */   public static ChildKey getInfoKey() {
/*  28 */     return INFO_CHILD_KEY;
/*     */   }
/*     */   
/*     */   private ChildKey(String key) {
/*  32 */     this.key = key;
/*     */   }
/*     */   
/*     */   public String asString() {
/*  36 */     return this.key;
/*     */   }
/*     */   
/*     */   public boolean isPriorityChildName() {
/*  40 */     return this == PRIORITY_CHILD_KEY;
/*     */   }
/*     */   
/*     */   protected boolean isInt() {
/*  44 */     return false;
/*     */   }
/*     */   
/*     */   protected int intValue() {
/*  48 */     return 0;
/*     */   }
/*     */   
/*     */   public int compareTo(ChildKey other)
/*     */   {
/*  53 */     if (this == other)
/*  54 */       return 0;
/*  55 */     if ((this == MIN_KEY) || (other == MAX_KEY))
/*  56 */       return -1;
/*  57 */     if ((other == MIN_KEY) || (this == MAX_KEY))
/*  58 */       return 1;
/*  59 */     if (isInt()) {
/*  60 */       if (other.isInt()) {
/*  61 */         int cmp = Utilities.compareInts(intValue(), other.intValue());
/*  62 */         return cmp == 0 ? Utilities.compareInts(this.key.length(), other.key.length()) : cmp;
/*     */       }
/*  64 */       return -1;
/*     */     }
/*  66 */     if (other.isInt()) {
/*  67 */       return 1;
/*     */     }
/*  69 */     return this.key.compareTo(other.key);
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     return "ChildKey(\"" + this.key + "\")";
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  80 */     return this.key.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/*  85 */     if (!(obj instanceof ChildKey)) {
/*  86 */       return false;
/*     */     }
/*  88 */     if (this == obj) {
/*  89 */       return true;
/*     */     }
/*  91 */     ChildKey other = (ChildKey)obj;
/*  92 */     return this.key.equals(other.key);
/*     */   }
/*     */   
/*     */   public static ChildKey fromString(String key) {
/*  96 */     Integer intValue = Utilities.tryParseInt(key);
/*  97 */     if (intValue != null)
/*  98 */       return new IntegerChildKey(key, intValue.intValue());
/*  99 */     if (key.equals(".priority")) {
/* 100 */       return PRIORITY_CHILD_KEY;
/*     */     }
/* 102 */     assert (!key.contains("/"));
/* 103 */     return new ChildKey(key);
/*     */   }
/*     */   
/*     */   private static class IntegerChildKey extends ChildKey
/*     */   {
/*     */     private final int intValue;
/*     */     
/*     */     IntegerChildKey(String name, int intValue) {
/* 111 */       super(null);
/* 112 */       this.intValue = intValue;
/*     */     }
/*     */     
/*     */     protected boolean isInt()
/*     */     {
/* 117 */       return true;
/*     */     }
/*     */     
/*     */     protected int intValue()
/*     */     {
/* 122 */       return this.intValue;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 127 */       return "IntegerChildName(\"" + this.key + "\")";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/snapshot/ChildKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */