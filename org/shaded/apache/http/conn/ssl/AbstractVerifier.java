/*     */ package org.shaded.apache.http.conn.ssl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateParsingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ import org.shaded.apache.http.conn.util.InetAddressUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class AbstractVerifier
/*     */   implements X509HostnameVerifier
/*     */ {
/*  72 */   private static final String[] BAD_COUNTRY_2LDS = { "ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", "lg", "ne", "net", "or", "org" };
/*     */   
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*  78 */     Arrays.sort(BAD_COUNTRY_2LDS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void verify(String host, SSLSocket ssl)
/*     */     throws IOException
/*     */   {
/*  87 */     if (host == null) {
/*  88 */       throw new NullPointerException("host to verify is null");
/*     */     }
/*     */     
/*  91 */     SSLSession session = ssl.getSession();
/*  92 */     if (session == null)
/*     */     {
/*     */ 
/*     */ 
/*  96 */       InputStream in = ssl.getInputStream();
/*  97 */       in.available();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 116 */       session = ssl.getSession();
/* 117 */       if (session == null)
/*     */       {
/*     */ 
/* 120 */         ssl.startHandshake();
/*     */         
/*     */ 
/*     */ 
/* 124 */         session = ssl.getSession();
/*     */       }
/*     */     }
/*     */     
/* 128 */     Certificate[] certs = session.getPeerCertificates();
/* 129 */     X509Certificate x509 = (X509Certificate)certs[0];
/* 130 */     verify(host, x509);
/*     */   }
/*     */   
/*     */   public final boolean verify(String host, SSLSession session) {
/*     */     try {
/* 135 */       Certificate[] certs = session.getPeerCertificates();
/* 136 */       X509Certificate x509 = (X509Certificate)certs[0];
/* 137 */       verify(host, x509);
/* 138 */       return true;
/*     */     }
/*     */     catch (SSLException e) {}
/* 141 */     return false;
/*     */   }
/*     */   
/*     */   public final void verify(String host, X509Certificate cert)
/*     */     throws SSLException
/*     */   {
/* 147 */     String[] cns = getCNs(cert);
/* 148 */     String[] subjectAlts = getSubjectAlts(cert, host);
/* 149 */     verify(host, cns, subjectAlts);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void verify(String host, String[] cns, String[] subjectAlts, boolean strictWithSubDomains)
/*     */     throws SSLException
/*     */   {
/* 161 */     LinkedList<String> names = new LinkedList();
/* 162 */     if ((cns != null) && (cns.length > 0) && (cns[0] != null)) {
/* 163 */       names.add(cns[0]);
/*     */     }
/* 165 */     if (subjectAlts != null) {
/* 166 */       for (String subjectAlt : subjectAlts) {
/* 167 */         if (subjectAlt != null) {
/* 168 */           names.add(subjectAlt);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 173 */     if (names.isEmpty()) {
/* 174 */       String msg = "Certificate for <" + host + "> doesn't contain CN or DNS subjectAlt";
/* 175 */       throw new SSLException(msg);
/*     */     }
/*     */     
/*     */ 
/* 179 */     StringBuffer buf = new StringBuffer();
/*     */     
/*     */ 
/*     */ 
/* 183 */     String hostName = host.trim().toLowerCase(Locale.ENGLISH);
/* 184 */     boolean match = false;
/* 185 */     for (Iterator<String> it = names.iterator(); it.hasNext();)
/*     */     {
/* 187 */       String cn = (String)it.next();
/* 188 */       cn = cn.toLowerCase(Locale.ENGLISH);
/*     */       
/* 190 */       buf.append(" <");
/* 191 */       buf.append(cn);
/* 192 */       buf.append('>');
/* 193 */       if (it.hasNext()) {
/* 194 */         buf.append(" OR");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 200 */       boolean doWildcard = (cn.startsWith("*.")) && (cn.lastIndexOf('.') >= 0) && (acceptableCountryWildcard(cn)) && (!isIPAddress(host));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 205 */       if (doWildcard) {
/* 206 */         match = hostName.endsWith(cn.substring(1));
/* 207 */         if ((match) && (strictWithSubDomains))
/*     */         {
/*     */ 
/* 210 */           match = countDots(hostName) == countDots(cn);
/*     */         }
/*     */       } else {
/* 213 */         match = hostName.equals(cn);
/*     */       }
/* 215 */       if (match) {
/*     */         break;
/*     */       }
/*     */     }
/* 219 */     if (!match) {
/* 220 */       throw new SSLException("hostname in certificate didn't match: <" + host + "> !=" + buf);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean acceptableCountryWildcard(String cn) {
/* 225 */     int cnLen = cn.length();
/* 226 */     if ((cnLen >= 7) && (cnLen <= 9))
/*     */     {
/* 228 */       if (cn.charAt(cnLen - 3) == '.')
/*     */       {
/* 230 */         String s = cn.substring(2, cnLen - 3);
/*     */         
/* 232 */         int x = Arrays.binarySearch(BAD_COUNTRY_2LDS, s);
/* 233 */         return x < 0;
/*     */       }
/*     */     }
/* 236 */     return true;
/*     */   }
/*     */   
/*     */   public static String[] getCNs(X509Certificate cert) {
/* 240 */     LinkedList<String> cnList = new LinkedList();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 263 */     String subjectPrincipal = cert.getSubjectX500Principal().toString();
/* 264 */     StringTokenizer st = new StringTokenizer(subjectPrincipal, ",");
/* 265 */     while (st.hasMoreTokens()) {
/* 266 */       String tok = st.nextToken();
/* 267 */       int x = tok.indexOf("CN=");
/* 268 */       if (x >= 0) {
/* 269 */         cnList.add(tok.substring(x + 3));
/*     */       }
/*     */     }
/* 272 */     if (!cnList.isEmpty()) {
/* 273 */       String[] cns = new String[cnList.size()];
/* 274 */       cnList.toArray(cns);
/* 275 */       return cns;
/*     */     }
/* 277 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String[] getSubjectAlts(X509Certificate cert, String hostname)
/*     */   {
/*     */     int subjectType;
/*     */     
/*     */ 
/*     */ 
/*     */     int subjectType;
/*     */     
/*     */ 
/* 292 */     if (isIPAddress(hostname)) {
/* 293 */       subjectType = 7;
/*     */     } else {
/* 295 */       subjectType = 2;
/*     */     }
/*     */     
/* 298 */     LinkedList<String> subjectAltList = new LinkedList();
/* 299 */     Collection<List<?>> c = null;
/*     */     try {
/* 301 */       c = cert.getSubjectAlternativeNames();
/*     */     }
/*     */     catch (CertificateParsingException cpe) {
/* 304 */       Logger.getLogger(AbstractVerifier.class.getName()).log(Level.FINE, "Error parsing certificate.", cpe);
/*     */     }
/*     */     
/* 307 */     if (c != null) {
/* 308 */       for (List<?> aC : c) {
/* 309 */         List<?> list = aC;
/* 310 */         int type = ((Integer)list.get(0)).intValue();
/* 311 */         if (type == subjectType) {
/* 312 */           String s = (String)list.get(1);
/* 313 */           subjectAltList.add(s);
/*     */         }
/*     */       }
/*     */     }
/* 317 */     if (!subjectAltList.isEmpty()) {
/* 318 */       String[] subjectAlts = new String[subjectAltList.size()];
/* 319 */       subjectAltList.toArray(subjectAlts);
/* 320 */       return subjectAlts;
/*     */     }
/* 322 */     return null;
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
/*     */   public static String[] getDNSSubjectAlts(X509Certificate cert)
/*     */   {
/* 341 */     return getSubjectAlts(cert, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int countDots(String s)
/*     */   {
/* 350 */     int count = 0;
/* 351 */     for (int i = 0; i < s.length(); i++) {
/* 352 */       if (s.charAt(i) == '.') {
/* 353 */         count++;
/*     */       }
/*     */     }
/* 356 */     return count;
/*     */   }
/*     */   
/*     */   private static boolean isIPAddress(String hostname) {
/* 360 */     return (hostname != null) && ((InetAddressUtils.isIPv4Address(hostname)) || (InetAddressUtils.isIPv6Address(hostname)));
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/ssl/AbstractVerifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */