/*     */ package org.shaded.apache.http.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VersionInfo
/*     */ {
/*     */   public static final String UNAVAILABLE = "UNAVAILABLE";
/*     */   public static final String VERSION_PROPERTY_FILE = "version.properties";
/*     */   public static final String PROPERTY_MODULE = "info.module";
/*     */   public static final String PROPERTY_RELEASE = "info.release";
/*     */   public static final String PROPERTY_TIMESTAMP = "info.timestamp";
/*     */   private final String infoPackage;
/*     */   private final String infoModule;
/*     */   private final String infoRelease;
/*     */   private final String infoTimestamp;
/*     */   private final String infoClassloader;
/*     */   
/*     */   protected VersionInfo(String pckg, String module, String release, String time, String clsldr)
/*     */   {
/*  94 */     if (pckg == null) {
/*  95 */       throw new IllegalArgumentException("Package identifier must not be null.");
/*     */     }
/*     */     
/*     */ 
/*  99 */     this.infoPackage = pckg;
/* 100 */     this.infoModule = (module != null ? module : "UNAVAILABLE");
/* 101 */     this.infoRelease = (release != null ? release : "UNAVAILABLE");
/* 102 */     this.infoTimestamp = (time != null ? time : "UNAVAILABLE");
/* 103 */     this.infoClassloader = (clsldr != null ? clsldr : "UNAVAILABLE");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getPackage()
/*     */   {
/* 114 */     return this.infoPackage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getModule()
/*     */   {
/* 124 */     return this.infoModule;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getRelease()
/*     */   {
/* 134 */     return this.infoRelease;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getTimestamp()
/*     */   {
/* 144 */     return this.infoTimestamp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getClassloader()
/*     */   {
/* 156 */     return this.infoClassloader;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 166 */     StringBuffer sb = new StringBuffer(20 + this.infoPackage.length() + this.infoModule.length() + this.infoRelease.length() + this.infoTimestamp.length() + this.infoClassloader.length());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 171 */     sb.append("VersionInfo(").append(this.infoPackage).append(':').append(this.infoModule);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 176 */     if (!"UNAVAILABLE".equals(this.infoRelease))
/* 177 */       sb.append(':').append(this.infoRelease);
/* 178 */     if (!"UNAVAILABLE".equals(this.infoTimestamp)) {
/* 179 */       sb.append(':').append(this.infoTimestamp);
/*     */     }
/* 181 */     sb.append(')');
/*     */     
/* 183 */     if (!"UNAVAILABLE".equals(this.infoClassloader)) {
/* 184 */       sb.append('@').append(this.infoClassloader);
/*     */     }
/* 186 */     return sb.toString();
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
/*     */   public static final VersionInfo[] loadVersionInfo(String[] pckgs, ClassLoader clsldr)
/*     */   {
/* 202 */     if (pckgs == null) {
/* 203 */       throw new IllegalArgumentException("Package identifier list must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 207 */     ArrayList vil = new ArrayList(pckgs.length);
/* 208 */     for (int i = 0; i < pckgs.length; i++) {
/* 209 */       VersionInfo vi = loadVersionInfo(pckgs[i], clsldr);
/* 210 */       if (vi != null) {
/* 211 */         vil.add(vi);
/*     */       }
/*     */     }
/* 214 */     return (VersionInfo[])vil.toArray(new VersionInfo[vil.size()]);
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
/*     */   public static final VersionInfo loadVersionInfo(String pckg, ClassLoader clsldr)
/*     */   {
/* 232 */     if (pckg == null) {
/* 233 */       throw new IllegalArgumentException("Package identifier must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 237 */     if (clsldr == null) {
/* 238 */       clsldr = Thread.currentThread().getContextClassLoader();
/*     */     }
/* 240 */     Properties vip = null;
/*     */     
/*     */     try
/*     */     {
/* 244 */       InputStream is = clsldr.getResourceAsStream(pckg.replace('.', '/') + "/" + "version.properties");
/*     */       
/* 246 */       if (is != null) {
/*     */         try {
/* 248 */           Properties props = new Properties();
/* 249 */           props.load(is);
/* 250 */           vip = props;
/*     */         } finally {
/* 252 */           is.close();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException ex) {}
/*     */     
/*     */ 
/* 259 */     VersionInfo result = null;
/* 260 */     if (vip != null) {
/* 261 */       result = fromMap(pckg, vip, clsldr);
/*     */     }
/* 263 */     return result;
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
/*     */   protected static final VersionInfo fromMap(String pckg, Map info, ClassLoader clsldr)
/*     */   {
/* 279 */     if (pckg == null) {
/* 280 */       throw new IllegalArgumentException("Package identifier must not be null.");
/*     */     }
/*     */     
/*     */ 
/* 284 */     String module = null;
/* 285 */     String release = null;
/* 286 */     String timestamp = null;
/*     */     
/* 288 */     if (info != null) {
/* 289 */       module = (String)info.get("info.module");
/* 290 */       if ((module != null) && (module.length() < 1)) {
/* 291 */         module = null;
/*     */       }
/* 293 */       release = (String)info.get("info.release");
/* 294 */       if ((release != null) && ((release.length() < 1) || (release.equals("${pom.version}"))))
/*     */       {
/* 296 */         release = null;
/*     */       }
/* 298 */       timestamp = (String)info.get("info.timestamp");
/* 299 */       if ((timestamp != null) && ((timestamp.length() < 1) || (timestamp.equals("${mvn.timestamp}"))))
/*     */       {
/*     */ 
/*     */ 
/* 303 */         timestamp = null;
/*     */       }
/*     */     }
/* 306 */     String clsldrstr = null;
/* 307 */     if (clsldr != null) {
/* 308 */       clsldrstr = clsldr.toString();
/*     */     }
/* 310 */     return new VersionInfo(pckg, module, release, timestamp, clsldrstr);
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/util/VersionInfo.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */