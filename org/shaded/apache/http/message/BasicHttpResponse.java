/*     */ package org.shaded.apache.http.message;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.shaded.apache.http.HttpEntity;
/*     */ import org.shaded.apache.http.HttpResponse;
/*     */ import org.shaded.apache.http.ProtocolVersion;
/*     */ import org.shaded.apache.http.ReasonPhraseCatalog;
/*     */ import org.shaded.apache.http.StatusLine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicHttpResponse
/*     */   extends AbstractHttpMessage
/*     */   implements HttpResponse
/*     */ {
/*     */   private StatusLine statusline;
/*     */   private HttpEntity entity;
/*     */   private ReasonPhraseCatalog reasonCatalog;
/*     */   private Locale locale;
/*     */   
/*     */   public BasicHttpResponse(StatusLine statusline, ReasonPhraseCatalog catalog, Locale locale)
/*     */   {
/*  74 */     if (statusline == null) {
/*  75 */       throw new IllegalArgumentException("Status line may not be null.");
/*     */     }
/*  77 */     this.statusline = statusline;
/*  78 */     this.reasonCatalog = catalog;
/*  79 */     this.locale = (locale != null ? locale : Locale.getDefault());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BasicHttpResponse(StatusLine statusline)
/*     */   {
/*  90 */     this(statusline, null, null);
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
/*     */   public BasicHttpResponse(ProtocolVersion ver, int code, String reason)
/*     */   {
/* 106 */     this(new BasicStatusLine(ver, code, reason), null, null);
/*     */   }
/*     */   
/*     */ 
/*     */   public ProtocolVersion getProtocolVersion()
/*     */   {
/* 112 */     return this.statusline.getProtocolVersion();
/*     */   }
/*     */   
/*     */   public StatusLine getStatusLine()
/*     */   {
/* 117 */     return this.statusline;
/*     */   }
/*     */   
/*     */   public HttpEntity getEntity()
/*     */   {
/* 122 */     return this.entity;
/*     */   }
/*     */   
/*     */   public Locale getLocale()
/*     */   {
/* 127 */     return this.locale;
/*     */   }
/*     */   
/*     */   public void setStatusLine(StatusLine statusline)
/*     */   {
/* 132 */     if (statusline == null) {
/* 133 */       throw new IllegalArgumentException("Status line may not be null");
/*     */     }
/* 135 */     this.statusline = statusline;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setStatusLine(ProtocolVersion ver, int code)
/*     */   {
/* 141 */     this.statusline = new BasicStatusLine(ver, code, getReason(code));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setStatusLine(ProtocolVersion ver, int code, String reason)
/*     */   {
/* 148 */     this.statusline = new BasicStatusLine(ver, code, reason);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setStatusCode(int code)
/*     */   {
/* 154 */     ProtocolVersion ver = this.statusline.getProtocolVersion();
/* 155 */     this.statusline = new BasicStatusLine(ver, code, getReason(code));
/*     */   }
/*     */   
/*     */ 
/*     */   public void setReasonPhrase(String reason)
/*     */   {
/* 161 */     if ((reason != null) && ((reason.indexOf('\n') >= 0) || (reason.indexOf('\r') >= 0)))
/*     */     {
/*     */ 
/* 164 */       throw new IllegalArgumentException("Line break in reason phrase.");
/*     */     }
/* 166 */     this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), this.statusline.getStatusCode(), reason);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setEntity(HttpEntity entity)
/*     */   {
/* 173 */     this.entity = entity;
/*     */   }
/*     */   
/*     */   public void setLocale(Locale loc)
/*     */   {
/* 178 */     if (loc == null) {
/* 179 */       throw new IllegalArgumentException("Locale may not be null.");
/*     */     }
/* 181 */     this.locale = loc;
/* 182 */     int code = this.statusline.getStatusCode();
/* 183 */     this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), code, getReason(code));
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
/*     */   protected String getReason(int code)
/*     */   {
/* 197 */     return this.reasonCatalog == null ? null : this.reasonCatalog.getReason(code, this.locale);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/message/BasicHttpResponse.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */