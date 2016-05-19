/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.shaded.apache.http.Header;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HeaderGroup
/*     */   implements Cloneable
/*     */ {
/*     */   private List headers;
/*     */   
/*     */   public HeaderGroup()
/*     */   {
/*  59 */     this.headers = new ArrayList(16);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/*  66 */     this.headers.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addHeader(Header header)
/*     */   {
/*  76 */     if (header == null) {
/*  77 */       return;
/*     */     }
/*  79 */     this.headers.add(header);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeHeader(Header header)
/*     */   {
/*  88 */     if (header == null) {
/*  89 */       return;
/*     */     }
/*  91 */     this.headers.remove(header);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateHeader(Header header)
/*     */   {
/* 102 */     if (header == null) {
/* 103 */       return;
/*     */     }
/* 105 */     for (int i = 0; i < this.headers.size(); i++) {
/* 106 */       Header current = (Header)this.headers.get(i);
/* 107 */       if (current.getName().equalsIgnoreCase(header.getName())) {
/* 108 */         this.headers.set(i, header);
/* 109 */         return;
/*     */       }
/*     */     }
/* 112 */     this.headers.add(header);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHeaders(Header[] headers)
/*     */   {
/* 123 */     clear();
/* 124 */     if (headers == null) {
/* 125 */       return;
/*     */     }
/* 127 */     for (int i = 0; i < headers.length; i++) {
/* 128 */       this.headers.add(headers[i]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public Header getCondensedHeader(String name)
/*     */   {
/* 144 */     Header[] headers = getHeaders(name);
/*     */     
/* 146 */     if (headers.length == 0)
/* 147 */       return null;
/* 148 */     if (headers.length == 1) {
/* 149 */       return headers[0];
/*     */     }
/* 151 */     CharArrayBuffer valueBuffer = new CharArrayBuffer(128);
/* 152 */     valueBuffer.append(headers[0].getValue());
/* 153 */     for (int i = 1; i < headers.length; i++) {
/* 154 */       valueBuffer.append(", ");
/* 155 */       valueBuffer.append(headers[i].getValue());
/*     */     }
/*     */     
/* 158 */     return new BasicHeader(name.toLowerCase(Locale.ENGLISH), valueBuffer.toString());
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
/*     */   public Header[] getHeaders(String name)
/*     */   {
/* 173 */     ArrayList headersFound = new ArrayList();
/*     */     
/* 175 */     for (int i = 0; i < this.headers.size(); i++) {
/* 176 */       Header header = (Header)this.headers.get(i);
/* 177 */       if (header.getName().equalsIgnoreCase(name)) {
/* 178 */         headersFound.add(header);
/*     */       }
/*     */     }
/*     */     
/* 182 */     return (Header[])headersFound.toArray(new Header[headersFound.size()]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Header getFirstHeader(String name)
/*     */   {
/* 194 */     for (int i = 0; i < this.headers.size(); i++) {
/* 195 */       Header header = (Header)this.headers.get(i);
/* 196 */       if (header.getName().equalsIgnoreCase(name)) {
/* 197 */         return header;
/*     */       }
/*     */     }
/* 200 */     return null;
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
/*     */   public Header getLastHeader(String name)
/*     */   {
/* 213 */     for (int i = this.headers.size() - 1; i >= 0; i--) {
/* 214 */       Header header = (Header)this.headers.get(i);
/* 215 */       if (header.getName().equalsIgnoreCase(name)) {
/* 216 */         return header;
/*     */       }
/*     */     }
/*     */     
/* 220 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Header[] getAllHeaders()
/*     */   {
/* 229 */     return (Header[])this.headers.toArray(new Header[this.headers.size()]);
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
/*     */   public boolean containsHeader(String name)
/*     */   {
/* 242 */     for (int i = 0; i < this.headers.size(); i++) {
/* 243 */       Header header = (Header)this.headers.get(i);
/* 244 */       if (header.getName().equalsIgnoreCase(name)) {
/* 245 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 249 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HeaderIterator iterator()
/*     */   {
/* 260 */     return new BasicListHeaderIterator(this.headers, null);
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
/*     */   public HeaderIterator iterator(String name)
/*     */   {
/* 274 */     return new BasicListHeaderIterator(this.headers, name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HeaderGroup copy()
/*     */   {
/* 283 */     HeaderGroup clone = new HeaderGroup();
/* 284 */     clone.headers.addAll(this.headers);
/* 285 */     return clone;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 289 */     HeaderGroup clone = (HeaderGroup)super.clone();
/* 290 */     clone.headers = new ArrayList(this.headers);
/* 291 */     return clone;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/HeaderGroup.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */