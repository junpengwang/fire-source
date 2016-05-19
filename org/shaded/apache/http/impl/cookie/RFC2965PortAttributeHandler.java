/*     */ package org.shaded.apache.http.impl.cookie;
/*     */ 
/*     */ import java.util.StringTokenizer;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.cookie.ClientCookie;
/*     */ import org.shaded.apache.http.cookie.Cookie;
/*     */ import org.shaded.apache.http.cookie.CookieAttributeHandler;
/*     */ import org.shaded.apache.http.cookie.CookieOrigin;
/*     */ import org.shaded.apache.http.cookie.MalformedCookieException;
/*     */ import org.shaded.apache.http.cookie.SetCookie;
/*     */ import org.shaded.apache.http.cookie.SetCookie2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public class RFC2965PortAttributeHandler
/*     */   implements CookieAttributeHandler
/*     */ {
/*     */   private static int[] parsePortAttribute(String portValue)
/*     */     throws MalformedCookieException
/*     */   {
/*  65 */     StringTokenizer st = new StringTokenizer(portValue, ",");
/*  66 */     int[] ports = new int[st.countTokens()];
/*     */     try {
/*  68 */       int i = 0;
/*  69 */       while (st.hasMoreTokens()) {
/*  70 */         ports[i] = Integer.parseInt(st.nextToken().trim());
/*  71 */         if (ports[i] < 0) {
/*  72 */           throw new MalformedCookieException("Invalid Port attribute.");
/*     */         }
/*  74 */         i++;
/*     */       }
/*     */     } catch (NumberFormatException e) {
/*  77 */       throw new MalformedCookieException("Invalid Port attribute: " + e.getMessage());
/*     */     }
/*     */     
/*  80 */     return ports;
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
/*     */   private static boolean portMatch(int port, int[] ports)
/*     */   {
/*  93 */     boolean portInList = false;
/*  94 */     int i = 0; for (int len = ports.length; i < len; i++) {
/*  95 */       if (port == ports[i]) {
/*  96 */         portInList = true;
/*  97 */         break;
/*     */       }
/*     */     }
/* 100 */     return portInList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void parse(SetCookie cookie, String portValue)
/*     */     throws MalformedCookieException
/*     */   {
/* 108 */     if (cookie == null) {
/* 109 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 111 */     if ((cookie instanceof SetCookie2)) {
/* 112 */       SetCookie2 cookie2 = (SetCookie2)cookie;
/* 113 */       if ((portValue != null) && (portValue.trim().length() > 0)) {
/* 114 */         int[] ports = parsePortAttribute(portValue);
/* 115 */         cookie2.setPorts(ports);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void validate(Cookie cookie, CookieOrigin origin)
/*     */     throws MalformedCookieException
/*     */   {
/* 126 */     if (cookie == null) {
/* 127 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 129 */     if (origin == null) {
/* 130 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 132 */     int port = origin.getPort();
/* 133 */     if (((cookie instanceof ClientCookie)) && (((ClientCookie)cookie).containsAttribute("port")))
/*     */     {
/* 135 */       if (!portMatch(port, cookie.getPorts())) {
/* 136 */         throw new MalformedCookieException("Port attribute violates RFC 2965: Request port not found in cookie's port list.");
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
/*     */   public boolean match(Cookie cookie, CookieOrigin origin)
/*     */   {
/* 149 */     if (cookie == null) {
/* 150 */       throw new IllegalArgumentException("Cookie may not be null");
/*     */     }
/* 152 */     if (origin == null) {
/* 153 */       throw new IllegalArgumentException("Cookie origin may not be null");
/*     */     }
/* 155 */     int port = origin.getPort();
/* 156 */     if (((cookie instanceof ClientCookie)) && (((ClientCookie)cookie).containsAttribute("port")))
/*     */     {
/* 158 */       if (cookie.getPorts() == null)
/*     */       {
/* 160 */         return false;
/*     */       }
/* 162 */       if (!portMatch(port, cookie.getPorts())) {
/* 163 */         return false;
/*     */       }
/*     */     }
/* 166 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/cookie/RFC2965PortAttributeHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */