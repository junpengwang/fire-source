/*     */ package org.shaded.apache.http.client.utils;
/*     */ 
/*     */ import java.util.StringTokenizer;
/*     */ import org.shaded.apache.http.annotation.Immutable;
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
/*     */ @Immutable
/*     */ public class Rfc3492Idn
/*     */   implements Idn
/*     */ {
/*     */   private static final int base = 36;
/*     */   private static final int tmin = 1;
/*     */   private static final int tmax = 26;
/*     */   private static final int skew = 38;
/*     */   private static final int damp = 700;
/*     */   private static final int initial_bias = 72;
/*     */   private static final int initial_n = 128;
/*     */   private static final char delimiter = '-';
/*     */   private static final String ACE_PREFIX = "xn--";
/*     */   
/*     */   private int adapt(int delta, int numpoints, boolean firsttime)
/*     */   {
/*  55 */     if (firsttime) delta /= 700; else
/*  56 */       delta /= 2;
/*  57 */     delta += delta / numpoints;
/*  58 */     int k = 0;
/*  59 */     while (delta > 455) {
/*  60 */       delta /= 35;
/*  61 */       k += 36;
/*     */     }
/*  63 */     return k + 36 * delta / (delta + 38);
/*     */   }
/*     */   
/*     */   private int digit(char c) {
/*  67 */     if ((c >= 'A') && (c <= 'Z')) return c - 'A';
/*  68 */     if ((c >= 'a') && (c <= 'z')) return c - 'a';
/*  69 */     if ((c >= '0') && (c <= '9')) return c - '0' + 26;
/*  70 */     throw new IllegalArgumentException("illegal digit: " + c);
/*     */   }
/*     */   
/*     */   public String toUnicode(String punycode) {
/*  74 */     StringBuilder unicode = new StringBuilder(punycode.length());
/*  75 */     StringTokenizer tok = new StringTokenizer(punycode, ".");
/*  76 */     while (tok.hasMoreTokens()) {
/*  77 */       String t = tok.nextToken();
/*  78 */       if (unicode.length() > 0) unicode.append('.');
/*  79 */       if (t.startsWith("xn--")) t = decode(t.substring(4));
/*  80 */       unicode.append(t);
/*     */     }
/*  82 */     return unicode.toString();
/*     */   }
/*     */   
/*     */   protected String decode(String input) {
/*  86 */     int n = 128;
/*  87 */     int i = 0;
/*  88 */     int bias = 72;
/*  89 */     StringBuilder output = new StringBuilder(input.length());
/*  90 */     int lastdelim = input.lastIndexOf('-');
/*  91 */     if (lastdelim != -1) {
/*  92 */       output.append(input.subSequence(0, lastdelim));
/*  93 */       input = input.substring(lastdelim + 1);
/*     */     }
/*     */     
/*  96 */     while (input.length() > 0) {
/*  97 */       int oldi = i;
/*  98 */       int w = 1;
/*  99 */       for (int k = 36; 
/* 100 */           input.length() != 0; k += 36)
/*     */       {
/* 101 */         char c = input.charAt(0);
/* 102 */         input = input.substring(1);
/* 103 */         int digit = digit(c);
/* 104 */         i += digit * w;
/*     */         int t;
/* 106 */         int t; if (k <= bias + 1) {
/* 107 */           t = 1; } else { int t;
/* 108 */           if (k >= bias + 26) {
/* 109 */             t = 26;
/*     */           } else
/* 111 */             t = k - bias;
/*     */         }
/* 113 */         if (digit < t) break;
/* 114 */         w *= (36 - t);
/*     */       }
/* 116 */       bias = adapt(i - oldi, output.length() + 1, oldi == 0);
/* 117 */       n += i / (output.length() + 1);
/* 118 */       i %= (output.length() + 1);
/*     */       
/* 120 */       output.insert(i, (char)n);
/* 121 */       i++;
/*     */     }
/* 123 */     return output.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/client/utils/Rfc3492Idn.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */