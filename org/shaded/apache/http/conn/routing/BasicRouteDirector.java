/*     */ package org.shaded.apache.http.conn.routing;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import org.shaded.apache.http.HttpHost;
/*     */ import org.shaded.apache.http.annotation.Immutable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class BasicRouteDirector
/*     */   implements HttpRouteDirector
/*     */ {
/*     */   public int nextStep(RouteInfo plan, RouteInfo fact)
/*     */   {
/*  53 */     if (plan == null) {
/*  54 */       throw new IllegalArgumentException("Planned route may not be null.");
/*     */     }
/*     */     
/*     */ 
/*  58 */     int step = -1;
/*     */     
/*  60 */     if ((fact == null) || (fact.getHopCount() < 1)) {
/*  61 */       step = firstStep(plan);
/*  62 */     } else if (plan.getHopCount() > 1) {
/*  63 */       step = proxiedStep(plan, fact);
/*     */     } else {
/*  65 */       step = directStep(plan, fact);
/*     */     }
/*  67 */     return step;
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
/*     */   protected int firstStep(RouteInfo plan)
/*     */   {
/*  81 */     return plan.getHopCount() > 1 ? 2 : 1;
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
/*     */   protected int directStep(RouteInfo plan, RouteInfo fact)
/*     */   {
/*  97 */     if (fact.getHopCount() > 1)
/*  98 */       return -1;
/*  99 */     if (!plan.getTargetHost().equals(fact.getTargetHost())) {
/* 100 */       return -1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 108 */     if (plan.isSecure() != fact.isSecure()) {
/* 109 */       return -1;
/*     */     }
/*     */     
/* 112 */     if ((plan.getLocalAddress() != null) && (!plan.getLocalAddress().equals(fact.getLocalAddress())))
/*     */     {
/*     */ 
/* 115 */       return -1;
/*     */     }
/* 117 */     return 0;
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
/*     */   protected int proxiedStep(RouteInfo plan, RouteInfo fact)
/*     */   {
/* 132 */     if (fact.getHopCount() <= 1)
/* 133 */       return -1;
/* 134 */     if (!plan.getTargetHost().equals(fact.getTargetHost()))
/* 135 */       return -1;
/* 136 */     int phc = plan.getHopCount();
/* 137 */     int fhc = fact.getHopCount();
/* 138 */     if (phc < fhc) {
/* 139 */       return -1;
/*     */     }
/* 141 */     for (int i = 0; i < fhc - 1; i++) {
/* 142 */       if (!plan.getHopTarget(i).equals(fact.getHopTarget(i))) {
/* 143 */         return -1;
/*     */       }
/*     */     }
/* 146 */     if (phc > fhc) {
/* 147 */       return 4;
/*     */     }
/*     */     
/* 150 */     if (((fact.isTunnelled()) && (!plan.isTunnelled())) || ((fact.isLayered()) && (!plan.isLayered())))
/*     */     {
/* 152 */       return -1;
/*     */     }
/* 154 */     if ((plan.isTunnelled()) && (!fact.isTunnelled()))
/* 155 */       return 3;
/* 156 */     if ((plan.isLayered()) && (!fact.isLayered())) {
/* 157 */       return 5;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 162 */     if (plan.isSecure() != fact.isSecure()) {
/* 163 */       return -1;
/*     */     }
/* 165 */     return 0;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/conn/routing/BasicRouteDirector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */