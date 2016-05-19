/*     */ package com.shaded.fasterxml.jackson.core.io;
/*     */ 
/*     */ 
/*     */ public final class NumberOutput
/*     */ {
/*     */   private static final char NULL_CHAR = '\000';
/*   7 */   private static int MILLION = 1000000;
/*   8 */   private static int BILLION = 1000000000;
/*   9 */   private static long TEN_BILLION_L = 10000000000L;
/*  10 */   private static long THOUSAND_L = 1000L;
/*     */   
/*  12 */   private static long MIN_INT_AS_LONG = -2147483648L;
/*  13 */   private static long MAX_INT_AS_LONG = 2147483647L;
/*     */   
/*  15 */   static final String SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
/*     */   
/*  17 */   static final char[] LEADING_TRIPLETS = new char['ྠ'];
/*  18 */   static final char[] FULL_TRIPLETS = new char['ྠ'];
/*     */   static final byte[] FULL_TRIPLETS_B;
/*     */   
/*     */   static
/*     */   {
/*  23 */     int i = 0;
/*  24 */     for (int j = 0; j < 10; j++) {
/*  25 */       int k = (char)(48 + j);
/*  26 */       int m = j == 0 ? 0 : k;
/*  27 */       for (int n = 0; n < 10; n++) {
/*  28 */         int i1 = (char)(48 + n);
/*  29 */         int i2 = (j == 0) && (n == 0) ? 0 : i1;
/*  30 */         for (int i3 = 0; i3 < 10; i3++)
/*     */         {
/*  32 */           int i4 = (char)(48 + i3);
/*  33 */           LEADING_TRIPLETS[i] = m;
/*  34 */           LEADING_TRIPLETS[(i + 1)] = i2;
/*  35 */           LEADING_TRIPLETS[(i + 2)] = i4;
/*  36 */           FULL_TRIPLETS[i] = k;
/*  37 */           FULL_TRIPLETS[(i + 1)] = i1;
/*  38 */           FULL_TRIPLETS[(i + 2)] = i4;
/*  39 */           i += 4;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  45 */     FULL_TRIPLETS_B = new byte['ྠ'];
/*     */     
/*  47 */     for (i = 0; i < 4000; i++) {
/*  48 */       FULL_TRIPLETS_B[i] = ((byte)FULL_TRIPLETS[i]);
/*     */     }
/*     */   }
/*     */   
/*  52 */   static final String[] sSmallIntStrs = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
/*     */   
/*     */ 
/*  55 */   static final String[] sSmallIntStrs2 = { "-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10" };
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
/*     */   public static int outputInt(int paramInt1, char[] paramArrayOfChar, int paramInt2)
/*     */   {
/*  70 */     if (paramInt1 < 0) {
/*  71 */       if (paramInt1 == Integer.MIN_VALUE)
/*     */       {
/*     */ 
/*     */ 
/*  75 */         return outputLong(paramInt1, paramArrayOfChar, paramInt2);
/*     */       }
/*  77 */       paramArrayOfChar[(paramInt2++)] = '-';
/*  78 */       paramInt1 = -paramInt1;
/*     */     }
/*     */     
/*  81 */     if (paramInt1 < MILLION) {
/*  82 */       if (paramInt1 < 1000) {
/*  83 */         if (paramInt1 < 10) {
/*  84 */           paramArrayOfChar[(paramInt2++)] = ((char)(48 + paramInt1));
/*     */         } else {
/*  86 */           paramInt2 = outputLeadingTriplet(paramInt1, paramArrayOfChar, paramInt2);
/*     */         }
/*     */       } else {
/*  89 */         i = paramInt1 / 1000;
/*  90 */         paramInt1 -= i * 1000;
/*  91 */         paramInt2 = outputLeadingTriplet(i, paramArrayOfChar, paramInt2);
/*  92 */         paramInt2 = outputFullTriplet(paramInt1, paramArrayOfChar, paramInt2);
/*     */       }
/*  94 */       return paramInt2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 102 */     int i = paramInt1 >= BILLION ? 1 : 0;
/* 103 */     if (i != 0) {
/* 104 */       paramInt1 -= BILLION;
/* 105 */       if (paramInt1 >= BILLION) {
/* 106 */         paramInt1 -= BILLION;
/* 107 */         paramArrayOfChar[(paramInt2++)] = '2';
/*     */       } else {
/* 109 */         paramArrayOfChar[(paramInt2++)] = '1';
/*     */       }
/*     */     }
/* 112 */     int j = paramInt1 / 1000;
/* 113 */     int k = paramInt1 - j * 1000;
/* 114 */     paramInt1 = j;
/* 115 */     j /= 1000;
/* 116 */     int m = paramInt1 - j * 1000;
/*     */     
/*     */ 
/* 119 */     if (i != 0) {
/* 120 */       paramInt2 = outputFullTriplet(j, paramArrayOfChar, paramInt2);
/*     */     } else {
/* 122 */       paramInt2 = outputLeadingTriplet(j, paramArrayOfChar, paramInt2);
/*     */     }
/* 124 */     paramInt2 = outputFullTriplet(m, paramArrayOfChar, paramInt2);
/* 125 */     paramInt2 = outputFullTriplet(k, paramArrayOfChar, paramInt2);
/* 126 */     return paramInt2;
/*     */   }
/*     */   
/*     */   public static int outputInt(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*     */   {
/* 131 */     if (paramInt1 < 0) {
/* 132 */       if (paramInt1 == Integer.MIN_VALUE) {
/* 133 */         return outputLong(paramInt1, paramArrayOfByte, paramInt2);
/*     */       }
/* 135 */       paramArrayOfByte[(paramInt2++)] = 45;
/* 136 */       paramInt1 = -paramInt1;
/*     */     }
/*     */     
/* 139 */     if (paramInt1 < MILLION) {
/* 140 */       if (paramInt1 < 1000) {
/* 141 */         if (paramInt1 < 10) {
/* 142 */           paramArrayOfByte[(paramInt2++)] = ((byte)(48 + paramInt1));
/*     */         } else {
/* 144 */           paramInt2 = outputLeadingTriplet(paramInt1, paramArrayOfByte, paramInt2);
/*     */         }
/*     */       } else {
/* 147 */         i = paramInt1 / 1000;
/* 148 */         paramInt1 -= i * 1000;
/* 149 */         paramInt2 = outputLeadingTriplet(i, paramArrayOfByte, paramInt2);
/* 150 */         paramInt2 = outputFullTriplet(paramInt1, paramArrayOfByte, paramInt2);
/*     */       }
/* 152 */       return paramInt2;
/*     */     }
/* 154 */     int i = paramInt1 >= BILLION ? 1 : 0;
/* 155 */     if (i != 0) {
/* 156 */       paramInt1 -= BILLION;
/* 157 */       if (paramInt1 >= BILLION) {
/* 158 */         paramInt1 -= BILLION;
/* 159 */         paramArrayOfByte[(paramInt2++)] = 50;
/*     */       } else {
/* 161 */         paramArrayOfByte[(paramInt2++)] = 49;
/*     */       }
/*     */     }
/* 164 */     int j = paramInt1 / 1000;
/* 165 */     int k = paramInt1 - j * 1000;
/* 166 */     paramInt1 = j;
/* 167 */     j /= 1000;
/* 168 */     int m = paramInt1 - j * 1000;
/*     */     
/* 170 */     if (i != 0) {
/* 171 */       paramInt2 = outputFullTriplet(j, paramArrayOfByte, paramInt2);
/*     */     } else {
/* 173 */       paramInt2 = outputLeadingTriplet(j, paramArrayOfByte, paramInt2);
/*     */     }
/* 175 */     paramInt2 = outputFullTriplet(m, paramArrayOfByte, paramInt2);
/* 176 */     paramInt2 = outputFullTriplet(k, paramArrayOfByte, paramInt2);
/* 177 */     return paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int outputLong(long paramLong, char[] paramArrayOfChar, int paramInt)
/*     */   {
/* 186 */     if (paramLong < 0L)
/*     */     {
/*     */ 
/*     */ 
/* 190 */       if (paramLong > MIN_INT_AS_LONG) {
/* 191 */         return outputInt((int)paramLong, paramArrayOfChar, paramInt);
/*     */       }
/* 193 */       if (paramLong == Long.MIN_VALUE)
/*     */       {
/* 195 */         i = SMALLEST_LONG.length();
/* 196 */         SMALLEST_LONG.getChars(0, i, paramArrayOfChar, paramInt);
/* 197 */         return paramInt + i;
/*     */       }
/* 199 */       paramArrayOfChar[(paramInt++)] = '-';
/* 200 */       paramLong = -paramLong;
/*     */     }
/* 202 */     else if (paramLong <= MAX_INT_AS_LONG) {
/* 203 */       return outputInt((int)paramLong, paramArrayOfChar, paramInt);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 210 */     int i = paramInt;
/* 211 */     paramInt += calcLongStrLength(paramLong);
/* 212 */     int j = paramInt;
/*     */     
/*     */     int k;
/* 215 */     while (paramLong > MAX_INT_AS_LONG) {
/* 216 */       j -= 3;
/* 217 */       long l = paramLong / THOUSAND_L;
/* 218 */       k = (int)(paramLong - l * THOUSAND_L);
/* 219 */       outputFullTriplet(k, paramArrayOfChar, j);
/* 220 */       paramLong = l;
/*     */     }
/*     */     
/* 223 */     int m = (int)paramLong;
/* 224 */     while (m >= 1000) {
/* 225 */       j -= 3;
/* 226 */       int n = m / 1000;
/* 227 */       k = m - n * 1000;
/* 228 */       outputFullTriplet(k, paramArrayOfChar, j);
/* 229 */       m = n;
/*     */     }
/*     */     
/* 232 */     outputLeadingTriplet(m, paramArrayOfChar, i);
/*     */     
/* 234 */     return paramInt;
/*     */   }
/*     */   
/*     */   public static int outputLong(long paramLong, byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 239 */     if (paramLong < 0L) {
/* 240 */       if (paramLong > MIN_INT_AS_LONG) {
/* 241 */         return outputInt((int)paramLong, paramArrayOfByte, paramInt);
/*     */       }
/* 243 */       if (paramLong == Long.MIN_VALUE)
/*     */       {
/* 245 */         i = SMALLEST_LONG.length();
/* 246 */         for (j = 0; j < i; j++) {
/* 247 */           paramArrayOfByte[(paramInt++)] = ((byte)SMALLEST_LONG.charAt(j));
/*     */         }
/* 249 */         return paramInt;
/*     */       }
/* 251 */       paramArrayOfByte[(paramInt++)] = 45;
/* 252 */       paramLong = -paramLong;
/*     */     }
/* 254 */     else if (paramLong <= MAX_INT_AS_LONG) {
/* 255 */       return outputInt((int)paramLong, paramArrayOfByte, paramInt);
/*     */     }
/*     */     
/* 258 */     int i = paramInt;
/* 259 */     paramInt += calcLongStrLength(paramLong);
/* 260 */     int j = paramInt;
/*     */     
/*     */     int k;
/* 263 */     while (paramLong > MAX_INT_AS_LONG) {
/* 264 */       j -= 3;
/* 265 */       long l = paramLong / THOUSAND_L;
/* 266 */       k = (int)(paramLong - l * THOUSAND_L);
/* 267 */       outputFullTriplet(k, paramArrayOfByte, j);
/* 268 */       paramLong = l;
/*     */     }
/*     */     
/* 271 */     int m = (int)paramLong;
/* 272 */     while (m >= 1000) {
/* 273 */       j -= 3;
/* 274 */       int n = m / 1000;
/* 275 */       k = m - n * 1000;
/* 276 */       outputFullTriplet(k, paramArrayOfByte, j);
/* 277 */       m = n;
/*     */     }
/* 279 */     outputLeadingTriplet(m, paramArrayOfByte, i);
/* 280 */     return paramInt;
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
/*     */   public static String toString(int paramInt)
/*     */   {
/* 296 */     if (paramInt < sSmallIntStrs.length) {
/* 297 */       if (paramInt >= 0) {
/* 298 */         return sSmallIntStrs[paramInt];
/*     */       }
/* 300 */       int i = -paramInt - 1;
/* 301 */       if (i < sSmallIntStrs2.length) {
/* 302 */         return sSmallIntStrs2[i];
/*     */       }
/*     */     }
/* 305 */     return Integer.toString(paramInt);
/*     */   }
/*     */   
/*     */   public static String toString(long paramLong)
/*     */   {
/* 310 */     if ((paramLong <= 2147483647L) && (paramLong >= -2147483648L))
/*     */     {
/* 312 */       return toString((int)paramLong);
/*     */     }
/* 314 */     return Long.toString(paramLong);
/*     */   }
/*     */   
/*     */   public static String toString(double paramDouble)
/*     */   {
/* 319 */     return Double.toString(paramDouble);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int outputLeadingTriplet(int paramInt1, char[] paramArrayOfChar, int paramInt2)
/*     */   {
/* 330 */     int i = paramInt1 << 2;
/* 331 */     int j = LEADING_TRIPLETS[(i++)];
/* 332 */     if (j != 0) {
/* 333 */       paramArrayOfChar[(paramInt2++)] = j;
/*     */     }
/* 335 */     j = LEADING_TRIPLETS[(i++)];
/* 336 */     if (j != 0) {
/* 337 */       paramArrayOfChar[(paramInt2++)] = j;
/*     */     }
/*     */     
/* 340 */     paramArrayOfChar[(paramInt2++)] = LEADING_TRIPLETS[i];
/* 341 */     return paramInt2;
/*     */   }
/*     */   
/*     */   private static int outputLeadingTriplet(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*     */   {
/* 346 */     int i = paramInt1 << 2;
/* 347 */     int j = LEADING_TRIPLETS[(i++)];
/* 348 */     if (j != 0) {
/* 349 */       paramArrayOfByte[(paramInt2++)] = ((byte)j);
/*     */     }
/* 351 */     j = LEADING_TRIPLETS[(i++)];
/* 352 */     if (j != 0) {
/* 353 */       paramArrayOfByte[(paramInt2++)] = ((byte)j);
/*     */     }
/*     */     
/* 356 */     paramArrayOfByte[(paramInt2++)] = ((byte)LEADING_TRIPLETS[i]);
/* 357 */     return paramInt2;
/*     */   }
/*     */   
/*     */   private static int outputFullTriplet(int paramInt1, char[] paramArrayOfChar, int paramInt2)
/*     */   {
/* 362 */     int i = paramInt1 << 2;
/* 363 */     paramArrayOfChar[(paramInt2++)] = FULL_TRIPLETS[(i++)];
/* 364 */     paramArrayOfChar[(paramInt2++)] = FULL_TRIPLETS[(i++)];
/* 365 */     paramArrayOfChar[(paramInt2++)] = FULL_TRIPLETS[i];
/* 366 */     return paramInt2;
/*     */   }
/*     */   
/*     */   private static int outputFullTriplet(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*     */   {
/* 371 */     int i = paramInt1 << 2;
/* 372 */     paramArrayOfByte[(paramInt2++)] = FULL_TRIPLETS_B[(i++)];
/* 373 */     paramArrayOfByte[(paramInt2++)] = FULL_TRIPLETS_B[(i++)];
/* 374 */     paramArrayOfByte[(paramInt2++)] = FULL_TRIPLETS_B[i];
/* 375 */     return paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int calcLongStrLength(long paramLong)
/*     */   {
/* 385 */     int i = 10;
/* 386 */     long l = TEN_BILLION_L;
/*     */     
/*     */ 
/* 389 */     while ((paramLong >= l) && 
/* 390 */       (i != 19))
/*     */     {
/*     */ 
/* 393 */       i++;
/* 394 */       l = (l << 3) + (l << 1);
/*     */     }
/* 396 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/io/NumberOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */