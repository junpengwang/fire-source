/*     */ package com.shaded.fasterxml.jackson.core.io;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ public final class CharTypes
/*     */ {
/*   8 */   private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
/*     */   private static final byte[] HEX_BYTES;
/*     */   
/*  11 */   static { int i = HEX_CHARS.length;
/*  12 */     HEX_BYTES = new byte[i];
/*  13 */     for (int k = 0; k < i; k++) {
/*  14 */       HEX_BYTES[k] = ((byte)HEX_CHARS[k]);
/*     */     }
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
/*  29 */     int[] arrayOfInt = new int['Ā'];
/*     */     
/*  31 */     for (k = 0; k < 32; k++) {
/*  32 */       arrayOfInt[k] = -1;
/*     */     }
/*     */     
/*  35 */     arrayOfInt[34] = 1;
/*  36 */     arrayOfInt[92] = 1;
/*  37 */     sInputCodes = arrayOfInt;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  46 */     arrayOfInt = new int[sInputCodes.length];
/*  47 */     System.arraycopy(sInputCodes, 0, arrayOfInt, 0, sInputCodes.length);
/*  48 */     for (k = 128; k < 256; k++)
/*     */     {
/*     */       int m;
/*     */       
/*  52 */       if ((k & 0xE0) == 192) {
/*  53 */         m = 2;
/*  54 */       } else if ((k & 0xF0) == 224) {
/*  55 */         m = 3;
/*  56 */       } else if ((k & 0xF8) == 240)
/*     */       {
/*  58 */         m = 4;
/*     */       }
/*     */       else {
/*  61 */         m = -1;
/*     */       }
/*  63 */       arrayOfInt[k] = m;
/*     */     }
/*  65 */     sInputCodesUtf8 = arrayOfInt;
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
/*  76 */     arrayOfInt = new int['Ā'];
/*     */     
/*  78 */     Arrays.fill(arrayOfInt, -1);
/*     */     
/*  80 */     for (k = 33; k < 256; k++) {
/*  81 */       if (Character.isJavaIdentifierPart((char)k)) {
/*  82 */         arrayOfInt[k] = 0;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  88 */     arrayOfInt[64] = 0;
/*  89 */     arrayOfInt[35] = 0;
/*  90 */     arrayOfInt[42] = 0;
/*  91 */     arrayOfInt[45] = 0;
/*  92 */     arrayOfInt[43] = 0;
/*  93 */     sInputCodesJsNames = arrayOfInt;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 103 */     arrayOfInt = new int['Ā'];
/*     */     
/* 105 */     System.arraycopy(sInputCodesJsNames, 0, arrayOfInt, 0, sInputCodesJsNames.length);
/* 106 */     Arrays.fill(arrayOfInt, 128, 128, 0);
/* 107 */     sInputCodesUtf8JsNames = arrayOfInt;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 114 */     sInputCodesComment = new int['Ā'];
/*     */     
/*     */ 
/* 117 */     System.arraycopy(sInputCodesUtf8, 128, sInputCodesComment, 128, 128);
/*     */     
/*     */ 
/* 120 */     Arrays.fill(sInputCodesComment, 0, 32, -1);
/* 121 */     sInputCodesComment[9] = 0;
/* 122 */     sInputCodesComment[10] = 10;
/* 123 */     sInputCodesComment[13] = 13;
/* 124 */     sInputCodesComment[42] = 42;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 133 */     arrayOfInt = new int[''];
/*     */     
/* 135 */     for (k = 0; k < 32; k++)
/*     */     {
/* 137 */       arrayOfInt[k] = -1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 142 */     arrayOfInt[34] = 34;
/* 143 */     arrayOfInt[92] = 92;
/*     */     
/* 145 */     arrayOfInt[8] = 98;
/* 146 */     arrayOfInt[9] = 116;
/* 147 */     arrayOfInt[12] = 102;
/* 148 */     arrayOfInt[10] = 110;
/* 149 */     arrayOfInt[13] = 114;
/* 150 */     sOutputEscapes128 = arrayOfInt;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 158 */     sHexValues = new int[''];
/*     */     
/* 160 */     Arrays.fill(sHexValues, -1);
/* 161 */     for (int j = 0; j < 10; j++) {
/* 162 */       sHexValues[(48 + j)] = j;
/*     */     }
/* 164 */     for (j = 0; j < 6; j++) {
/* 165 */       sHexValues[(97 + j)] = (10 + j);
/* 166 */       sHexValues[(65 + j)] = (10 + j);
/*     */     } }
/*     */   
/*     */   static final int[] sInputCodes;
/* 170 */   public static int[] getInputCodeLatin1() { return sInputCodes; }
/* 171 */   public static int[] getInputCodeUtf8() { return sInputCodesUtf8; }
/*     */   
/* 173 */   public static int[] getInputCodeLatin1JsNames() { return sInputCodesJsNames; }
/* 174 */   public static int[] getInputCodeUtf8JsNames() { return sInputCodesUtf8JsNames; }
/*     */   
/* 176 */   public static int[] getInputCodeComment() { return sInputCodesComment; }
/*     */   
/*     */   static final int[] sInputCodesUtf8;
/*     */   static final int[] sInputCodesJsNames;
/*     */   static final int[] sInputCodesUtf8JsNames;
/*     */   static final int[] sInputCodesComment;
/*     */   static final int[] sOutputEscapes128;
/*     */   static final int[] sHexValues;
/*     */   public static int[] get7BitOutputEscapes() {
/* 185 */     return sOutputEscapes128;
/*     */   }
/*     */   
/*     */   public static int charToHex(int paramInt) {
/* 189 */     return paramInt > 127 ? -1 : sHexValues[paramInt];
/*     */   }
/*     */   
/*     */   public static void appendQuoted(StringBuilder paramStringBuilder, String paramString)
/*     */   {
/* 194 */     int[] arrayOfInt = sOutputEscapes128;
/* 195 */     int i = arrayOfInt.length;
/* 196 */     int j = 0; for (int k = paramString.length(); j < k; j++) {
/* 197 */       int m = paramString.charAt(j);
/* 198 */       if ((m >= i) || (arrayOfInt[m] == 0)) {
/* 199 */         paramStringBuilder.append(m);
/*     */       }
/*     */       else {
/* 202 */         paramStringBuilder.append('\\');
/* 203 */         int n = arrayOfInt[m];
/* 204 */         if (n < 0)
/*     */         {
/* 206 */           paramStringBuilder.append('u');
/* 207 */           paramStringBuilder.append('0');
/* 208 */           paramStringBuilder.append('0');
/* 209 */           int i1 = -(n + 1);
/* 210 */           paramStringBuilder.append(HEX_CHARS[(i1 >> 4)]);
/* 211 */           paramStringBuilder.append(HEX_CHARS[(i1 & 0xF)]);
/*     */         } else {
/* 213 */           paramStringBuilder.append((char)n);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static char[] copyHexChars() {
/* 220 */     return (char[])HEX_CHARS.clone();
/*     */   }
/*     */   
/*     */   public static byte[] copyHexBytes()
/*     */   {
/* 225 */     return (byte[])HEX_BYTES.clone();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/io/CharTypes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */