/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public class PublicSuffixListParser
/*     */ {
/*     */   private static final int MAX_LINE_LEN = 256;
/*     */   private final PublicSuffixFilter filter;
/*     */   
/*     */   PublicSuffixListParser(PublicSuffixFilter filter)
/*     */   {
/*  54 */     this.filter = filter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void parse(Reader list)
/*     */     throws IOException
/*     */   {
/*  66 */     Collection<String> rules = new ArrayList();
/*  67 */     Collection<String> exceptions = new ArrayList();
/*  68 */     BufferedReader r = new BufferedReader(list);
/*  69 */     StringBuilder sb = new StringBuilder(256);
/*  70 */     boolean more = true;
/*  71 */     while (more) {
/*  72 */       more = readLine(r, sb);
/*  73 */       String line = sb.toString();
/*  74 */       if ((line.length() != 0) && 
/*  75 */         (!line.startsWith("//"))) {
/*  76 */         if (line.startsWith(".")) { line = line.substring(1);
/*     */         }
/*  78 */         boolean isException = line.startsWith("!");
/*  79 */         if (isException) { line = line.substring(1);
/*     */         }
/*  81 */         if (isException) {
/*  82 */           exceptions.add(line);
/*     */         } else {
/*  84 */           rules.add(line);
/*     */         }
/*     */       }
/*     */     }
/*  88 */     this.filter.setPublicSuffixes(rules);
/*  89 */     this.filter.setExceptions(exceptions);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean readLine(Reader r, StringBuilder sb)
/*     */     throws IOException
/*     */   {
/* 100 */     sb.setLength(0);
/*     */     
/* 102 */     boolean hitWhitespace = false;
/* 103 */     int b; while ((b = r.read()) != -1) {
/* 104 */       char c = (char)b;
/* 105 */       if (c == '\n')
/*     */         break;
/* 107 */       if (Character.isWhitespace(c)) hitWhitespace = true;
/* 108 */       if (!hitWhitespace) sb.append(c);
/* 109 */       if (sb.length() > 256) throw new IOException("Line too long");
/*     */     }
/* 111 */     return b != -1;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/PublicSuffixListParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */