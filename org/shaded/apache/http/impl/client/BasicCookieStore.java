/*     */ package org.shaded.apache.http.impl.client;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.shaded.apache.http.annotation.GuardedBy;
/*     */ import org.shaded.apache.http.annotation.ThreadSafe;
/*     */ import org.shaded.apache.http.client.CookieStore;
/*     */ import org.shaded.apache.http.cookie.Cookie;
/*     */ import org.shaded.apache.http.cookie.CookieIdentityComparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public class BasicCookieStore
/*     */   implements CookieStore
/*     */ {
/*     */   @GuardedBy("this")
/*     */   private final ArrayList<Cookie> cookies;
/*     */   @GuardedBy("this")
/*     */   private final Comparator<Cookie> cookieComparator;
/*     */   
/*     */   public BasicCookieStore()
/*     */   {
/*  65 */     this.cookies = new ArrayList();
/*  66 */     this.cookieComparator = new CookieIdentityComparator();
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
/*     */   public synchronized void addCookie(Cookie cookie)
/*     */   {
/*  80 */     if (cookie != null)
/*     */     {
/*  82 */       for (Iterator<Cookie> it = this.cookies.iterator(); it.hasNext();) {
/*  83 */         if (this.cookieComparator.compare(cookie, it.next()) == 0) {
/*  84 */           it.remove();
/*     */         }
/*     */       }
/*     */       
/*  88 */       if (!cookie.isExpired(new Date())) {
/*  89 */         this.cookies.add(cookie);
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
/*     */   public synchronized void addCookies(Cookie[] cookies)
/*     */   {
/* 105 */     if (cookies != null) {
/* 106 */       for (Cookie cooky : cookies) {
/* 107 */         addCookie(cooky);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized List<Cookie> getCookies()
/*     */   {
/* 119 */     return Collections.unmodifiableList(this.cookies);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized boolean clearExpired(Date date)
/*     */   {
/* 131 */     if (date == null) {
/* 132 */       return false;
/*     */     }
/* 134 */     boolean removed = false;
/* 135 */     for (Iterator<Cookie> it = this.cookies.iterator(); it.hasNext();) {
/* 136 */       if (((Cookie)it.next()).isExpired(date)) {
/* 137 */         it.remove();
/* 138 */         removed = true;
/*     */       }
/*     */     }
/* 141 */     return removed;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 146 */     return this.cookies.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void clear()
/*     */   {
/* 153 */     this.cookies.clear();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/client/BasicCookieStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */