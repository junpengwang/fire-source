/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ import org.shaded.apache.http.FormattedHeader;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.HeaderElementIterator;
/*     */ import org.shaded.apache.http.HeaderIterator;
/*     */ import org.shaded.apache.http.util.CharArrayBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicHeaderElementIterator
/*     */   implements HeaderElementIterator
/*     */ {
/*     */   private final HeaderIterator headerIt;
/*     */   private final HeaderValueParser parser;
/*  55 */   private HeaderElement currentElement = null;
/*  56 */   private CharArrayBuffer buffer = null;
/*  57 */   private ParserCursor cursor = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicHeaderElementIterator(HeaderIterator headerIterator, HeaderValueParser parser)
/*     */   {
/*  65 */     if (headerIterator == null) {
/*  66 */       throw new IllegalArgumentException("Header iterator may not be null");
/*     */     }
/*  68 */     if (parser == null) {
/*  69 */       throw new IllegalArgumentException("Parser may not be null");
/*     */     }
/*  71 */     this.headerIt = headerIterator;
/*  72 */     this.parser = parser;
/*     */   }
/*     */   
/*     */   public BasicHeaderElementIterator(HeaderIterator headerIterator)
/*     */   {
/*  77 */     this(headerIterator, BasicHeaderValueParser.DEFAULT);
/*     */   }
/*     */   
/*     */   private void bufferHeaderValue()
/*     */   {
/*  82 */     this.cursor = null;
/*  83 */     this.buffer = null;
/*  84 */     while (this.headerIt.hasNext()) {
/*  85 */       Header h = this.headerIt.nextHeader();
/*  86 */       if ((h instanceof FormattedHeader)) {
/*  87 */         this.buffer = ((FormattedHeader)h).getBuffer();
/*  88 */         this.cursor = new ParserCursor(0, this.buffer.length());
/*  89 */         this.cursor.updatePos(((FormattedHeader)h).getValuePos());
/*  90 */         break;
/*     */       }
/*  92 */       String value = h.getValue();
/*  93 */       if (value != null) {
/*  94 */         this.buffer = new CharArrayBuffer(value.length());
/*  95 */         this.buffer.append(value);
/*  96 */         this.cursor = new ParserCursor(0, this.buffer.length());
/*  97 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void parseNextElement()
/*     */   {
/* 105 */     while ((this.headerIt.hasNext()) || (this.cursor != null)) {
/* 106 */       if ((this.cursor == null) || (this.cursor.atEnd()))
/*     */       {
/* 108 */         bufferHeaderValue();
/*     */       }
/*     */       
/* 111 */       if (this.cursor != null)
/*     */       {
/* 113 */         while (!this.cursor.atEnd()) {
/* 114 */           HeaderElement e = this.parser.parseHeaderElement(this.buffer, this.cursor);
/* 115 */           if ((e.getName().length() != 0) || (e.getValue() != null))
/*     */           {
/* 117 */             this.currentElement = e;
/* 118 */             return;
/*     */           }
/*     */         }
/*     */         
/* 122 */         if (this.cursor.atEnd())
/*     */         {
/* 124 */           this.cursor = null;
/* 125 */           this.buffer = null;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasNext() {
/* 132 */     if (this.currentElement == null) {
/* 133 */       parseNextElement();
/*     */     }
/* 135 */     return this.currentElement != null;
/*     */   }
/*     */   
/*     */   public HeaderElement nextElement() throws NoSuchElementException {
/* 139 */     if (this.currentElement == null) {
/* 140 */       parseNextElement();
/*     */     }
/*     */     
/* 143 */     if (this.currentElement == null) {
/* 144 */       throw new NoSuchElementException("No more header elements available");
/*     */     }
/*     */     
/* 147 */     HeaderElement element = this.currentElement;
/* 148 */     this.currentElement = null;
/* 149 */     return element;
/*     */   }
/*     */   
/*     */   public final Object next() throws NoSuchElementException {
/* 153 */     return nextElement();
/*     */   }
/*     */   
/*     */   public void remove() throws UnsupportedOperationException {
/* 157 */     throw new UnsupportedOperationException("Remove not supported");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicHeaderElementIterator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */