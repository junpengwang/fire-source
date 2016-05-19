/*     */ package org.shaded.apache.http.impl.conn;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.shaded.apache.commons.logging.Log;
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
/*     */ 
/*     */ @Immutable
/*     */ public class Wire
/*     */ {
/*     */   private final Log log;
/*     */   
/*     */   public Wire(Log log)
/*     */   {
/*  50 */     this.log = log;
/*     */   }
/*     */   
/*     */   private void wire(String header, InputStream instream) throws IOException
/*     */   {
/*  55 */     StringBuilder buffer = new StringBuilder();
/*     */     int ch;
/*  57 */     while ((ch = instream.read()) != -1) {
/*  58 */       if (ch == 13) {
/*  59 */         buffer.append("[\\r]");
/*  60 */       } else if (ch == 10) {
/*  61 */         buffer.append("[\\n]\"");
/*  62 */         buffer.insert(0, "\"");
/*  63 */         buffer.insert(0, header);
/*  64 */         this.log.debug(buffer.toString());
/*  65 */         buffer.setLength(0);
/*  66 */       } else if ((ch < 32) || (ch > 127)) {
/*  67 */         buffer.append("[0x");
/*  68 */         buffer.append(Integer.toHexString(ch));
/*  69 */         buffer.append("]");
/*     */       } else {
/*  71 */         buffer.append((char)ch);
/*     */       }
/*     */     }
/*  74 */     if (buffer.length() > 0) {
/*  75 */       buffer.append('"');
/*  76 */       buffer.insert(0, '"');
/*  77 */       buffer.insert(0, header);
/*  78 */       this.log.debug(buffer.toString());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean enabled()
/*     */   {
/*  84 */     return this.log.isDebugEnabled();
/*     */   }
/*     */   
/*     */   public void output(InputStream outstream) throws IOException
/*     */   {
/*  89 */     if (outstream == null) {
/*  90 */       throw new IllegalArgumentException("Output may not be null");
/*     */     }
/*  92 */     wire(">> ", outstream);
/*     */   }
/*     */   
/*     */   public void input(InputStream instream) throws IOException
/*     */   {
/*  97 */     if (instream == null) {
/*  98 */       throw new IllegalArgumentException("Input may not be null");
/*     */     }
/* 100 */     wire("<< ", instream);
/*     */   }
/*     */   
/*     */   public void output(byte[] b, int off, int len) throws IOException
/*     */   {
/* 105 */     if (b == null) {
/* 106 */       throw new IllegalArgumentException("Output may not be null");
/*     */     }
/* 108 */     wire(">> ", new ByteArrayInputStream(b, off, len));
/*     */   }
/*     */   
/*     */   public void input(byte[] b, int off, int len) throws IOException
/*     */   {
/* 113 */     if (b == null) {
/* 114 */       throw new IllegalArgumentException("Input may not be null");
/*     */     }
/* 116 */     wire("<< ", new ByteArrayInputStream(b, off, len));
/*     */   }
/*     */   
/*     */   public void output(byte[] b) throws IOException
/*     */   {
/* 121 */     if (b == null) {
/* 122 */       throw new IllegalArgumentException("Output may not be null");
/*     */     }
/* 124 */     wire(">> ", new ByteArrayInputStream(b));
/*     */   }
/*     */   
/*     */   public void input(byte[] b) throws IOException
/*     */   {
/* 129 */     if (b == null) {
/* 130 */       throw new IllegalArgumentException("Input may not be null");
/*     */     }
/* 132 */     wire("<< ", new ByteArrayInputStream(b));
/*     */   }
/*     */   
/*     */   public void output(int b) throws IOException
/*     */   {
/* 137 */     output(new byte[] { (byte)b });
/*     */   }
/*     */   
/*     */   public void input(int b) throws IOException
/*     */   {
/* 142 */     input(new byte[] { (byte)b });
/*     */   }
/*     */   
/*     */   public void output(String s) throws IOException
/*     */   {
/* 147 */     if (s == null) {
/* 148 */       throw new IllegalArgumentException("Output may not be null");
/*     */     }
/* 150 */     output(s.getBytes());
/*     */   }
/*     */   
/*     */   public void input(String s) throws IOException
/*     */   {
/* 155 */     if (s == null) {
/* 156 */       throw new IllegalArgumentException("Input may not be null");
/*     */     }
/* 158 */     input(s.getBytes());
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/conn/Wire.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */