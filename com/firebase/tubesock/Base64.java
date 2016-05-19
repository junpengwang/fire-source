/*     */ package com.firebase.tubesock;
/*     */ 
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
/*     */ public class Base64
/*     */ {
/*  75 */   private static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
/*  76 */   private static final int[] IA = new int['Ā'];
/*     */   
/*  78 */   static { Arrays.fill(IA, -1);
/*  79 */     int i = 0; for (int iS = CA.length; i < iS; i++)
/*  80 */       IA[CA[i]] = i;
/*  81 */     IA[61] = 0;
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
/*     */   public static final char[] encodeToChar(byte[] sArr, boolean lineSep)
/*     */   {
/*  98 */     int sLen = sArr != null ? sArr.length : 0;
/*  99 */     if (sLen == 0) {
/* 100 */       return new char[0];
/*     */     }
/* 102 */     int eLen = sLen / 3 * 3;
/* 103 */     int cCnt = (sLen - 1) / 3 + 1 << 2;
/* 104 */     int dLen = cCnt + (lineSep ? (cCnt - 1) / 76 << 1 : 0);
/* 105 */     char[] dArr = new char[dLen];
/*     */     
/*     */ 
/* 108 */     int s = 0;int d = 0; for (int cc = 0; s < eLen;)
/*     */     {
/* 110 */       int i = (sArr[(s++)] & 0xFF) << 16 | (sArr[(s++)] & 0xFF) << 8 | sArr[(s++)] & 0xFF;
/*     */       
/*     */ 
/* 113 */       dArr[(d++)] = CA[(i >>> 18 & 0x3F)];
/* 114 */       dArr[(d++)] = CA[(i >>> 12 & 0x3F)];
/* 115 */       dArr[(d++)] = CA[(i >>> 6 & 0x3F)];
/* 116 */       dArr[(d++)] = CA[(i & 0x3F)];
/*     */       
/*     */ 
/* 119 */       if (lineSep) { cc++; if ((cc == 19) && (d < dLen - 2)) {
/* 120 */           dArr[(d++)] = '\r';
/* 121 */           dArr[(d++)] = '\n';
/* 122 */           cc = 0;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 127 */     int left = sLen - eLen;
/* 128 */     if (left > 0)
/*     */     {
/* 130 */       int i = (sArr[eLen] & 0xFF) << 10 | (left == 2 ? (sArr[(sLen - 1)] & 0xFF) << 2 : 0);
/*     */       
/*     */ 
/* 133 */       dArr[(dLen - 4)] = CA[(i >> 12)];
/* 134 */       dArr[(dLen - 3)] = CA[(i >>> 6 & 0x3F)];
/* 135 */       dArr[(dLen - 2)] = (left == 2 ? CA[(i & 0x3F)] : '=');
/* 136 */       dArr[(dLen - 1)] = '=';
/*     */     }
/* 138 */     return dArr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final byte[] decode(char[] sArr)
/*     */   {
/* 150 */     int sLen = sArr != null ? sArr.length : 0;
/* 151 */     if (sLen == 0) {
/* 152 */       return new byte[0];
/*     */     }
/*     */     
/*     */ 
/* 156 */     int sepCnt = 0;
/* 157 */     for (int i = 0; i < sLen; i++) {
/* 158 */       if (IA[sArr[i]] < 0) {
/* 159 */         sepCnt++;
/*     */       }
/*     */     }
/* 162 */     if ((sLen - sepCnt) % 4 != 0) {
/* 163 */       return null;
/*     */     }
/* 165 */     int pad = 0;
/* 166 */     for (int i = sLen; (i > 1) && (IA[sArr[(--i)]] <= 0);) {
/* 167 */       if (sArr[i] == '=')
/* 168 */         pad++;
/*     */     }
/* 170 */     int len = ((sLen - sepCnt) * 6 >> 3) - pad;
/*     */     
/* 172 */     byte[] dArr = new byte[len];
/*     */     
/* 174 */     int s = 0; for (int d = 0; d < len;)
/*     */     {
/* 176 */       int i = 0;
/* 177 */       for (int j = 0; j < 4; j++) {
/* 178 */         int c = IA[sArr[(s++)]];
/* 179 */         if (c >= 0) {
/* 180 */           i |= c << 18 - j * 6;
/*     */         } else {
/* 182 */           j--;
/*     */         }
/*     */       }
/* 185 */       dArr[(d++)] = ((byte)(i >> 16));
/* 186 */       if (d < len) {
/* 187 */         dArr[(d++)] = ((byte)(i >> 8));
/* 188 */         if (d < len)
/* 189 */           dArr[(d++)] = ((byte)i);
/*     */       }
/*     */     }
/* 192 */     return dArr;
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
/*     */   public static final byte[] decodeFast(char[] sArr)
/*     */   {
/* 207 */     int sLen = sArr.length;
/* 208 */     if (sLen == 0) {
/* 209 */       return new byte[0];
/*     */     }
/* 211 */     int sIx = 0;int eIx = sLen - 1;
/*     */     
/*     */ 
/* 214 */     while ((sIx < eIx) && (IA[sArr[sIx]] < 0)) {
/* 215 */       sIx++;
/*     */     }
/*     */     
/* 218 */     while ((eIx > 0) && (IA[sArr[eIx]] < 0)) {
/* 219 */       eIx--;
/*     */     }
/*     */     
/* 222 */     int pad = sArr[eIx] == '=' ? 1 : sArr[(eIx - 1)] == '=' ? 2 : 0;
/* 223 */     int cCnt = eIx - sIx + 1;
/* 224 */     int sepCnt = sLen > 76 ? (sArr[76] == '\r' ? cCnt / 78 : 0) << 1 : 0;
/*     */     
/* 226 */     int len = ((cCnt - sepCnt) * 6 >> 3) - pad;
/* 227 */     byte[] dArr = new byte[len];
/*     */     
/*     */ 
/* 230 */     int d = 0;
/* 231 */     int cc = 0; for (int eLen = len / 3 * 3; d < eLen;)
/*     */     {
/* 233 */       int i = IA[sArr[(sIx++)]] << 18 | IA[sArr[(sIx++)]] << 12 | IA[sArr[(sIx++)]] << 6 | IA[sArr[(sIx++)]];
/*     */       
/*     */ 
/* 236 */       dArr[(d++)] = ((byte)(i >> 16));
/* 237 */       dArr[(d++)] = ((byte)(i >> 8));
/* 238 */       dArr[(d++)] = ((byte)i);
/*     */       
/*     */ 
/* 241 */       if (sepCnt > 0) { cc++; if (cc == 19) {
/* 242 */           sIx += 2;
/* 243 */           cc = 0;
/*     */         }
/*     */       }
/*     */     }
/* 247 */     if (d < len)
/*     */     {
/* 249 */       int i = 0;
/* 250 */       for (int j = 0; sIx <= eIx - pad; j++) {
/* 251 */         i |= IA[sArr[(sIx++)]] << 18 - j * 6;
/*     */       }
/* 253 */       for (int r = 16; d < len; r -= 8) {
/* 254 */         dArr[(d++)] = ((byte)(i >> r));
/*     */       }
/*     */     }
/* 257 */     return dArr;
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
/*     */   public static final byte[] encodeToByte(byte[] sArr, boolean lineSep)
/*     */   {
/* 274 */     int sLen = sArr != null ? sArr.length : 0;
/* 275 */     if (sLen == 0) {
/* 276 */       return new byte[0];
/*     */     }
/* 278 */     int eLen = sLen / 3 * 3;
/* 279 */     int cCnt = (sLen - 1) / 3 + 1 << 2;
/* 280 */     int dLen = cCnt + (lineSep ? (cCnt - 1) / 76 << 1 : 0);
/* 281 */     byte[] dArr = new byte[dLen];
/*     */     
/*     */ 
/* 284 */     int s = 0;int d = 0; for (int cc = 0; s < eLen;)
/*     */     {
/* 286 */       int i = (sArr[(s++)] & 0xFF) << 16 | (sArr[(s++)] & 0xFF) << 8 | sArr[(s++)] & 0xFF;
/*     */       
/*     */ 
/* 289 */       dArr[(d++)] = ((byte)CA[(i >>> 18 & 0x3F)]);
/* 290 */       dArr[(d++)] = ((byte)CA[(i >>> 12 & 0x3F)]);
/* 291 */       dArr[(d++)] = ((byte)CA[(i >>> 6 & 0x3F)]);
/* 292 */       dArr[(d++)] = ((byte)CA[(i & 0x3F)]);
/*     */       
/*     */ 
/* 295 */       if (lineSep) { cc++; if ((cc == 19) && (d < dLen - 2)) {
/* 296 */           dArr[(d++)] = 13;
/* 297 */           dArr[(d++)] = 10;
/* 298 */           cc = 0;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 303 */     int left = sLen - eLen;
/* 304 */     if (left > 0)
/*     */     {
/* 306 */       int i = (sArr[eLen] & 0xFF) << 10 | (left == 2 ? (sArr[(sLen - 1)] & 0xFF) << 2 : 0);
/*     */       
/*     */ 
/* 309 */       dArr[(dLen - 4)] = ((byte)CA[(i >> 12)]);
/* 310 */       dArr[(dLen - 3)] = ((byte)CA[(i >>> 6 & 0x3F)]);
/* 311 */       dArr[(dLen - 2)] = (left == 2 ? (byte)CA[(i & 0x3F)] : 61);
/* 312 */       dArr[(dLen - 1)] = 61;
/*     */     }
/* 314 */     return dArr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final byte[] decode(byte[] sArr)
/*     */   {
/* 326 */     int sLen = sArr.length;
/*     */     
/*     */ 
/*     */ 
/* 330 */     int sepCnt = 0;
/* 331 */     for (int i = 0; i < sLen; i++) {
/* 332 */       if (IA[(sArr[i] & 0xFF)] < 0) {
/* 333 */         sepCnt++;
/*     */       }
/*     */     }
/* 336 */     if ((sLen - sepCnt) % 4 != 0) {
/* 337 */       return null;
/*     */     }
/* 339 */     int pad = 0;
/* 340 */     for (int i = sLen; (i > 1) && (IA[(sArr[(--i)] & 0xFF)] <= 0);) {
/* 341 */       if (sArr[i] == 61)
/* 342 */         pad++;
/*     */     }
/* 344 */     int len = ((sLen - sepCnt) * 6 >> 3) - pad;
/*     */     
/* 346 */     byte[] dArr = new byte[len];
/*     */     
/* 348 */     int s = 0; for (int d = 0; d < len;)
/*     */     {
/* 350 */       int i = 0;
/* 351 */       for (int j = 0; j < 4; j++) {
/* 352 */         int c = IA[(sArr[(s++)] & 0xFF)];
/* 353 */         if (c >= 0) {
/* 354 */           i |= c << 18 - j * 6;
/*     */         } else {
/* 356 */           j--;
/*     */         }
/*     */       }
/*     */       
/* 360 */       dArr[(d++)] = ((byte)(i >> 16));
/* 361 */       if (d < len) {
/* 362 */         dArr[(d++)] = ((byte)(i >> 8));
/* 363 */         if (d < len) {
/* 364 */           dArr[(d++)] = ((byte)i);
/*     */         }
/*     */       }
/*     */     }
/* 368 */     return dArr;
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
/*     */   public static final byte[] decodeFast(byte[] sArr)
/*     */   {
/* 384 */     int sLen = sArr.length;
/* 385 */     if (sLen == 0) {
/* 386 */       return new byte[0];
/*     */     }
/* 388 */     int sIx = 0;int eIx = sLen - 1;
/*     */     
/*     */ 
/* 391 */     while ((sIx < eIx) && (IA[(sArr[sIx] & 0xFF)] < 0)) {
/* 392 */       sIx++;
/*     */     }
/*     */     
/* 395 */     while ((eIx > 0) && (IA[(sArr[eIx] & 0xFF)] < 0)) {
/* 396 */       eIx--;
/*     */     }
/*     */     
/* 399 */     int pad = sArr[eIx] == 61 ? 1 : sArr[(eIx - 1)] == 61 ? 2 : 0;
/* 400 */     int cCnt = eIx - sIx + 1;
/* 401 */     int sepCnt = sLen > 76 ? (sArr[76] == 13 ? cCnt / 78 : 0) << 1 : 0;
/*     */     
/* 403 */     int len = ((cCnt - sepCnt) * 6 >> 3) - pad;
/* 404 */     byte[] dArr = new byte[len];
/*     */     
/*     */ 
/* 407 */     int d = 0;
/* 408 */     int cc = 0; for (int eLen = len / 3 * 3; d < eLen;)
/*     */     {
/* 410 */       int i = IA[sArr[(sIx++)]] << 18 | IA[sArr[(sIx++)]] << 12 | IA[sArr[(sIx++)]] << 6 | IA[sArr[(sIx++)]];
/*     */       
/*     */ 
/* 413 */       dArr[(d++)] = ((byte)(i >> 16));
/* 414 */       dArr[(d++)] = ((byte)(i >> 8));
/* 415 */       dArr[(d++)] = ((byte)i);
/*     */       
/*     */ 
/* 418 */       if (sepCnt > 0) { cc++; if (cc == 19) {
/* 419 */           sIx += 2;
/* 420 */           cc = 0;
/*     */         }
/*     */       }
/*     */     }
/* 424 */     if (d < len)
/*     */     {
/* 426 */       int i = 0;
/* 427 */       for (int j = 0; sIx <= eIx - pad; j++) {
/* 428 */         i |= IA[sArr[(sIx++)]] << 18 - j * 6;
/*     */       }
/* 430 */       for (int r = 16; d < len; r -= 8) {
/* 431 */         dArr[(d++)] = ((byte)(i >> r));
/*     */       }
/*     */     }
/* 434 */     return dArr;
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
/*     */   public static final String encodeToString(byte[] sArr, boolean lineSep)
/*     */   {
/* 451 */     return new String(encodeToChar(sArr, lineSep));
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
/*     */   public static final byte[] decode(String str)
/*     */   {
/* 465 */     int sLen = str != null ? str.length() : 0;
/* 466 */     if (sLen == 0) {
/* 467 */       return new byte[0];
/*     */     }
/*     */     
/*     */ 
/* 471 */     int sepCnt = 0;
/* 472 */     for (int i = 0; i < sLen; i++) {
/* 473 */       if (IA[str.charAt(i)] < 0) {
/* 474 */         sepCnt++;
/*     */       }
/*     */     }
/* 477 */     if ((sLen - sepCnt) % 4 != 0) {
/* 478 */       return null;
/*     */     }
/*     */     
/* 481 */     int pad = 0;
/* 482 */     for (int i = sLen; (i > 1) && (IA[str.charAt(--i)] <= 0);) {
/* 483 */       if (str.charAt(i) == '=')
/* 484 */         pad++;
/*     */     }
/* 486 */     int len = ((sLen - sepCnt) * 6 >> 3) - pad;
/*     */     
/* 488 */     byte[] dArr = new byte[len];
/*     */     
/* 490 */     int s = 0; for (int d = 0; d < len;)
/*     */     {
/* 492 */       int i = 0;
/* 493 */       for (int j = 0; j < 4; j++) {
/* 494 */         int c = IA[str.charAt(s++)];
/* 495 */         if (c >= 0) {
/* 496 */           i |= c << 18 - j * 6;
/*     */         } else {
/* 498 */           j--;
/*     */         }
/*     */       }
/* 501 */       dArr[(d++)] = ((byte)(i >> 16));
/* 502 */       if (d < len) {
/* 503 */         dArr[(d++)] = ((byte)(i >> 8));
/* 504 */         if (d < len)
/* 505 */           dArr[(d++)] = ((byte)i);
/*     */       }
/*     */     }
/* 508 */     return dArr;
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
/*     */   public static final byte[] decodeFast(String s)
/*     */   {
/* 523 */     int sLen = s.length();
/* 524 */     if (sLen == 0) {
/* 525 */       return new byte[0];
/*     */     }
/* 527 */     int sIx = 0;int eIx = sLen - 1;
/*     */     
/*     */ 
/* 530 */     while ((sIx < eIx) && (IA[(s.charAt(sIx) & 0xFF)] < 0)) {
/* 531 */       sIx++;
/*     */     }
/*     */     
/* 534 */     while ((eIx > 0) && (IA[(s.charAt(eIx) & 0xFF)] < 0)) {
/* 535 */       eIx--;
/*     */     }
/*     */     
/* 538 */     int pad = s.charAt(eIx) == '=' ? 1 : s.charAt(eIx - 1) == '=' ? 2 : 0;
/* 539 */     int cCnt = eIx - sIx + 1;
/* 540 */     int sepCnt = sLen > 76 ? (s.charAt(76) == '\r' ? cCnt / 78 : 0) << 1 : 0;
/*     */     
/* 542 */     int len = ((cCnt - sepCnt) * 6 >> 3) - pad;
/* 543 */     byte[] dArr = new byte[len];
/*     */     
/*     */ 
/* 546 */     int d = 0;
/* 547 */     int cc = 0; for (int eLen = len / 3 * 3; d < eLen;)
/*     */     {
/* 549 */       int i = IA[s.charAt(sIx++)] << 18 | IA[s.charAt(sIx++)] << 12 | IA[s.charAt(sIx++)] << 6 | IA[s.charAt(sIx++)];
/*     */       
/*     */ 
/* 552 */       dArr[(d++)] = ((byte)(i >> 16));
/* 553 */       dArr[(d++)] = ((byte)(i >> 8));
/* 554 */       dArr[(d++)] = ((byte)i);
/*     */       
/*     */ 
/* 557 */       if (sepCnt > 0) { cc++; if (cc == 19) {
/* 558 */           sIx += 2;
/* 559 */           cc = 0;
/*     */         }
/*     */       }
/*     */     }
/* 563 */     if (d < len)
/*     */     {
/* 565 */       int i = 0;
/* 566 */       for (int j = 0; sIx <= eIx - pad; j++) {
/* 567 */         i |= IA[s.charAt(sIx++)] << 18 - j * 6;
/*     */       }
/* 569 */       for (int r = 16; d < len; r -= 8) {
/* 570 */         dArr[(d++)] = ((byte)(i >> r));
/*     */       }
/*     */     }
/* 573 */     return dArr;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/tubesock/Base64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */