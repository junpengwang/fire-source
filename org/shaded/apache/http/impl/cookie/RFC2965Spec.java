/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.shaded.apache.http.Header;
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.NameValuePair;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.cookie.ClientCookie;
/*     */ import org.shaded.apache.http.cookie.Cookie;
/*     */ import org.shaded.apache.http.cookie.CookieAttributeHandler;
/*     */ import org.shaded.apache.http.cookie.CookieOrigin;
/*     */ import org.shaded.apache.http.cookie.MalformedCookieException;
/*     */ import org.shaded.apache.http.message.BufferedHeader;
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
/*     */ @NotThreadSafe
/*     */ public class RFC2965Spec
/*     */   extends RFC2109Spec
/*     */ {
/*     */   public RFC2965Spec()
/*     */   {
/*  63 */     this(null, false);
/*     */   }
/*     */   
/*     */   public RFC2965Spec(String[] datepatterns, boolean oneHeader) {
/*  67 */     super(datepatterns, oneHeader);
/*  68 */     registerAttribHandler("domain", new RFC2965DomainAttributeHandler());
/*  69 */     registerAttribHandler("port", new RFC2965PortAttributeHandler());
/*  70 */     registerAttribHandler("commenturl", new RFC2965CommentUrlAttributeHandler());
/*  71 */     registerAttribHandler("discard", new RFC2965DiscardAttributeHandler());
/*  72 */     registerAttribHandler("version", new RFC2965VersionAttributeHandler());
/*     */   }
/*     */   
/*     */ 
/*     */   public List<Cookie> parse(Header header, CookieOrigin origin)
/*     */     throws MalformedCookieException
/*     */   {
/*  79 */     if (header == null) {
/*  80 */       throw new IllegalArgumentException("Header may not be null");
/*     */     }
/*  82 */     if (origin == null) {
/*  83 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/*  85 */     if (!header.getName().equalsIgnoreCase("Set-Cookie2")) {
/*  86 */       throw new MalformedCookieException("Unrecognized cookie header '" + header.toString() + "'");
/*     */     }
/*     */     
/*  89 */     origin = adjustEffectiveHost(origin);
/*  90 */     HeaderElement[] elems = header.getElements();
/*  91 */     return createCookies(elems, origin);
/*     */   }
/*     */   
/*     */ 
/*     */   protected List<Cookie> parse(HeaderElement[] elems, CookieOrigin origin)
/*     */     throws MalformedCookieException
/*     */   {
/*  98 */     origin = adjustEffectiveHost(origin);
/*  99 */     return createCookies(elems, origin);
/*     */   }
/*     */   
/*     */   private List<Cookie> createCookies(HeaderElement[] elems, CookieOrigin origin)
/*     */     throws MalformedCookieException
/*     */   {
/* 105 */     List<Cookie> cookies = new ArrayList(elems.length);
/* 106 */     for (HeaderElement headerelement : elems) {
/* 107 */       String name = headerelement.getName();
/* 108 */       String value = headerelement.getValue();
/* 109 */       if ((name == null) || (name.length() == 0)) {
/* 110 */         throw new MalformedCookieException("Cookie name may not be empty");
/*     */       }
/*     */       
/* 113 */       BasicClientCookie2 cookie = new BasicClientCookie2(name, value);
/* 114 */       cookie.setPath(getDefaultPath(origin));
/* 115 */       cookie.setDomain(getDefaultDomain(origin));
/* 116 */       cookie.setPorts(new int[] { origin.getPort() });
/*     */       
/* 118 */       NameValuePair[] attribs = headerelement.getParameters();
/*     */       
/*     */ 
/*     */ 
/* 122 */       Map<String, NameValuePair> attribmap = new HashMap(attribs.length);
/*     */       
/* 124 */       for (int j = attribs.length - 1; j >= 0; j--) {
/* 125 */         NameValuePair param = attribs[j];
/* 126 */         attribmap.put(param.getName().toLowerCase(Locale.ENGLISH), param);
/*     */       }
/* 128 */       for (Map.Entry<String, NameValuePair> entry : attribmap.entrySet()) {
/* 129 */         NameValuePair attrib = (NameValuePair)entry.getValue();
/* 130 */         String s = attrib.getName().toLowerCase(Locale.ENGLISH);
/*     */         
/* 132 */         cookie.setAttribute(s, attrib.getValue());
/*     */         
/* 134 */         CookieAttributeHandler handler = findAttribHandler(s);
/* 135 */         if (handler != null) {
/* 136 */           handler.parse(cookie, attrib.getValue());
/*     */         }
/*     */       }
/* 139 */       cookies.add(cookie);
/*     */     }
/* 141 */     return cookies;
/*     */   }
/*     */   
/*     */   public void validate(Cookie cookie, CookieOrigin origin)
/*     */     throws MalformedCookieException
/*     */   {
/* 147 */     if (cookie == null) {
/* 148 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 150 */     if (origin == null) {
/* 151 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 153 */     origin = adjustEffectiveHost(origin);
/* 154 */     super.validate(cookie, origin);
/*     */   }
/*     */   
/*     */   public boolean match(Cookie cookie, CookieOrigin origin)
/*     */   {
/* 159 */     if (cookie == null) {
/* 160 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 162 */     if (origin == null) {
/* 163 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 165 */     origin = adjustEffectiveHost(origin);
/* 166 */     return super.match(cookie, origin);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void formatCookieAsVer(CharArrayBuffer buffer, Cookie cookie, int version)
/*     */   {
/* 175 */     super.formatCookieAsVer(buffer, cookie, version);
/*     */     
/* 177 */     if ((cookie instanceof ClientCookie))
/*     */     {
/* 179 */       String s = ((ClientCookie)cookie).getAttribute("port");
/* 180 */       if (s != null) {
/* 181 */         buffer.append("; $Port");
/* 182 */         buffer.append("=\"");
/* 183 */         if (s.trim().length() > 0) {
/* 184 */           int[] ports = cookie.getPorts();
/* 185 */           if (ports != null) {
/* 186 */             int i = 0; for (int len = ports.length; i < len; i++) {
/* 187 */               if (i > 0) {
/* 188 */                 buffer.append(",");
/*     */               }
/* 190 */               buffer.append(Integer.toString(ports[i]));
/*     */             }
/*     */           }
/*     */         }
/* 194 */         buffer.append("\"");
/*     */       }
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
/*     */   private static CookieOrigin adjustEffectiveHost(CookieOrigin origin)
/*     */   {
/* 211 */     String host = origin.getHost();
/*     */     
/*     */ 
/*     */ 
/* 215 */     boolean isLocalHost = true;
/* 216 */     for (int i = 0; i < host.length(); i++) {
/* 217 */       char ch = host.charAt(i);
/* 218 */       if ((ch == '.') || (ch == ':')) {
/* 219 */         isLocalHost = false;
/* 220 */         break;
/*     */       }
/*     */     }
/* 223 */     if (isLocalHost) {
/* 224 */       host = host + ".local";
/* 225 */       return new CookieOrigin(host, origin.getPort(), origin.getPath(), origin.isSecure());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 231 */     return origin;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getVersion()
/*     */   {
/* 237 */     return 1;
/*     */   }
/*     */   
/*     */   public Header getVersionHeader()
/*     */   {
/* 242 */     CharArrayBuffer buffer = new CharArrayBuffer(40);
/* 243 */     buffer.append("Cookie2");
/* 244 */     buffer.append(": ");
/* 245 */     buffer.append("$Version=");
/* 246 */     buffer.append(Integer.toString(getVersion()));
/* 247 */     return new BufferedHeader(buffer);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 252 */     return "rfc2965";
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/RFC2965Spec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */