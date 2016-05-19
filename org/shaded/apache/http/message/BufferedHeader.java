/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import org.shaded.apache.http.FormattedHeader;
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.ParseException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferedHeader
/*     */   implements FormattedHeader, Cloneable
/*     */ {
/*     */   private final String name;
/*     */   private final CharArrayBuffer buffer;
/*     */   private final int valuePos;
/*     */   
/*     */   public BufferedHeader(CharArrayBuffer buffer)
/*     */     throws ParseException
/*     */   {
/*  81 */     if (buffer == null) {
/*  82 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*     */     }
/*     */     
/*  85 */     int colon = buffer.indexOf(58);
/*  86 */     if (colon == -1) {
/*  87 */       throw new ParseException("Invalid header: " + buffer.toString());
/*     */     }
/*     */     
/*  90 */     String s = buffer.substringTrimmed(0, colon);
/*  91 */     if (s.length() == 0) {
/*  92 */       throw new ParseException("Invalid header: " + buffer.toString());
/*     */     }
/*     */     
/*  95 */     this.buffer = buffer;
/*  96 */     this.name = s;
/*  97 */     this.valuePos = (colon + 1);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 102 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 106 */     return this.buffer.substringTrimmed(this.valuePos, this.buffer.length());
/*     */   }
/*     */   
/*     */   public HeaderElement[] getElements() throws ParseException {
/* 110 */     ParserCursor cursor = new ParserCursor(0, this.buffer.length());
/* 111 */     cursor.updatePos(this.valuePos);
/* 112 */     return BasicHeaderValueParser.DEFAULT.parseElements(this.buffer, cursor);
/*     */   }
/*     */   
/*     */   public int getValuePos()
/*     */   {
/* 117 */     return this.valuePos;
/*     */   }
/*     */   
/*     */   public CharArrayBuffer getBuffer() {
/* 121 */     return this.buffer;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 125 */     return this.buffer.toString();
/*     */   }
/*     */   
/*     */   public Object clone()
/*     */     throws CloneNotSupportedException
/*     */   {
/* 131 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BufferedHeader.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */