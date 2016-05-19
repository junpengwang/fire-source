/*     */ package org.shaded.apache.http;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProtocolVersion
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 8950662842175091068L;
/*     */   protected final String protocol;
/*     */   protected final int major;
/*     */   protected final int minor;
/*     */   
/*     */   public ProtocolVersion(String protocol, int major, int minor)
/*     */   {
/*  75 */     if (protocol == null) {
/*  76 */       throw new IllegalArgumentException("Protocol name must not be null.");
/*     */     }
/*     */     
/*  79 */     if (major < 0) {
/*  80 */       throw new IllegalArgumentException("Protocol major version number must not be negative.");
/*     */     }
/*     */     
/*  83 */     if (minor < 0) {
/*  84 */       throw new IllegalArgumentException("Protocol minor version number may not be negative");
/*     */     }
/*     */     
/*  87 */     this.protocol = protocol;
/*  88 */     this.major = major;
/*  89 */     this.minor = minor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getProtocol()
/*     */   {
/*  98 */     return this.protocol;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getMajor()
/*     */   {
/* 107 */     return this.major;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getMinor()
/*     */   {
/* 116 */     return this.minor;
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
/*     */ 
/*     */ 
/*     */   public ProtocolVersion forVersion(int major, int minor)
/*     */   {
/* 137 */     if ((major == this.major) && (minor == this.minor)) {
/* 138 */       return this;
/*     */     }
/*     */     
/*     */ 
/* 142 */     return new ProtocolVersion(this.protocol, major, minor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int hashCode()
/*     */   {
/* 152 */     return this.protocol.hashCode() ^ this.major * 100000 ^ this.minor;
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
/*     */   public final boolean equals(Object obj)
/*     */   {
/* 170 */     if (this == obj) {
/* 171 */       return true;
/*     */     }
/* 173 */     if (!(obj instanceof ProtocolVersion)) {
/* 174 */       return false;
/*     */     }
/* 176 */     ProtocolVersion that = (ProtocolVersion)obj;
/*     */     
/* 178 */     return (this.protocol.equals(that.protocol)) && (this.major == that.major) && (this.minor == that.minor);
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
/*     */   public boolean isComparable(ProtocolVersion that)
/*     */   {
/* 195 */     return (that != null) && (this.protocol.equals(that.protocol));
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
/*     */ 
/*     */ 
/*     */   public int compareToVersion(ProtocolVersion that)
/*     */   {
/* 216 */     if (that == null) {
/* 217 */       throw new IllegalArgumentException("Protocol version must not be null.");
/*     */     }
/*     */     
/* 220 */     if (!this.protocol.equals(that.protocol)) {
/* 221 */       throw new IllegalArgumentException("Versions for different protocols cannot be compared. " + this + " " + that);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 226 */     int delta = getMajor() - that.getMajor();
/* 227 */     if (delta == 0) {
/* 228 */       delta = getMinor() - that.getMinor();
/*     */     }
/* 230 */     return delta;
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
/*     */   public final boolean greaterEquals(ProtocolVersion version)
/*     */   {
/* 245 */     return (isComparable(version)) && (compareToVersion(version) >= 0);
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
/*     */   public final boolean lessEquals(ProtocolVersion version)
/*     */   {
/* 260 */     return (isComparable(version)) && (compareToVersion(version) <= 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 270 */     CharArrayBuffer buffer = new CharArrayBuffer(16);
/* 271 */     buffer.append(this.protocol);
/* 272 */     buffer.append('/');
/* 273 */     buffer.append(Integer.toString(this.major));
/* 274 */     buffer.append('.');
/* 275 */     buffer.append(Integer.toString(this.minor));
/* 276 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 280 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /Users/junpengwang/Documents/FireJar/firebase-client-android-2.3.1.jar!/org/shaded/apache/http/ProtocolVersion.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */