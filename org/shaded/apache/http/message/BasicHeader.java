/*    */ package org.shaded.apache.http.message;
/*    */ 
/*    */ import org.shaded.apache.http.Header;
/*    */ import org.shaded.apache.http.HeaderElement;
/*    */ import org.shaded.apache.http.ParseException;
/*    */ import org.shaded.apache.http.util.CharArrayBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicHeader
/*    */   implements Header, Cloneable
/*    */ {
/*    */   private final String name;
/*    */   private final String value;
/*    */   
/*    */   public BasicHeader(String name, String value)
/*    */   {
/* 59 */     if (name == null) {
/* 60 */       throw new IllegalArgumentException("Name may not be null");
/*    */     }
/* 62 */     this.name = name;
/* 63 */     this.value = value;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 67 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 71 */     return this.value;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 76 */     return BasicLineFormatter.DEFAULT.formatHeader(null, this).toString();
/*    */   }
/*    */   
/*    */   public HeaderElement[] getElements() throws ParseException {
/* 80 */     if (this.value != null)
/*    */     {
/* 82 */       return BasicHeaderValueParser.parseElements(this.value, null);
/*    */     }
/* 84 */     return new HeaderElement[0];
/*    */   }
/*    */   
/*    */   public Object clone() throws CloneNotSupportedException
/*    */   {
/* 89 */     return super.clone();
/*    */   }
/*    */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicHeader.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */