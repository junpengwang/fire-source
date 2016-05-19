/*     */ package org.shaded.apache.http.impl;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import org.shaded.apache.http.HttpConnectionMetrics;
/*     */ import org.shaded.apache.http.io.HttpTransportMetrics;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpConnectionMetricsImpl
/*     */   implements HttpConnectionMetrics
/*     */ {
/*     */   public static final String REQUEST_COUNT = "http.request-count";
/*     */   public static final String RESPONSE_COUNT = "http.response-count";
/*     */   public static final String SENT_BYTES_COUNT = "http.sent-bytes-count";
/*     */   public static final String RECEIVED_BYTES_COUNT = "http.received-bytes-count";
/*     */   private final HttpTransportMetrics inTransportMetric;
/*     */   private final HttpTransportMetrics outTransportMetric;
/*  52 */   private long requestCount = 0L;
/*  53 */   private long responseCount = 0L;
/*     */   
/*     */ 
/*     */ 
/*     */   private HashMap metricsCache;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpConnectionMetricsImpl(HttpTransportMetrics inTransportMetric, HttpTransportMetrics outTransportMetric)
/*     */   {
/*  64 */     this.inTransportMetric = inTransportMetric;
/*  65 */     this.outTransportMetric = outTransportMetric;
/*     */   }
/*     */   
/*     */ 
/*     */   public long getReceivedBytesCount()
/*     */   {
/*  71 */     if (this.inTransportMetric != null) {
/*  72 */       return this.inTransportMetric.getBytesTransferred();
/*     */     }
/*  74 */     return -1L;
/*     */   }
/*     */   
/*     */   public long getSentBytesCount()
/*     */   {
/*  79 */     if (this.outTransportMetric != null) {
/*  80 */       return this.outTransportMetric.getBytesTransferred();
/*     */     }
/*  82 */     return -1L;
/*     */   }
/*     */   
/*     */   public long getRequestCount()
/*     */   {
/*  87 */     return this.requestCount;
/*     */   }
/*     */   
/*     */   public void incrementRequestCount() {
/*  91 */     this.requestCount += 1L;
/*     */   }
/*     */   
/*     */   public long getResponseCount() {
/*  95 */     return this.responseCount;
/*     */   }
/*     */   
/*     */   public void incrementResponseCount() {
/*  99 */     this.responseCount += 1L;
/*     */   }
/*     */   
/*     */   public Object getMetric(String metricName) {
/* 103 */     Object value = null;
/* 104 */     if (this.metricsCache != null) {
/* 105 */       value = this.metricsCache.get(metricName);
/*     */     }
/* 107 */     if (value == null) {
/* 108 */       if ("http.request-count".equals(metricName)) {
/* 109 */         value = new Long(this.requestCount);
/* 110 */       } else if ("http.response-count".equals(metricName)) {
/* 111 */         value = new Long(this.responseCount);
/* 112 */       } else { if ("http.received-bytes-count".equals(metricName)) {
/* 113 */           if (this.inTransportMetric != null) {
/* 114 */             return new Long(this.inTransportMetric.getBytesTransferred());
/*     */           }
/* 116 */           return null;
/*     */         }
/* 118 */         if ("http.sent-bytes-count".equals(metricName)) {
/* 119 */           if (this.outTransportMetric != null) {
/* 120 */             return new Long(this.outTransportMetric.getBytesTransferred());
/*     */           }
/* 122 */           return null;
/*     */         }
/*     */       }
/*     */     }
/* 126 */     return value;
/*     */   }
/*     */   
/*     */   public void setMetric(String metricName, Object obj) {
/* 130 */     if (this.metricsCache == null) {
/* 131 */       this.metricsCache = new HashMap();
/*     */     }
/* 133 */     this.metricsCache.put(metricName, obj);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 137 */     if (this.outTransportMetric != null) {
/* 138 */       this.outTransportMetric.reset();
/*     */     }
/* 140 */     if (this.inTransportMetric != null) {
/* 141 */       this.inTransportMetric.reset();
/*     */     }
/* 143 */     this.requestCount = 0L;
/* 144 */     this.responseCount = 0L;
/* 145 */     this.metricsCache = null;
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/HttpConnectionMetricsImpl.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */