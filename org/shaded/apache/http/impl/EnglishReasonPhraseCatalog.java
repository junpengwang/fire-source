/*     */ package org.shaded.apache.http.impl;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.shaded.apache.http.ReasonPhraseCatalog;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnglishReasonPhraseCatalog
/*     */   implements ReasonPhraseCatalog
/*     */ {
/*  59 */   public static final EnglishReasonPhraseCatalog INSTANCE = new EnglishReasonPhraseCatalog();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getReason(int status, Locale loc)
/*     */   {
/*  81 */     if ((status < 100) || (status >= 600)) {
/*  82 */       throw new IllegalArgumentException("Unknown category for status code " + status + ".");
/*     */     }
/*     */     
/*     */ 
/*  86 */     int category = status / 100;
/*  87 */     int subcode = status - 100 * category;
/*     */     
/*  89 */     String reason = null;
/*  90 */     if (REASON_PHRASES[category].length > subcode) {
/*  91 */       reason = REASON_PHRASES[category][subcode];
/*     */     }
/*  93 */     return reason;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  98 */   private static final String[][] REASON_PHRASES = { null, new String[3], new String[8], new String[8], new String[25], new String[8] };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void setReason(int status, String reason)
/*     */   {
/* 117 */     int category = status / 100;
/* 118 */     int subcode = status - 100 * category;
/* 119 */     REASON_PHRASES[category][subcode] = reason;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/* 128 */     setReason(200, "OK");
/*     */     
/* 130 */     setReason(201, "Created");
/*     */     
/* 132 */     setReason(202, "Accepted");
/*     */     
/* 134 */     setReason(204, "No Content");
/*     */     
/* 136 */     setReason(301, "Moved Permanently");
/*     */     
/* 138 */     setReason(302, "Moved Temporarily");
/*     */     
/* 140 */     setReason(304, "Not Modified");
/*     */     
/* 142 */     setReason(400, "Bad Request");
/*     */     
/* 144 */     setReason(401, "Unauthorized");
/*     */     
/* 146 */     setReason(403, "Forbidden");
/*     */     
/* 148 */     setReason(404, "Not Found");
/*     */     
/* 150 */     setReason(500, "Internal Server Error");
/*     */     
/* 152 */     setReason(501, "Not Implemented");
/*     */     
/* 154 */     setReason(502, "Bad Gateway");
/*     */     
/* 156 */     setReason(503, "Service Unavailable");
/*     */     
/*     */ 
/*     */ 
/* 160 */     setReason(100, "Continue");
/*     */     
/* 162 */     setReason(307, "Temporary Redirect");
/*     */     
/* 164 */     setReason(405, "Method Not Allowed");
/*     */     
/* 166 */     setReason(409, "Conflict");
/*     */     
/* 168 */     setReason(412, "Precondition Failed");
/*     */     
/* 170 */     setReason(413, "Request Too Long");
/*     */     
/* 172 */     setReason(414, "Request-URI Too Long");
/*     */     
/* 174 */     setReason(415, "Unsupported Media Type");
/*     */     
/* 176 */     setReason(300, "Multiple Choices");
/*     */     
/* 178 */     setReason(303, "See Other");
/*     */     
/* 180 */     setReason(305, "Use Proxy");
/*     */     
/* 182 */     setReason(402, "Payment Required");
/*     */     
/* 184 */     setReason(406, "Not Acceptable");
/*     */     
/* 186 */     setReason(407, "Proxy Authentication Required");
/*     */     
/* 188 */     setReason(408, "Request Timeout");
/*     */     
/*     */ 
/* 191 */     setReason(101, "Switching Protocols");
/*     */     
/* 193 */     setReason(203, "Non Authoritative Information");
/*     */     
/* 195 */     setReason(205, "Reset Content");
/*     */     
/* 197 */     setReason(206, "Partial Content");
/*     */     
/* 199 */     setReason(504, "Gateway Timeout");
/*     */     
/* 201 */     setReason(505, "Http Version Not Supported");
/*     */     
/* 203 */     setReason(410, "Gone");
/*     */     
/* 205 */     setReason(411, "Length Required");
/*     */     
/* 207 */     setReason(416, "Requested Range Not Satisfiable");
/*     */     
/* 209 */     setReason(417, "Expectation Failed");
/*     */     
/*     */ 
/*     */ 
/* 213 */     setReason(102, "Processing");
/*     */     
/* 215 */     setReason(207, "Multi-Status");
/*     */     
/* 217 */     setReason(422, "Unprocessable Entity");
/*     */     
/* 219 */     setReason(419, "Insufficient Space On Resource");
/*     */     
/* 221 */     setReason(420, "Method Failure");
/*     */     
/* 223 */     setReason(423, "Locked");
/*     */     
/* 225 */     setReason(507, "Insufficient Storage");
/*     */     
/* 227 */     setReason(424, "Failed Dependency");
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/impl/EnglishReasonPhraseCatalog.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */