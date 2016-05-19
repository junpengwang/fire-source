/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HeaderIterator;
/*     */ import org.shaded.apache.http.ParseException;
/*     */ import org.shaded.apache.http.TokenIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicTokenIterator
/*     */   implements TokenIterator
/*     */ {
/*     */   public static final String HTTP_SEPARATORS = " ,;=()<>@:\\\"/[]?{}\t";
/*     */   protected final HeaderIterator headerIt;
/*     */   protected String currentHeader;
/*     */   protected String currentToken;
/*     */   protected int searchPos;
/*     */   
/*     */   public BasicTokenIterator(HeaderIterator headerIterator)
/*     */   {
/*  87 */     if (headerIterator == null) {
/*  88 */       throw new IllegalArgumentException("Header iterator must not be null.");
/*     */     }
/*     */     
/*     */ 
/*  92 */     this.headerIt = headerIterator;
/*  93 */     this.searchPos = findNext(-1);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasNext()
/*     */   {
/*  99 */     return this.currentToken != null;
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
/*     */   public String nextToken()
/*     */     throws NoSuchElementException, ParseException
/*     */   {
/* 114 */     if (this.currentToken == null) {
/* 115 */       throw new NoSuchElementException("Iteration already finished.");
/*     */     }
/*     */     
/* 118 */     String result = this.currentToken;
/*     */     
/* 120 */     this.searchPos = findNext(this.searchPos);
/*     */     
/* 122 */     return result;
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
/*     */   public final Object next()
/*     */     throws NoSuchElementException, ParseException
/*     */   {
/* 137 */     return nextToken();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void remove()
/*     */     throws UnsupportedOperationException
/*     */   {
/* 149 */     throw new UnsupportedOperationException("Removing tokens is not supported.");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int findNext(int from)
/*     */     throws ParseException
/*     */   {
/* 173 */     if (from < 0)
/*     */     {
/* 175 */       if (!this.headerIt.hasNext()) {
/* 176 */         return -1;
/*     */       }
/* 178 */       this.currentHeader = this.headerIt.nextHeader().getValue();
/* 179 */       from = 0;
/*     */     }
/*     */     else {
/* 182 */       from = findTokenSeparator(from);
/*     */     }
/*     */     
/* 185 */     int start = findTokenStart(from);
/* 186 */     if (start < 0) {
/* 187 */       this.currentToken = null;
/* 188 */       return -1;
/*     */     }
/*     */     
/* 191 */     int end = findTokenEnd(start);
/* 192 */     this.currentToken = createToken(this.currentHeader, start, end);
/* 193 */     return end;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String createToken(String value, int start, int end)
/*     */   {
/* 218 */     return value.substring(start, end);
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
/*     */   protected int findTokenStart(int from)
/*     */   {
/* 233 */     if (from < 0) {
/* 234 */       throw new IllegalArgumentException("Search position must not be negative: " + from);
/*     */     }
/*     */     
/*     */ 
/* 238 */     boolean found = false;
/* 239 */     while ((!found) && (this.currentHeader != null))
/*     */     {
/* 241 */       int to = this.currentHeader.length();
/* 242 */       while ((!found) && (from < to))
/*     */       {
/* 244 */         char ch = this.currentHeader.charAt(from);
/* 245 */         if ((isTokenSeparator(ch)) || (isWhitespace(ch)))
/*     */         {
/* 247 */           from++;
/* 248 */         } else if (isTokenChar(this.currentHeader.charAt(from)))
/*     */         {
/* 250 */           found = true;
/*     */         } else {
/* 252 */           throw new ParseException("Invalid character before token (pos " + from + "): " + this.currentHeader);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 257 */       if (!found) {
/* 258 */         if (this.headerIt.hasNext()) {
/* 259 */           this.currentHeader = this.headerIt.nextHeader().getValue();
/* 260 */           from = 0;
/*     */         } else {
/* 262 */           this.currentHeader = null;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 267 */     return found ? from : -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int findTokenSeparator(int from)
/*     */   {
/* 289 */     if (from < 0) {
/* 290 */       throw new IllegalArgumentException("Search position must not be negative: " + from);
/*     */     }
/*     */     
/*     */ 
/* 294 */     boolean found = false;
/* 295 */     int to = this.currentHeader.length();
/* 296 */     while ((!found) && (from < to)) {
/* 297 */       char ch = this.currentHeader.charAt(from);
/* 298 */       if (isTokenSeparator(ch)) {
/* 299 */         found = true;
/* 300 */       } else if (isWhitespace(ch)) {
/* 301 */         from++;
/* 302 */       } else { if (isTokenChar(ch)) {
/* 303 */           throw new ParseException("Tokens without separator (pos " + from + "): " + this.currentHeader);
/*     */         }
/*     */         
/*     */ 
/* 307 */         throw new ParseException("Invalid character after token (pos " + from + "): " + this.currentHeader);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 313 */     return from;
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
/*     */   protected int findTokenEnd(int from)
/*     */   {
/* 329 */     if (from < 0) {
/* 330 */       throw new IllegalArgumentException("Token start position must not be negative: " + from);
/*     */     }
/*     */     
/*     */ 
/* 334 */     int to = this.currentHeader.length();
/* 335 */     int end = from + 1;
/* 336 */     while ((end < to) && (isTokenChar(this.currentHeader.charAt(end)))) {
/* 337 */       end++;
/*     */     }
/*     */     
/* 340 */     return end;
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
/*     */   protected boolean isTokenSeparator(char ch)
/*     */   {
/* 356 */     return ch == ',';
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
/*     */ 
/*     */ 
/*     */   protected boolean isWhitespace(char ch)
/*     */   {
/* 375 */     return (ch == '\t') || (Character.isSpaceChar(ch));
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
/*     */ 
/*     */ 
/*     */   protected boolean isTokenChar(char ch)
/*     */   {
/* 394 */     if (Character.isLetterOrDigit(ch)) {
/* 395 */       return true;
/*     */     }
/*     */     
/* 398 */     if (Character.isISOControl(ch)) {
/* 399 */       return false;
/*     */     }
/*     */     
/* 402 */     if (isHttpSeparator(ch)) {
/* 403 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 411 */     return true;
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
/*     */   protected boolean isHttpSeparator(char ch)
/*     */   {
/* 426 */     return " ,;=()<>@:\\\"/[]?{}\t".indexOf(ch) >= 0;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicTokenIterator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */