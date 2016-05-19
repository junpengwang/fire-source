/*     */ package com.shaded.fasterxml.jackson.core.io;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NumberInput
/*     */ {
/*     */   public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";
/*     */   
/*     */ 
/*     */ 
/*     */   static final long L_BILLION = 1000000000L;
/*     */   
/*     */ 
/*     */ 
/*  16 */   static final String MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE).substring(1);
/*  17 */   static final String MAX_LONG_STR = String.valueOf(Long.MAX_VALUE);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int parseInt(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/*  28 */     int i = paramArrayOfChar[paramInt1] - '0';
/*  29 */     paramInt2 += paramInt1;
/*     */     
/*  31 */     paramInt1++; if (paramInt1 < paramInt2) {
/*  32 */       i = i * 10 + (paramArrayOfChar[paramInt1] - '0');
/*  33 */       paramInt1++; if (paramInt1 < paramInt2) {
/*  34 */         i = i * 10 + (paramArrayOfChar[paramInt1] - '0');
/*  35 */         paramInt1++; if (paramInt1 < paramInt2) {
/*  36 */           i = i * 10 + (paramArrayOfChar[paramInt1] - '0');
/*  37 */           paramInt1++; if (paramInt1 < paramInt2) {
/*  38 */             i = i * 10 + (paramArrayOfChar[paramInt1] - '0');
/*  39 */             paramInt1++; if (paramInt1 < paramInt2) {
/*  40 */               i = i * 10 + (paramArrayOfChar[paramInt1] - '0');
/*  41 */               paramInt1++; if (paramInt1 < paramInt2) {
/*  42 */                 i = i * 10 + (paramArrayOfChar[paramInt1] - '0');
/*  43 */                 paramInt1++; if (paramInt1 < paramInt2) {
/*  44 */                   i = i * 10 + (paramArrayOfChar[paramInt1] - '0');
/*  45 */                   paramInt1++; if (paramInt1 < paramInt2) {
/*  46 */                     i = i * 10 + (paramArrayOfChar[paramInt1] - '0');
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  55 */     return i;
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
/*     */   public static int parseInt(String paramString)
/*     */   {
/*  68 */     int i = paramString.charAt(0);
/*  69 */     int j = paramString.length();
/*  70 */     int k = i == 45 ? 1 : 0;
/*  71 */     int m = 1;
/*     */     
/*     */ 
/*  74 */     if (k != 0) {
/*  75 */       if ((j == 1) || (j > 10)) {
/*  76 */         return Integer.parseInt(paramString);
/*     */       }
/*  78 */       i = paramString.charAt(m++);
/*     */     }
/*  80 */     else if (j > 9) {
/*  81 */       return Integer.parseInt(paramString);
/*     */     }
/*     */     
/*  84 */     if ((i > 57) || (i < 48)) {
/*  85 */       return Integer.parseInt(paramString);
/*     */     }
/*  87 */     int n = i - 48;
/*  88 */     if (m < j) {
/*  89 */       i = paramString.charAt(m++);
/*  90 */       if ((i > 57) || (i < 48)) {
/*  91 */         return Integer.parseInt(paramString);
/*     */       }
/*  93 */       n = n * 10 + (i - 48);
/*  94 */       if (m < j) {
/*  95 */         i = paramString.charAt(m++);
/*  96 */         if ((i > 57) || (i < 48)) {
/*  97 */           return Integer.parseInt(paramString);
/*     */         }
/*  99 */         n = n * 10 + (i - 48);
/*     */         
/* 101 */         if (m < j) {
/*     */           do {
/* 103 */             i = paramString.charAt(m++);
/* 104 */             if ((i > 57) || (i < 48)) {
/* 105 */               return Integer.parseInt(paramString);
/*     */             }
/* 107 */             n = n * 10 + (i - 48);
/* 108 */           } while (m < j);
/*     */         }
/*     */       }
/*     */     }
/* 112 */     return k != 0 ? -n : n;
/*     */   }
/*     */   
/*     */ 
/*     */   public static long parseLong(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 118 */     int i = paramInt2 - 9;
/* 119 */     long l = parseInt(paramArrayOfChar, paramInt1, i) * 1000000000L;
/* 120 */     return l + parseInt(paramArrayOfChar, paramInt1 + i, 9);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long parseLong(String paramString)
/*     */   {
/* 128 */     int i = paramString.length();
/* 129 */     if (i <= 9) {
/* 130 */       return parseInt(paramString);
/*     */     }
/*     */     
/* 133 */     return Long.parseLong(paramString);
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
/*     */   public static boolean inLongRange(char[] paramArrayOfChar, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/* 148 */     String str = paramBoolean ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
/* 149 */     int i = str.length();
/* 150 */     if (paramInt2 < i) return true;
/* 151 */     if (paramInt2 > i) { return false;
/*     */     }
/* 153 */     for (int j = 0; j < i; j++) {
/* 154 */       int k = paramArrayOfChar[(paramInt1 + j)] - str.charAt(j);
/* 155 */       if (k != 0) {
/* 156 */         return k < 0;
/*     */       }
/*     */     }
/* 159 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean inLongRange(String paramString, boolean paramBoolean)
/*     */   {
/* 171 */     String str = paramBoolean ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
/* 172 */     int i = str.length();
/* 173 */     int j = paramString.length();
/* 174 */     if (j < i) return true;
/* 175 */     if (j > i) { return false;
/*     */     }
/*     */     
/* 178 */     for (int k = 0; k < i; k++) {
/* 179 */       int m = paramString.charAt(k) - str.charAt(k);
/* 180 */       if (m != 0) {
/* 181 */         return m < 0;
/*     */       }
/*     */     }
/* 184 */     return true;
/*     */   }
/*     */   
/*     */   public static int parseAsInt(String paramString, int paramInt)
/*     */   {
/* 189 */     if (paramString == null) {
/* 190 */       return paramInt;
/*     */     }
/* 192 */     paramString = paramString.trim();
/* 193 */     int i = paramString.length();
/* 194 */     if (i == 0) {
/* 195 */       return paramInt;
/*     */     }
/*     */     
/* 198 */     int j = 0;
/* 199 */     int k; if (j < i) {
/* 200 */       k = paramString.charAt(0);
/* 201 */       if (k == 43) {
/* 202 */         paramString = paramString.substring(1);
/* 203 */         i = paramString.length();
/* 204 */       } else if (k == 45) {
/* 205 */         j++;
/*     */       }
/*     */     }
/* 208 */     for (; j < i; j++) {
/* 209 */       k = paramString.charAt(j);
/*     */       
/* 211 */       if ((k > 57) || (k < 48)) {
/*     */         try {
/* 213 */           return (int)parseDouble(paramString);
/*     */         } catch (NumberFormatException localNumberFormatException2) {
/* 215 */           return paramInt;
/*     */         }
/*     */       }
/*     */     }
/*     */     try {
/* 220 */       return Integer.parseInt(paramString);
/*     */     } catch (NumberFormatException localNumberFormatException1) {}
/* 222 */     return paramInt;
/*     */   }
/*     */   
/*     */   public static long parseAsLong(String paramString, long paramLong)
/*     */   {
/* 227 */     if (paramString == null) {
/* 228 */       return paramLong;
/*     */     }
/* 230 */     paramString = paramString.trim();
/* 231 */     int i = paramString.length();
/* 232 */     if (i == 0) {
/* 233 */       return paramLong;
/*     */     }
/*     */     
/* 236 */     int j = 0;
/* 237 */     int k; if (j < i) {
/* 238 */       k = paramString.charAt(0);
/* 239 */       if (k == 43) {
/* 240 */         paramString = paramString.substring(1);
/* 241 */         i = paramString.length();
/* 242 */       } else if (k == 45) {
/* 243 */         j++;
/*     */       }
/*     */     }
/* 246 */     for (; j < i; j++) {
/* 247 */       k = paramString.charAt(j);
/*     */       
/* 249 */       if ((k > 57) || (k < 48)) {
/*     */         try {
/* 251 */           return parseDouble(paramString);
/*     */         } catch (NumberFormatException localNumberFormatException2) {
/* 253 */           return paramLong;
/*     */         }
/*     */       }
/*     */     }
/*     */     try {
/* 258 */       return Long.parseLong(paramString);
/*     */     } catch (NumberFormatException localNumberFormatException1) {}
/* 260 */     return paramLong;
/*     */   }
/*     */   
/*     */   public static double parseAsDouble(String paramString, double paramDouble)
/*     */   {
/* 265 */     if (paramString == null) {
/* 266 */       return paramDouble;
/*     */     }
/* 268 */     paramString = paramString.trim();
/* 269 */     int i = paramString.length();
/* 270 */     if (i == 0) {
/* 271 */       return paramDouble;
/*     */     }
/*     */     try {
/* 274 */       return parseDouble(paramString);
/*     */     } catch (NumberFormatException localNumberFormatException) {}
/* 276 */     return paramDouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double parseDouble(String paramString)
/*     */     throws NumberFormatException
/*     */   {
/* 285 */     if ("2.2250738585072012e-308".equals(paramString)) {
/* 286 */       return Double.MIN_VALUE;
/*     */     }
/* 288 */     return Double.parseDouble(paramString);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/io/NumberInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */