/*     */ package com.shaded.fasterxml.jackson.core.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UTF8Writer
/*     */   extends Writer
/*     */ {
/*     */   static final int SURR1_FIRST = 55296;
/*     */   static final int SURR1_LAST = 56319;
/*     */   static final int SURR2_FIRST = 56320;
/*     */   static final int SURR2_LAST = 57343;
/*     */   private final IOContext _context;
/*     */   private OutputStream _out;
/*     */   private byte[] _outBuffer;
/*     */   private final int _outBufferEnd;
/*     */   private int _outPtr;
/*  27 */   private int _surrogate = 0;
/*     */   
/*     */   public UTF8Writer(IOContext paramIOContext, OutputStream paramOutputStream)
/*     */   {
/*  31 */     this._context = paramIOContext;
/*  32 */     this._out = paramOutputStream;
/*     */     
/*  34 */     this._outBuffer = paramIOContext.allocWriteEncodingBuffer();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  39 */     this._outBufferEnd = (this._outBuffer.length - 4);
/*  40 */     this._outPtr = 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public Writer append(char paramChar)
/*     */     throws IOException
/*     */   {
/*  47 */     write(paramChar);
/*  48 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  55 */     if (this._out != null) {
/*  56 */       if (this._outPtr > 0) {
/*  57 */         this._out.write(this._outBuffer, 0, this._outPtr);
/*  58 */         this._outPtr = 0;
/*     */       }
/*  60 */       OutputStream localOutputStream = this._out;
/*  61 */       this._out = null;
/*     */       
/*  63 */       byte[] arrayOfByte = this._outBuffer;
/*  64 */       if (arrayOfByte != null) {
/*  65 */         this._outBuffer = null;
/*  66 */         this._context.releaseWriteEncodingBuffer(arrayOfByte);
/*     */       }
/*     */       
/*  69 */       localOutputStream.close();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  74 */       int i = this._surrogate;
/*  75 */       this._surrogate = 0;
/*  76 */       if (i > 0) {
/*  77 */         illegalSurrogate(i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/*  86 */     if (this._out != null) {
/*  87 */       if (this._outPtr > 0) {
/*  88 */         this._out.write(this._outBuffer, 0, this._outPtr);
/*  89 */         this._outPtr = 0;
/*     */       }
/*  91 */       this._out.flush();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void write(char[] paramArrayOfChar)
/*     */     throws IOException
/*     */   {
/*  99 */     write(paramArrayOfChar, 0, paramArrayOfChar.length);
/*     */   }
/*     */   
/*     */ 
/*     */   public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 106 */     if (paramInt2 < 2) {
/* 107 */       if (paramInt2 == 1) {
/* 108 */         write(paramArrayOfChar[paramInt1]);
/*     */       }
/* 110 */       return;
/*     */     }
/*     */     
/*     */ 
/* 114 */     if (this._surrogate > 0) {
/* 115 */       i = paramArrayOfChar[(paramInt1++)];
/* 116 */       paramInt2--;
/* 117 */       write(convertSurrogate(i));
/*     */     }
/*     */     
/*     */ 
/* 121 */     int i = this._outPtr;
/* 122 */     byte[] arrayOfByte = this._outBuffer;
/* 123 */     int j = this._outBufferEnd;
/*     */     
/*     */ 
/* 126 */     paramInt2 += paramInt1;
/*     */     
/*     */ 
/* 129 */     while (paramInt1 < paramInt2)
/*     */     {
/*     */ 
/*     */ 
/* 133 */       if (i >= j) {
/* 134 */         this._out.write(arrayOfByte, 0, i);
/* 135 */         i = 0;
/*     */       }
/*     */       
/* 138 */       int k = paramArrayOfChar[(paramInt1++)];
/*     */       
/* 140 */       if (k < 128) {
/* 141 */         arrayOfByte[(i++)] = ((byte)k);
/*     */         
/* 143 */         int m = paramInt2 - paramInt1;
/* 144 */         int n = j - i;
/*     */         
/* 146 */         if (m > n) {
/* 147 */           m = n;
/*     */         }
/* 149 */         m += paramInt1;
/*     */         
/*     */ 
/* 152 */         while (paramInt1 < m)
/*     */         {
/*     */ 
/* 155 */           k = paramArrayOfChar[(paramInt1++)];
/* 156 */           if (k >= 128) {
/*     */             break label193;
/*     */           }
/* 159 */           arrayOfByte[(i++)] = ((byte)k);
/*     */         }
/*     */       }
/*     */       else {
/*     */         label193:
/* 164 */         if (k < 2048) {
/* 165 */           arrayOfByte[(i++)] = ((byte)(0xC0 | k >> 6));
/* 166 */           arrayOfByte[(i++)] = ((byte)(0x80 | k & 0x3F));
/*     */ 
/*     */         }
/* 169 */         else if ((k < 55296) || (k > 57343)) {
/* 170 */           arrayOfByte[(i++)] = ((byte)(0xE0 | k >> 12));
/* 171 */           arrayOfByte[(i++)] = ((byte)(0x80 | k >> 6 & 0x3F));
/* 172 */           arrayOfByte[(i++)] = ((byte)(0x80 | k & 0x3F));
/*     */         }
/*     */         else
/*     */         {
/* 176 */           if (k > 56319) {
/* 177 */             this._outPtr = i;
/* 178 */             illegalSurrogate(k);
/*     */           }
/* 180 */           this._surrogate = k;
/*     */           
/* 182 */           if (paramInt1 >= paramInt2) {
/*     */             break;
/*     */           }
/* 185 */           k = convertSurrogate(paramArrayOfChar[(paramInt1++)]);
/* 186 */           if (k > 1114111) {
/* 187 */             this._outPtr = i;
/* 188 */             illegalSurrogate(k);
/*     */           }
/* 190 */           arrayOfByte[(i++)] = ((byte)(0xF0 | k >> 18));
/* 191 */           arrayOfByte[(i++)] = ((byte)(0x80 | k >> 12 & 0x3F));
/* 192 */           arrayOfByte[(i++)] = ((byte)(0x80 | k >> 6 & 0x3F));
/* 193 */           arrayOfByte[(i++)] = ((byte)(0x80 | k & 0x3F));
/*     */         }
/*     */       } }
/* 196 */     this._outPtr = i;
/*     */   }
/*     */   
/*     */ 
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/* 203 */     if (this._surrogate > 0) {
/* 204 */       paramInt = convertSurrogate(paramInt);
/*     */     }
/* 206 */     else if ((paramInt >= 55296) && (paramInt <= 57343))
/*     */     {
/* 208 */       if (paramInt > 56319) {
/* 209 */         illegalSurrogate(paramInt);
/*     */       }
/*     */       
/* 212 */       this._surrogate = paramInt;
/* 213 */       return;
/*     */     }
/*     */     
/* 216 */     if (this._outPtr >= this._outBufferEnd) {
/* 217 */       this._out.write(this._outBuffer, 0, this._outPtr);
/* 218 */       this._outPtr = 0;
/*     */     }
/*     */     
/* 221 */     if (paramInt < 128) {
/* 222 */       this._outBuffer[(this._outPtr++)] = ((byte)paramInt);
/*     */     } else {
/* 224 */       int i = this._outPtr;
/* 225 */       if (paramInt < 2048) {
/* 226 */         this._outBuffer[(i++)] = ((byte)(0xC0 | paramInt >> 6));
/* 227 */         this._outBuffer[(i++)] = ((byte)(0x80 | paramInt & 0x3F));
/* 228 */       } else if (paramInt <= 65535) {
/* 229 */         this._outBuffer[(i++)] = ((byte)(0xE0 | paramInt >> 12));
/* 230 */         this._outBuffer[(i++)] = ((byte)(0x80 | paramInt >> 6 & 0x3F));
/* 231 */         this._outBuffer[(i++)] = ((byte)(0x80 | paramInt & 0x3F));
/*     */       } else {
/* 233 */         if (paramInt > 1114111) {
/* 234 */           illegalSurrogate(paramInt);
/*     */         }
/* 236 */         this._outBuffer[(i++)] = ((byte)(0xF0 | paramInt >> 18));
/* 237 */         this._outBuffer[(i++)] = ((byte)(0x80 | paramInt >> 12 & 0x3F));
/* 238 */         this._outBuffer[(i++)] = ((byte)(0x80 | paramInt >> 6 & 0x3F));
/* 239 */         this._outBuffer[(i++)] = ((byte)(0x80 | paramInt & 0x3F));
/*     */       }
/* 241 */       this._outPtr = i;
/*     */     }
/*     */   }
/*     */   
/*     */   public void write(String paramString)
/*     */     throws IOException
/*     */   {
/* 248 */     write(paramString, 0, paramString.length());
/*     */   }
/*     */   
/*     */   public void write(String paramString, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 254 */     if (paramInt2 < 2) {
/* 255 */       if (paramInt2 == 1) {
/* 256 */         write(paramString.charAt(paramInt1));
/*     */       }
/* 258 */       return;
/*     */     }
/*     */     
/*     */ 
/* 262 */     if (this._surrogate > 0) {
/* 263 */       i = paramString.charAt(paramInt1++);
/* 264 */       paramInt2--;
/* 265 */       write(convertSurrogate(i));
/*     */     }
/*     */     
/*     */ 
/* 269 */     int i = this._outPtr;
/* 270 */     byte[] arrayOfByte = this._outBuffer;
/* 271 */     int j = this._outBufferEnd;
/*     */     
/*     */ 
/* 274 */     paramInt2 += paramInt1;
/*     */     
/*     */ 
/* 277 */     while (paramInt1 < paramInt2)
/*     */     {
/*     */ 
/*     */ 
/* 281 */       if (i >= j) {
/* 282 */         this._out.write(arrayOfByte, 0, i);
/* 283 */         i = 0;
/*     */       }
/*     */       
/* 286 */       int k = paramString.charAt(paramInt1++);
/*     */       
/* 288 */       if (k < 128) {
/* 289 */         arrayOfByte[(i++)] = ((byte)k);
/*     */         
/* 291 */         int m = paramInt2 - paramInt1;
/* 292 */         int n = j - i;
/*     */         
/* 294 */         if (m > n) {
/* 295 */           m = n;
/*     */         }
/* 297 */         m += paramInt1;
/*     */         
/*     */ 
/* 300 */         while (paramInt1 < m)
/*     */         {
/*     */ 
/* 303 */           k = paramString.charAt(paramInt1++);
/* 304 */           if (k >= 128) {
/*     */             break label201;
/*     */           }
/* 307 */           arrayOfByte[(i++)] = ((byte)k);
/*     */         }
/*     */       }
/*     */       else {
/*     */         label201:
/* 312 */         if (k < 2048) {
/* 313 */           arrayOfByte[(i++)] = ((byte)(0xC0 | k >> 6));
/* 314 */           arrayOfByte[(i++)] = ((byte)(0x80 | k & 0x3F));
/*     */ 
/*     */         }
/* 317 */         else if ((k < 55296) || (k > 57343)) {
/* 318 */           arrayOfByte[(i++)] = ((byte)(0xE0 | k >> 12));
/* 319 */           arrayOfByte[(i++)] = ((byte)(0x80 | k >> 6 & 0x3F));
/* 320 */           arrayOfByte[(i++)] = ((byte)(0x80 | k & 0x3F));
/*     */         }
/*     */         else
/*     */         {
/* 324 */           if (k > 56319) {
/* 325 */             this._outPtr = i;
/* 326 */             illegalSurrogate(k);
/*     */           }
/* 328 */           this._surrogate = k;
/*     */           
/* 330 */           if (paramInt1 >= paramInt2) {
/*     */             break;
/*     */           }
/* 333 */           k = convertSurrogate(paramString.charAt(paramInt1++));
/* 334 */           if (k > 1114111) {
/* 335 */             this._outPtr = i;
/* 336 */             illegalSurrogate(k);
/*     */           }
/* 338 */           arrayOfByte[(i++)] = ((byte)(0xF0 | k >> 18));
/* 339 */           arrayOfByte[(i++)] = ((byte)(0x80 | k >> 12 & 0x3F));
/* 340 */           arrayOfByte[(i++)] = ((byte)(0x80 | k >> 6 & 0x3F));
/* 341 */           arrayOfByte[(i++)] = ((byte)(0x80 | k & 0x3F));
/*     */         }
/*     */       } }
/* 344 */     this._outPtr = i;
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
/*     */   protected int convertSurrogate(int paramInt)
/*     */     throws IOException
/*     */   {
/* 359 */     int i = this._surrogate;
/* 360 */     this._surrogate = 0;
/*     */     
/*     */ 
/* 363 */     if ((paramInt < 56320) || (paramInt > 57343)) {
/* 364 */       throw new IOException("Broken surrogate pair: first char 0x" + Integer.toHexString(i) + ", second 0x" + Integer.toHexString(paramInt) + "; illegal combination");
/*     */     }
/* 366 */     return 65536 + (i - 55296 << 10) + (paramInt - 56320);
/*     */   }
/*     */   
/*     */   protected static void illegalSurrogate(int paramInt) throws IOException {
/* 370 */     throw new IOException(illegalSurrogateDesc(paramInt));
/*     */   }
/*     */   
/*     */   protected static String illegalSurrogateDesc(int paramInt)
/*     */   {
/* 375 */     if (paramInt > 1114111) {
/* 376 */       return "Illegal character point (0x" + Integer.toHexString(paramInt) + ") to output; max is 0x10FFFF as per RFC 4627";
/*     */     }
/* 378 */     if (paramInt >= 55296) {
/* 379 */       if (paramInt <= 56319) {
/* 380 */         return "Unmatched first part of surrogate pair (0x" + Integer.toHexString(paramInt) + ")";
/*     */       }
/* 382 */       return "Unmatched second part of surrogate pair (0x" + Integer.toHexString(paramInt) + ")";
/*     */     }
/*     */     
/* 385 */     return "Illegal character point (0x" + Integer.toHexString(paramInt) + ") to output";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/io/UTF8Writer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */