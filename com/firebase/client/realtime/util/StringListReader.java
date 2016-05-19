/*     */ package com.firebase.client.realtime.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.nio.CharBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class StringListReader extends Reader
/*     */ {
/*  11 */   private List<String> strings = null;
/*  12 */   private boolean closed = false;
/*     */   
/*     */   private int charPos;
/*     */   
/*     */   private int stringListPos;
/*  17 */   private int markedCharPos = this.charPos;
/*  18 */   private int markedStringListPos = this.stringListPos;
/*     */   
/*  20 */   private boolean frozen = false;
/*     */   
/*     */   public StringListReader() {
/*  23 */     this.strings = new ArrayList();
/*     */   }
/*     */   
/*     */   public void addString(String string) {
/*  27 */     if (this.frozen) {
/*  28 */       throw new IllegalStateException("Trying to add string after reading");
/*     */     }
/*  30 */     if (string.length() > 0) {
/*  31 */       this.strings.add(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void freeze() {
/*  36 */     if (this.frozen) {
/*  37 */       throw new IllegalStateException("Trying to freeze frozen StringListReader");
/*     */     }
/*  39 */     this.frozen = true;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  43 */     StringBuilder builder = new StringBuilder();
/*  44 */     for (String string : this.strings) {
/*  45 */       builder.append(string);
/*     */     }
/*  47 */     return builder.toString();
/*     */   }
/*     */   
/*     */   public void reset() throws IOException
/*     */   {
/*  52 */     this.charPos = this.markedCharPos;
/*  53 */     this.stringListPos = this.markedStringListPos;
/*     */   }
/*     */   
/*     */   private String currentString() {
/*  57 */     return this.stringListPos < this.strings.size() ? (String)this.strings.get(this.stringListPos) : null;
/*     */   }
/*     */   
/*     */   private int currentStringRemainingChars() {
/*  61 */     String current = currentString();
/*  62 */     return current == null ? 0 : current.length() - this.charPos;
/*     */   }
/*     */   
/*     */   private void checkState() throws IOException {
/*  66 */     if (this.closed) {
/*  67 */       throw new IOException("Stream already closed");
/*     */     }
/*  69 */     if (!this.frozen) {
/*  70 */       throw new IOException("Reader needs to be frozen before read operations can be called");
/*     */     }
/*     */   }
/*     */   
/*     */   private long advance(long numChars) {
/*  75 */     long advanced = 0L;
/*  76 */     while ((this.stringListPos < this.strings.size()) && (advanced < numChars)) {
/*  77 */       int remainingStringChars = currentStringRemainingChars();
/*  78 */       long remainingChars = numChars - advanced;
/*  79 */       if (remainingChars < remainingStringChars) {
/*  80 */         this.charPos = ((int)(this.charPos + remainingChars));
/*  81 */         advanced += remainingChars;
/*     */       } else {
/*  83 */         advanced += remainingStringChars;
/*  84 */         this.charPos = 0;
/*  85 */         this.stringListPos += 1;
/*     */       }
/*     */     }
/*  88 */     return advanced;
/*     */   }
/*     */   
/*     */   public int read(CharBuffer target) throws IOException
/*     */   {
/*  93 */     checkState();
/*  94 */     int remaining = target.remaining();
/*  95 */     int total = 0;
/*  96 */     String current = currentString();
/*  97 */     while ((remaining > 0) && (current != null)) {
/*  98 */       int strLength = Math.min(current.length() - this.charPos, remaining);
/*  99 */       target.put((String)this.strings.get(this.stringListPos), this.charPos, this.charPos + strLength);
/* 100 */       remaining -= strLength;
/* 101 */       total += strLength;
/* 102 */       advance(strLength);
/* 103 */       current = currentString();
/*     */     }
/* 105 */     if ((total > 0) || (current != null)) {
/* 106 */       return total;
/*     */     }
/* 108 */     return -1;
/*     */   }
/*     */   
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 114 */     checkState();
/* 115 */     String current = currentString();
/* 116 */     if (current == null) {
/* 117 */       return -1;
/*     */     }
/* 119 */     char c = current.charAt(this.charPos);
/* 120 */     advance(1L);
/* 121 */     return c;
/*     */   }
/*     */   
/*     */   public long skip(long n)
/*     */     throws IOException
/*     */   {
/* 127 */     checkState();
/* 128 */     return advance(n);
/*     */   }
/*     */   
/*     */   public boolean ready() throws IOException
/*     */   {
/* 133 */     checkState();
/* 134 */     return true;
/*     */   }
/*     */   
/*     */   public boolean markSupported()
/*     */   {
/* 139 */     return true;
/*     */   }
/*     */   
/*     */   public void mark(int readAheadLimit) throws IOException
/*     */   {
/* 144 */     checkState();
/* 145 */     this.markedCharPos = this.charPos;
/* 146 */     this.markedStringListPos = this.stringListPos;
/*     */   }
/*     */   
/*     */   public int read(char[] cbuf, int off, int len) throws IOException
/*     */   {
/* 151 */     checkState();
/* 152 */     int charsCopied = 0;
/* 153 */     String current = currentString();
/* 154 */     while ((current != null) && (charsCopied < len)) {
/* 155 */       int copyLength = Math.min(currentStringRemainingChars(), len - charsCopied);
/* 156 */       current.getChars(this.charPos, this.charPos + copyLength, cbuf, off + charsCopied);
/* 157 */       charsCopied += copyLength;
/* 158 */       advance(copyLength);
/* 159 */       current = currentString();
/*     */     }
/* 161 */     if ((charsCopied > 0) || (current != null)) {
/* 162 */       return charsCopied;
/*     */     }
/* 164 */     return -1;
/*     */   }
/*     */   
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 170 */     checkState();
/* 171 */     this.closed = true;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/com/firebase/client/realtime/util/StringListReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */