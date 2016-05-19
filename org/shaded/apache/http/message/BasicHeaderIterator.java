/*     */ package org.shaded.apache.http.message;
/*     */ 
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
/*     */ public class BasicHeaderIterator
/*     */   implements HeaderIterator
/*     */ {
/*     */   protected final Header[] allHeaders;
/*     */   protected int currentIndex;
/*     */   protected String headerName;
/*     */   
/*     */   public BasicHeaderIterator(Header[] headers, String name)
/*     */   {
/*  82 */     if (headers == null) {
/*  83 */       throw new IllegalArgumentException("Header array must not be null.");
/*     */     }
/*     */     
/*     */ 
/*  87 */     this.allHeaders = headers;
/*  88 */     this.headerName = name;
/*  89 */     this.currentIndex = findNext(-1);
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
/* 103 */     if (from < -1) {
/* 104 */       return -1;
/*     */     }
/* 106 */     int to = this.allHeaders.length - 1;
/* 107 */     boolean found = false;
/* 108 */     while ((!found) && (from < to)) {
/* 109 */       from++;
/* 110 */       found = filterHeader(from);
/*     */     }
/* 112 */     return found ? from : -1;
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
/* 125 */     return (this.headerName == null) || (this.headerName.equalsIgnoreCase(this.allHeaders[index].getName()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasNext()
/*     */   {
/* 132 */     return this.currentIndex >= 0;
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
/* 146 */     int current = this.currentIndex;
/* 147 */     if (current < 0) {
/* 148 */       throw new NoSuchElementException("Iteration already finished.");
/*     */     }
/*     */     
/* 151 */     this.currentIndex = findNext(current);
/*     */     
/* 153 */     return this.allHeaders[current];
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
/* 167 */     return nextHeader();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void remove()
/*     */     throws UnsupportedOperationException
/*     */   {
/* 179 */     throw new UnsupportedOperationException("Removing headers is not supported.");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicHeaderIterator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */