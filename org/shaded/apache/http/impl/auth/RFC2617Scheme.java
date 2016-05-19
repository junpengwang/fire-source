/*     */ package org.shaded.apache.http.impl.auth;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.shaded.apache.http.HeaderElement;
/*     */ import org.shaded.apache.http.annotation.NotThreadSafe;
/*     */ import org.shaded.apache.http.auth.MalformedChallengeException;
/*     */ import org.shaded.apache.http.message.BasicHeaderValueParser;
/*     */ import org.shaded.apache.http.message.HeaderValueParser;
/*     */ import org.shaded.apache.http.message.ParserCursor;
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
/*     */ @NotThreadSafe
/*     */ public abstract class RFC2617Scheme
/*     */   extends AuthSchemeBase
/*     */ {
/*     */   private Map<String, String> params;
/*     */   
/*     */   protected void parseChallenge(CharArrayBuffer buffer, int pos, int len)
/*     */     throws MalformedChallengeException
/*     */   {
/*  67 */     HeaderValueParser parser = BasicHeaderValueParser.DEFAULT;
/*  68 */     ParserCursor cursor = new ParserCursor(pos, buffer.length());
/*  69 */     HeaderElement[] elements = parser.parseElements(buffer, cursor);
/*  70 */     if (elements.length == 0) {
/*  71 */       throw new MalformedChallengeException("Authentication challenge is empty");
/*     */     }
/*     */     
/*  74 */     this.params = new HashMap(elements.length);
/*  75 */     for (HeaderElement element : elements) {
/*  76 */       this.params.put(element.getName(), element.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Map<String, String> getParameters()
/*     */   {
/*  86 */     if (this.params == null) {
/*  87 */       this.params = new HashMap();
/*     */     }
/*  89 */     return this.params;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getParameter(String name)
/*     */   {
/* 100 */     if (name == null) {
/* 101 */       throw new IllegalArgumentException("Parameter name may not be null");
/*     */     }
/* 103 */     if (this.params == null) {
/* 104 */       return null;
/*     */     }
/* 106 */     return (String)this.params.get(name.toLowerCase(Locale.ENGLISH));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getRealm()
/*     */   {
/* 115 */     return getParameter("realm");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/auth/RFC2617Scheme.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */