/*     */ package com.shaded.fasterxml.jackson.databind.util;
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
/*     */ public abstract class NameTransformer
/*     */ {
/*  14 */   public static final NameTransformer NOP = new NameTransformer()
/*     */   {
/*     */     public String transform(String paramAnonymousString) {
/*  17 */       return paramAnonymousString;
/*     */     }
/*     */     
/*     */     public String reverse(String paramAnonymousString)
/*     */     {
/*  22 */       return paramAnonymousString;
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static NameTransformer simpleTransformer(String paramString1, final String paramString2)
/*     */   {
/*  34 */     int i = (paramString1 != null) && (paramString1.length() > 0) ? 1 : 0;
/*  35 */     int j = (paramString2 != null) && (paramString2.length() > 0) ? 1 : 0;
/*     */     
/*  37 */     if (i != 0) {
/*  38 */       if (j != 0) {
/*  39 */         new NameTransformer()
/*     */         {
/*  41 */           public String transform(String paramAnonymousString) { return this.val$prefix + paramAnonymousString + paramString2; }
/*     */           
/*     */           public String reverse(String paramAnonymousString) {
/*  44 */             if (paramAnonymousString.startsWith(this.val$prefix)) {
/*  45 */               String str = paramAnonymousString.substring(this.val$prefix.length());
/*  46 */               if (str.endsWith(paramString2)) {
/*  47 */                 return str.substring(0, str.length() - paramString2.length());
/*     */               }
/*     */             }
/*  50 */             return null;
/*     */           }
/*     */           
/*  53 */           public String toString() { return "[PreAndSuffixTransformer('" + this.val$prefix + "','" + paramString2 + "')]"; }
/*     */         };
/*     */       }
/*  56 */       new NameTransformer()
/*     */       {
/*  58 */         public String transform(String paramAnonymousString) { return this.val$prefix + paramAnonymousString; }
/*     */         
/*     */         public String reverse(String paramAnonymousString) {
/*  61 */           if (paramAnonymousString.startsWith(this.val$prefix)) {
/*  62 */             return paramAnonymousString.substring(this.val$prefix.length());
/*     */           }
/*  64 */           return null;
/*     */         }
/*     */         
/*  67 */         public String toString() { return "[PrefixTransformer('" + this.val$prefix + "')]"; }
/*     */       };
/*     */     }
/*  70 */     if (j != 0) {
/*  71 */       new NameTransformer()
/*     */       {
/*  73 */         public String transform(String paramAnonymousString) { return paramAnonymousString + this.val$suffix; }
/*     */         
/*     */         public String reverse(String paramAnonymousString) {
/*  76 */           if (paramAnonymousString.endsWith(this.val$suffix)) {
/*  77 */             return paramAnonymousString.substring(0, paramAnonymousString.length() - this.val$suffix.length());
/*     */           }
/*  79 */           return null;
/*     */         }
/*     */         
/*  82 */         public String toString() { return "[SuffixTransformer('" + this.val$suffix + "')]"; }
/*     */       };
/*     */     }
/*  85 */     return NOP;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static NameTransformer chainedTransformer(NameTransformer paramNameTransformer1, NameTransformer paramNameTransformer2)
/*     */   {
/*  94 */     return new Chained(paramNameTransformer1, paramNameTransformer2);
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract String transform(String paramString);
/*     */   
/*     */ 
/*     */   public abstract String reverse(String paramString);
/*     */   
/*     */ 
/*     */   public static class Chained
/*     */     extends NameTransformer
/*     */   {
/*     */     protected final NameTransformer _t1;
/*     */     
/*     */     protected final NameTransformer _t2;
/*     */     
/*     */ 
/*     */     public Chained(NameTransformer paramNameTransformer1, NameTransformer paramNameTransformer2)
/*     */     {
/* 114 */       this._t1 = paramNameTransformer1;
/* 115 */       this._t2 = paramNameTransformer2;
/*     */     }
/*     */     
/*     */     public String transform(String paramString)
/*     */     {
/* 120 */       return this._t1.transform(this._t2.transform(paramString));
/*     */     }
/*     */     
/*     */     public String reverse(String paramString)
/*     */     {
/* 125 */       paramString = this._t1.reverse(paramString);
/* 126 */       if (paramString != null) {
/* 127 */         paramString = this._t2.reverse(paramString);
/*     */       }
/* 129 */       return paramString;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 133 */       return "[ChainedTransformer(" + this._t1 + ", " + this._t2 + ")]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/databind/util/NameTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */