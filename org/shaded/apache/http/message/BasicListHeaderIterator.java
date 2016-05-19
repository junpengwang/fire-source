/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HeaderIterator;
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
/*     */ 
/*     */ public class BasicListHeaderIterator
/*     */   implements HeaderIterator
/*     */ {
/*     */   protected final List allHeaders;
/*     */   protected int currentIndex;
/*     */   protected int lastIndex;
/*     */   protected String headerName;
/*     */   
/*     */   public BasicListHeaderIterator(List headers, String name)
/*     */   {
/*  87 */     if (headers == null) {
/*  88 */       throw new IllegalArgumentException("Header list must not be null.");
/*     */     }
/*     */     
/*     */ 
/*  92 */     this.allHeaders = headers;
/*  93 */     this.headerName = name;
/*  94 */     this.currentIndex = findNext(-1);
/*  95 */     this.lastIndex = -1;
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
/*     */   protected int findNext(int from)
/*     */   {
/* 109 */     if (from < -1) {
/* 110 */       return -1;
/*     */     }
/* 112 */     int to = this.allHeaders.size() - 1;
/* 113 */     boolean found = false;
/* 114 */     while ((!found) && (from < to)) {
/* 115 */       from++;
/* 116 */       found = filterHeader(from);
/*     */     }
/* 118 */     return found ? from : -1;
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
/*     */   protected boolean filterHeader(int index)
/*     */   {
/* 131 */     if (this.headerName == null) {
/* 132 */       return true;
/*     */     }
/*     */     
/* 135 */     String name = ((Header)this.allHeaders.get(index)).getName();
/*     */     
/* 137 */     return this.headerName.equalsIgnoreCase(name);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasNext()
/*     */   {
/* 143 */     return this.currentIndex >= 0;
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
/*     */   public Header nextHeader()
/*     */     throws NoSuchElementException
/*     */   {
/* 157 */     int current = this.currentIndex;
/* 158 */     if (current < 0) {
/* 159 */       throw new NoSuchElementException("Iteration already finished.");
/*     */     }
/*     */     
/* 162 */     this.lastIndex = current;
/* 163 */     this.currentIndex = findNext(current);
/*     */     
/* 165 */     return (Header)this.allHeaders.get(current);
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
/*     */   public final Object next()
/*     */     throws NoSuchElementException
/*     */   {
/* 179 */     return nextHeader();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void remove()
/*     */     throws UnsupportedOperationException
/*     */   {
/* 189 */     if (this.lastIndex < 0) {
/* 190 */       throw new IllegalStateException("No header to remove.");
/*     */     }
/* 192 */     this.allHeaders.remove(this.lastIndex);
/* 193 */     this.lastIndex = -1;
/* 194 */     this.currentIndex -= 1;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicListHeaderIterator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */