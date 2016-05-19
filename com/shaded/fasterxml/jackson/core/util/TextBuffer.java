/*     */ package com.shaded.fasterxml.jackson.core.util;
/*     */ 
/*     */ import com.shaded.fasterxml.jackson.core.io.NumberInput;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.ArrayList;
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
/*     */ public final class TextBuffer
/*     */ {
/*  28 */   static final char[] NO_CHARS = new char[0];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int MIN_SEGMENT_LEN = 1000;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int MAX_SEGMENT_LEN = 262144;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final BufferRecycler _allocator;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private char[] _inputBuffer;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int _inputStart;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int _inputLen;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ArrayList<char[]> _segments;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  84 */   private boolean _hasSegments = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int _segmentSize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private char[] _currentSegment;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int _currentSize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String _resultString;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private char[] _resultArray;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TextBuffer(BufferRecycler paramBufferRecycler)
/*     */   {
/* 122 */     this._allocator = paramBufferRecycler;
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
/*     */   public void releaseBuffers()
/*     */   {
/* 136 */     if (this._allocator == null) {
/* 137 */       resetWithEmpty();
/*     */     }
/* 139 */     else if (this._currentSegment != null)
/*     */     {
/* 141 */       resetWithEmpty();
/*     */       
/* 143 */       char[] arrayOfChar = this._currentSegment;
/* 144 */       this._currentSegment = null;
/* 145 */       this._allocator.releaseCharBuffer(BufferRecycler.CharBufferType.TEXT_BUFFER, arrayOfChar);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resetWithEmpty()
/*     */   {
/* 156 */     this._inputStart = -1;
/* 157 */     this._currentSize = 0;
/* 158 */     this._inputLen = 0;
/*     */     
/* 160 */     this._inputBuffer = null;
/* 161 */     this._resultString = null;
/* 162 */     this._resultArray = null;
/*     */     
/*     */ 
/* 165 */     if (this._hasSegments) {
/* 166 */       clearSegments();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resetWithShared(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 179 */     this._resultString = null;
/* 180 */     this._resultArray = null;
/*     */     
/*     */ 
/* 183 */     this._inputBuffer = paramArrayOfChar;
/* 184 */     this._inputStart = paramInt1;
/* 185 */     this._inputLen = paramInt2;
/*     */     
/*     */ 
/* 188 */     if (this._hasSegments) {
/* 189 */       clearSegments();
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetWithCopy(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 195 */     this._inputBuffer = null;
/* 196 */     this._inputStart = -1;
/* 197 */     this._inputLen = 0;
/*     */     
/* 199 */     this._resultString = null;
/* 200 */     this._resultArray = null;
/*     */     
/*     */ 
/* 203 */     if (this._hasSegments) {
/* 204 */       clearSegments();
/* 205 */     } else if (this._currentSegment == null) {
/* 206 */       this._currentSegment = findBuffer(paramInt2);
/*     */     }
/* 208 */     this._currentSize = (this._segmentSize = 0);
/* 209 */     append(paramArrayOfChar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void resetWithString(String paramString)
/*     */   {
/* 214 */     this._inputBuffer = null;
/* 215 */     this._inputStart = -1;
/* 216 */     this._inputLen = 0;
/*     */     
/* 218 */     this._resultString = paramString;
/* 219 */     this._resultArray = null;
/*     */     
/* 221 */     if (this._hasSegments) {
/* 222 */       clearSegments();
/*     */     }
/* 224 */     this._currentSize = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private char[] findBuffer(int paramInt)
/*     */   {
/* 234 */     if (this._allocator != null) {
/* 235 */       return this._allocator.allocCharBuffer(BufferRecycler.CharBufferType.TEXT_BUFFER, paramInt);
/*     */     }
/* 237 */     return new char[Math.max(paramInt, 1000)];
/*     */   }
/*     */   
/*     */   private void clearSegments()
/*     */   {
/* 242 */     this._hasSegments = false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 250 */     this._segments.clear();
/* 251 */     this._currentSize = (this._segmentSize = 0);
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
/*     */   public int size()
/*     */   {
/* 264 */     if (this._inputStart >= 0) {
/* 265 */       return this._inputLen;
/*     */     }
/* 267 */     if (this._resultArray != null) {
/* 268 */       return this._resultArray.length;
/*     */     }
/* 270 */     if (this._resultString != null) {
/* 271 */       return this._resultString.length();
/*     */     }
/*     */     
/* 274 */     return this._segmentSize + this._currentSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getTextOffset()
/*     */   {
/* 283 */     return this._inputStart >= 0 ? this._inputStart : 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasTextAsCharacters()
/*     */   {
/* 293 */     if ((this._inputStart >= 0) || (this._resultArray != null)) {
/* 294 */       return true;
/*     */     }
/*     */     
/* 297 */     if (this._resultString != null) {
/* 298 */       return false;
/*     */     }
/* 300 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public char[] getTextBuffer()
/*     */   {
/* 306 */     if (this._inputStart >= 0) {
/* 307 */       return this._inputBuffer;
/*     */     }
/* 309 */     if (this._resultArray != null) {
/* 310 */       return this._resultArray;
/*     */     }
/* 312 */     if (this._resultString != null) {
/* 313 */       return this._resultArray = this._resultString.toCharArray();
/*     */     }
/*     */     
/* 316 */     if (!this._hasSegments) {
/* 317 */       return this._currentSegment;
/*     */     }
/*     */     
/* 320 */     return contentsAsArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String contentsAsString()
/*     */   {
/* 331 */     if (this._resultString == null)
/*     */     {
/* 333 */       if (this._resultArray != null) {
/* 334 */         this._resultString = new String(this._resultArray);
/*     */ 
/*     */       }
/* 337 */       else if (this._inputStart >= 0) {
/* 338 */         if (this._inputLen < 1) {
/* 339 */           return this._resultString = "";
/*     */         }
/* 341 */         this._resultString = new String(this._inputBuffer, this._inputStart, this._inputLen);
/*     */       }
/*     */       else {
/* 344 */         int i = this._segmentSize;
/* 345 */         int j = this._currentSize;
/*     */         
/* 347 */         if (i == 0) {
/* 348 */           this._resultString = (j == 0 ? "" : new String(this._currentSegment, 0, j));
/*     */         } else {
/* 350 */           StringBuilder localStringBuilder = new StringBuilder(i + j);
/*     */           
/* 352 */           if (this._segments != null) {
/* 353 */             int k = 0; for (int m = this._segments.size(); k < m; k++) {
/* 354 */               char[] arrayOfChar = (char[])this._segments.get(k);
/* 355 */               localStringBuilder.append(arrayOfChar, 0, arrayOfChar.length);
/*     */             }
/*     */           }
/*     */           
/* 359 */           localStringBuilder.append(this._currentSegment, 0, this._currentSize);
/* 360 */           this._resultString = localStringBuilder.toString();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 365 */     return this._resultString;
/*     */   }
/*     */   
/*     */   public char[] contentsAsArray()
/*     */   {
/* 370 */     char[] arrayOfChar = this._resultArray;
/* 371 */     if (arrayOfChar == null) {
/* 372 */       this._resultArray = (arrayOfChar = buildResultArray());
/*     */     }
/* 374 */     return arrayOfChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BigDecimal contentsAsDecimal()
/*     */     throws NumberFormatException
/*     */   {
/* 385 */     if (this._resultArray != null) {
/* 386 */       return new BigDecimal(this._resultArray);
/*     */     }
/*     */     
/* 389 */     if (this._inputStart >= 0) {
/* 390 */       return new BigDecimal(this._inputBuffer, this._inputStart, this._inputLen);
/*     */     }
/*     */     
/* 393 */     if (this._segmentSize == 0) {
/* 394 */       return new BigDecimal(this._currentSegment, 0, this._currentSize);
/*     */     }
/*     */     
/* 397 */     return new BigDecimal(contentsAsArray());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double contentsAsDouble()
/*     */     throws NumberFormatException
/*     */   {
/* 407 */     return NumberInput.parseDouble(contentsAsString());
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
/*     */   public void ensureNotShared()
/*     */   {
/* 421 */     if (this._inputStart >= 0) {
/* 422 */       unshare(16);
/*     */     }
/*     */   }
/*     */   
/*     */   public void append(char paramChar)
/*     */   {
/* 428 */     if (this._inputStart >= 0) {
/* 429 */       unshare(16);
/*     */     }
/* 431 */     this._resultString = null;
/* 432 */     this._resultArray = null;
/*     */     
/* 434 */     char[] arrayOfChar = this._currentSegment;
/* 435 */     if (this._currentSize >= arrayOfChar.length) {
/* 436 */       expand(1);
/* 437 */       arrayOfChar = this._currentSegment;
/*     */     }
/* 439 */     arrayOfChar[(this._currentSize++)] = paramChar;
/*     */   }
/*     */   
/*     */ 
/*     */   public void append(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 445 */     if (this._inputStart >= 0) {
/* 446 */       unshare(paramInt2);
/*     */     }
/* 448 */     this._resultString = null;
/* 449 */     this._resultArray = null;
/*     */     
/*     */ 
/* 452 */     char[] arrayOfChar = this._currentSegment;
/* 453 */     int i = arrayOfChar.length - this._currentSize;
/*     */     
/* 455 */     if (i >= paramInt2) {
/* 456 */       System.arraycopy(paramArrayOfChar, paramInt1, arrayOfChar, this._currentSize, paramInt2);
/* 457 */       this._currentSize += paramInt2;
/* 458 */       return;
/*     */     }
/*     */     
/* 461 */     if (i > 0) {
/* 462 */       System.arraycopy(paramArrayOfChar, paramInt1, arrayOfChar, this._currentSize, i);
/* 463 */       paramInt1 += i;
/* 464 */       paramInt2 -= i;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 471 */       expand(paramInt2);
/* 472 */       int j = Math.min(this._currentSegment.length, paramInt2);
/* 473 */       System.arraycopy(paramArrayOfChar, paramInt1, this._currentSegment, 0, j);
/* 474 */       this._currentSize += j;
/* 475 */       paramInt1 += j;
/* 476 */       paramInt2 -= j;
/* 477 */     } while (paramInt2 > 0);
/*     */   }
/*     */   
/*     */ 
/*     */   public void append(String paramString, int paramInt1, int paramInt2)
/*     */   {
/* 483 */     if (this._inputStart >= 0) {
/* 484 */       unshare(paramInt2);
/*     */     }
/* 486 */     this._resultString = null;
/* 487 */     this._resultArray = null;
/*     */     
/*     */ 
/* 490 */     char[] arrayOfChar = this._currentSegment;
/* 491 */     int i = arrayOfChar.length - this._currentSize;
/* 492 */     if (i >= paramInt2) {
/* 493 */       paramString.getChars(paramInt1, paramInt1 + paramInt2, arrayOfChar, this._currentSize);
/* 494 */       this._currentSize += paramInt2;
/* 495 */       return;
/*     */     }
/*     */     
/* 498 */     if (i > 0) {
/* 499 */       paramString.getChars(paramInt1, paramInt1 + i, arrayOfChar, this._currentSize);
/* 500 */       paramInt2 -= i;
/* 501 */       paramInt1 += i;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 508 */       expand(paramInt2);
/* 509 */       int j = Math.min(this._currentSegment.length, paramInt2);
/* 510 */       paramString.getChars(paramInt1, paramInt1 + j, this._currentSegment, 0);
/* 511 */       this._currentSize += j;
/* 512 */       paramInt1 += j;
/* 513 */       paramInt2 -= j;
/* 514 */     } while (paramInt2 > 0);
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
/*     */   public char[] getCurrentSegment()
/*     */   {
/* 529 */     if (this._inputStart >= 0) {
/* 530 */       unshare(1);
/*     */     } else {
/* 532 */       char[] arrayOfChar = this._currentSegment;
/* 533 */       if (arrayOfChar == null) {
/* 534 */         this._currentSegment = findBuffer(0);
/* 535 */       } else if (this._currentSize >= arrayOfChar.length)
/*     */       {
/* 537 */         expand(1);
/*     */       }
/*     */     }
/* 540 */     return this._currentSegment;
/*     */   }
/*     */   
/*     */ 
/*     */   public char[] emptyAndGetCurrentSegment()
/*     */   {
/* 546 */     this._inputStart = -1;
/* 547 */     this._currentSize = 0;
/* 548 */     this._inputLen = 0;
/*     */     
/* 550 */     this._inputBuffer = null;
/* 551 */     this._resultString = null;
/* 552 */     this._resultArray = null;
/*     */     
/*     */ 
/* 555 */     if (this._hasSegments) {
/* 556 */       clearSegments();
/*     */     }
/* 558 */     char[] arrayOfChar = this._currentSegment;
/* 559 */     if (arrayOfChar == null) {
/* 560 */       this._currentSegment = (arrayOfChar = findBuffer(0));
/*     */     }
/* 562 */     return arrayOfChar;
/*     */   }
/*     */   
/*     */   public int getCurrentSegmentSize() {
/* 566 */     return this._currentSize;
/*     */   }
/*     */   
/*     */   public void setCurrentLength(int paramInt) {
/* 570 */     this._currentSize = paramInt;
/*     */   }
/*     */   
/*     */   public char[] finishCurrentSegment()
/*     */   {
/* 575 */     if (this._segments == null) {
/* 576 */       this._segments = new ArrayList();
/*     */     }
/* 578 */     this._hasSegments = true;
/* 579 */     this._segments.add(this._currentSegment);
/* 580 */     int i = this._currentSegment.length;
/* 581 */     this._segmentSize += i;
/*     */     
/* 583 */     int j = Math.min(i + (i >> 1), 262144);
/* 584 */     char[] arrayOfChar = _charArray(j);
/* 585 */     this._currentSize = 0;
/* 586 */     this._currentSegment = arrayOfChar;
/* 587 */     return arrayOfChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public char[] expandCurrentSegment()
/*     */   {
/* 597 */     char[] arrayOfChar = this._currentSegment;
/*     */     
/* 599 */     int i = arrayOfChar.length;
/*     */     
/* 601 */     int j = i == 262144 ? 262145 : Math.min(262144, i + (i >> 1));
/*     */     
/* 603 */     this._currentSegment = _charArray(j);
/* 604 */     System.arraycopy(arrayOfChar, 0, this._currentSegment, 0, i);
/* 605 */     return this._currentSegment;
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
/*     */   public String toString()
/*     */   {
/* 621 */     return contentsAsString();
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
/*     */   private void unshare(int paramInt)
/*     */   {
/* 636 */     int i = this._inputLen;
/* 637 */     this._inputLen = 0;
/* 638 */     char[] arrayOfChar = this._inputBuffer;
/* 639 */     this._inputBuffer = null;
/* 640 */     int j = this._inputStart;
/* 641 */     this._inputStart = -1;
/*     */     
/*     */ 
/* 644 */     int k = i + paramInt;
/* 645 */     if ((this._currentSegment == null) || (k > this._currentSegment.length)) {
/* 646 */       this._currentSegment = findBuffer(k);
/*     */     }
/* 648 */     if (i > 0) {
/* 649 */       System.arraycopy(arrayOfChar, j, this._currentSegment, 0, i);
/*     */     }
/* 651 */     this._segmentSize = 0;
/* 652 */     this._currentSize = i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void expand(int paramInt)
/*     */   {
/* 662 */     if (this._segments == null) {
/* 663 */       this._segments = new ArrayList();
/*     */     }
/* 665 */     char[] arrayOfChar = this._currentSegment;
/* 666 */     this._hasSegments = true;
/* 667 */     this._segments.add(arrayOfChar);
/* 668 */     this._segmentSize += arrayOfChar.length;
/* 669 */     int i = arrayOfChar.length;
/*     */     
/* 671 */     int j = i >> 1;
/* 672 */     if (j < paramInt) {
/* 673 */       j = paramInt;
/*     */     }
/* 675 */     arrayOfChar = _charArray(Math.min(262144, i + j));
/* 676 */     this._currentSize = 0;
/* 677 */     this._currentSegment = arrayOfChar;
/*     */   }
/*     */   
/*     */   private char[] buildResultArray()
/*     */   {
/* 682 */     if (this._resultString != null) {
/* 683 */       return this._resultString.toCharArray();
/*     */     }
/*     */     
/*     */     char[] arrayOfChar1;
/*     */     
/* 688 */     if (this._inputStart >= 0) {
/* 689 */       if (this._inputLen < 1) {
/* 690 */         return NO_CHARS;
/*     */       }
/* 692 */       arrayOfChar1 = _charArray(this._inputLen);
/* 693 */       System.arraycopy(this._inputBuffer, this._inputStart, arrayOfChar1, 0, this._inputLen);
/*     */     }
/*     */     else {
/* 696 */       int i = size();
/* 697 */       if (i < 1) {
/* 698 */         return NO_CHARS;
/*     */       }
/* 700 */       int j = 0;
/* 701 */       arrayOfChar1 = _charArray(i);
/* 702 */       if (this._segments != null) {
/* 703 */         int k = 0; for (int m = this._segments.size(); k < m; k++) {
/* 704 */           char[] arrayOfChar2 = (char[])this._segments.get(k);
/* 705 */           int n = arrayOfChar2.length;
/* 706 */           System.arraycopy(arrayOfChar2, 0, arrayOfChar1, j, n);
/* 707 */           j += n;
/*     */         }
/*     */       }
/* 710 */       System.arraycopy(this._currentSegment, 0, arrayOfChar1, j, this._currentSize);
/*     */     }
/* 712 */     return arrayOfChar1;
/*     */   }
/*     */   
/*     */   private char[] _charArray(int paramInt) {
/* 716 */     return new char[paramInt];
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/shaded/fasterxml/jackson/core/util/TextBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */