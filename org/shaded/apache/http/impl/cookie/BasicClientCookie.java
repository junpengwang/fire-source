/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.cookie.ClientCookie;
/*     */ import org.shaded.apache.http.cookie.SetCookie;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotThreadSafe
/*     */ public class BasicClientCookie
/*     */   implements SetCookie, ClientCookie, Cloneable
/*     */ {
/*     */   private final String name;
/*     */   private Map<String, String> attribs;
/*     */   private String value;
/*     */   private String cookieComment;
/*     */   private String cookieDomain;
/*     */   private Date cookieExpiryDate;
/*     */   private String cookiePath;
/*     */   private boolean isSecure;
/*     */   private int cookieVersion;
/*     */   
/*     */   public BasicClientCookie(String name, String value)
/*     */   {
/*  56 */     if (name == null) {
/*  57 */       throw new IllegalArgumentException("Name may not be null");
/*     */     }
/*  59 */     this.name = name;
/*  60 */     this.attribs = new HashMap();
/*  61 */     this.value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  70 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getValue()
/*     */   {
/*  79 */     return this.value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/*  88 */     this.value = value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getComment()
/*     */   {
/* 100 */     return this.cookieComment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setComment(String comment)
/*     */   {
/* 112 */     this.cookieComment = comment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCommentURL()
/*     */   {
/* 120 */     return null;
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
/*     */   public Date getExpiryDate()
/*     */   {
/* 136 */     return this.cookieExpiryDate;
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
/*     */   public void setExpiryDate(Date expiryDate)
/*     */   {
/* 151 */     this.cookieExpiryDate = expiryDate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isPersistent()
/*     */   {
/* 163 */     return null != this.cookieExpiryDate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDomain()
/*     */   {
/* 175 */     return this.cookieDomain;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDomain(String domain)
/*     */   {
/* 186 */     if (domain != null) {
/* 187 */       this.cookieDomain = domain.toLowerCase(Locale.ENGLISH);
/*     */     } else {
/* 189 */       this.cookieDomain = null;
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
/*     */   public String getPath()
/*     */   {
/* 202 */     return this.cookiePath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPath(String path)
/*     */   {
/* 214 */     this.cookiePath = path;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSecure()
/*     */   {
/* 222 */     return this.isSecure;
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
/*     */   public void setSecure(boolean secure)
/*     */   {
/* 238 */     this.isSecure = secure;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int[] getPorts()
/*     */   {
/* 246 */     return null;
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
/*     */   public int getVersion()
/*     */   {
/* 260 */     return this.cookieVersion;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVersion(int version)
/*     */   {
/* 272 */     this.cookieVersion = version;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isExpired(Date date)
/*     */   {
/* 282 */     if (date == null) {
/* 283 */       throw new IllegalArgumentException("Date may not be null");
/*     */     }
/* 285 */     return (this.cookieExpiryDate != null) && (this.cookieExpiryDate.getTime() <= date.getTime());
/*     */   }
/*     */   
/*     */   public void setAttribute(String name, String value)
/*     */   {
/* 290 */     this.attribs.put(name, value);
/*     */   }
/*     */   
/*     */   public String getAttribute(String name) {
/* 294 */     return (String)this.attribs.get(name);
/*     */   }
/*     */   
/*     */   public boolean containsAttribute(String name) {
/* 298 */     return this.attribs.get(name) != null;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException
/*     */   {
/* 303 */     BasicClientCookie clone = (BasicClientCookie)super.clone();
/* 304 */     clone.attribs = new HashMap(this.attribs);
/* 305 */     return clone;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 310 */     StringBuilder buffer = new StringBuilder();
/* 311 */     buffer.append("[version: ");
/* 312 */     buffer.append(Integer.toString(this.cookieVersion));
/* 313 */     buffer.append("]");
/* 314 */     buffer.append("[name: ");
/* 315 */     buffer.append(this.name);
/* 316 */     buffer.append("]");
/* 317 */     buffer.append("[value: ");
/* 318 */     buffer.append(this.value);
/* 319 */     buffer.append("]");
/* 320 */     buffer.append("[domain: ");
/* 321 */     buffer.append(this.cookieDomain);
/* 322 */     buffer.append("]");
/* 323 */     buffer.append("[path: ");
/* 324 */     buffer.append(this.cookiePath);
/* 325 */     buffer.append("]");
/* 326 */     buffer.append("[expiry: ");
/* 327 */     buffer.append(this.cookieExpiryDate);
/* 328 */     buffer.append("]");
/* 329 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/BasicClientCookie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */