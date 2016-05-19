/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HeaderIterator;
/*     */ import org.shaded.apache.http.HttpMessage;
/*     */ import org.shaded.apache.http.params.BasicHttpParams;
/*     */ import org.shaded.apache.http.params.HttpParams;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractHttpMessage
/*     */   implements HttpMessage
/*     */ {
/*     */   protected HeaderGroup headergroup;
/*     */   protected HttpParams params;
/*     */   
/*     */   protected AbstractHttpMessage(HttpParams params)
/*     */   {
/*  58 */     this.headergroup = new HeaderGroup();
/*  59 */     this.params = params;
/*     */   }
/*     */   
/*     */   protected AbstractHttpMessage() {
/*  63 */     this(null);
/*     */   }
/*     */   
/*     */   public boolean containsHeader(String name)
/*     */   {
/*  68 */     return this.headergroup.containsHeader(name);
/*     */   }
/*     */   
/*     */   public Header[] getHeaders(String name)
/*     */   {
/*  73 */     return this.headergroup.getHeaders(name);
/*     */   }
/*     */   
/*     */   public Header getFirstHeader(String name)
/*     */   {
/*  78 */     return this.headergroup.getFirstHeader(name);
/*     */   }
/*     */   
/*     */   public Header getLastHeader(String name)
/*     */   {
/*  83 */     return this.headergroup.getLastHeader(name);
/*     */   }
/*     */   
/*     */   public Header[] getAllHeaders()
/*     */   {
/*  88 */     return this.headergroup.getAllHeaders();
/*     */   }
/*     */   
/*     */   public void addHeader(Header header)
/*     */   {
/*  93 */     this.headergroup.addHeader(header);
/*     */   }
/*     */   
/*     */   public void addHeader(String name, String value)
/*     */   {
/*  98 */     if (name == null) {
/*  99 */       throw new IllegalArgumentException("Header name may not be null");
/*     */     }
/* 101 */     this.headergroup.addHeader(new BasicHeader(name, value));
/*     */   }
/*     */   
/*     */   public void setHeader(Header header)
/*     */   {
/* 106 */     this.headergroup.updateHeader(header);
/*     */   }
/*     */   
/*     */   public void setHeader(String name, String value)
/*     */   {
/* 111 */     if (name == null) {
/* 112 */       throw new IllegalArgumentException("Header name may not be null");
/*     */     }
/* 114 */     this.headergroup.updateHeader(new BasicHeader(name, value));
/*     */   }
/*     */   
/*     */   public void setHeaders(Header[] headers)
/*     */   {
/* 119 */     this.headergroup.setHeaders(headers);
/*     */   }
/*     */   
/*     */   public void removeHeader(Header header)
/*     */   {
/* 124 */     this.headergroup.removeHeader(header);
/*     */   }
/*     */   
/*     */   public void removeHeaders(String name)
/*     */   {
/* 129 */     if (name == null) {
/* 130 */       return;
/*     */     }
/* 132 */     for (Iterator i = this.headergroup.iterator(); i.hasNext();) {
/* 133 */       Header header = (Header)i.next();
/* 134 */       if (name.equalsIgnoreCase(header.getName())) {
/* 135 */         i.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public HeaderIterator headerIterator()
/*     */   {
/* 142 */     return this.headergroup.iterator();
/*     */   }
/*     */   
/*     */   public HeaderIterator headerIterator(String name)
/*     */   {
/* 147 */     return this.headergroup.iterator(name);
/*     */   }
/*     */   
/*     */   public HttpParams getParams()
/*     */   {
/* 152 */     if (this.params == null) {
/* 153 */       this.params = new BasicHttpParams();
/*     */     }
/* 155 */     return this.params;
/*     */   }
/*     */   
/*     */   public void setParams(HttpParams params)
/*     */   {
/* 160 */     if (params == null) {
/* 161 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*     */     }
/* 163 */     this.params = params;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/AbstractHttpMessage.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */